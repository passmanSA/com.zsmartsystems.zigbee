/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.internal;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrame;

/**
 * Interface to exchange asynchronous packets and link state changes from the low level protocol handlers
 * (ASH/SPI) to the EZSP layer.
 *
 * @author Chris Jackson
 *
 */
public interface EzspFrameHandler {

    int getEzspVersion();

    default String getHandlerIdentifier() {
        return null;
    }

    /**
     * Passes received asynchronous frames from the ASH handler to the EZSP layer
     *
     * @param response incoming {@link EzspFrame} response frame
     */
    public void handlePacket(EzspFrame response);

    /**
     * Called when the ASH link state changes
     *
     * @param state true if the link is UP, false if the link is DOWN
     */
    public void handleLinkStateChange(boolean state);
}
