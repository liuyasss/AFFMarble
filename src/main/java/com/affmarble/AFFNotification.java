package com.affmarble;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.DrawableRes;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AFFNotification {

    /**
     * 发送一个 Notification
     *
     * @param context
     * @param notification 建议使用 NotificationCompat.Builder 的 build() 构造
     * @param tag
     * @param id
     * @param channelId
     * @param channelName
     */
    public static void sendNotification(Context context, Notification notification, String tag, int id, String channelId, String channelName) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }
        notificationManagerCompat.notify(tag, id, notification);
    }

    public static void sendNotification(Context context, Notification notification, int id, String channelId, String channelName) {
        sendNotification(context, notification, null, id, channelId, channelName);
    }

    /**
     * 创建一个 简单的通知栏消息
     *
     * @param context
     * @param channelId
     * @param title     通知栏标题
     * @param content   通栏问题内容
     * @param largeIcon 大 icon
     * @param smallIcon 小 icon
     * @return
     */
    public static Notification createSimpleNotification(Context context, String channelId, String title, String content, @DrawableRes int largeIcon, @DrawableRes int smallIcon) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIcon));
        builder.setSmallIcon(smallIcon);
        return builder.build();
    }


}
