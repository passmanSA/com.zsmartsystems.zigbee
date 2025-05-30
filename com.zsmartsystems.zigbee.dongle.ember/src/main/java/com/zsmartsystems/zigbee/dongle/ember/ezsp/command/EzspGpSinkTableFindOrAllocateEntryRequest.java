/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameRequest;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberGpAddress;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember EZSP command <b>gpSinkTableFindOrAllocateEntry</b>.
 * <p>
 * FFinds or allocates a sink entry.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspGpSinkTableFindOrAllocateEntryRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0xE1;

    /**
     * An EmberGpAddress struct containing a copy of the gpd address to be found.
     * <p>
     * EZSP type is <i>EmberGpAddress</i> - Java type is {@link EmberGpAddress}
     */
    private EmberGpAddress addr;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspGpSinkTableFindOrAllocateEntryRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * An EmberGpAddress struct containing a copy of the gpd address to be found.
     * <p>
     * EZSP type is <i>EmberGpAddress</i> - Java type is {@link EmberGpAddress}
     *
     * @return the current addr as {@link EmberGpAddress}
     */
    public EmberGpAddress getAddr() {
        return addr;
    }

    /**
     * An EmberGpAddress struct containing a copy of the gpd address to be found.
     *
     * @param addr the addr to set as {@link EmberGpAddress}
     */
    public void setAddr(EmberGpAddress addr) {
        this.addr = addr;
    }

    @Override
    public int[] serialize(int ezspVersion) {
        // Serialize the header
        serializeHeader(ezspVersion, serializer);

        // Serialize the fields
        serializer.serializeEmberGpAddress(addr);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(94);
        builder.append("EzspGpSinkTableFindOrAllocateEntryRequest [networkId=");
        builder.append(networkId);
        builder.append(", addr=");
        builder.append(addr);
        builder.append(']');
        return builder.toString();
    }
}
