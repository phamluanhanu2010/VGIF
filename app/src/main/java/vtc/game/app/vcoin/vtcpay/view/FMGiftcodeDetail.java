package vtc.game.app.vcoin.vtcpay.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.strategy.intecom.vtc.enums.TypeActionHitActivity;
import com.strategy.intecom.vtc.tracking.SDKManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.common.VtcString;
import vtc.game.app.vcoin.vtcpay.connection.VtcConnection;
import vtc.game.app.vcoin.vtcpay.connection.VtcHttpConnection;
import vtc.game.app.vcoin.vtcpay.enums.TypeActionConnection;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.interfaces.RequestListener;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;


/**
 * Created by ThuyChi on 9/16/2016.
 */
public class FMGiftcodeDetail extends Fragment implements View.OnClickListener, RequestListener {
    private View viewRoot;
    private Callback callback;
    private String eventID;
    private VtcConnection vtcConnection;
    private ImageView imgBanner;
    private ImageView imgAvatar;
    private LinearLayout loutFanpage;
    private Button btnDownloadSmall;
    private TextView btnPlayNowSmall;
    private TextView btnShareFB;
    private TextView btnDownloadNow;
    private TextView tvEventDescription;
    private TextView tvGameName;
    private String gameID;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private String packageName;
    private String linkDownload;
    private String fanpageURL;
    private JSONObject jsonObjectEvent;
    private TextView tvTitileView;
    private WebView wvAds;
    private Dialog dlReward;
    private String shareFbURL;

    public FMGiftcodeDetail() {

    }

    @SuppressLint("ValidFragment")
    public FMGiftcodeDetail(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_share_game, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new Bundle();
        bundle = this.getArguments();
        if (bundle != null) {
            eventID = bundle.getString(ConstParam.BUNDLE_KEY_EVENT_DETAIL);
        }
        wvAds = (WebView) view.findViewById(R.id.webView_Ads);
        wvAds.setVisibility(View.VISIBLE);
        wvAds.getSettings().setJavaScriptEnabled(true);
        wvAds.getSettings().setLoadWithOverviewMode(true);
        wvAds.getSettings().setUseWideViewPort(true);
        wvAds.getLayoutParams().height = (int) (Const.DISPLAY_WIDTH * 9 / 16);


        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);

        TextView tvDetail = (TextView) view.findViewById(R.id.tv_Detail);
        tvDetail.setOnClickListener(this);

        tvTitileView = (TextView) view.findViewById(R.id.tv_Titile_View);

        imgBanner = (ImageView) view.findViewById(R.id.img_Game_Info_Banner);
        imgAvatar = (ImageView) view.findViewById(R.id.img_Game_Info_Avatar);

        loutFanpage = (LinearLayout) view.findViewById(R.id.lout_Fanpage);
        loutFanpage.setOnClickListener(this);

        btnDownloadSmall = (Button) view.findViewById(R.id.btn_download);
        btnDownloadSmall.setOnClickListener(this);

        btnPlayNowSmall = (TextView) view.findViewById(R.id.btn_Play_Now);
        btnPlayNowSmall.setOnClickListener(this);

        btnShareFB = (TextView) view.findViewById(R.id.btn_Share_Facebook);
        btnShareFB.setOnClickListener(this);

        tvGameName = (TextView) view.findViewById(R.id.tv_Game_Name);

        btnDownloadNow = (TextView) view.findViewById(R.id.btn_Download_Now);
        btnDownloadNow.setOnClickListener(this);

        tvEventDescription = (TextView) view.findViewById(R.id.tv_Event_Description);

