package com.zsmartsystems.zigbee.console.main;

import com.zsmartsystems.zigbee.ZigBeeCommand;
import com.zsmartsystems.zigbee.ZigBeeCommandListener;
import com.zsmartsystems.zigbee.ZigBeeNetworkManager;
import com.zsmartsystems.zigbee.ZigBeeStatus;
import com.zsmartsystems.zigbee.app.ZigBeeNetworkExtension;
import com.zsmartsystems.zigbee.zcl.ZclStatus;
import com.zsmartsystems.zigbee.zcl.clusters.ZclTimeCluster;
import com.zsmartsystems.zigbee.zcl.clusters.general.ReadAttributesCommand;
import com.zsmartsystems.zigbee.zcl.clusters.general.ReadAttributesResponse;
import com.zsmartsystems.zigbee.zcl.field.ReadAttributeStatusRecord;
import com.zsmartsystems.zigbee.zcl.protocol.ZclDataType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ZclTimeServerExtension implements ZigBeeNetworkExtension, ZigBeeCommandListener {

    private static final int TIME_STATUS_MASTER = 0x02;

    private ZigBeeNetworkManager networkManager;

    @Override
    public ZigBeeStatus extensionInitialize(ZigBeeNetworkManager networkManager) {
        this.networkManager = networkManager;
        networkManager.addSupportedServerCluster(ZclTimeCluster.CLUSTER_ID);
        return ZigBeeStatus.SUCCESS;
    }

    @Override
    public ZigBeeStatus extensionStartup() {
        networkManager.addCommandListener(this);
        return ZigBeeStatus.SUCCESS;
    }

    @Override
    public void extensionShutdown() {
        networkManager.removeCommandListener(this);
    }

    @Override
    public void commandReceived(ZigBeeCommand command) {
        if (!(command instanceof ReadAttributesCommand)) {
            return;
        }
        ReadAttributesCommand readCmd = (ReadAttributesCommand) command;
        if (ZclTimeCluster.CLUSTER_ID != readCmd.getClusterId()) {
            return;
        }

        List<ReadAttributeStatusRecord> records = new ArrayList<>();
        for (Integer attributeId : readCmd.getIdentifiers()) {
            ReadAttributeStatusRecord record = new ReadAttributeStatusRecord();
            record.setAttributeIdentifier(attributeId);
            if (attributeId == ZclTimeCluster.ATTR_TIME) {
                record.setStatus(ZclStatus.SUCCESS);
                record.setAttributeDataType(ZclDataType.UTCTIME);
                record.setAttributeValue(Calendar.getInstance());
            } else if (attributeId == ZclTimeCluster.ATTR_TIMESTATUS) {
                record.setStatus(ZclStatus.SUCCESS);
                record.setAttributeDataType(ZclDataType.BITMAP_8_BIT);
                record.setAttributeValue(TIME_STATUS_MASTER);
            } else {
                record.setStatus(ZclStatus.UNSUPPORTED_ATTRIBUTE);
            }
            records.add(record);
        }

        ReadAttributesResponse response = new ReadAttributesResponse();
        response.setClusterId(ZclTimeCluster.CLUSTER_ID);
        response.setDestinationAddress(readCmd.getSourceAddress());
        response.setTransactionId(readCmd.getTransactionId());
        response.setDisableDefaultResponse(true);
        response.setRecords(records);

        networkManager.sendCommand(response);
    }
}
