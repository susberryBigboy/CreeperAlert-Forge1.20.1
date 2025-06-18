package com.papack.creeperalert;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@OnlyIn(Dist.CLIENT)
public class AlertSound {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CreeperAlert.MODID);

    @SuppressWarnings("removal")
    public static final RegistryObject<SoundEvent> CREEPER_ALERT_SOUND =
            SOUND_EVENTS.register("alert_sound",
                    () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreeperAlert.MODID, "alert_sound")));

    // Get it at run time and keep it (avoid lazy retrieval via Holder)
    public static SoundEvent CACHED_SOUND_EVENT = null;

    public static void register(IEventBus eventBus) {
        CreeperAlert.LOGGER.info("Registering sound events to Forge event bus");
        SOUND_EVENTS.register(eventBus);

        // Set `CACHED_SOUND_EVENT` after registering with lambda
        eventBus.addListener((FMLClientSetupEvent e) -> {
            CACHED_SOUND_EVENT = CREEPER_ALERT_SOUND.get();     // Cache
            CreeperAlert.LOGGER.info("Cached sound: {}", CACHED_SOUND_EVENT);
        });
    }
}