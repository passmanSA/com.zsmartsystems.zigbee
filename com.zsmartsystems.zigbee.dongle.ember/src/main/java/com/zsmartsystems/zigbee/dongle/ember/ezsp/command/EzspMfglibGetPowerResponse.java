/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;

/**
 * Class to implement the Ember EZSP command <b>mfglibGetPower</b>.
 * <p>
 * Returns the current radio power setting, as previously set via mfglibSetPower().
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspMfglibGetPowerResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x8D;

    /**
     * Power in units of dBm. Refer to radio data sheet for valid range.
     * <p>
     * EZSP type is <i>int8s</i> - Java type is {@link int}
     */
    private int power;

    /**
     * Response and Handler constructor
     */
    public EzspMfglibGetPowerResponse(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        power = deserializer.deserializeInt8S();
    }

    /**
     * Power in units of dBm. Refer to radio data sheet for valid range.
     * <p>
     * EZSP type is <i>int8s</i> - Java type is {@link int}
     *
     * @return the current power as {@link int}
     */
    public int getPower() {
        return power;
    }

    /**
     * Power in units of dBm. Refer to radio data sheet for valid range.
     *
     * @param power the power to set as {@link int}
     */
    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(79);
        builder.append("EzspMfglibGetPowerResponse [networkId=");
        builder.append(networkId);
        builder.append(", power=");
        builder.append(power);
        builder.append(']');
        return builder.toString();
    }
}
