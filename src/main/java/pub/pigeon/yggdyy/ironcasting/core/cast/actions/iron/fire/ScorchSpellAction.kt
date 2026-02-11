package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.fire

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.api.util.Utils
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager
import io.redspace.ironsspellbooks.damage.DamageSources
import io.redspace.ironsspellbooks.damage.SpellDamageSource
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireField
import io.redspace.ironsspellbooks.registries.SoundRegistry
import io.redspace.ironsspellbooks.spells.fire.ScorchSpell
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractVectorIronSpellAction
import java.util.function.Consumer
import java.util.function.Predicate

class ScorchSpellAction(spell: ScorchSpell, level: Int): AbstractVectorIronSpellAction(spell, level) {
    private data class Spell(val spell: ScorchSpell, val level: Int, val vec3: Vec3, val argFactor: Double): RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            updateIronCastCounter(env.castingEntity)
            MagicManager.spawnParticles(
                env.world,
                ParticleTypes.LAVA,
                vec3.x,
                vec3.y,
                vec3.z,
                25,
                1.0,
                1.0,
                1.0,
                1.0,
                true
            )
            MagicManager.spawnParticles(
                env.world,
                ParticleTypes.LAVA,
                vec3.x,
                vec3.y + 1.0,
                vec3.z,
                25,
                0.25,
                1.5,
                0.25,
                1.0,
                false
            )
            env.world.playSound(
                null as Player?,
                vec3.x,
                vec3.y,
                vec3.z,
                SoundRegistry.FIERY_EXPLOSION.get() as SoundEvent,
                SoundSource.PLAYERS,
                2.0f,
                Utils.random.nextIntBetweenInclusive(8, 12).toFloat() * 0.1f
            )
            val radius: Float = 2.5F
            val radiusSqr = radius * radius
            val damage: Float = (spell.getSpellPower(level, env.castingEntity) * argFactor).toFloat()
            val source: SpellDamageSource = spell.getDamageSource(env.castingEntity)
            env.world.getEntitiesOfClass<LivingEntity>(
                LivingEntity::class.java,
                AABB(
                    vec3.subtract(radius.toDouble(), radius.toDouble(), radius.toDouble()),
                    vec3.add(radius.toDouble(), radius.toDouble(), radius.toDouble())
                ),
                Predicate<LivingEntity> { livingEntity: LivingEntity ->
                    livingEntity !== env.castingEntity && (env.castingEntity!!.x - vec3.x) * (env.castingEntity!!.x - vec3.x) + (env.castingEntity!!.z - vec3.z) * (env.castingEntity!!.z - vec3.z) < radiusSqr && livingEntity.isPickable && !DamageSources.isFriendlyFireBetween(
                        livingEntity,
                        env.castingEntity
                    ) && Utils.hasLineOfSight(
                        env.world,
                        vec3.add(0.0, 1.5, 0.0),
                        livingEntity.boundingBox.center,
                        true
                    )
                }).forEach(
                Consumer<LivingEntity> { livingEntity: LivingEntity? ->
                    DamageSources.applyDamage(livingEntity, damage, source)
                    DamageSources.ignoreNextKnockback(livingEntity)
                })
            val fire = FireField(env.world)
            fire.owner = env.castingEntity
            fire.duration = 200
            fire.damage = damage * 0.1f
            fire.radius = radius
            fire.setCircular()
            fire.moveTo(vec3)
            env.world.addFreshEntity(fire)
        }
    }
    override fun getHexSpell(spell: AbstractSpell, level: Int, vec3: Vec3, argFactor: Double): RenderedSpell {
        return Spell(spell as ScorchSpell, level, vec3, argFactor)
    }
}