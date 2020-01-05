/**
 * Copyright (c) 2016-2020 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameRequest;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EzspValueId;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember EZSP command <b>getValue</b>.
 * <p>
 * Reads a value from the NCP.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspGetValueRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0xAA;

    /**
     * Identifies which policy to modify.
     * <p>
     * EZSP type is <i>EzspValueId</i> - Java type is {@link EzspValueId}
     */
    private EzspValueId valueId;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspGetValueRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * Identifies which policy to modify.
     * <p>
     * EZSP type is <i>EzspValueId</i> - Java type is {@link EzspValueId}
     *
     * @return the current valueId as {@link EzspValueId}
     */
    public EzspValueId getValueId() {
        return valueId;
    }

    /**
     * Identifies which policy to modify.
     *
     * @param valueId the valueId to set as {@link EzspValueId}
     */
    public void setValueId(EzspValueId valueId) {
        this.valueId = valueId;
    }

    @Override
    public int[] serialize() {
        // Serialize the header
        serializeHeader(serializer);

        // Serialize the fields
        serializer.serializeEzspValueId(valueId);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(47);
        builder.append("EzspGetValueRequest [valueId=");
        builder.append(valueId);
        builder.append(']');
        return builder.toString();
    }
}
