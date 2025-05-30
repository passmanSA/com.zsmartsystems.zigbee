/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberMulticastTableEntry;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberStatus;

/**
 * Class to implement the Ember EZSP command <b>getMulticastTableEntry</b>.
 * <p>
 * Gets an entry from the multicast table.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspGetMulticastTableEntryResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x63;

    /**
     * An EmberStatus value indicating success or the reason for failure.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     */
    private EmberStatus status;

    /**
     * The contents of the multicast entry.
     * <p>
     * EZSP type is <i>EmberMulticastTableEntry</i> - Java type is {@link EmberMulticastTableEntry}
     */
    private EmberMulticastTableEntry value;

    /**
     * Response and Handler constructor
     */
    public EzspGetMulticastTableEntryResponse(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        status = deserializer.deserializeEmberStatus();
        value = deserializer.deserializeEmberMulticastTableEntry();
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
     * The contents of the multicast entry.
     * <p>
     * EZSP type is <i>EmberMulticastTableEntry</i> - Java type is {@link EmberMulticastTableEntry}
     *
     * @return the current value as {@link EmberMulticastTableEntry}
     */
    public EmberMulticastTableEntry getValue() {
        return value;
    }

    /**
     * The contents of the multicast entry.
     *
     * @param value the value to set as {@link EmberMulticastTableEntry}
     */
    public void setValue(EmberMulticastTableEntry value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(112);
        builder.append("EzspGetMulticastTableEntryResponse [networkId=");
        builder.append(networkId);
        builder.append(", status=");
        builder.append(status);
        builder.append(", value=");
        builder.append(value);
        builder.append(']');
        return builder.toString();
    }
}
