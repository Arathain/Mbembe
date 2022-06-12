package mokele.mbembe.common.init;

import mokele.mbembe.Mbembe;
import mokele.mbembe.common.effect.MbembeEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class MbembeStatusEffects {
    private static final Map<StatusEffect, Identifier> EFFECTS = new LinkedHashMap<>();

    public static final StatusEffect MOKELES_BLESSING = create("mokeles_blessing", new MbembeEffect(StatusEffectType.BENEFICIAL, 0x16a5de));

    private static <T extends StatusEffect> T create(String name, T effect) {
        EFFECTS.put(effect, new Identifier(Mbembe.MOD_ID, name));
        return effect;
    }

    public static void init() {
        EFFECTS.keySet().forEach(effect -> Registry.register(Registry.STATUS_EFFECT, EFFECTS.get(effect), effect));
    }
}
