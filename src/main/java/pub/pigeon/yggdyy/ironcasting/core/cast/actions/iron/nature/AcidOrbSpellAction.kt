package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.nature

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
import io.redspace.ironsspellbooks.entity.spells.acid_orb.AcidOrb
import net.minecraft.world.entity.LivingEntity
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractProjectileIronSpellAction

class AcidOrbSpellAction(spell: AbstractSpell, level: Int): AbstractProjectileIronSpellAction(spell, level, false) {
    override fun constructProjectile(env: CastingEnvironment): AbstractMagicProjectile {
        return AcidOrb(env.world, env.castingEntity)
    }
    override fun getRadius(entity: LivingEntity?): Float {
        return ironSpell.getSpellPower(level, entity) * 3F
    }
    override fun customProjectileModify(env: CastingEnvironment, projectile: AbstractMagicProjectile) {
        super.customProjectileModify(env, projectile)
        (projectile as AcidOrb).rendLevel = level + 2
        projectile.rendDuration = (ironSpell.getSpellPower(level, env.castingEntity) * 400F).toInt()
    }
}