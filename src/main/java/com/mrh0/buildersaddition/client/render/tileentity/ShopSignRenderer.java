package com.mrh0.buildersaddition.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrh0.buildersaddition.blocks.ShopSign;
import com.mrh0.buildersaddition.state.FullDirectionalState;
import com.mrh0.buildersaddition.state.ShopSignState;
import com.mrh0.buildersaddition.tileentity.ShopSignTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.vector.Vector3f;

public class ShopSignRenderer extends TileEntityRenderer<ShopSignTileEntity> {

	private final Minecraft mc = Minecraft.getInstance();

	public ShopSignRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}
	
	private static final float u1 = 1f/16f;

	@Override
	public void render(ShopSignTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		ShopSignState state = tileEntityIn.getBlockState().get(ShopSign.STATE);
		
		ItemStack item = tileEntityIn.getDisplayItem();
		
		if(item == ItemStack.EMPTY)
			return;
		
		//First side
		matrixStackIn.push();
		matrixStackIn.translate(.5f, .5f, .5f);
		if(state.getAxis() == Axis.Z) {
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90f));
		}
		matrixStackIn.translate(offsetSide(state), offsetY(state), u1);
		matrixStackIn.scale(0.8f, 0.8f, .8f);
        Minecraft.getInstance().getItemRenderer().renderItem(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
        matrixStackIn.pop();
        
        //Other side
        matrixStackIn.push();
		matrixStackIn.translate(.5f, .5f, .5f);
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180f));
		if(state.getAxis() == Axis.Z) {
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90f));
		}
		matrixStackIn.translate(-offsetSide(state), offsetY(state), u1);
		matrixStackIn.scale(0.8f, 0.8f, .8f);
        Minecraft.getInstance().getItemRenderer().renderItem(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
        matrixStackIn.pop();
	}
	
	private float offsetY(ShopSignState state) {
		switch(state.getDirection()) {
			case DOWN:
				return -u1;
			case UP:
				return u1;
		}
		return 0f;
	}
	
	private float offsetSide(ShopSignState state) {
		switch(state.getDirection()) {
			case NORTH:
				return u1 - state.getOffset()/16f;
			case SOUTH:
				return -u1 + state.getOffset()/16f;
			case EAST:
				return u1 - state.getOffset()/16f;
			case WEST:
				return -u1 + state.getOffset()/16f;
		}
		return 0f;
	}
}
