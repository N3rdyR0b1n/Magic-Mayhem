package com.example.main.Item.armor;

public class MagicAttributeContainer<K extends Enum<K>> {

    private final Enum<K> key;
    private final float addedHealth;
    private final float addedMana;
    private final float addedManaRegeneration;
    private final float addedManaPenalty;
    private final float addedPassiveManaRegeneration;
    public MagicAttributeContainer(Enum<K> key, float addedHealth, float addedMana, float addedManaRegeneration, float addedManaPenalty, float addedPassiveManaRegeneration) {
        this.key = key;
        this.addedHealth = addedHealth;
        this.addedMana = addedMana;
        this.addedManaRegeneration = addedManaRegeneration;
        this.addedManaPenalty = addedManaPenalty;
        this.addedPassiveManaRegeneration = addedPassiveManaRegeneration;
    }
    public boolean AllignsWith(Enum<K> key) {
        return (this.key == key);
    }

    public float getExtraHealth() {
        return addedHealth;
    }
    public float getExtraMana() {
        return addedMana;
    }
    public float getRegen() {
        return addedManaRegeneration;
    }
    public float getPassiveRegen() {
        return addedPassiveManaRegeneration;
    }
    public float getLoss() {
        return addedManaPenalty;
    }
}
