package com.example.main.Item;


import com.example.N3rdyR0b1nsSpellEngine;
import com.example.client.item_render.custom.*;
import com.example.client.item_render.templates.EndSpellBookModel;
import com.example.client.item_render.templates.HighSpellBookModel;
import com.example.client.item_render.templates.SpellBookModel;
import com.example.main.Item.armor.ArmorAttributeHolders;
import com.example.main.Item.armor.MagicArmorMaterials;
import com.example.main.Item.custom.*;
import com.example.main.Item.customarmor.MagicArmorItem;
import com.example.main.Item.spellfocus.SpellFocus;
import com.example.main.Spells.ModSpells;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item TEST = registerItems("test",
            new Tester(new FabricItemSettings().maxCount(1))
    );

    public static final Item TEST2 = registerItems("test2",
            new SpellFocus(new FabricItemSettings().maxCount(1), 1, new SpellBookModel())
    );

    public static final Item CONTAINER = registerItems("container",
            new ContainerItem(new FabricItemSettings().maxCount(1))
    );

    public static final Item EXPERIENCE = registerItems("experience_ingot",
            new Item(new FabricItemSettings())
    );

    public static final Item COPPER_LIMIT_BREAK = registerItems("copper_limit_break",
            new LimitBreakerItem(new FabricItemSettings().maxCount(1), 0, 1));
    public static final Item IRON_LIMIT_BREAK = registerItems("iron_limit_break",
            new LimitBreakerItem(new FabricItemSettings().maxCount(1), 0, 3));
    public static final Item GOLD_LIMIT_BREAK = registerItems("golden_limit_break",
            new LimitBreakerItem(new FabricItemSettings().maxCount(1), 0,  5));
    public static final Item DIAMOND_LIMIT_BREAK = registerItems("diamond_limit_break",
            new LimitBreakerItem(new FabricItemSettings().maxCount(1), 0, 7));
    public static final Item NETHERITE_LIMIT_BREAK = registerItems("netherite_limit_break",
            new LimitBreakerItem(new FabricItemSettings().maxCount(1), 0, 8));


    public static final Item CHROMATIC_ORB_THUNDER = registerItems("chromatic_orb_thunder",
            new Item(new FabricItemSettings().maxCount(1))
            );
    public static final Item CHROMATIC_ORB_POISON = registerItems("chromatic_orb_poison",
            new Item(new FabricItemSettings().maxCount(1))
    );
    public static final Item CHROMATIC_ORB_LIGHTNING = registerItems("chromatic_orb_lightning",
            new Item(new FabricItemSettings().maxCount(1))
    );
    public static final Item CHROMATIC_ORB_FIRE = registerItems("chromatic_orb_fire",
            new Item(new FabricItemSettings().maxCount(1))
    );
    public static final Item CHROMATIC_ORB_COLD = registerItems("chromatic_orb_cold",
            new Item(new FabricItemSettings().maxCount(1))
    );
    public static final Item CHROMATIC_ORB_ACID = registerItems("chromatic_orb_acid",
            new Item(new FabricItemSettings().maxCount(1))
    );
    public static final Item FIREBALL = registerItems("fireball",
            new Item(new FabricItemSettings().maxCount(1))
    );

    public static final Item MANA_POTION = registerItems("mana_potion",
            new ManaPotion(new FabricItemSettings().maxCount(1)));

    public static final Item SIMPLE_SPELL_BOOK = registerItems("simple_spell_book",
            new SpellFocus(new FabricItemSettings().maxCount(1), 1, new SpellBookModel()));
    public static final Item MEDIUM_SPELL_BOOK = registerItems("medium_spell_book",
            new SpellFocus(new FabricItemSettings().maxCount(1), 3, new HighSpellBookModel()));
    public static final Item STRONG_SPELL_BOOK = registerItems("strong_spell_book",
            new SpellFocus(new FabricItemSettings().maxCount(1), 5, new EndSpellBookModel()));

    public static final Item SPELL_PAPER = registerItems("spell_paper",
            new SpellPaperItem(new FabricItemSettings().maxCount(1)));


    public static final Item ZAP_SCROLL = registerItems("zap_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.ZAP));
    public static final Item TELEPORT_SCROLL = registerItems("teleport_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.TELEPORT));
    public static final Item AIR_DRAFT_SCROLL = registerItems("air_draft_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.AIR_DRAFT));
    public static final Item HEAL_SCROLL = registerItems("heal_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.HEAL));
    public static final Item BEAMSCROLL = registerItems("beam_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(
                    ((ContinousUsageSpell)ModSpells.AMETHYST_BARRAGE).MaxUseTime), ModSpells.AMETHYST_BARRAGE));
    public static final Item REGENERATE_SCROLL = registerItems("regenerate_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(
                    1200), ModSpells.HEAL_OVER_TIME));
    public static final Item ARCANE_PIERCER_SCROLL = registerItems("arcane_piercer_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.ARCANE_PIERCER));
    public static final Item ABSORBING_SHIELD_SCROLL = registerItems("absorbing_shield_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.ABSORBING_SHIELD));
    public static final Item HASTE_SCROLL = registerItems("haste_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(1200),
                    ModSpells.HASTE));
    public static final Item CHROMATIC_ORB_SCROLL = registerItems("chromatic_orb_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1),
                    ModSpells.CHROMATIC_ORB));
    public static final Item MAGIC_MISSILE_SCROLL = registerItems("magic_missile_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.MAGIC_MISSILE));
    public static final Item MAGIC_ARMOR_SCROLL = registerItems("magic_armor_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(1200), ModSpells.MAGIC_ARMOR));
    public static final Item MAGIC_SHIELD_SCROLL = registerItems("magic_shield_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(200), ModSpells.MAGIC_SHIELD));
    public static final Item SEE_EVERYTHING_SCROLL = registerItems("occulta_sensus_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(6000), ModSpells.SEE_EVERYTHING));
    public static final Item HEAVENLY_SMITE_SCROLL = registerItems("heavenly_smite_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.HEAVENLY_SMITE));
    public static final Item FIREBALL_SCROLL = registerItems("fireball_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.FIREBALL));
    public static final Item METEOR_SHOWER_SCROLL = registerItems("meteor_shower_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(200), ModSpells.METEOR_SHOWER));
    public static final Item ALCHEMISTS_BREW = registerItems("alchemists_brew_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.ALCHEMISTS_BREW));
    public static final Item WEATHER_FORECAST_SCROLL = registerItems("weather_forecast_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.WEATHER_FORECAST));
    public static final Item HEALING_CIRCLE = registerItems("healing_circle_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(
                    100), ModSpells.HEALING_CIRCLE));
    public static final Item TELEKINESIS_SCROLL = registerItems("telekinesis_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(
                    60), ModSpells.GRAVITY_GRAB));
    public static final Item STASIS_SCROLL = registerItems("stasis_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(
                    100), ModSpells.STASIS));
    public static final Item LEVITATE_SCROLL = registerItems("levitate_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(
                    200), ModSpells.LEVITATE));
    public static final Item FORCE_LEVITATE_SCROLL = registerItems("force_levitate_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.FORCE_LEVITATE));
    public static final Item OVERHEAT_SCROLL = registerItems("overheat_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(
                    1200), ModSpells.OVERHEAT));
    public static final Item ICE_SHARDS_SCROLL = registerItems("ice_shards_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.ICE_SHARDS));
    public static final Item RAY_OF_FROST_SCROLL = registerItems("ray_of_frost_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.RAY_OF_FROST));
    public static final Item QUICK_FIX_SCROLL = registerItems("quick_fix_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.QUICK_FIX));
    public static final Item MENDING_SCROLL = registerItems("mending_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(1200), ModSpells.MENDING));
    public static final Item LIGHTNING_CHARGE_SCROLL = registerItems("lightning_charge_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(1200), ModSpells.CHARGE));
    public static final Item LIFESTEAL_SCROLL = registerItems("lifesteal_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(1800), ModSpells.LIFESTEAL));
    public static final Item ARCANE_SIGIL_SCROLL = registerItems("arcane_sigil_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.ARCANE_SIGIL));
    public static final Item SCULK_BLAST_SCROLL = registerItems("sculk_blast_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(80), ModSpells.SCULK_BLAST));
    public static final Item SOUL_BURST_SCROLL = registerItems("soul_burst_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(80), ModSpells.SOUL_BURST));
    public static final Item FROST_ARMOR_SCROLL = registerItems("frost_armor_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(400), ModSpells.FROZEN_SHIELD));
    public static final Item HUNGER_SCROLL = registerItems("hunger_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(400), ModSpells.HUNGER_SPELL));
    public static final Item TRUE_INVISIBILITY_SCROLL = registerItems("true_invisibility_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(1800), ModSpells.TRUE_INVISIBILITY));
    public static final Item CLEANSE_SCROLL = registerItems("cleanse_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.CLEANSE));
    public static final Item MAXEDCARP_SCROLL = registerItems("fish_time_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.MAXEDCARP));
    public static final Item CALLBACK_SCROLL = registerItems("callback_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(100), ModSpells.CALLBACK));
    public static final Item ELDRITCH_HUNGER_SCROLL = registerItems("eldritch_hunger_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(100), ModSpells.ELDRITCH_HUNGER));
    public static final Item ICE_SKATE_SCROLL = registerItems("ice_skate_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1).maxDamage(100), ModSpells.ICE_SKATER));
    public static final Item WITHER_SKULL_SCROLL = registerItems("wither_skull_scroll",
            new SpellScrollItem(new FabricItemSettings().maxCount(1), ModSpells.WITHER_SKULL));
    /// tier 1

    public static final Item PERFECTED_CONTAINING_WIZARD_BOOTS = registerItems("perfected_containing_wizard_boots",
            new MagicArmorItem(MagicArmorMaterials.MAX_MANA_TIER_6, ArmorAttributeHolders.ClassicBoots(), MagicArmorItem.Type.BOOTS, new FabricItemSettings(), new TheClassicArmorModel()));
    public static final Item PERFECTED_CONTAINING_WIZARD_ROBE = registerItems("perfected_containing_wizard_robe",
            new MagicArmorItem(MagicArmorMaterials.MAX_MANA_TIER_6, ArmorAttributeHolders.ClassicChest(), MagicArmorItem.Type.CHESTPLATE, new FabricItemSettings(), new TheClassicArmorModel()));
    public static final Item PERFECTED_CONTAINING_WIZARD_SKIRT = registerItems("perfected_containing_wizard_skirt",
            new MagicArmorItem(MagicArmorMaterials.MAX_MANA_TIER_6, ArmorAttributeHolders.ClassicLegs(), MagicArmorItem.Type.LEGGINGS, new FabricItemSettings(), new TheClassicLegArmorModel()));
    public static final Item PERFECTED_CONTAINING_WIZARD_HAT = registerItems("perfected_containing_wizard_hat",
            new MagicArmorItem(MagicArmorMaterials.MAX_MANA_TIER_6,  ArmorAttributeHolders.ClassicHelmet(),MagicArmorItem.Type.HELMET, new FabricItemSettings(), new TheClassicArmorModel()));


    public static final Item WARDEN_WIZARD_BOOTS = registerItems("warden_wizard_boots",
            new MagicArmorItem(MagicArmorMaterials.MAX_MANA_TIER_6, ArmorAttributeHolders.WardenBoots(),MagicArmorItem.Type.BOOTS, new FabricItemSettings(), new TheWardenArmorModel()));
    public static final Item WARDEN_WIZARD_RIBCAGE = registerItems("warden_wizard_ribcage",
            new MagicArmorItem(MagicArmorMaterials.MAX_MANA_TIER_6, ArmorAttributeHolders.WardenLegs(),MagicArmorItem.Type.CHESTPLATE, new FabricItemSettings(), new TheWardenArmorModel()));
    public static final Item WARDEN_WIZARD_SOULPACK = registerItems("warden_wizard_soulpack",
            new MagicArmorItem(MagicArmorMaterials.MAX_MANA_TIER_6, ArmorAttributeHolders.WardenChest(),MagicArmorItem.Type.LEGGINGS, new FabricItemSettings(), new TheWardenLegArmorModel()));
    public static final Item WARDEN_WIZARD_HORN = registerItems("warden_wizard_horn",
            new MagicArmorItem(MagicArmorMaterials.MAX_MANA_TIER_6, ArmorAttributeHolders.WardenHelmet(),MagicArmorItem.Type.HELMET, new FabricItemSettings(), new TheWardenArmorModel()));

    public static final Item ENDER_WIZARD_BOOTS = registerItems("ender_wizard_boots",
            new MagicArmorItem(MagicArmorMaterials.MAX_MANA_TIER_6, ArmorAttributeHolders.EnderBoots(),MagicArmorItem.Type.BOOTS, new FabricItemSettings(), new TheEnderArmorModel()));
    public static final Item ENDER_WIZARD_LEGGINGS = registerItems("ender_wizard_tuxedo",
            new MagicArmorItem(MagicArmorMaterials.MAX_MANA_TIER_6, ArmorAttributeHolders.EnderChest(),MagicArmorItem.Type.CHESTPLATE, new FabricItemSettings(), new TheEnderArmorModel()));
    public static final Item ENDER_WIZARD_TUXEDO = registerItems("ender_wizard_leggings",
            new MagicArmorItem(MagicArmorMaterials.MAX_MANA_TIER_6, ArmorAttributeHolders.EnderLegs(),MagicArmorItem.Type.LEGGINGS, new FabricItemSettings(), new TheEnderLegArmorModel()));
    public static final Item ENDER_WIZARD_HAT = registerItems("ender_wizard_hat",
            new MagicArmorItem(MagicArmorMaterials.MAX_MANA_TIER_6, ArmorAttributeHolders.EnderHelmet(),MagicArmorItem.Type.HELMET, new FabricItemSettings(), new TheEnderArmorModel()));


    private static Item registerItems(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID,name), item);
    }



    public static void registerModItems() {
        N3rdyR0b1nsSpellEngine.LOGGER.info("Registering Magic Items" + N3rdyR0b1nsSpellEngine.MOD_ID);
    }

}
