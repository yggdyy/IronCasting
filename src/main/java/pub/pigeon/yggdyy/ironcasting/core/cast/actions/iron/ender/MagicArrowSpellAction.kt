package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.ender

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
import io.redspace.ironsspellbooks.entity.spells.magic_arrow.MagicArrowProjectile
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractProjectileIronSpellAction

class MagicArrowSpellAction(spell: AbstractSpell, level: Int): AbstractProjectileIronSpellAction(spell, level, true) {
    override fun constructProjectile(env: CastingEnvironment): AbstractMagicProjectile {
        return MagicArrowProjectile(env.world, env.castingEntity)
    }
}