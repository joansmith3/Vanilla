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
package org.spout.vanilla.plugin.component.substance.material;

import org.spout.api.Spout;
import org.spout.api.entity.Player;

import org.spout.vanilla.api.component.substance.material.EnchantmentTableComponent;
import org.spout.vanilla.api.enchantment.Enchantment;
import org.spout.vanilla.api.event.inventory.EnchantmentTableCloseEvent;
import org.spout.vanilla.api.event.inventory.EnchantmentTableOpenEvent;
import org.spout.vanilla.api.inventory.window.prop.EnchantmentTableProperty;

import org.spout.vanilla.plugin.component.inventory.WindowHolder;
import org.spout.vanilla.plugin.inventory.block.EnchantmentTableInventory;
import org.spout.vanilla.plugin.inventory.window.block.EnchantmentTableWindow;

/**
 * Component that represents a enchantment table in a world.
 */
public class EnchantmentTable extends EnchantmentTableComponent {
	private final EnchantmentTableInventory inventory = new EnchantmentTableInventory();

	@Override
	public EnchantmentTableInventory getInventory() {
		return inventory;
	}

	@Override
	public void onTick(float dt) {
		EnchantmentTableInventory inventory = getInventory();
		// Only display enchantment levels if there is an item and it is not enchanted
		if (inventory.has()) {
			for (Player player : viewers) {
				for (int i = 0; i < 3; i++) {
					player.get(WindowHolder.class).getActiveWindow().setProperty(i, !Enchantment.isEnchanted(inventory.get()) ? inventory.getEnchantmentLevel(i) : 0);
				}
			}
		}
	}

	@Override
	public boolean open(Player player) {
		EnchantmentTableOpenEvent event = Spout.getEventManager().callEvent(new EnchantmentTableOpenEvent(this, player));
		if (!event.isCancelled()) {
			player.get(WindowHolder.class).openWindow(new EnchantmentTableWindow(player, inventory));
			// TODO: Calculate enchantment levels for an item elsewhere, taking into account nearby books and other factors
			inventory.setEnchantmentLevel(EnchantmentTableProperty.SLOT_1, 1);
			inventory.setEnchantmentLevel(EnchantmentTableProperty.SLOT_2, 15);
			inventory.setEnchantmentLevel(EnchantmentTableProperty.SLOT_3, 30);
			return true;
		}
		return false;
	}

	@Override
	public boolean close(Player player) {
		EnchantmentTableCloseEvent event = Spout.getEventManager().callEvent(new EnchantmentTableCloseEvent(this, player));
		if (!event.isCancelled()) {
			return super.close(player);
		}
		return false;
	}
}
