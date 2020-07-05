package com.mrh0.buildersaddition.gui;

import java.util.ArrayList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.container.BookshelfContainer;
import com.mrh0.buildersaddition.tileentity.BookshelfTileEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BookshelfGui extends ContainerScreen<BookshelfContainer> {

	private static final ResourceLocation GUI = new ResourceLocation(BuildersAddition.MODID,
			"textures/gui/container/bookshelf.png");
	public BookshelfTileEntity te;
	public BookshelfContainer screenContainer;

	private ArrayList<String> captext = new ArrayList<String>();

	public BookshelfGui(BookshelfContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.te = screenContainer.te;
		this.screenContainer = screenContainer;

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
	
	/*@Override
	protected void func_230451_b_(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
		this.field_230712_o_.func_238422_b_(p_230451_1_, this.field_230704_d_, (float)this.field_238742_p_, (float)this.field_238743_q_, 4210752);
	      this.field_230712_o_.func_238422_b_(p_230451_1_, this.playerInventory.getDisplayName(), (float)this.field_238744_r_, (float)this.field_238745_s_, 4210752);
	}*/
}
