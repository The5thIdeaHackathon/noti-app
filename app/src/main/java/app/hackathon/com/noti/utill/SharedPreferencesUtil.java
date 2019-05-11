package app.hackathon.com.noti.utill;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedPreferencesUtil {

    private static final String TAG = SharedPreferencesUtil.class.getSimpleName();

    private static SharedPreferencesUtil mInstance;
    private static Context mContext;
    private static SharedPreferences sp;

    public static final String PREF_NAME = "pref";
    public static final String PREF_RECENT_BLE_ADDR = "recentAddress";
    public static final String PREF_USER_ACCESS_TOKEN = "accessToken";
    public static final String PREF_KEYWORD = "keyword";
    public static final String PREF_FLAG = "flag";

    public static SharedPreferencesUtil getInstance() {
        if (mInstance == null)
            mInstance = new SharedPreferencesUtil();
        //else if (mContext == null)
            //throw new ContextIsNullException("Context is null. Please call initialize() first.");
        else if (sp == null)
            throw new IllegalStateException("SharedPreferences instance is invalid. Please recall initialize()");

        return mInstance;
    }

    public static void initialize(Context context) {
        initialize(context, PREF_NAME);
    }

    public static void initialize(Context context, String s) {
        getInstance();
        mContext = context;
        sp = mContext.getSharedPreferences(s, Context.MODE_PRIVATE);
    }

    public static boolean isInitialized(){
        return sp != null;
    }

    public void set(String key, Object value) {
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (float) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);
        }

        editor.apply();
    }

    public String getString(String key) {
        return sp.getString(key, null);
    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public float getFloat(String key) {
        return sp.getFloat(key, 0f);
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        return sp.getBoolean(key, def);
    }

    public long getLong(String key) {
        return sp.getLong(key, 0l);
    }

    public Set<String> getStringSet(String key) {
        return sp.getStringSet(key, null);
    }

    public void remove(String key){
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clear(){
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public void setFlag(String s) {
        set(PREF_FLAG, s);
    }

    public String getFlag() {
        return getString(PREF_FLAG);
    }
}
