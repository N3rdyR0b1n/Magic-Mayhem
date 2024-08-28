package com.example.main.SpellUtil.Spells;

import com.example.main.Attributes.ModAttributes;
import com.example.main.ModS2CMessages.ModPacketChannels;
import com.example.main.SpellUtil.Mana;
import com.example.main.SpellUtil.ManaContainer;
import com.example.main.Spells.ModSpells;
import com.example.main.SpellUtil.SpellSchool;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.nbt.scanner.NbtScanner;
import net.minecraft.nbt.visitor.NbtElementVisitor;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public abstract class Spell extends Action implements Cloneable, Serializable, NbtElement {
    public int manaCost;
    public int finishManaCost;
    public int id = -1;
    public int chargeTime;
    public int SpellCooldown;
    public String texture;
    public String name;
    public int CooldownProgress;
    protected byte level = 0;
    protected byte maxlevel = 0;
    protected byte minlevel = 0;
    protected int UpcastCost;
    public String text;
    public SpellSchool schools;
    public NbtCompound ToNbt() {
        NbtCompound compound = new NbtCompound();
        compound.putInt("ManaCost", manaCost);
        compound.putInt("Id", id);
        compound.putInt("ChargeTime", chargeTime);
        compound.putInt("Cooldown", SpellCooldown);
        compound.putInt("CooldownProgress", CooldownProgress);
        compound.putString("Name", name);
        compound.putByte("Level", level);
        compound.putInt("LevelPrice", UpcastCost);
        compound.put(NbtS.SPELL, this.clone());
        return compound;
    }

    public void UpdateFromNbt(NbtCompound compound) {
       this.manaCost = compound.getInt("ManaCost");
       this.schools = (SpellSchools.getShool(compound.getString("School")));
       this.chargeTime = compound.getInt("ChargeTime");
       this.name = compound.getString("Name");
       this.SpellCooldown = compound.getInt("Cooldown");
       this.CooldownProgress = compound.getInt("CooldownProgress");
       this.level = compound.getByte("Level");
       this.UpcastCost = compound.getInt("LevelPrice");
    }
    public Spell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture) {
        this(manaCost, school, chargeTime, name, cooldown, texture, 0, 0, 0);
    }

    public Spell(int manaCost, SpellSchool school, int chargeTime, String name, int cooldown, Identifier texture, int minlevel, int maxlevel, int upcastCost) {
        this.manaCost = manaCost;
        this.chargeTime = chargeTime;
        this.texture = texture.getNamespace() + ":" + texture.getPath();
        this.SpellCooldown = cooldown;
        this.name = name;
        this.schools = school;
        this.minlevel = (byte) minlevel;
        this.level = (byte) minlevel;
        this.maxlevel = (byte) maxlevel;
        this.UpcastCost = upcastCost;
        this.text = GetText();
    }
    public Identifier getIdentifier() {
        return new Identifier(texture);
    }
    public void castSpell(PlayerEntity player, World world, ItemStack stack) {
    }
    public void castSecSpell(PlayerEntity player, World world, ItemStack stack) {
    }

    public void castReleaseSpell(PlayerEntity player, World world, ItemStack stack, int tick) {
    }

    public String getExtraInfo(ItemStack stack) {
        return "";
    }
    public int getExtraInfoColor(ItemStack stack) {
        return 0;
    }
    public int getInfoColor(ItemStack stack) {
        return -1;
    }
    public int getInventoryColor() {
        if (schools != null) {
            return schools.getColor();
        }
        return 16777215;
    }


    public String getInfo(PlayerEntity player, ItemStack stack) {
        return text;
    }

    public int GetChargeTime(PlayerEntity player, ItemStack stack) {
        int chargetime = (int) (chargeTime / player.getAttributeValue(ModAttributes.CAST_SPEED));
        if (chargetime == 0 && this.chargeTime != 0) {
            return 1;
        }
        return chargetime;
    }

    @Override
    public void onHit(PlayerEntity player, World world, @Nullable Entity target, float hitlvl ) {
        if (hitlvl == 0){
            return;
        }
        Mana mana = ((ManaContainer) player).getMana();
        if (!world.isClient) {
            float a = (float) player.getAttributeValue(ModAttributes.MANA_ON_HIT);
            if (a > 0) {
                mana.addMana(a);
                PacketByteBuf sync = PacketByteBufs.create();
                sync.writeFloat(a);
                ServerPlayNetworking.send((ServerPlayerEntity) player, ModPacketChannels.ADDMANAFROMSERVER, sync);
            }
        }
        player.heal((float) (player.getAttributeValue(ModAttributes.HP_ON_HIT) * hitlvl));
    }

    @Override
    public void onKill(PlayerEntity player, World world, @Nullable Entity target) {
        {
            Mana mana = ((ManaContainer) player).getMana();
            if (!world.isClient) {
                float a = (float) player.getAttributeValue(ModAttributes.MANA_ON_KILL);
                if (a > 0) {
                    mana.addMana(a);
                    PacketByteBuf sync = PacketByteBufs.create();
                    sync.writeFloat(a);
                    ServerPlayNetworking.send((ServerPlayerEntity) player, ModPacketChannels.ADDMANAFROMSERVER, sync);
                }
            }

        }
        int healtboost = (int) player.getAttributeValue(ModAttributes.LIFESTEAL);
        if (healtboost > 0) {
            if (player.hasStatusEffect(StatusEffects.HEALTH_BOOST)) {
                healtboost = (player.getStatusEffect(StatusEffects.HEALTH_BOOST).getAmplifier() + healtboost);
            }
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 1200, healtboost-1));
        }
        player.heal((float) (player.getAttributeValue(ModAttributes.HP_ON_KILL)));
    }

    public int getCooldown(PlayerEntity player, ItemStack stack) {
        return SpellCooldown;
    }

    public boolean canCastSpell(PlayerEntity player, World world, ItemStack stack, NbtCompound nbt) {
        return CooldownProgress <= 0 && ((ManaContainer)player).getMana().getStoredMana() >= Manacost();
    }
    public boolean canSecCastSpell(PlayerEntity player, World world, ItemStack stack,NbtCompound nbt) {
        return false;
    }
    public boolean canCastFinishSpell(PlayerEntity player, World world, ItemStack stack,NbtCompound nbt, int tick) {
        return CooldownProgress <= 0 && ((ManaContainer)player).getMana().getStoredMana() >= FinManacost() && tick >= GetChargeTime(player, stack);
    }

    public void applyCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        ((ManaContainer)player).getMana().removeMana(Manacost());
    }
    public void applySecCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {

    }
    public void applyFinishCost(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        ((ManaContainer)player).getMana().removeMana(FinManacost());
        applyCooldown(player, stack, nbt, slot);
    }
    public void applyCooldown(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {
        if (!player.getWorld().isClient()) {
            CooldownProgress = SpellCooldown;
        }
    }

    public void applySecCooldown(PlayerEntity player, ItemStack stack, NbtCompound nbt, int slot) {

    }

    public Spell clone(){
        try {
            return (Spell)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return name;
    }

    /**
     * {@return the type of this NBT element}
     */
    public byte getType() {
        return SPELLNBT;
    }

    /**
     * {@return the NBT type definition of this NBT element}
     */
    public static final byte SPELLNBT = 101;
    public NbtType<?> getNbtType() {
        return TYPE;
    }

    /**
     * {@return an NBT element of equal value that won't change with this element}
     */
    public NbtElement copy() {
        return this;
    }

    public int getSizeInBytes() {
        return this.Serialise().length + 24;
    }

    /**
     * {@return the NBT's string representation}
     *
     * @implNote By default, this returns the same result as {@link
     * net.minecraft.nbt.visitor.StringNbtWriter}. {@link } will return its
     * string value instead.
     */


    public void accept(NbtElementVisitor var1) {
        var1.visitString(NbtString.of(name));
    }

    public NbtScanner.Result doAccept(NbtScanner var1) {
        return NbtScanner.Result.BREAK;
    }
    public static final NbtType<Spell> TYPE = new NbtType.OfVariableSize<Spell>(){

        @Override
        public Spell read(DataInput dataInput, int i, NbtTagSizeTracker nbtTagSizeTracker) throws IOException {
            return Spell.readSpell(dataInput, nbtTagSizeTracker);
        }

        @Override
        public NbtScanner.Result doAccept(DataInput input, NbtScanner visitor) throws IOException {
            byte[] a = new byte[input.readShort()];
            input.readFully(a);
            visitor.visitByteArray(a);
            return NbtScanner.Result.CONTINUE;
        }

        @Override
        public void skip(DataInput input) throws IOException {
            int a = input.readInt();
            input.skipBytes(a);
        }

        @Override
        public String getCrashReportName() {
            return "SPELL_NBT";
        }

        @Override
        public String getCommandFeedbackName() {
            return "SPELL+NBT";
        }


    };
    static Spell readSpell(DataInput input, NbtTagSizeTracker tracker) throws IOException {
        int a = input.readInt();
        tracker.add(a);
        byte[] data = new byte[a];
        input.readFully(data);
        try {
            ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = stream.readObject();
            stream.close();
            return (Spell) o;
        } catch (IOException | ClassNotFoundException e) {
            return new NullSpell();
        }
    }

    @Override
    public void write(DataOutput output) throws IOException {
        byte[] result = Serialise();
        if (result.length == 0) {
            result = NullSpell.Serialised;
        }
        int a = result.length;
        output.writeInt(a);
        output.write(result);
    }
    public boolean UpdateCooldown(PlayerEntity player, World world, ItemStack stack, NbtCompound compound) {
        if (!world.isClient() && CooldownProgress > 0) {
            CooldownProgress--;
            return true;
        }
        return false;
    }
    public byte[] Serialise() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        }
        catch (IOException exception) {
            return new byte[0];
        }
        return outputStream.toByteArray();
    }

    @Override
    public String asString() {
        return name;
    }
    public boolean ScrollDelete(World world, PlayerEntity player, ItemStack stack) {
        return true;
    }

    public boolean Upcast(PlayerEntity player) {
        level++;
        if (level > maxlevel) {
            level = maxlevel;
            return false;
        }
        if (level > player.getAttributeValue(ModAttributes.CASTING_LEVEL)) {
            level = (byte) player.getAttributeValue(ModAttributes.CASTING_LEVEL);
            if (level < minlevel) {
                level = minlevel;
            }
            return false;
        }
        return true;
    }

    public boolean DownCast(PlayerEntity player) {
        level--;
        if (level < minlevel) {
            level = minlevel;
            return false;
        }
        if (level > player.getAttributeValue(ModAttributes.CASTING_LEVEL)) {
            level = (byte) player.getAttributeValue(ModAttributes.CASTING_LEVEL);
            if (level < minlevel) {
                level = minlevel;
            }
        }
        return true;
    }
    public int Manacost() {
        if (manaCost == 0) {
            return 0;
        }
        return manaCost + UpcastCost * Level();
    }
    public int FinManacost() {
        if (finishManaCost == 0) {
            return 0;
        }
        return finishManaCost + UpcastCost * (level - minlevel);
    }
    public byte Level() {
        return (byte) (level - minlevel);
    }
    public String GetText() {
        if (Manacost() == 0 && FinManacost() != 0) {
            return ModSpells.FormatSpell(name,FinManacost(), level);
        }
        return ModSpells.FormatSpell(name,Manacost(), level);
    }
    public byte getLevel() {
        return level;
    }


        public Text[] GetDescription(PlayerEntity player, ItemStack stack) {
        Text[] text = new Text[5];
        text[0] = Text.translatable(name + " - [" + level + "]").setStyle(Style.EMPTY.withColor(getInventoryColor()));
        text[1] = Text.translatable(schools.getName()).setStyle(Style.EMPTY.withColor(getInventoryColor()));
        int cost = Manacost();
        if (cost == 0 && FinManacost() != 0) {
            cost = FinManacost();
        }
        text[2] = Text.translatable("Mana : " + cost).setStyle(Style.EMPTY.withColor(Formatting.AQUA));
        String cooldown = "Cooldown : ";
        if (getCooldown(player, stack) > 0) {
            cooldown += ((float)getCooldown(player, stack) / 20);
        }
        else {
            cooldown += 0;
        }
        cooldown += "s";
        text[3] = Text.translatable(cooldown).setStyle(Style.EMPTY.withColor(Formatting.GRAY));
        text[4] = Text.of("min: " + minlevel + " - max : " + maxlevel);
        return text;
    }
    public int getCooldownProgress(PlayerEntity player, ItemStack stack) {
        return CooldownProgress;
    }
}
