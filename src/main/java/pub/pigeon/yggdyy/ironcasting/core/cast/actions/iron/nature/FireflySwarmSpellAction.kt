package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.nature

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.firefly_swarm.FireflySwarmProjectile
import io.redspace.ironsspellbooks.spells.nature.FireflySwarmSpell
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.phys.Vec3
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractLivingEntityVectorIronSpellAction

class FireflySwarmSpellAction(spell: FireflySwarmSpell, level: Int): AbstractLivingEntityVectorIronSpellAction(spell, level) {
    private data class Spell(val spell: FireflySwarmSpell, val level: Int, val entity: LivingEntity, val vec: Vec3, val argFactor: Double): RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            updateIronCastCounter(env.castingEntity)
            val firefly: FireflySwarmProjectile = FireflySwarmProjectile(env.world, env.castingEntity, entity,
                (spell.getSpellPower(level, env.castingEntity) / 3.0F * argFactor).toFloat()
            )
            firefly.setPos(vec)
            env.world.addFreshEntity(firefly)
        }
    }
    override fun getHexSpell(
        spell: AbstractSpell,
        level: Int,
        entity: LivingEntity,
        vec: Vec3,
        argFactor: Double
    ): RenderedSpell {
        return Spell(spell as FireflySwarmSpell, level, entity, vec, argFactor)
    }
}