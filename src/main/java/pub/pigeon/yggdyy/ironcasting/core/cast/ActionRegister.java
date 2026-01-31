package pub.pigeon.yggdyy.ironcasting.core.cast;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.spells.fire.ScorchSpell;
import io.redspace.ironsspellbooks.spells.holy.WispSpell;
import io.redspace.ironsspellbooks.spells.nature.FireflySwarmSpell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import pub.pigeon.yggdyy.ironcasting.core.cast.actions.*;
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
    private static void applyHandlers() {
        handlers.add(scorchHandler);
        handlers.add(fireflySwarmHandler);
        handlers.add(wispHandler);
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
