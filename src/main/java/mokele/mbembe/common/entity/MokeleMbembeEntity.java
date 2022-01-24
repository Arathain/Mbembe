package mokele.mbembe.common.entity;

import mokele.mbembe.common.init.MbembeSoundEvents;
import mokele.mbembe.common.init.MbembeStatusEffects;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;
import java.util.UUID;

public class MokeleMbembeEntity extends HostileEntity implements Angerable, IAnimatable, IAnimationTickable {
    private static final TrackedData<Boolean> ANGRY = DataTracker.registerData(EndermanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> PROVOKED = DataTracker.registerData(EndermanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int angerTime;
    private final AnimationFactory factory = new AnimationFactory(this);
    @Nullable
    private UUID targetUuid;
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 69);

    public MokeleMbembeEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 2.0F;
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(0, new MoveIntoWaterGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0, 0.0F));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal(this, DrownedEntity.class, true, false));
        this.targetSelector.add(4, new UniversalAngerGoal(this, false));
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem().equals(Items.HEART_OF_THE_SEA) && !this.isAngry()) {
            player.addStatusEffect(new StatusEffectInstance(MbembeStatusEffects.MOKELES_BLESSING, 24000, 0, false, false), this);
            if(!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
            this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    protected void updatePostDeath() {
        ++this.deathTime;
        if (this.deathTime == 52 && !this.world.isClient()) {
            this.world.sendEntityStatus(this, (byte)60);
            this.remove(RemovalReason.KILLED);
        }
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ANGRY, false);
        this.dataTracker.startTracking(PROVOKED, false);
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }

    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        if (target == null) {
            this.dataTracker.set(ANGRY, false);
            this.dataTracker.set(PROVOKED, false);
        } else {
            this.dataTracker.set(ANGRY, true);
        }

    }


    public static DefaultAttributeContainer.Builder createMbembeAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 17.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 96.0);
    }

    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    public boolean isAngry() {
        return this.dataTracker.get(ANGRY);
    }

    public boolean isProvoked() {
        return this.dataTracker.get(PROVOKED);
    }

    public void setProvoked() {
        this.dataTracker.set(PROVOKED, true);
    }
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public boolean tryAttack(Entity target) {
        LightningEntity entity = new LightningEntity(EntityType.LIGHTNING_BOLT, this.world);
        entity.setPosition(target.getPos());
        entity.setCosmetic(false);
        world.spawnEntity(entity);
        return super.tryAttack(target);
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {

    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeAngerToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readAngerFromNbt(this.world, nbt);
    }

    @Override
    public int getAngerTime() {
        return angerTime;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MbembeSoundEvents.ENTITY_MOKELE_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return MbembeSoundEvents.ENTITY_MOKELE_AMBIENT;
    }

    @Override
    public void setAngerTime(int ticks) {
        this.angerTime = ticks;
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return targetUuid;
    }

    @Override
    public void setAngryAt(@Nullable UUID uuid) {
        targetUuid = uuid;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(!isAlive()){
            event.getController().setAnimation(new AnimationBuilder()
                    .addAnimation("animation.mokele.death", true));
            return PlayState.CONTINUE;
        }
        if(event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder()
                    .addAnimation("animation.mokele.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder()
                .addAnimation("animation.mokele.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public int tickTimer() {
        return age;
    }

    public static boolean canSpawn(EntityType<MokeleMbembeEntity> mokeleMbembeEntityEntityType, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
    return true;
    }
}
