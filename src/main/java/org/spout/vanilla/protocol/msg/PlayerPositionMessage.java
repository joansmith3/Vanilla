/*
 * This file is part of Vanilla (http://www.spout.org/).
 *
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
package org.spout.vanilla.protocol.msg;

import org.spout.api.protocol.Message;

public final class PlayerPositionMessage extends Message {
	private final double x, y, z, stance;
	private final boolean onGround;

	public PlayerPositionMessage(double x, double y, double z, double stance, boolean onGround) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.stance = stance;
		this.onGround = onGround;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getStance() {
		return stance;
	}

	public double getZ() {
		return z;
	}

	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public String toString() {
		return "PlayerPositionMessage{x=" + x + ",y=" + y + ",z=" + z + ",stance=" + stance + ",onGround=" + onGround + "}";
	}
}