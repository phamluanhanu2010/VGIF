package vtc.game.app.vcoin.vtcpay.view.account;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.strategy.intecom.vtc.common.Common;

import org.json.JSONArray;
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
import vtc.game.app.vcoin.vtcpay.interfaces.RequestListener;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;

/**
 * Created by LuanPV on 3/6/2017.
 */

public class FMTabSmsPlus extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, RequestListener {
    private View viewRoot;
    private TextView tvAccountName, tvPhoneNo, tvError;
    //    private Switch swAccessInfor, swReceiveSms, swIncreaseVcoin, swDecreaseVcoin, swOtpSecurity, swOdpSecurity;
    private ToggleButton swAccessInfor, swReceiveSms, swIncreaseVcoin, swDecreaseVcoin, swOtpSecurity, swOdpSecurity;
    private EditText editActiveSms;
    private LinearLayout loutDeActived, loutActived;
    private TextView tvPhoneActiveGuide;
    private VtcConnection vtcConnection;

    public FMTabSmsPlus() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_tab_sms_plus, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vtcConnection = new VtcConnection(getContext(), this);

        TextView tvTitleView = (TextView) view.findViewById(R.id.tv_Titile_View);
        tvTitleView.setText(getResources().getString(R.string.title_Secu_SMS_Plus));

        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);

        ImageView imgExit = (ImageView) view.findViewById(R.id.img_Exit);
        imgExit.setOnClickListener(this);

        Button btnUpdate = (Button) view.findViewById(R.id.btn_Update);
        btnUpdate.setOnClickListener(this);
        btnUpdate.setVisibility(View.GONE);

        tvAccountName = (TextView) view.findViewById(R.id.tv_Account_Name);
        tvPhoneNo = (TextView) view.findViewById(R.id.tv_Phone_No);
        tvError = (TextView) view.findViewById(R.id.tv_Error_Message);

        ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.toggle_Access_Information);
        toggleButton.setOnCheckedChangeListener(this);

        swAccessInfor = (ToggleButton) view.findViewById(R.id.toggle_Access_Information);
        swAccessInfor.setOnCheckedChangeListener(this);

        swReceiveSms = (ToggleButton) view.findViewById(R.id.toggle_Recieve_Sms);
        swReceiveSms.setOnCheckedChangeListener(this);

        swIncreaseVcoin = (ToggleButton) view.findViewById(R.id.toggle_Increase_Sms);
        swIncreaseVcoin.setOnCheckedChangeListener(this);

        swDecreaseVcoin = (ToggleButton) view.findViewById(R.id.toggle_Decrease_Sms);
        swDecreaseVcoin.setOnCheckedChangeListener(this);

        swOtpSecurity = (ToggleButton) view.findViewById(R.id.toggle_OTP_Security);
        swOtpSecurity.setOnCheckedChangeListener(this);

        swOdpSecurity = (ToggleButton) view.findViewById(R.id.toggle_ODP_Security);
        swOdpSecurity.setOnCheckedChangeListener(this);

        editActiveSms = (EditText) view.findViewById(R.id.edit_Active_Sms);

        tvPhoneActiveGuide = (TextView) view.findViewById(R.id.tv_Phone_Active_Msg);

        loutActived = (LinearLayout) view.findViewById(R.id.lout_Actived);
        loutActived.setVisibility(View.GONE);

        if (Const.PHONE_NUMBER_IS_ACTIVED == 0) {
            loutActived.setVisibility(View.GONE);
            tvPhoneActiveGuide.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
            if (Const.EMAIL_IS_ACTIVED == 0) {
                tvPhoneActiveGuide.setText(getResources().getString(R.string.label_Update_Profile_Msg));
            } else {
                tvPhoneActiveGuide.setText(getResources().getString(R.string.label_Phone_Active_Msg));
            }

            if (Const.ACCOUNT_VERIFY_PHONE_NO == Const.HIDE_VALUE) {
                btnUpdate.setVisibility(View.GONE);
            } else {
                btnUpdate.setVisibility(View.VISIBLE);
            }

        } else {
            btnUpdate.setVisibility(View.VISIBLE);
            loutActived.setVisibility(View.VISIBLE);
            tvPhoneActiveGuide.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
            initGetSmsInfo();
        }


    }

    private void initGetSmsInfo() {
        Map<String, String> mapSmsInfo = new HashMap<>();
        mapSmsInfo.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapSmsInfo.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
        mapSmsInfo.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);
        mapSmsInfo.put(ConstParam.PARAM_API_ACCOUNT_ID, VtcString.ACCOUNT_ID);

        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_SMS_PLUS_INFO, RequestListener.API_CONNECTION_GET_SMS_PLUS_INFO + VtcHttpConnection.urlEncodeUTF8(mapSmsInfo), true);

    }


    @Override
    public void onClick(View v) {
        AppBase.initHideKeyboard(v, getContext());
        switch (v.getId()) {
            case R.id.img_Back:
                AppBase.initBack();
                break;
            case R.id.img_Exit:
                AppBase.initClearPopback();
                break;
            case R.id.btn_Update:
                if (Const.EMAIL_IS_ACTIVED == 0) {
                    AppBase.callNewFragment(Const.UI_PROFILE_ACCOUNT, null, true);
                } else {
                    if (Const.PHONE_NUMBER_IS_ACTIVED == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ConstParam.BUNDLE_TYPE_KEY, Const.FUNC_PHONE_VERIFY);
                        AppBase.callNewFragment(Const.UI_ACCOUNT_VERIFY, bundle, true);
                    } else {
                        initUpdateHandle();
                    }
                }

                break;

        }
    }

    private void initUpdateHandle() {

        Map<String, String> mapUpdate = new HashMap<>();
        mapUpdate.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapUpdate.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
        mapUpdate.put(ConstParam.PARAM_API_ACCOUNT_ID, VtcString.ACCOUNT_ID);
        mapUpdate.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);

        if (swReceiveSms.isChecked()) {
            mapUpdate.put(ConstParam.PARAM_API_ADV, "1");
        } else {
            mapUpdate.put(ConstParam.PARAM_API_ADV, "0");
        }

        if (swDecreaseVcoin.isChecked()) {
            mapUpdate.put(ConstParam.PARAM_API_DESC, "1");
        } else {
            mapUpdate.put(ConstParam.PARAM_API_DESC, "0");
        }

        if (swIncreaseVcoin.isChecked()) {
            mapUpdate.put(ConstParam.PARAM_API_INC, "1");
        } else {
            mapUpdate.put(ConstParam.PARAM_API_INC, "0");
        }

        mapUpdate.put(ConstParam.PARAM_API_MIN_MOUNT, String.valueOf(editActiveSms.getText()));

        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_UPDATE_SMS_PLUS, RequestListener.API_CONNECTION_UPDATE_SMS_PLUS + VtcHttpConnection.urlEncodeUTF8(mapUpdate), true);

    }

    private void initSetSwitchStatus(ToggleButton toggleButton, int status) {
        if (status == 0) {
            toggleButton.setChecked(false);

        } else {
            toggleButton.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_UPDATE_SMS_PLUS:
                tvError.setText(msg);
                break;
        }
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_SMS_PLUS_INFO:
                try {
                    JSONObject jsonInfo = new JSONObject(info);
                    tvPhoneNo.setText(jsonInfo.optString(ConstParam.MOBILE));
                    tvAccountName.setText(VtcString.USER_NAME);

                    JSONArray jsonArrayData = new JSONArray(jsonInfo.optString(ConstParam.LIST_SMS_PLUS));
                    for (int i = 0; i < jsonArrayData.length(); i++) {
                        int serviceId = jsonArrayData.getJSONObject(i).optInt(ConstParam.SMS_PLUS_SERVICE_ID);
                        int serviceStatus = jsonArrayData.getJSONObject(i).optInt(ConstParam.SMS_PLUS_SERVICE_STATUS);

                        Common.showLog(i + "--------" + serviceId);
                        Common.showLog(i + "********" + serviceStatus);
                        switch (serviceId) {
                            case Const.SMS_PLUS_ACCESS_SERVICE:

                                initSetSwitchStatus(swAccessInfor, serviceStatus);
                                break;
                            case Const.SMS_PLUS_ADS:
                                initSetSwitchStatus(swReceiveSms, serviceStatus);
                                break;
                            case Const.SMS_PLUS_ACTIVE:
                                editActiveSms.setText(String.valueOf(jsonArrayData.getJSONObject(i).optInt(ConstParam.SMS_PLUS_MIN_AMOUNT)));
                                if (jsonArrayData.getJSONObject(i).optBoolean(ConstParam.SMS_PLUS_BALANCE_INC)) {
                                    swIncreaseVcoin.setChecked(true);
                                } else {
                                    swIncreaseVcoin.setChecked(false);
                                }

                                if (jsonArrayData.getJSONObject(i).optBoolean(ConstParam.SMS_PLUS_BALANCE_DESC)) {
                                    swDecreaseVcoin.setChecked(true);
                                } else {
                                    swDecreaseVcoin.setChecked(false);
                                }
                                break;
                            case Const.SMS_PLUS_PAY_SERVICE:

                                break;
                            case Const.SMS_PLUS_OTP_SERVICE:
                                initSetSwitchStatus(swOtpSecurity, serviceStatus);
                                break;
                            case Const.SMS_PLUS_ODP_SERVICE:
                                initSetSwitchStatus(swOdpSecurity, serviceStatus);
                                break;
                            case Const.SMS_PLUS_ACCOUNT_SERVICE:

                                break;
                            case Const.SMS_PLUS_ACCOUNT_OTP_LOGIN_SERVICE:

                                break;
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case TYPE_ACTION_UPDATE_SMS_PLUS:
                AppBase.initShowMessageAlert(getContext(), Const.ALERT_DONE, VtcString.LABEL_BTN_DONE, message);
                break;
        }

    }
}
