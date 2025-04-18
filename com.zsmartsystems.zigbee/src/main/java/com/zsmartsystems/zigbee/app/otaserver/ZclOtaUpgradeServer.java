/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.app.otaserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zsmartsystems.zigbee.CommandResult;
import com.zsmartsystems.zigbee.ZigBeeCommand;
import com.zsmartsystems.zigbee.ZigBeeExecutors;
import com.zsmartsystems.zigbee.ZigBeeStatus;
import com.zsmartsystems.zigbee.app.ZigBeeApplication;
import com.zsmartsystems.zigbee.zcl.ZclCluster;
import com.zsmartsystems.zigbee.zcl.ZclCommand;
import com.zsmartsystems.zigbee.zcl.ZclCommandListener;
import com.zsmartsystems.zigbee.zcl.ZclStatus;
import com.zsmartsystems.zigbee.zcl.clusters.ZclOtaUpgradeCluster;
import com.zsmartsystems.zigbee.zcl.clusters.general.ReadAttributesCommand;
import com.zsmartsystems.zigbee.zcl.clusters.general.ReadAttributesResponse;
import com.zsmartsystems.zigbee.zcl.clusters.otaupgrade.ImageBlockCommand;
import com.zsmartsystems.zigbee.zcl.clusters.otaupgrade.ImageBlockResponse;
import com.zsmartsystems.zigbee.zcl.clusters.otaupgrade.ImageNotifyCommand;
import com.zsmartsystems.zigbee.zcl.clusters.otaupgrade.ImagePageCommand;
import com.zsmartsystems.zigbee.zcl.clusters.otaupgrade.QueryNextImageCommand;
import com.zsmartsystems.zigbee.zcl.clusters.otaupgrade.QueryNextImageResponse;
import com.zsmartsystems.zigbee.zcl.clusters.otaupgrade.UpgradeEndCommand;
import com.zsmartsystems.zigbee.zcl.clusters.otaupgrade.UpgradeEndResponse;
import com.zsmartsystems.zigbee.zcl.clusters.otaupgrade.ZclOtaUpgradeCommand;
import com.zsmartsystems.zigbee.zcl.field.ByteArray;

/**
 * This class implements the logic to implement the Over The Air (OTA) server for a ZigBee node.
 * <p>
 * OTA upgrade messages do not differ from typical ZigBee APS messages so the upgrade process should
 * not interrupt the general network operation.
 * <p>
 * OTA Upgrade cluster commands, the frame control value shall follow the description below:
 * <ul>
 * <li>Frame type is 0x01: commands are cluster specific (not a global command).
 * <li>Manufacturer specific is 0x00: commands are not manufacturer specific.
 * <li>Direction: shall be either 0x00 (client->server) or 0x01 (server->client) depending on the commands.
 * <li>Disable default response is 0x00 for all OTA request commands sent from client to server:
 * default response command shall be sent when the server receives OTA Upgrade cluster
 * request commands that it does not support or in case an error case happens. A detailed
 * explanation of each error case along with its recommended action is described for each OTA
 * cluster command.
 * <li>Disable default response is 0x01 for all OTA response commands (sent from server to client)
 * and for broadcast/multicast Image Notify command: default response command is not sent
 * when the client receives a valid OTA Upgrade cluster response commands or when it receives
 * broadcast or multicast Image Notify command. However, if a client receives invalid OTA
 * Upgrade cluster response command, a default response shall be sent. A detailed explanation of
 * each error case along with its recommended action is described for each OTA cluster
 * command.
 * </ul>
 * <p>
 * Users can register with {@link #addListener} to receive {@link ZigBeeOtaStatusCallback} calls when the status
 * changes.
 * <p>
 * Upgrade lifecycle overview -:
 * <ul>
 * <li>Server instantiated. Status set to {@link ZigBeeOtaServerStatus#OTA_UNINITIALISED}.
 * <li>{@link #setFirmware(ZigBeeOtaFile)} is called to set the firmware. Status set to
 * {@link ZigBeeOtaServerStatus#OTA_WAITING}
 * <li>Server sends <i>Image Notify</i> when the firmware is set.
 * <li>User can call {@link #notifyClient()} periodically to send <i>Image Notify</i>.
 * <li>Client sends <i>Query Next Image Request</i>. Status set to
 * {@link ZigBeeOtaServerStatus#OTA_TRANSFER_IN_PROGRESS}.
 * <li>Server sends <i>Query Next Image Response</i>
 * <li>Client sends <i>Image Block/Page Request</i>
 * <li>Server sends <i>Image Block Response</i>
 * <li>Client sends <i>Image Block/Page Request</i>
 * <li>Server sends <i>Image Block Response</i>
 * <li>... repeat to end of transfer
 * <li>Client sends <i>Upgrade End Request</i>. Status set to {@link ZigBeeOtaServerStatus#OTA_TRANSFER_COMPLETE}.
 * <li>Server waits for {@link #completeUpgrade()} to be called unless {@link ZclOtaUpgradeServer#autoUpgrade} is true.
 * <li>Server checks the client state. If it is {@link ImageUpgradeStatus.DOWNLOAD_COMPLETE} it sends <i>Upgrade End
 * Response</i>.
 * <li>Client should respond with the default response, but this may not always be implemented as the device may start
 * to run the new firmware. Status set to {@link ZigBeeOtaServerStatus#OTA_UPGRADE_FIRMWARE_RESTARTING}.
 * <li>Server requests the current file version running on the client and checks this against the OTA file version that
 * was loaded. Status set to {@link ZigBeeOtaServerStatus#OTA_UPGRADE_COMPLETE} if the version is consistent, or Status
 * set to {@link ZigBeeOtaServerStatus#OTA_UPGRADE_FAILED} on error.
 * <li>When new firmware becomes available, the process begins from the top.
 * </ul
 * <p>
 * The following error conditions apply -:
 * <ul>
 * <li>Once the transfer is started, if the client doesn't send an image block/page request within a defined period the
 * transfer will time out. This period is set with the {@link #setTransferTimeoutPeriod(long)} method.
 * <li>If the server receives messages out of sequence, they will be ignored and the transfer will abort. For example if
 * an ImageBlockRequest is received when the server has not received a QueryNextImageRequest the transfer will
 * terminate.
 * </ul>
 * <p>
 * This class uses the {@link ZclOtaUpgradeCluster} which provides the low level commands.
 *
 * @author Chris Jackson
 */
