/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.structure;

import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspDeserializer;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember Structure <b>EmberRouteTableEntry</b>.
 * <p>
 * A route table entry stores information about the next hop along the route to the destination.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EmberRouteTableEntry {

    /**
     * The short id of the destination. A value of 0xFFFF indicates the entry is unused.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     */
    private int destination;

    /**
     * The short id of the next hop to this destination.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     */
    private int nextHop;

    /**
     * Indicates whether this entry is active (0), being discovered (1), unused (3), or validating
     * (4).
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int status;

    /**
     * The number of seconds since this route entry was last used to send a packet.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int age;

    /**
     * Indicates whether this destination is a High RAM Concentrator (2), a Low RAM Concentrator
     * (1), or not a concentrator (0).
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int concentratorType;

    /**
     * For a High RAM Concentrator, indicates whether a route record is needed (2), has been sent
     * (1), or is no long needed (0) because a source routed message from the concentrator has been
     * received.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int routeRecordState;

    /**
     * Default Constructor
     */
    public EmberRouteTableEntry() {
    }

    public EmberRouteTableEntry(EzspDeserializer deserializer) {
        deserialize(deserializer);
    }

    /**
     * The short id of the destination. A value of 0xFFFF indicates the entry is unused.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     *
     * @return the current destination as {@link int}
     */
    public int getDestination() {
        return destination;
    }

    /**
     * The short id of the destination. A value of 0xFFFF indicates the entry is unused.
     *
     * @param destination the destination to set as {@link int}
     */
    public void setDestination(int destination) {
        this.destination = destination;
    }

    /**
     * The short id of the next hop to this destination.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     *
     * @return the current nextHop as {@link int}
     */
    public int getNextHop() {
        return nextHop;
    }

    /**
     * The short id of the next hop to this destination.
     *
     * @param nextHop the nextHop to set as {@link int}
     */
    public void setNextHop(int nextHop) {
        this.nextHop = nextHop;
    }

    /**
     * Indicates whether this entry is active (0), being discovered (1), unused (3), or validating
     * (4).
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current status as {@link int}
     */
    public int getStatus() {
        return status;
    }

    /**
     * Indicates whether this entry is active (0), being discovered (1), unused (3), or validating
     * (4).
     *
     * @param status the status to set as {@link int}
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * The number of seconds since this route entry was last used to send a packet.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current age as {@link int}
     */
    public int getAge() {
        return age;
    }

    /**
     * The number of seconds since this route entry was last used to send a packet.
     *
     * @param age the age to set as {@link int}
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Indicates whether this destination is a High RAM Concentrator (2), a Low RAM Concentrator
     * (1), or not a concentrator (0).
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current concentratorType as {@link int}
     */
    public int getConcentratorType() {
        return concentratorType;
    }

    /**
     * Indicates whether this destination is a High RAM Concentrator (2), a Low RAM Concentrator
     * (1), or not a concentrator (0).
     *
     * @param concentratorType the concentratorType to set as {@link int}
     */
    public void setConcentratorType(int concentratorType) {
        this.concentratorType = concentratorType;
    }

    /**
     * For a High RAM Concentrator, indicates whether a route record is needed (2), has been sent
     * (1), or is no long needed (0) because a source routed message from the concentrator has been
     * received.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current routeRecordState as {@link int}
     */
    public int getRouteRecordState() {
        return routeRecordState;
    }

    /**
     * For a High RAM Concentrator, indicates whether a route record is needed (2), has been sent
     * (1), or is no long needed (0) because a source routed message from the concentrator has been
     * received.
     *
     * @param routeRecordState the routeRecordState to set as {@link int}
     */
    public void setRouteRecordState(int routeRecordState) {
        this.routeRecordState = routeRecordState;
    }

    /**
     * Serialise the contents of the EZSP structure.
     *
     * @param serializer the {@link EzspSerializer} used to serialize
     */
    public int[] serialize(EzspSerializer serializer) {
        // Serialize the fields
        serializer.serializeUInt16(destination);
        serializer.serializeUInt16(nextHop);
        serializer.serializeUInt8(status);
        serializer.serializeUInt8(age);
        serializer.serializeUInt8(concentratorType);
        serializer.serializeUInt8(routeRecordState);
        return serializer.getPayload();
    }

    /**
     * Deserialise the contents of the EZSP structure.
     *
     * @param deserializer the {@link EzspDeserializer} used to deserialize
     */
    public void deserialize(EzspDeserializer deserializer) {
        // Deserialize the fields
        destination = deserializer.deserializeUInt16();
        nextHop = deserializer.deserializeUInt16();
        status = deserializer.deserializeUInt8();
        age = deserializer.deserializeUInt8();
        concentratorType = deserializer.deserializeUInt8();
        routeRecordState = deserializer.deserializeUInt8();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(173);
        builder.append("EmberRouteTableEntry [destination=");
        builder.append(destination);
        builder.append(", nextHop=");
        builder.append(nextHop);
        builder.append(", status=");
        builder.append(status);
        builder.append(", age=");
        builder.append(age);
        builder.append(", concentratorType=");
        builder.append(concentratorType);
        builder.append(", routeRecordState=");
        builder.append(routeRecordState);
        builder.append(']');
        return builder.toString();
    }
}
