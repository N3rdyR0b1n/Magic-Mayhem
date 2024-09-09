package com.example.main.SpellUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.Inject;

public class ProcessedDamageSource extends DamageSource {
    private final RegistryEntry<DamageType> type;
    @Nullable
    private final Entity attacker;
    @Nullable
    private final Entity source;
    @Nullable
    private final Vec3d position;
    public ProcessedDamageSource(RegistryEntry<DamageType> type, @Nullable Entity source, @Nullable Entity attacker, @Nullable Vec3d position) {
        super(type, source, attacker);
        this.type =type;
        this.attacker =attacker;
        this.source = source;
        this.position = position;
    }
    @Override
    @Nullable
    public Vec3d getPosition() {
        if (this.position != null) {
            return this.position;
        } else {
            return this.source != null ? this.source.getPos() : null;
        }
    }

}
