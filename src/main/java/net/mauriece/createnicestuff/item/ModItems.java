package net.mauriece.createnicestuff.item;

import net.mauriece.createnicestuff.CreateNiceStuff;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateNiceStuff.MOD_ID);

    public static final DeferredItem<Item> SLUDGE = ITEMS.register("sludge",
            () -> new Item(new Item.Properties()));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
