package com.mrh0.buildersaddition.client.render;

import javax.annotation.Nullable;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrh0.buildersaddition.entity.SeatEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class SeatRender extends EntityRenderer<SeatEntity>
{
    public SeatRender(EntityRendererManager manager)
    {
        super(manager);
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(SeatEntity seatEntity)
    {
        return null;
    }
    
    @Override
    public void render(SeatEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
    		IRenderTypeBuffer bufferIn, int packedLightIn) {
    	super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}