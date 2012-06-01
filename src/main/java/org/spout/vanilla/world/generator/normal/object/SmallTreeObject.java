/*
 * This file is part of Vanilla.
 *
 * Copyright (c) 2011-2012, VanillaDev <http://www.spout.org/>
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
package org.spout.vanilla.world.generator.normal.object;

import java.util.Random;

import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.BlockMaterial;

import org.spout.vanilla.material.VanillaMaterials;

public class SmallTreeObject extends TreeObject {
	//size control
	private byte leavesHeight = 3;
	private byte radiusIncrease = 0;
	// extras
	private boolean addLeavesVines = false;
	private boolean addLogVines = false;

	public SmallTreeObject(Random random, SmallTreeType treeType) {
		super(random, (byte) 4, (byte) 3, treeType.metadata);
		overridable.add(VanillaMaterials.AIR);
		overridable.add(VanillaMaterials.LEAVES);
		overridable.add(VanillaMaterials.LOG);
		overridable.add(VanillaMaterials.DIRT);
		overridable.add(VanillaMaterials.GRASS);
	}

	@Override
	public boolean canPlaceObject(World w, int x, int y, int z) {
		if (y < 1 || y + totalHeight + 2 > w.getHeight()) {
			return false;
		}
		final BlockMaterial under = w.getBlockMaterial(x, y - 1, z);
		if (under != VanillaMaterials.DIRT && under != VanillaMaterials.GRASS) {
			return false;
		}
		byte radiusToCheck = radiusIncrease;
		for (byte yy = 0; yy < totalHeight + 2; yy++) {
			if (yy == 1 || yy == totalHeight - 1) {
				radiusToCheck++;
			}
			for (byte xx = (byte) -radiusToCheck; xx < radiusToCheck + 1; xx++) {
				for (byte zz = (byte) -radiusToCheck; zz < radiusToCheck + 1; zz++) {
					if (!overridable.contains(w.getBlockMaterial(x + xx, y + yy, z + zz))) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public void placeObject(World w, int x, int y, int z) {
		w.setBlockMaterial(x, y - 1, z, VanillaMaterials.DIRT, (short) 0, w);
		for (byte yy = (byte) (totalHeight - leavesHeight); yy < totalHeight + 1; yy++) {
			final byte yRadius = (byte) (yy - totalHeight);
			final byte xzRadius = (byte) ((radiusIncrease + 1) - yRadius / 2);
			for (byte xx = (byte) -xzRadius; xx < xzRadius + 1; xx++) {
				for (byte zz = (byte) -xzRadius; zz < xzRadius + 1; zz++) {
					if (Math.abs(xx) != xzRadius || Math.abs(zz) != xzRadius || random.nextBoolean() && yRadius != 0) {
						w.setBlockMaterial(x + xx, y + yy, z + zz, VanillaMaterials.LEAVES, leavesMetadata, w);
					}
				}
			}
		}
		for (byte yy = 0; yy < totalHeight; yy++) {
			w.setBlockMaterial(x, y + yy, z, VanillaMaterials.LOG, logMetadata, w);
			if (addLogVines) {
				placeVines(w, x, y + yy, z, (byte) 3, false);
			}
		}
		if (addLeavesVines) {
			for (byte yy = (byte) (totalHeight - leavesHeight); yy < totalHeight + 1; yy++) {
				final byte yRadius = (byte) (yy - totalHeight);
				final byte xzRadius = (byte) ((radiusIncrease + 2) - yRadius / 2);
				for (byte xx = (byte) -xzRadius; xx < xzRadius + 1; xx++) {
					for (byte zz = (byte) -xzRadius; zz < xzRadius + 1; zz++) {
						if (w.getBlockMaterial(x + xx, y + yy, z + zz) == VanillaMaterials.LEAVES) {
							placeVines(w, x + xx, y + yy, z + zz, (byte) 4, true);
						}
					}
				}
			}
		}
	}

	private void placeVines(World w, int x, int y, int z, byte faceOdd, boolean grow) {
		if (w.getBlockMaterial(x + 1, y, z) == VanillaMaterials.AIR && random.nextInt(faceOdd) > 0) {
			if (grow) {
				growVines(w, x + 1, y, z, (short) 2);
			} else {
				w.setBlockMaterial(x + 1, y, z, VanillaMaterials.VINES, (short) 2, w);
			}
		}
		if (w.getBlockMaterial(x - 1, y, z) == VanillaMaterials.AIR && random.nextInt(faceOdd) > 0) {
			if (grow) {
				growVines(w, x - 1, y, z, (short) 8);
			} else {
				w.setBlockMaterial(x - 1, y, z, VanillaMaterials.VINES, (short) 8, w);
			}
		}
		if (w.getBlockMaterial(x, y, z + 1) == VanillaMaterials.AIR && random.nextInt(faceOdd) > 0) {
			if (grow) {
				growVines(w, x, y, z + 1, (short) 4);
			} else {
				w.setBlockMaterial(x, y, z + 1, VanillaMaterials.VINES, (short) 4, w);
			}
		}
		if (w.getBlockMaterial(x, y, z - 1) == VanillaMaterials.AIR && random.nextInt(faceOdd) > 0) {
			if (grow) {
				growVines(w, x, y, z - 1, (short) 1);
			} else {
				w.setBlockMaterial(x, y, z - 1, VanillaMaterials.VINES, (short) 1, w);
			}
		}
	}

	private void growVines(World world, int x, int y, int z, short facing) {
		for (byte yy = 0; yy < 5; yy++) {
			Block block = world.getBlock(x, y - yy, z);
			if (block.getMaterial() != VanillaMaterials.AIR) {
				return;
			} else {
				block.setMaterial(VanillaMaterials.VINES, facing);
			}
		}
	}

	public void addLogVines(boolean addVines) {
		this.addLogVines = addVines;
	}

	public void addLeavesVines(boolean addLeavesVines) {
		this.addLeavesVines = addLeavesVines;
	}

	public void setTreeType(SmallTreeType type) {
		leavesMetadata = type.metadata;
		logMetadata = type.metadata;
	}

	public void setLeavesRadiusIncreaseXZ(byte radiusIncrease) {
		this.radiusIncrease = radiusIncrease;
	}

	public static enum SmallTreeType {

		OAK((short) 0),
		SPRUCE((short) 1),
		BIRCH((short) 2),
		JUNGLE((short) 3);
		//
		final private short metadata;
		final private static SmallTreeType[] cache;

		static {
			cache = new SmallTreeType[SmallTreeType.values().length];
			for (SmallTreeType t : SmallTreeType.values()) {
				cache[t.getMetadata() & 0xFFFF] = t;
			}
		}

		public static SmallTreeType getType(int metadata) {
			if (metadata < 0 || metadata > 3) {
				return null;
			} else {
				return cache[metadata];
			}
		}

		private SmallTreeType(short metadata) {
			this.metadata = metadata;
		}

		public short getMetadata() {
			return metadata;
		}
	}
}