public class ZclOtaUpgradeServer implements ZigBeeApplication, ZclCommandListener {
    /**
     * A static Thread pool is used here to ensure that we don't end up with large numbers of page requests
     * spawning multiple threads. This should ensure a level of pacing if we had a lot of devices on the network that
     * suddenly wanted to upgrade using page requests at the same time.
     */
    private static ScheduledExecutorService pageScheduledExecutor = ZigBeeExecutors.newScheduledThreadPool(1,
            "OtaUpgradeServer");

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ZclOtaUpgradeServer.class);

    /**
     * The current {@link ZigBeeOtaServerStatus} associated with this server.
     */
    private ZigBeeOtaServerStatus status;

    /**
     * The parameter is part of Image Notify Command sent by the upgrade server. The parameter indicates
     * whether the client receiving Image Notify Command should send in Query Next Image Request
     * command or not.
     * <p>
     * The server chooses the parameter value between 1 and 100 (inclusively) and includes it in the Image
     * Notify Command. On receipt of the command, the client will examine other information (the
     * manufacturer code and image type) to determine if they match its own values. If they do not, it shall
     * discard the command and no further processing shall continue. If they do match then it will determine
     * whether or not it should query the upgrade server. It does this by randomly choosing a number
     * between 1 and 100 and comparing it to the value of the QueryJitter parameter received. If it is less than
     * or equal to the QueryJitter value from the server, it shall continue with the query process. If not, then it
     * shall discard the command and no further processing shall continue.
     * <p>
     * By using the QueryJitter parameter, it prevents a single notification of a new OTA upgrade image from
     * flooding the upgrade server with requests from clients. Default value is 50.
     */
    private Integer queryJitter;

    /**
     * A value that indicates the length of the OTA image data included in the (Image Block Response)
     * command payload sent from the server to client. Default value is 0xff.
     */
    private Integer dataSize = 60;

    /**
     * The {@link ZclCluster} to which this server process belongs
     */
    private ZclOtaUpgradeCluster cluster;

    /**
     * The current firmware for this node.
     */
    private ZigBeeOtaFile otaFile = null;

    /**
     * A boolean defining the autoUpgrade state. If true, the server will automatically upgrade
     * the firmware in a device once the transfer is complete. If false, the user must explicitly
     * call {@link #completeUpgrade()} to complete the upgrade.
     * <p>
     * This defaults to true. It has been observed that if the upgrade is not completed quickly after the completion of
     * the transfer some devices will start the transfer from the beginning.
     */
    private boolean autoUpgrade = true;

    /**
     * Flag to allow the loading of existing firmware. If set to false, the server will not load a version of the
     * firmware that a device reports is already loaded.
     */
    private boolean allowExistingFile = false;

    /**
     * Our page scheduler task
     */
    private ScheduledFuture<?> scheduledPageTask;

    /**
     * Timer used to handle transfer timeout
     */
    private ScheduledExecutorService timer = ZigBeeExecutors.newScheduledThreadPool(1, "OtaUpgradeTimer");

    /**
     * Current timer task
     */
    private ScheduledFuture<?> timerTask;

    /**
     * The current percentage complete
     */
    private int percentComplete = 0;

    /**
     * Default transfer timeout period in milliseconds
     */
    private static final long TRANSFER_TIMEOUT_PERIOD = 300000;

    /**
     * Transfer timeout period in milliseconds
     */
    private long transferTimeoutPeriod = TRANSFER_TIMEOUT_PERIOD;

    /**
     * The amount of retries to get the current firmware version
     */
    private static final int CURRENT_FW_VERSION_REQUEST_RETRIES = 10;

    /**
     * The sleep time before trying to request the current firmware version
     */
    private static final long CURRENT_FW_VERSION_REQUEST_DELAY = 10000;

    /**
     * Field control value of 0x01 (bit 0 set) means that the client’s IEEE address is included in the payload. This
     * indicates that the client is requesting a device specific file such as security credential, log or configuration;
     * hence, the need to include the device’s IEEE address in the image request command.
     */
    private static final int IMAGE_BLOCK_FIELD_IEEE_ADDRESS = 0x01;

    /**
     * Specifies that the minimum block period field is present
     */
    private static final int IMAGE_BLOCK_FIELD_MINIMUM_BLOCK_PERIOD = 0x02;

    /**
     * A list of listeners to receive status callbacks
     */
    private List<ZigBeeOtaStatusCallback> statusListeners = Collections
            .unmodifiableList(new ArrayList<ZigBeeOtaStatusCallback>());

    /**
     * Constructor
     */
    public ZclOtaUpgradeServer() {
        status = ZigBeeOtaServerStatus.OTA_UNINITIALISED;

        // queryJitter needs to be a random value between 1 and 100
//        queryJitter = new Random().nextInt(100) + 1;
        queryJitter = 100;
    }

    @Override
    public ZigBeeStatus appStartup(final ZclCluster cluster) {
        this.cluster = (ZclOtaUpgradeCluster) cluster;
        cluster.addCommandListener(this);

        return ZigBeeStatus.SUCCESS;
    }

    @Override
    public void appShutdown() {
        cluster.removeCommandListener(this);
        stopTransferTimer();
        timer.shutdownNow();
    }

    @Override
    public int getClusterId() {
        return ZclOtaUpgradeCluster.CLUSTER_ID;
    }

    /**
     * Gets the current status of the server
     *
     * @return the {@link ZigBeeOtaServerStatus}
     */
    public ZigBeeOtaServerStatus getServerStatus() {
        return status;
    }

    /**
     * Cancels any upgrade transfers that are in progress and removes the current file. If a transfer is currently in
     * progress, then the listeners are notified.
     */
    public void cancelUpgrade() {
        otaFile = null;

        ZigBeeOtaServerStatus localStatus = status;
        status = ZigBeeOtaServerStatus.OTA_UNINITIALISED;

        if (localStatus != ZigBeeOtaServerStatus.OTA_WAITING && localStatus != ZigBeeOtaServerStatus.OTA_UNINITIALISED
                && localStatus != ZigBeeOtaServerStatus.OTA_UPGRADE_COMPLETE) {
            updateStatus(ZigBeeOtaServerStatus.OTA_CANCELLED);
        }
    }

    /**
     * Add a listener to receive status callbacks on the OTA server status.
     *
     * @param listener the {@link ZigBeeOtaStatusCallback} to receive the status
     */
    public void addListener(final ZigBeeOtaStatusCallback listener) {
        synchronized (this) {
            if (listener == null || statusListeners.contains(listener)) {
                return;
            }
            final List<ZigBeeOtaStatusCallback> modifiedListeners = new ArrayList<ZigBeeOtaStatusCallback>(
                    statusListeners);
            modifiedListeners.add(listener);
            statusListeners = Collections.unmodifiableList(modifiedListeners);
        }
    }

    /**
     * Remove a listener from receiving status callbacks on the OTA server status.
     *
     * @param listener the {@link ZigBeeOtaStatusCallback} to stop receiving status callbacks
     */
    public void removeListener(final ZigBeeOtaStatusCallback listener) {
        synchronized (this) {
            final List<ZigBeeOtaStatusCallback> modifiedListeners = new ArrayList<ZigBeeOtaStatusCallback>(
                    statusListeners);
            modifiedListeners.remove(listener);
            statusListeners = Collections.unmodifiableList(modifiedListeners);
        }
    }

    /**
     * Sets the firmware file for this node and send a notification to the device.
     * <p>
     * The file must conform to the standard file format containing the OTA Header, upgrade image, signer certificate,
     * signature.
     *
     * @param otaFile the current firmware version for this node
     */
    public void setFirmware(ZigBeeOtaFile otaFile) {
        setFirmwareWithoutNotify(otaFile);
        notifyClient();
    }

    /**
     * Sets the firmware file for this node.
     * <p>
     * The file must conform to the standard file format containing the OTA Header, upgrade image, signer certificate,
     * signature.
     *
     * @param otaFile the current firmware version for this node
     */
    public void setFirmwareWithoutNotify(ZigBeeOtaFile otaFile) {
        logger.debug("{}: OTA Server file set: {}", cluster.getZigBeeAddress(), otaFile);
        updateStatus(ZigBeeOtaServerStatus.OTA_WAITING);
        this.otaFile = otaFile;
    }

    /**
     * The purpose of sending Image Notify command is so the server has a way to notify client devices of
     * when the OTA upgrade images are available for them. It eliminates the need for ZR client devices
     * having to check with the server periodically of when the new images are available. However, all client
     * devices still need to send in Query Next Image Request command in order to officially start the OTA
     * upgrade process.
     */
    public void notifyClient() {
        // Only send the notify if the file is set
        if (otaFile == null) {
            return;
        }

        ImageNotifyCommand command = new ImageNotifyCommand(0, queryJitter, otaFile.getManufacturerCode(), otaFile.getImageType(), otaFile.getFileVersion());
        command.setDisableDefaultResponse(true);
        cluster.sendCommand(command);
    }

    /**
     * Sets the data size used in sending data to the client. Allowable range is 0 to 255.
     * <p>
     * A value that indicates the length of the OTA image data included in the (Image Block Response)
     * command payload sent from the server to client.
     *
     * @param dataSize the size of each data packet (0 to 255)
     */
    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    /**
     * Tells the server to automatically upgrade the firmware once the transfer is completed.
     * If autoUpgrade is not set, then the user must explicitly call {@link #completeUpgrade} once the server
     * state has reached {@link ZigBeeOtaServerStatus#OTA_TRANSFER_COMPLETE}.
     * <p>
     * It has been observed that if the upgrade is not completed quickly after the completion of
     * the transfer some devices will start the transfer from the beginning.
     *
     * @param autoUpgrade boolean defining the autoUpgrade state
     */
    public void setAutoUpgrade(boolean autoUpgrade) {
        this.autoUpgrade = autoUpgrade;
    }

    /**
     * Set the flag to allow upload of existing file version. By default the server will not reload an existing version.
     *
     * @param allowExistingFile true if the server is permitted to reload the same firmware to the device
     */
    public void setAllowExistingFile(boolean allowExistingFile) {
        this.allowExistingFile = allowExistingFile;
    }

    /**
     * Sets the transfer timeout period. Once a transfer is started, the server expects to receive an image block or
     * page request within the timer period. If this does not occur, the transfer will be aborted.
     *
     * @param transferTimeoutPeriod the timeout period in milliseconds
     */
    public synchronized void setTransferTimeoutPeriod(long transferTimeoutPeriod) {
        this.transferTimeoutPeriod = transferTimeoutPeriod;
    }

    /**
     * Instruct the server to complete the upgrade. This is only possible if the client state is
     * {@link ImageUpgradeStatus#DOWNLOAD_COMPLETE}.
     * The server will check the client status before sending the upgrade end response command.
     * The caller will be notified through the {@link ZigBeeOtaStatusCallback} of the completion status of this method.
     *
     * @return true if the upgrade is in a state to be completed, otherwise false.
     */
    public boolean completeUpgrade() {
        return completeUpgrade(null);
    }

    /**
     * Instruct the server to complete the upgrade. This is only possible if the client state is
     * {@link ImageUpgradeStatus#DOWNLOAD_COMPLETE}.
     * The server will check the client status before sending the upgrade end response command.
     * The caller will be notified through the {@link ZigBeeOtaStatusCallback} of the completion status of this method.
     *
     * @param command the received {@link UpgradeEndCommand}
     * @return true if the upgrade is in a state to be completed, otherwise false.
     */
    public boolean completeUpgrade(UpgradeEndCommand command) {
        // TODO: Handle the time?
        new Thread("OtaCompleteUpgrade") {
            @Override
            public void run() {
                try {
                    Integer statusValue = (Integer) cluster.getAttribute(ZclOtaUpgradeCluster.ATTR_IMAGEUPGRADESTATUS)
                            .readValue(0);
                    if (statusValue == null) {
                        // Failed to get the client status
                        updateStatus(ZigBeeOtaServerStatus.OTA_UPGRADE_FAILED);
                        return;
                    }
                    ImageUpgradeStatus status = ImageUpgradeStatus.getStatus(statusValue);
                    if (status != ImageUpgradeStatus.DOWNLOAD_COMPLETE
                            && status != ImageUpgradeStatus.WAITING_TO_UPGRADE) {
                        // Client is not in correct state to end upgrade
                        switch (status) {
                            case COUNT_DOWN:
                                updateStatus(ZigBeeOtaServerStatus.OTA_UPGRADE_WAITING);
                                break;
                            case DOWNLOAD_IN_PROGRESS:
                                updateStatus(ZigBeeOtaServerStatus.OTA_TRANSFER_IN_PROGRESS);
                                break;
                            case WAIT_FOR_MORE:
                                updateStatus(ZigBeeOtaServerStatus.OTA_WAITING);
                                break;
                            case NORMAL:
                            case UNKNOWN:
                            default:
                                updateStatus(ZigBeeOtaServerStatus.OTA_UPGRADE_FAILED);
                                break;
                        }
                        return;
                    }

                    updateStatus(ZigBeeOtaServerStatus.OTA_UPGRADE_FIRMWARE_RESTARTING);

                    UpgradeEndResponse upgradeEndResponse = new UpgradeEndResponse(otaFile.getManufacturerCode(),
                            otaFile.getImageType(), otaFile.getFileVersion(), 0, 0);

                    // If we received a UpgradeEndCommand then send the UpgradeEndResponse as a response. Otherwise send
                    // it as a commands
                    CommandResult response;
                    if (command != null) {
                        response = cluster.sendResponse(command, upgradeEndResponse).get();
                    } else {
                        response = cluster.sendCommand(upgradeEndResponse).get();
                    }

                    if (!(response.isSuccess() || response.isTimeout())) {
                        updateStatus(ZigBeeOtaServerStatus.OTA_UPGRADE_FAILED);
                        return;
                    }

                    // Attempt to get the current firmware version. As the device will be restarting, which could take
                    // some time to complete, we retry this a few times.
                    for (int cnt = 0; cnt < CURRENT_FW_VERSION_REQUEST_RETRIES; cnt++) {
                        Thread.sleep(CURRENT_FW_VERSION_REQUEST_DELAY);
                        CommandResult currentVersionCommandResult = cluster.sendCommand(
                                new ReadAttributesCommand(Arrays.asList(ZclOtaUpgradeCluster.ATTR_CURRENTFILEVERSION)))
                                .get();

                        if (currentVersionCommandResult == null) {
                            continue;
                        }

                        ZigBeeCommand currentVersionResponse = currentVersionCommandResult.getResponse();

                        if (currentVersionResponse instanceof ReadAttributesResponse) {
                            ReadAttributesResponse attributesResponse = (ReadAttributesResponse) currentVersionResponse;
                            logger.debug("{}: OTA file version to be installed={} Received ReadAttributesResponse: {}",
                                    cluster.getZigBeeAddress(), otaFile.getFileVersion(), attributesResponse);

                            if (!attributesResponse.getRecords().isEmpty()) {
                                if (attributesResponse.getRecords().get(0).getStatus() == ZclStatus.SUCCESS) {
                                    Integer fileVersion = (Integer) attributesResponse.getRecords().get(0)
                                            .getAttributeValue();

                                    if (fileVersion == null) {
                                        continue;
                                    }

                                    if (fileVersion.equals(otaFile.getFileVersion())) {
                                        updateStatus(ZigBeeOtaServerStatus.OTA_UPGRADE_COMPLETE);
                                        return;
                                    }
                                } else if (attributesResponse.getRecords().get(0)
                                        .getStatus() == ZclStatus.UNSUPPORTED_ATTRIBUTE) {
                                    // since this attribute is optional in the specification, we take a reply with
                                    // UNSUPPORTED_ATTRIBUTE as the case where the device is reachable again after it
                                    // has successfully rebooted
                                    updateStatus(ZigBeeOtaServerStatus.OTA_UPGRADE_COMPLETE);
                                    return;
                                }
                            }
                        }
                    }
                    logger.debug(
                            "{}: All attempts to reach the device failed after it should have rebooted, taking this as a failure.",
                            cluster.getZigBeeAddress());
                    updateStatus(ZigBeeOtaServerStatus.OTA_UPGRADE_FAILED);
                } catch (InterruptedException | ExecutionException e) {
                    logger.debug("{}: Error during OTA completeUpgrade ", cluster.getZigBeeAddress(), e);
                    updateStatus(ZigBeeOtaServerStatus.OTA_UPGRADE_FAILED);
                }
            }
        }.start();

        return false;
    }

    /**
     * The file version of the running firmware image on the device. The information is available for the server to
     * query via ZCL read attribute command. The attribute is optional on the client.
     * <p>
     * This calls the synchronous method in the cluster, and always performs an update (ie will not use cached data) to
     * ensure it is updated following any OTA upgrade operation.
     *
     * @return the current firmware version on the remote device
     */
    public Integer getCurrentFileVersion() {
        return (Integer) cluster.getAttribute(ZclOtaUpgradeCluster.ATTR_CURRENTFILEVERSION).readValue(0);
    }

    /**
     * Send an updated status on OTA progress to the listeners
     *
     * @param updatedStatus the new {@link ZigBeeOtaServerStatus}
     */
    private void updateStatus(final ZigBeeOtaServerStatus updatedStatus) {
        logger.debug("{} OTA status updated to {}.", cluster.getZigBeeAddress(), updatedStatus);
        status = updatedStatus;

        synchronized (this) {
            // Notify the listeners
            for (final ZigBeeOtaStatusCallback statusListener : statusListeners) {
                cluster.getNotificationService().execute(new Runnable() {
                    @Override
                    public void run() {
                        statusListener.otaStatusUpdate(updatedStatus, percentComplete);
                    }
                });
            }
        }
    }

    /**
     * Sends an image block to the client
     *
     * @param command the request command. May be null.
     * @param fileOffset the offset into the {@link ZigBeeOtaFile} to send the block
     * @param maximumDataSize the maximum data size the client can accept
     * @return the number of bytes sent
     */
    private int sendImageBlock(ZclOtaUpgradeCommand command, int fileOffset, int maximumDataSize) {
        ByteArray imageData = otaFile.getImageData(fileOffset, Math.min(dataSize, maximumDataSize));
        logger.debug("{} OTA Data: Sending {} bytes at offset {}", cluster.getZigBeeAddress(), imageData.size(),
                fileOffset);

        ImageBlockResponse response = new ImageBlockResponse(
                ZclStatus.SUCCESS,
                otaFile.getManufacturerCode(),
                otaFile.getImageType(),
                otaFile.getFileVersion(),
                fileOffset,
                imageData);

        if (command == null) {
            response.setDisableDefaultResponse(true);
            response.setAckRequest(false);
            cluster.sendCommand(response);
        } else {
            cluster.sendResponse(command, response);
        }

        return imageData.size();
    }

    /**
     * Handles the sending of {@link ImageBlockResponse} following a {@link ImagePageCommand}
     *
     * @param request the request command
     */
    private void doPageResponse(ImagePageCommand request) {
        class PageSender implements Runnable {
            private ImagePageCommand request;
            private int pagePosition;
            private final int pageEnd;
            private final int maximumDataSize;

            PageSender(ImagePageCommand request) {
                this.request = request;
                this.pagePosition = request.getFileOffset();
                this.pageEnd = request.getFileOffset() + request.getPageSize();
                this.maximumDataSize = request.getMaximumDataSize();

                // Stop the timer - restart it once the page has been sent
                // Restart the timer
                stopTransferTimer();
            }

            @Override
            public void run() {
                // Send the block response
                // TODO: Ideally we should disable APS retry for page requests
                int dataSent = sendImageBlock(request, pagePosition, maximumDataSize);

                // Refer to request only in a first block set.
                // Following blocks will have incremental sequence id so we null request ref here
                request = null;

                // If dataSent is 0, then we either reached the end of file, or there was an error
                // Either way, let's abort!
                if (dataSent == 0) {
                    pagePosition = Integer.MAX_VALUE;
                } else {
                    pagePosition += dataSent;
                }

                // Have we reached the end of the page?
                if (pagePosition > pageEnd) {
                    synchronized (pageScheduledExecutor) {
                        scheduledPageTask.cancel(false);
                        scheduledPageTask = null;

                        // Restart the timer
                        startTransferTimer();
                    }
                }
            }
        }
        ;

        synchronized (pageScheduledExecutor) {
            // Stop our task if it's running
            if (scheduledPageTask != null) {
                scheduledPageTask.cancel(true);
            }

            // Start the new task
            PageSender pageSender = new PageSender(request);
            scheduledPageTask = pageScheduledExecutor.scheduleAtFixedRate(pageSender, request.getResponseSpacing(),
                    request.getResponseSpacing(), TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Handle a received {@link QueryNextImageCommand} command.
     * This sends information on the firmware that is currently set in this server.
     *
     * @param command the received {@link QueryNextImageCommand}
     * @return true if the handler has, or will send a response to this command
     */
    private boolean handleQueryNextImageCommand(QueryNextImageCommand command) {
        if (otaFile == null) {
            logger.debug("{} OTA Server: No file set in QueryNextImageCommand.",
                    cluster.getZigBeeAddress());
            ZigBeeOtaFile newOtaFile = notifyUpdateRequestReceived(command);

            if (newOtaFile == null) {
                logger.debug("{} OTA Server: No file returned from listeners.",
                        cluster.getZigBeeAddress());
                sendNoImageAvailableResponse(command);
                return true;
            }

            logger.debug("{}: OTA Server listeners set file: {}", cluster.getZigBeeAddress(), otaFile);
            otaFile = newOtaFile;
            updateStatus(ZigBeeOtaServerStatus.OTA_WAITING);
        }

        // Ignore the request if we're not in the correct state
        if (status != ZigBeeOtaServerStatus.OTA_WAITING) {
            logger.debug("{} OTA Error: Invalid server state {} when handling QueryNextImageCommand.",
                    cluster.getZigBeeAddress(), status);
            cluster.sendDefaultResponse(command, ZclStatus.UNKNOWN);
            return true;
        }

        // Check that the file attributes are consistent with the file we have
        if (!(command.getManufacturerCode().equals(otaFile.getManufacturerCode())
                && command.getImageType().equals(otaFile.getImageType()))) {
            logger.debug("{} OTA Error: Request is inconsistent with OTA file - manufacturer={}/{}, imageType={}/{}",
                    cluster.getZigBeeAddress(), String.format("%04X", otaFile.getManufacturerCode()),
                    String.format("%04X", command.getManufacturerCode()), String.format("%02X", otaFile.getImageType()),
                    String.format("%02X", command.getImageType()));
            sendNoImageAvailableResponse(command);
            return true;
        }

        // If the request contains a hardware version, and the OTA file also has the hardware restriction
        // then perform a check
        if (command.getHardwareVersion() != null && otaFile.getMinimumHardware() != null
                && otaFile.getMaximumHardware() != null) {
            if (command.getHardwareVersion() < otaFile.getMinimumHardware()
                    || command.getHardwareVersion() > otaFile.getMaximumHardware()) {
                logger.debug(
                        "{} OTA Error: Request is inconsistent with hardware - requested={}, file min={}, file max={}",
                        cluster.getZigBeeAddress(), command.getHardwareVersion(), otaFile.getMinimumHardware(),
                        otaFile.getMaximumHardware());
                sendNoImageAvailableResponse(command);
                return true;
            }
        }

        // Some devices may make further requests for files once they have been updated
        // By default, don't resend the existing file
        if (!allowExistingFile && command.getFileVersion().equals(otaFile.getFileVersion())) {
            logger.debug("{} OTA Error: Request is for existing file version {}", cluster.getZigBeeAddress(),
                    command.getFileVersion());
            sendNoImageAvailableResponse(command);
            return true;
        }

        // Update the state as we're starting
        updateStatus(ZigBeeOtaServerStatus.OTA_TRANSFER_IN_PROGRESS);
        startTransferTimer();

        cluster.sendResponse(command, new QueryNextImageResponse(
                ZclStatus.SUCCESS,
                otaFile.getManufacturerCode(),
                otaFile.getImageType(),
                otaFile.getFileVersion(),
                otaFile.getImageSize()));

        return true;
    }

    private void sendNoImageAvailableResponse(QueryNextImageCommand command) {
        QueryNextImageResponse response = new QueryNextImageResponse(
                ZclStatus.NO_IMAGE_AVAILABLE,
                command.getManufacturerCode(),
                command.getImageType(),
                command.getFileVersion(),
                0);
        cluster.sendResponse(command, response);
    }

    /**
     * Handle a received {@link ImagePageCommand} command.
     * This will respond with a whole page (potentially a number of image blocks)
     *
     * @param command the received {@link ImagePageCommand}
     * @return true if the handler has, or will send a response to this command
     */
    private boolean handleImagePageCommand(ImagePageCommand command) {
        // Ignore the request if we're not in the correct state
        if (status != ZigBeeOtaServerStatus.OTA_TRANSFER_IN_PROGRESS) {
            logger.debug("{} OTA Error: Invalid server state {} when handling ImagePageCommand.",
                    cluster.getZigBeeAddress(), status);
            cluster.sendDefaultResponse(command, ZclStatus.UNKNOWN);
            return true;
        }

        // No current support for device specific requests
        if ((command.getFieldControl() & IMAGE_BLOCK_FIELD_IEEE_ADDRESS) != 0) {
            logger.debug("{} OTA Error: No file is set.", cluster.getZigBeeAddress());
            cluster.sendDefaultResponse(command, ZclStatus.UNSUP_CLUSTER_COMMAND);
            return true;
        }

        // Check that the file attributes are consistent with the file we have
        if (otaFile == null || !command.getManufacturerCode().equals(otaFile.getManufacturerCode())
                || !command.getFileVersion().equals(otaFile.getFileVersion())
                || !command.getImageType().equals(otaFile.getImageType())) {
            logger.debug("{} OTA Error: Request is inconsistent with OTA file.", cluster.getZigBeeAddress());
            cluster.sendDefaultResponse(command, ZclStatus.NO_IMAGE_AVAILABLE);
            return true;
        }

        // Check that the offset is within bounds of the image data
        if (command.getFileOffset() > otaFile.getImageSize()) {
            logger.debug("{} OTA Error: Requested offset is larger than file ({}>{})", cluster.getZigBeeAddress(),
                    command.getFileOffset(), otaFile.getImageSize());
            cluster.sendDefaultResponse(command, ZclStatus.MALFORMED_COMMAND);
            return true;
        }

        doPageResponse(command);

        return true;
    }

    /**
     * Handle a received {@link ImageBlockCommand} command.
     * This will respond with a single image block.
     *
     * @param command the received {@link ImageBlockCommand}
     * @return true if the handler has, or will send a response to this command
     */
    private boolean handleImageBlockCommand(ImageBlockCommand command) {
        // Ignore the request if we're not in the correct state
        if (status != ZigBeeOtaServerStatus.OTA_TRANSFER_IN_PROGRESS) {
            logger.debug("{} OTA Error: Invalid server state {} when handling ImageBlockCommand.",
                    cluster.getZigBeeAddress(), status);
            cluster.sendDefaultResponse(command, ZclStatus.UNKNOWN);
            return true;
        }

        // No current support for device specific requests
        if ((command.getFieldControl() & IMAGE_BLOCK_FIELD_IEEE_ADDRESS) != 0) {
            logger.debug("{} OTA Error: No file is set.", cluster.getZigBeeAddress());
            cluster.sendDefaultResponse(command, ZclStatus.UNSUP_CLUSTER_COMMAND);
            return true;
        }

        // Check that the file attributes are consistent with the file we have
        if (otaFile == null || !command.getManufacturerCode().equals(otaFile.getManufacturerCode())
                || !command.getFileVersion().equals(otaFile.getFileVersion())
                || !command.getImageType().equals(otaFile.getImageType())) {
            logger.debug("{} OTA Error: Request is inconsistent with OTA file.", cluster.getZigBeeAddress());
            cluster.sendDefaultResponse(command, ZclStatus.NO_IMAGE_AVAILABLE);
            return true;
        }

        // Check that the offset is within bounds of the image data
        if (command.getFileOffset() > otaFile.getImageSize()) {
            logger.debug("{} OTA Error: Requested offset is larger than file ({}>{})", cluster.getZigBeeAddress(),
                    command.getFileOffset(), otaFile.getImageSize());
            cluster.sendDefaultResponse(command, ZclStatus.MALFORMED_COMMAND);
            return true;
        }

        // Restart the timer
        startTransferTimer();

        int percent = command.getFileOffset() * 100 / otaFile.getImageSize();
        if (percent > 100) {
            percent = 100;
        }
        if (percent != percentComplete) {
            percentComplete = percent;
            updateStatus(ZigBeeOtaServerStatus.OTA_TRANSFER_IN_PROGRESS);
        }

        // Send the block response
        sendImageBlock(command, command.getFileOffset(), command.getMaximumDataSize());
        return true;
    }

    /**
     * Handle a received {@link UpgradeEndCommand} command.
     * If autoUpgrade is set, then we immediately send the {@link UpgradeEndResponse}
     *
     * @param command the received {@link UpgradeEndCommand}
     * @return true if the handler has, or will send a response to this command
     */
    private boolean handleUpgradeEndCommand(UpgradeEndCommand command) {
        // Ignore the request if we're not in the correct state
        if (status != ZigBeeOtaServerStatus.OTA_TRANSFER_IN_PROGRESS
                && status != ZigBeeOtaServerStatus.OTA_TRANSFER_COMPLETE) {
            logger.debug("{} OTA Error: Invalid server state {} when handling UpgradeEndCommand.",
                    cluster.getZigBeeAddress(), status);
            cluster.sendDefaultResponse(command, ZclStatus.UNKNOWN);
            return true;
        }

        // Check that the file attributes are consistent with the file we have
        if (otaFile == null || !command.getManufacturerCode().equals(otaFile.getManufacturerCode())
                || !command.getFileVersion().equals(otaFile.getFileVersion())
                || !command.getImageType().equals(otaFile.getImageType())) {
            logger.debug("{} OTA Error: Request is inconsistent with OTA file.", cluster.getZigBeeAddress());
            cluster.sendDefaultResponse(command, ZclStatus.NO_IMAGE_AVAILABLE);
            return true;
        }

        // Stop the transfer timer
        stopTransferTimer();

        percentComplete = 100;

        // Handle the status
        switch (command.getStatus()) {
            case INVALID_IMAGE:
                updateStatus(ZigBeeOtaServerStatus.OTA_UPGRADE_FAILED);
                break;
            case REQUIRE_MORE_IMAGE:
                updateStatus(ZigBeeOtaServerStatus.OTA_WAITING);
                break;
            default:
                updateStatus(ZigBeeOtaServerStatus.OTA_TRANSFER_COMPLETE);
                if (autoUpgrade) {
                    completeUpgrade(command);
                }
                break;
        }

        cluster.sendDefaultResponse(command, ZclStatus.SUCCESS);
        return true;
    }

    /**
     * Start or restart the transfer timer
     */
    private synchronized void startTransferTimer() {
        // Stop any existing timer
        stopTransferTimer();

        // Create the timer task
        timerTask = timer.schedule(new OtaTransferTimer(), transferTimeoutPeriod, TimeUnit.MILLISECONDS);
    }

    /**
     * Stop the transfer timer
     */
    private synchronized void stopTransferTimer() {
        // Stop any existing timer
        if (timerTask != null) {
            timerTask.cancel(true);
            timerTask = null;
        }
    }

    private class OtaTransferTimer implements Runnable {
        @Override
        public void run() {
            logger.debug("{}: OTA Error: Timeout - aborting transfer.", cluster.getZigBeeAddress());

            updateStatus(ZigBeeOtaServerStatus.OTA_UPGRADE_FAILED);
        }
    }

    @Override
    public boolean commandReceived(final ZclCommand command) {
        if (command instanceof QueryNextImageCommand) {
            return handleQueryNextImageCommand((QueryNextImageCommand) command);
        }

        if (command instanceof ImageBlockCommand) {
            return handleImageBlockCommand((ImageBlockCommand) command);
        }

        if (command instanceof ImagePageCommand) {
            return handleImagePageCommand((ImagePageCommand) command);
        }

        if (command instanceof UpgradeEndCommand) {
            return handleUpgradeEndCommand((UpgradeEndCommand) command);
        }

        return false;
    }

    private ZigBeeOtaFile notifyUpdateRequestReceived(final QueryNextImageCommand command) {
        CountDownLatch latch;
        List<ZigBeeOtaFile> otaFiles = new ArrayList<>();

        synchronized (this) {
            // Notify the listeners
            latch = new CountDownLatch(statusListeners.size());
            for (final ZigBeeOtaStatusCallback statusListener : statusListeners) {
                cluster.getNotificationService().execute(new Runnable() {
                    @Override
                    public void run() {
                        logger.trace("{}: ZigBeeOtaServer.notifyUpdateRequestReceived {} of {}",
                                cluster.getZigBeeAddress(), statusListener, command);

                        ZigBeeOtaFile response = statusListener.otaIncomingRequest(command);
                        if (response != null) {
                            otaFiles.add(response);
                            while (latch.getCount() > 0) {
                                latch.countDown();
                            }
                        } else {
                            latch.countDown();
                        }
                    }
                });
            }
        }

        try {
            // TODO: Set the timer properly
            if (latch.await(500, TimeUnit.MILLISECONDS)) {
                logger.trace("{}: ZigBeeOtaServer.notifyUpdateRequestReceived LATCH Done - {}",
                        cluster.getZigBeeAddress(), otaFiles);
            } else {
                logger.trace("{}: ZigBeeOtaServer.notifyUpdateRequestReceived LATCH Timeout, remaining = {}",
                        cluster.getZigBeeAddress(), latch.getCount());
            }
        } catch (InterruptedException e) {
            logger.trace("{}: ZigBeeOtaServer.notifyUpdateRequestReceived LATCH Interrupted, remaining = {}",
                    cluster.getZigBeeAddress(), latch.getCount());
        }

        if (otaFiles.isEmpty()) {
            logger.trace("{}: ZigBeeOtaServer.notifyUpdateRequestReceived no files available",
                    cluster.getZigBeeAddress());
            return null;
        }

        if (otaFiles.size() > 1) {
            logger.debug(
                    "{}: ZigBeeOtaServer.notifyUpdateRequestReceived received multiple OTA files - the first response will be used",
                    cluster.getZigBeeAddress());
        }

        return otaFiles.get(0);
    }

    @Override
    public String toString() {
        return "ZigBeeOtaServer [status=" + status + ", cluster=" + cluster + ", otaFile=" + otaFile + "]";
    }

}
