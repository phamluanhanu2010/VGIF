package vtc.game.app.vcoin.vtcpay.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.strategy.intecom.vtc.common.Common;
import com.strategy.intecom.vtc.common.UserModel;
import com.strategy.intecom.vtc.common.VTCString;
import com.strategy.intecom.vtc.enums.TypeActionHitActivity;
import com.strategy.intecom.vtc.tracking.SDKManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
public class FMGameInfo extends Fragment implements View.OnClickListener, RequestListener {
    private View viewRoot;
    private Callback callback;
    private String gameID;
    private VtcConnection vtcConnection;
    private ImageView imgGameInfoBanner;
    private ImageView imgGameInfoAvatar;
    private TextView tvGameName;
    private TextView tvGameType;
    private TextView tvCapacity;
    private TextView tvIos;
    private TextView tvDescription;
    private TextView tvPublishDate;
    private LinearLayout loutFanpage;
    private Button btnDownload, btnPlayNow;
    private static String fanpageURL;
    private String downloadURL;
    private ScrollView scrollViewWrap;
    private String packageName;

    public FMGameInfo() {

    }

    @SuppressLint("ValidFragment")
    public FMGameInfo(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_game_info, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            gameID = bundle.getString(ConstParam.BUNDLE_KEY_GAME_DETAIL);
            AppBase.showLog("FMGameInfo:----------ID-------: " + gameID);
        }
        initController(view);
    }

    private void initController(View view) {

        scrollViewWrap = (ScrollView) view.findViewById(R.id.scrollView_Wrap);
        scrollViewWrap.setVisibility(View.VISIBLE);
        imgGameInfoBanner = (ImageView) view.findViewById(R.id.img_Game_Info_Banner);
        imgGameInfoAvatar = (ImageView) view.findViewById(R.id.img_Game_Info_Avatar);
        tvGameName = (TextView) view.findViewById(R.id.tv_Game_Name);
        tvGameType = (TextView) view.findViewById(R.id.tv_Game_Type);
        tvIos = (TextView) view.findViewById(R.id.tv_Ios);
        tvCapacity = (TextView) view.findViewById(R.id.tv_Capacity);
        tvPublishDate = (TextView) view.findViewById(R.id.tv_PublishDate);
        tvDescription = (TextView) view.findViewById(R.id.tv_Description);

        loutFanpage = (LinearLayout) view.findViewById(R.id.lout_Fanpage);
        loutFanpage.setOnClickListener(this);

        btnDownload = (Button) view.findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);

        btnPlayNow = (Button) view.findViewById(R.id.btn_PlayNow);
        btnPlayNow.setOnClickListener(this);

        vtcConnection = new VtcConnection(getContext(), this);
        Map<String, String> mapInfoGame = new HashMap<>();
        if (gameID != null) {
            mapInfoGame.put(ConstParam.PARAM_REQUEST_GAME_ID, gameID);

            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_GAME_INFO, RequestListener.API_CONNECTION_GET_INFO_GAME + VtcHttpConnection.urlEncodeUTF8(mapInfoGame), false, "");

        }
        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_Back:
                AppBase.initBack();
                break;
            case R.id.lout_Fanpage:
                AppBase.showLog("fanpageURL: " + fanpageURL);
                if ((!fanpageURL.equals(null)) && (!fanpageURL.equals("null"))) {
                    startActivity(getOpenFacebookIntent(getContext()));
                }
                break;
            case R.id.btn_download:

                if ((VtcString.USER_NAME == null) || (VtcString.USER_NAME == "") || (VtcString.USER_NAME == "null")) {
                    SDKManager.hitActivity(TypeActionHitActivity.TYPE_ACTION_OTHER, "", 0, "HIT_DOWNLOAD");
                } else {
                    SDKManager.hitActivity(TypeActionHitActivity.TYPE_ACTION_OTHER, VtcString.USER_NAME, Integer.parseInt(VtcString.ACCOUNT_ID.toString()), "HIT_DOWNLOAD");
                }

                Map<String, Object> eventValue = new HashMap<>();
                eventValue.put("hit_Download_Game", 1);
                SDKManager.hitActivityAppsFlyer(getContext(), "hit_Download_Game", eventValue);

                openStore();
                break;

            case R.id.btn_PlayNow:
                Intent launchApp = getContext().getPackageManager().getLaunchIntentForPackage(packageName);
                startActivity(launchApp);
                break;
            default:
                break;
        }
    }

    public static Intent getOpenFacebookIntent(Context context) {
        AppBase.showLog("getOpenFacebookIntent");
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse(fanpageURL));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse(fanpageURL));
        }
    }

    public void openStore() {
        if ((downloadURL != null) && (downloadURL != "null") && (downloadURL != "")) {
            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse(downloadURL));
            startActivity(i);
        }
    }

    private void initData(String data) {
        try {
            JSONObject dataRespone = new JSONObject(data);
            tvGameName.setText(AppBase.checkStringNull(dataRespone.optString(ConstParam.NAME_GAME)));
            tvGameType.setText(AppBase.checkStringNull(dataRespone.optString(ConstParam.TYPE_GAME)));

            tvIos.setText(AppBase.checkStringNull(dataRespone.optString(ConstParam.BASIC_GAME)));
            tvCapacity.setText(AppBase.checkStringNull(dataRespone.optString(ConstParam.CAPACITY)));

            final String strDescription = dataRespone.optString(ConstParam.DESCRIPTION);

            if (strDescription.length() > 200) {
                String subDescription = String.valueOf(Html.fromHtml(strDescription.substring(0, 200) + "... Xem thêm"));
                //tvDescription.setText(subDescription);

                Spannable spannableDes = new SpannableString((subDescription));
                ClickableSpan clickableSpanDes = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        tvDescription.setText(Html.fromHtml(strDescription));
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.RED);
                        ds.setUnderlineText(true);
                    }
                };
                subDescription = subDescription.trim();
                Common.showLog("---------------------------------------------" + subDescription.length());
                spannableDes.setSpan(clickableSpanDes, subDescription.length() - 8, subDescription.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvDescription.setText(spannableDes);
                tvDescription.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                tvDescription.setText(Html.fromHtml(strDescription));
            }


            String linkBanner = dataRespone.optString(ConstParam.BANNER_GAME);
            String linkAvatar = dataRespone.optString(ConstParam.PICTURE_GAME);
            fanpageURL = dataRespone.optString(ConstParam.LINK_PAGE_FB);
            downloadURL = dataRespone.optString(ConstParam.LINK_DOWN_ANDROID);
            String date = AppBase.longToDateTime(dataRespone.optLong(ConstParam.PUBLISH_DATE));
            tvPublishDate.setText(date);
            AppBase.setImageWithCorner(linkAvatar, imgGameInfoAvatar);
            AppBase.setImageWithUrl(linkBanner, imgGameInfoBanner);

            packageName = dataRespone.optString(ConstParam.PACKAGE_NAME);
            if (AppBase.checkAppExisting(packageName, getContext())) {
                btnPlayNow.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
            } else {
                btnPlayNow.setVisibility(View.GONE);
                if (Const.HIDE_DOWNLOAD_BUTTON == true) {
                    btnDownload.setVisibility(View.GONE);
                } else {
                    btnDownload.setVisibility(View.VISIBLE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_GAME_INFO:
                scrollViewWrap.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        scrollViewWrap.setVisibility(View.VISIBLE);
        switch (keyType) {
            case TYPE_ACTION_GET_GAME_INFO:
                initData(info);

                break;
        }
    }
}
