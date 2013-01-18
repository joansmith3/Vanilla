/*
 * This file is part of Vanilla.
 *
 * Copyright (c) 2011-2012, Spout LLC <http://www.spout.org/>
 * Vanilla is licensed under the Spout License Version 1.
 *
 * Vanilla is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * Vanilla is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license, including
 * the MIT license.
 */
package org.spout.vanilla.plugin.event.inventory;

import org.spout.api.event.Cancellable;
import org.spout.api.event.Cause;
import org.spout.api.event.HandlerList;

import org.spout.vanilla.api.inventory.Container;
import org.spout.vanilla.plugin.component.substance.material.Beacon;

/**
 * Event which is called when a Beacon is opened / looked into.
 * todo implement calling of this event
 */
public class BeaconOpenEvent extends InventoryEvent implements Cancellable {
	private static HandlerList handlers = new HandlerList();
	private final Beacon beacon;
	private final Cause cause;

	public BeaconOpenEvent(Beacon beacon, Cause<?> reason) {
		super(beacon,reason);
		this.beacon = beacon;
		this.cause = reason;
	}

	/**
	 * Returns the beacon which caused this event.
	 *
	 * @return beacon
	 */
	public Container getEnchantmentTable() {
		return beacon;
	}

	/**
	 * Returns the Cause which caused the BeaconOpenEvent
	 * @return cause
	 */
	public Cause<?> getCause() {
		return cause;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		super.setCancelled(cancelled);
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
