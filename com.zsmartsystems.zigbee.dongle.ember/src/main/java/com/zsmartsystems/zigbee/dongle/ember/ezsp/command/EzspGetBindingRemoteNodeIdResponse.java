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
 * Class to implement the Ember EZSP command <b>getBindingRemoteNodeId</b>.
 * <p>
 * Returns the node ID for the binding's destination, if the ID is known. If a message is sent
 * using the binding and the destination's ID is not known, the stack will discover the ID by
 * broadcasting a ZDO address request. The application can avoid the need for this discovery by
 * using setBindingRemoteNodeId when it knows the correct ID via some other means. The
 * destination's node ID is forgotten when the binding is changed, when the local node reboots
 * or, much more rarely, when the destination node changes its ID in response to an ID conflict.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspGetBindingRemoteNodeIdResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x2F;

    /**
     * The short ID of the destination node or EMBER_NULL_NODE_ID if no destination is known.
     * <p>
     * EZSP type is <i>EmberNodeId</i> - Java type is {@link int}
     */
    private int nodeId;

    /**
     * Response and Handler constructor
     */
    public EzspGetBindingRemoteNodeIdResponse(int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(inputBuffer);

        // Deserialize the fields
        nodeId = deserializer.deserializeUInt16();
    }

    /**
     * The short ID of the destination node or EMBER_NULL_NODE_ID if no destination is known.
     * <p>
     * EZSP type is <i>EmberNodeId</i> - Java type is {@link int}
     *
     * @return the current nodeId as {@link int}
     */
    public int getNodeId() {
        return nodeId;
    }

    /**
     * The short ID of the destination node or EMBER_NULL_NODE_ID if no destination is known.
     *
     * @param nodeId the nodeId to set as {@link int}
     */
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(87);
        builder.append("EzspGetBindingRemoteNodeIdResponse [networkId=");
        builder.append(networkId);
        builder.append(", nodeId=");
        builder.append(String.format("%04X", nodeId));
        builder.append(']');
        return builder.toString();
    }
}
