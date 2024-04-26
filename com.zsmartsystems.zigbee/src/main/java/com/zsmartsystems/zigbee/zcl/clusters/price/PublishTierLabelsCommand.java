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
import com.zsmartsystems.zigbee.zcl.field.ByteArray;
import com.zsmartsystems.zigbee.zcl.protocol.ZclCommandDirection;
import com.zsmartsystems.zigbee.zcl.protocol.ZclDataType;

/**
 * Publish Tier Labels Command value object class.
 * <p>
 * Cluster: <b>Price</b>. Command ID 0x08 is sent <b>FROM</b> the server.
 * This command is a <b>specific</b> command used for the Price cluster.
 * <p>
 * The PublishTierLabels command is generated in response to receiving a GetTierLabels
 * command or when there is a tier label change
 * <p>
 * Code is auto-generated. Modifications may be overwritten!
 */
@Generated(value = "com.zsmartsystems.zigbee.autocode.ZigBeeCodeGenerator", date = "2020-12-25T10:11:19Z")
public class PublishTierLabelsCommand extends ZclPriceCommand {
    /**
     * The cluster ID to which this command belongs.
     */
    public static int CLUSTER_ID = 0x0700;

    /**
     * The command ID.
     */
    public static int COMMAND_ID = 0x08;

    /**
     * Provider ID command message field.
     * <p>
     * An unsigned 32-bit field containing a unique identifier for the commodity provider.
     * This field allows differentiation in deregulated markets where multiple commodity
     * providers may be available.
     */
    private Integer providerId;

    /**
     * Issuer Event ID command message field.
     * <p>
     * Unique identifier generated by the commodity provider. When new information is
     * provided that replaces older information for the same time period, this field allows
     * devices to determine which information is newer. It is expected that the value
     * contained in this field is a unique number managed by upstream servers or a UTC based time
     * stamp (UTCTime data type) identifying when the Publish command was issued. Thus, newer
     * information will have a value in the Issuer Event ID field that is larger than older
     * information.
     */
    private Integer issuerEventId;

    /**
     * Issuer Tariff ID command message field.
     * <p>
     * Unique identifier generated by the commodity supplier. This is used to identify the
     * tariff that the labels apply to.
     */
    private Integer issuerTariffId;

    /**
     * Command Index command message field.
     * <p>
     * The Command Index is used to count the payload fragments in the case where the entire
     * payload does not fit into one message. The Command Index starts at 0 and is incremented
     * for each fragment belonging to the same command.
     */
    private Integer commandIndex;

    /**
     * Total Number Of Commands command message field.
     * <p>
     * In the case where the entire payload does not fit into one message, the Total Number of
     * Commands field indicates the total number of sub commands in the message.
     */
    private Integer totalNumberOfCommands;

    /**
     * Number Of Labels command message field.
     * <p>
     * The number of Tier ID/Tier Label sets contained within the command.
     */
    private Integer numberOfLabels;

    /**
     * Tier ID command message field.
     * <p>
     * The tier number that the associated Tier Label applies to.
     */
    private Integer tierId;

    /**
     * Tier Label command message field.
     * <p>
     * ZCL Octet String field capable of storing a 12 character string (the first character
     * indicates the string length, represented in hexadecimal format) encoded in the UTF-8
     * format.
     */
    private ByteArray tierLabel;

    /**
     * Default constructor.
     *
     * @deprecated from release 1.3.0. Use the parameterised constructor instead of the default constructor and setters.
     */
    @Deprecated
    public PublishTierLabelsCommand() {
        clusterId = CLUSTER_ID;
        commandId = COMMAND_ID;
        genericCommand = false;
        commandDirection = ZclCommandDirection.SERVER_TO_CLIENT;
    }

