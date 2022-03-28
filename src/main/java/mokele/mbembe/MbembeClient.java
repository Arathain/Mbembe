package mokele.mbembe;

import draylar.omegaconfiggui.OmegaConfigGui;
import mokele.mbembe.client.entity.render.DodoRenderer;
import mokele.mbembe.client.entity.render.MokeleMbembeRenderer;
import mokele.mbembe.common.init.MbembeEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;


public class MbembeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OmegaConfigGui.registerConfigScreen(Mbembe.CONFIG);
        EntityRendererRegistry.register(MbembeEntities.MOKELE_MBEMBE, MokeleMbembeRenderer::new);
        EntityRendererRegistry.register(MbembeEntities.DODO, DodoRenderer::new);
    }
}
