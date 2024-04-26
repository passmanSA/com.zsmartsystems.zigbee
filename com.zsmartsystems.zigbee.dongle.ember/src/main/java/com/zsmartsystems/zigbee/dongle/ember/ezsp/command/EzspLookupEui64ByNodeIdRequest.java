/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember EZSP command <b>lookupEui64ByNodeId</b>.
 * <p>
 * Returns the EUI64 that corresponds to the specified node ID. The EUI64 is found by searching
 * through all stack tables for the specified node ID.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspLookupEui64ByNodeIdRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0x61;

    /**
     * The short ID of the node or EMBER_NULL_NODE_ID if the short ID is not known.
     * <p>
     * EZSP type is <i>EmberNodeId</i> - Java type is {@link int}
     */
    private int nodeId;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspLookupEui64ByNodeIdRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * The short ID of the node or EMBER_NULL_NODE_ID if the short ID is not known.
     * <p>
     * EZSP type is <i>EmberNodeId</i> - Java type is {@link int}
     *
     * @return the current nodeId as {@link int}
     */
    public int getNodeId() {
        return nodeId;
    }

    /**
     * The short ID of the node or EMBER_NULL_NODE_ID if the short ID is not known.
     *
     * @param nodeId the nodeId to set as {@link int}
     */
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public int[] serialize() {
        // Serialize the header
        serializeHeader(serializer);

        // Serialize the fields
        serializer.serializeUInt16(nodeId);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(83);
        builder.append("EzspLookupEui64ByNodeIdRequest [networkId=");
        builder.append(networkId);
        builder.append(", nodeId=");
        builder.append(String.format("%04X", nodeId));
        builder.append(']');
        return builder.toString();
    }
}
