package pub.pigeon.yggdyy.ironcasting.core.cast.render;

import com.mojang.serialization.Codec;
import com.samsthenerd.inline.api.InlineData;
import net.minecraft.resources.ResourceLocation;
import pub.pigeon.yggdyy.ironcasting.IronCasting;

public class IronSpellIconData implements InlineData<IronSpellIconData> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(IronCasting.MODID, "ironsspellicon");
    private final String spellID;
    public IronSpellIconData(String spellID) {
        this.spellID = spellID;
    }
    public String getSpellID() {
        return spellID;
    }
    @Override
    public InlineDataType<IronSpellIconData> getType() {
        return Type.INSTANCE;
    }
    @Override
    public ResourceLocation getRendererId() {
        return IronSpellIconRenderer.ID;
    }
    public static class Type implements InlineDataType<IronSpellIconData> {
        public static final Type INSTANCE = new Type();
        @Override
        public ResourceLocation getId() {
            return ID;
        }
        @Override
        public Codec<IronSpellIconData> getCodec() {
            return Codec.STRING.xmap(IronSpellIconData::new, IronSpellIconData::getSpellID);
        }
    }
}
