/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.zcl.clusters.price;

import java.util.Calendar;

import javax.annotation.Generated;

import com.zsmartsystems.zigbee.zcl.ZclFieldDeserializer;
import com.zsmartsystems.zigbee.zcl.ZclFieldSerializer;
import com.zsmartsystems.zigbee.zcl.clusters.price.BlockThresholdSubPayload;
import com.zsmartsystems.zigbee.zcl.protocol.ZclCommandDirection;
import com.zsmartsystems.zigbee.zcl.protocol.ZclDataType;

/**
 * Publish Block Thresholds Command value object class.
 * <p>
 * Cluster: <b>Price</b>. Command ID 0x06 is sent <b>FROM</b> the server.
 * This command is a <b>specific</b> command used for the Price cluster.
 * <p>
 * The PublishBlockThresholds command is sent in response to a GetBlockThresholds command.
 * Clients should be capable of storing at least two instances of the Block Thresholds, the
 * currently active and the next one. <br> There may be a separate set of Block Thresholds for
 * consumption delivered and received; in this case, each set of Block Thresholds will be
 * identified by a different IssuerTariffId value. <br> The price server shall send only the
 * number of block thresholds in use (NumberofBlockThresholdsInUse) as defined in the
 * PublishTariffInformation command. <br> The maximum application payload may not be
 * sufficient to transfer all thresholds in one command. In this case the Price server may send
 * two consecutive PublishBlockThreshold commands (CommandIndex set to 0 and 1
 * respectively); both commands shall use the same value of Issuer Event ID. Note that, in this
 * case, it is the client’s responsibility to ensure that it receives all associated
 * PublishBlockThreshold commands before any of the payloads can be used.
 * <p>
 * Code is auto-generated. Modifications may be overwritten!
 */
@Generated(value = "com.zsmartsystems.zigbee.autocode.ZigBeeCodeGenerator", date = "2020-12-25T10:11:19Z")
public class PublishBlockThresholdsCommand extends ZclPriceCommand {
    /**
     * The cluster ID to which this command belongs.
     */
    public static int CLUSTER_ID = 0x0700;

    /**
     * The command ID.
     */
    public static int COMMAND_ID = 0x06;

    /**
     * Provider ID command message field.
     */
    private Integer providerId;

    /**
     * Issuer Event ID command message field.
     * <p>
     * Unique identifier generated by the commodity provider. When new information is
     * provided that replaces older information for the same time period, this field allows
     * devices to determine which information is newer. The value contained in this field is a
     * unique number managed by upstream servers or a UTC based time stamp (UTCTime data type)
     * identifying when the Publish command was issued. Thus, newer information will have a
     * value in the Issuer Event ID field that is larger than older information.
     */
    private Integer issuerEventId;

    /**
     * Start Time command message field.
     * <p>
     * A UTCTime field to denote the time at which the price signal becomes valid. A start
     * date/time of 0x00000000 shall indicate that the command should be executed
     * immediately.
     */
    private Calendar startTime;

    /**
     * Issuer Tariff ID command message field.
     * <p>
     * Unique identifier generated by the commodity supplier. This must match the Issuer
     * Tariff ID sent in the related PublishTariffInformation command.
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
     * Commands field indicates the total number of sub9242 commands in the message. The
     * Sub-Payload Control bitmap specifies the usage of the information contained within
     * the Block Threshold Sub-Payload The BlockThreshold Sub-Payload consists of multiple
     * sets of data which consist of a Tier ID, Block Threshold Count and the threshold values
     * associated with the stated Tier. The number of thresholds contained in any one set is
     * identified in the NumberOfBlockThresholds sub-field.
     */
    private Integer totalNumberOfCommands;

    /**
     * Sub Payload Control command message field.
     * <p>
     * The Sub-Payload Control bitmap specifies the usage of the information contained
     * within the Block Threshold Sub-Payload.
     */
    private Integer subPayloadControl;

    /**
     * Block Threshold Sub Payload command message field.
     * <p>
     * The BlockThreshold Sub-Payload consists of multiple sets of data which consist of a
     * Tier ID, Block Threshold Count and the threshold values associated with the stated
     * Tier. The number of thresholds contained in any one set is identified in the
     * NumberOfBlockThresholds sub-field.
     */
    private BlockThresholdSubPayload blockThresholdSubPayload;

