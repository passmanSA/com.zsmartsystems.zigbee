/**
 * Copyright (c) 2016-2020 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;

/**
 * Class to implement the Ember EZSP command <b>mfglibGetChannel</b>.
 * <p>
 * Returns the current radio channel, as previously set via mfglibSetChannel().
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspMfglibGetChannelResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x8B;

    /**
     * The current channel.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int channel;

    /**
     * Response and Handler constructor
     */
    public EzspMfglibGetChannelResponse(int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(inputBuffer);

        // Deserialize the fields
        channel = deserializer.deserializeUInt8();
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
    public String toString() {
        final StringBuilder builder = new StringBuilder(56);
        builder.append("EzspMfglibGetChannelResponse [channel=");
        builder.append(channel);
        builder.append(']');
        return builder.toString();
    }
}
