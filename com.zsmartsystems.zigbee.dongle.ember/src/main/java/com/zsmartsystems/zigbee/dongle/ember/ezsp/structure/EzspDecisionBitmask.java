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
 * Class to implement the Ember Enumeration <b>EzspDecisionBitmask</b>.
 * <p>
 * This is the policy decision bitmask that controls the trust center decision strategies. The
 * bitmask is modified and extracted from the EzspDecisionId for supporting bitmask
 * operations.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public enum EzspDecisionBitmask {
    /**
     * Default unknown value
     */
    UNKNOWN(-1),

    /**
     * Disallow joins and rejoins.
     */
    EZSP_DECISION_BITMASK_DEFAULT_CONFIGURATION(0x0000),

    /**
     * Send the network key to all joining devices.
     */
    EZSP_DECISION_ALLOW_JOINS(0x0001),

    /**
     * Send the network key to all rejoining devices.
     */
    EZSP_DECISION_ALLOW_UNSECURED_REJOINS(0x0002),

    /**
     * Send the network key in the clear.
     */
    EZSP_DECISION_SEND_KEY_IN_CLEAR(0x0004),

    /**
     * Do nothing for unsecured rejoins.
     */
    EZSP_DECISION_IGNORE_UNSECURED_REJOINS(0x0008),

    /**
     * Allow joins if there is an entry in the transient key table
     */
    EZSP_DECISION_JOINS_USE_INSTALL_CODE_KEY(0x0010),

    /**
     * Delay sending the network key to a new joining device.
     */
    EZSP_DECISION_DEFER_JOINS(0x0020);

    /**
     * A mapping between the integer code and its corresponding type to
     * facilitate lookup by code.
     */
    private static Map<Integer, EzspDecisionBitmask> codeMapping;

    private int key;

    static {
        codeMapping = new HashMap<Integer, EzspDecisionBitmask>();
        for (EzspDecisionBitmask s : values()) {
            codeMapping.put(s.key, s);
        }
    }

    private EzspDecisionBitmask(int key) {
        this.key = key;
    }

    /**
     * Lookup function based on the EmberStatus type code. Returns null if the
     * code does not exist.
     *
     * @param code the code to lookup
     * @return enumeration value of the alarm type.
     */
    public static EzspDecisionBitmask getEzspDecisionBitmask(int code) {
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
