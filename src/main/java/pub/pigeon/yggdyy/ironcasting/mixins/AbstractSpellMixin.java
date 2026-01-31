package pub.pigeon.yggdyy.ironcasting.mixins;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pub.pigeon.yggdyy.ironcasting.Config;
import pub.pigeon.yggdyy.ironcasting.core.cast.capabilities.IIronCastingCap;
import pub.pigeon.yggdyy.ironcasting.core.cast.capabilities.IronCastingCapProvider;

@Mixin(AbstractSpell.class)
public class AbstractSpellMixin {
    @Inject(remap = false, cancellable = true, method = "getSpellPower", at = @At("RETURN"))
    public void applyPowerArgFactor(int spellLevel, Entity sourceEntity, CallbackInfoReturnable<Float> cir) {
        if(sourceEntity == null) {
            return;
        }
        cir.setReturnValue((float) (cir.getReturnValue() * sourceEntity.getCapability(IronCastingCapProvider.CAPABILITY).map(IIronCastingCap::getPowerArgFactor).orElse(1.0) * Config.POWER_FACTOR.get()));
    }
    /*@Inject(remap = false, cancellable = false, method = "castSpell", at = @At("HEAD"))
    public void debug(Level world, int spellLevel, ServerPlayer serverPlayer, CastSource castSource, boolean triggerCooldown, CallbackInfo ci) {
        serverPlayer.sendSystemMessage(Component.literal(serverPlayer.tickCount + ""));
    }*/
}
