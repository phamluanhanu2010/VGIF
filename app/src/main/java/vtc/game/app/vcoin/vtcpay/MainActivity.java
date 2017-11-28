package vtc.game.app.vcoin.vtcpay;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.strategy.intecom.vtc.tracking.SDKManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.common.VtcString;
import vtc.game.app.vcoin.vtcpay.connection.VtcConnection;
import vtc.game.app.vcoin.vtcpay.connection.VtcHttpConnection;
import vtc.game.app.vcoin.vtcpay.custom.ViewPageCus;
import vtc.game.app.vcoin.vtcpay.enums.TypeActionConnection;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.interfaces.RequestListener;
import vtc.game.app.vcoin.vtcpay.model.NotiItem;
import vtc.game.app.vcoin.vtcpay.service.RegistrationService2;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;
import vtc.game.app.vcoin.vtcpay.view.FMGameList;
import vtc.game.app.vcoin.vtcpay.view.FMGiftcode;
import vtc.game.app.vcoin.vtcpay.view.FMNews;
import vtc.game.app.vcoin.vtcpay.view.account.FMProfile;
import vtc.game.app.vcoin.vtcpay.view.FMSpinning;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, RequestListener {
    public Bundle bundle = new Bundle();

    private Dialog dlEmergency;
    private ViewPageCus viewPager;
    private TextView tvTitileView;
    private LinearLayout loutProfile;
    private LinearLayout loutGiftcode;
    private LinearLayout loutNews;
    private LinearLayout loutGameList;
    private LinearLayout loutSpinning;
    private int loutFlag = -1;
    public static TextView tvNoti;
    private static Context mContext;
    private static MyPagerAdapter pagerAdapter;
    private ImageView imgSearch, imgEdit, imgHistory, imgGuide;
    private static Activity mActivity;
    private int searchFlag = 0;
    private VtcConnection vtcConnection;
    private int spinningFlag = 0;
    private ImageView imgNoti;
    private LinearLayout loutHeader;
    private ImageView imgSplashScreen;
    private RelativeLayout loutAppMain;


    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mContext = this;
        this.mActivity = this;


        SDKManager.initStartSDK(this);


        // get bundle
        if (bundle == null && getIntent() != null) {
            bundle = getIntent().getExtras();
        }
        if (bundle != null) {
            if (bundle.containsKey("type")) {
                int type = bundle.getInt("type");
                String message = bundle.getString("message");
                AppBase.showLog("type:" + type);
                AppBase.showLog("message:" + message);
            }
        }
        initController();
    }

    private void initController() {

        loutAppMain = (RelativeLayout) findViewById(R.id.lout_App_Main);
        loutAppMain.setVisibility(View.INVISIBLE);
        AppBase.showLog("Resolution: " + AppBase.getDeviceResolution(this) + "----SCREEN------" + AppBase.getSizeName(this));

        AppBase.setActivity(this);

        //change header color
        AppBase.initToolsHeaderBar(this, true);

        loutHeader = (LinearLayout) findViewById(R.id.lout_App_Header_Bar);

        intGetDeviceToken(this);
        dlEmergency = new Dialog(this);
        dlEmergency.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlEmergency.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dlEmergency.setContentView(R.layout.dl_emergency);
        dlEmergency.setCancelable(false);
//        dlEmergency.show();

        imgSplashScreen = (ImageView) findViewById(R.id.img_Splash_Screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imgSplashScreen.setVisibility(View.INVISIBLE);
//                dlEmergency.show();
            }
        }, 5000);

        vtcConnection = new VtcConnection(this, this);

        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_DOWNLOAD_BTN_STT, RequestListener.API_CONNECTION_GET_DOWNLOAD_BTN_STT, false, false);

        Button btnLogin = (Button) dlEmergency.findViewById(R.id.btn_Login);
        btnLogin.setOnClickListener(this);

        Button btnRegister = (Button) dlEmergency.findViewById(R.id.btn_Register);
        btnRegister.setOnClickListener(this);

        ImageView imgExit = (ImageView) dlEmergency.findViewById(R.id.img_Exit);
        imgExit.setOnClickListener(this);

