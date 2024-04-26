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
 * Class to implement the Ember Enumeration <b>EmberNetworkStatus</b>.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public enum EmberNetworkStatus {
    /**
     * Default unknown value
     */
    UNKNOWN(-1),

    /**
     * The node is not associated with a network in any way.
     */
    EMBER_NO_NETWORK(0x0000),

    /**
     * The node is currently attempting to join a network.
     */
    EMBER_JOINING_NETWORK(0x0001),

    /**
     * The node is joined to a network.
     */
    EMBER_JOINED_NETWORK(0x0002),

    /**
     * The node is an end device joined to a network but its parent is not responding.
     */
    EMBER_JOINED_NETWORK_NO_PARENT(0x0003),

    /**
     * The node is in the process of leaving its current network.
     */
    EMBER_LEAVING_NETWORK(0x0004);

    /**
     * A mapping between the integer code and its corresponding type to
     * facilitate lookup by code.
     */
    private static Map<Integer, EmberNetworkStatus> codeMapping;

    private int key;

    static {
        codeMapping = new HashMap<Integer, EmberNetworkStatus>();
        for (EmberNetworkStatus s : values()) {
            codeMapping.put(s.key, s);
        }
    }

    private EmberNetworkStatus(int key) {
        this.key = key;
    }

    /**
     * Lookup function based on the EmberStatus type code. Returns null if the
     * code does not exist.
     *
     * @param code the code to lookup
     * @return enumeration value of the alarm type.
     */
    public static EmberNetworkStatus getEmberNetworkStatus(int code) {
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
