/**
 * Copyright (c) 2016-2022 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberPublicKeyData;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberStatus;

/**
 * Class to implement the Ember EZSP command <b>generateCbkeKeysHandler</b>.
 * <p>
 * A callback by the Crypto Engine indicating that a new ephemeral public/private key pair has
 * been generated. The pub- lic/private key pair is stored on the NCP, but only the associated
 * public key is returned to the host. The node's associated certificate is also returned.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspGenerateCbkeKeysHandler extends EzspFrameResponse {
    public static final int FRAME_ID = 0x9E;

    /**
     * The result of the CBKE operation.
     * <p>
     * EZSP type is <i>EmberStatus</i> - Java type is {@link EmberStatus}
     */
    private EmberStatus status;

    /**
     * The generated ephemeral public key.
     * <p>
     * EZSP type is <i>EmberPublicKeyData</i> - Java type is {@link EmberPublicKeyData}
     */
    private EmberPublicKeyData ephemeralPublicKey;

    /**
     * Response and Handler constructor
     */
    public EzspGenerateCbkeKeysHandler(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        status = deserializer.deserializeEmberStatus();
        ephemeralPublicKey = deserializer.deserializeEmberPublicKeyData();
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
     * The generated ephemeral public key.
     * <p>
     * EZSP type is <i>EmberPublicKeyData</i> - Java type is {@link EmberPublicKeyData}
     *
     * @return the current ephemeralPublicKey as {@link EmberPublicKeyData}
     */
    public EmberPublicKeyData getEphemeralPublicKey() {
        return ephemeralPublicKey;
    }

    /**
     * The generated ephemeral public key.
     *
     * @param ephemeralPublicKey the ephemeralPublicKey to set as {@link EmberPublicKeyData}
     */
    public void setEphemeralPublicKey(EmberPublicKeyData ephemeralPublicKey) {
        this.ephemeralPublicKey = ephemeralPublicKey;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(105);
        builder.append("EzspGenerateCbkeKeysHandler [networkId=");
        builder.append(networkId);
        builder.append(", status=");
        builder.append(status);
        builder.append(", ephemeralPublicKey=");
        builder.append(ephemeralPublicKey);
        builder.append(']');
        return builder.toString();
    }
}
