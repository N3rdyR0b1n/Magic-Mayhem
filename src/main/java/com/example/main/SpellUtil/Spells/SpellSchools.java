package com.example.main.SpellUtil.Spells;

import com.example.main.SpellUtil.SpellSchool;

import java.util.HashMap;

public class SpellSchools {
    private static HashMap<String, SpellSchool> Schools = new HashMap<>() {};
    public static SpellSchool getShool(String name) {
        return Schools.get(name);
    }
    public static SpellSchool register(SpellSchool spellSchool, String name) {
        Schools.put(name, spellSchool);
        return spellSchool;
    }
    public static SpellSchool register(SpellSchool spellSchool) {
        Schools.put(spellSchool.getName(), spellSchool);
        return spellSchool;
    }
    public static SpellSchool HOLY = register(new SpellSchool(16771584, "Holy"));
    public static SpellSchool SOUL= register(new SpellSchool(65531, "Soul"));
    public static SpellSchool BLOOD= register(new SpellSchool(12713984, "Blood"));
    public static SpellSchool LIGHTNING= register(new SpellSchool(16056148, "Lightning"));
    public static SpellSchool FIRE = register(new SpellSchool(16748800, "Fire"));
    public static SpellSchool ICE = register( new SpellSchool(6732031, "Ice"));
    public static SpellSchool AIR = register(new SpellSchool(15132390, "Air"));
    public static SpellSchool ARCANA= register(new SpellSchool(15505151, "Arcana"));
    public static SpellSchool SHADOW= register(new SpellSchool(4390722,"Shadow"));
}