    /**
     * Default constructor.
     *
     * @deprecated from release 1.3.0. Use the parameterised constructor instead of the default constructor and setters.
     */
    @Deprecated
    public PublishBlockThresholdsCommand() {
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
     * @param startTime {@link Calendar} Start Time
     * @param issuerTariffId {@link Integer} Issuer Tariff ID
     * @param commandIndex {@link Integer} Command Index
     * @param totalNumberOfCommands {@link Integer} Total Number Of Commands
     * @param subPayloadControl {@link Integer} Sub Payload Control
     * @param blockThresholdSubPayload {@link BlockThresholdSubPayload} Block Threshold Sub Payload
     */
    public PublishBlockThresholdsCommand(
            Integer providerId,
            Integer issuerEventId,
            Calendar startTime,
            Integer issuerTariffId,
            Integer commandIndex,
            Integer totalNumberOfCommands,
            Integer subPayloadControl,
            BlockThresholdSubPayload blockThresholdSubPayload) {

        clusterId = CLUSTER_ID;
        commandId = COMMAND_ID;
        genericCommand = false;
        commandDirection = ZclCommandDirection.SERVER_TO_CLIENT;

        this.providerId = providerId;
        this.issuerEventId = issuerEventId;
        this.startTime = startTime;
        this.issuerTariffId = issuerTariffId;
        this.commandIndex = commandIndex;
        this.totalNumberOfCommands = totalNumberOfCommands;
        this.subPayloadControl = subPayloadControl;
        this.blockThresholdSubPayload = blockThresholdSubPayload;
    }

    /**
     * Gets Provider ID.
     *
     * @return the Provider ID
     */
    public Integer getProviderId() {
        return providerId;
    }

    /**
     * Sets Provider ID.
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
     * devices to determine which information is newer. The value contained in this field is a
     * unique number managed by upstream servers or a UTC based time stamp (UTCTime data type)
     * identifying when the Publish command was issued. Thus, newer information will have a
     * value in the Issuer Event ID field that is larger than older information.
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
     * devices to determine which information is newer. The value contained in this field is a
     * unique number managed by upstream servers or a UTC based time stamp (UTCTime data type)
     * identifying when the Publish command was issued. Thus, newer information will have a
     * value in the Issuer Event ID field that is larger than older information.
     *
     * @param issuerEventId the Issuer Event ID
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setIssuerEventId(final Integer issuerEventId) {
        this.issuerEventId = issuerEventId;
    }

    /**
     * Gets Start Time.
     * <p>
     * A UTCTime field to denote the time at which the price signal becomes valid. A start
     * date/time of 0x00000000 shall indicate that the command should be executed
     * immediately.
     *
     * @return the Start Time
     */
    public Calendar getStartTime() {
        return startTime;
    }

    /**
     * Sets Start Time.
     * <p>
     * A UTCTime field to denote the time at which the price signal becomes valid. A start
     * date/time of 0x00000000 shall indicate that the command should be executed
     * immediately.
     *
     * @param startTime the Start Time
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setStartTime(final Calendar startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets Issuer Tariff ID.
     * <p>
     * Unique identifier generated by the commodity supplier. This must match the Issuer
     * Tariff ID sent in the related PublishTariffInformation command.
     *
     * @return the Issuer Tariff ID
     */
    public Integer getIssuerTariffId() {
        return issuerTariffId;
    }

    /**
     * Sets Issuer Tariff ID.
     * <p>
     * Unique identifier generated by the commodity supplier. This must match the Issuer
     * Tariff ID sent in the related PublishTariffInformation command.
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
     * Commands field indicates the total number of sub9242 commands in the message. The
     * Sub-Payload Control bitmap specifies the usage of the information contained within
     * the Block Threshold Sub-Payload The BlockThreshold Sub-Payload consists of multiple
     * sets of data which consist of a Tier ID, Block Threshold Count and the threshold values
     * associated with the stated Tier. The number of thresholds contained in any one set is
     * identified in the NumberOfBlockThresholds sub-field.
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
     * Commands field indicates the total number of sub9242 commands in the message. The
     * Sub-Payload Control bitmap specifies the usage of the information contained within
     * the Block Threshold Sub-Payload The BlockThreshold Sub-Payload consists of multiple
     * sets of data which consist of a Tier ID, Block Threshold Count and the threshold values
     * associated with the stated Tier. The number of thresholds contained in any one set is
     * identified in the NumberOfBlockThresholds sub-field.
     *
     * @param totalNumberOfCommands the Total Number Of Commands
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setTotalNumberOfCommands(final Integer totalNumberOfCommands) {
        this.totalNumberOfCommands = totalNumberOfCommands;
    }

    /**
     * Gets Sub Payload Control.
     * <p>
     * The Sub-Payload Control bitmap specifies the usage of the information contained
     * within the Block Threshold Sub-Payload.
     *
     * @return the Sub Payload Control
     */
    public Integer getSubPayloadControl() {
        return subPayloadControl;
    }

    /**
     * Sets Sub Payload Control.
     * <p>
     * The Sub-Payload Control bitmap specifies the usage of the information contained
     * within the Block Threshold Sub-Payload.
     *
     * @param subPayloadControl the Sub Payload Control
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setSubPayloadControl(final Integer subPayloadControl) {
        this.subPayloadControl = subPayloadControl;
    }

    /**
     * Gets Block Threshold Sub Payload.
     * <p>
     * The BlockThreshold Sub-Payload consists of multiple sets of data which consist of a
     * Tier ID, Block Threshold Count and the threshold values associated with the stated
     * Tier. The number of thresholds contained in any one set is identified in the
     * NumberOfBlockThresholds sub-field.
     *
     * @return the Block Threshold Sub Payload
     */
    public BlockThresholdSubPayload getBlockThresholdSubPayload() {
        return blockThresholdSubPayload;
    }

    /**
     * Sets Block Threshold Sub Payload.
     * <p>
     * The BlockThreshold Sub-Payload consists of multiple sets of data which consist of a
     * Tier ID, Block Threshold Count and the threshold values associated with the stated
     * Tier. The number of thresholds contained in any one set is identified in the
     * NumberOfBlockThresholds sub-field.
     *
     * @param blockThresholdSubPayload the Block Threshold Sub Payload
     * @deprecated as of 1.3.0. Use the parameterised constructor instead to ensure that all mandatory fields are provided.
     */
    @Deprecated
    public void setBlockThresholdSubPayload(final BlockThresholdSubPayload blockThresholdSubPayload) {
        this.blockThresholdSubPayload = blockThresholdSubPayload;
    }

    @Override
    public void serialize(final ZclFieldSerializer serializer) {
        serializer.serialize(providerId, ZclDataType.UNSIGNED_32_BIT_INTEGER);
        serializer.serialize(issuerEventId, ZclDataType.UNSIGNED_32_BIT_INTEGER);
        serializer.serialize(startTime, ZclDataType.UTCTIME);
        serializer.serialize(issuerTariffId, ZclDataType.UNSIGNED_32_BIT_INTEGER);
        serializer.serialize(commandIndex, ZclDataType.UNSIGNED_8_BIT_INTEGER);
        serializer.serialize(totalNumberOfCommands, ZclDataType.UNSIGNED_8_BIT_INTEGER);
        serializer.serialize(subPayloadControl, ZclDataType.BITMAP_8_BIT);
        blockThresholdSubPayload.serialize(serializer);
    }

    @Override
    public void deserialize(final ZclFieldDeserializer deserializer) {
        providerId = deserializer.deserialize(ZclDataType.UNSIGNED_32_BIT_INTEGER);
        issuerEventId = deserializer.deserialize(ZclDataType.UNSIGNED_32_BIT_INTEGER);
        startTime = deserializer.deserialize(ZclDataType.UTCTIME);
        issuerTariffId = deserializer.deserialize(ZclDataType.UNSIGNED_32_BIT_INTEGER);
        commandIndex = deserializer.deserialize(ZclDataType.UNSIGNED_8_BIT_INTEGER);
        totalNumberOfCommands = deserializer.deserialize(ZclDataType.UNSIGNED_8_BIT_INTEGER);
        subPayloadControl = deserializer.deserialize(ZclDataType.BITMAP_8_BIT);
        blockThresholdSubPayload = new BlockThresholdSubPayload();
        blockThresholdSubPayload.deserialize(deserializer);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(312);
        builder.append("PublishBlockThresholdsCommand [");
        builder.append(super.toString());
        builder.append(", providerId=");
        builder.append(providerId);
        builder.append(", issuerEventId=");
        builder.append(issuerEventId);
        builder.append(", startTime=");
        builder.append(startTime);
        builder.append(", issuerTariffId=");
        builder.append(issuerTariffId);
        builder.append(", commandIndex=");
        builder.append(commandIndex);
        builder.append(", totalNumberOfCommands=");
        builder.append(totalNumberOfCommands);
        builder.append(", subPayloadControl=");
        builder.append(subPayloadControl);
        builder.append(", blockThresholdSubPayload=");
        builder.append(blockThresholdSubPayload);
        builder.append(']');
        return builder.toString();
    }

}
