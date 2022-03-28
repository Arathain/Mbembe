package mokele.mbembe.common.init;

import mokele.mbembe.Mbembe;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class MbembeSoundEvents {
    private static final Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap<>();

    public static final SoundEvent ENTITY_MOKELE_AMBIENT = create("entity.mokele.ambient");
    public static final SoundEvent ENTITY_MOKELE_HURT = create("entity.mokele.hurt");
    public static final SoundEvent ENTITY_MOKELE_NOW = create("entity.mokele.now");

    public static final SoundEvent ENTITY_DODO_AMBIENT = create("entity.dodo.ambient");
    public static final SoundEvent ENTITY_DODO_HURT = create("entity.dodo.hurt");
    public static final SoundEvent ENTITY_DODO_DEATH = create("entity.dodo.death");
    public static final SoundEvent ENTITY_DODO_TRANSFORMS = create("entity.dodo.transform");

    private static SoundEvent create(String name) {
        Identifier id = new Identifier(Mbembe.MOD_ID, name);
        SoundEvent soundEvent = new SoundEvent(id);
        SOUND_EVENTS.put(soundEvent, id);
        return soundEvent;
    }

    public static void init() {
        SOUND_EVENTS.keySet().forEach(effect -> Registry.register(Registry.SOUND_EVENT, SOUND_EVENTS.get(effect), effect));
    }
}
