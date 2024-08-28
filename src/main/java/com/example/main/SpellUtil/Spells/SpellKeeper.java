package com.example.main.SpellUtil.Spells;



import java.util.*;

public class SpellKeeper {
    public static List<Spell> SpellRegisty = new ArrayList<Spell>();


    public static Spell RegisterSpell(Spell spell) {
        spell.id = SpellRegisty.size();
        SpellRegisty.add(spell);
        return spell;
    }

    public static boolean spellExists(int spellID) {
        if (spellID >= 0 && SpellRegisty.size() > spellID) {
            return true;
        }
        return false;
    }

    public static Spell getSpell(int index) {
        if (spellExists(index)) {
            return SpellRegisty.get(index);
        }
        return null;
    }

    public static void addSpellToSpellScroll(Spell spell) {

    }

}
