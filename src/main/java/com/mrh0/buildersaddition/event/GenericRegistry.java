package com.mrh0.buildersaddition.event;

import java.util.ArrayList;

import com.mrh0.buildersaddition.event.opts.RegOptions;

import net.minecraftforge.registries.IForgeRegistry;

public abstract class GenericRegistry<T, O extends RegOptions<O>> {
	protected ArrayList<T> objs;
	
	public GenericRegistry() {
		objs = new ArrayList<T>();
	}
	
	public T register(T obj, O opts) {
		if(opts.isEnabled)
			objs.add(obj);
		return obj;
	}
	
	@Deprecated
	public void initAll(IForgeRegistry<T> reg) {
		for(T obj : objs) {
			init(reg, obj);
		}
	}
	
	@Deprecated
	protected abstract void init(IForgeRegistry<T> reg, T obj);
}
