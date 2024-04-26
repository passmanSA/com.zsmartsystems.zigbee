/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameRequest;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberCertificateData;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberPublicKeyData;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember EZSP command <b>calculateSmacs</b>.
 * <p>
 * Calculates the SMAC verification keys for both the initiator and responder roles of CBKE
 * using the passed parameters and the stored public/private key pair previously generated
 * with ezspGenerateKeysRetrieveCert(). It also stores the unverified link key data in
 * temporary storage on the NCP until the key establishment is complete.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspCalculateSmacsRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0x9F;

    /**
     * The role of this device in the Key Establishment protocol.
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     */
    private boolean amInitiator;

    /**
     * The key establishment partner's implicit certificate.
     * <p>
     * EZSP type is <i>EmberCertificateData</i> - Java type is {@link EmberCertificateData}
     */
    private EmberCertificateData partnerCertificate;

    /**
     * The key establishment partner's ephemeral public key
     * <p>
     * EZSP type is <i>EmberPublicKeyData</i> - Java type is {@link EmberPublicKeyData}
     */
    private EmberPublicKeyData partnerEphemeralPublicKey;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspCalculateSmacsRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * The role of this device in the Key Establishment protocol.
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     *
     * @return the current amInitiator as {@link boolean}
     */
    public boolean getAmInitiator() {
        return amInitiator;
    }

    /**
     * The role of this device in the Key Establishment protocol.
     *
     * @param amInitiator the amInitiator to set as {@link boolean}
     */
    public void setAmInitiator(boolean amInitiator) {
        this.amInitiator = amInitiator;
    }

    /**
     * The key establishment partner's implicit certificate.
     * <p>
     * EZSP type is <i>EmberCertificateData</i> - Java type is {@link EmberCertificateData}
     *
     * @return the current partnerCertificate as {@link EmberCertificateData}
     */
    public EmberCertificateData getPartnerCertificate() {
        return partnerCertificate;
    }

    /**
     * The key establishment partner's implicit certificate.
     *
     * @param partnerCertificate the partnerCertificate to set as {@link EmberCertificateData}
     */
    public void setPartnerCertificate(EmberCertificateData partnerCertificate) {
        this.partnerCertificate = partnerCertificate;
    }

    /**
     * The key establishment partner's ephemeral public key
     * <p>
     * EZSP type is <i>EmberPublicKeyData</i> - Java type is {@link EmberPublicKeyData}
     *
     * @return the current partnerEphemeralPublicKey as {@link EmberPublicKeyData}
     */
    public EmberPublicKeyData getPartnerEphemeralPublicKey() {
        return partnerEphemeralPublicKey;
    }

    /**
     * The key establishment partner's ephemeral public key
     *
     * @param partnerEphemeralPublicKey the partnerEphemeralPublicKey to set as {@link EmberPublicKeyData}
     */
    public void setPartnerEphemeralPublicKey(EmberPublicKeyData partnerEphemeralPublicKey) {
        this.partnerEphemeralPublicKey = partnerEphemeralPublicKey;
    }

    @Override
    public int[] serialize() {
        // Serialize the header
        serializeHeader(serializer);

        // Serialize the fields
        serializer.serializeBool(amInitiator);
        serializer.serializeEmberCertificateData(partnerCertificate);
        serializer.serializeEmberPublicKeyData(partnerEphemeralPublicKey);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(128);
        builder.append("EzspCalculateSmacsRequest [networkId=");
        builder.append(networkId);
        builder.append(", amInitiator=");
        builder.append(amInitiator);
        builder.append(", partnerCertificate=");
        builder.append(partnerCertificate);
        builder.append(", partnerEphemeralPublicKey=");
        builder.append(partnerEphemeralPublicKey);
        builder.append(']');
        return builder.toString();
    }
}
