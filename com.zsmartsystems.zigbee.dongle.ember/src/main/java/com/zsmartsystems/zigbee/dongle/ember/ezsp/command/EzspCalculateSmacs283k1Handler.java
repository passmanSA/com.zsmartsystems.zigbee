/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberSmacData;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberStatus;

/**
 * Class to implement the Ember EZSP command <b>calculateSmacs283k1Handler</b>.
 * <p>
 * A callback to indicate that the NCP has finished calculating the Secure Message
 * Authentication Codes (SMAC) for both the initiator and responder for the CBKE 283k1
 * Library. The associated link key is kept in temporary storage until the host tells the NCP to
 * store or discard the key via emberClearTemporaryDataMaybeStoreLinkKey().
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspCalculateSmacs283k1Handler extends EzspFrameResponse {
    public static final int FRAME_ID = 0xEB;

    /**
     * The result of the CBKE operation.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     */
    private EmberStatus status;

    /**
     * The calculated value of the initiators SMAC.
     * <p>
     * EZSP type is <i>EmberSmacData</i> - Java type is {@link EmberSmacData}
     */
    private EmberSmacData initiatorSmac;

    /**
     * The calculated value of the responders SMAC.
     * <p>
     * EZSP type is <i>EmberSmacData</i> - Java type is {@link EmberSmacData}
     */
    private EmberSmacData responderSmac;

    /**
     * Response and Handler constructor
     */
    public EzspCalculateSmacs283k1Handler(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        status = deserializer.deserializeEmberStatus();
        initiatorSmac = deserializer.deserializeEmberSmacData();
        responderSmac = deserializer.deserializeEmberSmacData();
    }

    /**
     * The result of the CBKE operation.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     *
     * @return the current status as {@link EmberStatus}
     */
    public EmberStatus getStatus() {
        return status;
    }

    /**
     * The result of the CBKE operation.
     *
     * @param status the status to set as {@link EmberStatus}
     */
    public void setStatus(EmberStatus status) {
        this.status = status;
    }

    /**
     * The calculated value of the initiators SMAC.
     * <p>
     * EZSP type is <i>EmberSmacData</i> - Java type is {@link EmberSmacData}
     *
     * @return the current initiatorSmac as {@link EmberSmacData}
     */
    public EmberSmacData getInitiatorSmac() {
        return initiatorSmac;
    }

    /**
     * The calculated value of the initiators SMAC.
     *
     * @param initiatorSmac the initiatorSmac to set as {@link EmberSmacData}
     */
    public void setInitiatorSmac(EmberSmacData initiatorSmac) {
        this.initiatorSmac = initiatorSmac;
    }

    /**
     * The calculated value of the responders SMAC.
     * <p>
     * EZSP type is <i>EmberSmacData</i> - Java type is {@link EmberSmacData}
     *
     * @return the current responderSmac as {@link EmberSmacData}
     */
    public EmberSmacData getResponderSmac() {
        return responderSmac;
    }

    /**
     * The calculated value of the responders SMAC.
     *
     * @param responderSmac the responderSmac to set as {@link EmberSmacData}
     */
    public void setResponderSmac(EmberSmacData responderSmac) {
        this.responderSmac = responderSmac;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(133);
        builder.append("EzspCalculateSmacs283k1Handler [networkId=");
        builder.append(networkId);
        builder.append(", status=");
        builder.append(status);
        builder.append(", initiatorSmac=");
        builder.append(initiatorSmac);
        builder.append(", responderSmac=");
        builder.append(responderSmac);
        builder.append(']');
        return builder.toString();
    }
}
