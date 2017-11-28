package vtc.game.app.vcoin.vtcpay.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;

import vtc.game.app.vcoin.vtcpay.MainActivity;
import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;
import vtc.game.app.vcoin.vtcpay.utils.PreferenceUtil;
import vtc.game.app.vcoin.vtcpay.view.account.FMProfile;

/**
 * Created by ThuyChi on 9/16/2016.
 */
public class AppBase {
    private static FragmentActivity mActivity;
    private static PreferenceUtil preferenceUtil;
    private static AlertDialog alertDialog;

    public static FragmentActivity getActivity() {
        return mActivity;
    }

    public static void setActivity(FragmentActivity mActivity) {
        AppBase.mActivity = mActivity;
    }

    /**
     * Call Fragment
     *
     * @param key            the key for fragment on class FragmentFactory.class
     * @param bundle         put data using bundle
     * @param isAddBackStack add back stack yes or no
     */
    public static void callNewFragment(int key, Bundle bundle, boolean isAddBackStack) {
        Const.UI_CURRENT_CONTEXT = key;
        System.gc();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment fragment = FragmentFactory.getFragmentByKey(key);
        if (fragment != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.add(R.id.drawer_Wrap_Layout, fragment);

        if (isAddBackStack) {
            if (fragment != null) {
                fragmentTransaction.addToBackStack(String.valueOf(fragment.getClass().getClass()));
            }
        }

        fragmentTransaction.commit();
    }

    /**
     * Call Fragment
     *
     * @param key            the key for fragment on class FragmentFactory.class
     * @param bundle         put data using bundle
     * @param isAddBackStack add back stack yes or no
     * @param callback       return data
     */
    public static void callNewFragmentCallBack(int key, Bundle bundle, boolean isAddBackStack, Callback callback) {
        Const.UI_CURRENT_CONTEXT = key;
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment fragment = FragmentFactory.getFragmentCallBackByKey(key, callback);
        if (fragment != null) {
            fragment.setArguments(bundle);
        }
//        fragmentTransaction.add(R.id.lout_Main_Container, fragment);
        fragmentTransaction.add(R.id.drawer_Wrap_Layout, fragment);

        if (isAddBackStack) {
            if (fragment != null) {
                fragmentTransaction.addToBackStack(String.valueOf(fragment.getClass().getClass()));
            }
        }

        fragmentTransaction.commit();
    }

    public static void initBack() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public static void initClearPopback() {
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void showLog(String msg) {
//        Log.i("Luanpv - Vg", "----------------- : " + msg);
    }

    /**
     * Check internet connected or not
     *
     * @param context context of class
     * @return boolean true or false
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }

    /**
     * Call check service running or not
     *
     * @param context context of class
     * @return true or false
     */
    public static boolean checkPlayServices(final Context context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                //apiAvailability.getErrorDialog(context, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
                AppBase.showLog("checkPlayServices" + "resultCode" + String.valueOf(resultCode));
            } else {
                //AppCore.showLog(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    public static PreferenceUtil preferenceUtil(Context context) {
        if (preferenceUtil == null) {
            preferenceUtil = new PreferenceUtil(context);
        }
        return preferenceUtil;
    }

    public static void setImageWithCornerLocal(int resouseId, final ImageView img) {
        Glide.with(getActivity()).load(resouseId).asBitmap().centerCrop().into(new BitmapImageViewTarget(img) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(AppBase.getActivity().getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
                circularBitmapDrawable.setCornerRadius(getActivity().getResources().getInteger(R.integer.size_Image_Corner_Radius));

                img.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void setImageWithCorner(String url, final ImageView img) {
        Glide.with(getActivity()).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(img) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(AppBase.getActivity().getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
                circularBitmapDrawable.setCornerRadius(getActivity().getResources().getInteger(R.integer.size_Image_Corner_Radius));
                img.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void setImageWithCornerItemSpinning(String url, final ImageView img) {
        Glide.with(getActivity()).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(img) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(AppBase.getActivity().getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
                circularBitmapDrawable.setCornerRadius(getActivity().getResources().getInteger(R.integer.size_Image_Corner_Radius_Item_Spinning));
                img.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void setImageCircular(String url, final ImageView img) {
        Glide.with(getActivity()).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(img) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(AppBase.getActivity().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
//                circularBitmapDrawable.setCornerRadius(Const.IMAGE_CORNER_RADIUS);

                img.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void setImageWithUrl(String url, final ImageView img) {
        Glide.with(getActivity()).load(url).asBitmap().placeholder(R.drawable.ic_placeholder).placeholder(R.drawable.ic_placeholder).centerCrop().error(R.drawable.ic_placeholder).into(new BitmapImageViewTarget(img) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(AppBase.getActivity().getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                circularBitmapDrawable.setCornerRadius(Const.IMAGE_CORNER_RADIUS);
                img.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static String getSizeName(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return "large";
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                return "xlarge";
            default:
                return "undefined";
        }
    }

    public static String getDeviceResolution(Context mContext) {
        int density = mContext.getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_MEDIUM:
                return "MDPI";
            case DisplayMetrics.DENSITY_HIGH:
                return "HDPI";
            case DisplayMetrics.DENSITY_LOW:
                return "LDPI";
            case DisplayMetrics.DENSITY_XHIGH:
                return "XHDPI";
            case DisplayMetrics.DENSITY_TV:
                return "TV";
            case DisplayMetrics.DENSITY_XXHIGH:
                return "XXHDPI";
            case DisplayMetrics.DENSITY_XXXHIGH:
                return "XXXHDPI";
            default:
                return "Unknown";
        }
    }

    public static void startWebView(final Context mContext, WebView webView, String url) {
        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(mContext);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();

                }
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                AppBase.showLog("URL: " + url);
                AppBase.showLog("Titile: " + view.getTitle());
                String tittile = view.getTitle();
                if (tittile != null && !tittile.isEmpty()) {
                    // doSomething
                    // handleCallBack(tittile);
                }
            }
        });

        // Javascript inabled on webview
        //webView.getSettings().setJavaScriptEnabled(true);

        //Load url in webview
        webView.loadUrl(url);
    }

    public static Boolean checkAppExisting(String packageName, Context mContext) {
//        try {
//            getActivity().getPackageManager().getPackageInfo(pacekageName, 0);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    public static String checkStringNull(String str) {
        if ((str.equals("null")) || str.equals(null)) {
            str = "";
        }
        return str;
    }

    public static String longToDateTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        String dateString = dateFormat.format(new Date(time));
        return dateString;
    }

    public static String initCheckString(JSONObject jsonObject, String param) {
        String data = "";
        if (jsonObject.optString(param) != null && !jsonObject.optString(param).equals("") && !jsonObject.optString(param).equals("null")) {
            data = jsonObject.optString(param);
        }
        return data;
    }

    public static void initHideKeyboard(View view, Context context) {
        InputMethodManager keyboard = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (keyboard != null) {
            keyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public static long dateTimeToLong(String strDate) {
        long timeStamp = -1;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = null;
            date = dateFormat.parse(strDate);
            timeStamp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }


    public static void initToolsHeaderBar(final Activity context, final boolean isTrue) {
        if (context == null) {
            return;
        }

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Window window = context.getWindow();

                    // clear FLAG_TRANSLUCENT_STATUS flag:
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                    // finally change the color
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (isTrue) {
                            window.setStatusBarColor(context.getResources().getColor(R.color.color_Blue_Light));
                        } else {
                            window.setStatusBarColor(context.getResources().getColor(android.R.color.transparent));
                        }
                    }
                }
            }
        });
    }

    /**
     * Convert dp to Pixels
     */
    public static float convertDpToPixels(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    /**
     * Convert Pixel to dp
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

    public static void initShowMessageAlert(final Context context, final int type, String btnText, String msg) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dl_show_msg, null);

        final Button btnMain = (Button) dialoglayout.findViewById(R.id.btn_Main);

        switch (type) {
            case Const.ALERT_DONE:
                btnMain.setBackgroundResource(R.drawable.cus_button_green);
                btnMain.setTextColor(context.getResources().getColor(R.color.color_White));
                break;
            case Const.ALERT_CLOSE:
                btnMain.setBackgroundResource(R.drawable.cus_button_grey_dark);
                btnMain.setTextColor(context.getResources().getColor(R.color.color_Black));
                break;
            case Const.ALERT_HOME:
                btnMain.setBackgroundResource(R.drawable.cus_button_green);
                btnMain.setTextColor(context.getResources().getColor(R.color.color_White));
                break;
        }

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                if (type == Const.ALERT_CLOSE) {

                } else if (type == Const.ALERT_HOME) {
                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Intent i = new Intent(ConstParam.BRODCAST_RECEIVER_HOME);
                    context.sendBroadcast(i);
                } else if (type == Const.ALERT_DONE) {
                    AppBase.initClearPopback();

                } else if (type == Const.ALERT_UPDATE_VCOIN){
                    AppBase.initClearPopback();
                    MainActivity.initUpdateVcoinProfile();
                }

            }
        });

        TextView tvMsg = (TextView) dialoglayout.findViewById(R.id.tv_Msg_Content);
        tvMsg.setText(msg);

        btnMain.setText(btnText);

        alertDialog = new AlertDialog.Builder(context).setView(dialoglayout).setCancelable(false).show();
    }

    public static String splitByLength(String s, int subSize) {
        String str = "";
        List<String> strings = new ArrayList<>();
        int indexs = s.length();
        while (indexs >= subSize) {
            strings.add(s.substring(indexs - subSize, indexs));
            indexs -= subSize;
            if ((indexs < subSize) && (indexs > 0)) {
                strings.add(s.substring(0, indexs));
            }
        }
        if (strings.size() > 0) {
            for (int i = strings.size() - 1; i >= 0; i--) {
                if (i == strings.size() - 1) {
                    str = strings.get(i);
                } else {
                    str = str + "." + strings.get(i);
                }
            }
        } else {
            str = s;
        }
        return str;
    }

    //Get IP
    public static String getLocalIpAddress(Context context) throws SocketException {
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();
            String wifiIpAddress = String.format("%d.%d.%d.%d",
                    (ip & 0xff),
                    (ip >> 8 & 0xff),
                    (ip >> 16 & 0xff),
                    (ip >> 24 & 0xff));

            return wifiIpAddress;
        }

        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
            NetworkInterface intf = en.nextElement();
            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                InetAddress inetAddress = enumIpAddr.nextElement();
                //Log.i("","111 inetAddress.getHostAddress(): "+inetAddress.getHostAddress());
                //the condition after && is missing in your snippet, checking instance of inetAddress
                if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                    // Log.i("","111 return inetAddress.getHostAddress(): "+inetAddress.getHostAddress());
                    return inetAddress.getHostAddress();
                }

            }
        }

        return null;
    }


}
