package com.example.main.Spells.custom;

import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.SpellUtil.HitValues;
import com.example.main.SpellUtil.SpellSchool;
import com.example.main.SpellUtil.Spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class LightningStrikeSpell extends Spell {
    public LightningStrikeSpell(int manaCost, SpellSchool school, int chargeTime, int cooldown, Identifier texture) {
        super(0, school, chargeTime, "Zap", cooldown, texture);
        super.finishManaCost = manaCost;
        super.text = GetText();
    }
    @Override
    public void castReleaseSpell(PlayerEntity player, World world, ItemStack stack, int tick) {
        world.playSound(null,player.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1, 0.75f);
        HitValues values = GenericSpellAbilities.HitscanAttack(player, world, 255, 20, 17.5f, true, this, stack);
        GenericSpellAbilities.addParticles(world, player.getEyePos().subtract(0,0.25f,0), values.endPos(), ParticleTypes.ELECTRIC_SPARK, 4);
        BlockHitResult blockHitResult = world.raycast(new RaycastContext(player.getEyePos(), player.getEyePos().add(player.getRotationVec(0.5F).multiply(20)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player));
        if (values.hasHit() || (blockHitResult.getType() == HitResult.Type.BLOCK)) {
            world.playSound(null,player.getBlockPos(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 1, 2f);
        }
        player.addVelocity(player.getRotationVec(0.5F).multiply(-1));

    }
}
