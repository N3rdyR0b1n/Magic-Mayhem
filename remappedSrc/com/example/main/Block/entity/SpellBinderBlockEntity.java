package com.example.main.Block.entity;

import com.example.main.Block.ModBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class SpellBinderBlockEntity extends BlockEntity {


    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
    public SpellBinderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.SPELL_APPLICATION_TABLE, pos, state);
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

    }

    public Text getName() {
        return Text.translatable("block.spell.bind");
    }




}
