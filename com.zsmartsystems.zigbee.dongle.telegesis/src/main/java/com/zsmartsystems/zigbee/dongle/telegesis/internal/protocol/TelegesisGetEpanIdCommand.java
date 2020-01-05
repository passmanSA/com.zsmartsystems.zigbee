/**
 * Copyright (c) 2016-2020 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.telegesis.internal.protocol;

import com.zsmartsystems.zigbee.ExtendedPanId;

/**
 * Class to implement the Telegesis command <b>Get EPan ID</b>.
 * <p>
 * Gets the preferred Extended PAN ID
 * <p>
 * This class provides methods for processing Telegesis AT API commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class TelegesisGetEpanIdCommand extends TelegesisFrame implements TelegesisCommand {
    /**
     * Response field
     */
    private ExtendedPanId epanId;

    /**
     *
     * @return the epanId as {@link ExtendedPanId}
     */
    public ExtendedPanId getEpanId() {
        return epanId;
    }

    @Override
    public int[] serialize() {
        // Serialize the command fields
        serializeCommand("ATS03?");

        return getPayload();
    }

    @Override
    public boolean deserialize(int[] data) {
        // Handle standard status responses (ie. OK / ERROR)
        if (handleIncomingStatus(data)) {
            return true;
        }

        initialiseDeserializer(data);

        // Deserialize the fields for the response

        // Deserialize field "epan ID"
        epanId = deserializeExtendedPanId();

        return false;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(205);
        builder.append("TelegesisGetEpanIdCommand [epanId=");
        builder.append(epanId);
        if (status != null) {
            builder.append(", status=");
            builder.append(status);
        }
        builder.append(']');
        return builder.toString();
    }
}
