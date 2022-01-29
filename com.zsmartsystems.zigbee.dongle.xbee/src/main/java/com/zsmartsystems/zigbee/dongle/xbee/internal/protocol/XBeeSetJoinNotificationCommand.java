/**
 * Copyright (c) 2016-2022 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.xbee.internal.protocol;


/**
 * Class to implement the XBee command <b>Set Join Notification</b>.
 * <p>
 * AT Command <b>JN</b></p>Set or read the join notification setting. If enabled, the device
 * transmits a broadcast node identification packet on power up and when joining. This action
 * blinks the Associate LED rapidly on all devices that receive the transmission, and sends an
 * API frame out the serial port of API devices. Digi recommends you disable this feature for
 * large networks to prevent excessive broadcasts.
 * <p>
 * This class provides methods for processing XBee API commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class XBeeSetJoinNotificationCommand extends XBeeFrame implements XBeeCommand {
    /**
     */
    private Integer frameId;

    /**
     */
    private Boolean enableNotification;

    /**
     *
     * @param frameId the frameId to set as {@link Integer}
     */
    public void setFrameId(Integer frameId) {
        this.frameId = frameId;
    }

    /**
     *
     * @param enableNotification the enableNotification to set as {@link Boolean}
     */
    public void setEnableNotification(Boolean enableNotification) {
        this.enableNotification = enableNotification;
    }

    @Override
    public int[] serialize() {
        // Serialize the command fields
        serializeCommand(0x08);
        serializeInt8(frameId);
        serializeAtCommand("JN");
        serializeBoolean(enableNotification);

        return getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(390);
        // First present the command parameters...
        // Then the responses later if they are available
        builder.append("XBeeSetJoinNotificationCommand [frameId=");
        builder.append(frameId);
        builder.append(", enableNotification=");
        builder.append(enableNotification);
        builder.append(']');
        return builder.toString();
    }
}
