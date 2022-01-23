package mokele.mbembe;

import mokele.mbembe.common.init.MbembeEntities;
import mokele.mbembe.common.init.MbembeItems;
import mokele.mbembe.common.init.MbembeSoundEvents;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mbembe implements ModInitializer {
	public static String MOD_ID = "mbembe";

	@Override
	public void onInitialize() {
		MbembeEntities.init();
		MbembeSoundEvents.init();
		MbembeItems.init();
	}
}
