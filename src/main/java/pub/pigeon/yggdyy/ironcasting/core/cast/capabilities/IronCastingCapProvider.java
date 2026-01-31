package pub.pigeon.yggdyy.ironcasting.core.cast.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IronCastingCapProvider implements ICapabilitySerializable<CompoundTag>{
    public static Capability<IIronCastingCap> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    private IIronCastingCap backend = new IronCastingCap();
    private final LazyOptional<IIronCastingCap> optional = LazyOptional.of(() -> backend);
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, optional);
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putDouble("power_arg_factor", backend.getPowerArgFactor());
        nbt.putInt("spell_count", backend.getIronSpellCount());
        return nbt;
    }
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        backend.setPowerArgFactor(nbt.contains("power_arg_factor")? nbt.getDouble("power_arg_factor") : 1.0);
        backend.setIronSpellCount(nbt.contains("spell_count")? nbt.getInt("spell_count") : 0);
    }
}
