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

import com.zsmartsystems.zigbee.IeeeAddress;
import com.zsmartsystems.zigbee.TestUtilities;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrame;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameTest;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberInitialSecurityBitmask;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberInitialSecurityState;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberKeyData;

/**
 *
 * @author Chris Jackson
 *
 */
public class EzspSetInitialSecurityStateRequestTest extends EzspFrameTest {
    @Test
    public void testSecurityStateRequest() throws Exception {
        EmberKeyData keyData;
        EzspSetInitialSecurityStateRequest request = new EzspSetInitialSecurityStateRequest();
        EmberInitialSecurityState state = new EmberInitialSecurityState();
        keyData = new EmberKeyData();
        keyData.setContents(new int[] { 0xAA, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0xAA });
        state.setNetworkKey(keyData);
        keyData = new EmberKeyData();
        keyData.setContents(new int[] { 0xBB, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0xBB });
        state.setPreconfiguredKey(keyData);
        state.setPreconfiguredTrustCenterEui64(new IeeeAddress("1234567890ABCDEF"));
        state.setNetworkKeySequenceNumber(0);
        state.addBitmask(EmberInitialSecurityBitmask.EMBER_STANDARD_SECURITY_MODE);
        request.setState(state);
        TestUtilities.setField(EzspFrame.class, request, "sequenceNumber", 7);
        System.out.println(request);

        assertTrue(Arrays.equals(getPacketData(
                "07 00 68 00 00 BB 00 00 00 00 00 00 00 00 00 00 00 00 00 00 BB AA 00 00 00 00 00 00 00 00 00 00 00 00 00 00 AA 00 EF CD AB 90 78 56 34 12"),
                request.serialize(4)));
    }
}
