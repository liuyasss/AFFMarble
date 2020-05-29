package com.affmarble;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.StringRes;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AFFSharedPreferences {

    private static final String TAG = "AFFSharedPreferences";

    private static AFFSharedPreferences affSharedPreferencesUtils;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String DEFAULT_SP_NAME = "AFF_SP";
    private static final int DEFAULT_INT = 0;
    private static final float DEFAULT_FLOAT = 0.0f;
    private static final String DEFAULT_STRING = "";
    private static final boolean DEFAULT_BOOLEAN = false;
    private static final Set<String> DEFAULT_STRING_SET = new HashSet<>(0);

    private static String curSPName = DEFAULT_SP_NAME;
    private static Context context;

    private AFFSharedPreferences() {
        this(DEFAULT_SP_NAME);
    }

    private AFFSharedPreferences(String spName) {
        this(AFFOsmanthus.getApp().getApplicationContext(), spName);
    }

    private AFFSharedPreferences(Context context, String spName) {
        AFFSharedPreferences.context = context;
        sharedPreferences = AFFSharedPreferences.context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        curSPName = spName;
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "AFFSharedPreferences: " + curSPName);
        }
    }

    public static void init() {
        if (affSharedPreferencesUtils == null || !curSPName.equals(DEFAULT_SP_NAME)) {
            affSharedPreferencesUtils = new AFFSharedPreferences();
        }
    }

    public static void init(String spName) {
        if (affSharedPreferencesUtils == null) {
            affSharedPreferencesUtils = new AFFSharedPreferences(spName);
        } else if (!spName.equals(curSPName)) {
            affSharedPreferencesUtils = new AFFSharedPreferences(spName);
        }
    }

    public static void init(Context context, String spName) {
        if (affSharedPreferencesUtils == null) {
            affSharedPreferencesUtils = new AFFSharedPreferences(context, spName);
        } else if (!spName.equals(curSPName)) {
            affSharedPreferencesUtils = new AFFSharedPreferences(context, spName);
        }
    }

    public static AFFSharedPreferences get() {
        return affSharedPreferencesUtils;
    }

    /**
     * string.xml 文件支持
     *
     * @param key
     * @param value
     * @return
     */
    public AFFSharedPreferences put(@StringRes int key, Object value) {
        return put(context.getString(key), value);
    }

    public AFFSharedPreferences put(String key, Object value) {
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.apply();
        return affSharedPreferencesUtils;
    }

    public Object get(@StringRes int key, Object defaultObject) {
        return get(context.getString(key), defaultObject);
    }

    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (int) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (long) defaultObject);
        }
        return null;
    }

    public AFFSharedPreferences putInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
        return this;
    }

    public AFFSharedPreferences putInt(@StringRes int key, int value) {
        return putInt(context.getString(key), value);
    }

    public int getInt(@StringRes int key) {
        return getInt(context.getString(key));
    }

    public int getInt(@StringRes int key, int defValue) {
        return getInt(context.getString(key), defValue);
    }

    public int getInt(String key) {
        return getInt(key, DEFAULT_INT);
    }


    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public AFFSharedPreferences putFloat(@StringRes int key, float value) {
        return putFloat(context.getString(key), value);
    }

    public AFFSharedPreferences putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.apply();
        return affSharedPreferencesUtils;
    }

    public float getFloat(String key) {
        return getFloat(key, DEFAULT_FLOAT);
    }

    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public float getFloat(@StringRes int key) {
        return getFloat(context.getString(key));
    }

    public float getFloat(@StringRes int key, float defValue) {
        return getFloat(context.getString(key), defValue);
    }

    public AFFSharedPreferences putLong(@StringRes int key, long value) {
        return putLong(context.getString(key), value);
    }

    public AFFSharedPreferences putLong(String key, long value) {
        editor.putLong(key, value);
        editor.apply();
        return affSharedPreferencesUtils;
    }

    public long getLong(String key) {
        return getLong(key, DEFAULT_INT);
    }

    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public long getLong(@StringRes int key) {
        return getLong(context.getString(key));
    }

    public long getLong(@StringRes int key, long defValue) {
        return getLong(context.getString(key), defValue);
    }

    public AFFSharedPreferences putString(@StringRes int key, String value) {
        return putString(context.getString(key), value);
    }

    public AFFSharedPreferences putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
        return affSharedPreferencesUtils;
    }

    public String getString(String key) {
        return getString(key, DEFAULT_STRING);
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public String getString(@StringRes int key) {
        return getString(context.getString(key), DEFAULT_STRING);
    }

    public String getString(@StringRes int key, String defValue) {
        return getString(context.getString(key), defValue);
    }

    public AFFSharedPreferences putBoolean(@StringRes int key, boolean value) {
        return putBoolean(context.getString(key), value);
    }

    public AFFSharedPreferences putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
        return affSharedPreferencesUtils;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, DEFAULT_BOOLEAN);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public boolean getBoolean(@StringRes int key) {
        return getBoolean(context.getString(key));
    }

    public boolean getBoolean(@StringRes int key, boolean defValue) {
        return getBoolean(context.getString(key), defValue);
    }

    public AFFSharedPreferences putStringSet(@StringRes int key, Set<String> value) {
        return putStringSet(context.getString(key), value);
    }

    public AFFSharedPreferences putStringSet(String key, Set<String> value) {
        editor.putStringSet(key, value);
        editor.apply();
        return affSharedPreferencesUtils;
    }

    public Set<String> getStringSet(String key) {
        return getStringSet(key, DEFAULT_STRING_SET);
    }


    public Set<String> getStringSet(String key, Set<String> defValue) {
        return sharedPreferences.getStringSet(key, defValue);
    }

    public Set<String> getStringSet(@StringRes int key) {
        return getStringSet(context.getString(key));
    }

    public Set<String> getStringSet(@StringRes int key, Set<String> defValue) {
        return getStringSet(context.getString(key), defValue);
    }


    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public boolean contains(@StringRes int key) {
        return contains(context.getString(key));
    }

    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    public AFFSharedPreferences remove(@StringRes int key) {
        return remove(context.getString(key));
    }

    public AFFSharedPreferences remove(String key) {
        editor.remove(key);
        editor.apply();
        return affSharedPreferencesUtils;
    }

    public AFFSharedPreferences clear() {
        editor.clear();
        editor.apply();
        return affSharedPreferencesUtils;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

}
