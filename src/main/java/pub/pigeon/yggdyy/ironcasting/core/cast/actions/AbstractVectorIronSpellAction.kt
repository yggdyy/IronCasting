package pub.pigeon.yggdyy.ironcasting.core.cast.actions

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDoubleBetween
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadCaster
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation
import at.petrak.hexcasting.api.misc.MediaConstants
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import net.minecraft.world.phys.Vec3
import pub.pigeon.yggdyy.ironcasting.Config
import pub.pigeon.yggdyy.ironcasting.core.cast.capabilities.IronCastingCapProvider

abstract class AbstractVectorIronSpellAction(spell: AbstractSpell, level: Int): AbstractIronSpellAction(spell, level) {
    override val argc: Int
        get() = 2
    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        checkCastingEntity(env.castingEntity)
        val vec3: Vec3 = args.getVec3(0, argc)
        if(!env.isVecInRange(vec3)) {
            throw MishapBadLocation(vec3)
        }
        val argFactor = args.getDoubleBetween(1, 1.0, 10.0, argc)
        val cost: Long = (ironSpell.getManaCost(level) * MediaConstants.DUST_UNIT * Config.MANA_TO_MEDIA_FACTOR.get() * argFactor * argFactor * env.castingEntity!!.getCapability(
            IronCastingCapProvider.CAPABILITY).map { cap -> cap.ironSpellCount + 1 }.orElse(1)).toLong()
        return SpellAction.Result(getHexSpell(ironSpell, level, vec3, argFactor), cost, listOf())
    }
    abstract fun getHexSpell(spell: AbstractSpell, level: Int, vec3: Vec3, argFactor: Double): RenderedSpell
}