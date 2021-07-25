package com.mrh0.buildersaddition.blocks;

import java.util.Random;
import com.mrh0.buildersaddition.blocks.base.BaseBlock;
import com.mrh0.buildersaddition.config.Config;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Candle extends BaseBlock {

	public static final BooleanProperty LIT = BooleanProperty.create("lit");
	public static final DirectionProperty FACING = DirectionProperty.create("facing",
			p -> p != Direction.UP);
	protected static final VoxelShape SHAPE_WEST = Block.box(6d, 3d, 0d, 10d, 13d, 4d);
	protected static final VoxelShape SHAPE_EAST = Block.box(6d, 3d, 12d, 10d, 13d, 16d);
	protected static final VoxelShape SHAPE_SOUTH = Block.box(0d, 3d, 6d, 4d, 13d, 10d);
	protected static final VoxelShape SHAPE_NORTH = Block.box(12d, 3d, 6d, 16d, 13d, 10d);
	protected static final VoxelShape SHAPE_DOWN = Block.box(6d, 0d, 6d, 10d, 9d, 10d);
	
	private ParticleOptions parts;
	
	public Candle(String name, ParticleOptions parts) {
		super(name, Properties.copy(Blocks.TORCH).lightLevel((s) -> s.getValue(LIT).booleanValue() ? 15 : 0));
		registerDefaultState(defaultBlockState().setValue(LIT, true).setValue(FACING, Direction.NORTH));
		this.parts = parts;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		switch(state.getValue(FACING)) {
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
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		if(c.getClickedFace() == Direction.DOWN || c.getClickedFace() == Direction.UP) {
			if(c.getPlayer().isCreative())
				return defaultBlockState().setValue(LIT, true).setValue(FACING, Direction.DOWN);
			return defaultBlockState().setValue(LIT, false).setValue(FACING, Direction.DOWN);
		}
		if(c.getPlayer().isCreative())
			return defaultBlockState().setValue(LIT, true).setValue(FACING, c.getClickedFace().getClockWise());
		return defaultBlockState().setValue(LIT, false).setValue(FACING, c.getClickedFace().getClockWise());
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		ItemStack item = player.getItemInHand(hand);
		boolean lit = state.getValue(LIT);
		if(player.isCreative() || !Config.REQUIRE_FLINT_AND_STEEL.get()) {
			if(!lit) {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1, 1, false);
				if(!world.isClientSide())
					world.setBlockAndUpdate(pos, defaultBlockState().setValue(LIT, true).setValue(FACING, state.getValue(FACING)));
			}
			else {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.REDSTONE_TORCH_BURNOUT, SoundSource.BLOCKS, 1, 1, false);
				if(!world.isClientSide())
					world.setBlockAndUpdate(pos, defaultBlockState().setValue(LIT, false).setValue(FACING, state.getValue(FACING)));
			}
		}
		else if(item.isEmpty()) {
			if(lit) {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.REDSTONE_TORCH_BURNOUT, SoundSource.BLOCKS, 1, 1, false);
				if(!world.isClientSide())
					world.setBlockAndUpdate(pos, defaultBlockState().setValue(LIT, false).setValue(FACING, state.getValue(FACING)));
			}
		}
		else if(item.getItem() == Items.FLINT_AND_STEEL) {
			if(!lit) {
				world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1, 1, false);
				if(!world.isClientSide()) {
					world.setBlockAndUpdate(pos, defaultBlockState().setValue(LIT, true).setValue(FACING, state.getValue(FACING)));
					item.hurtAndBreak(1, player, (e) -> {});
				}
			}
		}
		
		
		if(world.isClientSide())
			return InteractionResult.SUCCESS;
		return InteractionResult.CONSUME;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(LIT, FACING);
	}
	
	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {
		double x = .5d;
		double y = 13d/16d;
		double z = .5d;
		double o = 3d/16d;
		switch(state.getValue(FACING)) {
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
		if(!state.getValue(LIT).booleanValue())
			return;
		double d0 = (double)pos.getX() + x;
		double d1 = (double)pos.getY() + y;
		double d2 = (double)pos.getZ() + z;
		world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		world.addParticle(parts, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}
}
