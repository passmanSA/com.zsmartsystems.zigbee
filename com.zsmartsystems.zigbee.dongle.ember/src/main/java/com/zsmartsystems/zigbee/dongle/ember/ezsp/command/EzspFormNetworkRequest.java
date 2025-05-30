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
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember EZSP command <b>formNetwork</b>.
 * <p>
 * Forms a new network by becoming the coordinator.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspFormNetworkRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0x1E;

    /**
     * Specification of the new network.
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
    public EzspFormNetworkRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * Specification of the new network.
     * <p>
     * EZSP type is <i>EmberNetworkParameters</i> - Java type is {@link EmberNetworkParameters}
     *
     * @return the current parameters as {@link EmberNetworkParameters}
     */
    public EmberNetworkParameters getParameters() {
        return parameters;
    }

    /**
     * Specification of the new network.
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
        serializer.serializeEmberNetworkParameters(parameters);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(75);
        builder.append("EzspFormNetworkRequest [networkId=");
        builder.append(networkId);
        builder.append(", parameters=");
        builder.append(parameters);
        builder.append(']');
        return builder.toString();
    }
}
