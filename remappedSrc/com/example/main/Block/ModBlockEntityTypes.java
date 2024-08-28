package com.example.main.Block;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.bin.SpellApplicationTableBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntityTypes {
    public static BlockEntityType<SpellApplicationTableBlockEntity> SPELL_APPLICATION_TABLE =
            FabricBlockEntityTypeBuilder.create(SpellApplicationTableBlockEntity::new, ModBlocks.ELEGANT_BOOKSHELF).build();


    public static void init() {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID,
                "spellapplicator"), SPELL_APPLICATION_TABLE);


        N3rdyR0b1nsSpellEngine.LOGGER.info("Registering Magic block entity types for " + N3rdyR0b1nsSpellEngine.MOD_ID);
    }
}
