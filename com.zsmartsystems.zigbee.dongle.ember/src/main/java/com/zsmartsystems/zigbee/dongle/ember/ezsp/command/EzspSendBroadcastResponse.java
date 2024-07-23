/**
 * Copyright (c) 2016-2023 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberStatus;

/**
 * Class to implement the Ember EZSP command <b>sendBroadcast</b>.
 * <p>
 * Sends a broadcast message as per the ZigBee specification.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspSendBroadcastResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x36;

    /**
     * An EmberStatus value indicating success or the reason for failure.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     */
    private EmberStatus status;

    /**
     * The sequence number that will be used when this message is transmitted.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int sequence;

    /**
     * Response and Handler constructor
     */
    public EzspSendBroadcastResponse(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        status = deserializer.deserializeEmberStatus();
        sequence = deserializer.deserializeUInt8();
    }

    /**
     * An EmberStatus value indicating success or the reason for failure.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     *
     * @return the current status as {@link EmberStatus}
     */
    public EmberStatus getStatus() {
        return status;
    }

    /**
     * An EmberStatus value indicating success or the reason for failure.
     *
     * @param status the status to set as {@link EmberStatus}
     */
    public void setStatus(EmberStatus status) {
        this.status = status;
    }

    /**
     * The sequence number that will be used when this message is transmitted.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current sequence as {@link int}
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * The sequence number that will be used when this message is transmitted.
     *
     * @param sequence the sequence to set as {@link int}
     */
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(103);
        builder.append("EzspSendBroadcastResponse [networkId=");
        builder.append(networkId);
        builder.append(", status=");
        builder.append(status);
        builder.append(", sequence=");
        builder.append(String.format("%02X", sequence));
        builder.append(']');
        return builder.toString();
    }
}
