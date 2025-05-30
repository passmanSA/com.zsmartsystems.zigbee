/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.IeeeAddress;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameRequest;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberKeyData;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember EZSP command <b>addOrUpdateKeyTableEntry</b>.
 * <p>
 * This function updates an existing entry in the key table or adds a new one. It first searches
 * the table for an existing entrythat matches the passed EUI64 address. If no entry is found, it
 * searches for the first free entry. If successful, it updates the key data and resets the
 * associated incoming frame counter. If it fails to find an existing entry and no free one
 * exists, it returns a failure.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspAddOrUpdateKeyTableEntryRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0x66;

    /**
     * The address of the partner device that shares the key
     * <p>
     * EZSP type is <i>EmberEUI64</i> - Java type is {@link IeeeAddress}
     */
    private IeeeAddress address;

    /**
     * This indicates whether the key is a Link or a Master Key
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     */
    private boolean linkKey;

    /**
     * The actual key data associated with the entry.
     * <p>
     * EZSP type is <i>EmberKeyData</i> - Java type is {@link EmberKeyData}
     */
    private EmberKeyData keyData;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspAddOrUpdateKeyTableEntryRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * The address of the partner device that shares the key
     * <p>
     * EZSP type is <i>EmberEUI64</i> - Java type is {@link IeeeAddress}
     *
     * @return the current address as {@link IeeeAddress}
     */
    public IeeeAddress getAddress() {
        return address;
    }

    /**
     * The address of the partner device that shares the key
     *
     * @param address the address to set as {@link IeeeAddress}
     */
    public void setAddress(IeeeAddress address) {
        this.address = address;
    }

    /**
     * This indicates whether the key is a Link or a Master Key
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     *
     * @return the current linkKey as {@link boolean}
     */
    public boolean getLinkKey() {
        return linkKey;
    }

    /**
     * This indicates whether the key is a Link or a Master Key
     *
     * @param linkKey the linkKey to set as {@link boolean}
     */
    public void setLinkKey(boolean linkKey) {
        this.linkKey = linkKey;
    }

    /**
     * The actual key data associated with the entry.
     * <p>
     * EZSP type is <i>EmberKeyData</i> - Java type is {@link EmberKeyData}
     *
     * @return the current keyData as {@link EmberKeyData}
     */
    public EmberKeyData getKeyData() {
        return keyData;
    }

    /**
     * The actual key data associated with the entry.
     *
     * @param keyData the keyData to set as {@link EmberKeyData}
     */
    public void setKeyData(EmberKeyData keyData) {
        this.keyData = keyData;
    }

    @Override
    public int[] serialize(int ezspVersion) {
        // Serialize the header
        serializeHeader(ezspVersion, serializer);

        // Serialize the fields
        serializer.serializeEmberEui64(address);
        serializer.serializeBool(linkKey);
        serializer.serializeEmberKeyData(keyData);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(138);
        builder.append("EzspAddOrUpdateKeyTableEntryRequest [networkId=");
        builder.append(networkId);
        builder.append(", address=");
        builder.append(address);
        builder.append(", linkKey=");
        builder.append(linkKey);
        builder.append(", keyData=");
        builder.append(keyData);
        builder.append(']');
        return builder.toString();
    }
}
