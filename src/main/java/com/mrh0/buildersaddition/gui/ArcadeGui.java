package com.mrh0.buildersaddition.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.arcade.ArcadeGame;
import com.mrh0.buildersaddition.arcade.ArcadeManager.GameConstructor;
import com.mrh0.buildersaddition.arcade.ArcadeScreen;
import com.mrh0.buildersaddition.arcade.games.ArcadeMenu;
import com.mrh0.buildersaddition.container.ArcadeContainer;
import com.mrh0.buildersaddition.tileentity.ArcadeTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ArcadeGui extends AbstractContainerScreen<ArcadeContainer> {

	private final ArcadeContainer screenContainer;
	private final ArcadeTileEntity te;
	public ArcadeScreen screen;
	public ArcadeGame game;
	
	private static final ResourceLocation GUI = new ResourceLocation(BuildersAddition.MODID,
			"textures/gui/container/arcade_gui.png");

	public ArcadeGui(ArcadeContainer screenContainer, Inventory inv, Component tc) {
		super(screenContainer, inv, tc);
		this.screenContainer = screenContainer;
		this.te = (ArcadeTileEntity) Minecraft.getInstance().level.getBlockEntity(screenContainer.pos);
		
		this.imageWidth = 336;
		this.imageHeight = 226;
		//this.inventoryLabelY -= 5;
		
		screen = te.screen;
		
		setGame(ArcadeMenu::new);
	}

	@Override
	protected void renderBg(GuiGraphics gg, float ticks, int x, int y) {
		//renderBackground(gg, x, y, ticks); //renderBackground(gg)
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		//RenderSystem.setShaderTexture(0, GUI);

		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		gg.blit(GUI, i, j - 5, 0, 0, this.imageWidth, this.imageHeight, 512, 256);
	}

	public void setGame(GameConstructor g) {
		screen.setBgRenderer(null);
		screen.setFgRenderer(null);
		te.setGame(g);
		game = te.game;
		if(game instanceof ArcadeMenu)
			((ArcadeMenu)game).gui = this;
		game.start();
	}
	
	@Override
	public void resize(Minecraft p_96575_, int p_96576_, int p_96577_) {
		super.resize(p_96575_, p_96576_, p_96577_);
		//this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
	}
	
	@Override
	public void onClose() {
		super.onClose();
		//this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
	}

	@Override
	public boolean mouseClicked(double x, double y, int b) {
		return super.mouseClicked(x, y, b);
	}

	@Override
	public void render(GuiGraphics gg, int x, int y, float p_230430_4_) {
		super.render(gg, x, y, p_230430_4_);
		
		screen.renderBackground(gg, this.width, this.height);
		screen.renderForeground(gg, this.font, this.width, this.height);
		
		//GlStateManager._disableLighting();
		GlStateManager._disableBlend();
		//Screen.fill(stack, p_238467_1_, p_238467_2_, p_238467_3_, p_238467_4_, color);
		this.renderTooltip(gg, x, y);
	}
	
	public BlockEntity getTE() {
		return te;
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

	@Override
	protected void renderLabels(GuiGraphics gg, int p_282681_, int p_283686_) {
		gg.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY - 5, 4210752, false);
	}
}
