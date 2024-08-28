package com.example;

import com.example.main.Attributes.ModAttributes;
import com.example.main.Block.ModBlockEntityTypes;
import com.example.main.Block.ModBlocks;
import com.example.main.Entity.ModEntities;
import com.example.main.Event.PlayerTickHandler;
import com.example.main.Gui.ModScreenHandler;
import com.example.main.Item.ModItemGroup;
import com.example.main.Item.ModItems;
import com.example.main.ModS2CMessages.ModPacketChannels;
import com.example.main.SpellUtil.DamageTypes.ModDamageTypes;
import com.example.main.SpellUtil.Spells.Action;
import com.example.main.SpellUtil.Spells.Spell;
import com.example.main.Spells.ModSpells;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class N3rdyR0b1nsSpellEngine implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("magic_mayhem_spell");
	public static final String MOD_ID = "magic_mayhem_spell";


	@Override
	public void onInitialize() {
		ModDamageTypes.init();
		ModSpells.init();

		ModEntities.init();
		ModAttributes.init();
		ModItemGroup.registerItemGroup();
		ModItems.registerModItems();
		ModPacketChannels.registerChannelIds();

		ModScreenHandler.registerAllScreenHandlers();
		ModBlocks.init();
		ModBlockEntityTypes.init();

		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Magical Fabric world :3!");
	}
}