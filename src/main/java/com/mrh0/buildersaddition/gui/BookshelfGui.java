package com.mrh0.buildersaddition.gui;

import java.util.ArrayList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.container.BookshelfContainer;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BookshelfGui extends AbstractContainerScreen<BookshelfContainer> {

	private static final ResourceLocation GUI = new ResourceLocation(BuildersAddition.MODID,
			"textures/gui/container/bookshelf.png");
	//public BookshelfTileEntity te;
	//public BookshelfContainer screenContainer;

	private ArrayList<String> captext = new ArrayList<String>();

	public BookshelfGui(BookshelfContainer screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		//this.te = screenContainer.te;
		//this.screenContainer = screenContainer;

		this.imageWidth = 176;
		this.imageHeight = 148;
		
		this.inventoryLabelY = 55;
	}

	// Render
	public void render(PoseStack stack, int p_230430_2_, int p_230430_3_, float ticks) {
		this.renderBackground(stack);
		super.render(stack, p_230430_2_, p_230430_3_, ticks);
		this.renderTooltip(stack, p_230430_2_, p_230430_3_);
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int x, int y) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, GUI);//this.minecraft.getTextureManager().bindForSetup(GUI);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(stack, i, j, 0, 0, this.imageWidth, this.imageHeight);
	}
	
	/*@Override
	protected void func_230451_b_(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
		this.field_230712_o_.func_238422_b_(p_230451_1_, this.field_230704_d_, (float)this.field_238742_p_, (float)this.field_238743_q_, 4210752);
	      this.field_230712_o_.func_238422_b_(p_230451_1_, this.playerInventory.getDisplayName(), (float)this.field_238744_r_, (float)this.field_238745_s_, 4210752);
	}*/
}
