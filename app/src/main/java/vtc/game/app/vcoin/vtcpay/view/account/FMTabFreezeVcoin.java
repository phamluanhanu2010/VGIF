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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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

public class FMTabFreezeVcoin extends Fragment implements View.OnClickListener, RequestListener, AdapterView.OnItemSelectedListener {
    private View viewRoot;
    private TextView tvErrorMsg, tvAccountName, tvAvailableVcoin, tvFreezeVcoin;
    private EditText editVcoinCount, editOtpCode;
    private Spinner spFreezeType, spOtpType;
    private VtcConnection vtcConnection;
    private LinearLayout loutOtp;


    public FMTabFreezeVcoin() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_tab_freeze_vcoin, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vtcConnection = new VtcConnection(getContext(), this);

        TextView tvTitleView = (TextView) view.findViewById(R.id.tv_Titile_View);
        tvTitleView.setText(getResources().getString(R.string.title_Secu_Freeze));

        TextView tvOtpGuide = (TextView) view.findViewById(R.id.tv_Otp_Guide);
        tvOtpGuide.setText(Html.fromHtml(VtcString.GUIDE_OTP));

        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);

        ImageView imgExit = (ImageView) view.findViewById(R.id.img_Exit);
        imgExit.setOnClickListener(this);

        Button btnAction = (Button) view.findViewById(R.id.btn_Action);
        btnAction.setOnClickListener(this);

        tvErrorMsg = (TextView) view.findViewById(R.id.tv_Error_Message);
        tvAccountName = (TextView) view.findViewById(R.id.tv_Account_Name);
        tvAvailableVcoin = (TextView) view.findViewById(R.id.tv_Available_Vcoin);
        tvFreezeVcoin = (TextView) view.findViewById(R.id.tv_Freeze_Vcoin);

        editVcoinCount = (EditText) view.findViewById(R.id.edit_Vcoin_Count);
        editOtpCode = (EditText) view.findViewById(R.id.edit_Otp_Code);

        loutOtp = (LinearLayout) view.findViewById(R.id.lout_Otp);
        loutOtp.setVisibility(View.GONE);

        spFreezeType = (Spinner) view.findViewById(R.id.sp_Type);
        String typeArray[] = getResources().getStringArray(R.array.freeze_vcoin);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, typeArray);
        spinnerArrayAdapter.setDropDownViewResource(com.strategy.intecom.vtc.vtclogin.R.layout.sdk_spiner_dropdown);
        spFreezeType.setAdapter(spinnerArrayAdapter);
        spFreezeType.setOnItemSelectedListener(this);

        spOtpType = (Spinner) view.findViewById(R.id.sp_Otp_Type);
        String otpArray[] = getResources().getStringArray(R.array.get_otp_type);
        ArrayAdapter<String> otpArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, otpArray);
        otpArrayAdapter.setDropDownViewResource(com.strategy.intecom.vtc.vtclogin.R.layout.sdk_spiner_dropdown);
        spOtpType.setAdapter(otpArrayAdapter);
        spOtpType.setOnItemSelectedListener(this);

        initGetFreezeInfo();
    }

    /**
     * initGetFreezeInfo method: get information of user account
     */
    private void initGetFreezeInfo() {
        Map<String, String> mapFreeze = new HashMap<>();
        mapFreeze.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapFreeze.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
        mapFreeze.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);
        mapFreeze.put(ConstParam.PARAM_API_ACCOUNT_ID, VtcString.ACCOUNT_ID);

        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_FREEZE_INFO, RequestListener.API_CONNECTION_GET_FREEZE_INFO + VtcHttpConnection.urlEncodeUTF8(mapFreeze), true);

    }


    @Override
    public void onClick(View v) {
        AppBase.initHideKeyboard(v, getContext());
        tvErrorMsg.setText("");
        switch (v.getId()) {
            case R.id.img_Back:
                AppBase.initBack();
                break;
            case R.id.img_Exit:
                AppBase.initClearPopback();
                break;
            case R.id.btn_Action:
                initActionHandle();
                break;

        }
    }

    private void initActionHandle() {
        Map<String, String> mapFreeze = new HashMap<>();
        mapFreeze.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapFreeze.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
        mapFreeze.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);
        mapFreeze.put(ConstParam.PARAM_API_ACCOUNT_ID, VtcString.ACCOUNT_ID);
        mapFreeze.put(ConstParam.PARAM_API_VCOIN, String.valueOf(editVcoinCount.getText()));
        if (spFreezeType.getSelectedItemId() == 0) {
            if (initCheckFreezeInput()) {
                vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_FREEZE, RequestListener.API_CONNECTION_FREEZE + VtcHttpConnection.urlEncodeUTF8(mapFreeze), true);
            }
        } else {
            if (initCheckUnFreezeInput()) {
                mapFreeze.put(ConstParam.PARAM_API_SECURE_TYPE, String.valueOf(spOtpType.getSelectedItemId() + 1));
                mapFreeze.put(ConstParam.PARAM_API_SECURE_CODE, String.valueOf(editOtpCode.getText()));
                vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_UNFREEZE, RequestListener.API_CONNECTION_UNFREEZE + VtcHttpConnection.urlEncodeUTF8(mapFreeze), true);
            }
        }
    }

    private boolean initCheckFreezeInput() {
        if (String.valueOf(editVcoinCount.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Freeze_Null));
            return false;
        }
        return true;
    }

    private boolean initCheckUnFreezeInput() {
        if (String.valueOf(editVcoinCount.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Unfreeze_Null));
            return false;
        }

        if (String.valueOf(editOtpCode.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Current_Otp_Null));
            return false;
        }
        return true;
    }

    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_FREEZE:
                tvErrorMsg.setText(msg);
                break;
            case TYPE_ACTION_UNFREEZE:
                tvErrorMsg.setText(msg);
                break;
        }
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_FREEZE_INFO:
                try {
                    JSONObject jsonData = new JSONObject(info);
                    tvAccountName.setText(AppBase.initCheckString(jsonData, ConstParam.ACCOUNT_NAME));
                    tvAvailableVcoin.setText(String.valueOf(jsonData.optInt(ConstParam.VCOIN)));
                    tvFreezeVcoin.setText(String.valueOf(jsonData.optInt(ConstParam.VCOIN_FREEZE)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case TYPE_ACTION_FREEZE:
                AppBase.initShowMessageAlert(getContext(), Const.ALERT_UPDATE_VCOIN, VtcString.LABEL_BTN_DONE, message);
                break;
            case TYPE_ACTION_UNFREEZE:
                AppBase.initShowMessageAlert(getContext(), Const.ALERT_UPDATE_VCOIN, VtcString.LABEL_BTN_DONE, message);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.sp_Type) {
            if (position == 0) {
                editVcoinCount.setHint("Nhập số Vcoin đóng băng");
                loutOtp.setVisibility(View.GONE);
            } else {
                editVcoinCount.setHint("Nhập số Vcoin mở đóng băng");
                loutOtp.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
