package vtc.game.app.vcoin.vtcpay.view.account;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.strategy.intecom.vtc.common.Common;
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
 * Created by ThuyChi on 10/5/2016.
 */
public class FMProfile extends Fragment implements View.OnClickListener, RequestListener {
    private View viewRoot;
    private TextView tvUserName, tvVcoin;
    private Callback callback;
    private ImageView imgAvatar;
    private VtcConnection vtcConnection;
    private Dialog dlEmergency;

    public FMProfile() {
    }

    @SuppressLint("ValidFragment")
    public FMProfile(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_profile, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgAvatar = (ImageView) view.findViewById(R.id.img_Avatar);
//        Glide.with(getActivity()).load(R.drawable.avatar_game).into(imgAvatar);

        Glide.with(getActivity()).load(R.mipmap.ic_avatar).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgAvatar) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(AppBase.getActivity().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
//                circularBitmapDrawable.setCornerRadius(Const.IMAGE_CORNER_RADIUS);
                imgAvatar.setImageDrawable(circularBitmapDrawable);
            }
        });
        vtcConnection = new vtc.game.app.vcoin.vtcpay.connection.VtcConnection(getContext(), this);

        RelativeLayout loutLogout = (RelativeLayout) view.findViewById(R.id.lout_Profile_Logout);
        loutLogout.setOnClickListener(this);

        RelativeLayout loutSecurity = (RelativeLayout) view.findViewById(R.id.lout_Profile_Security);
        loutSecurity.setVisibility(View.GONE);
        loutSecurity.setOnClickListener(this);

        RelativeLayout loutSupport = (RelativeLayout) view.findViewById(R.id.lout_Profile_Support);
        loutSupport.setVisibility(View.GONE);
        loutSupport.setOnClickListener(this);

        RelativeLayout loutInformation = (RelativeLayout) view.findViewById(R.id.lout_Profile_Information);
        loutInformation.setVisibility(View.GONE);
        loutInformation.setOnClickListener(this);


        tvUserName = (TextView) view.findViewById(R.id.tv_Username);
        tvVcoin = (TextView) view.findViewById(R.id.tv_Vcoin);

        if (Const.TAB_ACCOUNT_INFOR == Const.HIDE_VALUE) {
            loutInformation.setVisibility(View.GONE);
        } else {
            loutInformation.setVisibility(View.VISIBLE);
        }

        if (Const.TAB_ACCOUNT_SECURITY == Const.HIDE_VALUE) {
            loutSecurity.setVisibility(View.GONE);
        } else {
            loutSecurity.setVisibility(View.VISIBLE);
        }

        if (Const.TAB_ACCOUNT_SUPPORT == Const.HIDE_VALUE) {
            loutSupport.setVisibility(View.GONE);
        } else {
            loutSupport.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Get account information
     */
    private void initGetAccountDetail() {
        Map<String, String> mapGetDetail = new HashMap<>();
        mapGetDetail.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapGetDetail.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);

        if (vtcConnection != null) {
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_ACCOUNT_DETAIL, RequestListener.API_CONNECTION_GET_DETAIL + VtcHttpConnection.urlEncodeUTF8(mapGetDetail), false);
        }
    }

    /**
     * Start view
     */
    public void callBackMain() {
        AppBase.showLog("callBackMain--------------------------FMProfile");
        initController();
    }

    private void initController() {
        initGetAccountDetail();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lout_Profile_Logout:
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

                dialog.setTitle("Chú ý")
                        .setMessage("Bạn có thực sự muốn đăng xuất tài khoản?")
                        .setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                callback.onHandlerCallBack(0);
                            }
                        })
                        .setNegativeButton("Quay trở lại", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
            case R.id.lout_Profile_Information:
                AppBase.callNewFragment(Const.UI_PROFILE_ACCOUNT, null, true);
                break;
            case R.id.lout_Profile_Support:
                Bundle bundle = new Bundle();
                bundle.putString(ConstParam.BUNDLE_KEY_WEBVIEW_LINK, "http://hotro.vtc.vn/");
                bundle.putString(ConstParam.BUNDLE_KEY_WEBVIEW_TITILE, getResources().getString(R.string.title_View_SUPPORT));
                AppBase.callNewFragment(Const.UI_GUIDE, bundle, true);
                break;
            case R.id.lout_Profile_Security:
                AppBase.callNewFragment(Const.UI_ACCOUNT_SECURITY, null, true);
                break;

            default:
                break;
        }
    }

    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {

    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_ACCOUNT_DETAIL:
                AppBase.showLog("TYPE_ACTION_GET_ACCOUNT_DETAIL: " + info);

                try {
                    JSONObject jsonDetail = new JSONObject(info);
                    if (jsonDetail != null) {
                        tvUserName.setText(jsonDetail.optString(ConstParam.USER_NAME));
                        String vcoin = "Số dư: " + AppBase.splitByLength(jsonDetail.optString(ConstParam.VCOIN_PAYMENT), 3) + " Vcoin";
                        tvVcoin.setText(vcoin);

                        int phoneVerify = jsonDetail.optInt(ConstParam.VERIFY_TYPE);
                        if (phoneVerify == 3 || phoneVerify == 4) {
                            Const.PHONE_NUMBER_IS_ACTIVED = 1;
                        } else {
                            Const.PHONE_NUMBER_IS_ACTIVED = 0;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