        dlReward = new Dialog(getContext());
        dlReward.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlReward.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dlReward.setContentView(R.layout.dl_reward_item);
        dlReward.setCancelable(true);


//        initController(view);
        vtcConnection = new VtcConnection(getContext(), this);
        Map<String, String> mapEvent = new HashMap<>();
        if (eventID != null) {
            mapEvent.put(ConstParam.EVENT_ID, eventID);
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_EVENT_INFO_DETAIL, RequestListener.API_CONNECTION_GET_EVENT_DETAIL + VtcHttpConnection.urlEncodeUTF8(mapEvent), false, "");
        }
    }

    private void initController(String data) {
        try {
            JSONObject jsonObjectData = new JSONObject(data);
            jsonObjectEvent = jsonObjectData;

            packageName = jsonObjectData.optString(ConstParam.PACKAGE_NAME);
            if (jsonObjectData.optString(ConstParam.LINK_VIDEO_ADS) != "null") {
                wvAds.setVisibility(View.VISIBLE);
//                AppBase.startWebView(getContext(), wvAds, jsonObjectData.optString(ConstParam.LINK_VIDEO_ADS));
                String linkAds = jsonObjectData.optString(ConstParam.LINK_VIDEO_ADS);
                String frameVideo = "<iframe width=\"" + (Const.DISPLAY_WIDTH) + "\"" + " height=\"" + (Const.DISPLAY_WIDTH * 9 / 16) + "\" src=\"" + linkAds + "\"frameborder=\"0\" allowfullscreen></iframe>";
                wvAds.loadData(frameVideo, "text/html", "utf-8");


            } else wvAds.setVisibility(View.GONE);
            if (jsonObjectData.optInt(ConstParam.TYPE_MISSION) == Const.TYPE_MISSION_SHARE_FB) {
                shareFbURL = jsonObjectData.optString(ConstParam.SHARE_FACEBOOK_URL);
                tvTitileView.setText(getResources().getString(R.string.title_View_SHARE_GAME));
                btnShareFB.setVisibility(View.VISIBLE);
                btnDownloadNow.setVisibility(View.GONE);
                if (AppBase.checkAppExisting(packageName, getContext())) {
                    btnDownloadSmall.setVisibility(View.GONE);
                    btnPlayNowSmall.setVisibility(View.VISIBLE);
                } else {
                    if (Const.HIDE_DOWNLOAD_BUTTON == true) {
                        btnDownloadSmall.setVisibility(View.GONE);
                    } else {
                        btnDownloadSmall.setVisibility(View.VISIBLE);
                    }
                    btnPlayNowSmall.setVisibility(View.GONE);
                }

            } else if (jsonObjectData.optInt(ConstParam.TYPE_MISSION) == Const.TYPE_MISSION_DOWNLOAD) {
                if (AppBase.checkAppExisting(packageName, getContext())) {
                    btnDownloadSmall.setVisibility(View.GONE);
                    btnDownloadNow.setVisibility(View.GONE);
                    btnPlayNowSmall.setVisibility(View.VISIBLE);
                } else {
                    btnPlayNowSmall.setVisibility(View.GONE);
                    btnDownloadSmall.setVisibility(View.GONE);
                    if (Const.HIDE_DOWNLOAD_BUTTON == true) {
                        btnDownloadNow.setVisibility(View.GONE);
                    } else {
                        btnDownloadNow.setVisibility(View.VISIBLE);
                    }
                }
                tvTitileView.setText(getResources().getString(R.string.title_View_DOWNLOAD_GAME));
                btnShareFB.setVisibility(View.GONE);
//                btnDownloadNow.setVisibility(View.VISIBLE);
//                btnPlayNowSmall.setVisibility(View.GONE);
//                btnDownloadSmall.setVisibility(View.GONE);

            }
            gameID = jsonObjectData.optString(ConstParam.GAME_ID);
            String bannerURL = jsonObjectData.optString(ConstParam.BANNER_EVENT);
            String avatarURL = jsonObjectData.optString(ConstParam.PICTURE_GAME);
            AppBase.setImageWithUrl(bannerURL, imgBanner);
            AppBase.setImageWithCorner(avatarURL, imgAvatar);
            tvEventDescription.setText(jsonObjectData.optString(ConstParam.REWARD_DESCRIPTION));
            tvGameName.setText(jsonObjectData.optString(ConstParam.NAME_GAME));
            linkDownload = jsonObjectData.optString(ConstParam.LINK_DOWN_ANDROID);
            fanpageURL = jsonObjectData.optString(ConstParam.LINK_PAGE_FB);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initShowPopUp(String response) {
        ImageView imgDLAvatar = (ImageView) dlReward.findViewById(R.id.img_Avatar_Game);
        TextView tvDLGameName = (TextView) dlReward.findViewById(R.id.tv_Game_Name);
        TextView tvDLRewardTitle = (TextView) dlReward.findViewById(R.id.tv_Titile);
        TextView tvDLRewardType = (TextView) dlReward.findViewById(R.id.tv_Reward_Type);
        TextView tvDLRewardDescription = (TextView) dlReward.findViewById(R.id.tv_Reward_Description);
        tvDLRewardTitle.setVisibility(View.VISIBLE);
        tvDLRewardDescription.setVisibility(View.VISIBLE);

        ImageView imgClosePopup = (ImageView) dlReward.findViewById(R.id.img_Close_Popup);
        imgClosePopup.setOnClickListener(this);

        try {
            JSONObject jsonObject = new JSONObject(response);
            tvDLGameName.setText(jsonObject.optString(ConstParam.NAME_GAME));
            tvDLRewardTitle.setText(jsonObject.optString(ConstParam.TYPE_REWARD_STRING));
            tvDLRewardType.setText(jsonObject.optString(ConstParam.CODE_SERIAL));
            tvDLRewardDescription.setText(jsonObject.optString(ConstParam.REWARD_DESCRIPTION));
            AppBase.setImageWithCorner(jsonObject.optString(ConstParam.PICTURE_GAME), imgDLAvatar);
            dlReward.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_Back:
                AppBase.initBack();
                break;
            case R.id.tv_Detail:
                if (gameID != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstParam.BUNDLE_KEY_GAME_DETAIL, gameID);
                    AppBase.callNewFragment(Const.UI_GAME_INFO, bundle, true);
                }
                break;
            case R.id.lout_Fanpage:
                if ((!fanpageURL.equals(null)) && (!fanpageURL.equals("null")) && (fanpageURL != "")) {
                    startActivity(getOpenFacebookIntent(getContext(), fanpageURL));
                }
                break;
            case R.id.img_Close_Popup:
                dlReward.dismiss();
                break;
            case R.id.btn_download:
//                if (VtcString.ACCOUNT_ID == null) {
//                    callback.onHandlerCallBack(0);
//                } else {
                if ((linkDownload != null) && linkDownload != "null" && linkDownload != "") {

                    if ((VtcString.USER_NAME == null) || (VtcString.USER_NAME == "") || (VtcString.USER_NAME == "null")) {
                        SDKManager.hitActivity(TypeActionHitActivity.TYPE_ACTION_OTHER, "", 0, "HIT_DOWNLOAD");
                    } else {
                        SDKManager.hitActivity(TypeActionHitActivity.TYPE_ACTION_OTHER, VtcString.USER_NAME, Integer.parseInt(VtcString.ACCOUNT_ID.toString()), "HIT_DOWNLOAD");
                    }

                    Map<String, Object> eventValue = new HashMap<>();
                    eventValue.put("hit_Download_Game", 1);
                    SDKManager.hitActivityAppsFlyer(getContext(), "hit_Download_Game", eventValue);

//                    openStore(linkDownload);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(linkDownload));
                    startActivity(i);
                }
//                }

                break;
            case R.id.btn_Play_Now:
                Intent launchApp = getContext().getPackageManager().getLaunchIntentForPackage(packageName);
                startActivity(launchApp);
                break;
            case R.id.btn_Share_Facebook:
                if (VtcString.ACCOUNT_ID == null) {
                    callback.onHandlerCallBack(0);
                } else {
                    if ((shareFbURL != "null") && (shareFbURL != "")) {
                        shareFacebook(shareFbURL);
                    }
                }
                break;
            case R.id.btn_Download_Now:
                if (VtcString.ACCOUNT_ID == null) {
                    callback.onHandlerCallBack(0);
                } else {
                    if ((linkDownload != null) && linkDownload != "null" && linkDownload != "") {

                        if ((VtcString.USER_NAME == null) || (VtcString.USER_NAME == "") || (VtcString.USER_NAME == "null")) {
                            SDKManager.hitActivity(TypeActionHitActivity.TYPE_ACTION_OTHER, "", 0, "HIT_DOWNLOAD");
                        } else {
                            SDKManager.hitActivity(TypeActionHitActivity.TYPE_ACTION_OTHER, VtcString.USER_NAME, Integer.parseInt(VtcString.ACCOUNT_ID.toString()), "HIT_DOWNLOAD");
                        }

                        Map<String, Object> eventValue = new HashMap<>();
                        eventValue.put("hit_Download_Game", 1);
                        SDKManager.hitActivityAppsFlyer(getContext(), "hit_Download_Game", eventValue);

                        openStore(linkDownload);
                    }
                }
                break;
            default:
                break;
        }
    }

    public static Intent getOpenFacebookIntent(Context context, String fanpageURL) {
        AppBase.showLog("getOpenFacebookIntent");
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse(fanpageURL));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse(fanpageURL));
        }
    }

    public void openStore(String downloadURL) {
        if (jsonObjectEvent != null) {
            Map<String, String> mapSpinningInfo = new HashMap<>();
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_ACCOUNT_ID, VtcString.ACCOUNT_ID);
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_EVENT_ID, jsonObjectEvent.optString(ConstParam.ID));
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_GAME_ID, jsonObjectEvent.optString(ConstParam.GAME_ID));
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_GAME_NAME, jsonObjectEvent.optString(ConstParam.NAME_GAME));
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_TYPE_MISSION, jsonObjectEvent.optString(ConstParam.TYPE_MISSION));
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_DEVICE_TOKEN, VtcString.DEVICE_TOKEN);
//            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_DOWNLOAD_MISION, RequestListener.API_CONNECTION_DOWNLOAD_MISSION + VtcHttpConnection.urlEncodeUTF8(mapSpinningInfo), false);
//
            String s = VtcHttpConnection.VGIFT_URL_CONNECT_SERVER_RELEASE + RequestListener.API_CONNECTION_DOWNLOAD_MISSION + VtcHttpConnection.urlEncodeUTF8(mapSpinningInfo);