//        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
//
//        viewPager = (ViewPageCus) findViewById(R.id.viewpage_Content);
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setPagingEnabled(false);
//        viewPager.setOffscreenPageLimit(Const.COUNT_VIEW);


        loutGiftcode = (LinearLayout) findViewById(R.id.lout_Action_Giftcode);
        loutGiftcode.setOnClickListener(this);

        loutProfile = (LinearLayout) findViewById(R.id.lout_Action_Profile);
        loutProfile.setOnClickListener(this);

        loutSpinning = (LinearLayout) findViewById(R.id.lout_Action_Spinning);
        loutSpinning.setOnClickListener(this);

        loutNews = (LinearLayout) findViewById(R.id.lout_Action_News);
        loutNews.setOnClickListener(this);

        loutGameList = (LinearLayout) findViewById(R.id.lout_Action_Game);
        loutGameList.setOnClickListener(this);
//        loutGameList.setBackgroundColor(ContextCompat.getColor(this, R.color.color_Blue_Dark));


        imgNoti = (ImageView) findViewById(R.id.img_Notification);
        imgNoti.setOnClickListener(this);

        imgSearch = (ImageView) findViewById(R.id.img_Search);
        imgSearch.setOnClickListener(this);
        imgSearch.setVisibility(View.VISIBLE);

        imgEdit = (ImageView) findViewById(R.id.img_Edit);
        imgEdit.setOnClickListener(this);
        imgEdit.setVisibility(View.GONE);

        imgHistory = (ImageView) findViewById(R.id.img_Spinning_History);
        imgHistory.setOnClickListener(this);
        imgHistory.setVisibility(View.GONE);

        imgGuide = (ImageView) findViewById(R.id.img_Spinning_Guide);
        imgGuide.setOnClickListener(this);
        imgGuide.setVisibility(View.GONE);

        tvTitileView = (TextView) findViewById(R.id.tv_Titile_View);
        tvNoti = (TextView) findViewById(R.id.tv_noti);

        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Const.DISPLAY_WIDTH = size.x;
        Const.DISPLAY_HEIGHT = size.y;
//        initHideFunc();

        initBroadcastReceiver();

        try {
            VtcString.IP_ADDRESS = AppBase.getLocalIpAddress(this);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    private void initBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter(
                ConstParam.BRODCAST_RECEIVER_HOME);

        BroadcastReceiver mReceiverDeviceToken = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                initHomeShowFunc();
            }
        };
        mContext.registerReceiver(mReceiverDeviceToken, intentFilter);
    }

    public static void updateNotiCount() {
        mActivity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        if (tvNoti != null) {
                                            if (AppBase.preferenceUtil(mContext).getValueInt(VtcString.KEY_NOTIFICATION) > 0) {

                                                if (Const.HIDE_DOWNLOAD_BUTTON == true) {
                                                    tvNoti.setVisibility(View.GONE);
                                                } else {
                                                    tvNoti.setVisibility(View.VISIBLE);
                                                    tvNoti.setText(String.valueOf(AppBase.preferenceUtil(mContext).getValueInt(VtcString.KEY_NOTIFICATION)));
                                                }
                                                // hien count ngoai icon app
                                                ShortcutBadger.applyCount(mContext, (AppBase.preferenceUtil(mContext).getValueInt(VtcString.KEY_NOTIFICATION))); //for 1.1.4+
                                            } else tvNoti.setVisibility(View.GONE);
                                        }
                                    }
                                }
        );
    }

    public static void forceLogout() {
        mActivity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        final Dialog dlLogout = new Dialog(mContext);
                                        dlLogout.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dlLogout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dlLogout.setContentView(R.layout.dl_force_logout);
                                        dlLogout.setCancelable(false);
                                        dlLogout.show();

                                        Button btnOk = (Button) dlLogout.findViewById(R.id.btn_Ok);
                                        btnOk.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dlLogout.dismiss();
                                                pagerAdapter.initLogout();

                                            }
                                        });


