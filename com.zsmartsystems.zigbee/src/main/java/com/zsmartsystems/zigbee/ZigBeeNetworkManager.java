/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.zsmartsystems.zigbee.zdo.command.DeviceAnnounce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zsmartsystems.zigbee.ZigBeeNode.ZigBeeNodeState;
import com.zsmartsystems.zigbee.app.ZigBeeNetworkExtension;
import com.zsmartsystems.zigbee.aps.ApsDataEntity;
import com.zsmartsystems.zigbee.aps.ZigBeeApsFrame;
import com.zsmartsystems.zigbee.database.ZigBeeNetworkDataStore;
import com.zsmartsystems.zigbee.database.ZigBeeNetworkDatabaseManager;
import com.zsmartsystems.zigbee.groups.ZigBeeGroup;
import com.zsmartsystems.zigbee.groups.ZigBeeNetworkGroupManager;
import com.zsmartsystems.zigbee.groups.ZigBeeNetworkGroupManager.GroupSynchronizationMethod;
import com.zsmartsystems.zigbee.internal.ClusterMatcher;
import com.zsmartsystems.zigbee.internal.NotificationService;
import com.zsmartsystems.zigbee.internal.ZigBeeCommandNotifier;
import com.zsmartsystems.zigbee.security.ZigBeeKey;
import com.zsmartsystems.zigbee.serialization.ZigBeeDeserializer;
import com.zsmartsystems.zigbee.serialization.ZigBeeSerializer;
import com.zsmartsystems.zigbee.transaction.ZigBeeTransactionManager;
import com.zsmartsystems.zigbee.transaction.ZigBeeTransactionMatcher;
import com.zsmartsystems.zigbee.transport.TransportConfig;
import com.zsmartsystems.zigbee.transport.TransportConfigOption;
import com.zsmartsystems.zigbee.transport.ZigBeeTransportProgressState;
import com.zsmartsystems.zigbee.transport.ZigBeeTransportReceive;
import com.zsmartsystems.zigbee.transport.ZigBeeTransportState;
import com.zsmartsystems.zigbee.transport.ZigBeeTransportTransmit;
import com.zsmartsystems.zigbee.zcl.ZclCluster;
import com.zsmartsystems.zigbee.zcl.ZclCommand;
import com.zsmartsystems.zigbee.zcl.ZclFieldDeserializer;
import com.zsmartsystems.zigbee.zcl.ZclFieldSerializer;
import com.zsmartsystems.zigbee.zcl.ZclFrameType;
import com.zsmartsystems.zigbee.zcl.ZclHeader;
import com.zsmartsystems.zigbee.zcl.ZclStatus;
import com.zsmartsystems.zigbee.zcl.clusters.general.DefaultResponse;
import com.zsmartsystems.zigbee.zcl.protocol.ZclCommandDirection;
import com.zsmartsystems.zigbee.zdo.ZdoCommand;
import com.zsmartsystems.zigbee.zdo.ZdoCommandType;
import com.zsmartsystems.zigbee.zdo.command.ManagementLeaveRequest;
import com.zsmartsystems.zigbee.zdo.command.ManagementPermitJoiningRequest;
import com.zsmartsystems.zigbee.zdo.command.MatchDescriptorRequest;
import com.zsmartsystems.zigbee.zdo.command.NetworkAddressRequest;

/**
 * ZigBeeNetworkManager implements functions for managing the ZigBee interfaces. The network manager is the central
 * class of the framework. It provides the interface with the dongles to send and receive data, and application
 * interfaces to provide listeners for system events (eg network status with the {@link ZigBeeNetworkStateListener} or
 * changes to nodes with the {@link ZigBeeNetworkNodeListener} or to receive incoming commands with the
 * {@link ZigBeeCommandListener}).
 * <p>
 * The ZigBeeNetworkManager maintains a list of all {@link ZigBeeNode}s that are known on the network. Depending on the
 * system configuration, different discovery methods may be utilised to maintain this list. A Coordinator may actively
 * look for all nodes on the network while a Router implementation may only need to know about specific nodes that it is
 * communicating with.
 * <p>
 * The ZigBeeNetworkManager also maintains a list of {@link ZigBeeNetworkExtension}s which allow the functionality of
 * the network to be extended. Extensions may provide different levels of functionality - an extension may be as simple
 * as configuring the framework to work with a specific feature, or could provide a detailed application.
 * <p>
 * <h2>Lifecycle</h2>
 * The ZigBeeNetworkManager lifecycle is as follows -:
 * <ul>
 * <li>Instantiate a {@link ZigBeeTransportTransmit} class. The network state will be initialised to
 * {@link ZigBeeNetworkState#UNINITIALISED}.
 * <li>Instantiate a {@link ZigBeeNetworkManager} class passing the previously created {@link ZigBeeTransportTransmit}
 * class.
 * <li>Set the {@link ZigBeeSerializer} and {@link ZigBeeDeserializer} using the {@link #} method.
 * <li>Optionally call the {@link #setNetworkDataStore(ZigBeeNetworkDataStore)} method to set the
 * {@link ZigBeeNetworkDataStore} that will be called to serialise network data to a persistent store.
 * <li>Call the {@link #initialize()} method to perform the initial initialization of the ZigBee network. The network
 * state will be set to {@link ZigBeeNetworkState#INITIALISING}.
 * <li>Set the network configuration (see below).
 * <li>Call the {@link #startup(boolean)} method to start using the configured ZigBee network. Configuration methods may
 * not be used following this call. The network state will be updated to {@link ZigBeeNetworkState#ONLINE} on success,
 * or {@link ZigBeeNetworkState#OFFLINE} on failure.
 * <li>Call the {@link #reinitialize()} method to reinitialise the network. This will take the transport
 * {@link ZigBeeTransportState#OFFLINE} and place the network state in {@link ZigBeeNetworkState#INITIALISING}.
 * <li>Call the {@link shutdown()} method to close the network and free all resources. The network will be placed in
 * {@link ZigBeeNetworkState#SHUTDOWN} and may not be restarted.
 * </ul>
 * <p>
 * See the {@link ZigBeeNetworkState} transition diagram below -:
 * <p>
 * <img src="./doc-files/ZigBeeNetworkStateTransitions.png" width="50%">
 * <p>
 * Following a call to {@link #initialize} configuration calls can be made to configure the transport layer. This
 * includes -:
 * <ul>
 * <li>{@link #getZigBeeChannel}
 * <li>{@link #setZigBeeChannel(ZigBeeChannel)}
 * <li>{@link #getZigBeePanId}
 * <li>{@link #setZigBeePanId(int)}
 * <li>{@link #getZigBeeExtendedPanId}
 * <li>{@link #setZigBeeExtendedPanId(ExtendedPanId)}
 * <li>{@link #getZigBeeNetworkKey()}
 * <li>{@link #setZigBeeNetworkKey(ZigBeeKey)}
 * <li>{@link #getZigBeeLinkKey()}
 * <li>{@link #setZigBeeLinkKey(ZigBeeKey)}
 * </ul>
 * <p>
 * Once all transport initialization is complete, {@link #startup()} must be called to bring the network
 * {@link ZigBeeNetworkState#ONLINE}.
 * <p>
 * To shutdown the network and free all resources, call {@link #shutdown()}. The network may not be restarted and a new
 * {@link ZigBeeNetworkManager} must be instantiated. The network state will be {@link ZigBeeNetworkState#SHUTDOWN}.
 *
 * @author Chris Jackson
 */
public class ZigBeeNetworkManager implements ZigBeeTransportReceive {
    private final Logger txRxLogger = LoggerFactory.getLogger("TxRxLogger");

    private String networkManagerId;

    public String getNetworkManagerId() {
        return networkManagerId;
    }

    public void setNetworkManagerId(String networkManagerId) {
        this.networkManagerId = networkManagerId;
    }

    /**
     * The local endpoint ID used for all ZCL commands
     */
    private static final int DEFAULT_LOCAL_ENDPOINT_ID = 1;

