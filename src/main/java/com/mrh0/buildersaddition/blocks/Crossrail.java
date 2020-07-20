package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.event.BlockRegistry;
import com.mrh0.buildersaddition.event.opts.BlockOptions;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class Crossrail extends AbstractRailBlock {

	public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;

	public Crossrail() {
		super(true, Properties.from(Blocks.RAIL));
		this.setDefaultState(this.stateContainer.getBaseState().with(SHAPE, RailShape.NORTH_SOUTH));
		this.setRegistryName("crossrail");
		BlockRegistry.instance.register(this, new BlockOptions());
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(SHAPE);
	}
	
	@Override
	public RailShape getRailDirection(BlockState state, IBlockReader world, BlockPos pos, AbstractMinecartEntity cart) {
		if (cart == null)
			return RailShape.EAST_WEST;
		//double x = Math.abs();
		//double z = Math.abs(cart.getForward().z);
		if (cart.getHorizontalFacing().getAxis() == Axis.X) {
			return RailShape.NORTH_SOUTH;
		}
		return RailShape.EAST_WEST;
	}

	@Override
	public boolean isFlexibleRail(BlockState state, IBlockReader world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean canMakeSlopes(BlockState state, IBlockReader world, BlockPos pos) {
		return false;
	}

	@Override
	public Property<RailShape> getShapeProperty() {
		return SHAPE;
	}
}
