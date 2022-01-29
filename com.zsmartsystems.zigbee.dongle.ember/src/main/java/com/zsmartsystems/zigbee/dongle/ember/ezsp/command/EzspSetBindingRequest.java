/**
 * Copyright (c) 2016-2022 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameRequest;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberBindingTableEntry;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember EZSP command <b>setBinding</b>.
 * <p>
 * Sets an entry in the binding table.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspSetBindingRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0x2B;

    /**
     * The index of a binding table entry.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int index;

    /**
     * The contents of the binding entry.
     * <p>
     * EZSP type is <i>EmberBindingTableEntry</i> - Java type is {@link EmberBindingTableEntry}
     */
    private EmberBindingTableEntry value;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspSetBindingRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * The index of a binding table entry.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current index as {@link int}
     */
    public int getIndex() {
        return index;
    }

    /**
     * The index of a binding table entry.
     *
     * @param index the index to set as {@link int}
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * The contents of the binding entry.
     * <p>
     * EZSP type is <i>EmberBindingTableEntry</i> - Java type is {@link EmberBindingTableEntry}
     *
     * @return the current value as {@link EmberBindingTableEntry}
     */
    public EmberBindingTableEntry getValue() {
        return value;
    }

    /**
     * The contents of the binding entry.
     *
     * @param value the value to set as {@link EmberBindingTableEntry}
     */
    public void setValue(EmberBindingTableEntry value) {
        this.value = value;
    }

    @Override
    public int[] serialize(int ezspVersion) {
        // Serialize the header
        serializeHeader(ezspVersion, serializer);

        // Serialize the fields
        serializer.serializeUInt8(index);
        serializer.serializeEmberBindingTableEntry(value);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(99);
        builder.append("EzspSetBindingRequest [networkId=");
        builder.append(networkId);
        builder.append(", index=");
        builder.append(index);
        builder.append(", value=");
        builder.append(value);
        builder.append(']');
        return builder.toString();
    }
}
