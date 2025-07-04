/**
 * Copyright (c) 2016-2024 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.zcl.clusters;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Future;

import javax.annotation.Generated;

import com.zsmartsystems.zigbee.CommandResult;
import com.zsmartsystems.zigbee.ZigBeeEndpoint;
import com.zsmartsystems.zigbee.zcl.ZclAttribute;
import com.zsmartsystems.zigbee.zcl.ZclCluster;
import com.zsmartsystems.zigbee.zcl.protocol.ZclDataType;

/**
 * <b>Thermostat User Interface Configuration</b> cluster implementation (<i>Cluster ID 0x0204</i>).
 * <p>
 * This cluster provides an interface to allow configuration of the user interface for a
 * thermostat, or a thermostat controller device, that supports a keypad and LCD screen.
 * <p>
 * Code is auto-generated. Modifications may be overwritten!
 */
@Generated(value = "com.zsmartsystems.zigbee.autocode.ZigBeeCodeGenerator", date = "2025-06-16T13:36:22Z")
public class ZclThermostatUserInterfaceConfigurationCluster extends ZclCluster {
    /**
     * The ZigBee Cluster Library Cluster ID
     */
    public static final int CLUSTER_ID = 0x0204;

    /**
     * The ZigBee Cluster Library Cluster Name
     */
    public static final String CLUSTER_NAME = "Thermostat User Interface Configuration";

    // Attribute constants
    /**
     * The TemperatureDisplayMode attribute specifies the units of the temperature
     * displayed on the thermostat screen.
     */
    public static final int ATTR_TEMPERATUREDISPLAYMODE = 0x0000;
    /**
     * The KeypadLockout attribute specifies the level of functionality that is available to
     * the user via the keypad.
     */
    public static final int ATTR_KEYPADLOCKOUT = 0x0001;
    /**
     * The ScheduleProgrammingVisibility attribute is used to hide the weekly schedule
     * programming functionality or menu on a thermostat from a user to prevent local user
     * programming of the weekly schedule. The schedule programming may still be performed
     * via a remote interface, and the thermostat may operate in schedule programming mode.
     * <p>
     * This command is designed to prevent local tampering with or disabling of schedules that
     * may have been programmed by users or service providers via a more capable remote
     * interface. The programming schedule shall continue to run even though it is not visible
     * to the user locally at the thermostat.
     */
    public static final int ATTR_SCHEDULEPROGRAMMINGVISIBILITY = 0x0002;
    public static final int ATTR_DISPLAYLOCALTEMPERATURE = 0x0500;
    public static final int ATTR_DISPLAYFANCONTROL = 0x0501;
    public static final int ATTR_DISPLAYINFORMATIONS = 0x0502;

    @Override
    protected Map<Integer, ZclAttribute> initializeClientAttributes() {
        Map<Integer, ZclAttribute> attributeMap = super.initializeClientAttributes();

        return attributeMap;
    }

    @Override
    protected Map<Integer, ZclAttribute> initializeServerAttributes() {
        Map<Integer, ZclAttribute> attributeMap = super.initializeServerAttributes();

        attributeMap.put(ATTR_TEMPERATUREDISPLAYMODE, new ZclAttribute(this, ATTR_TEMPERATUREDISPLAYMODE, "Temperature Display Mode", ZclDataType.ENUMERATION_8_BIT, false, true, true, true));
        attributeMap.put(ATTR_KEYPADLOCKOUT, new ZclAttribute(this, ATTR_KEYPADLOCKOUT, "Keypad Lockout", ZclDataType.ENUMERATION_8_BIT, false, true, true, true));
        attributeMap.put(ATTR_SCHEDULEPROGRAMMINGVISIBILITY, new ZclAttribute(this, ATTR_SCHEDULEPROGRAMMINGVISIBILITY, "Schedule Programming Visibility", ZclDataType.ENUMERATION_8_BIT, false, true, true, true));
        attributeMap.put(ATTR_DISPLAYLOCALTEMPERATURE, new ZclAttribute(this, ATTR_DISPLAYLOCALTEMPERATURE, "Display Local Temperature", ZclDataType.UNSIGNED_8_BIT_INTEGER, true, true, true, true));
        attributeMap.put(ATTR_DISPLAYFANCONTROL, new ZclAttribute(this, ATTR_DISPLAYFANCONTROL, "Display Fan Control", ZclDataType.UNSIGNED_8_BIT_INTEGER, true, true, true, true));
        attributeMap.put(ATTR_DISPLAYINFORMATIONS, new ZclAttribute(this, ATTR_DISPLAYINFORMATIONS, "Display Informations", ZclDataType.BITMAP_8_BIT, true, true, true, true));

        return attributeMap;
    }


