package com.mrh0.buildersaddition.gui;

import java.util.ArrayList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.container.BookshelfContainer;
import com.mrh0.buildersaddition.container.ShelfContainer;
import com.mrh0.buildersaddition.tileentity.BookshelfTileEntity;
import com.mrh0.buildersaddition.tileentity.ShelfTileEntity;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ShelfGui extends ContainerScreen<ShelfContainer> {

	private static final ResourceLocation GUI = new ResourceLocation(BuildersAddition.MODID,
			"textures/gui/container/shelf.png");
	//public ShelfTileEntity te;
	//public ShelfContainer screenContainer;

	private ArrayList<String> captext = new ArrayList<String>();

	public ShelfGui(ShelfContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		//this.te = screenContainer.te;
		//this.screenContainer = screenContainer;

		this.xSize = 176;
		this.ySize = 148;
		
		this.playerInventoryTitleY = 55;
	}

	// Render
	@Override
	public void render(MatrixStack stack, int p_230430_2_, int p_230430_3_, float ticks) {
		this.renderBackground(stack);
		super.render(stack, p_230430_2_, p_230430_3_, ticks);
		this.func_230459_a_(stack, p_230430_2_, p_230430_3_);
	}
	
	//drawGuiContainerBackgroundLayer (i think)
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.blit(stack, i, j, 0, 0, this.xSize, this.ySize);
	}
}
