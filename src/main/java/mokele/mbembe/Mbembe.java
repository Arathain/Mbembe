package mokele.mbembe;

import draylar.omegaconfig.OmegaConfig;
import mokele.mbembe.common.config.MbembeConfig;
import mokele.mbembe.common.init.MbembeEntities;
import mokele.mbembe.common.init.MbembeItems;
import mokele.mbembe.common.init.MbembeSoundEvents;
import mokele.mbembe.common.init.MbembeStatusEffects;
import mokele.mbembe.common.world.MbembeEntitySpawns;
import net.minecraft.tag.TagKey;
import net.minecraft.world.biome.Biome;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class Mbembe implements ModInitializer {
	public static String MOD_ID = "mbembe";
	public static final MbembeConfig CONFIG = OmegaConfig.register(MbembeConfig.class);

	@Override
	public void onInitialize(ModContainer container) {
		MbembeEntities.init();
		MbembeSoundEvents.init();
		MbembeItems.init();
		MbembeStatusEffects.init();
		MbembeEntitySpawns.init();
	}
}
