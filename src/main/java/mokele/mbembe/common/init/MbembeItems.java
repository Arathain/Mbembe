package mokele.mbembe.common.init;

import mokele.mbembe.Mbembe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class MbembeItems {
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
    public static final Item MOKELE_SPAWN_EGG = create("mokele_spawn_egg", new SpawnEggItem(MbembeEntities.MOKELE_MBEMBE, 0x41372c, 0xe3cb8b, (new Item.Settings()).group(ItemGroup.MISC)));


    private static <T extends Item> T create(String name, T item) {
        ITEMS.put(item, new Identifier(Mbembe.MOD_ID, name));
        return item;
    }

    public static void init() {
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
    }
}
