package com.mrh0.buildersaddition.entity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.base.ISeat;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.FluidType;
import org.joml.Vector3f;

public class SeatEntity extends Entity {

	public SeatEntity(EntityType<?> entityTypeIn, Level worldIn) {
		super(Index.SEAT_ENTITY_TYPE.get(), worldIn);
		this.noPhysics = true;
	}
	
	public SeatEntity(EntityType<?> entityTypeIn, Level worldIn, BlockPos pos) {
		super(Index.SEAT_ENTITY_TYPE.get(), worldIn);
		this.setPos(pos.getX(), pos.getY(), pos.getZ());
	}
	
	private SeatEntity(Level world, BlockPos pos, double y)
    {
        this(Index.SEAT_ENTITY_TYPE.get(), world);
        this.setPos(pos.getX() + 0.5, pos.getY() + y, pos.getZ() + 0.5);
    }

	private BlockPos getCheckPos() {
		int x = (int)this.getX();
		int y = (int)this.getY();
		int z = (int)this.getZ();
		return new BlockPos(x < 0 ? x-1 : x, y < 0 ? y-1 : y, z < 0 ? z-1 : z);
	}
	
	@Override
	public void tick() {
		super.tick();
		if(!level().isClientSide()) {

			if(this.getPassengers().isEmpty() || !(level().getBlockState(getCheckPos()).getBlock() instanceof ISeat)) {
				System.out.println(this.getPassengers().isEmpty() + ":" + !(level().getBlockState(getCheckPos()).getBlock() instanceof ISeat) + ":" + getCheckPos());
				this.setRemoved(RemovalReason.KILLED);
			}
		}
	}

	/*@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}*/

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return super.getAddEntityPacket();
	}

	@Override
	protected boolean canAddPassenger(Entity p_20354_) {
		return true;
	}
	
	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean isPushedByFluid(FluidType type) {
		return false;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}
	
	@Override
	public boolean canRiderInteract() {
		return false;
	}

	/*@Override
	public double getPassengersRidingOffset() {
		return -.1d;
	}*/

	@Override
	protected Vector3f getPassengerAttachmentPoint(Entity p_297569_, EntityDimensions p_297882_, float p_300288_) {
		return new Vector3f(0f, -.1f, 0f);
	}

	public static InteractionResult createSeat(Level world, BlockPos pos, LivingEntity e, double y, SoundEvent sound) {
		if(e instanceof Player)
		world.playSound((Player)e, pos, sound, SoundSource.BLOCKS, 1f, 1f);
		if(world.isClientSide())
			return InteractionResult.SUCCESS;
		if(world.getEntitiesOfClass(SeatEntity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)).isEmpty()) {
			SeatEntity seat = new SeatEntity(world, pos, y);//.35d
			world.addFreshEntity(seat);
			e.startRiding(seat);
			return InteractionResult.CONSUME;
		}
		return InteractionResult.FAIL;
	}
	
	public static InteractionResult createSeat(Level world, BlockPos pos, LivingEntity e, SoundEvent sound) {
		return createSeat(world, pos, e, .45d, sound);
	}

	@Override
	protected void defineSynchedData() {
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_20052_) {
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_20139_) {
	}
}
