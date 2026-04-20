package com.zsmartsystems.zigbee.console.main;

import com.zsmartsystems.zigbee.ZigBeeEndpoint;
import com.zsmartsystems.zigbee.ZigBeeNetworkManager;
import com.zsmartsystems.zigbee.ZigBeeNetworkNodeListener;
import com.zsmartsystems.zigbee.ZigBeeNode;
import com.zsmartsystems.zigbee.ZigBeeProfileType;
import com.zsmartsystems.zigbee.ZigBeeStatus;
import com.zsmartsystems.zigbee.app.ZigBeeNetworkExtension;
import com.zsmartsystems.zigbee.zcl.ZclAttribute;
import com.zsmartsystems.zigbee.zcl.clusters.ZclTimeCluster;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ZclTimeServerExtension implements ZigBeeNetworkExtension, ZigBeeNetworkNodeListener {

    private ZigBeeNetworkManager networkManager;
    private ScheduledExecutorService scheduler;

    @Override
    public ZigBeeStatus extensionInitialize(ZigBeeNetworkManager networkManager) {
        this.networkManager = networkManager;
        networkManager.addSupportedServerCluster(ZclTimeCluster.CLUSTER_ID);
        return ZigBeeStatus.SUCCESS;
    }

    @Override
    public ZigBeeStatus extensionStartup() {
        networkManager.addNetworkNodeListener(this);
        setupCoordinatorEndpoint();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::updateTime, 0, 30, TimeUnit.SECONDS);
        return ZigBeeStatus.SUCCESS;
    }

    @Override
    public void extensionShutdown() {
        networkManager.removeNetworkNodeListener(this);
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }

    @Override
    public void nodeAdded(ZigBeeNode node) {
        if (node.getNetworkAddress() == 0) {
            setupCoordinatorEndpoint();
        }
    }

    private void setupCoordinatorEndpoint() {
        ZigBeeNode coordinatorNode = networkManager.getNode(0);
        if (coordinatorNode == null) {
            return;
        }
        ZigBeeEndpoint endpoint = coordinatorNode.getEndpoint(1);
        if (endpoint == null) {
            endpoint = new ZigBeeEndpoint(coordinatorNode, 1);
            endpoint.setProfileId(ZigBeeProfileType.ZIGBEE_HOME_AUTOMATION.getKey());
            coordinatorNode.addEndpoint(endpoint);
        }
        if (endpoint.getInputCluster(ZclTimeCluster.CLUSTER_ID) == null) {
            endpoint.addInputCluster(new ZclTimeCluster(endpoint));
        }
        updateTime();
    }

    private void updateTime() {
        ZigBeeNode coordinatorNode = networkManager.getNode(0);
        if (coordinatorNode == null) {
            return;
        }
        ZigBeeEndpoint endpoint = coordinatorNode.getEndpoint(1);
        if (endpoint == null) {
            return;
        }
        ZclTimeCluster cluster = (ZclTimeCluster) endpoint.getInputCluster(ZclTimeCluster.CLUSTER_ID);
        if (cluster == null) {
            return;
        }
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        ZclAttribute timeAttribute = cluster.getLocalAttribute(ZclTimeCluster.ATTR_TIME);
        if (timeAttribute != null) {
            timeAttribute.setValue(now);
        }
        ZclAttribute timeStatusAttribute = cluster.getLocalAttribute(ZclTimeCluster.ATTR_TIMESTATUS);
        if (timeStatusAttribute != null) {
            timeStatusAttribute.setValue(2);
        }
    }
}
