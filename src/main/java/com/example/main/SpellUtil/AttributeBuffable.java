package com.example.main.SpellUtil;

import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;

public interface AttributeBuffable {

    public String AddAttributeModifier(Multimap<EntityAttribute, EntityAttributeModifier> modifier, int duration, boolean stackable, String identifier);

    public boolean maintainModifier(String identifier, int newduration);



}
