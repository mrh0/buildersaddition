package com.mrh0.buildersaddition.state;

import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.IStringSerializable;

public enum FullDirectionalState implements IStringSerializable {
	Up_X("up_x"),
	Up_Z("up_z"),
	Down_X("down_x"),
	Down_Z("down_z"),
	North("north"),
	East("east"),
	South("south"),
	West("west");

	private String name;
	
	private FullDirectionalState(String name) {
		this.name = name;
	}
	
	/*@Override
	public String getName() {
		return this.name;
	}*/

	@Override
	public String func_176610_l() {
		return this.name;
	}
	
	public static FullDirectionalState getFor(Direction face, Direction facing) {
		switch(face) {
			case NORTH:
				return North;
			case EAST:
				return East;
			case SOUTH:
				return South;
			case WEST:
				return West;
			case UP:
				return facing.getAxis() == Axis.X ? Up_X : Up_Z;
			case DOWN:
				return facing.getAxis() == Axis.X ? Down_X : Down_Z;
		}
		return North;
	}
	
	public Axis getAxis() {
		if(this == East || this == West || this == Up_X || this == Down_X)
			return Axis.X;
		return Axis.Z;
	}
	
	public Direction getDirection() {
		switch(this) {
			case Up_X:
				return Direction.UP;
			case Up_Z:
				return Direction.UP;
			case Down_X:
				return Direction.DOWN;
			case Down_Z:
				return Direction.DOWN;
			case North:
				return Direction.NORTH;
			case East:
				return Direction.EAST;
			case South:
				return Direction.SOUTH;
			case West:
				return Direction.WEST;
		}
		return Direction.NORTH;
	}
	
	public boolean isHorizontal() {
		return this == North || this == East || this == South || this == West;
	}
}