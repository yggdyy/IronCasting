package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.nature

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
import io.redspace.ironsspellbooks.entity.spells.poison_arrow.PoisonArrow
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractProjectileIronSpellAction

class PoisonArrowSpellAction(spell: AbstractSpell, level: Int): AbstractProjectileIronSpellAction(spell, level, false) {
    override fun constructProjectile(env: CastingEnvironment): AbstractMagicProjectile {
        return PoisonArrow(env.world, env.castingEntity)
    }
    override fun customProjectileModify(env: CastingEnvironment, projectile: AbstractMagicProjectile) {
        super.customProjectileModify(env, projectile)
        (projectile as PoisonArrow).aoeDamage = ironSpell.getSpellPower(level, env.castingEntity) * 0.185F
    }
}