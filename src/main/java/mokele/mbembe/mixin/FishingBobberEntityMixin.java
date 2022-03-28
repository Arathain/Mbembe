package mokele.mbembe.mixin;


import mokele.mbembe.common.init.MbembeStatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
    @Mutable
    @Shadow @Final private int luckOfTheSeaLevel;

    @Shadow @Nullable public abstract PlayerEntity getPlayerOwner();


    @Shadow private int waitCountdown;

    @Inject(method = "use", at = @At("HEAD"))
    private void addMokeleLuckBlessing(ItemStack usedItem, CallbackInfoReturnable<Integer> cir) {
        if(getPlayerOwner() != null && getPlayerOwner().hasStatusEffect(MbembeStatusEffects.MOKELES_BLESSING)) {
            luckOfTheSeaLevel = (luckOfTheSeaLevel + 1) * 16;
        }
    }

    @Inject(method= "tickFishingLogic", at = @At("TAIL"))
    private void addMokeleSpeedBlessing(BlockPos pos, CallbackInfo ci) {
        if(getPlayerOwner() != null && getPlayerOwner().hasStatusEffect(MbembeStatusEffects.MOKELES_BLESSING)) {
            this.waitCountdown *= 0.5;
        }
    }
}
