package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.fire

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireBomb
import net.minecraft.world.entity.LivingEntity
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractProjectileIronSpellAction

class MagmaBombSpellAction(spell: AbstractSpell, level: Int): AbstractProjectileIronSpellAction(spell, level, false) {
    override fun constructProjectile(env: CastingEnvironment): AbstractMagicProjectile {
        return FireBomb(env.world, env.castingEntity)
    }
    override fun getDamage(entity: LivingEntity?): Float {
        return 8 * ironSpell.getEntityPowerMultiplier(entity)
    }
    override fun getRadius(entity: LivingEntity?): Float {
        return 3F + ironSpell.getEntityPowerMultiplier(entity)
    }
    override fun customProjectileModify(env: CastingEnvironment, projectile: AbstractMagicProjectile) {
        super.customProjectileModify(env, projectile)
        (projectile as FireBomb).aoeDamage = 1F + ironSpell.getSpellPower(level, env.castingEntity) * 0.1F
    }
}