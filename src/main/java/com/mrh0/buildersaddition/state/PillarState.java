package com.mrh0.buildersaddition.state;

import net.minecraft.util.StringRepresentable;

public enum PillarState implements StringRepresentable {
	None("none"),
	Bottom("bottom"),
	Top("top"),
	Both("both");

	private String name;
	
	private PillarState(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
