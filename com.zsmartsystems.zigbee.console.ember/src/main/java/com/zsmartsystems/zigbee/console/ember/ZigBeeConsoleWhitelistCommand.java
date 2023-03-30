/**
 * Copyright (c) 2016-2021 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.console.ember;

import com.zsmartsystems.zigbee.ZigBeeNetworkManager;
import com.zsmartsystems.zigbee.console.ZigBeeConsoleAbstractCommand;
import com.zsmartsystems.zigbee.dongle.ember.EmberNcp;
import com.zsmartsystems.zigbee.dongle.ember.ZigBeeDongleEzsp;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.command.EzspCustomFrameResponse;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberStatus;

import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Chris Jackson
 *
 */
public class ZigBeeConsoleWhitelistCommand extends ZigBeeConsoleAbstractCommand {

    private static final int CUSTOM_FRAME_ADD_ALLOWED_DEVICES_ID = 0;
    private static final int CUSTOM_FRAME_REMOVE_ALLOWED_DEVICES_ID = 1;

    @Override
    public String getCommand() {
        return "ncpwlist";
    }

    @Override
    public String getDescription() {
        return "Add or remove IEEE address to/from whitelist.";
    }

    @Override
    public String getSyntax() {
        return "[ADD|DEL] [IEEE]";
    }

    @Override
    public String getHelp() {
        return "";
    }

    @Override
    public void process(ZigBeeNetworkManager networkManager, String[] args, PrintStream out)
            throws IllegalArgumentException {
        if (!(networkManager.getZigBeeTransport() instanceof ZigBeeDongleEzsp)) {
            throw new IllegalArgumentException("Dongle is not an Ember NCP.");
        }
        ZigBeeDongleEzsp dongle = (ZigBeeDongleEzsp) networkManager.getZigBeeTransport();

        if (args.length != 2) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        if ("add".equalsIgnoreCase(args[1])) {
            addAllowedDevices(dongle.getEmberNcp(), Collections.singletonList(args[2]));
            out.println("Whitelist add " + args[2] + " success.");
        } else if ("del".equalsIgnoreCase(args[1])) {
            removeAllowedDevices(dongle.getEmberNcp(), Collections.singletonList(args[2]));
            out.println("Whitelist del " + args[2] + " success.");
        } else {
            throw new IllegalArgumentException("Invalid argument " + args[1]);
        }
    }

    public void addAllowedDevices(EmberNcp ncp, List<String> devices) {
        if(devices.isEmpty()) {
            return;
        }

        final int batchSize = 10;
        int next = 0;
        while(next < devices.size()) {
            final int remaining = devices.size() - next;
            final List<String> batch = devices.subList(next, remaining >= batchSize ? next + batchSize : next + remaining);
            next += batch.size();

            final int[] frame = new int[1 + (batch.size() * 8)];
            frame[0] = CUSTOM_FRAME_ADD_ALLOWED_DEVICES_ID;
            for (int idx = 0; idx < batch.size(); ++idx) {
                int[] deviceEuidBytes = hexToBytes(batch.get(idx));
                for (int i = 0; i < deviceEuidBytes.length; ++i) {
                    frame[1 + i + (idx * 8)] = deviceEuidBytes[i];
                }
            }
            EzspCustomFrameResponse response = ncp.sendCustomFrame(frame);
            if (response.getStatus() != EmberStatus.EMBER_SUCCESS) {
                throw new RuntimeException("fail to add allowed devices");
            }
        }
    }

    public void removeAllowedDevices(EmberNcp ncp, List<String> devices) {
        if(devices.isEmpty()) {
            return;
        }

        final int batchSize = 10;
        int next = 0;
        while(next < devices.size()) {
            final int remaining = devices.size() - next;
            final List<String> batch = devices.subList(next, remaining >= batchSize ? next + batchSize : next + remaining);
            next += batch.size();

            final int[] frame = new int[1 + (batch.size() * 8)];
            frame[0] = CUSTOM_FRAME_REMOVE_ALLOWED_DEVICES_ID;
            for (int idx = 0; idx < batch.size(); ++idx) {
                int[] deviceEuidBytes = hexToBytes(batch.get(idx));
                for (int i = 0; i < deviceEuidBytes.length; ++i) {
                    frame[1 + i + (idx * 8)] = deviceEuidBytes[i];
                }
            }
            EzspCustomFrameResponse response = ncp.sendCustomFrame(frame);
            if (response.getStatus() != EmberStatus.EMBER_SUCCESS) {
                throw new RuntimeException("fail to remove allowed devices");
            }
        }
    }

    public static int[] hexToBytes(String s) {
        int len = s.length();
        int[] data = new int[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}