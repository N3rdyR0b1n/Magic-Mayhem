package com.example.main.SpellUtil;

import java.io.Serializable;

public class SpellSchool implements Cloneable, Serializable {
    private int color;
    private String name;
    public SpellSchool(int color, String name) {
        this.color=color;
        this.name=name;
    }
    public int getColor() {
        return color;
    }
    public String getName() {
        return name;
    }

}
