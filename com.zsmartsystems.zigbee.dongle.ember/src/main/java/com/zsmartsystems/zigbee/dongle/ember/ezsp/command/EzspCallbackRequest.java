/**
 * Copyright (c) 2016-2023 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember EZSP command <b>callback</b>.
 * <p>
 * Allows the NCP to respond with a pending callback.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspCallbackRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0x06;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspCallbackRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    @Override
    public int[] serialize(int ezspVersion) {
        // Serialize the header
        serializeHeader(ezspVersion, serializer);

        // Serialize the fields
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(47);
        builder.append("EzspCallbackRequest [networkId=");
        builder.append(networkId);
        builder.append(']');
        return builder.toString();
    }
}
