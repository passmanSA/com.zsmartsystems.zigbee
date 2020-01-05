/**
 * Copyright (c) 2016-2020 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.IeeeAddress;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;

/**
 * Class to implement the Ember EZSP command <b>pollHandler</b>.
 * <p>
 * Indicates that the local node received a data poll from a child.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspPollHandler extends EzspFrameResponse {
    public static final int FRAME_ID = 0x44;

    /**
     * The EUI64 of the sender.
     * <p>
     * EZSP type is <i>EmberEUI64</i> - Java type is {@link IeeeAddress}
     */
    private IeeeAddress senderEui64;

    /**
     * Response and Handler constructor
     */
    public EzspPollHandler(int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(inputBuffer);

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
        final StringBuilder builder = new StringBuilder(43);
        builder.append("EzspPollHandler [senderEui64=");
        builder.append(senderEui64);
        builder.append(']');
        return builder.toString();
    }
}
