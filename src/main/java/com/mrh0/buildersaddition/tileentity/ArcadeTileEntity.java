package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.arcade.ArcadeGame;
import com.mrh0.buildersaddition.arcade.ArcadeManager.GameConstructor;
import com.mrh0.buildersaddition.arcade.ArcadeScreen;
import com.mrh0.buildersaddition.container.ArcadeContainer;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;;

public class ArcadeTileEntity extends BlockEntity implements MenuProvider {

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
		return Component.translatable("container.buildersaddition.arcade");
	}

	public static void tick(Level world, BlockPos pos, BlockState state, ArcadeTileEntity te) {
		if(world.isClientSide() && te.game != null)
			te.game.frame(te.time++);
	}
	
	public void setGame(GameConstructor g) {
		this.game = g.construct(screen, this);
	}
}
