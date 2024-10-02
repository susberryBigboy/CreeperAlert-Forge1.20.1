package com.papack.creeperalert;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import static com.papack.creeperalert.CreeperAlert.*;


@OnlyIn(Dist.CLIENT)
public class AlertConfig {

    public static void switching() {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = (LocalPlayer) client.cameraEntity;
        MutableComponent msg;
        long handle = client.getWindow().getWindow();


        if (GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS) {

            DISTANCE = (DISTANCE >= 15 ? 7 : DISTANCE + 1);
            msg = Component.literal("Distance : ").append(String.valueOf(DISTANCE));


        } else if (GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS) {

            VOLUME = (VOLUME >= 10 ? 1 : VOLUME + 1);
            SearchCreeper.playAlarmSound(player);
            msg = Component.literal("Volume : ").append(String.valueOf(VOLUME));


        } else if (GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS) {

            ALERT_OPTION = !ALERT_OPTION;
            msg = Component.literal("Closer Creeper = Shorter Alarm Interval : ").append(String.valueOf(ALERT_OPTION));


        } else {

            CREEPER_ALERT = !CREEPER_ALERT;
            msg = Component.literal("Creeper Alert : ").append(String.valueOf(CREEPER_ALERT));

        }

        if (player != null) {
            player.displayClientMessage(msg.withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GREEN), true);
        }


        ConfigFileOperation.existFile();
        ConfigFileOperation.saveFile();
    }
}
