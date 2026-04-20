package com.zsmartsystems.zigbee.console.main;

import com.zsmartsystems.zigbee.ZigBeeNetworkManager;
import com.zsmartsystems.zigbee.ZigBeeStatus;
import com.zsmartsystems.zigbee.app.ZigBeeNetworkExtension;
import com.zsmartsystems.zigbee.zcl.clusters.ZclTimeCluster;

public class ZclTimeServerExtension implements ZigBeeNetworkExtension {

    @Override
    public ZigBeeStatus extensionInitialize(ZigBeeNetworkManager networkManager) {
        networkManager.addSupportedServerCluster(ZclTimeCluster.CLUSTER_ID);
        return ZigBeeStatus.SUCCESS;
    }

    @Override
    public ZigBeeStatus extensionStartup() {
        return ZigBeeStatus.SUCCESS;
    }

    @Override
    public void extensionShutdown() {
    }
}
