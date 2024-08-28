package com.example.main.Spells.custom;

import com.example.main.Entity.custom.FireBallSpellEntity;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class FireballSpell extends Spell {
    public FireballSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int min, int max, int cost) {
        super(0, school, chargeTime, name, cooldown, texture, min, max, cost);
        super.finishManaCost = manaCost;
        super.text = GetText();
    }

    @Override
    public void castReleaseSpell(PlayerEntity player, World world, ItemStack stack, int tick) {
        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1, 0.75f);
        FireBallSpellEntity fireball = new FireBallSpellEntity(player, world, 25f + 2.5f * Level(), 1 + 0.4f * Level(), 1.5f, 1f, 0.5f, Level());
        fireball.setVelocity(player, player.getPitch(), player.getYaw(),0, 2f, 0 );
        world.spawnEntity(fireball);
    }

}
