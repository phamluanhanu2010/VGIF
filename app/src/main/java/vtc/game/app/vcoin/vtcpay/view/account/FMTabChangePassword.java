package vtc.game.app.vcoin.vtcpay.view.account;


import android.graphics.Color;
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
import android.widget.Switch;
import android.widget.TextView;

import com.strategy.intecom.vtc.common.Common;

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

public class FMTabChangePassword extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, RequestListener {
    private View viewRoot;
    private EditText editOtpCode, editOldPassword, editNewPassword, editReNewPassword;
    private Spinner spOtpType;
    private VtcConnection vtcConnection;
    private TextView tvError;
    private TextView tvOtpGuide;


    public FMTabChangePassword() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_tab_change_password, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vtcConnection = new VtcConnection(getContext(), this);

        TextView tvTitleView = (TextView) view.findViewById(R.id.tv_Titile_View);
        tvTitleView.setText(getResources().getString(R.string.title_Secu_Password_Change));

        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);

        ImageView imgExit = (ImageView) view.findViewById(R.id.img_Exit);
        imgExit.setOnClickListener(this);

        Button btnChangePw = (Button) view.findViewById(R.id.btn_Change_Password);
        btnChangePw.setOnClickListener(this);

        tvOtpGuide = (TextView) view.findViewById(R.id.tv_Otp_Guide);
        tvOtpGuide.setText(Html.fromHtml(VtcString.GUIDE_OTP));

        editOtpCode = (EditText) view.findViewById(R.id.edit_Otp_Code);
        editOldPassword = (EditText) view.findViewById(R.id.edit_Old_Password);
        editNewPassword = (EditText) view.findViewById(R.id.edit_New_Password);
        editReNewPassword = (EditText) view.findViewById(R.id.edit_ReInput_New_Password);

        tvError = (TextView) view.findViewById(R.id.tv_Error_Message);

        spOtpType = (Spinner) view.findViewById(R.id.sp_Otp_Type);
        String sexArray[] = getResources().getStringArray(R.array.get_otp_type);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, sexArray);
        spinnerArrayAdapter.setDropDownViewResource(com.strategy.intecom.vtc.vtclogin.R.layout.sdk_spiner_dropdown);
        spOtpType.setAdapter(spinnerArrayAdapter);
        spOtpType.setOnItemSelectedListener(this);

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
            case R.id.btn_Change_Password:
                initChangeHandle();
                break;
        }
    }

    private void initChangeHandle() {
        tvError.setText("");

        if (initCheckInput()) {
            if (String.valueOf(editNewPassword.getText()).equals(String.valueOf(editReNewPassword.getText()))) {
                Map<String, String> mapUpdatePass = new HashMap<>();
                mapUpdatePass.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
                mapUpdatePass.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
                mapUpdatePass.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);
                mapUpdatePass.put(ConstParam.PARAM_API_NEW_PASSWORD, String.valueOf(editNewPassword.getText()));
                mapUpdatePass.put(ConstParam.PARAM_API_OLD_PASSWORD, String.valueOf(editOldPassword.getText()));
                mapUpdatePass.put(ConstParam.PARAM_API_SECURE_CODE, String.valueOf(editOtpCode.getText()));
                mapUpdatePass.put(ConstParam.PARAM_API_SECURE_TYPE, String.valueOf(spOtpType.getSelectedItemId() + 1));

                vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_UPDATE_PASSWORD, RequestListener.API_CONNECTION_UPDATE_PASSWORD + VtcHttpConnection.urlEncodeUTF8(mapUpdatePass), true);
            } else {
                tvError.setText("Mật khẩu nhập lại không khớp.");
            }
        }
    }

    private boolean initCheckInput() {
        if (String.valueOf(editOldPassword.getText()).equals("")) {
            tvError.setText(getResources().getString(R.string.error_Old_Password_Null));
            return false;
        }

        if (String.valueOf(editNewPassword.getText()).equals("")) {
            tvError.setText(getResources().getString(R.string.error_New_Password_Null));
            return false;
        }

        if (String.valueOf(editReNewPassword.getText()).equals("")) {
            tvError.setText(getResources().getString(R.string.error_Re_New_Password_Null));
            return false;
        }

        if (String.valueOf(editOtpCode.getText()).equals("")) {
            tvError.setText(getResources().getString(R.string.error_Current_Otp_Null));
            return false;
        }

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {
        tvError.setText(msg);
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        AppBase.initShowMessageAlert(getContext(), Const.ALERT_DONE, VtcString.LABEL_BTN_DONE, message);
    }
}
