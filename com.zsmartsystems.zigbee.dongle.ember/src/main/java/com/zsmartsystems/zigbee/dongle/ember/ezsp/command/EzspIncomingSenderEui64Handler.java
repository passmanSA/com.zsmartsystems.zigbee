/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.IeeeAddress;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;

/**
 * Class to implement the Ember EZSP command <b>incomingSenderEui64Handler</b>.
 * <p>
 * A callback indicating a message has been received containing the EUI64 of the sender. This
 * callback is called immediately before the incomingMessageHandler callback. It is not
 * called if the incoming message did not contain the EUI64 of the sender.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspIncomingSenderEui64Handler extends EzspFrameResponse {
    public static final int FRAME_ID = 0x62;

    /**
     * The EUI64 of the sender.
     * <p>
     * EZSP type is <i>EmberEUI64</i> - Java type is {@link IeeeAddress}
     */
    private IeeeAddress senderEui64;

    /**
     * Response and Handler constructor
     */
    public EzspIncomingSenderEui64Handler(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        senderEui64 = deserializer.deserializeEmberEui64();
    }

    /**
     * The EUI64 of the sender.
     * <p>
     * EZSP type is <i>EmberEUI64</i> - Java type is {@link IeeeAddress}
     *
     * @return the current senderEui64 as {@link IeeeAddress}
     */
    public IeeeAddress getSenderEui64() {
        return senderEui64;
    }

    /**
     * The EUI64 of the sender.
     *
     * @param senderEui64 the senderEui64 to set as {@link IeeeAddress}
     */
    public void setSenderEui64(IeeeAddress senderEui64) {
        this.senderEui64 = senderEui64;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(83);
        builder.append("EzspIncomingSenderEui64Handler [networkId=");
        builder.append(networkId);
        builder.append(", senderEui64=");
        builder.append(senderEui64);
        builder.append(']');
        return builder.toString();
    }
}
