/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.zsmartsystems.zigbee.IeeeAddress;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrame;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameTest;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.command.EzspChildJoinHandler;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberNodeType;

/**
 * @author Chris Jackson
 */
public class EzspChildJoinHandlerTest extends EzspFrameTest {

    @Test
    public void testReceive1() {
        EzspChildJoinHandler handler = new EzspChildJoinHandler(4,
                getPacketData("0B 90 23 00 00 95 87 F9 41 F6 02 00 4B 12 00 04"));
        System.out.println(handler);

        assertTrue(handler.isResponse());
        assertEquals(0, handler.getIndex());
        assertEquals(34709, handler.getChildId());
        assertEquals(false, handler.getJoining());
        assertEquals(new IeeeAddress("00124B0002F641F9"), handler.getChildEui64());
        assertEquals(EmberNodeType.EMBER_SLEEPY_END_DEVICE, handler.getChildType());
    }
}
