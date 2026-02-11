package pub.pigeon.yggdyy.ironcasting.core.cast.render;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.samsthenerd.inline.api.InlineData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import pub.pigeon.yggdyy.ironcasting.IronCasting;

public class IronSpellIconData implements InlineData<IronSpellIconData> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(IronCasting.MODID, "ironsspellicon");
    private final String spellID;
    private final int level;
    public IronSpellIconData(String spellID, int level) {
        this.spellID = spellID;
        this.level = level;
    }
    public String getSpellID() {
        return spellID;
    }
    public int getLevel() {
        return level;
    }
    @Override
    public InlineDataType<IronSpellIconData> getType() {
        return Type.INSTANCE;
    }
    @Override
    public ResourceLocation getRendererId() {
        return IronSpellIconRenderer.ID;
    }
    @Override
    public Style getExtraStyle() {
        //return Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(Items.ACACIA_BOAT.getDefaultInstance())));
        AbstractSpell spell = SpellRegistry.getSpell(spellID);
        if(spell != null) {
            ItemStack stack = new ItemStack(ItemRegistry.SCROLL.get());
            ISpellContainer.createScrollContainer(spell, level, stack);
            return Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(stack)));
        }
        return Style.EMPTY;
    }
    @Override
    public Component asText(boolean withExtra) {
        return Component.literal(String.format("[IronsSpellIcon:%s,%d]", spellID, level)).withStyle(asStyle(withExtra));
    }
    public static class Type implements InlineDataType<IronSpellIconData> {
        public static final Type INSTANCE = new Type();
        @Override
        public ResourceLocation getId() {
            return ID;
        }
        @Override
        public Codec<IronSpellIconData> getCodec() {
            return RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("spell_id").forGetter(IronSpellIconData::getSpellID),
                    Codec.INT.fieldOf("level").forGetter(IronSpellIconData::getLevel)
            ).apply(instance, IronSpellIconData::new));
        }
    }
}
