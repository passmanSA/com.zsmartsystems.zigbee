/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.console.ember;

import java.io.PrintStream;

import com.zsmartsystems.zigbee.ZigBeeNetworkManager;
import com.zsmartsystems.zigbee.dongle.ember.EmberNcp;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrame;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.command.EzspVersionResponse;

/**
 *
 * @author Chris Jackson
 *
 */
public class EmberConsoleNcpVersionCommand extends EmberConsoleAbstractCommand {
    private static final int BOOTLOADER_INVALID_VERSION = 0xFFFF;
    @Override
    public String getCommand() {
        return "ncpversion";
    }

    @Override
    public String getDescription() {
        return "Gets the NCP firmware version";
    }

    @Override
    public String getSyntax() {
        return "";
    }

    @Override
    public String getHelp() {
        return "";
    }

    @Override
    public void process(ZigBeeNetworkManager networkManager, String[] args, PrintStream out)
            throws IllegalArgumentException {
        EmberNcp ncp = getEmberNcp(networkManager);

        EzspVersionResponse version = ncp.getVersion();
        String bootloaderVersion = getVersionString(ncp.getBootloaderVersion());
        out.println("Ember NCP version " + getVersionString(version.getStackVersion()) +
                    ", EZSP version " + version.getProtocolVersion() +
                    ", Bootloader version " + bootloaderVersion.replaceFirst("(\\..*?)\\.", "$1 build ").replaceAll("\\.(?!.*\\.)", ""));
    }

    private String getVersionString(int value) {
        StringBuilder builder = new StringBuilder(16);
        for (int cnt = 3; cnt >= 0; cnt--) {
            builder.append((value >> (cnt * 4)) & 0x0F);
            if (cnt != 0) {
                builder.append('.');
            }
        }

        return builder.toString();
    }

    private String getBootLoaderVersion(int bootloaderVersion) {
        StringBuilder builder = new StringBuilder();
        if (bootloaderVersion == BOOTLOADER_INVALID_VERSION) {
            builder.append("NONE");
        } else {
            builder.append((bootloaderVersion >> 12) & 0x0F);
            builder.append('.');
            builder.append((bootloaderVersion >> 8) & 0x0F);

            builder.append(" build ");
            builder.append(bootloaderVersion & 0xFF);
        }
        return builder.toString();
    }

}
