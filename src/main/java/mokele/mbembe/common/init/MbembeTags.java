package mokele.mbembe.common.init;

import mokele.mbembe.Mbembe;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class MbembeTags {
    public static final TagKey<Biome> HAS_MBEMBE = create("has_mbembe");
    private static TagKey<Biome> create(String id) {
        return TagKey.of(Registry.BIOME_KEY, new Identifier(Mbembe.MOD_ID, id));
    }
}
