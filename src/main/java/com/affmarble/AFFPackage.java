package com.affmarble;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;

public class AFFPackage {


    private static final String TAG = AFFPackage.class.getSimpleName();

    private AFFPackage() {
        throw new UnsupportedOperationException(AFFConstant.UNSUPPORTED_OPERATION_EXCEPTION_TIP);
    }

    /**
     * install a app
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     * <p>
     * 需要自行注册 FileProvider
     *
     * @param context
     * @param file    the file
     */
    public static void installApp(Context context, File file) {
        if (!isFileExists(file)) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "installApp: the install file not exist");
            }
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    // other method

    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

}
