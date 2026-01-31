package pub.pigeon.yggdyy.ironcasting.core.cast.render;

import com.samsthenerd.inline.api.matching.InlineMatch;
import com.samsthenerd.inline.api.matching.MatcherInfo;
import com.samsthenerd.inline.api.matching.RegexMatcher;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import net.minecraft.resources.ResourceLocation;
import pub.pigeon.yggdyy.ironcasting.IronCasting;

public class IronSpellIconMatcher extends RegexMatcher.Standard {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(IronCasting.MODID, "ironsspellicon");
    public IronSpellIconMatcher() {
        super("IronsSpellIcon", Standard.IDENTIFIER_REGEX_INSENSITIVE, ID, str -> {
            if(!ResourceLocation.isValidResourceLocation(str)) return null;
            var spell = SpellRegistry.getSpell(str);
            if(spell == SpellRegistry.none()) return null;
            return new InlineMatch.DataMatch(new IronSpellIconData(str));
        }, MatcherInfo.fromId(ID));
    }
}
