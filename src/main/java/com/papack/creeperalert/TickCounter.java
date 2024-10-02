package com.papack.creeperalert;


import static com.papack.creeperalert.CreeperAlert.ALERT_OPTION;
import static com.papack.creeperalert.CreeperAlert.INTERVAL_CORRECTION;

public class TickCounter {

    private static final int MAX_COUNT = 39;
    private static final int RESET = 0;
    private static int COUNTER = RESET;


    public static boolean tickCounter() {

        COUNTER++;

        if (COUNTER > (ALERT_OPTION ? MAX_COUNT - INTERVAL_CORRECTION : MAX_COUNT)) {
            COUNTER = RESET;
            return true;
        }
        return false;
    }
}
