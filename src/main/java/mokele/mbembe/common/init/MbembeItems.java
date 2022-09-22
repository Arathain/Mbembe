package mokele.mbembe.common.init;

import mokele.mbembe.Mbembe;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.LinkedHashMap;
import java.util.Map;

public class MbembeItems {
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
    public static final Item MOKELE_SPAWN_EGG = create("mokele_spawn_egg", new SpawnEggItem(MbembeEntities.MOKELE_MBEMBE, 0x41372c, 0xe3cb8b, (new Item.Settings()).group(ItemGroup.MISC)));
    public static final Item DODO_SPAWN_EGG = create("dodo_spawn_egg", new SpawnEggItem(MbembeEntities.DODO, 0xa8a196, 0x3f3930, (new Item.Settings()).group(ItemGroup.MISC)));
    public static final Item DODO = create("dodo", new Item(new QuiltItemSettings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.4F).build())));
    public static final Item COOKED_DODO = create("cooked_dodo", new Item(new QuiltItemSettings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(8).saturationModifier(0.6F).build())));


    private static <T extends Item> T create(String name, T item) {
        ITEMS.put(item, new Identifier(Mbembe.MOD_ID, name));
        return item;
    }

    public static void init() {
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
    }
}
