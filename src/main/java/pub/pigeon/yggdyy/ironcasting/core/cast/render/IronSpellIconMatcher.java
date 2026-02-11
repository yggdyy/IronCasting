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
        super("IronsSpellIcon", "(?:[0-9a-zA-Z._-]+:)?.*", ID, str -> {
            var parse = str.split(",");
            if(parse.length != 2) return null;
            String id = parse[0];
            int level = Integer.parseInt(parse[1]);
            if(!ResourceLocation.isValidResourceLocation(id)) return null;
            var spell = SpellRegistry.getSpell(id);
            if(spell == SpellRegistry.none()) return null;
            return new InlineMatch.DataMatch(new IronSpellIconData(id, level));
        }, MatcherInfo.fromId(ID));
    }
}
