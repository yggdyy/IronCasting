package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.fire

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
import io.redspace.ironsspellbooks.entity.spells.firebolt.FireboltProjectile
import net.minecraft.world.entity.LivingEntity
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractProjectileIronSpellAction

class FireboltSpellAction(spell: AbstractSpell, level: Int): AbstractProjectileIronSpellAction(spell, level, true) {
    override fun constructProjectile(env: CastingEnvironment): AbstractMagicProjectile {
        return FireboltProjectile(env.world, env.castingEntity)
    }
    override fun getDamage(entity: LivingEntity?): Float {
        return ironSpell.getSpellPower(level, entity) * 0.5F
    }
}