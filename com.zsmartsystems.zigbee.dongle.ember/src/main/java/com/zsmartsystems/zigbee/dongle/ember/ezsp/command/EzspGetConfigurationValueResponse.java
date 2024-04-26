/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EzspStatus;

/**
 * Class to implement the Ember EZSP command <b>getConfigurationValue</b>.
 * <p>
 * Reads a configuration value from the NCP
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspGetConfigurationValueResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x52;

    /**
     * EZSP_SUCCESS if the value was read successfully, EZSP_ERROR_INVALID_ID if the NCP does not
     * recognize configId.
     * <p>
     * EZSP type is <i>EzspStatus</i> - Java type is {@link EzspStatus}
     */
    private EzspStatus status;

    /**
     * The configuration value.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     */
    private int value;

    /**
     * Response and Handler constructor
     */
    public EzspGetConfigurationValueResponse(int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(inputBuffer);

        // Deserialize the fields
        status = deserializer.deserializeEzspStatus();
        value = deserializer.deserializeUInt16();
    }

    /**
     * EZSP_SUCCESS if the value was read successfully, EZSP_ERROR_INVALID_ID if the NCP does not
     * recognize configId.
     * <p>
     * EZSP type is <i>EzspStatus</i> - Java type is {@link EzspStatus}
     *
     * @return the current status as {@link EzspStatus}
     */
    public EzspStatus getStatus() {
        return status;
    }

    /**
     * EZSP_SUCCESS if the value was read successfully, EZSP_ERROR_INVALID_ID if the NCP does not
     * recognize configId.
     *
     * @param status the status to set as {@link EzspStatus}
     */
    public void setStatus(EzspStatus status) {
        this.status = status;
    }

    /**
     * The configuration value.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     *
     * @return the current value as {@link int}
     */
    public int getValue() {
        return value;
    }

    /**
     * The configuration value.
     *
     * @param value the value to set as {@link int}
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(111);
        builder.append("EzspGetConfigurationValueResponse [networkId=");
        builder.append(networkId);
        builder.append(", status=");
        builder.append(status);
        builder.append(", value=");
        builder.append(value);
        builder.append(']');
        return builder.toString();
    }
}
