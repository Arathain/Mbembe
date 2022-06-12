package mokele.mbembe.client.entity.model;

import mokele.mbembe.Mbembe;
import mokele.mbembe.common.entity.DodoEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class DodoModel extends AnimatedTickingGeoModel<DodoEntity> {
    @Override
    public Identifier getModelResource(DodoEntity object) {
        return new Identifier(Mbembe.MOD_ID, "geo/entity/dodo.geo.json");
    }

    @Override
    public Identifier getTextureResource(DodoEntity object) {
        return object.isGlistering() ? new Identifier(Mbembe.MOD_ID, "textures/entity/dodo/glistering_dodo.png") : new Identifier(Mbembe.MOD_ID, "textures/entity/dodo/dodo.png");
    }

    @Override
    public Identifier getAnimationResource(DodoEntity animatable) {
        return new Identifier(Mbembe.MOD_ID, "animations/entity/dodo.animation.json");
    }


    @Override
    public void setLivingAnimations(DodoEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        IBone root = this.getAnimationProcessor().getBone("root");
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone neck = this.getAnimationProcessor().getBone("neck");
        neck.setRotationX((float) MathHelper.lerp(root.getRotationX(), (-entity.getPitch() * 0.006), 0.6f));
        neck.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 360F));

        head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) - neck.getRotationY());
        if (entity.isBaby()) {
            root.setScaleX(0.4f);
            head.setScaleX(1.5f);
            root.setScaleY(0.4f);
            head.setScaleY(1.5f);
            root.setScaleZ(0.4f);
            head.setScaleZ(1.5f);
        }
    }
}
