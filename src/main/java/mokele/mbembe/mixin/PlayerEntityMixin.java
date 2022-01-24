package mokele.mbembe.mixin;

import mokele.mbembe.common.entity.MokeleMbembeEntity;
import mokele.mbembe.common.init.MbembeStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At("TAIL"))
    private void atttamck(Entity target, CallbackInfo ci) {
        if(target.isAttackable() && !target.getWorld().isClient) {
            if (!target.handleAttack(this)) {
                if(target.isInsideWaterOrBubbleColumn() && target.getWorld().getBiome(target.getBlockPos()).getCategory().equals(Biome.Category.RIVER) && this.hasStatusEffect(MbembeStatusEffects.MOKELES_BLESSING) && !(target instanceof MokeleMbembeEntity)) {
                    target.setVelocity(0, Math.abs(target.getVelocity().y) + 1f, 0);
                    target.damage(DamageSource.mob(this), 20);
                    if(target.getWorld() instanceof ServerWorld serverWorld) {
                        for (int i = 0; i < 10; i++) {
                            serverWorld.spawnParticles(ParticleTypes.FISHING, target.getX(), target.getY(), target.getZ(), 0, this.getRandom().nextFloat() / 10, 0.01, this.getRandom().nextFloat() / 10, 1.0);
                        }
                    }
                }
            }
        }
    }
}
