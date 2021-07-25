package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.state.ShopSignState;
import com.mrh0.buildersaddition.tileentity.ShopSignTileEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShopSign extends BaseDerivativeBlock implements EntityBlock {

	public static final EnumProperty<ShopSignState> STATE = EnumProperty.<ShopSignState>create("state", ShopSignState.class);
	
	private static VoxelShape SHAPE_DOWN_X = Shapes.or(Block.box(1d, 0d, 7d, 15d, 14d, 9d), 
			Block.box(2d, 14d, 6d, 4d, 16d, 10d), Block.box(12d, 14d, 6d, 14d, 16d, 10d));
	private static VoxelShape SHAPE_DOWN_Z = Shapes.or(Block.box(7d, 0d, 1d, 9d, 14d, 15d), 
			Block.box(6d, 14d, 2d, 10d, 16d, 4d), Block.box(6d, 14d, 12d, 10d, 16d, 14d));
	
	private static VoxelShape SHAPE_UP_X = Shapes.or(Block.box(1d, 2d, 7d, 15d, 16d, 9d), 
			Block.box(2d, 0d, 6d, 4d, 2d, 10d), Block.box(12d, 0d, 6d, 14d, 2d, 10d));
	private static VoxelShape SHAPE_UP_Z = Shapes.or(Block.box(7d, 2d, 1d, 9d, 16d, 15d), 
			Block.box(6d, 0d, 2d, 10d, 2d, 4d), Block.box(6d, 0d, 12d, 10d, 2d, 14d));
	
	private static VoxelShape SHAPE_NORTH = Shapes.or(Block.box(7d, 1d, 0d, 9d, 15d, 14d), 
			Block.box(6d, 2d, 14d, 10d, 4d, 16d), Block.box(6d, 12d, 14d, 10d, 14d, 16d));
	private static VoxelShape SHAPE_EAST = Shapes.or(Block.box(2d, 1d, 7d, 16d, 15d, 9d), 
			Block.box(0d, 2d, 6d, 2d, 4d, 10d), Block.box(0d, 12d, 6d, 2d, 14d, 10d));
	private static VoxelShape SHAPE_SOUTH = Shapes.or(Block.box(7d, 1d, 2d, 9d, 15d, 16d), 
			Block.box(6d, 2d, 0d, 10d, 4d, 2d), Block.box(6d, 12d, 0d, 10d, 14d, 2d));
	private static VoxelShape SHAPE_WEST = Shapes.or(Block.box(0d, 1d, 7d, 14d, 15d, 9d), 
			Block.box(14d, 2d, 6d, 16d, 4d, 10d), Block.box(14d, 12d, 6d, 16d, 14d, 10d));
	
	private static VoxelShape SHAPE_NORTH_PILLAR = SHAPE_NORTH.move(0, 0, 2f/16f); // move
	private static VoxelShape SHAPE_EAST_PILLAR = SHAPE_EAST.move(-2f/16f, 0, 0);
	private static VoxelShape SHAPE_SOUTH_PILLAR = SHAPE_SOUTH.move(0, 0, -2f/16f);
	private static VoxelShape SHAPE_WEST_PILLAR = SHAPE_WEST.move(2f/16f, 0, 0);
	
	private static VoxelShape SHAPE_NORTH_FENCE = SHAPE_NORTH.move(0, 0, 6f/16f);
	private static VoxelShape SHAPE_EAST_FENCE = SHAPE_EAST.move(-6f/16f, 0, 0);
	private static VoxelShape SHAPE_SOUTH_FENCE = SHAPE_SOUTH.move(0, 0, -6f/16f);
	private static VoxelShape SHAPE_WEST_FENCE = SHAPE_WEST.move(6f/16f, 0, 0);
	
	private static VoxelShape SHAPE_NORTH_WALL = SHAPE_NORTH.move(0, 0, 4f/16f);
	private static VoxelShape SHAPE_EAST_WALL = SHAPE_EAST.move(-4f/16f, 0, 0);
	private static VoxelShape SHAPE_SOUTH_WALL = SHAPE_SOUTH.move(0, 0, -4f/16f);
	private static VoxelShape SHAPE_WEST_WALL = SHAPE_WEST.move(4f/16f, 0, 0);
	
	
	public ShopSign(String name) {
		super("shop_sign_" + name, Blocks.OAK_WOOD);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(STATE);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		return defaultBlockState().setValue(STATE, ShopSignState.getFor(c.getClickedFace(), c.getHorizontalDirection().getClockWise(), c.getClickedPos(), c.getLevel()));
	}
	
	@Override
	public RenderShape getRenderShape(BlockState p_60550_) {
		return RenderShape.MODEL;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return getShape(state.getValue(STATE));
	}
	
	public VoxelShape getShape(ShopSignState state) {
		switch(state) {
			case Up_X:
				return SHAPE_UP_X;
			case Up_Z:
				return SHAPE_UP_Z;
			case Down_X:
				return SHAPE_DOWN_X;
			case Down_Z:
				return SHAPE_DOWN_Z;
			case North:
				return SHAPE_NORTH;
			case East:
				return SHAPE_EAST;
			case South:
				return SHAPE_SOUTH;
			case West:
				return SHAPE_WEST;
				
			case North_Pillar:
				return SHAPE_NORTH_PILLAR;
			case East_Pillar:
				return SHAPE_EAST_PILLAR;
			case South_Pillar:
				return SHAPE_SOUTH_PILLAR;
			case West_Pillar:
				return SHAPE_WEST_PILLAR;
				
			case North_Wall:
				return SHAPE_NORTH_WALL;
			case East_Wall:
				return SHAPE_EAST_WALL;
			case South_Wall:
				return SHAPE_SOUTH_WALL;
			case West_Wall:
				return SHAPE_WEST_WALL;
				
			case North_Fence:
				return SHAPE_NORTH_FENCE;
			case East_Fence:
				return SHAPE_EAST_FENCE;
			case South_Fence:
				return SHAPE_SOUTH_FENCE;
			case West_Fence:
				return SHAPE_WEST_FENCE;
		}
		return SHAPE_UP_Z;
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		if(world.isClientSide())
			return InteractionResult.SUCCESS;
		BlockEntity te = world.getBlockEntity(pos);
		if(te != null) {
			if(te instanceof ShopSignTileEntity) {
				ShopSignTileEntity sste = (ShopSignTileEntity) te;
				ItemStack heald = player.getItemInHand(hand);
				//ItemStack inTE = sste.getDisplayItem();
				if(player.isCrouching()) {
					Containers.dropItemStack(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), sste.getDisplayItem());
					sste.setDisplayItem(ItemStack.EMPTY);
					world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F, false);
					return InteractionResult.CONSUME;
				}
				else if(heald != ItemStack.EMPTY) {
					if(sste.hasDisplayItem()) {
						return InteractionResult.PASS;
					}
					else {
						sste.setDisplayItem(heald);
						if(!player.isCreative())
							heald.shrink(1);
						world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F, false);
						return InteractionResult.CONSUME;
					}
				}
			}
		}
		return InteractionResult.PASS;
	}
	
	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		BlockEntity te = world.getBlockEntity(pos);
		if(te != null) {
			if(te instanceof ShopSignTileEntity) {
				ShopSignTileEntity sste = (ShopSignTileEntity) te;
				Containers.dropItemStack(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), sste.getDisplayItem());
			}
			te.setRemoved();
		}
		super.onRemove(state, world, pos, newState, isMoving);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ShopSignTileEntity(pos, state);
	}
}
