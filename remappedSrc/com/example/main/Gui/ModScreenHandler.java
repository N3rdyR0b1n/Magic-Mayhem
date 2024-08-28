package com.example.main.Gui;

import com.example.N3rdyR0b1nsSpellEngine;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandler {
    public static ScreenHandlerType<MagicTableScreenHandler> SPELL_APPLICATION_SCREEN_HANDLER = new ScreenHandlerType<>(MagicTableScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

    public static void registerAllScreenHandlers() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "spell_application_screen"),
                SPELL_APPLICATION_SCREEN_HANDLER);


    }

}
