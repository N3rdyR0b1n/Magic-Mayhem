package com.example.main.Entity.custom;

import com.example.main.Entity.ModEntities;
import com.example.main.Item.ModItems;
import com.example.main.SpellUtil.GenericSpellAbilities;
import com.example.main.Spells.ModSpells;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireBallSpellEntity extends ThrownItemEntity {
    private int hittime = -1;
    private ItemStack stack;

    private float damage;
    private static TrackedData<Float> Radius = DataTracker.registerData(FireBallSpellEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private float knockback;
    private float stronghit;
    private float weakhit;
    private static TrackedData<Integer> Level = DataTracker.registerData(FireBallSpellEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public FireBallSpellEntity(LivingEntity livingEntity, World world, float damage, float radius, float knockback, float stronscale, float weakscale, int level) {
        super(ModEntities.FIREBALL, livingEntity, world);
        this.setOwner(livingEntity);
        this.damage = damage;
        this.knockback = knockback;
        this.stronghit = stronscale;
        this.weakhit = weakscale;
        this.dataTracker.set(Radius, radius);
        this.dataTracker.set(Level, level);
    }

    public FireBallSpellEntity(EntityType<FireBallSpellEntity> fireBallSpellEntityEntityType, World world) {
        super(ModEntities.FIREBALL, world);
    }
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(Radius,0f);
        this.dataTracker.startTracking(Level, 0);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        explode(entityHitResult.getPos());
    }
    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        explode(blockHitResult.getPos());
    }

    private void explode(Vec3d pos) {
        if (hittime == -1) {
            World world = this.method_48926();

            int level = this.dataTracker.get(Level);
            float radius = this.dataTracker.get(Radius);

            this.method_48926().playSound(null, this.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 10, 1);
            this.setPosition(pos);
            if (this.getOwner() instanceof PlayerEntity player) {
                GenericSpellAbilities.explodeFlatAoe(damage, knockback, radius, this.method_48926(), this, player, ModSpells.FIREBALL, stronghit, weakhit);
            }
            double y = this.getY() + 0.25f;
            float rad = 1f /(20f + 4f * level);
            float t_rad = 5f * radius;
            for (float i = 0; i < 6.283f; i += rad) {
                world.addImportantParticle(ParticleTypes.FLAME, true, (MathHelper.sin(i) * t_rad) + this.getX(), y, (MathHelper.cos(i) * t_rad) + this.getZ(), 0, 0.05, 0);
            }
            float expart = radius / 2 + 1f;
            int particles = (4 + level) * 35;
            for (int a = 0; a < particles; a++) {
                Vec3d explode = new Vec3d(random.nextFloat() - 0.5f, random.nextFloat()/2, random.nextFloat()- 0.5f).normalize().multiply(expart + random.nextFloat() / 3);
                world.addImportantParticle(ParticleTypes.FLAME, true, +this.getX(), y, +this.getZ(), explode.x, explode.y, explode.z);
            }
            world.addImportantParticle(ParticleTypes.EXPLOSION, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            hittime++;
        }
    }


    @Override
    public void tick() {
        if (hittime > -1) {
            if (hittime > 9) {
                this.kill();
            }
            hittime++;
        }
        Vec3d particlevec = this.getVelocity().multiply(-1);
        World world = this.method_48926();
        for (int i = 0; i < 5; i ++) {
            world.addImportantParticle(ParticleTypes.FLAME, true,
                this.getX() + (random.nextFloat() - 0.5f),
                this.getY() + (random.nextFloat() - 0.5f),
                this.getZ() + (random.nextFloat() - 0.5f),
                (random.nextFloat() - 0.5f)/4 + particlevec.x,
                (random.nextFloat() - 0.5f)/4 + particlevec.y,
                (random.nextFloat() - 0.5f)/4 + particlevec.z);
        }
        super.tick();
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.FIREBALL;
    }

    @Override
    protected float getGravity() {
        return 0.01f;
    }

}
