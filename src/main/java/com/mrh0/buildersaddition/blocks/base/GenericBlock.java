package com.mrh0.buildersaddition.blocks.base;

import com.mrh0.buildersaddition.event.BlockRegistry;
import com.mrh0.buildersaddition.event.opts.BlockOptions;

public class GenericBlock extends BaseBlock{

	public GenericBlock(String name, Properties properties, BlockOptions opts) {
		super(name, properties);
		BlockRegistry.instance.register(this, opts);
	}
}
