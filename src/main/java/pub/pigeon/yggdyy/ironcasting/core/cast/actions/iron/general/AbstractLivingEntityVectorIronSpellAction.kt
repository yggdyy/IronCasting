package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDoubleBetween
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadEntity
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation
import at.petrak.hexcasting.api.casting.mishaps.MishapDisallowedSpell
import at.petrak.hexcasting.api.misc.MediaConstants
import io.redspace.ironsspellbooks.api.magic.MagicData
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.phys.Vec3
import pub.pigeon.yggdyy.ironcasting.Config
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractIronSpellAction
import pub.pigeon.yggdyy.ironcasting.core.cast.capabilities.IronCastingCapProvider

abstract class AbstractLivingEntityVectorIronSpellAction(spell: AbstractSpell, level: Int): AbstractIronSpellAction(spell, level) {
    override val argc: Int
        get() = 3

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        checkCastingEntity(env.castingEntity)
        if(MagicData.getPlayerMagicData(env.castingEntity).castDurationRemaining > 0) {
            throw MishapDisallowedSpell("irons_spell_cooldown")
        }
        val _target: Entity = args.getEntity(0, argc)
        if(_target !is LivingEntity) {
            throw MishapBadEntity(_target, Component.literal("LivingEntity"))
        }
        val target: LivingEntity = _target
        if(!env.isVecInRange(target.position())) {
            throw MishapBadLocation(target.position())
        }
        val spawnPos: Vec3 = args.getVec3(1, argc)
        if(!env.isVecInRange(spawnPos)) {
            throw MishapBadLocation(spawnPos)
        }
        val argFactor: Double = args.getDoubleBetween(2, 1.0, 10.0, argc)
        val cost: Long = (ironSpell.getManaCost(level) * MediaConstants.DUST_UNIT * Config.MANA_TO_MEDIA_FACTOR.get() * argFactor * argFactor * env.castingEntity!!.getCapability(IronCastingCapProvider.CAPABILITY).map { cap -> cap.ironSpellCount + 1 }.orElse(1)).toLong()
        return SpellAction.Result(getHexSpell(ironSpell, level, target, spawnPos, argFactor), cost, listOf())
    }
    abstract fun getHexSpell(spell: AbstractSpell, level: Int, entity: LivingEntity, vec: Vec3, argFactor: Double): RenderedSpell
}