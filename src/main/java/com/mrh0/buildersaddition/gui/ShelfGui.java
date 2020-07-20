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
		
		this.field_238745_s_ = 55;
	}

	// Render
	public void func_230430_a_(MatrixStack stack, int p_230430_2_, int p_230430_3_, float ticks) {
		this.func_230446_a_(stack);
		super.func_230430_a_(stack, p_230430_2_, p_230430_3_, ticks);
		this.func_230459_a_(stack, p_230430_2_, p_230430_3_);
	}
	
	//drawGuiContainerBackgroundLayer (i think)
	protected void func_230450_a_(MatrixStack stack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.field_230706_i_.getTextureManager().bindTexture(GUI);
		int i = (this.field_230708_k_ - this.xSize) / 2;
		int j = (this.field_230709_l_ - this.ySize) / 2;
		this.func_238474_b_(stack, i, j, 0, 0, this.xSize, this.ySize);
	}
}
