package mokele.mbembe.common.world;

import mokele.mbembe.Mbembe;
import mokele.mbembe.common.entity.DodoEntity;
import mokele.mbembe.common.entity.MokeleMbembeEntity;
import mokele.mbembe.common.init.MbembeEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.tag.BiomeTags;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class MbembeEntitySpawns {
    public static void init() {
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_BEACH).or(BiomeSelectors.includeByKey(BiomeKeys.STONY_SHORE)), SpawnGroup.CREATURE, MbembeEntities.DODO, Mbembe.CONFIG.dodoWeight, Mbembe.CONFIG.dodoMin, Mbembe.CONFIG.dodoMax);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.RIVER), SpawnGroup.WATER_AMBIENT, MbembeEntities.MOKELE_MBEMBE, Mbembe.CONFIG.mbembeWeight, Mbembe.CONFIG.mbembeMin, Mbembe.CONFIG.mbembeMax);
        initSpawnRestrictions();
    }
    private static void initSpawnRestrictions() {
        SpawnRestrictionAccessor.callRegister(MbembeEntities.MOKELE_MBEMBE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MokeleMbembeEntity::canSpawn);
        SpawnRestrictionAccessor.callRegister(MbembeEntities.DODO, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DodoEntity::canSpawn);
    }
}
