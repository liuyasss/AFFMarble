package com.affmarble;

import android.content.res.Configuration;
import android.os.Build;

import java.io.File;

public class AFFDevice {

    private AFFDevice() {
        throw new UnsupportedOperationException(AFFConstant.UNSUPPORTED_OPERATION_EXCEPTION_TIP);
    }

    /**
     * Return whether device is rooted.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/",
                "/system/sbin/", "/usr/bin/", "/vendor/bin/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }

    public static int getSDKVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Return the model of device.
     * <p>e.g. MI2SC</p>
     *
     * @return the model of device
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * Return whether device is tablet.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isTablet() {
        return (AFFOsmanthus.getApp().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
