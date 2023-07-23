package com.mrh0.buildersaddition.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.container.ShelfContainer;

import net.minecraft.client.gui.GuiGraphics;
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

		this.imageWidth = 176;
		this.imageHeight = 148;
		
		this.inventoryLabelY = 55;
	}

	// Render
	@Override
	public void render(GuiGraphics gg, int x, int y, float ticks) {
		this.renderBackground(gg);
		super.render(gg, x, y, ticks);
		this.renderTooltip(gg, x, y);
	}


	@Override
	protected void renderBg(GuiGraphics gg, float partialTicks, int x, int y) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		//RenderSystem.setShaderTexture(0, GUI);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		gg.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);
	}
}
