/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.xbee.internal.protocol;

import com.zsmartsystems.zigbee.dongle.xbee.internal.protocol.CommandStatus;

/**
 * Class to implement the XBee command <b>Network Reset</b>.
 * <p>
 * AT Command <b>NR</b></p>Resets network layer parameters on one or more modules within a
 * PAN. Responds immediately with an OK then causes a network restart. The device loses all
 * network configuration and routing information. If NR = 0: Resets network layer parameters
 * on the node issuing the command. If NR = 1: Sends broadcast transmission to reset network
 * layer parameters on all nodes in the PAN.
 * <p>
 * This class provides methods for processing XBee API commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class XBeeNetworkResetResponse extends XBeeFrame implements XBeeResponse {
    /**
     * Response field
     */
    private Integer frameId;

    /**
     * Response field
     */
    private CommandStatus commandStatus;

    /**
     * Response field
     * Sends broadcast transmission to reset network layer parameters on all nodes in the PAN.
     */
    private Boolean resetRemoteDevices;

    /**
     *
     * @return the frameId as {@link Integer}
     */
    public Integer getFrameId() {
        return frameId;
    }

    /**
     *
     * @return the commandStatus as {@link CommandStatus}
     */
    public CommandStatus getCommandStatus() {
        return commandStatus;
    }

    /**
     * Sends broadcast transmission to reset network layer parameters on all nodes in the PAN.
     *
     * @return the resetRemoteDevices as {@link Boolean}
     */
    public Boolean getResetRemoteDevices() {
        return resetRemoteDevices;
    }


    @Override
    public void deserialize(int[] incomingData) {
        initialiseDeserializer(incomingData);

        // Deserialize the fields for the response

        // Deserialize field "Frame ID"
        frameId = deserializeInt8();
        deserializeAtCommand();

        // Deserialize field "Command Status"
        commandStatus = deserializeCommandStatus();
        if (commandStatus != CommandStatus.OK || isComplete()) {
            return;
        }

        // Deserialize field "Reset Remote Devices"
        resetRemoteDevices = deserializeBoolean();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(474);
        builder.append("XBeeNetworkResetResponse [frameId=");
        builder.append(frameId);
        builder.append(", commandStatus=");
        builder.append(commandStatus);
        if (commandStatus == CommandStatus.OK) {
            builder.append(", resetRemoteDevices=");
            builder.append(resetRemoteDevices);
        }
        builder.append(']');
        return builder.toString();
    }
}
