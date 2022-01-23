package mokele.mbembe.client.entity.render;

import mokele.mbembe.client.entity.model.MokeleMbembeModel;
import mokele.mbembe.common.entity.MokeleMbembeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MokeleMbembeRenderer extends GeoEntityRenderer<MokeleMbembeEntity> {
    public MokeleMbembeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MokeleMbembeModel());
    }

    @Override
    protected float getDeathMaxRotation(MokeleMbembeEntity entityLivingBaseIn) {
        return 0;
    }

}
