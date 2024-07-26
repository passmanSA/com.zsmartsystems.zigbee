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
 * Class to implement the Ember EZSP command <b>getRadioParameters</b>.
 * <p>
 * Returns the current radio parameters based on phy index.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspGetRadioParametersRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0xFD;

    /**
     * The physical radio to query.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int phyIndex;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspGetRadioParametersRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * The physical radio to query.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current phyIndex as {@link int}
     */
    public int getPhyIndex() {
        return phyIndex;
    }

    /**
     * The physical radio to query.
     *
     * @param phyIndex the phyIndex to set as {@link int}
     */
    public void setPhyIndex(int phyIndex) {
        this.phyIndex = phyIndex;
    }

    @Override
    public int[] serialize(int ezspVersion) {
        // Serialize the header
        serializeHeader(ezspVersion, serializer);

        // Serialize the fields
        serializer.serializeUInt8(phyIndex);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(82);
        builder.append("EzspGetRadioParametersRequest [networkId=");
        builder.append(networkId);
        builder.append(", phyIndex=");
        builder.append(phyIndex);
        builder.append(']');
        return builder.toString();
    }
}
