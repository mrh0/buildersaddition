package com.mrh0.buildersaddition.items.base;

import java.util.List;
import com.mrh0.buildersaddition.event.ItemRegistry;
import com.mrh0.buildersaddition.event.opts.ItemOptions;
import com.mrh0.buildersaddition.itemgroup.ModGroup;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.registries.ForgeRegistries;

public class BaseItem extends Item {

	private String itemName;
	private ItemOptions opts;
	public BaseItem(String name, Properties properties, ItemOptions opts) {
		super(properties.tab(opts.hidden?null:ModGroup.MAIN));
		this.itemName = name;
		this.opts = opts;
		//setRegistryName(name);
		ForgeRegistries.ITEMS.register(name, this);
		ItemRegistry.instance.register(this, opts);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
		if(opts.addTooltip)
			tooltip.add(Component.translatable("tooltip.buildersaddition." + getItemName()));
		super.appendHoverText(stack, world, tooltip, flag);
	}
	
	public String getItemName() {
		return itemName;
	}
}