//                                        VtcString.ACCESS_TOKEN = null;
//                                        VtcString.USER_NAME = null;
//                                        VtcString.ACCOUNT_ID = null;
//                                        tvNoti.setVisibility(View.GONE);
                                    }
                                }
        );
    }

    /**
     * Call get device token
     *
     * @param mContext The calling context
     */
    private static void intGetDeviceToken(Context mContext) {
        // start get service get device id
        Intent intentService = new Intent(mContext, RegistrationService2.class);

//        if (!AppBase.checkPlayServices(mContext)) {
//            mContext.stopService(intentService);
//        }
        mContext.startService(intentService);
        // end get service get device Token
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Const.REQUEST_CODE_LOGIN) {
            AppBase.showLog("CallBack Login:" + SDKManager.userModel.getAccountName());
            AppBase.showLog("CallBack Login:" + SDKManager.userModel.getAccountId());
            AppBase.showLog("CallBack Login:" + SDKManager.userModel.getEmail());
            AppBase.showLog("CallBack Login:" + SDKManager.userModel.getAccessToken());

            VtcString.ACCESS_TOKEN = SDKManager.userModel.getAccessToken();
            VtcString.USER_NAME = SDKManager.userModel.getAccountName();
            VtcString.ACCOUNT_ID = SDKManager.userModel.getAccountId();
            AppBase.showLog("img_Notification: " + VtcString.ACCOUNT_ID);
            Map<String, String> mapUserInfo = new HashMap<>();
            if (SDKManager.userModel.getAccountId() != null) {
                mapUserInfo.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, SDKManager.userModel.getAccessToken());
                mapUserInfo.put(ConstParam.PARAM_REQUEST_USERNAME, SDKManager.userModel.getAccountName());
                mapUserInfo.put(ConstParam.PARAM_REQUEST_OS_TYPE, String.valueOf(Const.OS_TYPE));
                mapUserInfo.put(ConstParam.PARAM_REQUEST_DEVICE_TOKEN, VtcString.DEVICE_TOKEN);
                vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_CREATE_ACCOUNT, RequestListener.API_CONNECTION_CREATE_ACCOUNT + VtcHttpConnection.urlEncodeUTF8(mapUserInfo), true);

            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_Spinning_Guide:
                bundle.putString(ConstParam.BUNDLE_KEY_WEBVIEW_LINK, "https://vtcgame.vn/");
                bundle.putString(ConstParam.BUNDLE_KEY_WEBVIEW_TITILE, getResources().getString(R.string.title_View_GUIDE));
                AppBase.callNewFragment(Const.UI_GUIDE, bundle, true);
                break;
            case R.id.img_Spinning_History:
                bundle.putString(ConstParam.BUNDLE_KEY_SPINNING_HISTORY, VtcString.SPINNING_HISTORY_ALL);
                AppBase.callNewFragment(Const.UI_HISTORY, bundle, true);
                break;
            case R.id.btn_Login:
                // Toast.makeText(this, "btnLogin", Toast.LENGTH_SHORT).show();
                SDKManager.SignIn(this, Const.REQUEST_CODE_LOGIN);

//                AppBase.callNewFragment(Const.UI_PROFILE_ACCOUNT, null, true);

                dlEmergency.dismiss();
                break;
            case R.id.btn_Register:
//                Toast.makeText(this, "btnRegister", Toast.LENGTH_SHORT).show();
                SDKManager.Register(this, Const.REQUEST_CODE_LOGIN);
                dlEmergency.dismiss();
                break;
            case R.id.img_Exit:
                dlEmergency.dismiss();
                break;
            case R.id.img_Notification:
                AppBase.showLog("img_Notification: " + VtcString.ACCOUNT_ID);
                if (VtcString.ACCOUNT_ID == null) {
                    dlEmergency.show();
                } else {
//                    AppBase.callNewFragment(Const.UI_NOTI, null, true);
                    AppBase.callNewFragmentCallBack(Const.UI_NOTI, null, true, new Callback() {
                        @Override
                        public <T> void onHandlerCallBack(int key, T... t) {
//                            tvNoti.setText(AppBase.preferenceUtil(mContext).getValueInt(VtcString.KEY_NOTIFICATION));
                            updateNotiCount();
                            AppBase.showLog("onHandlerCallBack");
                        }
                    });
                }
                break;
            case R.id.lout_Action_Giftcode:
                loutHeader.setVisibility(View.VISIBLE);
                searchFlag = 1;
//                pagerAdapter.callViewPager(Const.INDEX_ACTION_GIFT_CODE);
                imgSearch.setVisibility(View.VISIBLE);
                imgEdit.setVisibility(View.GONE);
                imgGuide.setVisibility(View.GONE);
                imgHistory.setVisibility(View.GONE);
                viewPager.setCurrentItem(Const.INDEX_ACTION_GIFT_CODE);
                tvTitileView.setText(getResources().getString(R.string.title_View_GIFTCODE));
                changeBGButton(loutGiftcode, Const.INDEX_ACTION_GIFT_CODE);
                break;
            case R.id.lout_Action_Profile:
                if (VtcString.ACCOUNT_ID == null) {
                    dlEmergency.show();
                    loutHeader.setVisibility(View.VISIBLE);
                } else {
                    initProfileTab();
                }


                break;
            case R.id.lout_Action_Spinning:
                loutHeader.setVisibility(View.VISIBLE);
