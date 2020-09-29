package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.arcade.ArcadeGame;
import com.mrh0.buildersaddition.arcade.ArcadeScreen;
import com.mrh0.buildersaddition.arcade.games.ArcadeSnake;
import com.mrh0.buildersaddition.container.ArcadeContainer;
import com.mrh0.buildersaddition.qspl.QSPLArcadeGame;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ArcadeTileEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

	public ArcadeScreen screen;
	public ArcadeGame game;
	public long time;
	
	public ArcadeTileEntity() {
		super(Index.ARCADE_TILE_ENTITY_TYPE);
		screen = new ArcadeScreen();
		//game = new QSPLArcadeGame(screen, this);
		game = new ArcadeSnake(screen, this);
	}

	@Override
	public Container createMenu(int windowId, PlayerInventory inv, PlayerEntity player) {
		return ArcadeContainer.create(windowId, inv, pos);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.buildersaddition.arcade");
	}

	@Override
	public void tick() {
		if(world.isRemote())
			game.frame(time++);
	}
}
