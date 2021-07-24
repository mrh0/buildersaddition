package com.mrh0.buildersaddition.state;

import net.minecraft.core.Direction.Axis;
import net.minecraft.util.StringRepresentable;

public enum BenchState implements StringRepresentable {
	North("north"),
	West("west"),
	South("south"),
	East("east"),
	None_X("none_x"),
	None_Z("none_z"),
	Both_X("both_x"),
	Both_Z("both_z");

	private String name;
	
	private BenchState(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
	
	public Axis getAxis() {
		if(this == North || this == South || this == None_Z || this == Both_Z)
			return Axis.Z;
		return Axis.X;
	}
	
	public boolean hasLegNorth() {
		return this == South || this == Both_Z;
	}
	
	public boolean hasLegWest() {
		return this == East || this == Both_X;
	}
	
	public boolean hasLegSouth() {
		return this == North || this == Both_Z;
	}
	
	public boolean hasLegEast() {
		return this == West || this == Both_X;
	}
}