//                pagerAdapter.callViewPager(Const.INDEX_ACTION_SPINNING);
                imgSearch.setVisibility(View.GONE);
                imgEdit.setVisibility(View.GONE);
                imgGuide.setVisibility(View.VISIBLE);
                imgHistory.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(Const.INDEX_ACTION_SPINNING);
                tvTitileView.setText(getResources().getString(R.string.title_View_SPINNING));
                changeBGButton(loutSpinning, Const.INDEX_ACTION_SPINNING);
                break;
            case R.id.lout_Action_News:
                loutHeader.setVisibility(View.VISIBLE);
//                pagerAdapter.callViewPager(Const.INDEX_ACTION_NEWS);
                imgSearch.setVisibility(View.GONE);
                imgEdit.setVisibility(View.GONE);
                imgGuide.setVisibility(View.GONE);
                imgHistory.setVisibility(View.GONE);
                viewPager.setCurrentItem(Const.INDEX_ACTION_NEWS);
                tvTitileView.setText(getResources().getString(R.string.title_View_NEWS));
                changeBGButton(loutNews, Const.INDEX_ACTION_NEWS);
                break;
            case R.id.lout_Action_Game:
                initHomeShowFunc();
                break;
            case R.id.img_Search:
                bundle.putInt(ConstParam.BUNDLE_KEY_SEARCH, searchFlag);
                AppBase.callNewFragmentCallBack(Const.UI_SEARCH, bundle, true, new Callback() {
                    @Override
                    public <T> void onHandlerCallBack(int key, T... t) {
                        dlEmergency.show();
                    }
                });
                break;
            case R.id.img_Edit:
                AppBase.callNewFragment(Const.UI_EDIT_PROFILE, null, true);
                AppBase.showLog("img_Edit");
                break;
            default:
                return;
        }
    }

    public static void initUpdateVcoinProfile() {
        pagerAdapter.callViewPager(Const.INDEX_ACTION_PROFILE);
    }

    private void initHomeShowFunc() {
        loutHeader.setVisibility(View.VISIBLE);
        searchFlag = 0;
        imgSearch.setVisibility(View.VISIBLE);
        imgEdit.setVisibility(View.GONE);
        imgGuide.setVisibility(View.GONE);
        imgHistory.setVisibility(View.GONE);
        viewPager.setCurrentItem(Const.INDEX_ACTION_GAME_LIST);
        tvTitileView.setText(getResources().getString(R.string.title_View_Game_List));
        changeBGButton(loutGameList, Const.INDEX_ACTION_GAME_LIST);
    }

    private void initProfileTab() {
        loutHeader.setVisibility(View.GONE);
        pagerAdapter.callViewPager(Const.INDEX_ACTION_PROFILE);
        imgSearch.setVisibility(View.GONE);
        imgGuide.setVisibility(View.GONE);
        imgHistory.setVisibility(View.GONE);
        viewPager.setCurrentItem(Const.INDEX_ACTION_PROFILE);
        tvTitileView.setText(getResources().getString(R.string.title_View_PROFILE));
        changeBGButton(loutProfile, Const.INDEX_ACTION_PROFILE);
    }

    /**
     * Call change color button method
     *
     * @param loutAction button that want to change color
     * @param tag        tag of button
     */
    private void changeBGButton(LinearLayout loutAction, int tag) {
        switch (loutFlag) {
            case Const.INDEX_ACTION_NEWS:
                loutNews.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
                break;
            case Const.INDEX_ACTION_GAME_LIST:
                loutGameList.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
                break;
            case Const.INDEX_ACTION_GIFT_CODE:
                loutGiftcode.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
                break;
            case Const.INDEX_ACTION_PROFILE:
                loutProfile.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
                break;
            case Const.INDEX_ACTION_SPINNING:
                loutSpinning.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
                break;
            default:
                loutGameList.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        }
        loutAction.setBackgroundColor(ContextCompat.getColor(this, R.color.color_Blue_Dark));
        loutFlag = tag;
    }

    /**
     * handle data are got from server
     *
     * @param data
     */

    private void initDataNoti(String data) {
        try {
            JSONArray jsonArrayData = new JSONArray(data);
            if (jsonArrayData.length() > 0) {
                final List<NotiItem> lst = new ArrayList<>();
                int count = 0;
                for (int i = 0; i < jsonArrayData.length(); i++) {
                    if (!jsonArrayData.optJSONObject(i).optBoolean(ConstParam.IS_OPEN)) {
                        count++;
                    }
                }
                AppBase.preferenceUtil(this).setValueInt(VtcString.KEY_NOTIFICATION, count);
                ShortcutBadger.applyCount(this, count); //for 1.1.4+
                if (AppBase.preferenceUtil(this).getValueInt(VtcString.KEY_NOTIFICATION) > 0) {
                    tvNoti.setVisibility(View.VISIBLE);
                    tvNoti.setText(String.valueOf(AppBase.preferenceUtil(this).getValueInt(VtcString.KEY_NOTIFICATION)));

                    if (Const.HIDE_DOWNLOAD_BUTTON == true) {
                        tvNoti.setVisibility(View.GONE);
                    } else {
                        tvNoti.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvNoti.setVisibility(View.GONE);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Request notification data
     */
    private void initGetNotiCount() {
        if (VtcString.ACCOUNT_ID != null) {
            Map<String, String> mapInfoGame = new HashMap<>();
            mapInfoGame.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
            mapInfoGame.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
            mapInfoGame.put(ConstParam.PARAM_REQUEST_ACCOUNT_ID, VtcString.ACCOUNT_ID);

            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_LIST_NOTI, RequestListener.API_CONNECTION_GET_LIST_NOTI + VtcHttpConnection.urlEncodeUTF8(mapInfoGame), false, "");
        }
    }


    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {
        AppBase.showLog("onPostExecuteError");
        switch (keyType) {
            case TYPE_ACTION_CREATE_ACCOUNT:
                VtcString.ACCOUNT_ID = null;
                break;
            case TYPE_ACTION_GET_DOWNLOAD_BTN_STT:
                break;
        }
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_CREATE_ACCOUNT:
                AppBase.showLog("TYPE_ACTION_CREATE_ACCOUNT00: " + info);
                if (spinningFlag == 1) {
                    pagerAdapter.callViewPager(Const.INDEX_ACTION_SPINNING);
                    spinningFlag = 0;
                } else {
                    pagerAdapter.callViewPager(Const.INDEX_ACTION_SPINNING);
                    initProfileTab();
                }
                initGetNotiCount();
                break;
            case TYPE_ACTION_GET_LIST_NOTI:
                AppBase.showLog("onPostExecuteSuccess---TYPE_ACTION_GET_LIST_NOTI: " + info);
                initDataNoti(info);
                break;
            case TYPE_ACTION_GET_DOWNLOAD_BTN_STT:

                loutAppMain.setVisibility(View.VISIBLE);
                if ((!info.equals("")) && (!info.equals("null"))) {
                    try {
                        try {
                            JSONObject json = new JSONObject(info);
                            String versionServer = json.optString(ConstParam.HIIDDEN_VERSION);
                            VtcString.GUIDE_OTP = json.optString(ConstParam.OTP_MESSASGE);
                            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            String version = pInfo.versionName;

                            String updateVersion = version;
                            updateVersion = json.optString(ConstParam.UPDATE_VERSION);

                            if (version.equals(updateVersion)) {

                            } else {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                                dialog.setCancelable(false);

                                dialog.setTitle("Chú ý")
                                        .setMessage("Ứng dụng đã có phiên bản mới. Vui lòng cập nhật để có thể sử dụng ổn định!")
                                        .setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.cancel();
                                                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                                                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=vtc.game.app.vcoin.vtcpay"));
                                                startActivity(i);
                                            }
                                        })
                                        .setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }


//                            if (version.equals(versionServer)) {
//                                Const.HIDE_DOWNLOAD_BUTTON = true;
//                                initHideFunc();
//                            } else {
//                                Const.HIDE_DOWNLOAD_BUTTON = false;
//                                initShowFunc();
//                            }

                            Const.TAB_ACCOUNT_INFOR = json.optInt(ConstParam.STATUS_TAB_INFOR);
                            Const.TAB_ACCOUNT_SECURITY = json.optInt(ConstParam.STATUS_TAB_SECURITY);
                            Const.TAB_ACCOUNT_SUPPORT = json.optInt(ConstParam.STATUS_TAB_SUPPORT);
                            Const.ACCOUNT_EDIT_PHONE_NO = json.optInt(ConstParam.STATUS_MOBILE_EDIT);
                            Const.ACCOUNT_REMOVE_PHONE_NO = json.optInt(ConstParam.STATUS_MOBILE_REMOVE);
                            Const.ACCOUNT_VERIFY_PHONE_NO = json.optInt(ConstParam.STATUS_MOBILE_VERIFY);
                            Const.ACCOUNT_EDIT_EMAIL = json.optInt(ConstParam.STATUS_EMAIL_EDIT);
                            Const.TAB_SMS_PLUS = json.optInt(ConstParam.STATUS_FUNC_SMS_PLUS);
                            Const.TAB_LOGIN_SECURITY = json.optInt(ConstParam.STATUS_FUNC_LOGIN_SECURITY);
                            Const.TAB_EDIT_PASSWORD = json.optInt(ConstParam.STATUS_FUNC_PASSWORD_EDIT);
                            Const.TAB_FREEZE_VCOIN = json.optInt(ConstParam.STATUS_FUNC_FREEZE);
                            Const.TAB_EDIT_SECRET_QUESTION = json.optInt(ConstParam.STATUS_FUNC_QUESTION_EDIT);
                            Const.ACCOUNT_INFOR_PHONE_NO = json.optInt(ConstParam.STATUS_MOBILE_LOUT);
                            Const.ACCOUNT_INFOR_EMAIL = json.optInt(ConstParam.STATUS_EMAIL_LOUT);

                            pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

                            viewPager = (ViewPageCus) findViewById(R.id.viewpage_Content);
                            viewPager.setAdapter(pagerAdapter);
                            viewPager.setPagingEnabled(false);
                            viewPager.setOffscreenPageLimit(Const.COUNT_VIEW);
                            if (version.equals(versionServer)) {
                                Const.HIDE_DOWNLOAD_BUTTON = true;
                                initHideFunc();
                            } else {
                                Const.HIDE_DOWNLOAD_BUTTON = false;
                                initShowFunc();
                            }

//                            initHideFunc();//27/3/2017
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * show function when server requests
     */
    private void initShowFunc() {
        loutProfile.setVisibility(View.VISIBLE);
        loutGiftcode.setVisibility(View.VISIBLE);
        loutNews.setVisibility(View.VISIBLE);
        loutGameList.setVisibility(View.VISIBLE);
        loutSpinning.setVisibility(View.VISIBLE);
        changeBGButton(loutGameList, Const.INDEX_ACTION_GAME_LIST);
        imgNoti.setVisibility(View.VISIBLE);
        viewPager.setCurrentItem(Const.INDEX_ACTION_GAME_LIST);
        tvTitileView.setText(getResources().getString(R.string.title_View_Game_List));
        imgSearch.setVisibility(View.VISIBLE);
    }

    /**
     * hide function when server requests
     */
    private void initHideFunc() {
        loutProfile.setVisibility(View.VISIBLE);
        loutGiftcode.setVisibility(View.GONE);
        loutNews.setVisibility(View.VISIBLE);
        loutGameList.setVisibility(View.GONE);
        loutSpinning.setVisibility(View.GONE);
        changeBGButton(loutNews, Const.INDEX_ACTION_NEWS);
        imgNoti.setVisibility(View.GONE);
        tvNoti.setVisibility(View.GONE);
        viewPager.setCurrentItem(Const.INDEX_ACTION_NEWS);
        tvTitileView.setText(getResources().getString(R.string.title_View_NEWS));
        imgSearch.setVisibility(View.GONE);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        private FMGameList fmGameList;
        private FMNews fmNews;
        private FMGiftcode fmGiftcode;
        private FMProfile fmProfile;
        private FMSpinning fmSpinning;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fmGameList = new FMGameList();
            fmNews = new FMNews();
            fmGiftcode = new FMGiftcode(new Callback() {
                @Override
                public <T> void onHandlerCallBack(int key, T... t) {
                    dlEmergency.show();
                }
            });
            fmProfile = new FMProfile(new Callback() {
                @Override
                public <T> void onHandlerCallBack(int key, T... t) {
                    SDKManager.setOnSignOutCallBack(new SDKManager.onSignOutCallBack() {
                        @Override
                        public void onSignout() {
                            AppBase.showLog("SIGNOUT");
                            if (vtcConnection != null) {
                                Map<String, String> mapUserInfo = new HashMap<>();
                                if (SDKManager.userModel.getAccountId() != null) {
                                    mapUserInfo.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
                                    mapUserInfo.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
                                    vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_SIGNOUT, RequestListener.API_CONNECTION_SIGN_OUT + VtcHttpConnection.urlEncodeUTF8(mapUserInfo), true);
                                }
                            }
                        }
                    });
                    SDKManager.Signout();

                    initLogout();
                }
            });
            fmSpinning = new FMSpinning(new Callback() {
                @Override
                public <T> void onHandlerCallBack(int key, T... t) {
                    dlEmergency.show();
                    spinningFlag = 1;
                }
            });
        }

        public void initLogout() {
            loutHeader.setVisibility(View.VISIBLE);
            AppBase.preferenceUtil(mContext).setValueInt(VtcString.KEY_NOTIFICATION, 0);
            ShortcutBadger.removeCount(mContext);

            VtcString.ACCESS_TOKEN = null;
            VtcString.USER_NAME = null;
            VtcString.ACCOUNT_ID = null;
            tvNoti.setVisibility(View.GONE);
            pagerAdapter.callViewPager(Const.INDEX_ACTION_SPINNING);

            searchFlag = 0;
            pagerAdapter.callViewPager(Const.INDEX_ACTION_GAME_LIST);
            imgSearch.setVisibility(View.VISIBLE);
            imgEdit.setVisibility(View.GONE);
            imgGuide.setVisibility(View.GONE);
            imgHistory.setVisibility(View.GONE);

            if (Const.HIDE_DOWNLOAD_BUTTON == false) {
                initShowFunc();
            } else {
                initHideFunc();
            }
        }

        public void callViewPager(int index) {
            switch (index) {
                case Const.INDEX_ACTION_GAME_LIST:
                    fmGameList.callBackMain();
                    break;
                case Const.INDEX_ACTION_GIFT_CODE:
                    fmGiftcode.callBackMain();
                    break;
                case Const.INDEX_ACTION_SPINNING:
                    fmSpinning.callBackMain();
                    break;
                case Const.INDEX_ACTION_NEWS:
                    fmNews.callBackMain();
                    break;
                case Const.INDEX_ACTION_PROFILE:
                    fmProfile.callBackMain();
                    break;
            }

        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case Const.INDEX_ACTION_NEWS:
                    return fmNews;
                case Const.INDEX_ACTION_GAME_LIST:
                    return fmGameList;
                case Const.INDEX_ACTION_GIFT_CODE:
                    return fmGiftcode;
                case Const.INDEX_ACTION_PROFILE:
                    return fmProfile;
                case Const.INDEX_ACTION_SPINNING:
                    return fmSpinning;
                default:
                    return fmGameList;
            }
        }

        @Override
        public int getCount() {
            return Const.COUNT_VIEW;
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
    }
// Xử lý avatar
    /*private void initSelectImage() {
        DlMedia dlMedia = new DlMedia(this);
        dlMedia.setOnClickDialogPhoto(new DlMedia.IActionDialogPhoto() {
            @Override
            public void onClickGetPictureLibrary() {
                galleryIntent();
            }

            @Override
            public void onClickGetCamera() {
                cameraIntent();
            }
        });
        dlMedia.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //imgAvatar.setImageBitmap(bm);
        Uri uriImage = bitmapToUriConverter(bm);
        Glide.with(this).load(uriImage).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgAvatar) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgAvatar.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public Uri bitmapToUriConverter(Bitmap mBitmap) {
        Uri uri = null;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            // Calculate inSampleSize
            ///options.inSampleSize = calculateInSampleSize(options, 100, 100);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap newBitmap = Bitmap.createScaledBitmap(mBitmap, 200, 200,
                    true);
            File file = new File(this.getFilesDir(), "Image"
                    + new Random().nextInt() + ".jpeg");
            FileOutputStream out = this.openFileOutput(file.getName(),
                    Context.MODE_WORLD_READABLE);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            //get absolute path
            String realPath = file.getAbsolutePath();
            File f = new File(realPath);
            uri = Uri.fromFile(f);

        } catch (Exception e) {
            Log.e("Your Error Message", e.getMessage());
        }
        return uri;
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        imgAvatar.setImageBitmap(thumbnail);
        Uri uriImage = bitmapToUriConverter(thumbnail);
        Glide.with(this).load(uriImage).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgAvatar) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgAvatar.setImageDrawable(circularBitmapDrawable);
            }
        });
    }*/

}
