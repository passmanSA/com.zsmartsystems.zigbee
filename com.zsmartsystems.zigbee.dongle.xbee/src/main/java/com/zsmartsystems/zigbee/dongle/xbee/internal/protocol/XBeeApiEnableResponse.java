/**
 * Copyright (c) 2016-2020 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.xbee.internal.protocol;

import com.zsmartsystems.zigbee.dongle.xbee.internal.protocol.CommandStatus;

/**
 * Class to implement the XBee command <b>API Enable</b>.
 * <p>
 * AT Command <b>AP</b></p>Enables API Mode. The device ignores this command when using SPI.
 * API mode 1 is always used.
 * <p>
 * This class provides methods for processing XBee API commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class XBeeApiEnableResponse extends XBeeFrame implements XBeeResponse {
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
    private Integer mode;

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
     * @return the mode as {@link Integer}
     */
    public Integer getMode() {
        return mode;
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

        // Deserialize field "Mode"
        mode = deserializeInt8();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(471);
        builder.append("XBeeApiEnableResponse [frameId=");
        builder.append(frameId);
        builder.append(", commandStatus=");
        builder.append(commandStatus);
        if (commandStatus == CommandStatus.OK) {
            builder.append(", mode=");
            builder.append(mode);
        }
        builder.append(']');
        return builder.toString();
    }
}
