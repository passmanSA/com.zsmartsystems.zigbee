/**
 * Copyright (c) 2016-2020 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.telegesis.internal.protocol;


/**
 * Class to implement the Telegesis command <b>Become Trust Centre</b>.
 * <p>
 * Local Device takes over the Trust Centre. Can only be used if no other device in the network is
 * Trust Centre (i.e. the network has been started in distributed Trust Centre mode). Notes.
 * Can only be used if Network has been started in distributed Trust Centre mode (bit 9 of S0A
 * set). AT+BECOMETC causes the network key to be updated.
 * <p>
 * This class provides methods for processing Telegesis AT API commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class TelegesisBecomeTrustCentreCommand extends TelegesisFrame implements TelegesisCommand {
    @Override
    public int[] serialize() {
        // Serialize the command fields
        serializeCommand("AT+BECOMETC");

        return getPayload();
    }

    @Override
    public boolean deserialize(int[] data) {
        // Handle standard status responses (ie. OK / ERROR)
        if (handleIncomingStatus(data)) {
            return true;
        }

        initialiseDeserializer(data);


        return false;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(123);
        builder.append("TelegesisBecomeTrustCentreCommand [");
        if (status != null) {
            builder.append("status=");
            builder.append(status);
        }
        builder.append(']');
        return builder.toString();
    }
}
