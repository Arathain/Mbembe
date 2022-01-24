package mokele.mbembe.common.world;

import mokele.mbembe.Mbembe;
import mokele.mbembe.common.entity.MokeleMbembeEntity;
import mokele.mbembe.common.init.MbembeEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

public class MbembeEntitySpawns {
    public static void init() {
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.RIVER), SpawnGroup.WATER_CREATURE, MbembeEntities.MOKELE_MBEMBE, Mbembe.CONFIG.mbembeWeight, Mbembe.CONFIG.mbembeMin, Mbembe.CONFIG.mbembeMax);
        //initSpawnRestrictions();
    }
    private static void initSpawnRestrictions() {
        SpawnRestrictionAccessor.callRegister(MbembeEntities.MOKELE_MBEMBE, SpawnRestriction.Location.IN_WATER, Heightmap.Type.WORLD_SURFACE, MokeleMbembeEntity::canSpawn);
    }
}