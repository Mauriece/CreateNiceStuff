package net.mauriece.createnicestuff.block;

import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import net.mauriece.createnicestuff.CreateNiceStuff;
import net.mauriece.createnicestuff.content.kinetics.shaftcoupler.ShaftCouplerBlock;

import net.mauriece.createnicestuff.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CreateNiceStuff.MOD_ID);

    //Blocks
    public static final DeferredBlock<ShaftCouplerBlock> SHAFT_COUPLER = registerBlock("shaft_coupler",
            () -> new ShaftCouplerBlock(Block.Properties.of()
                    .strength(1.4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.WOOD)
            ));
    public static final DeferredBlock<Block> TEST_SHAFT = registerBlock("test_shaft",
            () -> new ShaftBlock(Block.Properties.of()
                    .strength(1.4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.WOOD)
            ));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);}
}
