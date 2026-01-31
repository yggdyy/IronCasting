package pub.pigeon.yggdyy.ironcasting.core.cast.actions

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDoubleBetween
import at.petrak.hexcasting.api.casting.getPositiveDoubleUnderInclusive
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadCaster
import at.petrak.hexcasting.api.casting.mishaps.MishapDisallowedSpell
import at.petrak.hexcasting.api.casting.mishaps.MishapInternalException
import at.petrak.hexcasting.api.misc.MediaConstants
import io.redspace.ironsspellbooks.api.magic.MagicData
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.api.spells.CastSource
import io.redspace.ironsspellbooks.api.spells.CastType
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import pub.pigeon.yggdyy.ironcasting.Config
import pub.pigeon.yggdyy.ironcasting.core.cast.capabilities.IronCastingCapProvider
import kotlin.collections.*

class SimpleIronSpellAction(ironSpell: AbstractSpell, level: Int) : AbstractIronSpellAction(ironSpell, level) {
    override val argc: Int
        get() = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        checkCastingEntity(env.castingEntity)
        if(MagicData.getPlayerMagicData(env.castingEntity).castDurationRemaining > 0) {
            throw MishapDisallowedSpell("irons_spell_cooldown")
        }
        val argFactor: Double = args.getDoubleBetween(0, 1.0, 10.0, argc)
        val mana2MediaFactor: Long = MediaConstants.DUST_UNIT
        val countFactor = env.castingEntity!!.getCapability(IronCastingCapProvider.CAPABILITY).map { cap -> cap.ironSpellCount + 1 }.orElse(1)
        val cost: Long = (ironSpell.getManaCost(level) * argFactor * argFactor * mana2MediaFactor * countFactor * Config.MANA_TO_MEDIA_FACTOR.get()).toLong()
        return SpellAction.Result(Spell(ironSpell, level, argFactor), cost, listOf())
    }
    private data class Spell(val ironSpell: AbstractSpell, val level: Int, val argFactor: Double): RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            ironSpell.onServerPreCast(env.world, level, env.castingEntity, MagicData.getPlayerMagicData(env.castingEntity))
            (env.castingEntity)?.getCapability(IronCastingCapProvider.CAPABILITY)
                ?.ifPresent { cap ->
                    cap.powerArgFactor = argFactor; cap.ironSpellCount = cap.ironSpellCount + 1
                }
            if (env.castingEntity is ServerPlayer) {
                ironSpell.castSpell(env.world, level, env.castingEntity as ServerPlayer, CastSource.NONE, false);
            } else {
                ironSpell.onCast(
                    env.world,
                    level,
                    env.castingEntity,
                    CastSource.NONE,
                    MagicData.getPlayerMagicData(env.castingEntity)
                )
            }
            (env.castingEntity)?.getCapability(IronCastingCapProvider.CAPABILITY)
                ?.ifPresent { cap -> cap.powerArgFactor = 1.0 }
        }
    }
}