/**
 * Copyright (c) 2016-2023 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.dongle.ember.ezsp.command;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.EzspFrameRequest;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;

/**
 * Class to implement the Ember EZSP command <b>clearTemporaryDataMaybeStoreLinkKey283k1</b>.
 * <p>
 * Clears the temporary data associated with CBKE and the key establishment, most notably the
 * ephemeral public/private key pair. If storeLinKey is true it moves the unverified link key
 * stored in temporary storage into the link key table. Otherwise it discards the key.
 * <p>
 * This class provides methods for processing EZSP commands.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
public class EzspClearTemporaryDataMaybeStoreLinkKey283k1Request extends EzspFrameRequest {
    public static final int FRAME_ID = 0xEE;

    /**
     * A bool indicating whether to store (true) or discard (false) the unverified link key derived
     * when ezspCalculateSmacs() was previously called.
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     */
    private boolean storeLinkKey;

    /**
     * Serialiser used to serialise to binary line data
     */
    private EzspSerializer serializer;

    /**
     * Request constructor
     */
    public EzspClearTemporaryDataMaybeStoreLinkKey283k1Request() {
        frameId = FRAME_ID;
        serializer = new EzspSerializer();
    }

    /**
     * A bool indicating whether to store (true) or discard (false) the unverified link key derived
     * when ezspCalculateSmacs() was previously called.
     * <p>
     * EZSP type is <i>bool</i> - Java type is {@link boolean}
     *
     * @return the current storeLinkKey as {@link boolean}
     */
    public boolean getStoreLinkKey() {
        return storeLinkKey;
    }

    /**
     * A bool indicating whether to store (true) or discard (false) the unverified link key derived
     * when ezspCalculateSmacs() was previously called.
     *
     * @param storeLinkKey the storeLinkKey to set as {@link boolean}
     */
    public void setStoreLinkKey(boolean storeLinkKey) {
        this.storeLinkKey = storeLinkKey;
    }

    @Override
    public int[] serialize(int ezspVersion) {
        // Serialize the header
        serializeHeader(ezspVersion, serializer);

        // Serialize the fields
        serializer.serializeBool(storeLinkKey);
        return serializer.getPayload();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(104);
        builder.append("EzspClearTemporaryDataMaybeStoreLinkKey283k1Request [networkId=");
        builder.append(networkId);
        builder.append(", storeLinkKey=");
        builder.append(storeLinkKey);
        builder.append(']');
        return builder.toString();
    }
}
