/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.zdo.command;

import javax.annotation.Generated;

import com.zsmartsystems.zigbee.zdo.ZdoRequest;

/**
 * User Descriptor Conf value object class.
 * <p>
 * <p>
 * The User_Desc_conf is generated by a remote device in response to a User_Desc_set directed
 * to the remote device. This command shall be unicast to the originator of the User_Desc_set
 * command.
 * <p>
 * Code is auto-generated. Modifications may be overwritten!
 */
@Generated(value = "com.zsmartsystems.zigbee.autocode.ZigBeeCodeGenerator", date = "2020-06-06T12:25:30Z")
public class UserDescriptorConf extends ZdoRequest {
    /**
     * The ZDO cluster ID.
     */
    public static int CLUSTER_ID = 0x8014;

    /**
     * Default constructor.
     *
     */
    public UserDescriptorConf() {
        clusterId = CLUSTER_ID;
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(21);
        builder.append("UserDescriptorConf [");
        builder.append(super.toString());
        builder.append(']');
        return builder.toString();
    }

}
