package com.example.main.Entity;

import com.example.N3rdyR0b1nsSpellEngine;
import com.example.main.Entity.custom.*;
import com.example.main.Entity.sigils.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModEntities {

    public static final EntityType<FireBallSpellEntity> FIREBALL = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "fire_ball"),
            FabricEntityTypeBuilder.<FireBallSpellEntity>create(SpawnGroup.MISC, FireBallSpellEntity::new)
                    .dimensions(new EntityDimensions(3,3,true))
                    .build()
    );

    public static final EntityType<ChromaticOrbEntity> CHROMATIC_ORB = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "chromatic_orb"),
            FabricEntityTypeBuilder.<ChromaticOrbEntity>create(SpawnGroup.MISC, ChromaticOrbEntity::new)
                    .dimensions(new EntityDimensions(1,1,true))
                    .build()
    );

    public static final EntityType<MagicMissileEntity> MAGIC_MISSILE = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "magic_missile"),
            FabricEntityTypeBuilder.<MagicMissileEntity>create(SpawnGroup.MISC, MagicMissileEntity::new)
                    .dimensions(new EntityDimensions(1,1,true))
                    .build()
    );

    public static final EntityType<HeavenlySmiteEntity> HEAVENLY_SMITE = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "heavenly_smite"),
            FabricEntityTypeBuilder.<HeavenlySmiteEntity>create(SpawnGroup.MISC, HeavenlySmiteEntity::new)
                    .dimensions(new EntityDimensions(1,1,true))
                    .build()
    );


    public static final EntityType<GravityHelperEntity> GRAVITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "gravity"),
            FabricEntityTypeBuilder.<GravityHelperEntity>create(SpawnGroup.MISC, GravityHelperEntity::new)
                    .dimensions(new EntityDimensions(0,0,true))
                    .build()
    );

    public static final EntityType<MeteorEntity> METEOR = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "meteor"),
            FabricEntityTypeBuilder.<MeteorEntity>create(SpawnGroup.MISC, MeteorEntity::new)
                    .dimensions(new EntityDimensions(0,0,true))
                    .build()
    );
    public static final EntityType<IcycleEntity> ICYCLE = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "icycle_shard"),
            FabricEntityTypeBuilder.<IcycleEntity>create(SpawnGroup.MISC, IcycleEntity::new)
                    .dimensions(new EntityDimensions(0.5f,0.5f,true))
                    .build()
    );

    public static final EntityType<FireSigil> FIRE_SIGIL = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "fire_sigil"),
            FabricEntityTypeBuilder.<FireSigil>create(SpawnGroup.MISC, FireSigil::new)
                    .dimensions(new EntityDimensions(1,1,true))
                    .build()
    );
    public static final EntityType<IceSigil> ICE_SIGIL = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "ice_sigil"),
            FabricEntityTypeBuilder.<IceSigil>create(SpawnGroup.MISC, IceSigil::new)
                    .dimensions(new EntityDimensions(1,1,true))
                    .build()
    );
    public static final EntityType<CurseSigil> CURSE_SIGIL = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "curse_sigil"),
            FabricEntityTypeBuilder.<CurseSigil>create(SpawnGroup.MISC, CurseSigil::new)
                    .dimensions(new EntityDimensions(1,1,true))
                    .build()
    );
    public static final EntityType<ExplosionSigil> EXPLOSION_SIGIL = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "explosion_sigil"),
            FabricEntityTypeBuilder.<ExplosionSigil>create(SpawnGroup.MISC, ExplosionSigil::new)
                    .dimensions(new EntityDimensions(1,1,true))
                    .build()
    );
    public static final EntityType<ImplosionSigil> IMPLOSION_SIGIL = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "implosion_sigil"),
            FabricEntityTypeBuilder.<ImplosionSigil>create(SpawnGroup.MISC, ImplosionSigil::new)
                    .dimensions(new EntityDimensions(1,1,true))
                    .build()
    );
    public static final EntityType<KnockbackSigil> PUSH_SIGIL = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(N3rdyR0b1nsSpellEngine.MOD_ID, "knockback_sigil"),
            FabricEntityTypeBuilder.<KnockbackSigil>create(SpawnGroup.MISC, KnockbackSigil::new)
                    .dimensions(new EntityDimensions(1,1,true))
                    .build()
    );


    public static void init() {

    }

}
