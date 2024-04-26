/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.structure;

import com.zsmartsystems.zigbee.IeeeAddress;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberKeyData;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberKeyType;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspDeserializer;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to implement the Ember Structure <b>EmberKeyStruct</b>.
 * <p>
 * A structure containing a key and its associated data.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EmberKeyStruct {

    /**
     * A bitmask indicating the presence of data within the various fields in the structure.
     * <p>
     * EZSP type is <i>EmberKeyStructBitmask</i> - Java type is {@link EmberKeyStructBitmask}
     * Parameter allows multiple options so implemented as a {@link Set}.
     */
    private Set<EmberKeyStructBitmask> bitmask = new HashSet<EmberKeyStructBitmask>();

    /**
     * The type of the key.
     * <p>
     * EZSP type is <i>EmberKeyType</i> - Java type is {@link EmberKeyType}
     */
    private EmberKeyType type;

    /**
     * The actual key data.
     * <p>
     * EZSP type is <i>EmberKeyData</i> - Java type is {@link EmberKeyData}
     */
    private EmberKeyData key;

    /**
     * The outgoing frame counter associated with the key.
     * <p>
     * EZSP type is <i>uint32_t</i> - Java type is {@link int}
     */
    private int outgoingFrameCounter;

    /**
     * The frame counter of the partner device associated with the key.
     * <p>
     * EZSP type is <i>uint32_t</i> - Java type is {@link int}
     */
    private int incomingFrameCounter;

    /**
     * The sequence number associated with the key.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int sequenceNumber;

    /**
     * The IEEE address of the partner device also in possession of the key.
     * <p>
     * EZSP type is <i>EmberEUI64</i> - Java type is {@link IeeeAddress}
     */
    private IeeeAddress partnerEUI64;

    /**
     * Default Constructor
     */
    public EmberKeyStruct() {
    }

    public EmberKeyStruct(EzspDeserializer deserializer) {
        deserialize(deserializer);
    }

    /**
     * A bitmask indicating the presence of data within the various fields in the structure.
     * <p>
     * EZSP type is <i>EmberKeyStructBitmask</i> - Java type is {@link EmberKeyStructBitmask}
     *
     * @return the current bitmask as {@link Set} of {@link EmberKeyStructBitmask}
     */
    public Set<EmberKeyStructBitmask> getBitmask() {
        return bitmask;
    }

    /**
     * A bitmask indicating the presence of data within the various fields in the structure.
     *
     * @param bitmask the bitmask to add to the {@link Set} as {@link EmberKeyStructBitmask}
     */
    public void addBitmask(EmberKeyStructBitmask bitmask) {
        this.bitmask.add(bitmask);
    }

    /**
     * A bitmask indicating the presence of data within the various fields in the structure.
     *
     * @param bitmask the bitmask to remove to the {@link Set} as {@link EmberKeyStructBitmask}
     */
    public void removeBitmask(EmberKeyStructBitmask bitmask) {
        this.bitmask.remove(bitmask);
    }

    /**
     * The type of the key.
     * <p>
     * EZSP type is <i>EmberKeyType</i> - Java type is {@link EmberKeyType}
     *
     * @return the current type as {@link EmberKeyType}
     */
    public EmberKeyType getType() {
        return type;
    }

    /**
     * The type of the key.
     *
     * @param type the type to set as {@link EmberKeyType}
     */
    public void setType(EmberKeyType type) {
        this.type = type;
    }

    /**
     * The actual key data.
     * <p>
     * EZSP type is <i>EmberKeyData</i> - Java type is {@link EmberKeyData}
     *
     * @return the current key as {@link EmberKeyData}
     */
    public EmberKeyData getKey() {
        return key;
    }

    /**
     * The actual key data.
     *
     * @param key the key to set as {@link EmberKeyData}
     */
    public void setKey(EmberKeyData key) {
        this.key = key;
    }

    /**
     * The outgoing frame counter associated with the key.
     * <p>
     * EZSP type is <i>uint32_t</i> - Java type is {@link int}
     *
     * @return the current outgoingFrameCounter as {@link int}
     */
    public int getOutgoingFrameCounter() {
        return outgoingFrameCounter;
    }

    /**
     * The outgoing frame counter associated with the key.
     *
     * @param outgoingFrameCounter the outgoingFrameCounter to set as {@link int}
     */
    public void setOutgoingFrameCounter(int outgoingFrameCounter) {
        this.outgoingFrameCounter = outgoingFrameCounter;
    }

    /**
     * The frame counter of the partner device associated with the key.
     * <p>
     * EZSP type is <i>uint32_t</i> - Java type is {@link int}
     *
     * @return the current incomingFrameCounter as {@link int}
     */
    public int getIncomingFrameCounter() {
        return incomingFrameCounter;
    }

    /**
     * The frame counter of the partner device associated with the key.
     *
     * @param incomingFrameCounter the incomingFrameCounter to set as {@link int}
     */
    public void setIncomingFrameCounter(int incomingFrameCounter) {
        this.incomingFrameCounter = incomingFrameCounter;
    }

    /**
     * The sequence number associated with the key.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current sequenceNumber as {@link int}
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * The sequence number associated with the key.
     *
     * @param sequenceNumber the sequenceNumber to set as {@link int}
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * The IEEE address of the partner device also in possession of the key.
     * <p>
     * EZSP type is <i>EmberEUI64</i> - Java type is {@link IeeeAddress}
     *
     * @return the current partnerEUI64 as {@link IeeeAddress}
     */
    public IeeeAddress getPartnerEUI64() {
        return partnerEUI64;
    }

    /**
     * The IEEE address of the partner device also in possession of the key.
     *
     * @param partnerEUI64 the partnerEUI64 to set as {@link IeeeAddress}
     */
    public void setPartnerEUI64(IeeeAddress partnerEUI64) {
        this.partnerEUI64 = partnerEUI64;
    }

    /**
     * Serialise the contents of the EZSP structure.
     *
     * @param serializer the {@link EzspSerializer} used to serialize
     */
    public int[] serialize(EzspSerializer serializer) {
        // Serialize the fields
        serializer.serializeEmberKeyStructBitmask(bitmask);
        serializer.serializeEmberKeyType(type);
        serializer.serializeEmberKeyData(key);
        serializer.serializeUInt32(outgoingFrameCounter);
        serializer.serializeUInt32(incomingFrameCounter);
        serializer.serializeUInt8(sequenceNumber);
        serializer.serializeEmberEui64(partnerEUI64);
        return serializer.getPayload();
    }

    /**
     * Deserialise the contents of the EZSP structure.
     *
     * @param deserializer the {@link EzspDeserializer} used to deserialize
     */
    public void deserialize(EzspDeserializer deserializer) {
        // Deserialize the fields
        bitmask = deserializer.deserializeEmberKeyStructBitmask();
        type = deserializer.deserializeEmberKeyType();
        key = deserializer.deserializeEmberKeyData();
        outgoingFrameCounter = deserializer.deserializeUInt32();
        incomingFrameCounter = deserializer.deserializeUInt32();
        sequenceNumber = deserializer.deserializeUInt8();
        partnerEUI64 = deserializer.deserializeEmberEui64();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(192);
        builder.append("EmberKeyStruct [bitmask=");
        builder.append(bitmask);
        builder.append(", type=");
        builder.append(type);
        builder.append(", key=");
        builder.append(key);
        builder.append(", outgoingFrameCounter=");
        builder.append(String.format("%08X", outgoingFrameCounter));
        builder.append(", incomingFrameCounter=");
        builder.append(String.format("%08X", incomingFrameCounter));
        builder.append(", sequenceNumber=");
        builder.append(String.format("%02X", sequenceNumber));
        builder.append(", partnerEUI64=");
        builder.append(partnerEUI64);
        builder.append(']');
        return builder.toString();
    }
}
