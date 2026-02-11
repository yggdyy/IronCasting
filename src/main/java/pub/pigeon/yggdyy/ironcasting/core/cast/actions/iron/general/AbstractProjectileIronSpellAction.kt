package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDoubleBetween
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation
import at.petrak.hexcasting.api.casting.mishaps.MishapDisallowedSpell
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.common.lib.HexSounds
import io.redspace.ironsspellbooks.api.magic.MagicData
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.phys.Vec3
import pub.pigeon.yggdyy.ironcasting.Config
import kotlin.math.max

abstract class AbstractProjectileIronSpellAction(spell: AbstractSpell, level: Int, val noGravity: Boolean): AbstractIronSpellAction(spell, level) {
    override val argc: Int
        get() = 3
    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        checkCastingEntity(env.castingEntity)
        if(MagicData.getPlayerMagicData(env.castingEntity).castDurationRemaining > 0) {
            throw MishapDisallowedSpell("irons_spell_cooldown")
        }
        val origin: Vec3 = args.getVec3(0, argc)
        val dir: Vec3 = args.getVec3(1, argc)
        val factor: Double = args.getDoubleBetween(2, 1.0, 10.0, argc)
        if(!env.isVecInRange(origin)) {
            throw MishapBadLocation(origin)
        }
        applyArgFactor(env.castingEntity, factor)
        val projectile: AbstractMagicProjectile = constructProjectile(env)
        projectile.damage = getDamage(env.castingEntity)
        projectile.explosionRadius = getRadius(env.castingEntity)
        projectile.isNoGravity = noGravity
        projectile.setPos(origin)
        projectile.shoot(dir)
        customProjectileModify(env, projectile)
        applyArgFactor(env.castingEntity, 1.0)
        val cost: Long = (MediaConstants.DUST_UNIT * Config.MANA_TO_MEDIA_FACTOR.get() * getCounter(env.castingEntity!!) * ironSpell.getManaCost(level) * max(dir.length(), 1.0) * factor * factor).toLong()
        return SpellAction.Result(Spell(ironSpell, projectile), cost, listOf())
    }
    open fun getDamage(entity: LivingEntity?): Float {
        return ironSpell.getSpellPower(level, entity);
    }
    open fun getRadius(entity: LivingEntity?): Float {
        return 0F;
    }
    open fun customProjectileModify(env: CastingEnvironment, projectile: AbstractMagicProjectile) {

    }
    abstract fun constructProjectile(env: CastingEnvironment): AbstractMagicProjectile
    private data class Spell(val spell: AbstractSpell, val projectile: AbstractMagicProjectile): RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            updateIronCastCounter(env.castingEntity)
            env.world.addFreshEntity(projectile)
            env.world.playSound(null, BlockPos.containing(projectile.position()), spell.castFinishSound.orElse(HexSounds.CAST_SPELL), env.castingEntity!!.soundSource)
        }
    }
}