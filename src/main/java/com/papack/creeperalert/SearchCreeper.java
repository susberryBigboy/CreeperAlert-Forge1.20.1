package com.papack.creeperalert;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;

import static com.papack.creeperalert.CreeperAlert.*;

@OnlyIn(Dist.CLIENT)
public class SearchCreeper {

    private static final double FAR = 32;

    public static void checkAndPlaySound() {
        Minecraft client = Minecraft.getInstance();
        ClientLevel world = client.level;
        LocalPlayer clientPlayer = (LocalPlayer) client.cameraEntity;


        if (world != null && clientPlayer != null) {

            double nearestDistance = FAR;
            double entityDistance;

            for (Entity entity : world.entitiesForRendering()) {
                if (entity instanceof Creeper creeper) {

                    entityDistance = willTheyFindMe(creeper, clientPlayer);

                    if (nearestDistance > entityDistance) {
                        nearestDistance = entityDistance;
                    }
                }
            }

            if (nearestDistance <= DISTANCE) {

                playAlarmSound(clientPlayer);

                INTERVAL_CORRECTION = 0;        // 2s : 40 tick (default)

                if (11.8d >= nearestDistance) {
                    INTERVAL_CORRECTION = 20;   // 1s : 20 tick
                }
                if (7.8d >= nearestDistance) {
                    INTERVAL_CORRECTION = 30;   // 0.5s : 10 tick
                }
            }
        }
    }

    public static void playAlarmSound(LocalPlayer clientPlayer) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            if (AlertSound.CACHED_SOUND_EVENT != null && clientPlayer != null) {
                clientPlayer.clientLevel.playLocalSound(
                        clientPlayer.getX(), clientPlayer.getY(), clientPlayer.getZ(),
                        AlertSound.CACHED_SOUND_EVENT,
                        SoundSource.PLAYERS,
                        (VOLUME * 0.1f),
                        1.0f,
                        false
                );
            } else {
                CreeperAlert.LOGGER.warn("Sound not cached yet or clientPlayer is null");
            }
        }
    }

    private static double willTheyFindMe(Creeper creeper, LocalPlayer clientPlayer) {

        Vec3 creeperEyePos = creeper.getEyePosition();
        Vec3 creeperCenter = creeper.getBoundingBox().getCenter();

        Vec3 playerPos = clientPlayer.position();
        Vec3 playerEyePos = clientPlayer.getEyePosition();
        Vec3 playerCenter = clientPlayer.getBoundingBox().getCenter();
        Level world = creeper.level();

        if (playerCenter.distanceTo(creeperCenter) > DISTANCE) {
            return FAR;

        } else {
            double distance = FAR;
            boolean canSeePlayerPos = canYouSeeMe(world, creeperEyePos, playerPos, creeper);
            boolean canSeePlayerEyePos = canYouSeeMe(world, creeperEyePos, playerEyePos, creeper);

            if (canSeePlayerPos || canSeePlayerEyePos) {
                distance = playerPos.distanceTo(creeperCenter);
            }
            return distance;
        }
    }


    private static boolean canYouSeeMe(Level world, Vec3 startPos, Vec3 endPos, Entity entity) {
        return world.clip(new ClipContext(
                        startPos,
                        endPos,
                        ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE,
                        entity)).
                getType() == HitResult.Type.MISS;
    }
}
