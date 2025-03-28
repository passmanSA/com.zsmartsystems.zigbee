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
 * Class to implement the Ember EZSP command <b>requestLinkKey</b>.
 * <p>
 * A function to request a Link Key from the Trust Center with another device on the Network
 * (which could be the Trust Center). A Link Key with the Trust Center is possible but the
 * requesting device cannot be the Trust Center. Link Keys are optional in ZigBee Standard
 * Security and thus the stack cannot know whether the other device supports them. If
 * EMBER_REQUEST_KEY_TIMEOUT is non-zero on the Trust Center and the partner device is not the
 * Trust Center, both devices must request keys with their partner device within the time
 * period. The Trust Center only supports one outstanding key request at a time and therefore
 * will ignore other requests. If the timeout is zero then the Trust Center will immediately
 * respond and not wait for the second request. The Trust Center will always immediately
 * respond to requests for a Link Key with it. Sleepy devices should poll at a higher rate until a
 * response is received or the request times out. The success or failure of the request is
 * returned via ezspZigbeeKeyEstablishmentHandler(...).
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspRequestLinkKeyResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x14;

    /**
     * The success or failure of sending the request. This is not the final result of the attempt.
     * ezspZigbeeKeyEstablishmentHandler(...) will return that.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     */
    private EmberStatus status;

    /**
     * Response and Handler constructor
     */
    public EzspRequestLinkKeyResponse(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        status = deserializer.deserializeEmberStatus();
    }

    /**
     * The success or failure of sending the request. This is not the final result of the attempt.
     * ezspZigbeeKeyEstablishmentHandler(...) will return that.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     *
     * @return the current status as {@link EmberStatus}
     */
    public EmberStatus getStatus() {
        return status;
    }

    /**
     * The success or failure of sending the request. This is not the final result of the attempt.
     * ezspZigbeeKeyEstablishmentHandler(...) will return that.
     *
     * @param status the status to set as {@link EmberStatus}
     */
    public void setStatus(EmberStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(79);
        builder.append("EzspRequestLinkKeyResponse [networkId=");
        builder.append(networkId);
        builder.append(", status=");
        builder.append(status);
        builder.append(']');
        return builder.toString();
    }
}
