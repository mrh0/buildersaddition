package com.mrh0.buildersaddition.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrh0.buildersaddition.blocks.ShopSign;
import com.mrh0.buildersaddition.state.FullDirectionalState;
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
	private ItemStack item = new ItemStack(Items.DIAMOND_PICKAXE, 1);

	public ShopSignRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}
	
	private static final float u1 = 1f/16f;

	@Override
	public void render(ShopSignTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		FullDirectionalState state = tileEntityIn.getBlockState().get(ShopSign.STATE);
		matrixStackIn.push();
		matrixStackIn.translate(.5F, .5F, .5F);
		if(state.getAxis() == Axis.X) {
			matrixStackIn.translate(0, offsetY(state), u1);
		}
		else {
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
			matrixStackIn.translate(0, offsetY(state), u1);
		}
		matrixStackIn.scale(0.8F, 0.8F, .8F);
        Minecraft.getInstance().getItemRenderer().renderItem(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
        matrixStackIn.pop();
	}
	
	private float offsetY(FullDirectionalState state) {
		switch(state.getDirection()) {
			case DOWN:
				return -u1;
			case UP:
				return u1;
		}
		return 0f;
	}
}
