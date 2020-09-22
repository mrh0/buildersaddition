package com.mrh0.buildersaddition.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.arcade.ArcadeGame;
import com.mrh0.buildersaddition.arcade.ArcadeScreen;
import com.mrh0.buildersaddition.container.ArcadeContainer;
import com.mrh0.buildersaddition.tileentity.ArcadeTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ArcadeGui extends ContainerScreen<ArcadeContainer> {

	private final ArcadeContainer screenContainer;
	private final ArcadeTileEntity te;
	public ArcadeScreen screen;
	public ArcadeGame game;
	
	private static final ResourceLocation GUI = new ResourceLocation(BuildersAddition.MODID,
			"textures/gui/container/arcade_gui.png");

	public ArcadeGui(ArcadeContainer screenContainer, PlayerInventory inv, ITextComponent tc) {
		super(screenContainer, inv, tc);
		this.screenContainer = screenContainer;
		this.te = (ArcadeTileEntity) Minecraft.getInstance().world.getTileEntity(screenContainer.pos);

		this.xSize = 336;
		this.ySize = 226;
		
		screen = te.screen;
		game = te.game;
		game.start();
	}

	@Override
	public void init(Minecraft p_231158_1_, int p_231158_2_, int p_231158_3_) {
		super.init(p_231158_1_, p_231158_2_, p_231158_3_);
		this.minecraft.keyboardListener.enableRepeatEvents(false);
	}
	
	@Override
	public void closeScreen() {
		super.closeScreen();
		this.minecraft.keyboardListener.enableRepeatEvents(true);
	}

	@Override
	public boolean mouseClicked(double x, double y, int b) {
		return super.mouseClicked(x, y, b);
	}

	@Override
	public void render(MatrixStack stack, int x, int y, float p_230430_4_) {
		super.render(stack, x, y, p_230430_4_);
		screen.renderBackground(stack, this.width, this.height);
		screen.renderForeground(stack, this.font, this.width, this.height);
		GlStateManager.disableLighting();
		GlStateManager.disableBlend();
		//Screen.fill(stack, p_238467_1_, p_238467_2_, p_238467_3_, p_238467_4_, color);
		this.func_230459_a_(stack, x, y);
	}
	
	public TileEntity getTE() {
		return te;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack stack, int x, int y) {
		this.font.func_243248_b(stack, this.title, (float)this.titleX, (float)this.titleY - 5, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		renderBackground(stack);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		AbstractGui.blit(stack, i, j - 5, 0, 0, this.xSize, this.ySize, 512, 256);
		
		//Screen.fill(stack, 0, 0, width, height, 0xFFAAAAAA);
		
		//GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		game.onKeyPressed(keyCode);
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		game.onKeyReleased(keyCode);
		return super.keyReleased(keyCode, scanCode, modifiers);
	}
	
	
}
