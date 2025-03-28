/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.zsmartsystems.zigbee.TestUtilities;
import com.zsmartsystems.zigbee.ZigBeeChannelMask;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrame;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameTest;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EzspNetworkScanType;

/**
 *
 * @author Chris Jackson
 *
 */
public class EzspStartScanRequestTest extends EzspFrameTest {
    @Test
    public void testVersion() throws Exception {
        EzspStartScanRequest request = new EzspStartScanRequest();
        TestUtilities.setField(EzspFrame.class, request, "sequenceNumber", 3);
        request.setScanType(EzspNetworkScanType.EZSP_ENERGY_SCAN);
        request.setChannelMask(ZigBeeChannelMask.CHANNEL_MASK_2GHZ);
        request.setDuration(1);
        System.out.println(request);

        assertTrue(Arrays.equals(getPacketData("03 00 1A 00 00 F8 FF 07 01"), request.serialize(4)));
    }
}
