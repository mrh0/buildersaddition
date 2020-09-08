package com.mrh0.buildersaddition.state;

import net.minecraft.util.IStringSerializable;

public enum SofaState implements IStringSerializable {
	None("none"),
	Left("left"),
	Right("right"),
	Both("both");

	private String name;
	
	private SofaState(String name) {
		this.name = name;
	}

	@Override
	public String getString() {
		return this.name;
	}
}
