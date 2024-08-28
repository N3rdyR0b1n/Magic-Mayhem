package com.example.main.Spells.custom.weather;

import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.Spells.ModSpells;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class StormWeather extends NameCostAction{
    public StormWeather(int price, int up) {
        super(price, up,"Weather : [Storm]");
    }

    @Override
    public void Perform(ItemStack stack, PlayerEntity player, World world, int level) {
        if (world instanceof ServerWorld server) {
            server.setWeather(0, 2400 + 900 * level, true, true);
        }
        List<Entity> entities = GenericSpellAbilities.GetNearbyEntities(50 + 10 * level, world, player, true ,true, Entity.class);
        List<Entity> hitentities = new ArrayList<Entity>();
        for (int i = 0; i < entities.size(); i++) {
            if (world.getRandom().nextFloat() > 0.75f) {
                ModSpells.WEATHER_FORECAST.onHit(player, world, entities.get(i), 1);
                hitentities.add(entities.get(i));
            }
        }
        for (int i = 0; i < hitentities.size(); i++) {
            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            lightning.setPosition(entities.get(i).getPos());
            world.spawnEntity(lightning);
            if (entities.get(i) instanceof LivingEntity living && living.isDead()) {
                ModSpells.WEATHER_FORECAST.onKill(player, world, entities.get(i));
            }
        }
    }
}
