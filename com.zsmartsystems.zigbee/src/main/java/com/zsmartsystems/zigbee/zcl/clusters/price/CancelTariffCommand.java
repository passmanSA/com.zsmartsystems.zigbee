/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.zcl.clusters.price;

import javax.annotation.Generated;

import com.zsmartsystems.zigbee.zcl.ZclFieldDeserializer;
import com.zsmartsystems.zigbee.zcl.ZclFieldSerializer;
import com.zsmartsystems.zigbee.zcl.protocol.ZclCommandDirection;
import com.zsmartsystems.zigbee.zcl.protocol.ZclDataType;

/**
 * Cancel Tariff Command value object class.
 * <p>
 * Cluster: <b>Price</b>. Command ID 0x0E is sent <b>FROM</b> the server.
 * This command is a <b>specific</b> command used for the Price cluster.
 * <p>
 * The CancelTariff command indicates that all data associated with a particular tariff
 * instance should be discarded. <br> In markets where permanently active price information
 * is required for billing purposes, it is recommended that replacement/superseding
 * PublishTariffInformation, PublishPriceMatrix, PublishBlockThresholds and
 * PublishTierLabels commands are used in place of a CancelTariff command.
 * <p>
 * Code is auto-generated. Modifications may be overwritten!
 */
@Generated(value = "com.zsmartsystems.zigbee.autocode.ZigBeeCodeGenerator", date = "2020-12-25T10:11:19Z")
public class CancelTariffCommand extends ZclPriceCommand {
    /**
     * The cluster ID to which this command belongs.
     */
    public static int CLUSTER_ID = 0x0700;

    /**
     * The command ID.
     */
    public static int COMMAND_ID = 0x0E;

    /**
     * Provider ID command message field.
     * <p>
     * An unsigned 32-bit field containing a unique identifier for the commodity provider.
     * This field allows differentiation in deregulated markets where multiple commodity
     * providers may be available.
     */
    private Integer providerId;

    /**
     * Issuer Tariff ID command message field.
     * <p>
     * Unique identifier generated by the commodity Supplier. All parts of a tariff instance
     * shall have the same Issuer Tariff ID.
     */
    private Integer issuerTariffId;

    /**
     * Tariff Type command message field.
     * <p>
     * An 8-bit bitmap identifying the type of tariff to be cancelled by this command. The least
     * significant nibble represents an enumeration of the tariff type (Generation Meters
     * shall use the ‘Received’ Tariff). The most significant nibble is reserved.
     */
    private Integer tariffType;

    /**
     * Default constructor.
     *
     * @deprecated from release 1.3.0. Use the parameterised constructor instead of the default constructor and setters.
     */
    @Deprecated
    public CancelTariffCommand() {
        clusterId = CLUSTER_ID;
        commandId = COMMAND_ID;
        genericCommand = false;
        commandDirection = ZclCommandDirection.SERVER_TO_CLIENT;
    }

    /**
     * Constructor providing all required parameters.
     *
     * @param providerId {@link Integer} Provider ID
     * @param issuerTariffId {@link Integer} Issuer Tariff ID
     * @param tariffType {@link Integer} Tariff Type
     */
    public CancelTariffCommand(
            Integer providerId,
            Integer issuerTariffId,
            Integer tariffType) {

        clusterId = CLUSTER_ID;
        commandId = COMMAND_ID;
        genericCommand = false;
        commandDirection = ZclCommandDirection.SERVER_TO_CLIENT;

        this.providerId = providerId;
        this.issuerTariffId = issuerTariffId;
        this.tariffType = tariffType;
    }

    /**
     * Gets Provider ID.
     * <p>
     * An unsigned 32-bit field containing a unique identifier for the commodity provider.
     * This field allows differentiation in deregulated markets where multiple commodity
     * providers may be available.
     *
     * @return the Provider ID
     */
    public Integer getProviderId() {
        return providerId;
    }

    /**
     * Sets Provider ID.
     * <p>
     * An unsigned 32-bit field containing a unique identifier for the commodity provider.
     * This field allows differentiation in deregulated markets where multiple commodity
     * providers may be available.
     *
     * @param providerId the Provider ID
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setProviderId(final Integer providerId) {
        this.providerId = providerId;
    }

    /**
     * Gets Issuer Tariff ID.
     * <p>
     * Unique identifier generated by the commodity Supplier. All parts of a tariff instance
     * shall have the same Issuer Tariff ID.
     *
     * @return the Issuer Tariff ID
     */
    public Integer getIssuerTariffId() {
        return issuerTariffId;
    }

    /**
     * Sets Issuer Tariff ID.
     * <p>
     * Unique identifier generated by the commodity Supplier. All parts of a tariff instance
     * shall have the same Issuer Tariff ID.
     *
     * @param issuerTariffId the Issuer Tariff ID
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setIssuerTariffId(final Integer issuerTariffId) {
        this.issuerTariffId = issuerTariffId;
    }

    /**
     * Gets Tariff Type.
     * <p>
     * An 8-bit bitmap identifying the type of tariff to be cancelled by this command. The least
     * significant nibble represents an enumeration of the tariff type (Generation Meters
     * shall use the ‘Received’ Tariff). The most significant nibble is reserved.
     *
     * @return the Tariff Type
     */
    public Integer getTariffType() {
        return tariffType;
    }

    /**
     * Sets Tariff Type.
     * <p>
     * An 8-bit bitmap identifying the type of tariff to be cancelled by this command. The least
     * significant nibble represents an enumeration of the tariff type (Generation Meters
     * shall use the ‘Received’ Tariff). The most significant nibble is reserved.
     *
     * @param tariffType the Tariff Type
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setTariffType(final Integer tariffType) {
        this.tariffType = tariffType;
    }

    @Override
    public void serialize(final ZclFieldSerializer serializer) {
        serializer.serialize(providerId, ZclDataType.UNSIGNED_32_BIT_INTEGER);
        serializer.serialize(issuerTariffId, ZclDataType.UNSIGNED_32_BIT_INTEGER);
        serializer.serialize(tariffType, ZclDataType.BITMAP_8_BIT);
    }

    @Override
    public void deserialize(final ZclFieldDeserializer deserializer) {
        providerId = deserializer.deserialize(ZclDataType.UNSIGNED_32_BIT_INTEGER);
        issuerTariffId = deserializer.deserialize(ZclDataType.UNSIGNED_32_BIT_INTEGER);
        tariffType = deserializer.deserialize(ZclDataType.BITMAP_8_BIT);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(116);
        builder.append("CancelTariffCommand [");
        builder.append(super.toString());
        builder.append(", providerId=");
        builder.append(providerId);
        builder.append(", issuerTariffId=");
        builder.append(issuerTariffId);
        builder.append(", tariffType=");
        builder.append(tariffType);
        builder.append(']');
        return builder.toString();
    }

}