    /**
     * Default constructor to create a Thermostat User Interface Configuration cluster.
     *
     * @param zigbeeEndpoint the {@link ZigBeeEndpoint} this cluster is contained within
     */
    public ZclThermostatUserInterfaceConfigurationCluster(final ZigBeeEndpoint zigbeeEndpoint) {
        super(zigbeeEndpoint, CLUSTER_ID, CLUSTER_NAME);
    }

    /**
     * Set the <i>Temperature Display Mode</i> attribute [attribute ID <b>0x0000</b>].
     * <p>
     * The TemperatureDisplayMode attribute specifies the units of the temperature
     * displayed on the thermostat screen.
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param temperatureDisplayMode the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #writeAttribute(int attributeId, Object value)}
     */
    @Deprecated
    public Future<CommandResult> setTemperatureDisplayMode(final Integer temperatureDisplayMode) {
        return write(serverAttributes.get(ATTR_TEMPERATUREDISPLAYMODE), temperatureDisplayMode);
    }

    /**
     * Get the <i>Temperature Display Mode</i> attribute [attribute ID <b>0x0000</b>].
     * <p>
     * The TemperatureDisplayMode attribute specifies the units of the temperature
     * displayed on the thermostat screen.
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #readAttribute(int attributeId)}
     */
    @Deprecated
    public Future<CommandResult> getTemperatureDisplayModeAsync() {
        return read(serverAttributes.get(ATTR_TEMPERATUREDISPLAYMODE));
    }

    /**
     * Synchronously get the <i>Temperature Display Mode</i> attribute [attribute ID <b>0x0000</b>].
     * <p>
     * The TemperatureDisplayMode attribute specifies the units of the temperature
     * displayed on the thermostat screen.
     * <p>
     * This method can return cached data if the attribute has already been received.
     * The parameter <i>refreshPeriod</i> is used to control this. If the attribute has been received
     * within <i>refreshPeriod</i> milliseconds, then the method will immediately return the last value
     * received. If <i>refreshPeriod</i> is set to 0, then the attribute will always be updated.
     * <p>
     * This method will block until the response is received or a timeout occurs unless the current value is returned.
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param refreshPeriod the maximum age of the data (in milliseconds) before an update is needed
     * @return the {@link Integer} attribute value, or null on error
     * @deprecated As of release 1.2.0, replaced by {@link #ZclAttribute#readValue(long refreshPeriod)}
     */
    @Deprecated
    public Integer getTemperatureDisplayMode(final long refreshPeriod) {
        if (serverAttributes.get(ATTR_TEMPERATUREDISPLAYMODE).isLastValueCurrent(refreshPeriod)) {
            return (Integer) serverAttributes.get(ATTR_TEMPERATUREDISPLAYMODE).getLastValue();
        }

        return (Integer) readSync(serverAttributes.get(ATTR_TEMPERATUREDISPLAYMODE));
    }

    /**
     * Set the <i>Keypad Lockout</i> attribute [attribute ID <b>0x0001</b>].
     * <p>
     * The KeypadLockout attribute specifies the level of functionality that is available to
     * the user via the keypad.
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param keypadLockout the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #writeAttribute(int attributeId, Object value)}
     */
    @Deprecated
    public Future<CommandResult> setKeypadLockout(final Integer keypadLockout) {
        return write(serverAttributes.get(ATTR_KEYPADLOCKOUT), keypadLockout);
    }

    /**
     * Get the <i>Keypad Lockout</i> attribute [attribute ID <b>0x0001</b>].
     * <p>
     * The KeypadLockout attribute specifies the level of functionality that is available to
     * the user via the keypad.
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #readAttribute(int attributeId)}
     */
    @Deprecated
    public Future<CommandResult> getKeypadLockoutAsync() {
        return read(serverAttributes.get(ATTR_KEYPADLOCKOUT));
    }

