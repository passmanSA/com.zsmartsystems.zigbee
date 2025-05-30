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

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrame;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameTest;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.command.EzspGetRouteTableEntryResponse;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberRouteTableEntry;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberStatus;

/**
 *
 * @author Chris Jackson
 *
 */
public class EzspGetRouteTableEntryResponseTest extends EzspFrameTest {
    @Test
    public void testVersion() {
        EzspGetRouteTableEntryResponse response = new EzspGetRouteTableEntryResponse(4,
                getPacketData("28 80 7B 00 FF FF 00 00 03 00 00 00"));
        System.out.println(response);

        assertEquals(true, response.isResponse());
        assertEquals(EzspGetRouteTableEntryResponse.FRAME_ID, response.getFrameId());
        assertEquals(EmberStatus.EMBER_SUCCESS, response.getStatus());
        EmberRouteTableEntry route = response.getValue();
        assertNotNull(route);
        assertEquals(3, route.getStatus());
        assertEquals(65535, route.getDestination());
    }
}
