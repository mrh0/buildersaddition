package com.mrh0.buildersaddition.blocks;

import java.util.Random;
import com.mrh0.buildersaddition.blocks.base.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Candle extends BaseBlock {

	public static final BooleanProperty LIT = BooleanProperty.create("lit");
	public static final DirectionProperty FACING = DirectionProperty.create("facing",
			p -> p.getIndex() != 1);
	protected static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(6d, 3d, 0d, 10d, 13d, 4d);
	protected static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(6d, 3d, 12d, 10d, 13d, 16d);
	protected static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(0d, 3d, 6d, 4d, 13d, 10d);
	protected static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(12d, 3d, 6d, 16d, 13d, 10d);
	protected static final VoxelShape SHAPE_DOWN = Block.makeCuboidShape(6d, 0d, 6d, 10d, 9d, 10d);
	
	private IParticleData parts;
	
	public Candle(String name, IParticleData parts) {
		super(name, Properties.from(Blocks.TORCH).setLightLevel((s) -> s.get(LIT).booleanValue() ? 15 : 0));
		setDefaultState(getDefaultState().with(LIT, true).with(FACING, Direction.NORTH));
		this.parts = parts;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch(state.get(FACING)) {
			case NORTH:
				return SHAPE_NORTH;
			case EAST:
				return SHAPE_EAST;
			case SOUTH:
				return SHAPE_SOUTH;
			case WEST:
				return SHAPE_WEST;
			case DOWN:
				return SHAPE_DOWN;
			default:
				return SHAPE_NORTH;
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		if(c.getFace() == Direction.DOWN || c.getFace() == Direction.UP) {
			if(c.getPlayer().isCreative())
				return getDefaultState().with(LIT, true).with(FACING, Direction.DOWN);
			return getDefaultState().with(LIT, false).with(FACING, Direction.DOWN);
		}
		if(c.getPlayer().isCreative())
			return getDefaultState().with(LIT, true).with(FACING, c.getFace().rotateY());
		return getDefaultState().with(LIT, false).with(FACING, c.getFace().rotateY());
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		ItemStack item = player.getHeldItem(handIn);
		boolean lit = state.get(LIT);
		if(player.isCreative()) {
			if(!lit) {
				worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1, 1, false);
				if(!worldIn.isRemote())
					worldIn.setBlockState(pos, getDefaultState().with(LIT, true).with(FACING, state.get(FACING)));
			}
			else {
				worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 1, 1, false);
				if(!worldIn.isRemote())
					worldIn.setBlockState(pos, getDefaultState().with(LIT, false).with(FACING, state.get(FACING)));
			}
		}
		else if(item.isEmpty()) {
			if(lit) {
				worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 1, 1, false);
				if(!worldIn.isRemote())
					worldIn.setBlockState(pos, getDefaultState().with(LIT, false).with(FACING, state.get(FACING)));
			}
		}
		else if(item.getItem() == Items.FLINT_AND_STEEL) {
			if(!lit) {
				worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1, 1, false);
				if(!worldIn.isRemote()) {
					worldIn.setBlockState(pos, getDefaultState().with(LIT, true).with(FACING, state.get(FACING)));
					item.damageItem(1, player, (e) -> {});
				}
			}
		}
		
		
		if(worldIn.isRemote())
			return ActionResultType.SUCCESS;
		return ActionResultType.CONSUME;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(LIT, FACING);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		double x = .5d;
		double y = 13d/16d;
		double z = .5d;
		double o = 3d/16d;
		switch(stateIn.get(FACING)) {
			case EAST:
				z = 1 - o;
				break;
			case NORTH:
				x = 1 - o;
				break;
			case SOUTH:
				x = o;
				break;
			case WEST:
				z = o;
				break;
			case DOWN:
				y = 11d/16d;
				break;
			default:
				break;
		}
		if(!stateIn.get(LIT).booleanValue())
			return;
		double d0 = (double)pos.getX() + x;
		double d1 = (double)pos.getY() + y;
		double d2 = (double)pos.getZ() + z;
		worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(parts, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}
}
