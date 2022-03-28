package mokele.mbembe.client.entity.render;

import mokele.mbembe.client.entity.model.DodoModel;
import mokele.mbembe.common.entity.DodoEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DodoRenderer extends GeoEntityRenderer<DodoEntity> {
    public DodoRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DodoModel());
    }

    @Override
    public RenderLayer getRenderType(DodoEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
    }

    @Override
    protected float getDeathMaxRotation(DodoEntity entityLivingBaseIn) {
        return 0;
    }

}
