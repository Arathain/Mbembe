package mokele.mbembe.common.entity;

import mokele.mbembe.common.entity.goal.TwerkGoal;
import mokele.mbembe.common.entity.goal.TwerkerNavigation;
import mokele.mbembe.common.init.MbembeEntities;
import mokele.mbembe.common.init.MbembeSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
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

public class DodoEntity extends AnimalEntity implements IAnimatable, IAnimationTickable, Twerker {
    private static final TrackedData<Boolean> IS_GLISTERING = DataTracker.registerData(DodoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final Ingredient LURING_INGREDIENT = Ingredient.ofItems(Items.GLISTERING_MELON_SLICE, Items.MELON_SLICE, Items.MELON, Items.MELON_SEEDS);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.MELON_SLICE);
    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean songPlaying;

    public DodoEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setGlistering(false);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(0, new TwerkGoal(this));
        this.goalSelector.add(2, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, LURING_INGREDIENT, false));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createDodoAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
    }
    @Override
    public void setNearbySongPlaying(BlockPos songPosition, boolean playing) {
        this.songPlaying = playing;
    }

    public boolean isTwerking() {
        return this.songPlaying;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (itemStack.isOf(Items.GLISTERING_MELON_SLICE) && !this.isGlistering()) {
            if(!world.isClient) {
                itemStack.decrement(1);
                this.world.playSoundFromEntity(null, this, MbembeSoundEvents.ENTITY_DODO_TRANSFORMS, this.getSoundCategory(), 1.0F, 1.0F);
                Vec3d vec3d = this.getBoundingBox().getCenter();
                RandomGenerator random = this.world.getRandom();
                for (int i = 0; i < 10; ++i) {
                    double velX = random.nextGaussian() * 0.075D;
                    double velY = random.nextGaussian() * 0.075D;
                    double velZ = random.nextGaussian() * 0.075D;
                    this.world.addParticle(ParticleTypes.POOF, true, vec3d.x, vec3d.y, vec3d.z, velX, velY, velZ);
                }
                this.setGlistering(true);
            }
            return ActionResult.success(this.world.isClient);
        }

        if (itemStack.isOf(Items.WATER_BUCKET) && this.isGlistering()) {
            if(!world.isClient) {
                this.world.playSoundFromEntity(null, this, SoundEvents.ITEM_BUCKET_EMPTY, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                this.setGlistering(false);
            }
            return ActionResult.success(this.world.isClient);
        }

        return super.interactMob(player, hand);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isGlistering()) {
            this.world.addParticle(ParticleTypes.WAX_ON, this.getParticleX(0.1D), this.getBodyY(0.4D), this.getParticleZ(0.15D), 1.0D, 1.0D, 1.0D);
        }
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0D) {
            this.setVelocity(vec3d.multiply(1.0D, 0.6D, 1.0D));
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    public boolean isGlistering() {
        return this.dataTracker.get(IS_GLISTERING);
    }

    public void setGlistering(boolean glistering) {
        this.dataTracker.set(IS_GLISTERING, glistering);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(IS_GLISTERING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("IsGlistering", this.isGlistering());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setGlistering(nbt.getBoolean("IsGlistering"));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return MbembeSoundEvents.ENTITY_DODO_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MbembeSoundEvents.ENTITY_DODO_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return MbembeSoundEvents.ENTITY_DODO_DEATH;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return MbembeEntities.DODO.create(world);
    }
    @Override
    protected EntityNavigation createNavigation(World world) {
        return new TwerkerNavigation<>(this, world);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 20, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(this.isTwerking()){
            event.getController().setAnimation(new AnimationBuilder()
                    .addAnimation("animation.dodo.eltwerka", true));
            return PlayState.CONTINUE;
        }
        if((this.isInsideWaterOrBubbleColumn() || this.touchingWater) || !this.isOnGround()) {
            event.getController().setAnimation(new AnimationBuilder()
                .addAnimation("animation.dodo.fall", true));
            return PlayState.CONTINUE;
        }
        if(event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder()
                .addAnimation("animation.dodo.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder()
            .addAnimation("animation.dodo.idle", true));
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
    public static boolean canSpawn(EntityType<? extends DodoEntity> type, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, RandomGenerator randomGenerator) {
        return randomGenerator.nextDouble() > 0.78 && isBrightEnoughForNaturalSpawn(serverWorldAccess, blockPos);
    }
}
