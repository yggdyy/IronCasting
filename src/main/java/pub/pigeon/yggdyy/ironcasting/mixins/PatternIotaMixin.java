package pub.pigeon.yggdyy.ironcasting.mixins;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.AbstractIronSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.SimpleIronSpellAction;

@Mixin(PatternIota.class)
public class PatternIotaMixin {
    @Inject(method = "display", remap = false, at = @At("HEAD"), cancellable = true)
    private static void displayIronSpell(HexPattern pat, CallbackInfoReturnable<Component> cir) {
        if(pat.getAngles().size() > 16) {
            if(PatternRegistryManifestAccessor.getNORMAL_ACTION_LOOKUP().containsKey(pat.getAngles())) {
                ResourceKey<ActionRegistryEntry> key = PatternRegistryManifestAccessor.getNORMAL_ACTION_LOOKUP().get(pat.getAngles());
                if(HexActions.REGISTRY.get(key).action() instanceof AbstractIronSpellAction spell) {
                    cir.setReturnValue(Component.literal(String.format("[IronsSpellIcon:%s]", spell.getIronSpell().getSpellId())));
                }
            }
        }
    }
}
