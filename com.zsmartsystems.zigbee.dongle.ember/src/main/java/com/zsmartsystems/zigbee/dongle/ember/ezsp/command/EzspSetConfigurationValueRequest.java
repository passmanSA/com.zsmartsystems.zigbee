/**
 * Copyright (c) 2016-2023 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameRequest;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EzspConfigId;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember EZSP command <b>setConfigurationValue</b>.
 * <p>
 * Writes a configuration value to the NCP. Configuration values can be modified by the Host
 * after the NCP has reset. Once the status of the stack changes to EMBER_NETWORK_UP,
 * configuration values can no longer be modified and this command will respond with
 * EZSP_ERROR_INVALID_CALL.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspSetConfigurationValueRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0x53;

    /**
     * Identifies which configuration value to change.
     * <p>
     * EZSP type is <i>EzspConfigId</i> - Java type is {@link EzspConfigId}
     */
    private EzspConfigId configId;

    /**
     * The new configuration value.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     */
    private int value;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspSetConfigurationValueRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * Identifies which configuration value to change.
     * <p>
     * EZSP type is <i>EzspConfigId</i> - Java type is {@link EzspConfigId}
     *
     * @return the current configId as {@link EzspConfigId}
     */
    public EzspConfigId getConfigId() {
        return configId;
    }

    /**
     * Identifies which configuration value to change.
     *
     * @param configId the configId to set as {@link EzspConfigId}
     */
    public void setConfigId(EzspConfigId configId) {
        this.configId = configId;
    }

    /**
     * The new configuration value.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     *
     * @return the current value as {@link int}
     */
    public int getValue() {
        return value;
    }

    /**
     * The new configuration value.
     *
     * @param value the value to set as {@link int}
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int[] serialize(int ezspVersion) {
        // Serialize the header
        serializeHeader(ezspVersion, serializer);

        // Serialize the fields
        serializer.serializeEzspConfigId(configId);
        serializer.serializeUInt16(value);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(110);
        builder.append("EzspSetConfigurationValueRequest [networkId=");
        builder.append(networkId);
        builder.append(", configId=");
        builder.append(configId);
        builder.append(", value=");
        builder.append(value);
        builder.append(']');
        return builder.toString();
    }
}
