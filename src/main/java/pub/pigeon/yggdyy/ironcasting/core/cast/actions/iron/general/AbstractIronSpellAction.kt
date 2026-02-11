package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general

import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.mishaps.MishapBadCaster
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import net.minecraft.world.entity.LivingEntity
import pub.pigeon.yggdyy.ironcasting.core.cast.capabilities.IronCastingCapProvider

abstract class AbstractIronSpellAction(val ironSpell: AbstractSpell, val level: Int): SpellAction {
    companion object {
        @JvmStatic
        protected fun updateIronCastCounter(entity: LivingEntity?) {
            entity?.getCapability(IronCastingCapProvider.CAPABILITY)?.ifPresent { cap -> cap.ironSpellCount = cap.ironSpellCount + 1 }
        }
        @JvmStatic
        protected fun getCounter(entity: LivingEntity): Int {
            return entity.getCapability(IronCastingCapProvider.CAPABILITY).map { cap -> cap.ironSpellCount + 1 }.orElse(1)
        }
        @JvmStatic
        protected fun checkCastingEntity(entity: LivingEntity?) {
            if(entity == null) {
                throw MishapBadCaster()
            }
        }
        @JvmStatic
        protected fun applyArgFactor(entity: LivingEntity?, factor: Double) {
            entity?.getCapability(IronCastingCapProvider.CAPABILITY)?.ifPresent { cap -> cap.powerArgFactor = factor }
        }
    }
}