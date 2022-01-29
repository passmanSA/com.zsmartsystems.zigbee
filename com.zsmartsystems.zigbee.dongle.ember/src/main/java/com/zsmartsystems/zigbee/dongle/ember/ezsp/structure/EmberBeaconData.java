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
 * Class to implement the Ember Structure <b>EmberBeaconData</b>.
 * <p>
 * Beacon data structure.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EmberBeaconData {

    /**
     * The channel of the received beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int channel;

    /**
     * The LQI of the received beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int lqi;

    /**
     * The RSSI of the received beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int rssi;

    /**
     * The depth of the received beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int depth;

    /**
     * The network update ID of the received beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int nwkUpdateId;

    /**
     * The power level of the received beacon. This field is valid only if the beacon is an enhanced
     * beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int power;

    /**
     * The TC connectivity and long uptime from capacity field.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int parentPriority;

    /**
     * The PAN ID of the received beacon.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     */
    private int panId;

    /**
     * The extended PAN ID of the received beacon.
     * <p>
     * EZSP type is <i>ExtendedPanId</i> - Java type is {@link ExtendedPanId}
     */
    private ExtendedPanId extendedPanId;

    /**
     * The sender of the received beacon.
     * <p>
     * EZSP type is <i>EmberNodeId</i> - Java type is {@link int}
     */
    private int sender;

    /**
     * Whether or not the beacon is enhanced.
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     */
    private boolean enhanced;

    /**
     * Whether the beacon is advertising permit join.
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     */
    private boolean permitJoin;

    /**
     * Whether the beacon is advertising capacity.
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     */
    private boolean hasCapacity;

    /**
     * Default Constructor
     */
    public EmberBeaconData() {
    }

    public EmberBeaconData(EzspDeserializer deserializer) {
        deserialize(deserializer);
    }

    /**
     * The channel of the received beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current channel as {@link int}
     */
    public int getChannel() {
        return channel;
    }

    /**
     * The channel of the received beacon.
     *
     * @param channel the channel to set as {@link int}
     */
    public void setChannel(int channel) {
        this.channel = channel;
    }

    /**
     * The LQI of the received beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current lqi as {@link int}
     */
    public int getLqi() {
        return lqi;
    }

    /**
     * The LQI of the received beacon.
     *
     * @param lqi the lqi to set as {@link int}
     */
    public void setLqi(int lqi) {
        this.lqi = lqi;
    }

    /**
     * The RSSI of the received beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current rssi as {@link int}
     */
    public int getRssi() {
        return rssi;
    }

    /**
     * The RSSI of the received beacon.
     *
     * @param rssi the rssi to set as {@link int}
     */
    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    /**
     * The depth of the received beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current depth as {@link int}
     */
    public int getDepth() {
        return depth;
    }

    /**
     * The depth of the received beacon.
     *
     * @param depth the depth to set as {@link int}
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * The network update ID of the received beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current nwkUpdateId as {@link int}
     */
    public int getNwkUpdateId() {
        return nwkUpdateId;
    }

    /**
     * The network update ID of the received beacon.
     *
     * @param nwkUpdateId the nwkUpdateId to set as {@link int}
     */
    public void setNwkUpdateId(int nwkUpdateId) {
        this.nwkUpdateId = nwkUpdateId;
    }

    /**
     * The power level of the received beacon. This field is valid only if the beacon is an enhanced
     * beacon.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current power as {@link int}
     */
    public int getPower() {
        return power;
    }

    /**
     * The power level of the received beacon. This field is valid only if the beacon is an enhanced
     * beacon.
     *
     * @param power the power to set as {@link int}
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * The TC connectivity and long uptime from capacity field.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current parentPriority as {@link int}
     */
    public int getParentPriority() {
        return parentPriority;
    }

    /**
     * The TC connectivity and long uptime from capacity field.
     *
     * @param parentPriority the parentPriority to set as {@link int}
     */
    public void setParentPriority(int parentPriority) {
        this.parentPriority = parentPriority;
    }

    /**
     * The PAN ID of the received beacon.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     *
     * @return the current panId as {@link int}
     */
    public int getPanId() {
        return panId;
    }

    /**
     * The PAN ID of the received beacon.
     *
     * @param panId the panId to set as {@link int}
     */
    public void setPanId(int panId) {
        this.panId = panId;
    }

    /**
     * The extended PAN ID of the received beacon.
     * <p>
     * EZSP type is <i>ExtendedPanId</i> - Java type is {@link ExtendedPanId}
     *
     * @return the current extendedPanId as {@link ExtendedPanId}
     */
    public ExtendedPanId getExtendedPanId() {
        return extendedPanId;
    }

    /**
     * The extended PAN ID of the received beacon.
     *
     * @param extendedPanId the extendedPanId to set as {@link ExtendedPanId}
     */
    public void setExtendedPanId(ExtendedPanId extendedPanId) {
        this.extendedPanId = extendedPanId;
    }

    /**
     * The sender of the received beacon.
     * <p>
     * EZSP type is <i>EmberNodeId</i> - Java type is {@link int}
     *
     * @return the current sender as {@link int}
     */
    public int getSender() {
        return sender;
    }

    /**
     * The sender of the received beacon.
     *
     * @param sender the sender to set as {@link int}
     */
    public void setSender(int sender) {
        this.sender = sender;
    }

    /**
     * Whether or not the beacon is enhanced.
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     *
     * @return the current enhanced as {@link boolean}
     */
    public boolean getEnhanced() {
        return enhanced;
    }

    /**
     * Whether or not the beacon is enhanced.
     *
     * @param enhanced the enhanced to set as {@link boolean}
     */
    public void setEnhanced(boolean enhanced) {
        this.enhanced = enhanced;
    }

    /**
     * Whether the beacon is advertising permit join.
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     *
     * @return the current permitJoin as {@link boolean}
     */
    public boolean getPermitJoin() {
        return permitJoin;
    }

    /**
     * Whether the beacon is advertising permit join.
     *
     * @param permitJoin the permitJoin to set as {@link boolean}
     */
    public void setPermitJoin(boolean permitJoin) {
        this.permitJoin = permitJoin;
    }

    /**
     * Whether the beacon is advertising capacity.
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     *
     * @return the current hasCapacity as {@link boolean}
     */
    public boolean getHasCapacity() {
        return hasCapacity;
    }

    /**
     * Whether the beacon is advertising capacity.
     *
     * @param hasCapacity the hasCapacity to set as {@link boolean}
     */
    public void setHasCapacity(boolean hasCapacity) {
        this.hasCapacity = hasCapacity;
    }

    /**
     * Serialise the contents of the EZSP structure.
     *
     * @param serializer the {@link EzspSerializer} used to serialize
     */
    public int[] serialize(EzspSerializer serializer) {
        // Serialize the fields
        serializer.serializeUInt8(channel);
        serializer.serializeUInt8(lqi);
        serializer.serializeUInt8(rssi);
        serializer.serializeUInt8(depth);
        serializer.serializeUInt8(nwkUpdateId);
        serializer.serializeUInt8(power);
        serializer.serializeUInt8(parentPriority);
        serializer.serializeUInt16(panId);
        serializer.serializeExtendedPanId(extendedPanId);
        serializer.serializeUInt16(sender);
        serializer.serializeBool(enhanced);
        serializer.serializeBool(permitJoin);
        serializer.serializeBool(hasCapacity);
        return serializer.getPayload();
    }

    /**
     * Deserialise the contents of the EZSP structure.
     *
     * @param deserializer the {@link EzspDeserializer} used to deserialize
     */
    public void deserialize(EzspDeserializer deserializer) {
        // Deserialize the fields
        channel = deserializer.deserializeUInt8();
        lqi = deserializer.deserializeUInt8();
        rssi = deserializer.deserializeUInt8();
        depth = deserializer.deserializeUInt8();
        nwkUpdateId = deserializer.deserializeUInt8();
        power = deserializer.deserializeUInt8();
        parentPriority = deserializer.deserializeUInt8();
        panId = deserializer.deserializeUInt16();
        extendedPanId = deserializer.deserializeExtendedPanId();
        sender = deserializer.deserializeUInt16();
        enhanced = deserializer.deserializeBool();
        permitJoin = deserializer.deserializeBool();
        hasCapacity = deserializer.deserializeBool();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(343);
        builder.append("EmberBeaconData [channel=");
        builder.append(channel);
        builder.append(", lqi=");
        builder.append(lqi);
        builder.append(", rssi=");
        builder.append(rssi);
        builder.append(", depth=");
        builder.append(depth);
        builder.append(", nwkUpdateId=");
        builder.append(nwkUpdateId);
        builder.append(", power=");
        builder.append(power);
        builder.append(", parentPriority=");
        builder.append(parentPriority);
        builder.append(", panId=");
        builder.append(panId);
        builder.append(", extendedPanId=");
        builder.append(extendedPanId);
        builder.append(", sender=");
        builder.append(String.format("%04X", sender));
        builder.append(", enhanced=");
        builder.append(enhanced);
        builder.append(", permitJoin=");
        builder.append(permitJoin);
        builder.append(", hasCapacity=");
        builder.append(hasCapacity);
        builder.append(']');
        return builder.toString();
    }
}
