/**
 * Copyright (c) 2016-2020 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberStatus;

/**
 * Class to implement the Ember EZSP command <b>startScan</b>.
 * <p>
 * This function will start a scan.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspStartScanResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x1A;

    /**
     * EMBER_SUCCESS signals that the scan successfully started. Possible error responses and
     * their meanings: EMBER_MAC_SCANNING, we are already scanning;
     * EMBER_MAC_JOINED_NETWORK, we are currently joined to a network and cannot begin a scan;
     * EMBER_MAC_BAD_SCAN_DURATION, we have set a duration value that is not 0..14 inclusive;
     * EMBER_MAC_INCORRECT_SCAN_TYPE, we have requested an undefined scanning type;
     * EMBER_MAC_INVALID_CHANNEL_MASK, our channel mask did not specify any valid channels.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     */
    private EmberStatus status;

    /**
     * Response and Handler constructor
     */
    public EzspStartScanResponse(int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(inputBuffer);

        // Deserialize the fields
        status = deserializer.deserializeEmberStatus();
    }

    /**
     * EMBER_SUCCESS signals that the scan successfully started. Possible error responses and
     * their meanings: EMBER_MAC_SCANNING, we are already scanning;
     * EMBER_MAC_JOINED_NETWORK, we are currently joined to a network and cannot begin a scan;
     * EMBER_MAC_BAD_SCAN_DURATION, we have set a duration value that is not 0..14 inclusive;
     * EMBER_MAC_INCORRECT_SCAN_TYPE, we have requested an undefined scanning type;
     * EMBER_MAC_INVALID_CHANNEL_MASK, our channel mask did not specify any valid channels.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     *
     * @return the current status as {@link EmberStatus}
     */
    public EmberStatus getStatus() {
        return status;
    }

    /**
     * EMBER_SUCCESS signals that the scan successfully started. Possible error responses and
     * their meanings: EMBER_MAC_SCANNING, we are already scanning;
     * EMBER_MAC_JOINED_NETWORK, we are currently joined to a network and cannot begin a scan;
     * EMBER_MAC_BAD_SCAN_DURATION, we have set a duration value that is not 0..14 inclusive;
     * EMBER_MAC_INCORRECT_SCAN_TYPE, we have requested an undefined scanning type;
     * EMBER_MAC_INVALID_CHANNEL_MASK, our channel mask did not specify any valid channels.
     *
     * @param status the status to set as {@link EmberStatus}
     */
    public void setStatus(EmberStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(49);
        builder.append("EzspStartScanResponse [status=");
        builder.append(status);
        builder.append(']');
        return builder.toString();
    }
}
