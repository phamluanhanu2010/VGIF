package vtc.game.app.vcoin.vtcpay.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ThuyChi on 7/7/2016.
 */
public class PreferenceUtil {
    private SharedPreferences IShare = null;

    public PreferenceUtil(Context context) {
        if (context != null)
            IShare = context.getSharedPreferences(context.getApplicationInfo().packageName, Context.MODE_PRIVATE);
    }

    public void setValueString(String key, String val) {
        IShare.edit().putString(key, val).commit();
    }
    public String getValueString(String key) {
        return IShare.getString(key, "").trim().toString();
    }

    public void setValueInt(String key, int val) {
        IShare.edit().putInt(key, val).commit();
    }
    public int getValueInt(String key) {
        return IShare.getInt(key, 0);
    }
}
