package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.blood

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
import io.redspace.ironsspellbooks.entity.spells.WitherSkullProjectile
import net.minecraft.world.entity.LivingEntity
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractProjectileIronSpellAction

class WitherSkullSpellAction(spell: AbstractSpell, level: Int): AbstractProjectileIronSpellAction(spell, level, true) {
    override fun constructProjectile(env: CastingEnvironment): AbstractMagicProjectile {
        return WitherSkullProjectile(env.castingEntity, env.world, 0.08F * (6 + level), getDamage(env.castingEntity))
    }
    override fun getDamage(entity: LivingEntity?): Float {
        return ironSpell.getSpellPower(level, entity) * 0.5F
    }
    override fun getRadius(entity: LivingEntity?): Float {
        return 2F
    }
}