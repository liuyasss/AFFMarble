package com.affmarble;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.PermissionChecker;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class AFFPermission {

    private static final List<String> PERMISSIONS = getPermissions();

    private static AFFPermission instance;

    private OnRationaleListener onRationaleListener;
    private SimpleCallback simpleCallback;
    private FullCallback fullCallback;
    private ThemeCallback themeCallback;
    private Set<String> permissions;
    private List<String> permissionsRequest;
    private List<String> permissionsGranted;
    private List<String> permissionsDenied;
    private List<String> permissionsDeniedForever;

    private static SimpleCallback simpleCallback4WriteSettings;
    private static SimpleCallback simpleCallback4DrawOverlays;

    /**
     * Return the permissions used in application.
     *
     * @return the permissions used in application
     */
    public static List<String> getPermissions() {
        return getPermissions(AFFOsmanthus.getApp().getPackageName());
    }

    /**
     * Return the permissions used in application.
     *
     * @param packageName The name of the package.
     * @return the permissions used in application
     */
    public static List<String> getPermissions(final String packageName) {
        PackageManager pm = AFFOsmanthus.getApp().getPackageManager();
        try {
            String[] permissions = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
            if (permissions == null) return Collections.emptyList();
            return Arrays.asList(permissions);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Return whether <em>you</em> have been granted the permissions.
     *
     * @param permissions The permissions.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isGranted(final String... permissions) {
        for (String permission : permissions) {
            if (!isGranted(permission)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isGranted(final String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                PermissionChecker.PERMISSION_GRANTED ==
                        PermissionChecker.checkSelfPermission(AFFOsmanthus.getApp(), permission);
    }

    /**
     * Return whether the app can modify system settings.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isGrantedWriteSettings() {
        return Settings.System.canWrite(AFFOsmanthus.getApp());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestWriteSettings(final SimpleCallback callback) {
        if (isGrantedWriteSettings()) {
            if (callback != null) callback.onGranted();
            return;
        }
        simpleCallback4WriteSettings = callback;
        PermissionActivity.start(AFFOsmanthus.getApp(), PermissionActivity.TYPE_WRITE_SETTINGS);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void startWriteSettingsActivity(final Activity activity, final int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + AFFOsmanthus.getApp().getPackageName()));
        if (!isIntentAvailable(intent)) {
            launchAppDetailsSettings();
            return;
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * Return whether the app can draw on top of other apps.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isGrantedDrawOverlays() {
        return Settings.canDrawOverlays(AFFOsmanthus.getApp());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestDrawOverlays(final SimpleCallback callback) {
        if (isGrantedDrawOverlays()) {
            if (callback != null) callback.onGranted();
            return;
        }
        simpleCallback4DrawOverlays = callback;
        PermissionActivity.start(AFFOsmanthus.getApp(), PermissionActivity.TYPE_DRAW_OVERLAYS);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void startOverlayPermissionActivity(final Activity activity, final int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + AFFOsmanthus.getApp().getPackageName()));
        if (!isIntentAvailable(intent)) {
            launchAppDetailsSettings();
            return;
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * Launch the application's details settings.
     */
    public static void launchAppDetailsSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + AFFOsmanthus.getApp().getPackageName()));
        if (!isIntentAvailable(intent)) return;
        AFFOsmanthus.getApp().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * Set the permissions.
     *
     * @param permissions The permissions.
     * @return the single {@link AFFPermission} instance
     */
    public static AFFPermission of(@AFFPermissionConstant.Permission final String... permissions) {
        return new AFFPermission(permissions);
    }

    private static boolean isIntentAvailable(final Intent intent) {
        return AFFOsmanthus.getApp()
                .getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0;
    }

    private AFFPermission(final String... permissions) {
        this.permissions = new LinkedHashSet<>();
        for (String permission : permissions) {
            for (String aPermission : AFFPermissionConstant.getPermissions(permission)) {
                if (PERMISSIONS.contains(aPermission)) {
                    this.permissions.add(aPermission);
                }
            }
        }
        instance = this;
    }

    /**
     * Set rationale listener.
     *
     * @param listener The rationale listener.
     * @return the single {@link AFFPermission} instance
     */
    public AFFPermission rationale(final OnRationaleListener listener) {
        onRationaleListener = listener;
        return this;
    }

    /**
     * Set the simple call back.
     *
     * @param callback the simple call back
     * @return the single {@link AFFPermission} instance
     */
    public AFFPermission callback(final SimpleCallback callback) {
        simpleCallback = callback;
        return this;
    }

    /**
     * Set the full call back.
     *
     * @param callback the full call back
     * @return the single {@link AFFPermission} instance
     */
    public AFFPermission callback(final FullCallback callback) {
        fullCallback = callback;
        return this;
    }

    /**
     * Set the theme callback.
     *
     * @param callback The theme callback.
     * @return the single {@link AFFPermission} instance
     */
    public AFFPermission theme(final ThemeCallback callback) {
        themeCallback = callback;
        return this;
    }

    /**
     * Start request.
     */
    public void request() {
        permissionsGranted = new ArrayList<>();
        permissionsRequest = new ArrayList<>();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionsGranted.addAll(permissions);
            requestCallback();
        } else {
            for (String permission : permissions) {
                if (isGranted(permission)) {
                    permissionsGranted.add(permission);
                } else {
                    permissionsRequest.add(permission);
                }
            }
            if (permissionsRequest.isEmpty()) {
                requestCallback();
            } else {
                startPermissionActivity();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startPermissionActivity() {
        permissionsDenied = new ArrayList<>();
        permissionsDeniedForever = new ArrayList<>();
        PermissionActivity.start(AFFOsmanthus.getApp(), PermissionActivity.TYPE_RUNTIME);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean rationale(final Activity activity) {
        boolean isRationale = false;
        if (onRationaleListener != null) {
            for (String permission : permissionsRequest) {
                if (activity.shouldShowRequestPermissionRationale(permission)) {
                    getPermissionsStatus(activity);
                    onRationaleListener.rationale(new OnRationaleListener.ShouldRequest() {
                        @Override
                        public void again(boolean again) {
                            activity.finish();
                            if (again) {
                                startPermissionActivity();
                            } else {
                                requestCallback();
                            }
                        }
                    });
                    isRationale = true;
                    break;
                }
            }
            onRationaleListener = null;
        }
        return isRationale;
    }

    private void getPermissionsStatus(final Activity activity) {
        for (String permission : permissionsRequest) {
            if (isGranted(permission)) {
                permissionsGranted.add(permission);
            } else {
                permissionsDenied.add(permission);
                if (!activity.shouldShowRequestPermissionRationale(permission)) {
                    permissionsDeniedForever.add(permission);
                }
            }
        }
    }

    private void requestCallback() {
        if (simpleCallback != null) {
            if (permissionsRequest.size() == 0
                    || permissions.size() == permissionsGranted.size()) {
                simpleCallback.onGranted();
            }

            if (!permissionsDenied.isEmpty()) {
                simpleCallback.onDenied();
            }

            simpleCallback = null;
        }
        if (fullCallback != null) {
            if (permissionsRequest.size() == 0
                    || permissionsGranted.size() > 0) {
                fullCallback.onGranted(permissionsGranted);
            }

            if (!permissionsDenied.isEmpty()) {
                fullCallback.onDenied(permissionsDeniedForever, permissionsDenied);
            }

            fullCallback = null;
        }
        onRationaleListener = null;
        themeCallback = null;
    }

    private void onRequestPermissionsResult(final Activity activity) {
        getPermissionsStatus(activity);
        requestCallback();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static class PermissionActivity extends Activity {

        private static final String TYPE = "TYPE";
        public static final int TYPE_RUNTIME = 0x01;
        public static final int TYPE_WRITE_SETTINGS = 0x02;
        public static final int TYPE_DRAW_OVERLAYS = 0x03;

        public static void start(final Context context, int type) {
            Intent starter = new Intent(context, PermissionActivity.class);
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            starter.putExtra(TYPE, type);
            context.startActivity(starter);
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
            int byteExtra = getIntent().getIntExtra(TYPE, TYPE_RUNTIME);
            if (byteExtra == TYPE_RUNTIME) {
                if (instance == null) {
                    super.onCreate(savedInstanceState);
                    Log.e("AFFPermission", "request permissions failed");
                    finish();
                    return;
                }
                if (instance.themeCallback != null) {
                    instance.themeCallback.onActivityCreate(this);
                }
                super.onCreate(savedInstanceState);

                if (instance.rationale(this)) {
                    return;
                }
                if (instance.permissionsRequest != null) {
                    int size = instance.permissionsRequest.size();
                    if (size <= 0) {
                        finish();
                        return;
                    }
                    requestPermissions(instance.permissionsRequest.toArray(new String[size]), 1);
                }
            } else if (byteExtra == TYPE_WRITE_SETTINGS) {
                super.onCreate(savedInstanceState);
                startWriteSettingsActivity(this, TYPE_WRITE_SETTINGS);
            } else if (byteExtra == TYPE_DRAW_OVERLAYS) {
                super.onCreate(savedInstanceState);
                startOverlayPermissionActivity(this, TYPE_DRAW_OVERLAYS);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode,
                                               @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            if (instance != null && instance.permissionsRequest != null) {
                instance.onRequestPermissionsResult(this);
            }
            finish();
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            finish();
            return true;
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == TYPE_WRITE_SETTINGS) {
                if (simpleCallback4WriteSettings == null) return;
                if (isGrantedWriteSettings()) {
                    simpleCallback4WriteSettings.onGranted();
                } else {
                    simpleCallback4WriteSettings.onDenied();
                }
                simpleCallback4WriteSettings = null;
            } else if (requestCode == TYPE_DRAW_OVERLAYS) {
                if (simpleCallback4DrawOverlays == null) return;
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isGrantedDrawOverlays()) {
                            simpleCallback4DrawOverlays.onGranted();
                        } else {
                            simpleCallback4DrawOverlays.onDenied();
                        }
                        simpleCallback4DrawOverlays = null;
                    }
                }, 100);
            }
            finish();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    public interface OnRationaleListener {

        void rationale(ShouldRequest shouldRequest);

        interface ShouldRequest {
            void again(boolean again);
        }
    }

    public interface SimpleCallback {
        void onGranted();

        void onDenied();
    }

    public interface FullCallback {
        void onGranted(List<String> permissionsGranted);

        void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied);
    }

    public interface ThemeCallback {
        void onActivityCreate(Activity activity);
    }
}


