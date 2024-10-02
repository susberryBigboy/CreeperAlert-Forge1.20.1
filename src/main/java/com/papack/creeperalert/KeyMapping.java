package com.papack.creeperalert;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = CreeperAlert.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyMapping {

    private static final String KEY_CATEGORY_CREEPER_ALERT = "key.category.creeper_alert";
    private static final String KEY_DESC = "key.desc.creeper_alert";

    public static net.minecraft.client.KeyMapping keyMapping = new net.minecraft.client.KeyMapping(
            KEY_DESC,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_J,
            KEY_CATEGORY_CREEPER_ALERT
    );

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {event.register(keyMapping);}
}
