/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;

/**
 * Class to implement the Ember EZSP command <b>addressTableEntryIsActive</b>.
 * <p>
 * Indicates whether any messages are currently being sent using this address table entry.
 * Note that this function does not indicate whether the address table entry is unused. To
 * determine whether an address table entry is unused, check the remote node ID. The remote node
 * ID will have the value EMBER_TABLE_ENTRY_UNUSED_NODE_ID when the address table entry is
 * not in use.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspAddressTableEntryIsActiveResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x5B;

    /**
     * True if the address table entry is active, false otherwise.
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     */
    private boolean active;

    /**
     * Response and Handler constructor
     */
    public EzspAddressTableEntryIsActiveResponse(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        active = deserializer.deserializeBool();
    }

    /**
     * True if the address table entry is active, false otherwise.
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     *
     * @return the current active as {@link boolean}
     */
    public boolean getActive() {
        return active;
    }

    /**
     * True if the address table entry is active, false otherwise.
     *
     * @param active the active to set as {@link boolean}
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(90);
        builder.append("EzspAddressTableEntryIsActiveResponse [networkId=");
        builder.append(networkId);
        builder.append(", active=");
        builder.append(active);
        builder.append(']');
        return builder.toString();
    }
}
