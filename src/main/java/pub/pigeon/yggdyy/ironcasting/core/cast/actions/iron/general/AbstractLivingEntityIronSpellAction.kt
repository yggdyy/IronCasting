package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDoubleBetween
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadEntity
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation
import at.petrak.hexcasting.api.misc.MediaConstants
import io.redspace.ironsspellbooks.api.magic.MagicData
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.api.spells.CastSource
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData
import io.redspace.ironsspellbooks.spells.TargetedTargetAreaCastData
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import pub.pigeon.yggdyy.ironcasting.Config
import pub.pigeon.yggdyy.ironcasting.core.cast.capabilities.IronCastingCapProvider

abstract class AbstractLivingEntityIronSpellAction(spell: AbstractSpell, level: Int): AbstractIronSpellAction(spell, level) {
    override val argc: Int
        get() = 2
    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        checkCastingEntity(env.castingEntity)
        val entity: Entity = args.getEntity(0, argc)
        if(entity !is LivingEntity) {
            throw MishapBadEntity(entity, Component.literal("LivingEntity"))
        }
        if(!env.isVecInRange(entity.position())) {
            throw MishapBadLocation(entity.position())
        }
        val argFactor = args.getDoubleBetween(1, 1.0, 10.0, argc)
        val cost: Long = (ironSpell.getManaCost(level) * MediaConstants.DUST_UNIT * Config.MANA_TO_MEDIA_FACTOR.get() * argFactor * argFactor * env.castingEntity!!.getCapability(
            IronCastingCapProvider.CAPABILITY).map { cap -> cap.ironSpellCount + 1 }.orElse(1)).toLong()
        return SpellAction.Result(getHexSpell(ironSpell, level, entity, argFactor), cost, listOf())
    }
    abstract fun getHexSpell(spell: AbstractSpell, level: Int, entity: LivingEntity, argFactor: Double): RenderedSpell
    companion object {
        class Simple(spell: AbstractSpell, level: Int): AbstractLivingEntityIronSpellAction(spell, level) {
            private data class Spell(val spell: AbstractSpell, val level: Int, val entity: LivingEntity, val argFactor: Double): RenderedSpell {
                override fun cast(env: CastingEnvironment) {
                    updateIronCastCounter(env.castingEntity)
                    MagicData.getPlayerMagicData(env.castingEntity).additionalCastData = TargetEntityCastData(entity)
                    applyArgFactor(env.castingEntity, argFactor)
                    if(env.castingEntity is ServerPlayer) {
                        spell.castSpell(env.world, level, env.castingEntity as ServerPlayer, CastSource.NONE, false)
                        //spell.onCast(env.world, level, env.castingEntity, CastSource.NONE, MagicData.getPlayerMagicData(env.castingEntity))
                    } else {
                        spell.onCast(env.world, level, env.castingEntity, CastSource.NONE, MagicData.getPlayerMagicData(env.castingEntity))
                    }
                    applyArgFactor(env.castingEntity, 1.0)
                    MagicData.getPlayerMagicData(env.castingEntity).resetAdditionalCastData()
                }
            }
            override fun getHexSpell(
                spell: AbstractSpell,
                level: Int,
                entity: LivingEntity,
                argFactor: Double
            ): RenderedSpell {
                return Spell(spell, level, entity, argFactor)
            }
        }
        class Varied(spell: AbstractSpell, level: Int): AbstractLivingEntityIronSpellAction(spell, level) {
            private data class Spell(val spell: AbstractSpell, val level: Int, val entity: LivingEntity, val argFactor: Double): RenderedSpell {
                override fun cast(env: CastingEnvironment) {
                    updateIronCastCounter(env.castingEntity)
                    MagicData.getPlayerMagicData(env.castingEntity).additionalCastData = TargetedTargetAreaCastData(entity, null)
                    env.castingEntity?.getCapability(IronCastingCapProvider.CAPABILITY)?.ifPresent { cap -> cap.powerArgFactor = argFactor }
                    if(env.castingEntity is ServerPlayer) {
                        spell.castSpell(env.world, level, env.castingEntity as ServerPlayer, CastSource.NONE, false)
                        //spell.onCast(env.world, level, env.castingEntity, CastSource.NONE, MagicData.getPlayerMagicData(env.castingEntity))
                    } else {
                        spell.onCast(env.world, level, env.castingEntity, CastSource.NONE, MagicData.getPlayerMagicData(env.castingEntity))
                    }
                    env.castingEntity?.getCapability(IronCastingCapProvider.CAPABILITY)?.ifPresent { cap -> cap.powerArgFactor = 1.0 }
                    MagicData.getPlayerMagicData(env.castingEntity).resetAdditionalCastData()
                }
            }
            override fun getHexSpell(
                spell: AbstractSpell,
                level: Int,
                entity: LivingEntity,
                argFactor: Double
            ): RenderedSpell {
                return Spell(spell, level, entity, argFactor)
            }
        }
    }
}