//            AppBase.showLog("00000000000000-----" + VtcHttpConnection.urlEncodeUTF8(mapSpinningInfo));
//            AppBase.showLog("11111111111111-----" + RequestListener.API_CONNECTION_DOWNLOAD_MISSION + VtcHttpConnection.urlEncodeUTF8(mapSpinningInfo));
//            AppBase.showLog("22222222222222-----" + VtcHttpConnection.VGIFT_URL_CONNECT_SERVER_RELEASE + RequestListener.API_CONNECTION_DOWNLOAD_MISSION + VtcHttpConnection.urlEncodeUTF8(mapSpinningInfo));
//            String linkdown = "https://play.google.com/store/apps/details?id=gamevtc.gamexeng.tamquocxeng.gamevcoin&hl=en&utm_source=stestvgift27&utm_campaign=ctestvgift27&utm_medium=mtestvgift27";

//
            Map<String, String> mapPostback = new HashMap<>();
            mapPostback.put("callback", s);
            String postback = downloadURL + "&referrer=%26" + VtcHttpConnection.urlEncodeUTF8(mapPostback).substring(1);
            AppBase.showLog("POSTBACK:------------" + postback);

//            try {
//                AppBase.showLog("Encode: " + URLEncoder.encode(postback, "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(postback));
            startActivity(i);
        }
    }

    private void shareFacebook(String shareFbURL) {
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
                AppBase.showLog("onSuccess");
                AppBase.showLog("registerCallback: " + result.getPostId());

                initShareMission();
            }

            @Override
            public void onCancel() {
                AppBase.showLog("onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                AppBase.showLog("onError");
            }

        });

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                    .setContentTitle("Hello Facebook")
//                    .setContentDescription(
//                            "The 'Hello Facebook' sample  showcases simple Facebook integration")
                    .setContentUrl(Uri.parse(shareFbURL))
                    .build();

            shareDialog.show(linkContent, ShareDialog.Mode.FEED);
        }
    }

    private void initShareMission() {
        if (jsonObjectEvent != null) {
            Map<String, String> mapSpinningInfo = new HashMap<>();
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_ACCOUNT_ID, VtcString.ACCOUNT_ID);
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_EVENT_ID, jsonObjectEvent.optString(ConstParam.ID));
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_GAME_ID, jsonObjectEvent.optString(ConstParam.GAME_ID));
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_GAME_NAME, jsonObjectEvent.optString(ConstParam.NAME_GAME));
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_TYPE_MISSION, jsonObjectEvent.optString(ConstParam.TYPE_MISSION));
            mapSpinningInfo.put(ConstParam.PARAM_REQUEST_DEVICE_TOKEN, VtcString.DEVICE_TOKEN);
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_SHARE_MISION, RequestListener.API_CONNECTION_SHARE_MISSION + VtcHttpConnection.urlEncodeUTF8(mapSpinningInfo), true);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppBase.showLog("onActivityResult11111: " + resultCode);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        AppBase.showLog("onActivityResult: " + resultCode);

        if (requestCode == Const.REQUEST_CODE_SHARE_FB) {
            if (resultCode == Activity.RESULT_OK) {
                AppBase.showLog("onActivityResult" + String.valueOf(resultCode));
            } else {
                AppBase.showLog("Share failed or cancel");
            }
        }
    }

    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {
        AppBase.showLog("onPostExecuteError: " + reponseFullData);
        switch (keyType) {
            case TYPE_ACTION_SHARE_MISION:
                if (errorcode.equals("206") || (errorcode.equals("207"))) {
                    ImageView imgDLAvatar = (ImageView) dlReward.findViewById(R.id.img_Avatar_Game);
                    TextView tvDLGameName = (TextView) dlReward.findViewById(R.id.tv_Game_Name);
                    TextView tvDLRewardTitle = (TextView) dlReward.findViewById(R.id.tv_Titile);
                    TextView tvDLRewardType = (TextView) dlReward.findViewById(R.id.tv_Reward_Type);
                    TextView tvDLRewardDescription = (TextView) dlReward.findViewById(R.id.tv_Reward_Description);
                    tvDLRewardTitle.setVisibility(View.INVISIBLE);
                    tvDLRewardDescription.setVisibility(View.INVISIBLE);

                    ImageView imgClosePopup = (ImageView) dlReward.findViewById(R.id.img_Close_Popup);
                    imgClosePopup.setOnClickListener(this);

                    try {
                        if ((info != "") && (info != "null")) {
                            JSONObject jsonObject = new JSONObject(info);
                            tvDLGameName.setText(jsonObject.optString(ConstParam.NAME_GAME));
                            if ((msg != "null") && (msg != "")) {
                                tvDLRewardType.setText(msg);
                            }
                            AppBase.setImageWithCorner(jsonObject.optString(ConstParam.PICTURE_GAME), imgDLAvatar);
                            dlReward.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (errorcode.equals("205")) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        AppBase.showLog("onPostExecuteSuccess--TYPE_ACTION_SHARE_MISION222222: " + reponseFullData);
        switch (keyType) {
            case TYPE_ACTION_GET_EVENT_INFO_DETAIL:
                AppBase.showLog("onPostExecuteSuccess--TYPE_ACTION_GET_EVENT_INFO_DETAIL: " + info);
                initController(info);
                break;
            case TYPE_ACTION_SHARE_MISION:

                try {
                    JSONObject jsonObject = new JSONObject(reponseFullData);
                    if (jsonObject != null) {
                        AppBase.showLog("onPostExecuteSuccess--TYPE_ACTION_SHARE_MISION: " + reponseFullData);
                        Toast.makeText(getContext(), jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        if (!info.equals(""))
                            initShowPopUp(info);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case TYPE_ACTION_DOWNLOAD_MISION:
                AppBase.showLog("onPostExecuteSuccess--TYPE_ACTION_DOWNLOAD_MISION: " + reponseFullData);
                break;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        wvAds.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
