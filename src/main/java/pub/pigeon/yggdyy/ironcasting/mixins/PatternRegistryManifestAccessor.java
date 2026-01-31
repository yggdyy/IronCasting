package pub.pigeon.yggdyy.ironcasting.mixins;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.math.HexAngle;
import at.petrak.hexcasting.common.casting.PatternRegistryManifest;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

@Mixin(PatternRegistryManifest.class)
public interface PatternRegistryManifestAccessor {
    @Accessor(remap = false)
    static ConcurrentMap<List<HexAngle>, ResourceKey<ActionRegistryEntry>> getNORMAL_ACTION_LOOKUP() {
        throw new UnsupportedOperationException();
    }
}
