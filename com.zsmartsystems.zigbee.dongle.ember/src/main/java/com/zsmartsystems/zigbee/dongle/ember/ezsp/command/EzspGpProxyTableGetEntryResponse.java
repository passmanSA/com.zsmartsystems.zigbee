/**
 * Copyright (c) 2016-2023 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberGpProxyTableEntry;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberStatus;

/**
 * Class to implement the Ember EZSP command <b>gpProxyTableGetEntry</b>.
 * <p>
 * Retrieves the proxy table entry stored at the passed index.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspGpProxyTableGetEntryResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0xC8;

    /**
     * An EmberStatus value indicating success or the reason for failure.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     */
    private EmberStatus status;

    /**
     * An EmberGpProxyTableEntry struct containing a copy of the requested proxy entry.
     * <p>
     * EZSP type is <i>EmberGpProxyTableEntry</i> - Java type is {@link EmberGpProxyTableEntry}
     */
    private EmberGpProxyTableEntry entry;

    /**
     * Response and Handler constructor
     */
    public EzspGpProxyTableGetEntryResponse(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        status = deserializer.deserializeEmberStatus();
        entry = deserializer.deserializeEmberGpProxyTableEntry();
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
     * An EmberGpProxyTableEntry struct containing a copy of the requested proxy entry.
     * <p>
     * EZSP type is <i>EmberGpProxyTableEntry</i> - Java type is {@link EmberGpProxyTableEntry}
     *
     * @return the current entry as {@link EmberGpProxyTableEntry}
     */
    public EmberGpProxyTableEntry getEntry() {
        return entry;
    }

    /**
     * An EmberGpProxyTableEntry struct containing a copy of the requested proxy entry.
     *
     * @param entry the entry to set as {@link EmberGpProxyTableEntry}
     */
    public void setEntry(EmberGpProxyTableEntry entry) {
        this.entry = entry;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(110);
        builder.append("EzspGpProxyTableGetEntryResponse [networkId=");
        builder.append(networkId);
        builder.append(", status=");
        builder.append(status);
        builder.append(", entry=");
        builder.append(entry);
        builder.append(']');
        return builder.toString();
    }
}
