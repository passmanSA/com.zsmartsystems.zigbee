/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember EZSP command <b>launchStandaloneBootloader</b>.
 * <p>
 * Quits the current application and launches the standalone bootloader (if installed) The
 * function returns an error if the standalone bootloader is not present
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspLaunchStandaloneBootloaderRequest extends EzspFrameRequest {
    public static final int FRAME_ID = 0x8F;

    /**
     * Controls the mode in which the standalone bootloader will run. See the app. note for full
     * details. Options are: STANDALONE_BOOTLOADER_NORMAL_MODE: Will listen for an
     * over-the-air image transfer on the current channel with current power  settings. 
     * STANDALONE_BOOTLOADER_RECOVERY_MODE: Will listen for an over-the-air im-age transfer
     * on the default channel with default power settings. Both modes also allow an image transfer
     * to begin with XMODEM over the serial protocol's Bootloader Frame.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int mode;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspLaunchStandaloneBootloaderRequest() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * Controls the mode in which the standalone bootloader will run. See the app. note for full
     * details. Options are: STANDALONE_BOOTLOADER_NORMAL_MODE: Will listen for an
     * over-the-air image transfer on the current channel with current power  settings. 
     * STANDALONE_BOOTLOADER_RECOVERY_MODE: Will listen for an over-the-air im-age transfer
     * on the default channel with default power settings. Both modes also allow an image transfer
     * to begin with XMODEM over the serial protocol's Bootloader Frame.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current mode as {@link int}
     */
    public int getMode() {
        return mode;
    }

    /**
     * Controls the mode in which the standalone bootloader will run. See the app. note for full
     * details. Options are: STANDALONE_BOOTLOADER_NORMAL_MODE: Will listen for an
     * over-the-air image transfer on the current channel with current power  settings. 
     * STANDALONE_BOOTLOADER_RECOVERY_MODE: Will listen for an over-the-air im-age transfer
     * on the default channel with default power settings. Both modes also allow an image transfer
     * to begin with XMODEM over the serial protocol's Bootloader Frame.
     *
     * @param mode the mode to set as {@link int}
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public int[] serialize(int ezspVersion) {
        // Serialize the header
        serializeHeader(ezspVersion, serializer);

        // Serialize the fields
        serializer.serializeUInt8(mode);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(90);
        builder.append("EzspLaunchStandaloneBootloaderRequest [networkId=");
        builder.append(networkId);
        builder.append(", mode=");
        builder.append(mode);
        builder.append(']');
        return builder.toString();
    }
}
