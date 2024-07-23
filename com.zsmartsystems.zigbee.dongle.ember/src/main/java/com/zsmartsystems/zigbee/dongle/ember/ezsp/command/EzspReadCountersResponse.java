/**
 * Copyright (c) 2016-2023 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;

/**
 * Class to implement the Ember EZSP command <b>readCounters</b>.
 * <p>
 * Retrieves Ember counters. See the EmberCounterType enumeration for the counter types.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspReadCountersResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0xF1;

    /**
     * A list of all counter values ordered according to the EmberCounterType enumeration.
     * <p>
     * EZSP type is <i>uint16_t[29]</i> - Java type is {@link int[]}
     */
    private int[] values;

    /**
     * Response and Handler constructor
     */
    public EzspReadCountersResponse(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        values = deserializer.deserializeUInt16Array(29);
    }

    /**
     * A list of all counter values ordered according to the EmberCounterType enumeration.
     * <p>
     * EZSP type is <i>uint16_t[29]</i> - Java type is {@link int[]}
     *
     * @return the current values as {@link int[]}
     */
    public int[] getValues() {
        return values;
    }

    /**
     * A list of all counter values ordered according to the EmberCounterType enumeration.
     *
     * @param values the values to set as {@link int[]}
     */
    public void setValues(int[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(77);
        builder.append("EzspReadCountersResponse [networkId=");
        builder.append(networkId);
        builder.append(", values=");
        for (int cnt = 0; cnt < values.length; cnt++) {
            if (cnt > 0) {
                builder.append(' ');
            }
            builder.append(String.format("%04X", values[cnt]));
        }
        builder.append(']');
        return builder.toString();
    }
}