    /**
     * The broadcast endpoint
     */
    private static final int BROADCAST_ENDPOINT_ID = 255;

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ZigBeeNetworkManager.class);

    /**
     * The nodes in the ZigBee network - maps {@link IeeeAddress} to {@link ZigBeeNode}
     */
    private final Map<IeeeAddress, ZigBeeNode> networkNodes = new ConcurrentHashMap<>();

    private ZigBeeNetworkGroupManager groupManager;

    /**
     * The node listeners of the ZigBee network. Registered listeners will be
     * notified of additions, deletions and changes to {@link ZigBeeNode}s.
     */
    private List<ZigBeeNetworkNodeListener> nodeListeners = Collections.unmodifiableList(new ArrayList<>());

    /**
     * The announce listeners are notified whenever a new device is discovered.
     * This can be called from the transport layer, or internally by methods watching the network state.
     */
    private Collection<ZigBeeAnnounceListener> announceListeners = Collections.unmodifiableCollection(new HashSet<>());

    private Collection<ZigBeeNetworkPermitJoinListener> permitJoinListeners = Collections.unmodifiableCollection(new HashSet<>());
    
    /**
     * {@link AtomicInteger} used to generate APS header counters
     */
    private final AtomicInteger apsCounter = new AtomicInteger();

    /**
     * The network database - used to save the state of the network and all its nodes
     */
    private final ZigBeeNetworkDatabaseManager databaseManager;

    /**
     * An executor service for running notifications in separate threads.
     */
    private NotificationService notificationService = new NotificationService();

    /**
     * Executor service to execute update threads for discovery or mesh updates etc.
     * We use a {@link ZigBeeExecutors.newScheduledThreadPool} to provide a fixed number of threads as otherwise this
     * could result in a large number of simultaneous threads in large networks.
     */
    private final ScheduledExecutorService executorService = ZigBeeExecutors.newScheduledThreadPool(6,
            "NetworkManager");

    /**
     * The {@link ZigBeeTransportTransmit} implementation. This provides the interface
     * for sending data to the network which is an implementation of a ZigBee
     * interface (eg a Dongle).
     */
    private final ZigBeeTransportTransmit transport;

    /**
     * The {@link ZigBeeTransactionManager} responsible for queueing and sending commands, and correlating transactions.
     */
    private final ZigBeeTransactionManager transactionManager;

    /**
     * The local endpoint ID used for all ZCL commands
     */
    private int localEndpointId = DEFAULT_LOCAL_ENDPOINT_ID;

    /**
     * The {@link ApsDataEntity} provides APS layer services such as duplicate removal and fragmentation control
     */
    private final ApsDataEntity apsDataEntity;

    /**
     * The {@link ZigBeeCommandNotifier}. This is used for sending notifications asynchronously to listeners.
     */
    private final ZigBeeCommandNotifier commandNotifier = new ZigBeeCommandNotifier(this);

    /**
     * The listeners of the ZigBee network state.
     */
    private List<ZigBeeNetworkStateListener> stateListeners = Collections.unmodifiableList(new ArrayList<>());

    /**
     * The serializer class used to serialize commands to data packets
     */
    private Class<ZigBeeSerializer> serializerClass;

    /**
     * The deserializer class used to deserialize commands from data packets
     */
    private Class<ZigBeeDeserializer> deserializerClass;

    /**
     * List of {@link ZigBeeNetworkExtension}s that are available to this network. Extensions are added
     * with the {@link #addApplication(ZigBeeNetworkExtension extension)} method.
     */
    private final List<ZigBeeNetworkExtension> extensions = new CopyOnWriteArrayList<>();

    /**
     * A ClusterMatcher used to respond to the {@link MatchDescriptorRequest} command.
     */
    private ClusterMatcher clusterMatcher;

    /**
     * The current {@link ZigBeeNetworkState}
     */
    private ZigBeeNetworkState networkState = ZigBeeNetworkState.UNINITIALISED;

    /**
     * Map of allowable transport state transitions
     */
    private final Map<ZigBeeTransportState, Set<ZigBeeTransportState>> validTransportStateTransitions;

    /**
     * Current state of the transport layer
     */
    private ZigBeeTransportState transportState = ZigBeeTransportState.UNINITIALISED;

    /**
     * Our local {@link IeeeAddress}
     */
    private IeeeAddress localIeeeAddress;

    /**
     * Our local network address
     */
    private int localNwkAddress = 0;

    /**
     * The default profile used for sending frames when no other profile can be determined
     */
    private int defaultProfileId = ZigBeeProfileType.ZIGBEE_HOME_AUTOMATION.getKey();

    /**
     * Constructor which configures serial port and ZigBee network.
     *
     * @param transport the dongle providing the {@link ZigBeeTransportTransmit}
     */
    public ZigBeeNetworkManager(final ZigBeeTransportTransmit transport) {
        databaseManager = new ZigBeeNetworkDatabaseManager(this);
        groupManager = new ZigBeeNetworkGroupManager(this);

        Map<ZigBeeTransportState, Set<ZigBeeTransportState>> transitions = new ConcurrentHashMap<>();
        transitions.put(ZigBeeTransportState.UNINITIALISED,
                new HashSet<>(Arrays.asList(ZigBeeTransportState.INITIALISING, ZigBeeTransportState.OFFLINE)));
        transitions.put(ZigBeeTransportState.INITIALISING, new HashSet<>(Arrays.asList(ZigBeeTransportState.ONLINE)));
        transitions.put(ZigBeeTransportState.ONLINE, new HashSet<>(Arrays.asList(ZigBeeTransportState.OFFLINE)));
        transitions.put(ZigBeeTransportState.OFFLINE, new HashSet<>(Arrays.asList(ZigBeeTransportState.ONLINE)));

        validTransportStateTransitions = Collections.unmodifiableMap(new HashMap<>(transitions));

        this.transport = transport;

        transport.setZigBeeTransportReceive(this);

        apsDataEntity = new ApsDataEntity(transport);
        transactionManager = new ZigBeeTransactionManager(this);
    }

    /**
     * Set a state {@link ZigBeeNetworkDataStore}. This will allow saving and restoring the network.
     *
     * @param dataStore the {@link ZigBeeNetworkDataStore}
     */
    public void setNetworkDataStore(ZigBeeNetworkDataStore dataStore) {
        synchronized (this) {
            databaseManager.setDataStore(dataStore);
        }
    }

    /**
     * Serializes a node to the {@link ZigBeeNetworkDataStore}.
     * <p>
     * Note that the data will not be serialized instantly, but will be handled by the
     * {@link ZigBeeNetworkDatabaseManager} as per a standard node update.
     *
     * @param nodeAddress the {@link IeeeAddress} of the node to serialize
     */
    public void serializeNetworkDataStore(IeeeAddress nodeAddress) {
        ZigBeeNode node = getNode(nodeAddress);
        if (node == null) {
            logger.debug("[{}]: {}: ZigBeeNetworkManager serializeNetworkDataStore: node not found", networkManagerId, nodeAddress);
            return;
        }

        synchronized (this) {
            databaseManager.nodeUpdated(node);
        }
    }

    /**
     * Writes a key value pair to the data store
     *
     * @param key
     * @param value
     */
    public void writeNetworkDataStore(String key, Object value) {
        databaseManager.writeKey(key, value);
    }

    /**
     * Reads a key value pair from the data store
     *
     * @param key
     * @return
     */
    public Object readNetworkDataStore(String key) {
        return databaseManager.readKey(key);
    }

    /**
     * Set the serializer class to be used to convert commands and fields into data to be sent to the dongle.
     * The system instantiates a new serializer for each command.
     *
     * @param serializer the {@link ZigBeeSerializer} class
     * @param deserializer the {@link ZigBeeDeerializer} class
     */
    @SuppressWarnings("unchecked")
    public void setSerializer(Class<?> serializer, Class<?> deserializer) {
        this.serializerClass = (Class<ZigBeeSerializer>) serializer;
        this.deserializerClass = (Class<ZigBeeDeserializer>) deserializer;
    }

    /**
     * Sets the local endpoint ID
     *
     * @param localEndpointId
     */
    public void setLocalEndpointId(int localEndpointId) {
        this.localEndpointId = localEndpointId;
        transport.setDefaultLocalEndpointId(localEndpointId);
    }

    /**
     * Initializes ZigBee manager components and initializes the transport layer. This call may only be called once in
     * the life of the network.
     * <p>
     * If a network state was previously serialized, it will be deserialized here if the serializer is set with the
     * {@link #setNetworkStateSerializer} method.
     * <p>
     * Following a call to {@link #initialize} configuration calls can be made to configure the transport layer. This
     * includes -:
     * <ul>
     * <li>{@link #getZigBeeChannel}
     * <li>{@link #setZigBeeChannel}
     * <li>{@link #getZigBeePanId}
     * <li>{@link #setZigBeePanId}
     * <li>{@link #getZigBeeExtendedPanId}
     * <li>{@link #setZigBeeExtendedPanId}
     * </ul>
     * <p>
     * Once all transport initialization is complete, {@link #startup} must be called.
     *
     * @return {@link ZigBeeStatus}
     */
    public ZigBeeStatus initialize() {
        logger.debug("[{}]: ZigBeeNetworkManager initialize: networkState={}", networkManagerId, networkState);
        synchronized (this) {
            if (networkState != ZigBeeNetworkState.UNINITIALISED) {
                return ZigBeeStatus.INVALID_STATE;
            }
            setNetworkState(ZigBeeNetworkState.INITIALISING);
        }

        databaseManager.startup();

        ZigBeeStatus transportResponse = transport.initialize();
        if (transportResponse != ZigBeeStatus.SUCCESS) {
            setNetworkState(ZigBeeNetworkState.OFFLINE);
            return transportResponse;
        }

        localIeeeAddress = transport.getIeeeAddress();

        return ZigBeeStatus.SUCCESS;
    }

    private void addLocalNode() {
        Integer nwkAddress = transport.getNwkAddress();
        IeeeAddress ieeeAddress = transport.getIeeeAddress();
        if (ieeeAddress == null) {
            logger.error("[{}]: Adding local node to network FAILED as IEEE address is unknown", networkManagerId);
            return;
        }

        if (nwkAddress == null) {
            if (networkState == ZigBeeNetworkState.ONLINE) {
                logger.error("[{}]: {}: Adding local node to network: NWK unknown", networkManagerId, ieeeAddress);
            }
            nwkAddress = 0xFFFE;
        }

        logger.debug("[{}]: {}: Adding local node to network, NWK={}", networkManagerId, ieeeAddress, String.format("%04X", nwkAddress));

        if (networkNodes.containsKey(ieeeAddress)) {
            return;
        }

        ZigBeeNode newNode = new ZigBeeNode(this, ieeeAddress, nwkAddress);
        networkNodes.put(newNode.getIeeeAddress(), newNode);
    }

    /**
     * Gets the {@link ZigBeeTransportTransmit} used by the network
     *
     * @return the {@link ZigBeeTransportTransmit} used by the network
     */
    public ZigBeeTransportTransmit getZigBeeTransport() {
        return transport;
    }

    /**
     * Gets the current ZigBee RF channel.
     *
     * @return the current {@link ZigBeeChannel} or {@link ZigBeeChannel.UNKNOWN} on error
     */
    public ZigBeeChannel getZigBeeChannel() {
        return transport.getZigBeeChannel();
    }

    /**
     * Sets the ZigBee RF channel. The allowable channel range is 11 to 26 for 2.4GHz, however the transport
     * implementation may allow any value it supports.
     * <p>
     * Note that this method may only be called following the {@link #initialize} call, and before the {@link #startup}
     * call.
     *
     * @param channel {@link ZigBeeChannel} defining the channel to use
     * @return {@link ZigBeeStatus} with the status of function
     */
    public ZigBeeStatus setZigBeeChannel(ZigBeeChannel channel) {
        return transport.setZigBeeChannel(channel);
    }

    /**
     * Gets the ZigBee PAN ID currently in use by the transport
     *
     * @return the PAN ID
     */
    public int getZigBeePanId() {
        return (transport.getZigBeePanId() & 0xFFFF);
    }

    /**
     * Sets the ZigBee PAN ID to the specified value. The range of the PAN ID is 0 to 0x3FFF.
     * Additionally a value of 0xFFFF is allowed to indicate the user doesn't care and a random value
     * can be set by the transport.
     * <p>
     * Note that this method may only be called following the {@link #initialize} call, and before the {@link #startup}
     * call.
     *
     * @param panId the new PAN ID
     * @return {@link ZigBeeStatus} with the status of function
     */
    public ZigBeeStatus setZigBeePanId(int panId) {
        if (panId < 0 || panId > 0xfffe) {
            return ZigBeeStatus.INVALID_ARGUMENTS;
        }
        return transport.setZigBeePanId(panId);
    }

    /**
     * Gets the ZigBee Extended PAN ID currently in use by the transport
     *
     * @return the PAN ID
     */
    public ExtendedPanId getZigBeeExtendedPanId() {
        return transport.getZigBeeExtendedPanId();
    }

    /**
     * Sets the ZigBee Extended PAN ID to the specified value
     * <p>
     * Note that this method may only be called following the {@link #initialize} call, and before the {@link #startup}
     * call.
     *
     * @param panId the new {@link ExtendedPanId}
     * @return {@link ZigBeeStatus} with the status of function
     */
    public ZigBeeStatus setZigBeeExtendedPanId(ExtendedPanId panId) {
        return transport.setZigBeeExtendedPanId(panId);
    }

    /**
     * Sets the default profile ID to use. Standard profile IDs are defined in {@link ZigBeeProfileType}.
     * <p>
     * This should be set before calling the {@link #initialize} method.
     *
     * @param defaultProfileId the profile ID
     * @return the {@link ZigBeeStatus} showing {@link ZigBeeStatus#SUCCESS} or reason for failure
     */
    public ZigBeeStatus setDefaultProfileId(int defaultProfileId) {
        if (networkState != ZigBeeNetworkState.INITIALISING) {
            logger.error("[{}]: Cannot set default profile ID to {} when network state is {}", networkManagerId,
                    String.format("%04X", defaultProfileId), networkState);
            return ZigBeeStatus.INVALID_STATE;
        }
        logger.debug("[{}]: Default profile ID set to {} [{}]", networkManagerId, String.format("%04X", defaultProfileId),
                ZigBeeProfileType.getByValue(defaultProfileId));
        this.defaultProfileId = defaultProfileId;
        transport.setDefaultProfileId(defaultProfileId);
        return ZigBeeStatus.SUCCESS;
    }

    /**
     * Sets the default device ID to use. Standard device IDs are defined in {@link ZigBeeDeviceType}.
     * <p>
     * This should be set before calling the {@link #initialize} method.
     *
     * @param defaultDeviceId the device ID
     * @return the {@link ZigBeeStatus} showing {@link ZigBeeStatus#SUCCESS} or reason for failure
     */
    public ZigBeeStatus setDefaultDeviceId(int defaultDeviceId) {
        if (networkState != ZigBeeNetworkState.INITIALISING) {
            logger.error("[{}]: Cannot set default device ID to {} when network state is {}", networkManagerId,
                    String.format("%04X", defaultDeviceId), networkState);
            return ZigBeeStatus.INVALID_STATE;
        }
        logger.debug("[{}]: Default device ID set to {} [{}] for profile {} [{}]", networkManagerId, String.format("%04X", defaultDeviceId),
                ZigBeeDeviceType.getByValue(ZigBeeProfileType.getByValue(defaultProfileId), defaultDeviceId),
                String.format("%04X", defaultProfileId),
                ZigBeeProfileType.getByValue(defaultProfileId));
        transport.setDefaultDeviceId(defaultDeviceId);
        return ZigBeeStatus.SUCCESS;
    }

    /**
     * Set the current network key in use by the system.
     * <p>
     * Note that this method may only be called following the {@link #initialize} call, and before the {@link #startup}
     * call.
     *
     * @param key the new network key as {@link ZigBeeKey}
     * @return {@link ZigBeeStatus} with the status of function
     */
    public ZigBeeStatus setZigBeeNetworkKey(final ZigBeeKey key) {
        return transport.setZigBeeNetworkKey(key);
    }

    /**
     * Gets the current network key used by the system
     *
     * @return the current network {@link ZigBeeKey}
     */
    public ZigBeeKey getZigBeeNetworkKey() {
        return transport.getZigBeeNetworkKey();
    }

    /**
     * Gets the {@link ApdDataEntity} in use by the network manager. This allows configuration of the APSDE.
     *
     * @return the {@link ApdDataEntity} in use by the network manager.
     */
    public ApsDataEntity getApsDataEntity() {
        return apsDataEntity;
    }

    /**
     * Set the current link key in use by the system.
     * <p>
     * Note that this method may only be called following the {@link #initialize} call, and before the {@link #startup}
     * call.
     *
     * @param key the new link key as {@link ZigBeeKey}
     * @return {@link ZigBeeStatus} with the status of function
     */
    public ZigBeeStatus setZigBeeLinkKey(final ZigBeeKey key) {
        return transport.setTcLinkKey(key);
    }

    /**
     * Gets the current Trust Centre link key used by the system
     *
     * @return the current trust centre link {@link ZigBeeKey}
     */
    public ZigBeeKey getZigBeeLinkKey() {
        return transport.getTcLinkKey();
    }

    /**
     * Adds an installation key for the specified address. The {@link ZigBeeKey} should have an address associated with
     * it.
     *
     * @param key the install key as {@link ZigBeeKey} to be used. The key must contain a partner address.
     * @return {@link ZigBeeStatus} with the status of function
     */
    public ZigBeeStatus setZigBeeInstallKey(final ZigBeeKey key) {
        if (!key.hasAddress()) {
            return ZigBeeStatus.INVALID_ARGUMENTS;
        }
        TransportConfig config = new TransportConfig(TransportConfigOption.INSTALL_KEY, key);
        transport.updateTransportConfig(config);

        return config.getResult(TransportConfigOption.INSTALL_KEY);
    }

    /**
     * Starts up ZigBee manager components, and starts the transport layer to bring the network ONLINE.
     * <p>
     * If the network is reinitialized (by setting the reinitialize parameter to true) the network will be cleared. All
     * nodes other than the local node are removed prior to the initialisation of the transport layer.
     *
     * @param reinitialize true if the provider is to reinitialise the network with the parameters configured since the
     *            {@link #initialize} method was called.
     * @return {@link ZigBeeStatus} with the status of function
     */
    public ZigBeeStatus startup(boolean reinitialize) {
        logger.debug("[{}]: ZigBeeNetworkManager startup: reinitialize={}, networkState={}", networkManagerId, reinitialize, networkState);
        if (networkState != ZigBeeNetworkState.INITIALISING) {
            logger.error("[{}]: ZigBeeNetworkManager startup: Can't be called when networkState={}", networkManagerId, networkState);
            return ZigBeeStatus.INVALID_STATE;
        }

        if (reinitialize) {
            // Remove all nodes other than the local node
            for (ZigBeeNode node : networkNodes.values()) {
                if (node.getIeeeAddress().equals(localIeeeAddress)) {
                    continue;
                }
                removeNode(node);
            }
            databaseManager.clear();
        }

        groupManager.initialize();

        // Start the transport layer
        ZigBeeStatus status = transport.startup(reinitialize);
        if (status != ZigBeeStatus.SUCCESS) {
            setNetworkState(ZigBeeNetworkState.OFFLINE);
            return status;
        }
        setNetworkState(ZigBeeNetworkState.ONLINE);
        return ZigBeeStatus.SUCCESS;
    }

    /**
     * Reinitialises the network. This may take the transport layer OFFLINE, and will return the
     * {@link ZigBeeNetworkState} to {@link ZigBeeNetworkState#INITIALISING}. Following this call, the system should be
     * in the same state as if the {@link #initialize()} method had first been called. {@link #startup(boolean)} must be
     * called to place the network back ONLINE.
     * <p>
     * Note that while this method will take the {@link ZigBeeTransportState} to {@link ZigBeeTransportState#OFFLINE}
     * the {@link ZigBeeNetworkState} will be {@link ZigBeeNetworkState#INITIALISING} - the
     * {@link ZigBeeTransportState#OFFLINE} will not be reflected through the the application.
     *
     * @return
     */
    public ZigBeeStatus reinitialize() {
        logger.debug("[{}]: ZigBeeNetworkManager reinitialize: networkState={}", networkManagerId, networkState);

        // Set the state to INITIALISING before reconfiguring the transport layer
        // to ensure we don't pass the OFFLINE state to the application
        setNetworkState(ZigBeeNetworkState.INITIALISING);

        return ZigBeeStatus.SUCCESS;
    }

    /**
     * Shuts down ZigBee manager components.
     */
    public void shutdown() {
        logger.debug("ZigBeeNetworkManager shutdown: networkState={}", networkState);
        setNetworkState(ZigBeeNetworkState.OFFLINE);

        // To avoid deferred writes while we shut down, set the deferred write time to 0.
        databaseManager.setDeferredWriteTime(0);
        databaseManager.setMaxDeferredWriteTime(0);

        setNetworkState(ZigBeeNetworkState.SHUTDOWN);

        if (clusterMatcher != null) {
            clusterMatcher.shutdown();
        }

        groupManager.shutdown();

        // Write out all nodes to the data store.
        for (ZigBeeNode node : networkNodes.values()) {
            databaseManager.nodeUpdated(node);
            node.shutdown();
        }

        for (ZigBeeNetworkExtension extension : extensions) {
            extension.extensionShutdown();
        }

        databaseManager.shutdown();

        transport.shutdown();
        transactionManager.shutdown();

        notificationService.shutdown(5000);
        executorService.shutdownNow();
    }

    /**
     * Schedules a runnable task for execution. This uses a fixed size scheduler to limit thread execution.
     *
     * @param runnableTask the {@link Runnable} to execute
     */
    public void executeTask(Runnable runnableTask) {
        if (networkState != ZigBeeNetworkState.ONLINE) {
            logger.debug("[{}]: ZigBeeNetworkManager executeTask: not executing task while {}", networkManagerId, networkState);
            return;
        }
        executorService.execute(runnableTask);
    }

    /**
     * Schedules a runnable task for execution. This uses a fixed size scheduler to limit thread execution.
     *
     * @param runnableTask the {@link Runnable} to execute
     * @param delay the delay in milliseconds before the task will be executed
     * @return the {@link ScheduledFuture} for the scheduled task
     */
    public ScheduledFuture<?> scheduleTask(Runnable runnableTask, long delay) {
        if (networkState != ZigBeeNetworkState.ONLINE) {
            logger.debug("[{}]: ZigBeeNetworkManager scheduleTask: not scheduling task while {}", networkManagerId, networkState);
            return null;
        }
        return executorService.schedule(runnableTask, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops the current execution of a task and schedules a runnable task for execution again.
     * This uses a fixed size scheduler to limit thread execution.
     *
     * @param futureTask the {@link ScheduledFuture} for the current scheduled task
     * @param runnableTask the {@link Runnable} to execute
     * @param delay the delay in milliseconds before the task will be executed
     * @return the {@link ScheduledFuture} for the scheduled task
     */
    public ScheduledFuture<?> rescheduleTask(ScheduledFuture<?> futureTask, Runnable runnableTask, long delay) {
        futureTask.cancel(false);
        if (networkState != ZigBeeNetworkState.ONLINE) {
            logger.debug("[{}]: ZigBeeNetworkManager rescheduleTask: not scheduling task while {}", networkManagerId, networkState);
            return null;
        }

        return executorService.schedule(runnableTask, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * Schedules a runnable task for periodic execution. This uses a fixed size scheduler to limit thread execution
     * resources.
     * If period is set to 0, then the task will be scheduled for a single execution.
     *
     * @param runnableTask the {@link Runnable} to execute
     * @param initialDelay the delay in milliseconds before the task will be executed
     * @param period the period in milliseconds between each subsequent execution. If set to 0, the task will only be
     *            scheduled once.
     * @return the {@link ScheduledFuture} for the scheduled task
     */
    public ScheduledFuture<?> scheduleTask(Runnable runnableTask, long initialDelay, long period) {
        if (period == 0) {
            return executorService.schedule(runnableTask, initialDelay, TimeUnit.MILLISECONDS);
        } else {
            return executorService.scheduleAtFixedRate(runnableTask, initialDelay, period, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Get the transport layer version string
     *
     * @return {@link String} containing the transport layer version
     */
    public String getTransportVersionString() {
        return transport.getVersionString();
    }

    /**
     * Sends a command directly to the ZigBeeTransportTransmit} interface. This method should only be used by the
     * transaction manager.
     *
     * @param command the {@link ZigBeeCommand} to send
     * @return true if the command was successfully passed to the transport layer
     */
    public boolean sendCommand(ZigBeeCommand command) {
        // Create the application frame
        ZigBeeApsFrame apsFrame = new ZigBeeApsFrame();

        logger.debug("[{}]: TX CMD: {}", networkManagerId, command);
        txRxLogger.debug("[" + networkManagerId + "] TX CMD: {}", command);

        apsFrame.setCluster(command.getClusterId());
        apsFrame.setApsCounter(apsCounter.getAndIncrement() & 0xff);
        apsFrame.setSecurityEnabled(command.getApsSecurity());
        apsFrame.setAckRequest(command.isAckRequest());

        // TODO: Set the source address correctly?
        apsFrame.setSourceAddress(localNwkAddress);

        apsFrame.setRadius(8); // TODO: Make this configurable

        if (command.getDestinationAddress() instanceof ZigBeeEndpointAddress) {
            apsFrame.setAddressMode(ZigBeeNwkAddressMode.DEVICE);
            apsFrame.setDestinationAddress(((ZigBeeEndpointAddress) command.getDestinationAddress()).getAddress());
            apsFrame.setDestinationEndpoint(((ZigBeeEndpointAddress) command.getDestinationAddress()).getEndpoint());

            ZigBeeNode node = getNode(command.getDestinationAddress().getAddress());
            if (node != null) {
                apsFrame.setDestinationIeeeAddress(node.getIeeeAddress());
            }
        } else if (command.getDestinationAddress() instanceof ZigBeeGroupAddress) {
            apsFrame.setAddressMode(ZigBeeNwkAddressMode.GROUP);
            apsFrame.setDestinationAddress(((ZigBeeGroupAddress) command.getDestinationAddress()).getAddress());
            apsFrame.setGroupAddress(((ZigBeeGroupAddress) command.getDestinationAddress()).getAddress());
        } else {
            logger.debug("[{}]: Command cannot be sent due to unknown destination address type {}", networkManagerId, command);
            return false;
        }

        final ZclFieldSerializer fieldSerializer;
        try {
            Constructor<? extends ZigBeeSerializer> constructor;
            constructor = serializerClass.getConstructor();
            ZigBeeSerializer serializer = constructor.newInstance();
            fieldSerializer = new ZclFieldSerializer(serializer);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            logger.debug("[{}]: Error serializing ZigBee frame {}", networkManagerId, e);
            return false;
        }

        if (command instanceof ZdoCommand) {
            // Source endpoint is (currently) set by the dongle since it registers the clusters into an endpoint
            // apsHeader.setSourceEndpoint(sourceEndpoint);

            apsFrame.setProfile(0);
            apsFrame.setSourceEndpoint(0);
            apsFrame.setDestinationEndpoint(0);
            command.serialize(fieldSerializer);

            // Serialise the ZCL header and add the payload
            apsFrame.setPayload(fieldSerializer.getPayload());
        }

        if (command instanceof ZclCommand) {
            // For ZCL commands we pass the NWK and APS headers as classes to the transport layer.
            // The ZCL packet is serialised here.
            ZclCommand zclCommand = (ZclCommand) command;

            apsFrame.setSourceEndpoint(localEndpointId);

            // Set the profile
            apsFrame.setProfile(defaultProfileId);

            // Create the cluster library header
            ZclHeader zclHeader = new ZclHeader();
            zclHeader.setFrameType(zclCommand.isGenericCommand() ? ZclFrameType.ENTIRE_PROFILE_COMMAND
                    : ZclFrameType.CLUSTER_SPECIFIC_COMMAND);
            zclHeader.setCommandId(zclCommand.getCommandId());
            zclHeader.setSequenceNumber(command.getTransactionId());
            zclHeader.setDirection(zclCommand.getCommandDirection());
            zclHeader.setDisableDefaultResponse(zclCommand.isDisableDefaultResponse());

            if (zclCommand.isManufacturerSpecific()) {
                zclHeader.setManufacturerSpecific(true);
                zclHeader.setManufacturerCode(zclCommand.getManufacturerCode());
            }

            command.serialize(fieldSerializer);

            // Serialise the ZCL header and add the payload
            apsFrame.setPayload(zclHeader.serialize(fieldSerializer, fieldSerializer.getPayload()));

            logger.debug("[{}]: TX ZCL: {}", networkManagerId, zclHeader);
            txRxLogger.debug("[" + networkManagerId + "] TX ZCL: {}", zclHeader);
        }
        logger.debug("[{}]: TX APS: {}", networkManagerId, apsFrame);
        txRxLogger.debug("[" + networkManagerId + "] TX APS: {}", apsFrame);

        apsDataEntity.send(command.getTransactionId(), apsFrame);
        return true;
    }

    public void addCommandListener(ZigBeeCommandListener commandListener) {
        commandNotifier.addCommandListener(commandListener);
    }

    public void removeCommandListener(ZigBeeCommandListener commandListener) {
        commandNotifier.removeCommandListener(commandListener);
    }

    @Override
    public void receiveCommand(final ZigBeeApsFrame incomingApsFrame) {
        synchronized (this) {
            if (networkState != ZigBeeNetworkState.ONLINE) {
                logger.debug("[{}]: Dropping APS: state={}, frame={}", networkManagerId, networkState, incomingApsFrame);
                return;
            }
        }

        logger.debug("[{}]: RX APS: {}", networkManagerId, incomingApsFrame);
        txRxLogger.debug("[" + networkManagerId + "] RX APS: {}", incomingApsFrame);

        // Process the APS layer - this performs services such as duplicate removal and defragmentation
        ZigBeeApsFrame apsFrame = apsDataEntity.receive(incomingApsFrame);
        if (apsFrame == null) {
            return;
        }

        if (getNode(apsFrame.getSourceAddress()) == null) {
            logger.debug("[{}]: Incoming message from unknown node {}: Notifying announce listeners", networkManagerId,
                    String.format("%04X", apsFrame.getSourceAddress()));

            // Notify the listeners that we have heard a command that was unknown to us
            for (final ZigBeeAnnounceListener announceListener : announceListeners) {
                notificationService.execute(new Runnable() {
                    @Override
                    public void run() {
                        announceListener.announceUnknownDevice(apsFrame.getSourceAddress());
                    }
                });
            }
        }

        // Set node state to ONLINE
        int sourceAddress = apsFrame.getSourceAddress();
        ZigBeeNode zigBeeNode = getNode(sourceAddress);
        if (zigBeeNode != null) {
            ZigBeeNode updatedNode = new ZigBeeNode(this, zigBeeNode.getIeeeAddress());
            updatedNode.setNodeState(ZigBeeNodeState.ONLINE);
            refreshNode(updatedNode);
        }

        // Create the deserialiser
        Constructor<? extends ZigBeeDeserializer> constructor;
        ZigBeeDeserializer deserializer;
        try {
            constructor = deserializerClass.getConstructor(int[].class);
            deserializer = constructor.newInstance(new Object[] { apsFrame.getPayload() });
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            logger.debug("[{}]: Error creating deserializer", networkManagerId, e);
            return;
        }
        ZclFieldDeserializer fieldDeserializer = new ZclFieldDeserializer(deserializer);

        ZigBeeCommand command = null;
        switch (apsFrame.getProfile()) { // TODO: Use ZigBeeProfileType
            case 0x0000:
                command = receiveZdoCommand(fieldDeserializer, apsFrame);
                break;
            case 0x0104:
            case 0x0109:
            case 0xC05E:
                command = receiveZclCommand(fieldDeserializer, apsFrame);
                break;
            default:
                logger.debug("[{}]: Received message with unknown profile {}", networkManagerId, String.format("%04X", apsFrame.getProfile()));
                break;
        }

        if (command == null) {
            logger.debug("[{}]: Incoming message from node {} did not translate to command", networkManagerId,
                    String.format("%04X", apsFrame.getSourceAddress()));

            return;
        }

        // Create an address from the sourceAddress and endpoint
        command.setSourceAddress(new ZigBeeEndpointAddress(apsFrame.getSourceAddress(), apsFrame.getSourceEndpoint()));
        command.setDestinationAddress(
                new ZigBeeEndpointAddress(apsFrame.getDestinationAddress(), apsFrame.getDestinationEndpoint()));
        command.setApsSecurity(apsFrame.getSecurityEnabled());

        logger.debug("[{}]: RX CMD: {}", networkManagerId, command);
        txRxLogger.debug("[" + networkManagerId + "] RX CMD: {}", command);

        // Pass the command to the transaction manager for processing
        // If the transaction manager wants to drop this command, it returns null
        final ZigBeeCommand finalCommand = transactionManager.receive(command);
        if (finalCommand == null) {
            return;
        }

        // Ignore the DefaultResponse
        if (finalCommand instanceof DefaultResponse) {
            return;
        }

        // Directly distribute commands to nodes
        ZigBeeNode node = getNode(command.getSourceAddress().getAddress());
        if (node != null) {
            notificationService.execute(new Runnable() {
                @Override
                public void run() {
                    node.commandReceived(finalCommand, apsFrame.getReceivedRssi(), apsFrame.getReceivedLqi());
                }
            });
        }

        if (node != null && command instanceof DeviceAnnounce) {
            final DeviceAnnounce deviceAnnounce = (DeviceAnnounce) command;
            node.setMacCapabilities(deviceAnnounce.getCapability());
            logger.debug("[{}]: {}: Save DeviceAnnounce reported MAC capabilities", networkManagerId, node.getIeeeAddress());
            updateNode(node);
        }

        // Notify the listeners
        commandNotifier.notifyCommandListeners(command);
    }

    private ZigBeeCommand receiveZdoCommand(final ZclFieldDeserializer fieldDeserializer,
            final ZigBeeApsFrame apsFrame) {
        if (apsFrame.getSourceEndpoint() != 0 || apsFrame.getDestinationEndpoint() != 0) {
            logger.debug(
                    "[{}]: Error instantiating ZDO command: Source or destination endpoints are not 0 (source={}, destination={})",
                    networkManagerId, apsFrame.getSourceEndpoint(), apsFrame.getDestinationEndpoint());
            return null;
        }
        ZdoCommandType commandType = ZdoCommandType.getValueById(apsFrame.getCluster());
        if (commandType == null) {
            logger.debug("[{}]: Error instantiating ZDO command: Unknown cluster {}", networkManagerId,
                    String.format("%04X", apsFrame.getCluster()));
            return null;
        }

        ZigBeeCommand command;
        try {
            Class<? extends ZdoCommand> commandClass = commandType.getCommandClass();
            Constructor<? extends ZdoCommand> constructor;
            constructor = commandClass.getConstructor();
            command = constructor.newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            logger.debug("[{}]: Error instantiating ZDO command", networkManagerId, e);
            return null;
        }

        command.deserialize(fieldDeserializer);

        return command;
    }

    private ZigBeeCommand receiveZclCommand(final ZclFieldDeserializer fieldDeserializer,
            final ZigBeeApsFrame apsFrame) {
        if (apsFrame.getDestinationEndpoint() != localEndpointId
                && apsFrame.getDestinationEndpoint() != BROADCAST_ENDPOINT_ID) {
            logger.debug("[{}]: Unknown local endpoint for APS frame {}", networkManagerId, apsFrame);
            return null;
        }
        // Process the ZCL header
        ZclHeader zclHeader = new ZclHeader(fieldDeserializer);
        logger.debug("[{}]: RX ZCL: {}", networkManagerId, zclHeader);
        txRxLogger.debug("[{}] RX ZCL: {}", networkManagerId, zclHeader);

        ZigBeeNode node = getNode(apsFrame.getSourceAddress());
        if (node == null) {
            logger.debug("[{}]: Unknown remote node {}", networkManagerId, String.format("%04X", apsFrame.getSourceAddress()));
            return null;
        }

        ZigBeeEndpoint endpoint = node.getEndpoint(apsFrame.getSourceEndpoint());
        if (endpoint == null) {
            logger.debug("[{}]: {}: Endpoint {}. Unknown remote endpoint", networkManagerId, node.getIeeeAddress(),
                    apsFrame.getSourceEndpoint());
            return null;
        }

        ZclCommand command;
        if (zclHeader.getDirection() == ZclCommandDirection.SERVER_TO_CLIENT) {
            if (!isClientClusterSupported(apsFrame.getCluster())) {
                logger.debug("[{}]: Unsupported local client cluster {}", networkManagerId, String.format("%04X", apsFrame.getCluster()));
            }
            ZclCluster cluster = endpoint.getInputCluster(apsFrame.getCluster());
            if (cluster == null) {
                logger.debug("[{}]: {}: Endpoint {}. Unknown input cluster {}", networkManagerId, node.getIeeeAddress(),
                        endpoint.getEndpointId(), String.format("%04X", apsFrame.getCluster()));

                ZigBeeNode newNode = new ZigBeeNode(this, node.getIeeeAddress());
                ZigBeeEndpoint newEndpoint = new ZigBeeEndpoint(node, endpoint.getEndpointId());
                newEndpoint.setInputClusterIds(Collections.singletonList(apsFrame.getCluster()));
                newEndpoint.updateEndpoint(newEndpoint);
                newNode.addEndpoint(newEndpoint);
                try {
                    boolean state = refreshNode(newNode).get();
                    logger.debug("[{}]: {}: Endpoint {}. Unknown input cluster {} updated node complete state {}",
                            networkManagerId, node.getIeeeAddress(), endpoint.getEndpointId(),
                            String.format("%04X", apsFrame.getCluster()), state);
                } catch (InterruptedException | ExecutionException e) {
                    logger.debug("[{}]: {}: Endpoint {}. Unknown input cluster {} updated node failed with {}",
                            networkManagerId, node.getIeeeAddress(), endpoint.getEndpointId(),
                            String.format("%04X", apsFrame.getCluster()), e.getClass().getSimpleName());
                }

                cluster = endpoint.getInputCluster(apsFrame.getCluster());
            }
            command = cluster.getResponseFromId(zclHeader.getFrameType(), zclHeader.getCommandId());
        } else {
            if (!isServerClusterSupported(apsFrame.getCluster())) {
                logger.debug("[{}]: Unsupported local server cluster {}", networkManagerId, String.format("%04X", apsFrame.getCluster()));
                createDefaultResponse(apsFrame, zclHeader, ZclStatus.FAILURE);
                return null;
            }
            ZclCluster cluster = endpoint.getOutputCluster(apsFrame.getCluster());
            if (cluster == null) {
                logger.debug("[{}]: {}: Endpoint {}. Unknown output cluster {}", networkManagerId, node.getIeeeAddress(),
                        endpoint.getEndpointId(), String.format("%04X", apsFrame.getCluster()));

                ZigBeeNode newNode = new ZigBeeNode(this, node.getIeeeAddress());
                ZigBeeEndpoint newEndpoint = new ZigBeeEndpoint(node, endpoint.getEndpointId());
                newEndpoint.setOutputClusterIds(Collections.singletonList(apsFrame.getCluster()));
                newEndpoint.updateEndpoint(newEndpoint);
                newNode.addEndpoint(newEndpoint);
                try {
                    boolean state = refreshNode(newNode).get();
                    logger.debug("[{}]: {}: Endpoint {}. Unknown output cluster {} updated node complete state {}",
                            networkManagerId, node.getIeeeAddress(), endpoint.getEndpointId(),
                            String.format("%04X", apsFrame.getCluster()), state);
                } catch (InterruptedException | ExecutionException e) {
                    logger.debug("[{}]: {}: Endpoint {}. Unknown output cluster {} updated node failed with {}",
                            networkManagerId, node.getIeeeAddress(), endpoint.getEndpointId(),
                            String.format("%04X", apsFrame.getCluster()), e.getClass().getSimpleName());
                }

                cluster = endpoint.getOutputCluster(apsFrame.getCluster());
            }
            command = cluster.getCommandFromId(zclHeader.getFrameType(), zclHeader.getCommandId());
        }
        if (command == null) {
            logger.debug("[{}]: {}: Unknown command {}", networkManagerId, node.getIeeeAddress(), zclHeader.getCommandId());
            createDefaultResponse(apsFrame, zclHeader, ZclStatus.FAILURE);
            return null;
        }

        command.setCommandDirection(zclHeader.getDirection());
        command.deserialize(fieldDeserializer);
        command.setClusterId(apsFrame.getCluster());
        command.setTransactionId(zclHeader.getSequenceNumber());
        command.setDisableDefaultResponse(zclHeader.isDisableDefaultResponse());

        return command;
    }

    /**
     * Generates a {@link DefaultResponse} with the requested {@link ZclStatus} code.
     * If the command does not require a response, this method will return null.
     *
     * @param apsFrame the received {@link ZigBeeApsFrame} of the frame to which the response is being generated
     * @param zclHeader the received {@link ZclHeader} of the frame to which the response is being generated
     * @param status the {@link ZclStatus} to return in the response
     */
    public void createDefaultResponse(ZigBeeApsFrame apsFrame, ZclHeader zclHeader, ZclStatus status) {
        if (zclHeader.isDisableDefaultResponse()) {
            return;
        }
        DefaultResponse defaultResponse = new DefaultResponse(zclHeader.getCommandId(), status);
        defaultResponse.setTransactionId(zclHeader.getSequenceNumber());
        defaultResponse.setDestinationAddress(
                new ZigBeeEndpointAddress(apsFrame.getSourceAddress(), apsFrame.getSourceEndpoint()));
        defaultResponse.setClusterId(apsFrame.getCluster());
        defaultResponse.setDisableDefaultResponse(true);
        defaultResponse.setCommandDirection(zclHeader.getDirection().getResponseDirection());
        defaultResponse.setApsSecurity(apsFrame.getSecurityEnabled());

        sendTransaction(defaultResponse);
    }

    /**
     * Add a {@link ZigBeeAnnounceListener} that will be notified whenever a new device is detected
     * on the network.
     *
     * @param announceListener the new {@link ZigBeeAnnounceListener} to add
     */
    public void addAnnounceListener(ZigBeeAnnounceListener announceListener) {
        if (announceListeners.contains(announceListener)) {
            return;
        }
        final Collection<ZigBeeAnnounceListener> modifiedStateListeners = new HashSet<>(announceListeners);
        modifiedStateListeners.add(announceListener);
        announceListeners = Collections.unmodifiableCollection(modifiedStateListeners);
    }

    /**
     * Remove a {@link ZigBeeAnnounceListener}
     *
     * @param announceListener the new {@link ZigBeeAnnounceListener} to remove
     */
    public void removeAnnounceListener(ZigBeeAnnounceListener announceListener) {
        final Collection<ZigBeeAnnounceListener> modifiedStateListeners = new HashSet<>(announceListeners);
        modifiedStateListeners.remove(announceListener);
        announceListeners = Collections.unmodifiableCollection(modifiedStateListeners);
    }

    @Override
    public void nodeStatusUpdate(final ZigBeeNodeStatus deviceStatus, final Integer networkAddress,
            final IeeeAddress ieeeAddress, final Integer parentNetworkAddress) {
        logger.debug("[{}]: {}: nodeStatusUpdate - node status is {}, network address is {}.", networkManagerId, ieeeAddress, deviceStatus,
                String.format("%04X", networkAddress));

        // This method should only be called when the transport layer has authoritative information about
        // a devices status. Therefore, we should update the network manager view of a device as appropriate.
        // A coordinator/router may ask a device to leave the network and rejoin. In this case, we do not want
        // to remove the node - otherwise we would have to perform the rediscovery of services etc.
        // Instead, we mark the node status as OFFLINE, and leave it to the application to remove the node from
        // the network manager.
        switch (deviceStatus) {
            // Device has gone - lets remove it
            case DEVICE_LEFT:
            case UNTRUSTED_DEVICE_LEFT:
                // Find the node
                ZigBeeNode node = getNode(ieeeAddress);
                if (node == null) {
                    node = getNode(networkAddress);
                }
                if (node == null) {
                    logger.debug("[{}]: {}: Node has left, but wasn't found in the network.", networkManagerId, networkAddress);
                } else {
                    // Mark the node as OFFLINE
                    ZigBeeNode updatedNode = new ZigBeeNode(this, node.getIeeeAddress());
                    updatedNode.setNodeState(ZigBeeNodeState.OFFLINE);
                    updateNode(updatedNode);
                }
                break;

            // Leave the join/rejoin notifications for the discovery handler
            case UNSECURED_JOIN:
            case UNSECURED_JOIN_OR_REJOIN:
                // We only care about devices that have joined or rejoined
                logger.debug("[{}]: {}: Device status updated. NWK={}", networkManagerId, ieeeAddress, String.format("%04X", networkAddress));
                ZigBeeNode updatedNode = new ZigBeeNode(this, ieeeAddress, networkAddress);
                updatedNode.setNodeState(ZigBeeNodeState.ONLINE);
                updateNode(updatedNode);
                break;
            case SECURED_REJOIN:
            case UNSECURED_REJOIN:
                break;
            default:
                break;
        }

        // Notify the announce listeners
        for (final ZigBeeAnnounceListener announceListener : announceListeners) {
            notificationService.execute(new Runnable() {
                @Override
                public void run() {
                    announceListener.deviceStatusUpdate(deviceStatus, networkAddress, ieeeAddress, parentNetworkAddress);
                }
            });
        }
    }

    /**
     * Adds a {@link ZigBeeNetworkStateListener} to receive notifications when the network state changes.
     *
     * @param stateListener the {@link ZigBeeNetworkStateListener} to receive the notifications
     */
    public void addNetworkStateListener(ZigBeeNetworkStateListener stateListener) {
        final List<ZigBeeNetworkStateListener> modifiedStateListeners = new ArrayList<>(stateListeners);
        modifiedStateListeners.add(stateListener);
        stateListeners = Collections.unmodifiableList(modifiedStateListeners);
    }

    /**
     * Removes a {@link ZigBeeNetworkStateListener}.
     *
     * @param stateListener the {@link ZigBeeNetworkStateListener} to stop receiving the notifications
     */
    public void removeNetworkStateListener(ZigBeeNetworkStateListener stateListener) {
        final List<ZigBeeNetworkStateListener> modifiedStateListeners = new ArrayList<>(stateListeners);
        modifiedStateListeners.remove(stateListener);
        stateListeners = Collections.unmodifiableList(modifiedStateListeners);
    }

    @Override
    public synchronized void setTransportState(final ZigBeeTransportState state) {
        // Filter out unwanted transport state changes
        synchronized (validTransportStateTransitions) {
            if (!validTransportStateTransitions.get(transportState).contains(state)) {
                logger.debug("[{}]: Ignoring invalid transport state transition from {} to {}", networkManagerId, transportState, state);
                return;
            }
            transportState = state;
        }

        // Process the network state given the updated transport layer state
        if (networkState != ZigBeeNetworkState.INITIALISING && networkState != ZigBeeNetworkState.SHUTDOWN) {
            logger.debug("[{}]: ZigBeeNetworkManager transport state updated to {}", networkManagerId, state);
            setNetworkState(ZigBeeNetworkState.valueOf(state.toString()));
        }
    }

    private synchronized void setNetworkState(final ZigBeeNetworkState state) {
        // Only notify users of state changes
        if (state.equals(networkState)) {
            return;
        }
        networkState = state;
        logger.debug("[{}]: Network state is updated to {}", networkManagerId, networkState);

        // If the state has changed to online, then we need to add any pending nodes,
        // and ensure that the local node is added.
        // This is done in another thread which will then notify users.
        if (state == ZigBeeNetworkState.ONLINE) {
            notificationService.execute(new Runnable() {
                @Override
                public void run() {
                    setNetworkStateOnline();
                }
            });

            return;
        }

        for (final ZigBeeNetworkStateListener stateListener : stateListeners) {
            notificationService.execute(new Runnable() {
                @Override
                public void run() {
                    stateListener.networkStateUpdated(state);
                }
            });
        }
    }

    private void setNetworkStateOnline() {
        logger.debug("[{}]: Network state ONLINE: Process running. {} Nodes in network.", networkManagerId, networkNodes.size());

        localNwkAddress = transport.getNwkAddress();
        localIeeeAddress = transport.getIeeeAddress();

        // Make sure that we know the local node, and that the network address is correct.
        addLocalNode();

        // Disable JOIN mode if we are the coordinator.
        // This should be disabled by default (at least in ZigBee 3.0) but some older stacks may
        // have join enabled permanently by default.
        if (localNwkAddress == 0) {
            permitJoin(0);
        }

        // Start the extensions
        for (ZigBeeNetworkExtension extension : extensions) {
            extension.extensionStartup();
        }

        // Notify all listeners of the nodes in the network,
        // and provide all known NodeDescriptors to the transport layer
        for (final ZigBeeNode node : networkNodes.values()) {
            logger.debug("[{}]: Network state ONLINE: Notifying node {} [{}]", networkManagerId, node.getIeeeAddress(),
                    String.format("%04X", node.getNetworkAddress()));
            if (node.getNodeDescriptor() != null) {
                notificationService.execute(new Runnable() {
                    @Override
                    public void run() {
                        transport.setNodeDescriptor(node.getIeeeAddress(), node.getNodeDescriptor());
                    }
                });
            }

            for (final ZigBeeNetworkNodeListener listener : nodeListeners) {
                notificationService.execute(new Runnable() {
                    @Override
                    public void run() {
                        listener.nodeAdded(node);
                    }
                });
            }
        }

        // Now that everything is added and started, notify the listeners that the state has updated
        for (final ZigBeeNetworkStateListener stateListener : stateListeners) {
            notificationService.execute(new Runnable() {
                @Override
                public void run() {
                    stateListener.networkStateUpdated(ZigBeeNetworkState.ONLINE);
                }
            });
        }
    }

    /**
     * Enables or disables devices to join the whole network.
     * <p>
     * Devices can only join the network when joining is enabled. It is not advised to leave joining enabled permanently
     * since it allows devices to join the network without the installer knowing.
     *
     * @param duration sets the duration in seconds of the join being enabled. Setting this to 0 disables joining.
     *            As per ZigBee 3, a value of 255 is not permitted and will be ignored.
     * @return {@link ZigBeeStatus} with the status of function
     */
    public ZigBeeStatus permitJoin(final int duration) {
        return permitJoin(new ZigBeeEndpointAddress(ZigBeeBroadcastDestination.BROADCAST_ROUTERS_AND_COORD.getKey()),
                duration);
    }

    /**
     * Enables or disables devices to join the network.
     * <p>
     * Devices can only join the network when joining is enabled. It is not advised to leave joining enabled permanently
     * since it allows devices to join the network without the installer knowing.
     *
     * @param destination the {@link ZigBeeEndpointAddress} to send the join request to
     * @param duration sets the duration in seconds of the join being enabled. Setting this to 0 disables joining.
     *            As per ZigBee 3, a value of 255 is not permitted and will be ignored.
     * @return {@link ZigBeeStatus} with the status of function
     */
    public ZigBeeStatus permitJoin(final ZigBeeEndpointAddress destination, final int duration) {
        if (duration < 0 || duration >= 255) {
            logger.debug("[{}]: Permit join to {} invalid period of {} seconds.", networkManagerId, destination, duration);
            return ZigBeeStatus.INVALID_ARGUMENTS;
        }
        logger.debug("[{}]: Permit join to {} for {} seconds.", networkManagerId, destination, duration);

        ManagementPermitJoiningRequest command = new ManagementPermitJoiningRequest(duration, true);
        command.setDestinationAddress(destination);
        command.setSourceAddress(new ZigBeeEndpointAddress(0));

        sendTransaction(command);
        
        for (final ZigBeeNetworkPermitJoinListener permitJoinListener : permitJoinListeners) {
            notificationService.execute(new Runnable() {
                @Override
                public void run() {
                    permitJoinListener.permitJoinExecuted(networkManagerId);
                }
            });
        }

        // If this is a broadcast, then we send it to our own address as well
        // This seems to be required for some stacks (eg TI ZNP).
        // Note that in recent Ember ZNet stacks a unicast join will return an error INV_REQUESTTYPE!
        if (ZigBeeBroadcastDestination.isBroadcast(destination.getAddress())) {
            command = new ManagementPermitJoiningRequest(duration, true);
            command.setDestinationAddress(new ZigBeeEndpointAddress(0));
            command.setSourceAddress(new ZigBeeEndpointAddress(0));

            sendTransaction(command);
        }

        return ZigBeeStatus.SUCCESS;
    }

    /**
     * Sends a ZDO Leave Request to a device requesting that an end device leave the network.
     * Only in case of a successful response is the corresponding node removed from the node list.
     *
     * @param destinationAddress the network address to send the request to - this is the device parent or the the
     *            device we want to leave.
     * @param leaveAddress the {@link IeeeAddress} of the end device we want to leave the network
     */
    public void leave(final Integer destinationAddress, final IeeeAddress leaveAddress) {
        leave(destinationAddress, leaveAddress, false);
    }

    /**
     * Sends a ZDO Leave Request to a device requesting that an device leave the network. If the node responds
     * successfully, the node is removed from the network, and the {@link ZigBeeNetworkNodeListener#nodeRemoved}
     * notification is called.
     *
     * @param destinationAddress the network address to send the request to - this is the device parent or the the
     *            device we want to leave.
     * @param leaveAddress the {@link IeeeAddress} of the end device we want to leave the network
     * @param forceNodeRemoval If this flag is set, then the corresponding node is removed from the node list
     *            even if there was no success response to the leave command.
     */
    public void leave(final Integer destinationAddress, final IeeeAddress leaveAddress, boolean forceNodeRemoval) {
        final ManagementLeaveRequest command = new ManagementLeaveRequest(leaveAddress, false);

        command.setDestinationAddress(new ZigBeeEndpointAddress(destinationAddress));
        command.setSourceAddress(new ZigBeeEndpointAddress(0));

        // Start a thread to wait for the response
        // When we receive the response, if it's successful, we assume the device left.
        new Thread("NetworkLeave") {
            @Override
            public void run() {
                try {
                    CommandResult response = sendTransaction(command, command).get();
                    ZigBeeNode node = getNode(leaveAddress);

                    if (node != null) {
                        if (response.getStatusCode() == 0) {
                            removeNode(node);
                        } else {
                            logger.debug("[{}]: {}: No successful response received to leave command (status code {})",
                                    networkManagerId, leaveAddress, response.getStatusCode());
                            if (forceNodeRemoval) {
                                logger.debug(
                                        "[{}]: {}: Force-removing node from the node list after unsuccessful leave request",
                                        networkManagerId, leaveAddress);
                                removeNode(node);
                            }
                        }
                    } else {
                        logger.debug("[{}]: {}: No node found after leave command", networkManagerId, leaveAddress);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    logger.debug("[{}]: Error sending leave command.", networkManagerId, e);
                }
            }
        }.start();
    }

    /**
     * Returns the {@link ZigBeeNetworkGroupManager}. It should not normally be needed to directly access the
     * {@link ZigBeeNetworkGroupManager} for most applications as the {@link #addGroup}, {@link #getGroup},
     * {@link #removeGroup}, {@link #getGroups} and {@link #synchronizeGroups} methods can be used to manipulate groups.
     * <p>
     * Access to the {@link ZigBeeNetworkGroupManager} is provided for low level functionality as may be required for
     * debugging etc.
     *
     * @return the {@link ZigBeeNetworkGroupManager} used by the network
     */
    public ZigBeeNetworkGroupManager getGroupManager() {
        return groupManager;
    }

    /**
     * Synchronises the groups across the network. The method to be used for synchronisation is defined in
     * {@link GroupSynchronizationMethod} enumeration.
     *
     * @param syncMethod the {@link GroupSynchronizationMethod} to use when synchronizing the group members
     * @return {@link Future} providing the {@link ZigBeeStatus} of the result
     */
    public Future<ZigBeeStatus> synchronizeGroups(GroupSynchronizationMethod syncMethod) {
        return groupManager.synchronize(syncMethod);
    }

    /**
     * Creates or returns a {@link ZigBeeGroup}. If the group already exists, the existing {@link ZigBeeGroup} is
     * returned, otherwise a new {@link ZigBeeGroup} is created and returned.
     *
     * @param groupId the group ID to add or get
     * @return the {@link ZigBeeGroup}
     */
    public ZigBeeGroup addGroup(final int groupId) {
        return groupManager.add(groupId);
    }

    /**
     * Gets a {@link ZigBeeGroup} given the group ID
     *
     * @param groupId the ID of the group to return
     * @return the {@link ZigBeeGroup} or null if the group ID is not known
     */
    public ZigBeeGroup getGroup(final int groupId) {
        return groupManager.get(groupId);
    }

    /**
     * Returns the collection of {@link ZigBeeGroup} known in the network
     *
     * @return the Collection of {@link ZigBeeGroupAddress}
     */
    public Collection<ZigBeeGroup> getGroups() {
        return groupManager.getAll();
    }

    /**
     * Removes a group given the group ID
     *
     * @param groupId the ID of the group to remove
     * @return the {@link ZigBeeGroup} that was removed, or null if the group was not gound
     */
    public ZigBeeGroup deleteGroup(final int groupId) {
        return groupManager.delete(groupId);
    }

    /**
     * Adds a {@link ZigBeeNetworkNodeListener} that will be notified when node information changes
     *
     * @param networkNodeListener the {@link ZigBeeNetworkNodeListener} to add
     */
    public void addNetworkNodeListener(final ZigBeeNetworkNodeListener networkNodeListener) {
        if (networkNodeListener == null) {
            return;
        }
        final List<ZigBeeNetworkNodeListener> modifiedListeners = new ArrayList<>(nodeListeners);
        modifiedListeners.add(networkNodeListener);
        nodeListeners = Collections.unmodifiableList(modifiedListeners);
    }

    /**
     * Removes a {@link ZigBeeNetworkNodeListener} that will be notified when node information changes
     *
     * @param networkNodeListener the {@link ZigBeeNetworkNodeListener} to remove
     */
    public void removeNetworkNodeListener(final ZigBeeNetworkNodeListener networkNodeListener) {
        final List<ZigBeeNetworkNodeListener> modifiedListeners = new ArrayList<>(nodeListeners);
        modifiedListeners.remove(networkNodeListener);
        nodeListeners = Collections.unmodifiableList(modifiedListeners);
    }

    /**
     * Gets a {@link Set} of {@link ZigBeeNode}s known by the network
     *
     * @return {@link Set} of {@link ZigBeeNode}s
     */
    public Set<ZigBeeNode> getNodes() {
        return new HashSet<>(networkNodes.values());
    }

    /**
     * Gets a node given the 16 bit network address
     *
     * @param networkAddress the 16 bit network address as {@link Integer}
     * @return the {@link ZigBeeNode} or null if the node with the requested network address was not found
     */
    public ZigBeeNode getNode(final Integer networkAddress) {
        for (ZigBeeNode node : networkNodes.values()) {
            if (node.getNetworkAddress().equals(networkAddress)) {
                return node;
            }
        }

        return null;
    }

    /**
     * Gets a node given the {@link IeeeAddress}
     *
     * @param ieeeAddress the {@link IeeeAddress}
     * @return the {@link ZigBeeNode} or null if the node was not found
     */
    public ZigBeeNode getNode(final IeeeAddress ieeeAddress) {
        return networkNodes.get(ieeeAddress);
    }

    /**
     * Removes a {@link ZigBeeNode} from the network
     *
     * @param node the {@link ZigBeeNode} to remove - must not be null
     */
    public void removeNode(final ZigBeeNode node) {
        if (node == null) {
            return;
        }

        logger.debug("[{}]: {}: Node {} is removed from the network", networkManagerId, node.getIeeeAddress(),
                String.format("%04X", node.getNetworkAddress()));

        // Don't update if the node is not known
        // We especially don't want to notify listeners of a device we removed, that didn't exist!
        if (!networkNodes.containsKey(node.getIeeeAddress())) {
            return;
        }
        networkNodes.remove(node.getIeeeAddress());

        synchronized (this) {
            if (networkState != ZigBeeNetworkState.ONLINE) {
                return;
            }
        }

        for (final ZigBeeNetworkNodeListener listener : nodeListeners) {
            notificationService.execute(new Runnable() {
                @Override
                public void run() {
                    listener.nodeRemoved(node);
                }
            });
        }
    }

    /**
     * Adds or updates a {@link ZigBeeNode} to the network.
     * <p>
     * If the node is already known on the network (as uniquely identified by the {@link IeeeAddress} then registered
     * {@link ZigBeeNetworkNodeListener} listeners will receive the
     * {@link ZigBeeNetworkNodeListener#nodeUpdated(ZigBeeNode)} notification, otherwise the
     * {@link ZigBeeNetworkNodeListener#nodeAdded(ZigBeeNode)} notification will be received.
     *
     * @param node the {@link ZigBeeNode} to add or update
     */
    public void updateNode(final ZigBeeNode node) {
        if (node == null) {
            return;
        }

        logger.debug("[{}]: {}: Updating node NWK={}", networkManagerId, node.getIeeeAddress(),
                String.format("%04X", node.getNetworkAddress()));

        // Don't add if the node is already known
        // We especially don't want to notify listeners
        if (networkNodes.containsKey(node.getIeeeAddress())) {
            refreshNode(node);
            return;
        }
        networkNodes.put(node.getIeeeAddress(), node);

        synchronized (this) {
            if (networkState != ZigBeeNetworkState.ONLINE) {
                return;
            }
        }

        if (node.getNodeDescriptor() != null) {
            notificationService.execute(new Runnable() {
                @Override
                public void run() {
                    transport.setNodeDescriptor(node.getIeeeAddress(), node.getNodeDescriptor());
                }
            });
        }

        for (final ZigBeeNetworkNodeListener listener : nodeListeners) {
            notificationService.execute(new Runnable() {
                @Override
                public void run() {
                    listener.nodeAdded(node);
                }
            });
        }
    }

    /**
     * Update a {@link ZigBeeNode} within the network.
     * <p>
     * This method will return a {@link Future} {@link @Boolean} which will be true if all
     * {@link ZigBeeNetworkNodeListener}s completed within a timeout. Callers can use this to ensure that all handlers
     * have been notified of the updated node, and have performed any further manipulation within the system before
     * proceeding.
     *
     * @param node the {@link ZigBeeNode} to update
     * @return {@link Future} {@link @Boolean} which will be true if all {@link ZigBeeNetworkNodeListener}s completed
     */
    private Future<Boolean> refreshNode(final ZigBeeNode node) {
        if (node == null) {
            return null;
        }
        logger.debug("[{}]: {}: Node update. NWK Address={}", networkManagerId, node.getIeeeAddress(),
                String.format("%04X", node.getNetworkAddress()));

        final ZigBeeNode currentNode = networkNodes.get(node.getIeeeAddress());

        // Return if we don't know this node
        if (currentNode == null) {
            logger.debug("[{}]: {}: Node {} is not known - can't be updated", networkManagerId, node.getIeeeAddress(),
                    String.format("%04X", node.getNetworkAddress()));
            return null;
        }

        // Return if there were no updates
        if (!currentNode.updateNode(node)) {
            logger.debug("[{}]: {}: Node {} is not updated", networkManagerId, node.getIeeeAddress(),
                    String.format("%04X", node.getNetworkAddress()));
            return null;
        }

        if (node.getNodeDescriptor() != null && currentNode.getNodeDescriptor() != null) {
            notificationService.execute(new Runnable() {
                @Override
                public void run() {
                    transport.setNodeDescriptor(currentNode.getIeeeAddress(), currentNode.getNodeDescriptor());
                }
            });
        }

        synchronized (this) {
            if (networkState != ZigBeeNetworkState.ONLINE) {
                return null;
            }
        }

        return executorService.submit(() -> {
            CountDownLatch latch;
            synchronized (nodeListeners) {
                latch = new CountDownLatch(nodeListeners.size());
                for (final ZigBeeNetworkNodeListener listener : nodeListeners) {
                    notificationService.execute(new Runnable() {
                        @Override
                        public void run() {
                            logger.debug("[{}]: node updated call - refreshNode", networkManagerId);
                            listener.nodeUpdated(currentNode);
                            latch.countDown();
                        }
                    });
                }
            }

            try {
                // TODO: Set the timer properly
                if (latch.await(2, TimeUnit.SECONDS)) {
                    logger.trace("[{}]: {}: Refresh Node notifyListener LATCH Complete", networkManagerId, currentNode.getIeeeAddress());
                    return true;
                } else {
                    logger.trace("[{}]: {}: Refresh Node notifyListener LATCH Timeout, remaining = {}", networkManagerId, currentNode.getIeeeAddress(),
                            latch.getCount());
                    return false;
                }
            } catch (InterruptedException e) {
                logger.trace("[{}]: {}: Refresh Node notifyListener LATCH Interrupted, remaining = {}", networkManagerId, currentNode.getIeeeAddress(),
                        latch.getCount());
                return false;
            }
        });
    }

    /**
     * Adds a cluster to the list of clusters we will respond to with the {@link MatchDescriptorRequest}. Adding a
     * cluster here is only required in order to respond to this request. Typically the application should provide
     * further support for such clusters.
     *
     * @param cluster the supported cluster ID
     * @deprecated use addSupportedClientCluster or addSupportedServerCluster
     */
    @Deprecated
    public void addSupportedCluster(int cluster) {
        logger.debug("[{}]: Adding supported cluster {}", networkManagerId, String.format("%04X", cluster));
        addSupportedClientCluster(cluster);
    }

    /**
     * Adds a client cluster to the list of clusters we support.
     * We will respond to with the {@link MatchDescriptorRequest} and process any received client commands received.
     * <p>
     * This method is only valid when the network state is {@link ZigBeeNetworkState.INITIALISING}
     *
     * @param cluster the supported client cluster ID
     * @return the {@link ZigBeeStatus} showing {@link ZigBeeStatus#SUCCESS} or reason for failure
     */
    public ZigBeeStatus addSupportedClientCluster(int cluster) {
        if (networkState != ZigBeeNetworkState.INITIALISING) {
            logger.error("[{}]: Cannot add supported client cluster {} when network state is {}", networkManagerId,
                    String.format("%04X", cluster), networkState);
            return ZigBeeStatus.INVALID_STATE;
        }

        logger.debug("[{}]: Adding supported client cluster {}", networkManagerId, String.format("%04X", cluster));
        if (clusterMatcher == null) {
            clusterMatcher = new ClusterMatcher(this, localEndpointId, defaultProfileId);
        }
        clusterMatcher.addClientCluster(cluster);
        return ZigBeeStatus.SUCCESS;
    }

    /**
     * Adds a server cluster to the list of clusters we support.
     * We will respond to with the {@link MatchDescriptorRequest} and process any received server commands received.
     * <p>
     * This method is only valid when the network state is {@link ZigBeeNetworkState.INITIALISING}
     *
     * @param cluster the supported server cluster ID
     * @return the {@link ZigBeeStatus} showing {@link ZigBeeStatus#SUCCESS} or reason for failure
     */
    public ZigBeeStatus addSupportedServerCluster(int cluster) {
        if (networkState != ZigBeeNetworkState.INITIALISING) {
            logger.error("[{}]: Cannot add supported server cluster {} when network state is {}", networkManagerId,
                    String.format("%04X", cluster), networkState);
            return ZigBeeStatus.INVALID_STATE;
        }

        logger.debug("[{}]: Adding supported server cluster {}", networkManagerId, String.format("%04X", cluster));
        if (clusterMatcher == null) {
            clusterMatcher = new ClusterMatcher(this, localEndpointId, defaultProfileId);
        }
        clusterMatcher.addServerCluster(cluster);
        return ZigBeeStatus.SUCCESS;
    }

    /**
     * Checks if the local device (ie the framework) is supporting the specified client cluster.
     *
     * @param cluster the supported client cluster ID
     * @return true if the client cluster is supported locally
     */
    public boolean isClientClusterSupported(int cluster) {
        if (clusterMatcher == null) {
            return true;
        }
        return clusterMatcher.isClientSupported(cluster);
    }

    /**
     * Checks if the local device (ie the framework) is supporting the specified server cluster.
     *
     * @param cluster the supported server cluster ID
     * @return true if the server cluster is supported locally
     */
    public boolean isServerClusterSupported(int cluster) {
        if (clusterMatcher == null) {
            return true;
        }
        return clusterMatcher.isServerSupported(cluster);
    }

    /**
     * Adds a functional extension to the network. Extensions may only be added when the network manager is in the
     * {@link ZigBeeNetworkState.INITIALISING} state.
     *
     * @param extension the new {@link ZigBeeNetworkExtension}
     * @return the {@link ZigBeeStatus} showing {@link ZigBeeStatus#SUCCESS} or reason for failure
     */
    public ZigBeeStatus addExtension(ZigBeeNetworkExtension extension) {
        if (networkState != ZigBeeNetworkState.INITIALISING) {
            logger.error("[{}]: Cannot add extension {} when network state is {}", networkManagerId, extension.getClass().getSimpleName(),
                    networkState);
            return ZigBeeStatus.INVALID_STATE;
        }

        extensions.add(extension);
        extension.extensionInitialize(this);
        return ZigBeeStatus.SUCCESS;
    }

    /**
     * Gets a functional extension that has been registered with the network.
     *
     * @param requestedExtension the {@link ZigBeeNetworkExtension} to get
     * @return the requested {@link ZigBeeNetworkExtension} if it exists, or null
     */
    public <T extends ZigBeeNetworkExtension> ZigBeeNetworkExtension getExtension(Class<T> requestedExtension) {
        for (ZigBeeNetworkExtension extensionCheck : extensions) {
            if (requestedExtension.isInstance(extensionCheck)) {
                return extensionCheck;
            }
        }

        return null;
    }

    /**
     * Gets the current {@link ZigBeeNetworkState}
     *
     * @return the current {@link ZigBeeNetworkState}
     */
    public ZigBeeNetworkState getNetworkState() {
        return networkState;
    }

    /**
     * Get's the {@link IeeeAddress} of the local node.
     *
     * @return the {@link IeeeAddress} of the local node.
     */
    public IeeeAddress getLocalIeeeAddress() {
        return localIeeeAddress;
    }

    /**
     * Gets the network address of the local node. This is only valid once the network state is
     * {@link ZigBeeNetworkState.ONLINE}.
     *
     * @return the network address of the local node.
     */
    public Integer getLocalNwkAddress() {
        return localNwkAddress;
    }

    /**
     * Gets this network manager's {@link NotificationService} instance.
     *
     * @return this network manager's {@link NotificationService} instance.
     */
    public NotificationService getNotificationService() {
        return notificationService;
    }

    /**
     * Finalises the command in preparation for sending. This adds the local source address to an outgoing command.
     *
     * @param command the {@link ZigBeeCommand} that is being sent
     */
    private void finaliseOutgoingCommand(ZigBeeCommand command) {
        // Set the source address - should probably be improved!
        // Note that the endpoint is set (currently!) in the transport layer
        // TODO: Use only a single endpoint for HA and fix this here
        command.setSourceAddress(new ZigBeeEndpointAddress(localNwkAddress));
    }

    /**
     * Sends a transaction into the ZigBee stack without waiting for a response
     *
     * @param command the {@link ZigBeeCommand} to send
     */
    public void sendTransaction(ZigBeeCommand command) {
        finaliseOutgoingCommand(command);
        transactionManager.sendTransaction(command);
    }

    /**
     * Sends {@link ZigBeeCommand} command and uses the {@link ZigBeeTransactionMatcher} to match the response.
     *
     * @param command the {@link ZigBeeCommand} to send
     * @param responseMatcher the {@link ZigBeeTransactionMatcher} used to match the response to the request
     * @return the {@link CommandResult} future.
     */
    public Future<CommandResult> sendTransaction(ZigBeeCommand command, ZigBeeTransactionMatcher responseMatcher) {
        finaliseOutgoingCommand(command);
        return transactionManager.sendTransaction(command, responseMatcher);
    }

    @Override
    public void receiveCommandState(int msgTag, ZigBeeTransportProgressState state) {
        logger.debug("[{}]: RX STA: msgTag={} state={}", networkManagerId, String.format("%02X", msgTag), state);
        txRxLogger.debug("[" + networkManagerId + "] RX STA: msgTag={} state={}", String.format("%02X", msgTag), state);
        if (apsDataEntity.receiveCommandState(msgTag, state)) {
            // Pass the update to the transaction if this is NACK or ACK for last/only fragment
            transactionManager.receiveCommandState(msgTag, state);
        }
    }
    
    public void addNetworkPermitJoinListener(final ZigBeeNetworkPermitJoinListener networkPermitJoinListener) {
        if (permitJoinListeners.contains(networkPermitJoinListener)) {
            return;
        }
        final Collection<ZigBeeNetworkPermitJoinListener> modifiedPermitJoinListeners = new HashSet<>(permitJoinListeners);
        modifiedPermitJoinListeners.add(networkPermitJoinListener);
        permitJoinListeners = Collections.unmodifiableCollection(modifiedPermitJoinListeners);
    }

    
    public void removeNetworkPermitJoinListener(final ZigBeeNetworkPermitJoinListener networkPermitJoinListener) {
        final List<ZigBeeNetworkPermitJoinListener> modifiedListeners = new ArrayList<>(permitJoinListeners);
        modifiedListeners.remove(networkPermitJoinListener);
        permitJoinListeners = Collections.unmodifiableList(modifiedListeners);
    }
}
