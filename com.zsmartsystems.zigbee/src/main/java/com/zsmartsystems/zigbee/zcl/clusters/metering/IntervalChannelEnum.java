/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.zcl.clusters.metering;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

/**
 * Interval Channel value enumeration.
 * <p>
 * Code is auto-generated. Modifications may be overwritten!
 */
@Generated(value = "com.zsmartsystems.zigbee.autocode.ZigBeeCodeGenerator", date = "2021-01-15T11:25:03Z")
public enum IntervalChannelEnum {

    /**
     * Consumption Delivered, 0, 0x0000
     */
    CONSUMPTION_DELIVERED(0x0000),

    /**
     * Consumption Received, 1, 0x0001
     */
    CONSUMPTION_RECEIVED(0x0001);

    /**
     * A mapping between the integer code and its corresponding IntervalChannelEnum type to facilitate lookup by value.
     */
    private static Map<Integer, IntervalChannelEnum> idMap;

    static {
        idMap = new HashMap<Integer, IntervalChannelEnum>();
        for (IntervalChannelEnum enumValue : values()) {
            idMap.put(enumValue.key, enumValue);
        }
    }

    private final int key;

    private IntervalChannelEnum(final int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public static IntervalChannelEnum getByValue(final int value) {
        return idMap.get(value);
    }
}
