package com.papack.creeperalert;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;

@Mod(CreeperAlert.MODID)
public class CreeperAlert {
    public static final String MODID = "creeperalert";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Path CR_CONF_DIR = Paths.get(FMLPaths.GAMEDIR.get() + "/config/CreeperAlert");
    public static final Path CR_CONFIG = Paths.get(CR_CONF_DIR + "/creeper_alert.properties");


    public static boolean CREEPER_ALERT;
    public static int DISTANCE;
    public static int VOLUME;
    public static boolean ALERT_OPTION;
    public static int INTERVAL_CORRECTION = 0;


    public CreeperAlert() {

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // sound
        AlertSound.register(eventBus);

        // config
        eventBus.addListener(this::onClientSetup);
    }


    public void onClientSetup(FMLClientSetupEvent event) {
        ConfigFileOperation.existFile();
        ConfigFileOperation.loadFile();
    }
}
