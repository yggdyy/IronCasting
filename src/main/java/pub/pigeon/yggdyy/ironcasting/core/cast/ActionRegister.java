package pub.pigeon.yggdyy.ironcasting.core.cast;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.spells.blood.WitherSkullSpell;
import io.redspace.ironsspellbooks.spells.ender.MagicArrowSpell;
import io.redspace.ironsspellbooks.spells.ender.MagicMissileSpell;
import io.redspace.ironsspellbooks.spells.evocation.LobCreeperSpell;
import io.redspace.ironsspellbooks.spells.fire.*;
import io.redspace.ironsspellbooks.spells.holy.GuidingBoltSpell;
import io.redspace.ironsspellbooks.spells.holy.WispSpell;
import io.redspace.ironsspellbooks.spells.ice.IcicleSpell;
import io.redspace.ironsspellbooks.spells.ice.SnowballSpell;
import io.redspace.ironsspellbooks.spells.lightning.BallLightningSpell;
import io.redspace.ironsspellbooks.spells.lightning.LightningLanceSpell;
import io.redspace.ironsspellbooks.spells.nature.AcidOrbSpell;
import io.redspace.ironsspellbooks.spells.nature.FireflySwarmSpell;
import io.redspace.ironsspellbooks.spells.nature.PoisonArrowSpell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.blood.WitherSkullSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.ender.MagicArrowSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.ender.MagicMissileSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.evocation.LobCreeperSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.fire.*;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractIronSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.AbstractLivingEntityIronSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.ContinuousIronSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.general.SimpleIronSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.holy.GuidingBoltSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.holy.WispSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.ice.IcicleSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.ice.SnowballSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.lightning.BallLightningSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.lightning.LightningLanceSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.nature.AcidOrbSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.nature.FireflySwarmSpellAction;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.iron.nature.PoisonArrowSpellAction;
import pub.pigeon.yggdyy.ironcasting.mixins.SpellRegistryAccessor;

import java.util.ArrayList;
import java.util.List;

public class ActionRegister {
    private static final List<IIronSpellRegisterHandler> handlers = new ArrayList<>(List.of());
    private static final IIronSpellRegisterHandler simpleHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return !spell.getCastType().equals(CastType.CONTINUOUS);
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new SimpleIronSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler continuousHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell.getCastType().equals(CastType.CONTINUOUS);
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new ContinuousIronSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler wispHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof WispSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new WispSpellAction((WispSpell) spell, level);
        }
    };
    private static final IIronSpellRegisterHandler fireflySwarmHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof FireflySwarmSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new FireflySwarmSpellAction((FireflySwarmSpell) spell, level);
        }
    };
    private static final IIronSpellRegisterHandler scorchHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof ScorchSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new ScorchSpellAction((ScorchSpell) spell, level);
        }
    };
    private static final IIronSpellRegisterHandler targetEntityCastDataAssociatedHandler = new IIronSpellRegisterHandler() {
        private static final List<String> spellIDs = List.of(
                "irons_spellbooks:acupuncture",
                "irons_spellbooks:blessing_of_life",
                "irons_spellbooks:blight",
                "irons_spellbooks:chain_lightning",
                "irons_spellbooks:devour",
                "irons_spellbooks:root",
                "irons_spellbooks:sacrifice",
                "irons_spellbooks:wololo"
        );
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spellIDs.contains(spell.getSpellId());
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new AbstractLivingEntityIronSpellAction.Companion.Simple(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler targetedTargetAreaCastDataAssociatedHandler = new IIronSpellRegisterHandler() {
        private static final List<String> spellIDs = List.of(
                "irons_spellbooks:slow",
                "irons_spellbooks:haste"
        );
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spellIDs.contains(spell.getSpellId());
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new AbstractLivingEntityIronSpellAction.Companion.Varied(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler acidOrbHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof AcidOrbSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new AcidOrbSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler poisonArrowHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof PoisonArrowSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new PoisonArrowSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler lightningLanceHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof LightningLanceSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new LightningLanceSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler ballLightningHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof BallLightningSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new BallLightningSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler icicleHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof IcicleSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new IcicleSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler snowballHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof SnowballSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new SnowballSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler witherSkullHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof WitherSkullSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new WitherSkullSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler guidingBoltHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof GuidingBoltSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new GuidingBoltSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler magicMissileHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof MagicMissileSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new MagicMissileSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler magicArrowHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof MagicArrowSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new MagicArrowSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler fireboltHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof FireboltSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new FireboltSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler fireballHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof FireballSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new FireballSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler magmaBombHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof MagmaBombSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new MagmaBombSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler fireArrowHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof FireArrowSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new FireArrowSpellAction(spell, level);
        }
    };
    private static final IIronSpellRegisterHandler lobCreeperHandler = new IIronSpellRegisterHandler() {
        @Override
        public boolean canApply(AbstractSpell spell, int level) {
            return spell instanceof LobCreeperSpell;
        }
        @Override
        public AbstractIronSpellAction getAction(AbstractSpell spell, int level) {
            return new LobCreeperSpellAction(spell, level);
        }
    };
    private static void applyHandlers() {
        handlers.add(acidOrbHandler);
        handlers.add(ballLightningHandler);
        handlers.add(fireArrowHandler);
        handlers.add(fireballHandler);
        handlers.add(fireboltHandler);
        handlers.add(fireflySwarmHandler);
        handlers.add(guidingBoltHandler);
        handlers.add(icicleHandler);
        handlers.add(lightningLanceHandler);
        handlers.add(lobCreeperHandler);
        handlers.add(magicArrowHandler);
        handlers.add(magicMissileHandler);
        handlers.add(magmaBombHandler);
        handlers.add(poisonArrowHandler);
        handlers.add(scorchHandler);
        handlers.add(snowballHandler);
        handlers.add(wispHandler);
        handlers.add(witherSkullHandler);

        handlers.add(targetedTargetAreaCastDataAssociatedHandler);
        handlers.add(targetEntityCastDataAssociatedHandler);
        handlers.add(continuousHandler);
        handlers.add(simpleHandler);
    }
    public static void register() {
        applyHandlers();
        var spells = SpellRegistryAccessor.getSPELLS();
        spells.getEntries().stream().forEach(entry -> {
            var spell = entry.get();
            var namespace = spell.getSpellResource().getNamespace();
            var path = spell.getSpellResource().getPath();
            int maxLevel = Math.max(10, spell.getMaxLevel());
            for(int level = spell.getMinLevel(); level <= maxLevel; ++level) {
                for (IIronSpellRegisterHandler handler : handlers) {
                    if (handler.canApply(spell, level)) {
                        Registry.register(HexActions.REGISTRY, ResourceLocation.fromNamespaceAndPath(namespace, path + level), new ActionRegistryEntry(Utils.getPatternForIronSpell(spell, level), handler.getAction(spell, level)));
                        break;
                    }
                }
            }
        });
    }

    private interface IIronSpellRegisterHandler {
        boolean canApply(AbstractSpell spell, int level);
        AbstractIronSpellAction getAction(AbstractSpell spell, int level);
    }
}
