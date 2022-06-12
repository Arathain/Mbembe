package mokele.mbembe.common.world;

import mokele.mbembe.Mbembe;
import mokele.mbembe.common.entity.DodoEntity;
import mokele.mbembe.common.entity.MokeleMbembeEntity;
import mokele.mbembe.common.init.MbembeEntities;

import net.minecraft.world.biome.SpawnSettings;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.ModificationPhase;

public class MbembeEntitySpawns {
    public static void init() {
        BiomeModifications.create(new Identifier(Mbembe.MOD_ID, "mbembe"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.isIn(BiomeTags.IS_BEACH).or(BiomeSelectors.includeByKey(BiomeKeys.STONY_SHORE)), context -> context.getSpawnSettings().addSpawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(MbembeEntities.DODO, Mbembe.CONFIG.dodoWeight, Mbembe.CONFIG.dodoMin, Mbembe.CONFIG.dodoMax)))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.isIn(BiomeTags.IS_RIVER).and(BiomeSelectors.excludeByKey(BiomeKeys.FROZEN_RIVER)), context -> context.getSpawnSettings().addSpawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(MbembeEntities.MOKELE_MBEMBE, Mbembe.CONFIG.mbembeWeight, Mbembe.CONFIG.mbembeMin, Mbembe.CONFIG.mbembeMax)));
        initSpawnRestrictions();
    }
    private static void initSpawnRestrictions() {
        SpawnRestrictionAccessor.callRegister(MbembeEntities.MOKELE_MBEMBE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MokeleMbembeEntity::canSpawn);
        SpawnRestrictionAccessor.callRegister(MbembeEntities.DODO, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DodoEntity::canSpawn);
    }
}
