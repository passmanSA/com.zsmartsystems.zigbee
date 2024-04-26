/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.xbee.internal.protocol;


/**
 * Class to implement the XBee command <b>Set Save Data</b>.
 * <p>
 * AT Command <b>WR</b></p>Writes parameter values to non-volatile memory so that parameter
 * modifications persist through subsequent resets.
 * <p>
 * This class provides methods for processing XBee API commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class XBeeSetSaveDataCommand extends XBeeFrame implements XBeeCommand {
    /**
     */
    private Integer frameId;

    /**
     *
     * @param frameId the frameId to set as {@link Integer}
     */
    public void setFrameId(Integer frameId) {
        this.frameId = frameId;
    }

    @Override
    public int[] serialize() {
        // Serialize the command fields
        serializeCommand(0x08);
        serializeInt8(frameId);
        serializeAtCommand("WR");

        return getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(292);
        // First present the command parameters...
        // Then the responses later if they are available
        builder.append("XBeeSetSaveDataCommand [frameId=");
        builder.append(frameId);
        builder.append(']');
        return builder.toString();
    }
}
