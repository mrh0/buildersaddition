package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.arcade.ArcadeGame;
import com.mrh0.buildersaddition.arcade.ArcadeManager.GameConstructor;
import com.mrh0.buildersaddition.arcade.ArcadeScreen;
import com.mrh0.buildersaddition.arcade.games.ArcadeMenu;
import com.mrh0.buildersaddition.arcade.games.ArcadeSnake;
import com.mrh0.buildersaddition.container.ArcadeContainer;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;;

public class ArcadeTileEntity extends BlockEntity implements MenuProvider, TickingBlockEntity {

	public ArcadeScreen screen = null;
	public ArcadeGame game = null;
	public long time = 0;
	
	public ArcadeTileEntity(BlockPos pos, BlockState state) {
		super(Index.ARCADE_TILE_ENTITY_TYPE, pos, state);
		screen = new ArcadeScreen();
		//game = new QSPLArcadeGame(screen, this);
	}

	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
		return ArcadeContainer.create(windowId, inv, getBlockPos());
	}

	@Override
	public Component getDisplayName() {
		return new TranslatableComponent("container.buildersaddition.arcade");
	}

	@Override
	public void tick() {
		if(level.isClientSide() && game != null)
			game.frame(time++);
	}
	
	public void setGame(GameConstructor g) {
		this.game = g.construct(screen, this);
	}
}
