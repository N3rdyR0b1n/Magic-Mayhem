package com.example.main.SpellUtil;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class HitValues {
    private Vec3d end;
    private boolean hit;

    private Entity target;
    private List<Entity> targets;

    public HitValues(Vec3d end, boolean hit){
        this(end, hit, (Entity) null);
    }
    public HitValues(Vec3d end, boolean hit, Entity target) {
        this.end = end;
        this.hit = hit;
        this.target = target;
    }
    public HitValues(Vec3d end, boolean hit, List<Entity> targets) {
        this.end = end;
        this.hit = hit;
        this.targets = targets;
    }
    public Vec3d endPos() {
        return end;
    }
    public boolean hasHit() {
        return hit;
    }
    public Entity getTarget() {
        return target;
    }
    public List<Entity> getTargets() {
        return targets;
    }

}
