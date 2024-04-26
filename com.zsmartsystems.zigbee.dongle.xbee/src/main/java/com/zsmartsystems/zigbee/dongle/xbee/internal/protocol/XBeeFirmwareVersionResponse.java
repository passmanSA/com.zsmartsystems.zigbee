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
 * Class to implement the XBee command <b>Firmware Version</b>.
 * <p>
 * AT Command <b>VR</b></p>Reads the firmware version on a device as a 4-digit hex number.
 * <p>
 * This class provides methods for processing XBee API commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class XBeeFirmwareVersionResponse extends XBeeFrame implements XBeeResponse {
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
     */
    private int[] firmwareVersion;

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
     *
     * @return the firmwareVersion as {@link int[]}
     */
    public int[] getFirmwareVersion() {
        return firmwareVersion;
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

        // Deserialize field "Firmware Version"
        firmwareVersion = deserializeData();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(477);
        builder.append("XBeeFirmwareVersionResponse [frameId=");
        builder.append(frameId);
        builder.append(", commandStatus=");
        builder.append(commandStatus);
        if (commandStatus == CommandStatus.OK) {
            builder.append(", firmwareVersion=");
            if (firmwareVersion == null) {
                builder.append("null");
            } else {
                for (int cnt = 0; cnt < firmwareVersion.length; cnt++) {
                    if (cnt > 0) {
                        builder.append(' ');
                    }
                    builder.append(String.format("%02X", firmwareVersion[cnt]));
                }
            }
        }
        builder.append(']');
        return builder.toString();
    }
}
