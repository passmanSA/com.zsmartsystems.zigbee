/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberNetworkStatus;

/**
 * Class to implement the Ember EZSP command <b>networkState</b>.
 * <p>
 * Returns a value indicating whether the node is joining, joined to, or leaving a network.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspNetworkStateResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x18;

    /**
     * An EmberNetworkStatus value indicating the current join status.
     * <p>
     * EZSP type is <i>EmberNetworkStatus</i> - Java type is {@link EmberNetworkStatus}
     */
    private EmberNetworkStatus status;

    /**
     * Response and Handler constructor
     */
    public EzspNetworkStateResponse(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        status = deserializer.deserializeEmberNetworkStatus();
    }

    /**
     * An EmberNetworkStatus value indicating the current join status.
     * <p>
     * EZSP type is <i>EmberNetworkStatus</i> - Java type is {@link EmberNetworkStatus}
     *
     * @return the current status as {@link EmberNetworkStatus}
     */
    public EmberNetworkStatus getStatus() {
        return status;
    }

    /**
     * An EmberNetworkStatus value indicating the current join status.
     *
     * @param status the status to set as {@link EmberNetworkStatus}
     */
    public void setStatus(EmberNetworkStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(77);
        builder.append("EzspNetworkStateResponse [networkId=");
        builder.append(networkId);
        builder.append(", status=");
        builder.append(status);
        builder.append(']');
        return builder.toString();
    }
}
