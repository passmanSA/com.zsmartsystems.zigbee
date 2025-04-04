/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameRequest;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberNetworkParameters;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberNodeType;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember EZSP command <b>joinNetwork</b>.
 * <p>
 * Causes the stack to associate with the network using the specified network parameters. It
 * can take several seconds for the stack to associate with the local network. Do not send
 * messages until the stackStatusHandler callback informs you that the stack is up.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspJoinNetworkRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0x1F;

    /**
     * Specification of the role that this node will have in the network. This role must not be
     * EMBER_COORDINATOR. To be a coordinator, use the formNetwork command.
     * <p>
     * EZSP type is <i>EmberNodeType</i> - Java type is {@link EmberNodeType}
     */
    private EmberNodeType nodeType;

    /**
     * Specification of the network with which the node should associate.
     * <p>
     * EZSP type is <i>EmberNetworkParameters</i> - Java type is {@link EmberNetworkParameters}
     */
    private EmberNetworkParameters parameters;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspJoinNetworkRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * Specification of the role that this node will have in the network. This role must not be
     * EMBER_COORDINATOR. To be a coordinator, use the formNetwork command.
     * <p>
     * EZSP type is <i>EmberNodeType</i> - Java type is {@link EmberNodeType}
     *
     * @return the current nodeType as {@link EmberNodeType}
     */
    public EmberNodeType getNodeType() {
        return nodeType;
    }

    /**
     * Specification of the role that this node will have in the network. This role must not be
     * EMBER_COORDINATOR. To be a coordinator, use the formNetwork command.
     *
     * @param nodeType the nodeType to set as {@link EmberNodeType}
     */
    public void setNodeType(EmberNodeType nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * Specification of the network with which the node should associate.
     * <p>
     * EZSP type is <i>EmberNetworkParameters</i> - Java type is {@link EmberNetworkParameters}
     *
     * @return the current parameters as {@link EmberNetworkParameters}
     */
    public EmberNetworkParameters getParameters() {
        return parameters;
    }

    /**
     * Specification of the network with which the node should associate.
     *
     * @param parameters the parameters to set as {@link EmberNetworkParameters}
     */
    public void setParameters(EmberNetworkParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public int[] serialize(int ezspVersion) {
        // Serialize the header
        serializeHeader(ezspVersion, serializer);

        // Serialize the fields
        serializer.serializeEmberNodeType(nodeType);
        serializer.serializeEmberNetworkParameters(parameters);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(100);
        builder.append("EzspJoinNetworkRequest [networkId=");
        builder.append(networkId);
        builder.append(", nodeType=");
        builder.append(nodeType);
        builder.append(", parameters=");
        builder.append(parameters);
        builder.append(']');
        return builder.toString();
    }
}