    /**
     * Synchronously get the <i>Keypad Lockout</i> attribute [attribute ID <b>0x0001</b>].
     * <p>
     * The KeypadLockout attribute specifies the level of functionality that is available to
     * the user via the keypad.
     * <p>
     * This method can return cached data if the attribute has already been received.
     * The parameter <i>refreshPeriod</i> is used to control this. If the attribute has been received
     * within <i>refreshPeriod</i> milliseconds, then the method will immediately return the last value
     * received. If <i>refreshPeriod</i> is set to 0, then the attribute will always be updated.
     * <p>
     * This method will block until the response is received or a timeout occurs unless the current value is returned.
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param refreshPeriod the maximum age of the data (in milliseconds) before an update is needed
     * @return the {@link Integer} attribute value, or null on error
     * @deprecated As of release 1.2.0, replaced by {@link #ZclAttribute#readValue(long refreshPeriod)}
     */
    @Deprecated
    public Integer getKeypadLockout(final long refreshPeriod) {
        if (serverAttributes.get(ATTR_KEYPADLOCKOUT).isLastValueCurrent(refreshPeriod)) {
            return (Integer) serverAttributes.get(ATTR_KEYPADLOCKOUT).getLastValue();
        }

        return (Integer) readSync(serverAttributes.get(ATTR_KEYPADLOCKOUT));
    }

    /**
     * Set the <i>Schedule Programming Visibility</i> attribute [attribute ID <b>0x0002</b>].
     * <p>
     * The ScheduleProgrammingVisibility attribute is used to hide the weekly schedule
     * programming functionality or menu on a thermostat from a user to prevent local user
     * programming of the weekly schedule. The schedule programming may still be performed
     * via a remote interface, and the thermostat may operate in schedule programming mode.
     * <p>
     * This command is designed to prevent local tampering with or disabling of schedules that
     * may have been programmed by users or service providers via a more capable remote
     * interface. The programming schedule shall continue to run even though it is not visible
     * to the user locally at the thermostat.
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param scheduleProgrammingVisibility the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #writeAttribute(int attributeId, Object value)}
     */
    @Deprecated
    public Future<CommandResult> setScheduleProgrammingVisibility(final Integer scheduleProgrammingVisibility) {
        return write(serverAttributes.get(ATTR_SCHEDULEPROGRAMMINGVISIBILITY), scheduleProgrammingVisibility);
    }

    /**
     * Get the <i>Schedule Programming Visibility</i> attribute [attribute ID <b>0x0002</b>].
     * <p>
     * The ScheduleProgrammingVisibility attribute is used to hide the weekly schedule
     * programming functionality or menu on a thermostat from a user to prevent local user
     * programming of the weekly schedule. The schedule programming may still be performed
     * via a remote interface, and the thermostat may operate in schedule programming mode.
     * <p>
     * This command is designed to prevent local tampering with or disabling of schedules that
     * may have been programmed by users or service providers via a more capable remote
     * interface. The programming schedule shall continue to run even though it is not visible
     * to the user locally at the thermostat.
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #readAttribute(int attributeId)}
     */
    @Deprecated
    public Future<CommandResult> getScheduleProgrammingVisibilityAsync() {
        return read(serverAttributes.get(ATTR_SCHEDULEPROGRAMMINGVISIBILITY));
    }

    /**
     * Synchronously get the <i>Schedule Programming Visibility</i> attribute [attribute ID <b>0x0002</b>].
     * <p>
     * The ScheduleProgrammingVisibility attribute is used to hide the weekly schedule
     * programming functionality or menu on a thermostat from a user to prevent local user
     * programming of the weekly schedule. The schedule programming may still be performed
     * via a remote interface, and the thermostat may operate in schedule programming mode.
     * <p>
     * This command is designed to prevent local tampering with or disabling of schedules that
     * may have been programmed by users or service providers via a more capable remote
     * interface. The programming schedule shall continue to run even though it is not visible
     * to the user locally at the thermostat.
     * <p>
     * This method can return cached data if the attribute has already been received.
     * The parameter <i>refreshPeriod</i> is used to control this. If the attribute has been received
     * within <i>refreshPeriod</i> milliseconds, then the method will immediately return the last value
     * received. If <i>refreshPeriod</i> is set to 0, then the attribute will always be updated.
     * <p>
     * This method will block until the response is received or a timeout occurs unless the current value is returned.
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is OPTIONAL
     *
     * @param refreshPeriod the maximum age of the data (in milliseconds) before an update is needed
     * @return the {@link Integer} attribute value, or null on error
     * @deprecated As of release 1.2.0, replaced by {@link #ZclAttribute#readValue(long refreshPeriod)}
     */
    @Deprecated
    public Integer getScheduleProgrammingVisibility(final long refreshPeriod) {
        if (serverAttributes.get(ATTR_SCHEDULEPROGRAMMINGVISIBILITY).isLastValueCurrent(refreshPeriod)) {
            return (Integer) serverAttributes.get(ATTR_SCHEDULEPROGRAMMINGVISIBILITY).getLastValue();
        }

        return (Integer) readSync(serverAttributes.get(ATTR_SCHEDULEPROGRAMMINGVISIBILITY));
    }

