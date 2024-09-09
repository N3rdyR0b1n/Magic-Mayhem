package com.example.main.Spells;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.SpellUtil.Spells.NullSpell;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.SpellUtil.Spells.SpellKeeper;
import com.example.main.SpellUtil.Spells.SpellSchools;
import com.example.main.Spells.custom.*;
import com.example.main.Spells.custom.SaveLoadSpell;
import net.minecraft.util.Identifier;

public class ModSpells {


    public static int MAX_LEVEL = 127;
    public static Identifier identifierCreator(String name) {
        return new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "textures/gui/spells/" + name + ".png");
    }

    public static Spell ZAP = SpellKeeper.RegisterSpell(
            new ElectricalZapSpell(15, SpellSchools.LIGHTNING ,55, 100,
                    identifierCreator("zap"), 1, MAX_LEVEL, 10, 40, 10, 5)
    );
    public static Spell HEAL = SpellKeeper.RegisterSpell(
            new HealingSpell(10, SpellSchools.HOLY,0, "Heal", 200,
                    identifierCreator("heal"), 1, MAX_LEVEL, 20)
    );
    public static Spell TELEPORT = SpellKeeper.RegisterSpell(
            new TelleportationSpell(15, SpellSchools.ARCANA,0, "Teleport", 200,
                    identifierCreator("teleport"), 3, MAX_LEVEL, 65)
    );
    public static Spell AIR_DRAFT = SpellKeeper.RegisterSpell(
            new AirDraftSpell(35,  SpellSchools.AIR, 0, "Air Draft", 200,
                    identifierCreator("dash"), 1, MAX_LEVEL, 45)
    );
    public static Spell AMETHYST_BARRAGE = SpellKeeper.RegisterSpell(
            new AmethystBarrage(10, SpellSchools.ARCANA,0,"Amethyst Barrage", 100,
                    identifierCreator("amethyst_barrage"), 200, true,
                    1, 2, 2, 0, 0)
    );
    public static Spell HEALING_CIRCLE = SpellKeeper.RegisterSpell(
            new HealingCircle(150, SpellSchools.HOLY, 0, "Healing Circle",
                    100, identifierCreator("healing_circle"), 400, true, 0, 2, 9, 35, 0)
    );
    public static Spell CHROMATIC_ORB = SpellKeeper.RegisterSpell(
            new ChromaticOrbSpell(30, SpellSchools.ARCANA, 10, "Chromatic Orb",
                    identifierCreator("chromatic_orb"), 2, 2)
    );
    public static Spell MAGIC_MISSILE = SpellKeeper.RegisterSpell(
            new MagicMissileSpell(30, SpellSchools.ARCANA, 20, "Magic Missile",
                    200 ,identifierCreator("magic_missile"), 1, MAX_LEVEL, 20, 400, 40, 25)
    );
    public static Spell HEAL_OVER_TIME = SpellKeeper.RegisterSpell(
            new ToggleHealling(85, SpellSchools.HOLY, 0, "Regenerate Health", 200,
                    identifierCreator("regenerate"), 1200, false, 0, 2, 2, 0 ,0
    ));
    public static Spell ARCANE_PIERCER = SpellKeeper.RegisterSpell(
            new ArcanePiercerSpell(45, SpellSchools.ARCANA, 80, "Arcane Piercer", 200,
                    identifierCreator("arcane_piercer"), 3, MAX_LEVEL, 25
    ));
    public static Spell ABSORBING_SHIELD = SpellKeeper.RegisterSpell(
            new AbsorbtionSpell(10, SpellSchools.HOLY, 0, "Absorption Shield",
                    identifierCreator("absorb"),
                    400, 6, 1, MAX_LEVEL, 15)
    );
    public static Spell HASTE = SpellKeeper.RegisterSpell(new HasteSpell(
        20, SpellSchools.ARCANA, 0, "Haste", 200, identifierCreator("haste"),
        1800, false, 4, 3, 3, 0, 0
    ));
    public static Spell MAGIC_ARMOR = SpellKeeper.RegisterSpell(new MagicArmorSpell(
            80, SpellSchools.ARCANA, 0, "Magic Armor", 3600,
            identifierCreator("magic_armor"),
            24000, false, 0, 2, 3, 3, 0, 0
    ));
    public static Spell MAGIC_SHIELD = SpellKeeper.RegisterSpell(new MagicArmorSpell(
            150, SpellSchools.ARCANA, 0, "Magic Shield", 3600, identifierCreator("magic_shield"),
            1800, false, 0, 6, 3, 3, 0 ,0
    ));
    public static Spell SEE_EVERYTHING = SpellKeeper.RegisterSpell(new AllSeeingSpell(
            50, SpellSchools.SOUL, 0, "Occulta Sensus", 1200, identifierCreator("see_eyes"),
            6000, false, 0, 4, MAX_LEVEL, 30 ,0
    ));
    public static Spell HEAVENLY_SMITE = SpellKeeper.RegisterSpell(new HeavenlySmiteSpell(
            50, SpellSchools.HOLY, 200, "Heavenly Smite", identifierCreator("heavenly_smite"), 4, MAX_LEVEL, 25
    ));
    public static Spell FIREBALL = SpellKeeper.RegisterSpell(new FireballSpell(50, SpellSchools.FIRE,
            30, "Fireball", 300, identifierCreator("fireball"), 3, MAX_LEVEL, 75));
    public static Spell ALCHEMISTS_BREW = SpellKeeper.RegisterSpell(new AlchemistsBrew(
            SpellSchools.ARCANA, 32,"Alchemist's Brew", 100,
            identifierCreator("alchemists_brew"), 6));
    public static Spell WEATHER_FORECAST = SpellKeeper.RegisterSpell(new WeatherForecastSpell(
            75,100,125,200, SpellSchools.AIR, 0, "Weather Forecast",1200, identifierCreator("controll_weather"), 5, MAX_LEVEL, 75
    ));
    public static Spell GRAVITY_GRAB = SpellKeeper.RegisterSpell(new CommandGrabSpell(50, SpellSchools.ARCANA, 0,
            "Telekinesis", 900, identifierCreator("telekinesis"), 72000, true, 20, 7, MAX_LEVEL, 75));
    public static Spell LEVITATE = SpellKeeper.RegisterSpell(new LevitateOpponentSpell(
            25, SpellSchools.AIR, 0, "Levitate", 100,
            identifierCreator("levitate"), 300, true, 1, 3, MAX_LEVEL, 1));
    public static Spell STASIS = SpellKeeper.RegisterSpell(new StasisSpell(
            50, SpellSchools.ARCANA, 0, "Stasis", 1200,
    identifierCreator("stasis"), 300, true, 1, 4));
    public static Spell FORCE_LEVITATE = SpellKeeper.RegisterSpell(new ForceLevitate(
            50, SpellSchools.AIR, 0, "Shulker Burst", 200,
            identifierCreator("force_levitate"), 4, MAX_LEVEL, 30));
    public static Spell METEOR_SHOWER = SpellKeeper.RegisterSpell(new MeteorShowerSpell(
            40, SpellSchools.FIRE, 0, "Meteor Shower", 300,
            identifierCreator("meteor_shower"), 400, true, 20, 5, MAX_LEVEL, 15
    ));
    public static Spell OVERHEAT = SpellKeeper.RegisterSpell(new OverHeatSpell(
            0, SpellSchools.FIRE, 0, "Overheat", 600,
            identifierCreator("overheat"), 6000, false, 0, 6, 6, 0, 0
    ));
    public static Spell ICE_SHARDS = SpellKeeper.RegisterSpell(new IceShardSpell(50, SpellSchools.ICE,
            10, "Ice Shards", 200, identifierCreator("ice_shard"), 2, MAX_LEVEL, 30));

    public static Spell RAY_OF_FROST = SpellKeeper.RegisterSpell(new RayOfFrost(35, SpellSchools.ICE,
            25, "Ray of Frost", 200, identifierCreator("frost_ray"), 1, MAX_LEVEL, 25));
    public static Spell MENDING = SpellKeeper.RegisterSpell(new MendingSpell(
            100, SpellSchools.ARCANA, 0, "Mending", 100,
            identifierCreator("mending"), 3600, false, 0, 3, 3, 0, 0
    ));
    public static Spell QUICK_FIX = SpellKeeper.RegisterSpell(new QuickRepairs(
            200, SpellSchools.ARCANA, 0, "Quick Fix", 200,
            identifierCreator("quick_fix"), 3, 3, 0
    ));
    public static Spell CHARGE = SpellKeeper.RegisterSpell(new ChargeSpell(
            250, SpellSchools.LIGHTNING, 0, "Lightning Charge", 6000,
            identifierCreator("discharge"), 6000,  false, 0, 5, 9, 75, 0
    ));
    public static Spell LIFESTEAL = SpellKeeper.RegisterSpell(new LifeStealSpell(
            150, SpellSchools.BLOOD, 0, "Lifesteal", 1200,
            identifierCreator("lifesteal"), 1800,  false, 0, 6, 6, 0, 0
    ));
    public static Spell ARCANE_SIGIL = SpellKeeper.RegisterSpell(new ArcaneSigilSpell(
            75, SpellSchools.ARCANA, 60, "Arcane Sigil", 600,
            identifierCreator("arcane_sigil")));
    public static Spell SCULK_BLAST = SpellKeeper.RegisterSpell(new WardenBlastSpell(165, SpellSchools.SOUL,
            80, "Sculk Blast", 200, identifierCreator("sculk_blast"), 80, true, 0, 7, MAX_LEVEL, 50, 5));
    public static Spell SOUL_BURST = SpellKeeper.RegisterSpell(new SoulBurstSpell(165, SpellSchools.SOUL,
            80, "Soul Burst", 200, identifierCreator("soul_burst"), 80, true, 0, 7, MAX_LEVEL, 50, 5));
    public static Spell FROZEN_SHIELD = SpellKeeper.RegisterSpell(new FrostArmorSpell(
            100, SpellSchools.ICE, 0, "Frozen Shield", 3600,
            identifierCreator("frozen_shield"),
            3600, false, 0, 4, 4, 4, 0, 0
    ));
    public static Spell HUNGER_SPELL = SpellKeeper.RegisterSpell(new HungerSpell(
            155, SpellSchools.SOUL, 0, "Essence Leech", 3600,
            identifierCreator("eldritch_hunger"),
            9600, false, 0, 6, 6, 0, 0
    ));
    public static Spell TRUE_INVISIBILITY = SpellKeeper.RegisterSpell(new TrueInvisibilitySpell(
            50, SpellSchools.SHADOW, 0, "True Invisibility", 3600,
            identifierCreator("true_invisibility"),
            6000, false, 0, 4, 4, 0, 0
    ));
    public static Spell CLEANSE = SpellKeeper.RegisterSpell(
            new CleanseSpell(15, SpellSchools.HOLY ,15, "Cleanse",
                    100,identifierCreator("cleanse"), 1, 1, 0)
    );

    public static Spell MAXEDCARP = SpellKeeper.RegisterSpell(
            new FishSummonSpell(10, SpellSchools.ARCANA, 0,
                    "Emergency Rations", 1200,identifierCreator("fish_time"), 0, MAX_LEVEL, 10));

    public static Spell CALLBACK = SpellKeeper.RegisterSpell(
            new SaveLoadSpell(50, SpellSchools.ARCANA, 0, "Callback", 500, identifierCreator("callback"), 100, false, 0, 6, MAX_LEVEL, 45, 0)
    );
    public static Spell ICE_SKATER = SpellKeeper.RegisterSpell(
            new FrostSkaterSpell(85, SpellSchools.ICE, 0, "Ice Skates",
                    1200, identifierCreator("ice_skate"), 250, false,
                    0, 5, MAX_LEVEL, 15, 0
            ));

    public static Spell ELDRITCH_HUNGER = SpellKeeper.RegisterSpell(new
            EldritchHunger(170, SpellSchools.SOUL, 0, "Eldritch Hunger", 1200,
            identifierCreator("eldritch-hunger"), 200, true, 10,
            7, MAX_LEVEL, 105, 5));

    public static Spell WITHER_SKULL = SpellKeeper.RegisterSpell(new
            WitherSkullSpell(100, SpellSchools.SHADOW, 0, "Wither Skull", 60,
            identifierCreator("wither_skull"), 6, MAX_LEVEL, 55));

    public static String FormatSpell(String name, int cost, int level) {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(" - LV : [");
        builder.append(level);
        builder.append("] - Mana : ");
        builder.append(cost);
        return builder.toString();
    }

    public static String FormatSpell(String name, int cost, float tickprice, int level) {
        return FormatSpell(name,cost,level) + " + " + tickprice + "/s";
    }
    public static StringBuilder secStringBuilder(int seccost) {
        StringBuilder builder = new StringBuilder();
        builder.append("Alt - Mana: ");
        builder.append(seccost);
        return builder;
    }
    public static void init() {
        N3rdyR0b1nsSpellEngine.LOGGER.info("Registering Magic Spells for " + N3rdyR0b1nsSpellEngine.MOD_ID);
        new NullSpell();
    }

}
