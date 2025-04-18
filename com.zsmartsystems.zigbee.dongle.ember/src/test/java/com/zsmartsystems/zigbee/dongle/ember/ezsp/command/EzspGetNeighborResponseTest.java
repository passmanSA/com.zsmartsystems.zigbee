/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.zsmartsystems.zigbee.IeeeAddress;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrame;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameTest;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.command.EzspGetNeighborResponse;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberNeighborTableEntry;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberStatus;

/**
 *
 * @author Chris Jackson
 *
 */
public class EzspGetNeighborResponseTest extends EzspFrameTest {
    @Test
    public void testVersion() {
        EzspGetNeighborResponse response = new EzspGetNeighborResponse(4,
                getPacketData("29 80 79 00 9E 72 FF 01 01 03 CC 43 6B 05 00 6F 0D 00"));
        System.out.println(response);

        assertEquals(true, response.isResponse());
        assertEquals(EzspGetNeighborResponse.FRAME_ID, response.getFrameId());
        assertEquals(EmberStatus.EMBER_SUCCESS, response.getStatus());
        EmberNeighborTableEntry neighbor = response.getValue();
        assertNotNull(neighbor);
        assertEquals(255, neighbor.getAverageLqi());
        assertEquals(1, neighbor.getInCost());
        assertEquals(1, neighbor.getOutCost());
        assertEquals(29342, neighbor.getShortId());
        assertEquals(29342, neighbor.getShortId());
        assertEquals(new IeeeAddress("000D6F00056B43CC"), neighbor.getLongId());
        assertEquals(3, neighbor.getAge());
    }
}
