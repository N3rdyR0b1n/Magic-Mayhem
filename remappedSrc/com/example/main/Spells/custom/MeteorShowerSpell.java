package com.example.main.Spells.custom;

import com.example.main.Entity.custom.MeteorEntity;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.Spells.extra.ContinousUsageSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MeteorShowerSpell extends ContinousUsageSpell {
    public MeteorShowerSpell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int useTime, boolean needsHeld, int overtimecost, int min, int max, int overtime) {
        super(manaCost, school, chargeTime, name, cooldown, texture, useTime, needsHeld, overtimecost, min, max, 0, overtime);
    }


    @Override
    public void tickAction(PlayerEntity user , World world, ItemStack stack, int index, int tick) {
        if (tick % 4 == 0) {
            int distance = 10 + Level() * 3;
            for (int i = 0; i < Level() + 1; i++) {
                Vec3d pos = GenericSpellAbilities.HitscanSelect(world, user, 256, false).endPos();
                pos = pos.add((world.random.nextFloat() - 0.5f) * distance, 256, (world.random.nextFloat() - 0.5f) * distance);
                MeteorEntity meteor = new MeteorEntity(user, world, stack);
                meteor.setPosition(pos);
                meteor.setVelocity(0, -2, 0);
                world.spawnEntity(meteor);
            }
        }
    }



}
