package pub.pigeon.yggdyy.ironcasting.mixins;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.item.IotaHolderItem;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.item.Scroll;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import pub.pigeon.yggdyy.ironcasting.core.cast.Utils;

@Implements(@Interface(iface = IotaHolderItem.class, prefix = "ironCasting$"))
@Mixin(Scroll.class)
public abstract class ScrollMixin{
    @Shadow(remap = false) @NotNull protected abstract SpellData getSpellSlotFromStack(ItemStack itemStack);
    public @Nullable CompoundTag ironCasting$readIotaTag(ItemStack itemStack) {
        var spellData = getSpellSlotFromStack(itemStack);
        var spell = spellData.getSpell();
        var level = spellData.getLevel();
        return IotaType.serialize(new PatternIota(Utils.getPatternForIronSpell(spell, level)));
    }
    public boolean ironCasting$writeable(ItemStack itemStack) {
        return false;
    }
    public boolean ironCasting$canWrite(ItemStack itemStack, @Nullable Iota iota) {
        return false;
    }
    public void ironCasting$writeDatum(ItemStack itemStack, @Nullable Iota iota) {

    }
}
