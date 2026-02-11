package pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.lightning

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import io.redspace.ironsspellbooks.api.spells.AbstractSpell
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile
import io.redspace.ironsspellbooks.entity.spells.lightning_lance.LightningLanceProjectile
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractProjectileIronSpellAction

class LightningLanceSpellAction(spell: AbstractSpell, level: Int): AbstractProjectileIronSpellAction(spell, level, false) {
    override fun constructProjectile(env: CastingEnvironment): AbstractMagicProjectile {
        return LightningLanceProjectile(env.world, env.castingEntity);
    }
}