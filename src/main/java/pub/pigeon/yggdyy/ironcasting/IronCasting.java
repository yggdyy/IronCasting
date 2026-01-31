package pub.pigeon.yggdyy.ironcasting;

import com.mojang.logging.LogUtils;
import com.samsthenerd.inline.api.client.InlineClientAPI;
import com.samsthenerd.inline.api.matching.RegexMatcher;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import pub.pigeon.yggdyy.ironcasting.core.cast.ActionRegister;
import pub.pigeon.yggdyy.ironcasting.core.cast.render.IronSpellIconMatcher;
import pub.pigeon.yggdyy.ironcasting.core.cast.render.IronSpellIconRenderer;

@Mod(IronCasting.MODID)
public class IronCasting
{
    public static final String MODID = "ironcasting";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public IronCasting(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ActionRegister.register();
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            InlineClientAPI.INSTANCE.addMatcher(new IronSpellIconMatcher());
            InlineClientAPI.INSTANCE.addRenderer(IronSpellIconRenderer.INSTANCE);
        }
    }
}
