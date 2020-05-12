package com.affmarble;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

public class AFFToast {

    private static Toast toast;

    public static void show(String content) {
        createToast(content, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void show(@StringRes int res) {
        String content = AFFOsmanthus.getApp().getResources().getString(res);
        show(content);
    }

    public static void showLong(String content) {
        createToast(content, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showLong(@StringRes int res) {
        String content = AFFOsmanthus.getApp().getResources().getString(res);
        showLong(content);
    }

    private static void createToast(String content, int duration) {
        if (toast != null)
            toast.cancel();

        toast = make();

        setContent(content);
        toast.setDuration(duration);
    }

    private static Toast make() {
        toast = new Toast(AFFOsmanthus.getApp());
        LayoutInflater inflate = (LayoutInflater)
                AFFOsmanthus.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.layout_toast, null);
        toast.setView(view);
        return toast;
    }

    private static TextView findTextView() {
        return toast.getView().findViewById(R.id.tv_content);
    }

    private static void setContent(String content) {
        findTextView().setText(content);
    }
}
