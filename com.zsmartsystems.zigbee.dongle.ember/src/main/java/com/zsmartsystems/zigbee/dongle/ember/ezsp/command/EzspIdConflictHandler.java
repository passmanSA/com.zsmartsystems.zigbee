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
 * Class to implement the Ember EZSP command <b>idConflictHandler</b>.
 * <p>
 * A callback invoked by the EmberZNet stack when an id conflict is discovered, that is, two
 * different nodes in the network were found to be using the same short id. The stack
 * automatically removes the conflicting short id from its internal tables (address,
 * binding, route, neighbor, and child tables). The application should discontinue any other
 * use of the id.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspIdConflictHandler extends EzspFrameResponse {
    public static final int FRAME_ID = 0x7C;

    /**
     * The short id for which a conflict was detected.
     * <p>
     * EZSP type is <i>EmberNodeId</i> - Java type is {@link int}
     */
    private int id;

    /**
     * Response and Handler constructor
     */
    public EzspIdConflictHandler(int ezspVersion, int[] inputBuffer) {
        // Super creates deserializer and reads header fields
        super(ezspVersion, inputBuffer);

        // Deserialize the fields
        id = deserializer.deserializeUInt16();
    }

    /**
     * The short id for which a conflict was detected.
     * <p>
     * EZSP type is <i>EmberNodeId</i> - Java type is {@link int}
     *
     * @return the current id as {@link int}
     */
    public int getId() {
        return id;
    }

    /**
     * The short id for which a conflict was detected.
     *
     * @param id the id to set as {@link int}
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(74);
        builder.append("EzspIdConflictHandler [networkId=");
        builder.append(networkId);
        builder.append(", id=");
        builder.append(String.format("%04X", id));
        builder.append(']');
        return builder.toString();
    }
}
