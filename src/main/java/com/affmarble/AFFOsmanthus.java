package com.affmarble;

import android.app.Application;

/**
 * aff "桂花初成" 初始化
 */
public class AFFOsmanthus {

    private static Application app;

    /**
     * 初始化
     * <p>
     * 不会有额外的内存消耗
     *
     * @param application 为工具类提供一个 application
     */
    public static void mature(Application application) {
        app = application;
    }

    public static Application getApp() {
        return app;
    }
}
