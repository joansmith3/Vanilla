/*
 * This file is part of Vanilla.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * Vanilla is licensed under the SpoutDev License Version 1.
 *
 * Vanilla is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * Vanilla is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.vanilla.material.block.solid;

import org.spout.api.entity.Entity;
import org.spout.api.event.player.PlayerInteractEvent.Action;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.inventory.ItemStack;
import org.spout.api.material.block.BlockFace;

import org.spout.vanilla.controller.VanillaControllerTypes;
import org.spout.vanilla.controller.block.JukeboxController;
import org.spout.vanilla.material.Fuel;
import org.spout.vanilla.material.block.Solid;
import org.spout.vanilla.util.Instrument;
import org.spout.vanilla.util.MoveReaction;
import org.spout.vanilla.util.VanillaPlayerUtil;

public class Jukebox extends Solid implements Fuel {

	public final float BURN_TIME = 15.f;

	public Jukebox(String name, int id) {
		super(name, id);
	}

	@Override
	public void loadProperties() {
		super.loadProperties();
		this.setController(VanillaControllerTypes.JUKEBOX);
	}

	@Override
	public JukeboxController getController(Block block) {
		return (JukeboxController) super.getController(block);
	}

	@Override
	public void onDestroy(Block block) {
		super.onDestroy(block);
		getController(block).stopMusic();
	}

	@Override
	public boolean onPlacement(Block block, short data, BlockFace against, boolean isClickedBlock) {
		if (super.onPlacement(block, data, against, isClickedBlock)) {
			block.setController(new JukeboxController());
			return true;
		}
		return false;
	}

	@Override
	public MoveReaction getMoveReaction(Block block) {
		return MoveReaction.DENY;
	}

	@Override
	public void onInteractBy(Entity entity, Block block, Action type, BlockFace clickedFace) {
		super.onInteractBy(entity, block, type, clickedFace);
		if (type == Action.RIGHT_CLICK) {
			JukeboxController controller = this.getController(block);
			controller.eject();
			ItemStack item = entity.getInventory().getCurrentItem();
			if (controller.canPlay(item)) {
				controller.getInventory().setCurrentItem(item.clone().setAmount(1));
				controller.update();
				if (!VanillaPlayerUtil.isCreative(entity)) {
					entity.getInventory().addCurrentItemAmount(-1);
				}
			}
		} else if (type == Action.RIGHT_CLICK) {
			getController(block).stopMusic();
		}
	}

	@Override
	public Instrument getInstrument() {
		return Instrument.BASSGUITAR;
	}

	@Override
	public float getFuelTime() {
		return BURN_TIME;
	}
}