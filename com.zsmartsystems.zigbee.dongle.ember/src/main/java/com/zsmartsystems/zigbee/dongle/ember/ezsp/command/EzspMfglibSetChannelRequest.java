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
 * Class to implement the Ember EZSP command <b>mfglibSetChannel</b>.
 * <p>
 * Sets the radio channel. Calibration occurs if this is the first time the channel has been
 * used.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspMfglibSetChannelRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0x8A;

    /**
     * The current channel.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int channel;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspMfglibSetChannelRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * The current channel.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current channel as {@link int}
     */
    public int getChannel() {
        return channel;
    }

    /**
     * The current channel.
     *
     * @param channel the channel to set as {@link int}
     */
    public void setChannel(int channel) {
        this.channel = channel;
    }

    @Override
    public int[] serialize(int ezspVersion) {
        // Serialize the header
        serializeHeader(ezspVersion, serializer);

        // Serialize the fields
        serializer.serializeUInt8(channel);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(80);
        builder.append("EzspMfglibSetChannelRequest [networkId=");
        builder.append(networkId);
        builder.append(", channel=");
        builder.append(channel);
        builder.append(']');
        return builder.toString();
    }
}
