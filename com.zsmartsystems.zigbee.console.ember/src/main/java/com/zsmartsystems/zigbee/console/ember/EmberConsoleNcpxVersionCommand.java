/**
 * Copyright (c) 2016-2021 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.console.ember;

import com.zsmartsystems.zigbee.ZigBeeNetworkManager;
import com.zsmartsystems.zigbee.dongle.ember.EmberNcp;
import com.zsmartsystems.zigbee.dongle.ember.ezsp.command.EzspGetXncpInfoResponse;

import java.io.PrintStream;

public class EmberConsoleNcpxVersionCommand extends EmberConsoleAbstractCommand {
    @Override
    public String getCommand() {
        return "ncpxversion";
    }

    @Override
    public String getDescription() {
        return "If exists gets the XNCP version and the manufactured ID";
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


        EzspGetXncpInfoResponse xncpInfo = ncp.getXncpInfo();
        if (xncpInfo != null) {
            out.println(String.format("Ember xNCP information: manufacturerId=0x%04X, versionNumber=0x%04X", xncpInfo.getManufacturerId(), xncpInfo.getVersionNumber()));
        } else {
            out.println("XNCP extension is not supported");
        }
    }
}
