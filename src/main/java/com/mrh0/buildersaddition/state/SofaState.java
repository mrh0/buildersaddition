package com.mrh0.buildersaddition.state;

import net.minecraft.util.StringRepresentable;

public enum SofaState implements StringRepresentable {
	None("none"),
	Left("left"),
	Right("right"),
	Both("both");

	private String name;
	
	private SofaState(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
