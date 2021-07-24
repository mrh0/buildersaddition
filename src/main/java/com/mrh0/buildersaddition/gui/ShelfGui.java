package com.mrh0.buildersaddition.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.container.ShelfContainer;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ShelfGui extends AbstractContainerScreen<ShelfContainer> {

	private static final ResourceLocation GUI = new ResourceLocation(BuildersAddition.MODID,
			"textures/gui/container/shelf.png");
	//public ShelfTileEntity te;
	//public ShelfContainer screenContainer;

	//private ArrayList<String> captext = new ArrayList<String>();

	public ShelfGui(ShelfContainer screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		//this.te = screenContainer.te;
		//this.screenContainer = screenContainer;

		this.width = 176;
		this.height = 148;
		
		this.inventoryLabelX = 55;
	}

	// Render
	@Override
	public void render(PoseStack stack, int x, int y, float ticks) {
		this.renderBackground(stack);
		super.render(stack, x, y, ticks);
		this.renderTooltip(stack, x, y);
	}


	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int x, int y) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindForSetup(GUI);
		int i = (this.width - this.width) / 2;
		int j = (this.height - this.height) / 2;
		this.blit(stack, i, j, 0, 0, this.width, this.height);
	}
}
