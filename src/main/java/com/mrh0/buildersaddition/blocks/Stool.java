package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.blocks.base.ISeat;
import com.mrh0.buildersaddition.entity.SeatEntity;
import com.mrh0.buildersaddition.state.StoolState;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Stool extends BaseDerivativeBlock implements ISeat {
	private static VoxelShape SHAPE_PILLOW = Block.box(3d, 0d, 3d, 13d, 9d, 13d);
	private static VoxelShape SHAPE_NO_PILLOW = Block.box(3d, 0d, 3d, 13d, 8d, 13d);
	private static VoxelShape SHAPE_BASE = Block.box(2d, 6d, 2d, 14d, 8d, 14d);
	
	public static final EnumProperty<StoolState> PILLOW = EnumProperty.<StoolState>create("pillow", StoolState.class);
	
	public Stool(String name, Block source) {
		super(name, source);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(PILLOW);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		boolean type = state.getValue(PILLOW) == StoolState.None;
		if(type) {
			Item item = player.getItemInHand(hand).getItem();
			for(int i = 0; i < Index.PILLOW.length; i++) {
				if(item == Index.PILLOW[i].asItem()) {
					if(!player.isCreative())
						player.getItemInHand(hand).shrink(1);
					world.setBlockAndUpdate(pos, state.setValue(PILLOW, StoolState.fromIndex(i)));
					world.playSound(player, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1f, 1f);
					return InteractionResult.CONSUME;
				}
			}
		}
		return SeatEntity.createSeat(world, pos, player, type ? .45 - 1d/16d : .45d, type ? SoundEvents.WOOD_HIT : SoundEvents.WOOL_HIT);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		if(state.getValue(PILLOW) == StoolState.None)
			return Shapes.or(SHAPE_NO_PILLOW, SHAPE_BASE);
		return Shapes.or(SHAPE_PILLOW, SHAPE_BASE);
	}
}
