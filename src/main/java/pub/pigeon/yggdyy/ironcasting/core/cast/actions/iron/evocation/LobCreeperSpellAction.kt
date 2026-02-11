package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.evocation

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
import io.redspace.ironsspellbooks.entity.spells.creeper_head.CreeperHeadProjectile
import net.minecraft.world.entity.LivingEntity
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractProjectileIronSpellAction

class LobCreeperSpellAction(spell: AbstractSpell, level: Int): AbstractProjectileIronSpellAction(spell, level, false) {
    override fun constructProjectile(env: CastingEnvironment): AbstractMagicProjectile {
        return CreeperHeadProjectile(env.castingEntity, env.world, 0.08F * (10 + level), getDamage(env.castingEntity))
    }
    override fun getDamage(entity: LivingEntity?): Float {
        return ironSpell.getSpellPower(level, entity) * 0.5F
    }
}