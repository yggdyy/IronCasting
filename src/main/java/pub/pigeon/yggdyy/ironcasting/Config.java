package pub.pigeon.yggdyy.ironcasting;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = IronCasting.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.DoubleValue MANA_TO_MEDIA_FACTOR = BUILDER.comment("baseMediaCost(dust unit) = spellManaCost * thisValue").defineInRange("mana_to_media_factor", 1.0, 0, 1000);
    public static final ForgeConfigSpec.DoubleValue POWER_FACTOR = BUILDER.comment("spellStrength = strengthCalculateByIron * patternArg * thisValue").defineInRange("power_factor", 1.0, 0, 1000);
    static final ForgeConfigSpec SPEC = BUILDER.build();
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {

    }
}
