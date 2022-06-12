package mokele.mbembe;

import draylar.omegaconfiggui.OmegaConfigGui;
import mokele.mbembe.client.entity.render.DodoRenderer;
import mokele.mbembe.client.entity.render.MokeleMbembeRenderer;
import mokele.mbembe.common.init.MbembeEntities;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;


public class MbembeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        OmegaConfigGui.registerConfigScreen(Mbembe.CONFIG);
        EntityRendererRegistry.register(MbembeEntities.MOKELE_MBEMBE, MokeleMbembeRenderer::new);
        EntityRendererRegistry.register(MbembeEntities.DODO, DodoRenderer::new);
    }
}