    /**
     * Set the <i>Display Local Temperature</i> attribute [attribute ID <b>0x0500</b>].
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is MANDATORY
     *
     * @param displayLocalTemperature the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #writeAttribute(int attributeId, Object value)}
     */
    @Deprecated
    public Future<CommandResult> setDisplayLocalTemperature(final Integer displayLocalTemperature) {
        return write(serverAttributes.get(ATTR_DISPLAYLOCALTEMPERATURE), displayLocalTemperature);
    }

    /**
     * Get the <i>Display Local Temperature</i> attribute [attribute ID <b>0x0500</b>].
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is MANDATORY
     *
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #readAttribute(int attributeId)}
     */
    @Deprecated
    public Future<CommandResult> getDisplayLocalTemperatureAsync() {
        return read(serverAttributes.get(ATTR_DISPLAYLOCALTEMPERATURE));
    }

    /**
     * Synchronously get the <i>Display Local Temperature</i> attribute [attribute ID <b>0x0500</b>].
     * <p>
     * This method can return cached data if the attribute has already been received.
     * The parameter <i>refreshPeriod</i> is used to control this. If the attribute has been received
     * within <i>refreshPeriod</i> milliseconds, then the method will immediately return the last value
     * received. If <i>refreshPeriod</i> is set to 0, then the attribute will always be updated.
     * <p>
     * This method will block until the response is received or a timeout occurs unless the current value is returned.
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is MANDATORY
     *
     * @param refreshPeriod the maximum age of the data (in milliseconds) before an update is needed
     * @return the {@link Integer} attribute value, or null on error
     * @deprecated As of release 1.2.0, replaced by {@link #ZclAttribute#readValue(long refreshPeriod)}
     */
    @Deprecated
    public Integer getDisplayLocalTemperature(final long refreshPeriod) {
        if (serverAttributes.get(ATTR_DISPLAYLOCALTEMPERATURE).isLastValueCurrent(refreshPeriod)) {
            return (Integer) serverAttributes.get(ATTR_DISPLAYLOCALTEMPERATURE).getLastValue();
        }

        return (Integer) readSync(serverAttributes.get(ATTR_DISPLAYLOCALTEMPERATURE));
    }

    /**
     * Set reporting for the <i>Display Local Temperature</i> attribute [attribute ID <b>0x0500</b>].
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is MANDATORY
     *
     * @param minInterval minimum reporting period
     * @param maxInterval maximum reporting period
     * @param reportableChange {@link Object} delta required to trigger report
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #setReporting(int attributeId, int minInterval, int maxInterval, Object reportableChange)}
     */
    @Deprecated
    public Future<CommandResult> setDisplayLocalTemperatureReporting(final int minInterval, final int maxInterval, final Object reportableChange) {
        return setReporting(serverAttributes.get(ATTR_DISPLAYLOCALTEMPERATURE), minInterval, maxInterval, reportableChange);
    }

    /**
     * Set the <i>Display Fan Control</i> attribute [attribute ID <b>0x0501</b>].
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is MANDATORY
     *
     * @param displayFanControl the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #writeAttribute(int attributeId, Object value)}
     */
    @Deprecated
    public Future<CommandResult> setDisplayFanControl(final Integer displayFanControl) {
        return write(serverAttributes.get(ATTR_DISPLAYFANCONTROL), displayFanControl);
    }

    /**
     * Get the <i>Display Fan Control</i> attribute [attribute ID <b>0x0501</b>].
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is MANDATORY
     *
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #readAttribute(int attributeId)}
     */
    @Deprecated
    public Future<CommandResult> getDisplayFanControlAsync() {
        return read(serverAttributes.get(ATTR_DISPLAYFANCONTROL));
    }

