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
 * Class to implement the Ember EZSP command <b>setAddressTableRemoteNodeId</b>.
 * <p>
 * Sets the short ID of an address table entry. Usually the application will not need to set the
 * short ID in the address table. Once the remote EUI64 is set the stack is capable of figuring out
 * the short ID on its own. However, in cases where the application does set the short ID, the
 * application must set the remote EUI64 prior to setting the short ID.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspSetAddressTableRemoteNodeIdResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x5D;

    /**
     * Response and Handler constructor
     */
    public EzspSetAddressTableRemoteNodeIdResponse(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(67);
        builder.append("EzspSetAddressTableRemoteNodeIdResponse [networkId=");
        builder.append(networkId);
        builder.append(']');
        return builder.toString();
    }
}
