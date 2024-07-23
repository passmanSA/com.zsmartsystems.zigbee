/**
 * Copyright (c) 2016-2023 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberZigbeeNetwork;

/**
 * Class to implement the Ember EZSP command <b>networkFoundHandler</b>.
 * <p>
 * Reports that a network was found as a result of a prior call to startScan. Gives the network
 * parameters useful for deciding which network to join.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspNetworkFoundHandler extends EzspFrameResponse {
    public static final int FRAME_ID = 0x1B;

    /**
     * The parameters associated with the network found.
     * <p>
     * EZSP type is <i>EmberZigbeeNetwork</i> - Java type is {@link EmberZigbeeNetwork}
     */
    private EmberZigbeeNetwork networkFound;

    /**
     * The link quality from the node that generated this beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int lastHopLqi;

    /**
     * The energy level (in units of dBm) observed during the reception.
     * <p>
     * EZSP type is <i>int8s</i> - Java type is {@link int}
     */
    private int lastHopRssi;

    /**
     * Response and Handler constructor
     */
    public EzspNetworkFoundHandler(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        networkFound = deserializer.deserializeEmberZigbeeNetwork();
        lastHopLqi = deserializer.deserializeUInt8();
        lastHopRssi = deserializer.deserializeInt8S();
    }

    /**
     * The parameters associated with the network found.
     * <p>
     * EZSP type is <i>EmberZigbeeNetwork</i> - Java type is {@link EmberZigbeeNetwork}
     *
     * @return the current networkFound as {@link EmberZigbeeNetwork}
     */
    public EmberZigbeeNetwork getNetworkFound() {
        return networkFound;
    }

    /**
     * The parameters associated with the network found.
     *
     * @param networkFound the networkFound to set as {@link EmberZigbeeNetwork}
     */
    public void setNetworkFound(EmberZigbeeNetwork networkFound) {
        this.networkFound = networkFound;
    }

    /**
     * The link quality from the node that generated this beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current lastHopLqi as {@link int}
     */
    public int getLastHopLqi() {
        return lastHopLqi;
    }

    /**
     * The link quality from the node that generated this beacon.
     *
     * @param lastHopLqi the lastHopLqi to set as {@link int}
     */
    public void setLastHopLqi(int lastHopLqi) {
        this.lastHopLqi = lastHopLqi;
    }

    /**
     * The energy level (in units of dBm) observed during the reception.
     * <p>
     * EZSP type is <i>int8s</i> - Java type is {@link int}
     *
     * @return the current lastHopRssi as {@link int}
     */
    public int getLastHopRssi() {
        return lastHopRssi;
    }

    /**
     * The energy level (in units of dBm) observed during the reception.
     *
     * @param lastHopRssi the lastHopRssi to set as {@link int}
     */
    public void setLastHopRssi(int lastHopRssi) {
        this.lastHopRssi = lastHopRssi;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(126);
        builder.append("EzspNetworkFoundHandler [networkId=");
        builder.append(networkId);
        builder.append(", networkFound=");
        builder.append(networkFound);
        builder.append(", lastHopLqi=");
        builder.append(lastHopLqi);
        builder.append(", lastHopRssi=");
        builder.append(lastHopRssi);
        builder.append(']');
        return builder.toString();
    }
}
