package com.example.main.Block;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Block.custom.SpellBinder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block ELEGANT_BOOKSHELF = registerBlock("elegant_bookshelf",
            new SpellBinder(FabricBlockSettings.of().luminance(3).strength(4f).sounds(BlockSoundGroup.WOOD)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID,name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        BlockItem blockItem = new BlockItem(block, new FabricItemSettings());
        return Registry.register(Registries.ITEM, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID,name), blockItem);

    }



    public static void init() {

        N3rdyR0b1nsSpellEngine.LOGGER.info("Registering Magic blocks for " + N3rdyR0b1nsSpellEngine.MOD_ID);
    }
}
