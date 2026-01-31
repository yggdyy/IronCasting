package pub.pigeon.yggdyy.ironcasting.core.cast.actions

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.wisp.WispEntity
import io.redspace.ironsspellbooks.spells.holy.WispSpell
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.phys.Vec3
import pub.pigeon.yggdyy.ironcasting.core.cast.capabilities.IronCastingCapProvider


//target: LivingEntity, spawnPos: Vec3, powerFactor: Int ->
class WispSpellAction(spell: WispSpell, level: Int) : AbstractLivingEntityVectorIronSpellAction(spell, level){
    private data class Spell(val spell: WispSpell, val level: Int, val target: LivingEntity, val spawnPos: Vec3, val argFactor: Double): RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            updateIronCastCounter(env.castingEntity)
            val wisp: WispEntity = WispEntity(
                env.world,
                env.castingEntity,
                (spell.getSpellPower(level, env.castingEntity) * argFactor).toFloat()
            )
            wisp.target = target
            wisp.setPos(spawnPos)
            env.world.addFreshEntity(wisp)
        }
    }
    override fun getHexSpell(
        spell: AbstractSpell,
        level: Int,
        entity: LivingEntity,
        vec: Vec3,
        argFactor: Double
    ): RenderedSpell {
        return Spell(ironSpell as WispSpell, level, entity, vec, argFactor)
    }
}