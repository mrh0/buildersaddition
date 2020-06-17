package com.mrh0.buildersaddition.entity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.base.ISeat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SeatEntity extends Entity{

	public SeatEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(Index.SEAT_ENTITY_TYPE, worldIn);
		this.noClip = true;
	}
	
	public SeatEntity(EntityType<?> entityTypeIn, World worldIn, BlockPos pos) {
		super(Index.SEAT_ENTITY_TYPE, worldIn);
		this.setPosition(pos.getX(), pos.getY(), pos.getZ());
	}
	
	private SeatEntity(World world, BlockPos pos, double y)
    {
        this(Index.SEAT_ENTITY_TYPE, world);
        this.setPosition(pos.getX() + 0.5, pos.getY() + y, pos.getZ() + 0.5);
    }
	
	@Override
	public void tick() {
		super.tick();
		if(!world.isRemote) {
			if(this.getPassengers().isEmpty() || !(world.getBlockState(this.getPosition()).getBlock() instanceof ISeat)) {
				this.remove();
			}
		}
	}

	@Override
	protected void registerData() {
		
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		
	}
	
	@Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
	
	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return true;
	}
	
	@Override
	public boolean canBePushed() {
		return false;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}
	
	@Override
	public boolean canPassengerSteer() {
		return false;
	}
	
	@Override
	public double getMountedYOffset() {
		return 0d;
	}

	public static boolean createSeat(World world, BlockPos pos, LivingEntity e) {
		if(!world.isRemote) {
			if(world.getEntitiesWithinAABB(SeatEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)).isEmpty()) {
				SeatEntity seat = new SeatEntity(world, pos, .35d);
				world.addEntity(seat);
				e.startRiding(seat);
				return true;
			}
		}
		return false;
	}
}
