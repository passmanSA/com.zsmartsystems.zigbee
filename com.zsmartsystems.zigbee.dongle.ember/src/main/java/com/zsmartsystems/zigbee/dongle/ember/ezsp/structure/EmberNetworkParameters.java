/**
 * Copyright (c) 2016-2022 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.structure;

import com.zsmartsystems.zigbee.ExtendedPanId;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspDeserializer;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember Structure <b>EmberNetworkParameters</b>.
 * <p>
 * Network parameters.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EmberNetworkParameters {

    /**
     * The network's extended PAN identifier.
     * <p>
     * EZSP type is <i>ExtendedPanId</i> - Java type is {@link ExtendedPanId}
     */
    private ExtendedPanId extendedPanId;

    /**
     * The network's PAN identifier.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     */
    private int panId;

    /**
     * A power setting, in dBm.
     * <p>
     * EZSP type is <i>int8s</i> - Java type is {@link int}
     */
    private int radioTxPower;

    /**
     * A radio channel.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int radioChannel;

    /**
     * The method used to initially join the network
     * <p>
     * EZSP type is <i>EmberJoinMethod</i> - Java type is {@link EmberJoinMethod}
     */
    private EmberJoinMethod joinMethod;

    /**
     * NWK Manager ID. The ID of the network manager in the current network. This may only be set at
     * joining when using EMBER_USE_NWK_COMMISSIONING as the join method.
     * <p>
     * EZSP type is <i>EmberNodeId</i> - Java type is {@link int}
     */
    private int nwkManagerId;

    /**
     * NWK Update ID. The value of the ZigBee nwkUpdateId known by the stack. This is used to
     * determine the newest instance of the network after a PAN ID or channel change. This may only be
     * set at joining when using EMBER_USE_NWK_COMMISSIONING as the join method.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int nwkUpdateId;

    /**
     * NWK channel mask. The list of preferred channels that the NWK manager has told this device to
     * use when searching for the network. This may only be set at joining when using
     * EMBER_USE_NWK_COMMISSIONING as the join method.
     * <p>
     * EZSP type is <i>uint32_t</i> - Java type is {@link int}
     */
    private int channels;

    /**
     * Default Constructor
     */
    public EmberNetworkParameters() {
    }

    public EmberNetworkParameters(EzspDeserializer deserializer) {
        deserialize(deserializer);
    }

    /**
     * The network's extended PAN identifier.
     * <p>
     * EZSP type is <i>ExtendedPanId</i> - Java type is {@link ExtendedPanId}
     *
     * @return the current extendedPanId as {@link ExtendedPanId}
     */
    public ExtendedPanId getExtendedPanId() {
        return extendedPanId;
    }

    /**
     * The network's extended PAN identifier.
     *
     * @param extendedPanId the extendedPanId to set as {@link ExtendedPanId}
     */
    public void setExtendedPanId(ExtendedPanId extendedPanId) {
        this.extendedPanId = extendedPanId;
    }

    /**
     * The network's PAN identifier.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     *
     * @return the current panId as {@link int}
     */
    public int getPanId() {
        return panId;
    }

    /**
     * The network's PAN identifier.
     *
     * @param panId the panId to set as {@link int}
     */
    public void setPanId(int panId) {
        this.panId = panId;
    }

    /**
     * A power setting, in dBm.
     * <p>
     * EZSP type is <i>int8s</i> - Java type is {@link int}
     *
     * @return the current radioTxPower as {@link int}
     */
    public int getRadioTxPower() {
        return radioTxPower;
    }

    /**
     * A power setting, in dBm.
     *
     * @param radioTxPower the radioTxPower to set as {@link int}
     */
    public void setRadioTxPower(int radioTxPower) {
        this.radioTxPower = radioTxPower;
    }

    /**
     * A radio channel.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current radioChannel as {@link int}
     */
    public int getRadioChannel() {
        return radioChannel;
    }

    /**
     * A radio channel.
     *
     * @param radioChannel the radioChannel to set as {@link int}
     */
    public void setRadioChannel(int radioChannel) {
        this.radioChannel = radioChannel;
    }

    /**
     * The method used to initially join the network
     * <p>
     * EZSP type is <i>EmberJoinMethod</i> - Java type is {@link EmberJoinMethod}
     *
     * @return the current joinMethod as {@link EmberJoinMethod}
     */
    public EmberJoinMethod getJoinMethod() {
        return joinMethod;
    }

    /**
     * The method used to initially join the network
     *
     * @param joinMethod the joinMethod to set as {@link EmberJoinMethod}
     */
    public void setJoinMethod(EmberJoinMethod joinMethod) {
        this.joinMethod = joinMethod;
    }

    /**
     * NWK Manager ID. The ID of the network manager in the current network. This may only be set at
     * joining when using EMBER_USE_NWK_COMMISSIONING as the join method.
     * <p>
     * EZSP type is <i>EmberNodeId</i> - Java type is {@link int}
     *
     * @return the current nwkManagerId as {@link int}
     */
    public int getNwkManagerId() {
        return nwkManagerId;
    }

    /**
     * NWK Manager ID. The ID of the network manager in the current network. This may only be set at
     * joining when using EMBER_USE_NWK_COMMISSIONING as the join method.
     *
     * @param nwkManagerId the nwkManagerId to set as {@link int}
     */
    public void setNwkManagerId(int nwkManagerId) {
        this.nwkManagerId = nwkManagerId;
    }

    /**
     * NWK Update ID. The value of the ZigBee nwkUpdateId known by the stack. This is used to
     * determine the newest instance of the network after a PAN ID or channel change. This may only be
     * set at joining when using EMBER_USE_NWK_COMMISSIONING as the join method.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current nwkUpdateId as {@link int}
     */
    public int getNwkUpdateId() {
        return nwkUpdateId;
    }

    /**
     * NWK Update ID. The value of the ZigBee nwkUpdateId known by the stack. This is used to
     * determine the newest instance of the network after a PAN ID or channel change. This may only be
     * set at joining when using EMBER_USE_NWK_COMMISSIONING as the join method.
     *
     * @param nwkUpdateId the nwkUpdateId to set as {@link int}
     */
    public void setNwkUpdateId(int nwkUpdateId) {
        this.nwkUpdateId = nwkUpdateId;
    }

    /**
     * NWK channel mask. The list of preferred channels that the NWK manager has told this device to
     * use when searching for the network. This may only be set at joining when using
     * EMBER_USE_NWK_COMMISSIONING as the join method.
     * <p>
     * EZSP type is <i>uint32_t</i> - Java type is {@link int}
     *
     * @return the current channels as {@link int}
     */
    public int getChannels() {
        return channels;
    }

    /**
     * NWK channel mask. The list of preferred channels that the NWK manager has told this device to
     * use when searching for the network. This may only be set at joining when using
     * EMBER_USE_NWK_COMMISSIONING as the join method.
     *
     * @param channels the channels to set as {@link int}
     */
    public void setChannels(int channels) {
        this.channels = channels;
    }

    /**
     * Serialise the contents of the EZSP structure.
     *
     * @param serializer the {@link EzspSerializer} used to serialize
     */
    public int[] serialize(EzspSerializer serializer) {
        // Serialize the fields
        serializer.serializeExtendedPanId(extendedPanId);
        serializer.serializeUInt16(panId);
        serializer.serializeInt8S(radioTxPower);
        serializer.serializeUInt8(radioChannel);
        serializer.serializeEmberJoinMethod(joinMethod);
        serializer.serializeUInt16(nwkManagerId);
        serializer.serializeUInt8(nwkUpdateId);
        serializer.serializeUInt32(channels);
        return serializer.getPayload();
    }

    /**
     * Deserialise the contents of the EZSP structure.
     *
     * @param deserializer the {@link EzspDeserializer} used to deserialize
     */
    public void deserialize(EzspDeserializer deserializer) {
        // Deserialize the fields
        extendedPanId = deserializer.deserializeExtendedPanId();
        panId = deserializer.deserializeUInt16();
        radioTxPower = deserializer.deserializeInt8S();
        radioChannel = deserializer.deserializeUInt8();
        joinMethod = deserializer.deserializeEmberJoinMethod();
        nwkManagerId = deserializer.deserializeUInt16();
        nwkUpdateId = deserializer.deserializeUInt8();
        channels = deserializer.deserializeUInt32();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(225);
        builder.append("EmberNetworkParameters [extendedPanId=");
        builder.append(extendedPanId);
        builder.append(", panId=");
        builder.append(String.format("%04X", panId));
        builder.append(", radioTxPower=");
        builder.append(radioTxPower);
        builder.append(", radioChannel=");
        builder.append(radioChannel);
        builder.append(", joinMethod=");
        builder.append(joinMethod);
        builder.append(", nwkManagerId=");
        builder.append(String.format("%04X", nwkManagerId));
        builder.append(", nwkUpdateId=");
        builder.append(nwkUpdateId);
        builder.append(", channels=");
        builder.append(String.format("%08X", channels));
        builder.append(']');
        return builder.toString();
    }
}
