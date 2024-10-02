package com.papack.creeperalert;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

import static com.papack.creeperalert.CreeperAlert.*;


@OnlyIn(Dist.CLIENT)
public class ConfigFileOperation {

    private static final String PROP_CREEPER_ALERT = "CreeperAlert";
    private static final String CREEPER_ALERT_DEFAULT_VALUE = "true";

    private static final String PROP_VOLUME = "Volume";
    private static final String VOLUME_DEFAULT_VALUE = "3";

    private static final String PROP_DISTANCE = "Distance";
    private static final String DISTANCE_DEFAULT_VALUE = "15";

    private static final String PROP_ALERT_OPTION = "AlertOption";
    private static final String ALERT_OPTION_DEFAULT_VALUE = "true";


    public static void existFile() {
        if (!Files.exists(CR_CONF_DIR)) {
            try {
                Files.createDirectories(CR_CONF_DIR);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }

        if (!Files.exists(CR_CONFIG)) {
            try {
                Files.createFile(CR_CONFIG);
                Properties properties = new Properties();

                properties.setProperty(PROP_CREEPER_ALERT, CREEPER_ALERT_DEFAULT_VALUE);
                properties.setProperty(PROP_VOLUME, VOLUME_DEFAULT_VALUE);
                properties.setProperty(PROP_DISTANCE, DISTANCE_DEFAULT_VALUE);
                properties.setProperty(PROP_ALERT_OPTION, ALERT_OPTION_DEFAULT_VALUE);

                properties.store(new FileOutputStream("" + CR_CONFIG), "");
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }


    public static void loadFile() {
        Properties properties = new Properties();
        String ipPass = CR_CONFIG.toString();
        try {
            InputStream inputStream = new FileInputStream(ipPass);
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        CREEPER_ALERT = Boolean.parseBoolean(properties.getProperty(PROP_CREEPER_ALERT, CREEPER_ALERT_DEFAULT_VALUE));
        VOLUME = Integer.parseInt(properties.getProperty(PROP_VOLUME, VOLUME_DEFAULT_VALUE));
        DISTANCE = Integer.parseInt(properties.getProperty(PROP_DISTANCE, DISTANCE_DEFAULT_VALUE));
        ALERT_OPTION = Boolean.parseBoolean(properties.getProperty(PROP_ALERT_OPTION, ALERT_OPTION_DEFAULT_VALUE));
    }


    public static void saveFile() {
        Properties properties = new Properties();
        try {
            properties.setProperty(PROP_CREEPER_ALERT, String.valueOf(CREEPER_ALERT));
            properties.setProperty(PROP_VOLUME, String.valueOf(VOLUME));
            properties.setProperty(PROP_DISTANCE, String.valueOf(DISTANCE));
            properties.setProperty(PROP_ALERT_OPTION, String.valueOf(ALERT_OPTION));
            properties.store(new FileOutputStream("" + CR_CONFIG), "");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}

