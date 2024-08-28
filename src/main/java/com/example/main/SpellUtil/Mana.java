package com.example.main.SpellUtil;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;

public class Mana {
    private float storedMana;
    private int maxMana;
    public boolean visible;
    public Mana (int maxMana, boolean visible) {
        this.maxMana = maxMana;
        this.storedMana = 0;
        this.visible = visible;
    }
    public float addMana(float amount) {
        storedMana += amount;
        if (storedMana > maxMana) {
            float returnvalue = storedMana-maxMana;
            storedMana = maxMana;
            return  returnvalue;
        }
        return 0;
    }
    public float removeMana (float amount) {
        storedMana -= amount;
        if (storedMana < 0) {
            float returnvalue = -storedMana;
            storedMana = 0;
            return returnvalue;
        }
        return 0;
    }

    public boolean hasEnoughMana(int removalvalue) {
        if (storedMana - removalvalue >= 0) {
            return  true;
        }
        return  false;
    }
    public float getStoredMana() {
        return  storedMana;
    }
    public int getMaxMana() {
        return  maxMana;
    }
    public float getManaPercent() {
        return  (float) storedMana/(float) maxMana;
    }

    public void setMaxMana(int newMax) {
        this.maxMana = newMax;
        if (maxMana < 0) {
            maxMana = 0;
        }
    }

    public void setStoredMana(float mana) {
        this.storedMana = mana;
        if (storedMana < 0) {
            storedMana = 0;
        }
    }
}
