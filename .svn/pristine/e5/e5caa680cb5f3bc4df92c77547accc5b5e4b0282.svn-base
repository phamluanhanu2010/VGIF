package vtc.game.app.vcoin.vtcpay.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import me.leolin.shortcutbadger.ShortcutBadger;
import vtc.game.app.vcoin.vtcpay.MainActivity;
import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.common.VtcString;

/**
 * Created by Thuy Chi on 5/17/16.
 */
public class GcmListenerServices2 extends GcmListenerService {

    public GcmListenerServices2() {

    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");

        AppBase.showLog("VGIFT" + " onMessageReceived -------- : " + from + " --------- : " + message);

        //Check Activity is active or not
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager
                .getRunningTasks(Integer.MAX_VALUE);
        boolean isActivityFound = false;

        if (services.get(0).topActivity.getPackageName()
                .equalsIgnoreCase(this.getPackageName())) {
            isActivityFound = true;
        }
        if (!message.equals("") && !message.equals("null")) {
            try {
                JSONObject jsonObject = new JSONObject(message);
                int notiCode = jsonObject.optInt("code");
                String notiMessage = jsonObject.optString("message");
                if (isActivityFound) {
                    //app is running
                    switch (notiCode) {
                        case 9:
                            // push khi trung thuong
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Do something here
                                    int count = AppBase.preferenceUtil(GcmListenerServices2.this).getValueInt(VtcString.KEY_NOTIFICATION) + 1;
                                    AppBase.preferenceUtil(GcmListenerServices2.this).setValueInt(VtcString.KEY_NOTIFICATION, count);
                                    MainActivity.updateNotiCount();
                                }
                            }, 11000);
                            break;
                        case 10:
                            MainActivity.forceLogout();
                            break;

                        case 11:
                            if ((!jsonObject.optString("message").equals("")) && (!jsonObject.optString("message").equals("null")))
                                initShowNotification(2, "", jsonObject.optString("message"));
                            int count2 = AppBase.preferenceUtil(GcmListenerServices2.this).getValueInt(VtcString.KEY_NOTIFICATION) + 1;
                            AppBase.preferenceUtil(GcmListenerServices2.this).setValueInt(VtcString.KEY_NOTIFICATION, count2);
                            MainActivity.updateNotiCount();
                            break;
                    }
                    return;
                } else {
//                    initShowNotification(2, "Vgift", jsonObject.optString("message"));
                    // applicaiton is not open, notification can come
                    switch (notiCode) {
                        case 11:
                            if ((!jsonObject.optString("message").equals("")) && (!jsonObject.optString("message").equals("null")))
                                initShowNotification(2, "", jsonObject.optString("message"));

                            int count2 = AppBase.preferenceUtil(GcmListenerServices2.this).getValueInt(VtcString.KEY_NOTIFICATION) + 1;
                            AppBase.preferenceUtil(GcmListenerServices2.this).setValueInt(VtcString.KEY_NOTIFICATION, count2);
                            ShortcutBadger.applyCount(this, count2); //for 1.1.4+
                            break;
                        case 10:
                            VtcString.ACCESS_TOKEN = null;
                            VtcString.USER_NAME = null;
                            VtcString.ACCOUNT_ID = null;
                            break;
                        case 9:
                            int count = AppBase.preferenceUtil(GcmListenerServices2.this).getValueInt(VtcString.KEY_NOTIFICATION) + 1;
                            AppBase.preferenceUtil(GcmListenerServices2.this).setValueInt(VtcString.KEY_NOTIFICATION, count);
                            ShortcutBadger.applyCount(this, count); //for 1.1.4+
                            break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Check Activity is active or not

    }

    public boolean isRunning(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
                return true;
        }

        return false;
    }

    @Override
    public void onDeletedMessages() {
    }

    @Override
    public void onMessageSent(String msgId) {
        AppBase.showLog("GCM:" + " onMessageSent -------- : " + msgId);
    }

    @Override
    public void onSendError(String msgId, String error) {
    }

    /**
     * Show Big content Notification
     *
     * @param msg -->> Message
     **/
    public void initShowNotification(int type, String title, String msg) {

        // Start wake screen lock
        PowerManager.WakeLock screenLock = ((PowerManager) getSystemService(POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        screenLock.acquire();
        screenLock.release();
        // End wake screen lock

        Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                getApplicationContext()).setAutoCancel(true)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(icon1).setContentText(msg);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(msg);
        bigText.setBigContentTitle(title);
        //bigText.setSummaryText("Por: " + msg);
        mBuilder.setStyle(bigText);
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(getApplicationContext(),
                MainActivity.class);
        //resultIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        resultIntent.putExtra("type", type);
        resultIntent.putExtra("message", msg);

        // The stack builder object will contain an artificial back
        // stack for
        // the
        // started Activity.
        // getApplicationContext() ensures that navigating backward from
        // the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());

        // Adds the back stack for the Intent (but not the Intent
        // itself)
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the
        // stack
        int notiID = new Random().nextInt(1000);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), notiID, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);//
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(notiID, mBuilder.build());
    }
}
