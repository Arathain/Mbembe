package mokele.mbembe.common.init;

import mokele.mbembe.Mbembe;
import mokele.mbembe.common.entity.MokeleMbembeEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class MbembeEntities {
    private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();

    public static final EntityType<MokeleMbembeEntity> MOKELE_MBEMBE = createEntity("mokele", MokeleMbembeEntity.createMbembeAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, MokeleMbembeEntity::new).dimensions(EntityDimensions.fixed(2.0F, 4.0F)).spawnGroup(SpawnGroup.WATER_CREATURE).build());

    private static <T extends LivingEntity> EntityType<T> createEntity(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
        FabricDefaultAttributeRegistry.register(type, attributes);
        ENTITY_TYPES.put(type, new Identifier(Mbembe.MOD_ID, name));
        return type;
    }


    public static void init() {
        ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
    }

}
