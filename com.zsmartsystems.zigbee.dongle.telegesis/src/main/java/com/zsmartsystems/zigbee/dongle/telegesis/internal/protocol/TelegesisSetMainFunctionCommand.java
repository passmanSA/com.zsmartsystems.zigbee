/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.telegesis.internal.protocol;


/**
 * Class to implement the Telegesis command <b>Set Main Function</b>.
 * <p>
 * Sets the Main Function configuration
 * <p>
 * This class provides methods for processing Telegesis AT API commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class TelegesisSetMainFunctionCommand extends TelegesisFrame implements TelegesisCommand {
    /**
     * Command field
     */
    private Integer configuration;

    /**
     * Command field
     */
    private String password;

    /**
     *
     * @param configuration the configuration to set as {@link Integer}
     */
    public void setConfiguration(Integer configuration) {
        this.configuration = configuration;
    }

    /**
     *
     * @param password the password to set as {@link String}
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int[] serialize() {
        // Serialize the command fields
        serializeCommand("ATS0A=");
        serializeInt16(configuration);
        serializeDelimiter();
        serializeString(password);

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
        final StringBuilder builder = new StringBuilder(301);
        // First present the command parameters...
        // Then the responses later if they are available
        builder.append("TelegesisSetMainFunctionCommand [configuration=");
        builder.append(configuration);
        builder.append(", password=");
        builder.append(password);
        if (status != null) {
            builder.append(", status=");
            builder.append(status);
        }
        builder.append(']');
        return builder.toString();
    }
}
