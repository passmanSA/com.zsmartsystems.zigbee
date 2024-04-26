/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.zdo.command;

import javax.annotation.Generated;

import com.zsmartsystems.zigbee.zcl.ZclFieldDeserializer;
import com.zsmartsystems.zigbee.zcl.ZclFieldSerializer;
import com.zsmartsystems.zigbee.zcl.protocol.ZclDataType;
import com.zsmartsystems.zigbee.zdo.ZdoResponse;
import com.zsmartsystems.zigbee.zdo.ZdoStatus;
import com.zsmartsystems.zigbee.zdo.field.SimpleDescriptor;

/**
 * Simple Descriptor Response value object class.
 * <p>
 * <p>
 * The Simple_Desc_rsp is generated by a remote device in response to a Simple_Desc_req
 * directed to the remote device. This command shall be unicast to the originator of the
 * Simple_Desc_req command.
 * <p>
 * Code is auto-generated. Modifications may be overwritten!
 */
@Generated(value = "com.zsmartsystems.zigbee.autocode.ZigBeeCodeGenerator", date = "2020-12-25T10:11:19Z")
public class SimpleDescriptorResponse extends ZdoResponse {
    /**
     * The ZDO cluster ID.
     */
    public static int CLUSTER_ID = 0x8004;

    /**
     * NWK Addr Of Interest command message field.
     */
    private Integer nwkAddrOfInterest;

    /**
     * Length command message field.
     */
    private Integer length;

    /**
     * Simple Descriptor command message field.
     */
    private SimpleDescriptor simpleDescriptor;

    /**
     * Default constructor.
     *
     * @deprecated from release 1.3.0. Use the parameterised constructor instead of the default constructor and setters.
     */
    @Deprecated
    public SimpleDescriptorResponse() {
        clusterId = CLUSTER_ID;
    }

    /**
     * Constructor providing all required parameters.
     *
     * @param status {@link ZdoStatus} Status
     * @param nwkAddrOfInterest {@link Integer} NWK Addr Of Interest
     * @param length {@link Integer} Length
     * @param simpleDescriptor {@link SimpleDescriptor} Simple Descriptor
     */
    public SimpleDescriptorResponse(
            ZdoStatus status,
            Integer nwkAddrOfInterest,
            Integer length,
            SimpleDescriptor simpleDescriptor) {

        clusterId = CLUSTER_ID;

        this.status = status;
        this.nwkAddrOfInterest = nwkAddrOfInterest;
        this.length = length;
        this.simpleDescriptor = simpleDescriptor;
    }

    /**
     * Gets NWK Addr Of Interest.
     *
     * @return the NWK Addr Of Interest
     */
    public Integer getNwkAddrOfInterest() {
        return nwkAddrOfInterest;
    }

    /**
     * Sets NWK Addr Of Interest.
     *
     * @param nwkAddrOfInterest the NWK Addr Of Interest
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setNwkAddrOfInterest(final Integer nwkAddrOfInterest) {
        this.nwkAddrOfInterest = nwkAddrOfInterest;
    }

    /**
     * Gets Length.
     *
     * @return the Length
     */
    public Integer getLength() {
        return length;
    }

    /**
     * Sets Length.
     *
     * @param length the Length
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setLength(final Integer length) {
        this.length = length;
    }

    /**
     * Gets Simple Descriptor.
     *
     * @return the Simple Descriptor
     */
    public SimpleDescriptor getSimpleDescriptor() {
        return simpleDescriptor;
    }

    /**
     * Sets Simple Descriptor.
     *
     * @param simpleDescriptor the Simple Descriptor
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setSimpleDescriptor(final SimpleDescriptor simpleDescriptor) {
        this.simpleDescriptor = simpleDescriptor;
    }

    @Override
    public void serialize(final ZclFieldSerializer serializer) {
        super.serialize(serializer);

        serializer.serialize(status, ZclDataType.ZDO_STATUS);
        serializer.serialize(nwkAddrOfInterest, ZclDataType.NWK_ADDRESS);
        serializer.serialize(length, ZclDataType.UNSIGNED_8_BIT_INTEGER);
        serializer.serialize(simpleDescriptor, ZclDataType.SIMPLE_DESCRIPTOR);
    }

    @Override
    public void deserialize(final ZclFieldDeserializer deserializer) {
        super.deserialize(deserializer);

        status = deserializer.deserialize(ZclDataType.ZDO_STATUS);
        if (status != ZdoStatus.SUCCESS) {
            // Don't read the full response if we have an error
            return;
        }
        nwkAddrOfInterest = deserializer.deserialize(ZclDataType.NWK_ADDRESS);
        length = deserializer.deserialize(ZclDataType.UNSIGNED_8_BIT_INTEGER);
        simpleDescriptor = deserializer.deserialize(ZclDataType.SIMPLE_DESCRIPTOR);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(152);
        builder.append("SimpleDescriptorResponse [");
        builder.append(super.toString());
        builder.append(", status=");
        builder.append(status);
        builder.append(", nwkAddrOfInterest=");
        builder.append(String.format("%04X", nwkAddrOfInterest));
        builder.append(", length=");
        builder.append(length);
        builder.append(", simpleDescriptor=");
        builder.append(simpleDescriptor);
        builder.append(']');
        return builder.toString();
    }

}
