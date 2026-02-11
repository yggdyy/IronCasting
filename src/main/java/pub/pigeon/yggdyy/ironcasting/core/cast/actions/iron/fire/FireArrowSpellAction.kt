package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.fire

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
import io.redspace.ironsspellbooks.entity.spells.fire_arrow.FireArrowProjectile
import net.minecraft.world.entity.LivingEntity
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractProjectileIronSpellAction

class FireArrowSpellAction(spell: AbstractSpell, level: Int): AbstractProjectileIronSpellAction(spell, level, true) {
    override fun constructProjectile(env: CastingEnvironment): AbstractMagicProjectile {
        return FireArrowProjectile(env.world, env.castingEntity)
    }
    override fun getRadius(entity: LivingEntity?): Float {
        return 3F
    }
}