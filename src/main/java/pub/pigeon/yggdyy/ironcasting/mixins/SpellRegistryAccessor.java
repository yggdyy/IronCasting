package pub.pigeon.yggdyy.ironcasting.mixins;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraftforge.registries.DeferredRegister;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SpellRegistry.class)
public interface SpellRegistryAccessor {
    @Accessor(remap = false)
    static DeferredRegister<AbstractSpell> getSPELLS() {
        throw new UnsupportedOperationException();
    }
}
