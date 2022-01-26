package mokele.mbembe.client.entity.model;

import mokele.mbembe.Mbembe;
import mokele.mbembe.common.entity.MokeleMbembeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MokeleMbembeModel extends AnimatedTickingGeoModel<MokeleMbembeEntity> {
    @Override
    public Identifier getModelLocation(MokeleMbembeEntity object) {
        return new Identifier(Mbembe.MOD_ID, "geo/entity/mokele.geo.json");
    }

    @Override
    public Identifier getTextureLocation(MokeleMbembeEntity object) {
        return  new Identifier(Mbembe.MOD_ID, "textures/entity/mokele.png");
    }

    @Override
    public Identifier getAnimationFileLocation(MokeleMbembeEntity animatable) {
        return new Identifier(Mbembe.MOD_ID, "animations/entity/mokele.animation.json");
    }


    @Override
    public void setLivingAnimations(MokeleMbembeEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        IBone body = this.getAnimationProcessor().getBone("body");
        IBone head = this.getAnimationProcessor().getBone("head2");
        IBone neck = this.getAnimationProcessor().getBone("neck");
        neck.setRotationX((float) MathHelper.lerp(body.getRotationX(), (-entity.getPitch() * 0.006), 0.6f));
        neck.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 360F));

        head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) - neck.getRotationY());
        if (entity.isBaby()) {
            body.setScaleX(0.4f);
            body.setScaleY(0.4f);
            body.setScaleZ(0.4f);
            body.setPositionY(-15F);
        }
    }
}
