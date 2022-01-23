package mokele.mbembe;

import mokele.mbembe.client.entity.render.MokeleMbembeRenderer;
import mokele.mbembe.common.init.MbembeEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class MbembeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(MbembeEntities.MOKELE_MBEMBE, MokeleMbembeRenderer::new);
    }
}
