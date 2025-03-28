/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.internal.ash;

import java.util.Arrays;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameRequest;

/**
 * ASH Data Frame
 *
 * @author Chris Jackson
 *
 */
public class AshFrameData extends AshFrame {
    /**
     * Constructor to create an ASH Data frame for sending.
     *
     * @param ezspRequestFrame the {@link EzspFrameRequest} to send
     */
    public AshFrameData(int ezspVersion, EzspFrameRequest ezspRequestFrame) {
        frameType = FrameType.DATA;
        dataBuffer = ezspRequestFrame.serialize(ezspVersion);
    }

    /**
     * Constructor taking an incoming data buffer
     *
     * @param frameBuffer the incoming data buffer
     */
    public AshFrameData(int[] frameBuffer) {
        frameType = FrameType.DATA;

        processHeader(frameBuffer);
        dataBuffer = Arrays.copyOfRange(frameBuffer, 1, frameBuffer.length - 2);
    }

    public void setReTx() {
        reTx = true;
    }

    public boolean getReTx() {
        return reTx;
    }

    public int[] getDataBuffer() {
        return dataBuffer;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(96);
        result.append("AshFrameData [frmNum=");
        result.append(frmNum);
        result.append(", ackNum=");
        result.append(ackNum);
        result.append(", reTx=");
        result.append(reTx);
        result.append(", data=");

        for (int i = 0; i < dataBuffer.length; i++) {
            if (i != 0) {
                result.append(' ');
            }
            result.append(String.format("%02X", dataBuffer[i]));
        }
        result.append(']');

        return result.toString();
    }
}