    /**
     * Synchronously get the <i>Display Fan Control</i> attribute [attribute ID <b>0x0501</b>].
     * <p>
     * This method can return cached data if the attribute has already been received.
     * The parameter <i>refreshPeriod</i> is used to control this. If the attribute has been received
     * within <i>refreshPeriod</i> milliseconds, then the method will immediately return the last value
     * received. If <i>refreshPeriod</i> is set to 0, then the attribute will always be updated.
     * <p>
     * This method will block until the response is received or a timeout occurs unless the current value is returned.
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is MANDATORY
     *
     * @param refreshPeriod the maximum age of the data (in milliseconds) before an update is needed
     * @return the {@link Integer} attribute value, or null on error
     * @deprecated As of release 1.2.0, replaced by {@link #ZclAttribute#readValue(long refreshPeriod)}
     */
    @Deprecated
    public Integer getDisplayFanControl(final long refreshPeriod) {
        if (serverAttributes.get(ATTR_DISPLAYFANCONTROL).isLastValueCurrent(refreshPeriod)) {
            return (Integer) serverAttributes.get(ATTR_DISPLAYFANCONTROL).getLastValue();
        }

        return (Integer) readSync(serverAttributes.get(ATTR_DISPLAYFANCONTROL));
    }

    /**
     * Set reporting for the <i>Display Fan Control</i> attribute [attribute ID <b>0x0501</b>].
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is MANDATORY
     *
     * @param minInterval minimum reporting period
     * @param maxInterval maximum reporting period
     * @param reportableChange {@link Object} delta required to trigger report
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #setReporting(int attributeId, int minInterval, int maxInterval, Object reportableChange)}
     */
    @Deprecated
    public Future<CommandResult> setDisplayFanControlReporting(final int minInterval, final int maxInterval, final Object reportableChange) {
        return setReporting(serverAttributes.get(ATTR_DISPLAYFANCONTROL), minInterval, maxInterval, reportableChange);
    }

    /**
     * Set the <i>Display Informations</i> attribute [attribute ID <b>0x0502</b>].
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is MANDATORY
     *
     * @param displayInformations the {@link Integer} attribute value to be set
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #writeAttribute(int attributeId, Object value)}
     */
    @Deprecated
    public Future<CommandResult> setDisplayInformations(final Integer displayInformations) {
        return write(serverAttributes.get(ATTR_DISPLAYINFORMATIONS), displayInformations);
    }

    /**
     * Get the <i>Display Informations</i> attribute [attribute ID <b>0x0502</b>].
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is MANDATORY
     *
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #readAttribute(int attributeId)}
     */
    @Deprecated
    public Future<CommandResult> getDisplayInformationsAsync() {
        return read(serverAttributes.get(ATTR_DISPLAYINFORMATIONS));
    }

    /**
     * Synchronously get the <i>Display Informations</i> attribute [attribute ID <b>0x0502</b>].
     * <p>
     * This method can return cached data if the attribute has already been received.
     * The parameter <i>refreshPeriod</i> is used to control this. If the attribute has been received
     * within <i>refreshPeriod</i> milliseconds, then the method will immediately return the last value
     * received. If <i>refreshPeriod</i> is set to 0, then the attribute will always be updated.
     * <p>
     * This method will block until the response is received or a timeout occurs unless the current value is returned.
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is MANDATORY
     *
     * @param refreshPeriod the maximum age of the data (in milliseconds) before an update is needed
     * @return the {@link Integer} attribute value, or null on error
     * @deprecated As of release 1.2.0, replaced by {@link #ZclAttribute#readValue(long refreshPeriod)}
     */
    @Deprecated
    public Integer getDisplayInformations(final long refreshPeriod) {
        if (serverAttributes.get(ATTR_DISPLAYINFORMATIONS).isLastValueCurrent(refreshPeriod)) {
            return (Integer) serverAttributes.get(ATTR_DISPLAYINFORMATIONS).getLastValue();
        }

        return (Integer) readSync(serverAttributes.get(ATTR_DISPLAYINFORMATIONS));
    }

    /**
     * Set reporting for the <i>Display Informations</i> attribute [attribute ID <b>0x0502</b>].
     * <p>
     * The attribute is of type {@link Integer}.
     * <p>
     * The implementation of this attribute by a device is MANDATORY
     *
     * @param minInterval minimum reporting period
     * @param maxInterval maximum reporting period
     * @return the {@link Future<CommandResult>} command result future
     * @deprecated As of release 1.2.0, replaced by {@link #setReporting(int attributeId, int minInterval, int maxInterval)}
     */
    @Deprecated
    public Future<CommandResult> setDisplayInformationsReporting(final int minInterval, final int maxInterval) {
        return setReporting(serverAttributes.get(ATTR_DISPLAYINFORMATIONS), minInterval, maxInterval);
    }
}
