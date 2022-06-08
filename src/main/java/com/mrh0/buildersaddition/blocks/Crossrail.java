package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.event.BlockRegistry;
import com.mrh0.buildersaddition.event.opts.BlockOptions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraftforge.registries.ForgeRegistries;

public class Crossrail extends BaseRailBlock {

	public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;

	public Crossrail() {
		super(true, Properties.copy(Blocks.RAIL));
		this.registerDefaultState(this.defaultBlockState().setValue(SHAPE, RailShape.NORTH_SOUTH));
		ForgeRegistries.BLOCKS.register("crossrail", this);
		//this.setRegistryName("crossrail");
		BlockRegistry.instance.register(this, new BlockOptions());
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(SHAPE, WATERLOGGED);
	}
	
	@Override
	public RailShape getRailDirection(BlockState state, BlockGetter world, BlockPos pos, AbstractMinecart cart) {
		if (cart == null)
			return RailShape.EAST_WEST;
		//double x = Math.abs();
		//double z = Math.abs(cart.getForward().z);
		if (cart.getMotionDirection().getAxis() == Axis.Z) {
			return RailShape.NORTH_SOUTH;
		}
		return RailShape.EAST_WEST;
	}
	
	@Override
	public boolean canMakeSlopes(BlockState state, BlockGetter world, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean isFlexibleRail(BlockState state, BlockGetter world, BlockPos pos) {
		return false;
	}

	@Override
	public Property<RailShape> getShapeProperty() {
		return SHAPE;
	}
}
