package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.tileentity.SpeakerTileEntity;
import com.mrh0.buildersaddition.tileentity.base.BaseInstrument;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class Speaker extends BaseDerivativeBlock implements EntityBlock {
	
	protected static final VoxelShape SHAPE = Block.box(2d, 1d, 2d, 14d, 16d, 14d);
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public Speaker() {
		super("speaker", Blocks.OAK_PLANKS);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
    	return this.defaultBlockState().setValue(FACING, c.getPlayer().getDirection());
    }
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand p_60507_, BlockHitResult p_60508_) {
		if (player.isSpectator()) {
            return InteractionResult.PASS;
        }
    	if (world.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
    	
    	BaseInstrument mte = (BaseInstrument) world.getBlockEntity(pos);
		NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) mte, extraData -> {
            extraData.writeBlockPos(pos);
        });
    	return InteractionResult.SUCCESS;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SpeakerTileEntity(pos, state);
	}
}