    /**
     * Constructor providing all required parameters.
     *
     * @param providerId {@link Integer} Provider ID
     * @param issuerEventId {@link Integer} Issuer Event ID
     * @param issuerTariffId {@link Integer} Issuer Tariff ID
     * @param commandIndex {@link Integer} Command Index
     * @param totalNumberOfCommands {@link Integer} Total Number Of Commands
     * @param numberOfLabels {@link Integer} Number Of Labels
     * @param tierId {@link Integer} Tier ID
     * @param tierLabel {@link ByteArray} Tier Label
     */
    public PublishTierLabelsCommand(
            Integer providerId,
            Integer issuerEventId,
            Integer issuerTariffId,
            Integer commandIndex,
            Integer totalNumberOfCommands,
            Integer numberOfLabels,
            Integer tierId,
            ByteArray tierLabel) {

        clusterId = CLUSTER_ID;
        commandId = COMMAND_ID;
        genericCommand = false;
        commandDirection = ZclCommandDirection.SERVER_TO_CLIENT;

        this.providerId = providerId;
        this.issuerEventId = issuerEventId;
        this.issuerTariffId = issuerTariffId;
        this.commandIndex = commandIndex;
        this.totalNumberOfCommands = totalNumberOfCommands;
        this.numberOfLabels = numberOfLabels;
        this.tierId = tierId;
        this.tierLabel = tierLabel;
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
     * Gets Issuer Event ID.
     * <p>
     * Unique identifier generated by the commodity provider. When new information is
     * provided that replaces older information for the same time period, this field allows
     * devices to determine which information is newer. It is expected that the value
     * contained in this field is a unique number managed by upstream servers or a UTC based time
     * stamp (UTCTime data type) identifying when the Publish command was issued. Thus, newer
     * information will have a value in the Issuer Event ID field that is larger than older
     * information.
     *
     * @return the Issuer Event ID
     */
    public Integer getIssuerEventId() {
        return issuerEventId;
    }

    /**
     * Sets Issuer Event ID.
     * <p>
     * Unique identifier generated by the commodity provider. When new information is
     * provided that replaces older information for the same time period, this field allows
     * devices to determine which information is newer. It is expected that the value
     * contained in this field is a unique number managed by upstream servers or a UTC based time
     * stamp (UTCTime data type) identifying when the Publish command was issued. Thus, newer
     * information will have a value in the Issuer Event ID field that is larger than older
     * information.
     *
     * @param issuerEventId the Issuer Event ID
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setIssuerEventId(final Integer issuerEventId) {
        this.issuerEventId = issuerEventId;
    }

    /**
     * Gets Issuer Tariff ID.
     * <p>
     * Unique identifier generated by the commodity supplier. This is used to identify the
     * tariff that the labels apply to.
     *
     * @return the Issuer Tariff ID
     */
    public Integer getIssuerTariffId() {
        return issuerTariffId;
    }

    /**
     * Sets Issuer Tariff ID.
     * <p>
     * Unique identifier generated by the commodity supplier. This is used to identify the
     * tariff that the labels apply to.
     *
     * @param issuerTariffId the Issuer Tariff ID
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setIssuerTariffId(final Integer issuerTariffId) {
        this.issuerTariffId = issuerTariffId;
    }

    /**
     * Gets Command Index.
     * <p>
     * The Command Index is used to count the payload fragments in the case where the entire
     * payload does not fit into one message. The Command Index starts at 0 and is incremented
     * for each fragment belonging to the same command.
     *
     * @return the Command Index
     */
    public Integer getCommandIndex() {
        return commandIndex;
    }

    /**
     * Sets Command Index.
     * <p>
     * The Command Index is used to count the payload fragments in the case where the entire
     * payload does not fit into one message. The Command Index starts at 0 and is incremented
     * for each fragment belonging to the same command.
     *
     * @param commandIndex the Command Index
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setCommandIndex(final Integer commandIndex) {
        this.commandIndex = commandIndex;
    }

    /**
     * Gets Total Number Of Commands.
     * <p>
     * In the case where the entire payload does not fit into one message, the Total Number of
     * Commands field indicates the total number of sub commands in the message.
     *
     * @return the Total Number Of Commands
     */
    public Integer getTotalNumberOfCommands() {
        return totalNumberOfCommands;
    }

    /**
     * Sets Total Number Of Commands.
     * <p>
     * In the case where the entire payload does not fit into one message, the Total Number of
     * Commands field indicates the total number of sub commands in the message.
     *
     * @param totalNumberOfCommands the Total Number Of Commands
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setTotalNumberOfCommands(final Integer totalNumberOfCommands) {
        this.totalNumberOfCommands = totalNumberOfCommands;
    }

    /**
     * Gets Number Of Labels.
     * <p>
     * The number of Tier ID/Tier Label sets contained within the command.
     *
     * @return the Number Of Labels
     */
    public Integer getNumberOfLabels() {
        return numberOfLabels;
    }

    /**
     * Sets Number Of Labels.
     * <p>
     * The number of Tier ID/Tier Label sets contained within the command.
     *
     * @param numberOfLabels the Number Of Labels
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setNumberOfLabels(final Integer numberOfLabels) {
        this.numberOfLabels = numberOfLabels;
    }

    /**
     * Gets Tier ID.
     * <p>
     * The tier number that the associated Tier Label applies to.
     *
     * @return the Tier ID
     */
    public Integer getTierId() {
        return tierId;
    }

    /**
     * Sets Tier ID.
     * <p>
     * The tier number that the associated Tier Label applies to.
     *
     * @param tierId the Tier ID
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setTierId(final Integer tierId) {
        this.tierId = tierId;
    }

    /**
     * Gets Tier Label.
     * <p>
     * ZCL Octet String field capable of storing a 12 character string (the first character
     * indicates the string length, represented in hexadecimal format) encoded in the UTF-8
     * format.
     *
     * @return the Tier Label
     */
    public ByteArray getTierLabel() {
        return tierLabel;
    }

    /**
     * Sets Tier Label.
     * <p>
     * ZCL Octet String field capable of storing a 12 character string (the first character
     * indicates the string length, represented in hexadecimal format) encoded in the UTF-8
     * format.
     *
     * @param tierLabel the Tier Label
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setTierLabel(final ByteArray tierLabel) {
        this.tierLabel = tierLabel;
    }

    @Override
    public void serialize(final ZclFieldSerializer serializer) {
        serializer.serialize(providerId, ZclDataType.UNSIGNED_32_BIT_INTEGER);
        serializer.serialize(issuerEventId, ZclDataType.UNSIGNED_32_BIT_INTEGER);
        serializer.serialize(issuerTariffId, ZclDataType.UNSIGNED_32_BIT_INTEGER);
        serializer.serialize(commandIndex, ZclDataType.UNSIGNED_8_BIT_INTEGER);
        serializer.serialize(totalNumberOfCommands, ZclDataType.UNSIGNED_8_BIT_INTEGER);
        serializer.serialize(numberOfLabels, ZclDataType.UNSIGNED_8_BIT_INTEGER);
        serializer.serialize(tierId, ZclDataType.UNSIGNED_8_BIT_INTEGER);
        serializer.serialize(tierLabel, ZclDataType.OCTET_STRING);
    }

    @Override
    public void deserialize(final ZclFieldDeserializer deserializer) {
        providerId = deserializer.deserialize(ZclDataType.UNSIGNED_32_BIT_INTEGER);
        issuerEventId = deserializer.deserialize(ZclDataType.UNSIGNED_32_BIT_INTEGER);
        issuerTariffId = deserializer.deserialize(ZclDataType.UNSIGNED_32_BIT_INTEGER);
        commandIndex = deserializer.deserialize(ZclDataType.UNSIGNED_8_BIT_INTEGER);
        totalNumberOfCommands = deserializer.deserialize(ZclDataType.UNSIGNED_8_BIT_INTEGER);
        numberOfLabels = deserializer.deserialize(ZclDataType.UNSIGNED_8_BIT_INTEGER);
        tierId = deserializer.deserialize(ZclDataType.UNSIGNED_8_BIT_INTEGER);
        tierLabel = deserializer.deserialize(ZclDataType.OCTET_STRING);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(286);
        builder.append("PublishTierLabelsCommand [");
        builder.append(super.toString());
        builder.append(", providerId=");
        builder.append(providerId);
        builder.append(", issuerEventId=");
        builder.append(issuerEventId);
        builder.append(", issuerTariffId=");
        builder.append(issuerTariffId);
        builder.append(", commandIndex=");
        builder.append(commandIndex);
        builder.append(", totalNumberOfCommands=");
        builder.append(totalNumberOfCommands);
        builder.append(", numberOfLabels=");
        builder.append(numberOfLabels);
        builder.append(", tierId=");
        builder.append(tierId);
        builder.append(", tierLabel=");
        builder.append(tierLabel);
        builder.append(']');
        return builder.toString();
    }

}
