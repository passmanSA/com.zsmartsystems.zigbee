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
public class EzspSetConfigurationValueResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x53;

    /**
     * EZSP_SUCCESS if the configuration value was changed, EZSP_ERROR_OUT_OF_MEMORY if the new
     * value exceeded the available memory, EZSP_ERROR_INVALID_VALUE if the new value was out of
     * bounds, EZSP_ERROR_INVALID_ID if the NCP does not recognize configId,
     * EZSP_ERROR_INVALID_CALL if configuration values can no longer be modified.
     * <p>
     * EZSP type is <i>EzspStatus</i> - Java type is {@link EzspStatus}
     */
    private EzspStatus status;

    /**
     * Response and Handler constructor
     */
    public EzspSetConfigurationValueResponse(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        status = deserializer.deserializeEzspStatus();
    }

    /**
     * EZSP_SUCCESS if the configuration value was changed, EZSP_ERROR_OUT_OF_MEMORY if the new
     * value exceeded the available memory, EZSP_ERROR_INVALID_VALUE if the new value was out of
     * bounds, EZSP_ERROR_INVALID_ID if the NCP does not recognize configId,
     * EZSP_ERROR_INVALID_CALL if configuration values can no longer be modified.
     * <p>
     * EZSP type is <i>EzspStatus</i> - Java type is {@link EzspStatus}
     *
     * @return the current status as {@link EzspStatus}
     */
    public EzspStatus getStatus() {
        return status;
    }

    /**
     * EZSP_SUCCESS if the configuration value was changed, EZSP_ERROR_OUT_OF_MEMORY if the new
     * value exceeded the available memory, EZSP_ERROR_INVALID_VALUE if the new value was out of
     * bounds, EZSP_ERROR_INVALID_ID if the NCP does not recognize configId,
     * EZSP_ERROR_INVALID_CALL if configuration values can no longer be modified.
     *
     * @param status the status to set as {@link EzspStatus}
     */
    public void setStatus(EzspStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(86);
        builder.append("EzspSetConfigurationValueResponse [networkId=");
        builder.append(networkId);
        builder.append(", status=");
        builder.append(status);
        builder.append(']');
        return builder.toString();
    }
}
