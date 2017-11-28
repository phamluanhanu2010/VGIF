package vtc.game.app.vcoin.vtcpay.view.account;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

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

public class FMTabLoginSecurity extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, RequestListener {
    private View viewRoot;
    private EditText editOtpCode;
    private ToggleButton swAccessInformation;
    private LinearLayout loutActive, loutDeactive, loutUpdate;
    private Spinner spOtpType;
    private VtcConnection vtcConnection;
    private TextView tvErrorActive, tvErrorMsg;

    public FMTabLoginSecurity() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_tab_login_security, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vtcConnection = new VtcConnection(getContext(), this);

        TextView tvTitleView = (TextView) view.findViewById(R.id.tv_Titile_View);
        tvTitleView.setText(getResources().getString(R.string.title_Secu_Login));

        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);

        ImageView imgExit = (ImageView) view.findViewById(R.id.img_Exit);
        imgExit.setOnClickListener(this);

        Button btnCancel = (Button) view.findViewById(R.id.btn_Cancel_Register);
        btnCancel.setOnClickListener(this);

        Button btnUpdate = (Button) view.findViewById(R.id.btn_Update);
        btnUpdate.setOnClickListener(this);
        btnUpdate.setVisibility(View.GONE);

        TextView tvOtpGuide = (TextView) view.findViewById(R.id.tv_Otp_Guide);
        tvOtpGuide.setText(Html.fromHtml(VtcString.GUIDE_OTP));

        editOtpCode = (EditText) view.findViewById(R.id.edit_Otp_Code);

        tvErrorActive = (TextView) view.findViewById(R.id.tv_Error_Message_Active);
        tvErrorMsg = (TextView) view.findViewById(R.id.tv_Error_Message);

        swAccessInformation = (ToggleButton) view.findViewById(R.id.toggle_Access_Information);
        swAccessInformation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Map<String, String> mapActive = new HashMap<>();
                mapActive.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
                mapActive.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
                mapActive.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);
                mapActive.put(ConstParam.PARAM_API_SETUP_TYPE, "1");

                vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_ON_LOGIN_SECURITY_INFO, RequestListener.API_CONNECTION_ON_LOGIN_SECURITY_INFO + VtcHttpConnection.urlEncodeUTF8(mapActive), true);

            }
        });

        loutActive = (LinearLayout) view.findViewById(R.id.lout_Active);
        loutActive.setVisibility(View.GONE);
        loutDeactive = (LinearLayout) view.findViewById(R.id.lout_Deactive);
        loutDeactive.setVisibility(View.GONE);
        loutUpdate = (LinearLayout) view.findViewById(R.id.lout_Update);
        loutUpdate.setVisibility(View.GONE);

        spOtpType = (Spinner) view.findViewById(R.id.sp_Otp_Type);
        String sexArray[] = getResources().getStringArray(R.array.get_otp_type);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, sexArray);
        spinnerArrayAdapter.setDropDownViewResource(com.strategy.intecom.vtc.vtclogin.R.layout.sdk_spiner_dropdown);
        spOtpType.setAdapter(spinnerArrayAdapter);
        spOtpType.setOnItemSelectedListener(this);

        TextView tvPhoneActiveGuide = (TextView) view.findViewById(R.id.tv_Phone_Active_Msg);

        if (Const.PHONE_NUMBER_IS_ACTIVED == 0) {
            loutUpdate.setVisibility(View.VISIBLE);
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
            loutUpdate.setVisibility(View.GONE);
            initCheckStatus();
        }
    }

    private void initCheckStatus() {
        Map<String, String> mapCheckStatus = new HashMap<>();
        mapCheckStatus.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapCheckStatus.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
        mapCheckStatus.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);

        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_LOGIN_SECURITY_INFO, RequestListener.API_CONNECTION_GET_LOGIN_SECURITY_INFO + VtcHttpConnection.urlEncodeUTF8(mapCheckStatus), true, false);

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
            case R.id.btn_Cancel_Register:
                if (initCheckCancelInput()) {
                    initCancelHandle();
                }
                break;
            case R.id.btn_Update:
                if (Const.EMAIL_IS_ACTIVED == 0) {
                    AppBase.callNewFragment(Const.UI_PROFILE_ACCOUNT, null, true);
                } else {
                    if (Const.PHONE_NUMBER_IS_ACTIVED == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ConstParam.BUNDLE_TYPE_KEY, Const.FUNC_PHONE_VERIFY);
                        AppBase.callNewFragment(Const.UI_ACCOUNT_VERIFY, bundle, true);
                    }
                }
                break;

        }
    }

    private boolean initCheckCancelInput() {
        if (String.valueOf(editOtpCode.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Current_Otp_Null));
            return false;
        }
        return true;
    }

    private void initCancelHandle() {
        Map<String, String> mapActive = new HashMap<>();
        mapActive.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapActive.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
        mapActive.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);
        mapActive.put(ConstParam.PARAM_API_SETUP_TYPE, "2");
        mapActive.put(ConstParam.PARAM_API_SECURE_TYPE, String.valueOf(spOtpType.getSelectedItemId() + 1));
        mapActive.put(ConstParam.PARAM_API_SECURE_CODE, String.valueOf(editOtpCode.getText()));


        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_OFF_LOGIN_SECURITY_INFO, RequestListener.API_CONNECTION_ON_LOGIN_SECURITY_INFO + VtcHttpConnection.urlEncodeUTF8(mapActive), true);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_LOGIN_SECURITY_INFO:
                loutActive.setVisibility(View.VISIBLE);
                break;
            case TYPE_ACTION_ON_LOGIN_SECURITY_INFO:
                tvErrorActive.setText(msg);
                break;
            case TYPE_ACTION_OFF_LOGIN_SECURITY_INFO:
                tvErrorMsg.setText(msg);
                break;
        }
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_LOGIN_SECURITY_INFO:
                loutDeactive.setVisibility(View.VISIBLE);
                break;
            case TYPE_ACTION_ON_LOGIN_SECURITY_INFO:
                loutActive.setVisibility(View.GONE);
                loutDeactive.setVisibility(View.VISIBLE);
                break;
            case TYPE_ACTION_OFF_LOGIN_SECURITY_INFO:
                AppBase.initShowMessageAlert(getContext(), Const.ALERT_DONE, VtcString.LABEL_BTN_DONE, message);
                break;
        }
    }
}
