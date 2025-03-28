/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberStatus;

/**
 * Class to implement the Ember EZSP command <b>setAddressTableRemoteEui64</b>.
 * <p>
 * Sets the EUI64 of an address table entry. This function will also check other address table
 * entries, the child table and the neighbor table to see if the node ID for the given EUI64 is
 * already known. If known then this function will also set node ID. If not known it will set the
 * node ID to EMBER_UNKNOWN_NODE_ID.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspSetAddressTableRemoteEui64Response extends EzspFrameResponse {
    public static final int FRAME_ID = 0x5C;

    /**
     * EMBER_SUCCESS if the EUI64 was successfully set, and
     * EMBER_ADDRESS_TABLE_ENTRY_IS_ACTIVE otherwise.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     */
    private EmberStatus status;

    /**
     * Response and Handler constructor
     */
    public EzspSetAddressTableRemoteEui64Response(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        status = deserializer.deserializeEmberStatus();
    }

    /**
     * EMBER_SUCCESS if the EUI64 was successfully set, and
     * EMBER_ADDRESS_TABLE_ENTRY_IS_ACTIVE otherwise.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     *
     * @return the current status as {@link EmberStatus}
     */
    public EmberStatus getStatus() {
        return status;
    }

    /**
     * EMBER_SUCCESS if the EUI64 was successfully set, and
     * EMBER_ADDRESS_TABLE_ENTRY_IS_ACTIVE otherwise.
     *
     * @param status the status to set as {@link EmberStatus}
     */
    public void setStatus(EmberStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(91);
        builder.append("EzspSetAddressTableRemoteEui64Response [networkId=");
        builder.append(networkId);
        builder.append(", status=");
        builder.append(status);
        builder.append(']');
        return builder.toString();
    }
}
