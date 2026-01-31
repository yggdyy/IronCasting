package pub.pigeon.yggdyy.ironcasting.core.cast.capabilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import pub.pigeon.yggdyy.ironcasting.IronCasting;

@Mod.EventBusSubscriber(modid = IronCasting.MODID)
public class IronCastingCap implements IIronCastingCap{
    private double powerArgFactor = 1;
    private int spellCount = 0;
    public IronCastingCap() {

    }
    @Override
    public double getPowerArgFactor() {
        return powerArgFactor;
    }
    @Override
    public void setPowerArgFactor(double newValue) {
        powerArgFactor = newValue;
    }
    @Override
    public int getIronSpellCount() {
        return spellCount;
    }
    @Override
    public void setIronSpellCount(int newValue) {
        spellCount = newValue;
    }
    @SubscribeEvent
    static void onRegister(RegisterCapabilitiesEvent event) {
        event.register(IIronCastingCap.class);
    }
    @SubscribeEvent
    static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof LivingEntity entity && !entity.level().isClientSide) {
            event.addCapability(ResourceLocation.fromNamespaceAndPath(IronCasting.MODID, "iron_casting"), new IronCastingCapProvider());
        }
    }
    @SubscribeEvent
    static void entityTick(LivingEvent.LivingTickEvent event) {
        var entity = event.getEntity();
        if(entity.isAlive()) {
            entity.getCapability(IronCastingCapProvider.CAPABILITY).ifPresent(cap -> {
                cap.setIronSpellCount(0);
            });
        }
    }
}
