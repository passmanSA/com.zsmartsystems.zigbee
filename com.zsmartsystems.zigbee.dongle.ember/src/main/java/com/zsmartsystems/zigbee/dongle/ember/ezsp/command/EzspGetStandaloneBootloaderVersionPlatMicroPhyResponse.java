/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameResponse;

/**
 * Class to implement the Ember EZSP command <b>getStandaloneBootloaderVersionPlatMicroPhy</b>.
 * <p>
 * Detects if the standalone bootloader is installed, and if so returns the installed version.
 * If not return 0xffff. A returned version of 0x1234 would indicate version 1.2 build 34. Also
 * return the node's version of PLAT, MICRO and PHY.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspGetStandaloneBootloaderVersionPlatMicroPhyResponse extends EzspFrameResponse {
    public static final int FRAME_ID = 0x91;

    /**
     * BOOTLOADER_INVALID_VERSION if the standalone bootloader is not present, or the version of
     * the installed standalone bootloader.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     */
    private int bootloaderVersion;

    /**
     * The value of PLAT on the node.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int nodePlat;

    /**
     * The value of MICRO on the node.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int nodeMicro;

    /**
     * The value of PHY on the node.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int nodePhy;

    /**
     * Response and Handler constructor
     */
    public EzspGetStandaloneBootloaderVersionPlatMicroPhyResponse(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        bootloaderVersion = deserializer.deserializeUInt16();
        nodePlat = deserializer.deserializeUInt8();
        nodeMicro = deserializer.deserializeUInt8();
        nodePhy = deserializer.deserializeUInt8();
    }

    /**
     * BOOTLOADER_INVALID_VERSION if the standalone bootloader is not present, or the version of
     * the installed standalone bootloader.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     *
     * @return the current bootloaderVersion as {@link int}
     */
    public int getBootloaderVersion() {
        return bootloaderVersion;
    }

    /**
     * BOOTLOADER_INVALID_VERSION if the standalone bootloader is not present, or the version of
     * the installed standalone bootloader.
     *
     * @param bootloaderVersion the bootloaderVersion to set as {@link int}
     */
    public void setBootloaderVersion(int bootloaderVersion) {
        this.bootloaderVersion = bootloaderVersion;
    }

    /**
     * The value of PLAT on the node.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current nodePlat as {@link int}
     */
    public int getNodePlat() {
        return nodePlat;
    }

    /**
     * The value of PLAT on the node.
     *
     * @param nodePlat the nodePlat to set as {@link int}
     */
    public void setNodePlat(int nodePlat) {
        this.nodePlat = nodePlat;
    }

    /**
     * The value of MICRO on the node.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current nodeMicro as {@link int}
     */
    public int getNodeMicro() {
        return nodeMicro;
    }

    /**
     * The value of MICRO on the node.
     *
     * @param nodeMicro the nodeMicro to set as {@link int}
     */
    public void setNodeMicro(int nodeMicro) {
        this.nodeMicro = nodeMicro;
    }

    /**
     * The value of PHY on the node.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current nodePhy as {@link int}
     */
    public int getNodePhy() {
        return nodePhy;
    }

    /**
     * The value of PHY on the node.
     *
     * @param nodePhy the nodePhy to set as {@link int}
     */
    public void setNodePhy(int nodePhy) {
        this.nodePhy = nodePhy;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(182);
        builder.append("EzspGetStandaloneBootloaderVersionPlatMicroPhyResponse [networkId=");
        builder.append(networkId);
        builder.append(", bootloaderVersion=");
        builder.append(String.format("%04X", bootloaderVersion));
        builder.append(", nodePlat=");
        builder.append(nodePlat);
        builder.append(", nodeMicro=");
        builder.append(nodeMicro);
        builder.append(", nodePhy=");
        builder.append(nodePhy);
        builder.append(']');
        return builder.toString();
    }
}
