/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.telegesis.internal.protocol;

import com.zsmartsystems.zigbee.ExtendedPanId;

/**
 * Class to implement the Telegesis command <b>Network Joined</b>.
 * <p>
 * Local Node has joined PAN with given parameters
 * <p>
 * This class provides methods for processing Telegesis AT API commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class TelegesisNetworkJoinedEvent extends TelegesisFrame implements TelegesisEvent {
    /**
     * JPAN response field
     */
    private Integer channel;

    /**
     * JPAN response field
     */
    private Integer panId;

    /**
     * JPAN response field
     */
    private ExtendedPanId epanId;

    /**
     *
     * @return the channel as {@link Integer}
     */
    public Integer getChannel() {
        return channel;
    }

    /**
     *
     * @return the panId as {@link Integer}
     */
    public Integer getPanId() {
        return panId;
    }

    /**
     *
     * @return the epanId as {@link ExtendedPanId}
     */
    public ExtendedPanId getEpanId() {
        return epanId;
    }


    @Override
    public void deserialize(int[] data) {
        initialiseDeserializer(data);

        // Deserialize the fields for the "JPAN" response
        if (testPrompt(data, "JPAN:")) {
            setDeserializer(5);

            // Deserialize field "channel"
            channel = deserializeInteger();
            stepDeserializer();

            // Deserialize field "Pan ID"
            panId = deserializeInt16();
            stepDeserializer();

            // Deserialize field "EPan ID"
            epanId = deserializeExtendedPanId();
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(387);
        builder.append("TelegesisNetworkJoinedEvent [channel=");
        builder.append(channel);
        builder.append(", panId=");
        builder.append(panId);
        builder.append(", epanId=");
        builder.append(epanId);
        builder.append(']');
        return builder.toString();
    }
}
