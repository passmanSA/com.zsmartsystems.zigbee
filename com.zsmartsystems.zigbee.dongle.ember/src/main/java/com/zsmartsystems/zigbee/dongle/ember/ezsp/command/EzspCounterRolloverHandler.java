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
 * Class to implement the Ember EZSP command <b>counterRolloverHandler</b>.
 * <p>
 * This call is fired when a counter exceeds its threshold.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspCounterRolloverHandler extends EzspFrameResponse {
    public static final int FRAME_ID = 0xF2;

    /**
     * Type of Counter.
     * <p>
     * EZSP type is <i>EmberCounterType</i> - Java type is {@link int}
     */
    private int type;

    /**
     * Response and Handler constructor
     */
    public EzspCounterRolloverHandler(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        type = deserializer.deserializeUInt8();
    }

    /**
     * Type of Counter.
     * <p>
     * EZSP type is <i>EmberCounterType</i> - Java type is {@link int}
     *
     * @return the current type as {@link int}
     */
    public int getType() {
        return type;
    }

    /**
     * Type of Counter.
     *
     * @param type the type to set as {@link int}
     */
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(79);
        builder.append("EzspCounterRolloverHandler [networkId=");
        builder.append(networkId);
        builder.append(", type=");
        builder.append(type);
        builder.append(']');
        return builder.toString();
    }
}
