/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to implement the Ember Enumeration <b>EmberPowerMode</b>.
 * <p>
 * Defines the Ember power modes.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public enum EmberPowerMode {
    /**
     * Default unknown value
     */
    UNKNOWN(-1),

    /**
     * Normal power mode and bi-directional RF transmitter output.
     */
    EMBER_TX_POWER_MODE_DEFAULT(0x0000),

    /**
     * Enable boost power mode. This is a high performance radio mode which offers increased
     * receive sensitivity and transmit power at the cost of an increase in power consumption.
     */
    EMBER_TX_POWER_MODE_BOOST(0x0001),

    /**
     * Enable the alternate transmitter output. This allows for simplified connection to an
     * external power amplifier via the RF_TX_ALT_P and RF_TX_ALT_N pins.
     */
    EMBER_TX_POWER_MODE_ALTERNATE(0x0002),

    /**
     * Enable both boost mode and the alternate transmitter output.
     */
    EMBER_TX_POWER_MODE_BOOST_AND_ALTERNATE(0x0003);

    /**
     * A mapping between the integer code and its corresponding type to
     * facilitate lookup by code.
     */
    private static Map<Integer, EmberPowerMode> codeMapping;

    private int key;

    static {
        codeMapping = new HashMap<Integer, EmberPowerMode>();
        for (EmberPowerMode s : values()) {
            codeMapping.put(s.key, s);
        }
    }

    private EmberPowerMode(int key) {
        this.key = key;
    }

    /**
     * Lookup function based on the EmberStatus type code. Returns null if the
     * code does not exist.
     *
     * @param code the code to lookup
     * @return enumeration value of the alarm type.
     */
    public static EmberPowerMode getEmberPowerMode(int code) {
        if (codeMapping.get(code) == null) {
            return UNKNOWN;
        }

        return codeMapping.get(code);
    }

    /**
     * Returns the EZSP protocol defined value for this enumeration.
     *
     * @return the EZSP protocol key
     */
    public int getKey() {
        return key;
    }
}
