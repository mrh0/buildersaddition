package com.mrh0.buildersaddition.event.opts;

public class RegOptions<T> {
	public boolean isEnabled = true;
	
	public T enable(boolean b) {
		isEnabled = b;
		return (T)this;
	}
}
