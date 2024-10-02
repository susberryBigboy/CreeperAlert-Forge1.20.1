package com.papack.creeperalert;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.papack.creeperalert.CreeperAlert.CREEPER_ALERT;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = CreeperAlert.MODID)
public class EventPlayerTick {

    @SubscribeEvent
    //public static void onPlayerTickEvent(PlayerTickEvent.Post event) {
    public static void onPlayerTickEvent(TickEvent.ClientTickEvent event) {

        if (event.phase == TickEvent.Phase.END) {

            Minecraft client = Minecraft.getInstance();

            if (client.screen == null && client.player != null) {
                while (KeyMapping.keyMapping.consumeClick()) {
                    AlertConfig.switching();
                }

                if (!client.player.isSpectator() && !client.player.isCreative()) {

                    if (CREEPER_ALERT && TickCounter.tickCounter()) {
                        SearchCreeper.checkAndPlaySound();
                    }
                }
            }
        }
    }
}
