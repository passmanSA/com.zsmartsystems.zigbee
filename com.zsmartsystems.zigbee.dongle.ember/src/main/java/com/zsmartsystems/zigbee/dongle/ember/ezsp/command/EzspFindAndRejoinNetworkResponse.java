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
 * Class to implement the Ember EZSP command <b>findAndRejoinNetwork</b>.
 * <p>
 * The application may call this function when contact with the network has been lost. The most
 * common usage case is when an end device can no longer communicate with its parent and wishes to
 * find a new one. Another case is when a device has missed a Network Key update and no longer has
 * the current Network Key. The stack will call ezspStackStatusHandler to indicate that the
 * network is down, then try to re-establish contact with the network by performing an active
 * scan, choosing a network with matching extended pan id, and sending a ZigBee network rejoin
 * request. A sec- ond call to the ezspStackStatusHandler callback indicates either the
 * success or the failure of the attempt. The process takes approximately 150 milliseconds per
 * channel to complete. This call replaces the emberMobileNodeHasMoved API from EmberZNet
 * 2.x, which used MAC association and consequently took half a second longer to complete.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspFindAndRejoinNetworkResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x21;

    /**
     * An EmberStatus value indicating success or the reason for failure.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     */
    private EmberStatus status;

    /**
     * Response and Handler constructor
     */
    public EzspFindAndRejoinNetworkResponse(int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(inputBuffer);

        // Deserialize the fields
        status = deserializer.deserializeEmberStatus();
    }

    /**
     * An EmberStatus value indicating success or the reason for failure.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     *
     * @return the current status as {@link EmberStatus}
     */
    public EmberStatus getStatus() {
        return status;
    }

    /**
     * An EmberStatus value indicating success or the reason for failure.
     *
     * @param status the status to set as {@link EmberStatus}
     */
    public void setStatus(EmberStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(60);
        builder.append("EzspFindAndRejoinNetworkResponse [status=");
        builder.append(status);
        builder.append(']');
        return builder.toString();
    }
}
