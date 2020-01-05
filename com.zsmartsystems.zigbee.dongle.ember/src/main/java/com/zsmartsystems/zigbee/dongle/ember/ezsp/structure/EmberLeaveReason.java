/**
 * Copyright (c) 2016-2020 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to implement the Ember Enumeration <b>EmberLeaveReason</b>.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public enum EmberLeaveReason {
    /**
     * Default unknown value
     */
    UNKNOWN(-1),

    /**
     */
    EMBER_LEAVE_REASON_NONE(0x0000),

    /**
     */
    EMBER_LEAVE_DUE_TO_NWK_LEAVE_MESSAGE(0x0001),

    /**
     */
    EMBER_LEAVE_DUE_TO_APS_REMOVE_MESSAGE(0x0002),

    /**
     */
    EMBER_LEAVE_DUE_TO_ZDO_LEAVE_MESSAGE(0x0003),

    /**
     */
    EMBER_LEAVE_DUE_TO_ZLL_TOUCHLINK(0x0004);

    /**
     * A mapping between the integer code and its corresponding type to
     * facilitate lookup by code.
     */
    private static Map<Integer, EmberLeaveReason> codeMapping;

    private int key;

    static {
        codeMapping = new HashMap<Integer, EmberLeaveReason>();
        for (EmberLeaveReason s : values()) {
            codeMapping.put(s.key, s);
        }
    }

    private EmberLeaveReason(int key) {
        this.key = key;
    }

    /**
     * Lookup function based on the EmberStatus type code. Returns null if the
     * code does not exist.
     *
     * @param code the code to lookup
     * @return enumeration value of the alarm type.
     */
    public static EmberLeaveReason getEmberLeaveReason(int code) {
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
