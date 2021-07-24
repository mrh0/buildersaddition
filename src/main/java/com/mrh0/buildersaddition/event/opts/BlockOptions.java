package com.mrh0.buildersaddition.event.opts;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockOptions extends RegOptions<BlockOptions>{
	public boolean makeItem = true;
	public VoxelShape shape = Shapes.block();
	public ItemOptions itemOpts = new ItemOptions();
	public BlockOptions hasItem(boolean b) {
		makeItem = b;
		return this;
	}
	
	public BlockOptions setShape(VoxelShape shape) {
		this.shape = shape;
		return this;
	}
	
	public BlockOptions setItemOptions(ItemOptions itemOpts) {
		this.itemOpts = itemOpts;
		return this;
	}
}
