package com.example.main.Item.armor;

public class MagicAttributesHolder<K extends Enum<K>> {

    private MagicAttributeContainer[] attributes;


    public MagicAttributesHolder(MagicAttributeContainer[] attributesHolderList) {
        this.attributes = attributesHolderList;
    }



    public MagicAttributeContainer getContainer(Enum<K> key) {
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].AllignsWith(key)) {
                return attributes[i];
            }
        }
        return null;
    }
}
