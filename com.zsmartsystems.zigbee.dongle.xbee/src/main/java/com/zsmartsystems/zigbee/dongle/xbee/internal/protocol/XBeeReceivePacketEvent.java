/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.xbee.internal.protocol;

import com.zsmartsystems.zigbee.IeeeAddress;
import com.zsmartsystems.zigbee.dongle.xbee.internal.protocol.ReceiveOptions;

/**
 * Class to implement the XBee command <b>Receive Packet</b>.
 * <p>
 * When a device configured with a standard API Rx Indicator (AO = 0) receives an RF data packet,
 * it sends it out the serial interface using this message type.
 * <p>
 * This class provides methods for processing XBee API commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class XBeeReceivePacketEvent extends XBeeFrame implements XBeeEvent {
    /**
     * Response field
     * The sender's 64-bit address. MSB first, LSB last.
     */
    private IeeeAddress ieeeAddress;

    /**
     * Response field
     * The sender's 16-bit address.
     */
    private Integer networkAddress;

    /**
     * Response field
     */
    private ReceiveOptions receiveOptions;

    /**
     * Response field
     * The RF data that the device receives.
     */
    private int[] data;

    /**
     * The sender's 64-bit address. MSB first, LSB last.
     *
     * @return the ieeeAddress as {@link IeeeAddress}
     */
    public IeeeAddress getIeeeAddress() {
        return ieeeAddress;
    }

    /**
     * The sender's 16-bit address.
     *
     * @return the networkAddress as {@link Integer}
     */
    public Integer getNetworkAddress() {
        return networkAddress;
    }

    /**
     *
     * @return the receiveOptions as {@link ReceiveOptions}
     */
    public ReceiveOptions getReceiveOptions() {
        return receiveOptions;
    }

    /**
     * The RF data that the device receives.
     *
     * @return the data as {@link int[]}
     */
    public int[] getData() {
        return data;
    }


    @Override
    public void deserialize(int[] incomingData) {
        initialiseDeserializer(incomingData);

        // Deserialize the fields for the response

        // Deserialize field "Ieee Address"
        ieeeAddress = deserializeIeeeAddress();

        // Deserialize field "Network Address"
        networkAddress = deserializeInt16();

        // Deserialize field "Receive Options"
        receiveOptions = deserializeReceiveOptions();

        // Deserialize field "Data"
        data = deserializeData();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(472);
        builder.append("XBeeReceivePacketEvent [ieeeAddress=");
        builder.append(ieeeAddress);
        builder.append(", networkAddress=");
        builder.append(networkAddress);
        builder.append(", receiveOptions=");
        builder.append(receiveOptions);
        builder.append(", data=");
        if (data == null) {
            builder.append("null");
        } else {
            for (int cnt = 0; cnt < data.length; cnt++) {
                if (cnt > 0) {
                    builder.append(' ');
                }
                builder.append(String.format("%02X", data[cnt]));
            }
        }
        builder.append(']');
        return builder.toString();
    }
}
