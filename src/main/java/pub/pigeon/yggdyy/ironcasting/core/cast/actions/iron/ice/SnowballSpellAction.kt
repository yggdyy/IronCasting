package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.ice

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
import io.redspace.ironsspellbooks.entity.spells.snowball.Snowball
import net.minecraft.world.entity.LivingEntity
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractProjectileIronSpellAction
import kotlin.math.sqrt

class SnowballSpellAction(spell: AbstractSpell, level: Int): AbstractProjectileIronSpellAction(spell, level, false) {
    override fun constructProjectile(env: CastingEnvironment): AbstractMagicProjectile {
        return Snowball(env.world, env.castingEntity)
    }
    override fun getDamage(entity: LivingEntity?): Float {
        return 200F * sqrt(ironSpell.getEntityPowerMultiplier(entity))
    }
    override fun getRadius(entity: LivingEntity?): Float {
        return 3.5F + 0.5F * level
    }
}