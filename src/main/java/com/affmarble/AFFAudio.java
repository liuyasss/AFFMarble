package com.affmarble;

import android.content.Context;
import android.media.AudioManager;

public class AFFAudio {

    /**
     * 增大 媒体 音量
     */
    public static void raiseVolume() {
        AudioManager audioManager = (AudioManager) AFFOsmanthus.getApp().getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_RAISE,
                    AudioManager.FLAG_SHOW_UI);
        }
    }

    /**
     * 降低 媒体 音量
     */
    public static void lowerVolume() {
        AudioManager audioManager = (AudioManager) AFFOsmanthus.getApp().getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_LOWER,
                    AudioManager.FLAG_SHOW_UI);
        }
    }

}
