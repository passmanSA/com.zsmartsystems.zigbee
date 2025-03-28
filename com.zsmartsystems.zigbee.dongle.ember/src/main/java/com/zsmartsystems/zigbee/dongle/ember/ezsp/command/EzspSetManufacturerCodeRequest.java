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
 * Class to implement the Ember EZSP command <b>setManufacturerCode</b>.
 * <p>
 * Sets the manufacturer code to the specified value. The manufacturer code is one of the fields
 * of the node descriptor.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspSetManufacturerCodeRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0x15;

    /**
     * The manufacturer code for the local node.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     */
    private int code;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspSetManufacturerCodeRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * The manufacturer code for the local node.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     *
     * @return the current code as {@link int}
     */
    public int getCode() {
        return code;
    }

    /**
     * The manufacturer code for the local node.
     *
     * @param code the code to set as {@link int}
     */
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public int[] serialize(int ezspVersion) {
        // Serialize the header
        serializeHeader(ezspVersion, serializer);

        // Serialize the fields
        serializer.serializeUInt16(code);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(83);
        builder.append("EzspSetManufacturerCodeRequest [networkId=");
        builder.append(networkId);
        builder.append(", code=");
        builder.append(code);
        builder.append(']');
        return builder.toString();
    }
}
