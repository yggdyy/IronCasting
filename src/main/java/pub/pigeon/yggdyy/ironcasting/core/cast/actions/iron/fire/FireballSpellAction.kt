package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.fire

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
import io.redspace.ironsspellbooks.entity.spells.fireball.MagicFireball
import net.minecraft.world.entity.LivingEntity
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractProjectileIronSpellAction

class FireballSpellAction(spell: AbstractSpell, level: Int): AbstractProjectileIronSpellAction(spell, level, true) {
    override fun constructProjectile(env: CastingEnvironment): AbstractMagicProjectile {
        return MagicFireball(env.world, env.castingEntity)
    }
    override fun getDamage(entity: LivingEntity?): Float {
        return 5F + 5F * ironSpell.getSpellPower(level, entity)
    }
    override fun getRadius(entity: LivingEntity?): Float {
        return 2 + ironSpell.getSpellPower(level, entity)
    }
}