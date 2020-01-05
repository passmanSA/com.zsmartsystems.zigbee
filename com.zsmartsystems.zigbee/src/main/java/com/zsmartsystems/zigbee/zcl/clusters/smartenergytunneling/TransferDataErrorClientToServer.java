/**
 * Copyright (c) 2016-2020 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.zcl.clusters.smartenergytunneling;

import javax.annotation.Generated;

import com.zsmartsystems.zigbee.zcl.ZclCommand;
import com.zsmartsystems.zigbee.zcl.ZclFieldDeserializer;
import com.zsmartsystems.zigbee.zcl.ZclFieldSerializer;
import com.zsmartsystems.zigbee.zcl.protocol.ZclCommandDirection;
import com.zsmartsystems.zigbee.zcl.protocol.ZclDataType;

/**
 * Transfer Data Error Client To Server value object class.
 * <p>
 * Cluster: <b>Smart Energy Tunneling</b>. Command ID 0x03 is sent <b>TO</b> the server.
 * This command is a <b>specific</b> command used for the Smart Energy Tunneling cluster.
 * <p>
 * This command is generated by the receiver of a TransferData command if the tunnel status
 * indicates that something is wrong. There are two three cases in which TransferDataError is
 * sent:
 * <p>
 * The TransferData received contains a TunnelID that does not match to any of the active
 * tunnels of the receiving device. This could happen if a (sleeping) device sends a
 * TransferData command to a tunnel that has been closed by the server after the
 * CloseTunnelTimeout.
 * <p>
 * The TransferData received contains a proper TunnelID of an active tunnel, but the device
 * sending the data does not match to it.
 * <p>
 * The TransferData received contains more data than indicated by the
 * MaximumIncomingTransferSize of the receiving device.
 * <p>
 * Code is auto-generated. Modifications may be overwritten!
 */
@Generated(value = "com.zsmartsystems.zigbee.autocode.ZigBeeCodeGenerator", date = "2019-04-14T08:41:54Z")
public class TransferDataErrorClientToServer extends ZclCommand {
    /**
     * The cluster ID to which this command belongs.
     */
    public static int CLUSTER_ID = 0x0704;

    /**
     * The command ID.
     */
    public static int COMMAND_ID = 0x03;

    /**
     * Tunnel ID command message field.
     * <p>
     * A number between 0..65535 that uniquely identifies the tunnel that has been allocated
     * in the server triggered through the RequestTunnel command. This ID must be used for the
     * data transfer through the tunnel or passed with any commands concerning that specific
     * tunnel.
     */
    private Integer tunnelId;

    /**
     * Transfer Data Status command message field.
     * <p>
     * The TransferDataStatus parameter indicates the error that occurred within the
     * receiver after the last TransferData command.
     */
    private Integer transferDataStatus;

    /**
     * Default constructor.
     */
    public TransferDataErrorClientToServer() {
        clusterId = CLUSTER_ID;
        commandId = COMMAND_ID;
        genericCommand = false;
        commandDirection = ZclCommandDirection.CLIENT_TO_SERVER;
    }

    /**
     * Gets Tunnel ID.
     * <p>
     * A number between 0..65535 that uniquely identifies the tunnel that has been allocated
     * in the server triggered through the RequestTunnel command. This ID must be used for the
     * data transfer through the tunnel or passed with any commands concerning that specific
     * tunnel.
     *
     * @return the Tunnel ID
     */
    public Integer getTunnelId() {
        return tunnelId;
    }

    /**
     * Sets Tunnel ID.
     * <p>
     * A number between 0..65535 that uniquely identifies the tunnel that has been allocated
     * in the server triggered through the RequestTunnel command. This ID must be used for the
     * data transfer through the tunnel or passed with any commands concerning that specific
     * tunnel.
     *
     * @param tunnelId the Tunnel ID
     */
    public void setTunnelId(final Integer tunnelId) {
        this.tunnelId = tunnelId;
    }

    /**
     * Gets Transfer Data Status.
     * <p>
     * The TransferDataStatus parameter indicates the error that occurred within the
     * receiver after the last TransferData command.
     *
     * @return the Transfer Data Status
     */
    public Integer getTransferDataStatus() {
        return transferDataStatus;
    }

    /**
     * Sets Transfer Data Status.
     * <p>
     * The TransferDataStatus parameter indicates the error that occurred within the
     * receiver after the last TransferData command.
     *
     * @param transferDataStatus the Transfer Data Status
     */
    public void setTransferDataStatus(final Integer transferDataStatus) {
        this.transferDataStatus = transferDataStatus;
    }

    @Override
    public void serialize(final ZclFieldSerializer serializer) {
        serializer.serialize(tunnelId, ZclDataType.UNSIGNED_16_BIT_INTEGER);
        serializer.serialize(transferDataStatus, ZclDataType.ENUMERATION_8_BIT);
    }

    @Override
    public void deserialize(final ZclFieldDeserializer deserializer) {
        tunnelId = (Integer) deserializer.deserialize(ZclDataType.UNSIGNED_16_BIT_INTEGER);
        transferDataStatus = (Integer) deserializer.deserialize(ZclDataType.ENUMERATION_8_BIT);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(100);
        builder.append("TransferDataErrorClientToServer [");
        builder.append(super.toString());
        builder.append(", tunnelId=");
        builder.append(tunnelId);
        builder.append(", transferDataStatus=");
        builder.append(transferDataStatus);
        builder.append(']');
        return builder.toString();
    }

}
