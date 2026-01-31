package pub.pigeon.yggdyy.ironcasting.core.cast.render;

import com.samsthenerd.inline.api.client.InlineRenderer;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import pub.pigeon.yggdyy.ironcasting.IronCasting;

public class IronSpellIconRenderer implements InlineRenderer<IronSpellIconData> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(IronCasting.MODID, "ironsspellicon");
    public static final IronSpellIconRenderer INSTANCE = new IronSpellIconRenderer();
    @Override
    public ResourceLocation getId() {
        return ID;
    }
    @Override
    public int render(IronSpellIconData ironSpellIconData, GuiGraphics guiGraphics, int index, Style style, int codepoint, TextRenderingContext textRenderingContext) {
        AbstractSpell spell = SpellRegistry.getSpell(ironSpellIconData.getSpellID());
        ResourceLocation textureID = spell.getSpellIconResource();
        guiGraphics.blit(textureID, 0, 0, 0, 0, 8, 8, 8, 8);
        return 12;
    }
    @Override
    public int charWidth(IronSpellIconData ironSpellIconData, Style style, int i) {
        return 12;
    }
}
