package com.example.main.Item;


import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static ItemGroup MAGIC_ITEMS;
    public static ItemGroup SPELL_SCROLLS;
    public static ItemGroup DRIP;

    public static void registerItemGroup () {
        DRIP = Registry.register(Registries.ITEM_GROUP,
                new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "magic_mayhem_magic_items"), FabricItemGroup
                        .builder()
                        .displayName(Text.translatable("itemgroup.magic_mayhem_spell_drip"))
                        .icon(() -> new ItemStack(ModItems.PERFECTED_CONTAINING_WIZARD_HAT)).entries(((displayContext, entries) -> {
                            entries.add(ModItems.PERFECTED_CONTAINING_WIZARD_HAT);
                            entries.add(ModItems.PERFECTED_CONTAINING_WIZARD_ROBE);
                            entries.add(ModItems.PERFECTED_CONTAINING_WIZARD_SKIRT);
                            entries.add(ModItems.PERFECTED_CONTAINING_WIZARD_BOOTS);

                            entries.add(ModItems.WARDEN_WIZARD_HORN);
                            entries.add(ModItems.WARDEN_WIZARD_RIBCAGE);
                            entries.add(ModItems.WARDEN_WIZARD_SOULPACK);
                            entries.add(ModItems.WARDEN_WIZARD_BOOTS);

                            entries.add(ModItems.ENDER_WIZARD_HAT);
                            entries.add(ModItems.ENDER_WIZARD_TUXEDO);
                            entries.add(ModItems.ENDER_WIZARD_LEGGINGS);
                            entries.add(ModItems.ENDER_WIZARD_BOOTS);

                            entries.add(ModBlocks.ELEGANT_BOOKSHELF);
                        })).build());

        SPELL_SCROLLS = Registry.register(Registries.ITEM_GROUP, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "magic_mayhem_spell_scrolls"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemgroup.magic_mayhem_spell_scrolls" ))
                        .icon(() -> new ItemStack(ModItems.ZAP_SCROLL)).entries(((displayContext, entries) -> {

                            entries.add(ModItems.ZAP_SCROLL);
                            entries.add(ModItems.LIGHTNING_CHARGE_SCROLL);

                            entries.add(ModItems.HEAL_SCROLL);
                            entries.add(ModItems.HEALING_CIRCLE);
                            entries.add(ModItems.REGENERATE_SCROLL);
                            entries.add(ModItems.ABSORBING_SHIELD_SCROLL);
                            entries.add(ModItems.HEAVENLY_SMITE_SCROLL);
                            entries.add(ModItems.CLEANSE_SCROLL);

                            entries.add(ModItems.SEE_EVERYTHING_SCROLL);
                            entries.add(ModItems.HUNGER_SCROLL);
                            entries.add(ModItems.SCULK_BLAST_SCROLL);
                            entries.add(ModItems.SOUL_BURST_SCROLL);

                            entries.add(ModItems.LIFESTEAL_SCROLL);

                            entries.add(ModItems.OVERHEAT_SCROLL);
                            entries.add(ModItems.FIREBALL_SCROLL);
                            entries.add(ModItems.METEOR_SHOWER_SCROLL);

                            entries.add(ModItems.FROST_ARMOR_SCROLL);
                            entries.add(ModItems.RAY_OF_FROST_SCROLL);
                            entries.add(ModItems.ICE_SHARDS_SCROLL);

                            entries.add(ModItems.FORCE_LEVITATE_SCROLL);
                            entries.add(ModItems.LEVITATE_SCROLL);
                            entries.add(ModItems.AIR_DRAFT_SCROLL);
                            entries.add(ModItems.WEATHER_FORECAST_SCROLL);

                            entries.add(ModItems.CHROMATIC_ORB_SCROLL);
                            entries.add(ModItems.MAGIC_MISSILE_SCROLL);
                            entries.add(ModItems.ARCANE_PIERCER_SCROLL);
                            entries.add(ModItems.ARCANE_SIGIL_SCROLL);
                            entries.add(ModItems.TELEPORT_SCROLL);
                            entries.add(ModItems.HASTE_SCROLL);
                            entries.add(ModItems.STASIS_SCROLL);
                            entries.add(ModItems.QUICK_FIX_SCROLL);
                            entries.add(ModItems.MENDING_SCROLL);
                            entries.add(ModItems.ALCHEMISTS_BREW);
                            entries.add(ModItems.MAGIC_ARMOR_SCROLL);
                            entries.add(ModItems.MAGIC_SHIELD_SCROLL);
                            entries.add(ModItems.TELEKINESIS_SCROLL);
                            entries.add(ModItems.BEAMSCROLL);
                            entries.add(ModItems.MAXEDCARP_SCROLL);
                            entries.add(ModItems.CALLBACK_SCROLL);

                            entries.add(ModItems.TRUE_INVISIBILITY_SCROLL);
                        }))
                        .build());

        MAGIC_ITEMS = Registry.register(Registries.ITEM_GROUP, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "magic_mayhem_spell_drip"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemgroup.magic_mayhem_magic_items"))
                        .icon(() -> new ItemStack(ModItems.SIMPLE_SPELL_BOOK)).entries(((displayContext, entries) -> {
                            entries.add(ModItems.SIMPLE_SPELL_BOOK);
                            entries.add(ModItems.MEDIUM_SPELL_BOOK);
                            entries.add(ModItems.STRONG_SPELL_BOOK);

                            entries.add(ModItems.MANA_POTION);

                            entries.add(ModItems.COPPER_LIMIT_BREAK);
                            entries.add(ModItems.IRON_LIMIT_BREAK);
                            entries.add(ModItems.GOLD_LIMIT_BREAK);
                            entries.add(ModItems.DIAMOND_LIMIT_BREAK);
                            entries.add(ModItems.NETHERITE_LIMIT_BREAK);

                            entries.add(ModItems.CONTAINER);
                            entries.add(ModItems.EXPERIENCE);

                        }
                        ))
                        .build());
    }
}
