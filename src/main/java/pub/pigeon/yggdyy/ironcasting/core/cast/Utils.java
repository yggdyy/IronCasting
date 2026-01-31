package pub.pigeon.yggdyy.ironcasting.core.cast;

import at.petrak.hexcasting.api.casting.math.HexAngle;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static HexPattern getPatternForIronSpell(AbstractSpell spell, int level) {
        String id = spell.getSpellId();
        List<HexAngle> stroke = new ArrayList<>(List.of());
        for(int i = 0; i < id.length(); ++i) {
            int dirIdx = Math.abs(id.charAt(i) - 'a') % 6;
            stroke.add(HexAngle.values()[dirIdx]);
        }
        //stroke.add(HexAngle.values()[level % 6]);
        int tmp = level;
        while(tmp > 0) {
            stroke.add(HexAngle.values()[tmp % 6]);
            tmp = tmp / 6;
        }
        return new HexPattern(HexDir.EAST, stroke);
    }
}
