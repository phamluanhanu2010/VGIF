package vtc.game.app.vcoin.vtcpay.view.account;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.strategy.intecom.vtc.common.VTCString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.common.VtcString;
import vtc.game.app.vcoin.vtcpay.connection.VtcConnection;
import vtc.game.app.vcoin.vtcpay.connection.VtcHttpConnection;
import vtc.game.app.vcoin.vtcpay.enums.TypeActionConnection;
import vtc.game.app.vcoin.vtcpay.interfaces.RequestListener;
import vtc.game.app.vcoin.vtcpay.model.SecretQuestion;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;

/**
 * Created by ThuyChi on 10/5/2016.
 */
public class FMChangeAndVerify extends Fragment implements View.OnClickListener, RequestListener, AdapterView.OnItemSelectedListener {
    private View viewRoot;
    private TextView tvAccountName, tvErrorMsg, tvOtpGuide, tvTitle;
    private EditText editPhoneNo, editNewPhoneNo, editOtpCode;
    private ImageView lineNewPhoneNo;
    public Bundle bundle = new Bundle();
    private int uiKey = 0;
    private LinearLayout loutPhone, loutEmail, loutNewPhoneNo, loutNewOtpCode, loutEditPhone;
    private RelativeLayout loutOtpCode;
    private Spinner spSecretQuestion;
    private EditText editSecretQuestion, editCurrentEmail, editNewEmail, editNewOtpCode;
    private VtcConnection vtcConnection;
    private Button btnUpdate;
    private Context context;
    private String sign = "";
    private int verifyPhoneStep = 0;
    private int editPhoneStep = 0;

    public FMChangeAndVerify() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_change_and_verify, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.context = getContext();

        vtcConnection = new VtcConnection(getContext(), this);

        uiKey = getArguments().getInt(ConstParam.BUNDLE_TYPE_KEY);

        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);

        ImageView imgExit = (ImageView) view.findViewById(R.id.img_Exit);
        imgExit.setOnClickListener(this);

        btnUpdate = (Button) view.findViewById(R.id.btn_Update);
        btnUpdate.setOnClickListener(this);

        tvAccountName = (TextView) view.findViewById(R.id.tv_Account_Name);
        tvErrorMsg = (TextView) view.findViewById(R.id.tv_Error_Message);

        tvOtpGuide = (TextView) view.findViewById(R.id.tv_Otp_Guide);
        tvOtpGuide.setText(Html.fromHtml(VtcString.GUIDE_OTP));

        tvTitle = (TextView) view.findViewById(R.id.tv_Titile_View);

        editPhoneNo = (EditText) view.findViewById(R.id.edit_Phone_No);
        editNewPhoneNo = (EditText) view.findViewById(R.id.edit_New_Phone_No);
        editOtpCode = (EditText) view.findViewById(R.id.edit_Otp_Code);
        editNewOtpCode = (EditText) view.findViewById(R.id.edit_New_Otp_Code);

        lineNewPhoneNo = (ImageView) view.findViewById(R.id.line_New_Phone_No);

        loutPhone = (LinearLayout) view.findViewById(R.id.lout_Phone_Verify);
        loutPhone.setVisibility(View.GONE);
        loutEmail = (LinearLayout) view.findViewById(R.id.lout_Email_Edit);
        loutEmail.setVisibility(View.GONE);
        loutNewPhoneNo = (LinearLayout) view.findViewById(R.id.lout_New_Phone_No);
        loutOtpCode = (RelativeLayout) view.findViewById(R.id.lout_Otp_Code);
        loutNewOtpCode = (LinearLayout) view.findViewById(R.id.lout_New_Otp_Code);
        loutEditPhone = (LinearLayout) view.findViewById(R.id.lout_Edit_Phone);


        editSecretQuestion = (EditText) view.findViewById(R.id.edit_Secret_Question);
        editCurrentEmail = (EditText) view.findViewById(R.id.edit_Current_Email);
        editNewEmail = (EditText) view.findViewById(R.id.edit_New_Email);

        spSecretQuestion = (Spinner) view.findViewById(R.id.sp_Secret_Question);

        switch (uiKey) {
            case Const.FUNC_PHONE_VERIFY:
                tvTitle.setText(getResources().getString(R.string.title_View_Account_Phone_Verify));
                btnUpdate.setText(context.getResources().getString(R.string.title_Btn_Continuous));
                loutNewPhoneNo.setVisibility(View.GONE);
                lineNewPhoneNo.setVisibility(View.GONE);
                loutPhone.setVisibility(View.VISIBLE);
                loutOtpCode.setVisibility(View.GONE);
                tvOtpGuide.setVisibility(View.GONE);

                tvAccountName.setText(VtcString.USER_NAME);

                break;
            case Const.FUNC_EMAIL_EDIT:
                tvTitle.setText(getResources().getString(R.string.title_View_Account_Email_Edit));
                tvOtpGuide.setVisibility(View.GONE);
                initEmailEditFunc();
                break;
            case Const.FUNC_PHONE_EDIT:
                tvAccountName.setText(VtcString.USER_NAME);
                editOtpCode.setHint(context.getResources().getString(R.string.hint_Account_Otp_Code));
                btnUpdate.setText(context.getResources().getString(R.string.title_Btn_Continuous));
                tvTitle.setText(getResources().getString(R.string.title_View_Account_Phone_Edit));
                loutEmail.setVisibility(View.GONE);
                loutPhone.setVisibility(View.VISIBLE);
                tvOtpGuide.setVisibility(View.VISIBLE);
                break;
            case Const.FUNC_PHONE_REMOVE:
                tvTitle.setText(getResources().getString(R.string.title_View_Account_Phone_Remove));

                loutNewPhoneNo.setVisibility(View.GONE);
                lineNewPhoneNo.setVisibility(View.GONE);
                loutPhone.setVisibility(View.VISIBLE);
                loutOtpCode.setVisibility(View.VISIBLE);
                tvOtpGuide.setVisibility(View.VISIBLE);

                tvAccountName.setText(VtcString.USER_NAME);
                break;
        }
    }

    private void initPhoneEditFunc() {
        Map<String, String> mapEditPhone = new HashMap<>();
        mapEditPhone.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapEditPhone.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
        mapEditPhone.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);
        mapEditPhone.put(ConstParam.PARAM_API_MOBILE, String.valueOf(editPhoneNo.getText()));
        mapEditPhone.put(ConstParam.PARAM_API_OTP, String.valueOf(editOtpCode.getText()));
        mapEditPhone.put(ConstParam.PARAM_API_NEW_MOBILE, String.valueOf(editNewPhoneNo.getText()));


        if (editPhoneStep == 0) {
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_EDIT_PHONE_FIRST_STEP, RequestListener.API_CONNECTION_EDIT_PHONE_FIRST_STEP + VtcHttpConnection.urlEncodeUTF8(mapEditPhone), true);
        } else {
            mapEditPhone.put(ConstParam.PARAM_API_SIGN, sign);
            mapEditPhone.put(ConstParam.PARAM_API_NEW_OTP, String.valueOf(editNewOtpCode.getText()));
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_EDIT_PHONE_SECOND_STEP, RequestListener.API_CONNECTION_EDIT_PHONE_SECOND_STEP + VtcHttpConnection.urlEncodeUTF8(mapEditPhone), true);
        }

    }

    private void initEmailEditFunc() {
        loutEmail.setVisibility(View.VISIBLE);

        initGetSecretQuestion();
    }

    private void initGetSecretQuestion() {
        if (vtcConnection != null) {
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_SECRET_QUESTION, RequestListener.API_CONNECTION_GET_SECRET_QUESTION, true);
        }
    }

    private void initQuestionHandle(String data) {
        try {
            JSONArray jsonArrayData = new JSONArray(data);
            ArrayList<SecretQuestion> question = new ArrayList<>();

            question.add(new SecretQuestion("Câu hỏi bảo mật", 0));
            for (int i = 0; i < jsonArrayData.length(); i++) {
                question.add(new SecretQuestion(jsonArrayData.optJSONObject(i).optString("questionName"), jsonArrayData.optJSONObject(i).optInt("questionID")));

            }

            ArrayAdapter<SecretQuestion> adapter =
                    new ArrayAdapter<SecretQuestion>(getContext(), R.layout.spinner_item, question) {
                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                // Set the hint text color gray
                                tv.setTextColor(Color.GRAY);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }

                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                return false;
                            } else {
                                return true;
                            }
                        }
                    };
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

            spSecretQuestion.setAdapter(adapter);
            spSecretQuestion.setOnItemSelectedListener(this);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * initPhoneVerifyFunc method to verify phone number of user account
     */
    private void initPhoneVerifyFunc() {

        Map<String, String> mapVerifyPhone = new HashMap<>();
        mapVerifyPhone.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapVerifyPhone.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
        mapVerifyPhone.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);
        mapVerifyPhone.put(ConstParam.PARAM_API_MOBILE, String.valueOf(editPhoneNo.getText()));

        if (verifyPhoneStep == 0) {
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_VERIFY_PHONE_FIRST_STEP, RequestListener.API_CONNECTION_VERIFY_PHONE_FIRST_STEP + VtcHttpConnection.urlEncodeUTF8(mapVerifyPhone), true);
        } else {
            mapVerifyPhone.put(ConstParam.PARAM_API_OTP, String.valueOf(editOtpCode.getText()));
            mapVerifyPhone.put(ConstParam.PARAM_API_SIGN, sign);
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_VERIFY_PHONE_SECOND_STEP, RequestListener.API_CONNECTION_VERIFY_PHONE_SECOND_STEP + VtcHttpConnection.urlEncodeUTF8(mapVerifyPhone), true);
        }

    }

    @Override
    public void onClick(View v) {
        AppBase.initHideKeyboard(v, getContext());
        tvErrorMsg.setText("");
        switch (v.getId()) {
            case R.id.img_Back:
                if ((uiKey == Const.FUNC_PHONE_VERIFY) && (verifyPhoneStep == 1)) {
                    btnUpdate.setText(context.getResources().getString(R.string.title_Btn_Continuous));
                    loutOtpCode.setVisibility(View.GONE);
                    tvOtpGuide.setVisibility(View.GONE);
                    verifyPhoneStep = 0;
                    editPhoneNo.setEnabled(true);

                } else if ((uiKey == Const.FUNC_PHONE_EDIT) && (editPhoneStep == 1)) {
                    editPhoneStep = 0;

                    loutEditPhone.setVisibility(View.VISIBLE);
                    loutOtpCode.setVisibility(View.VISIBLE);
                    editNewPhoneNo.setEnabled(true);

                    loutNewOtpCode.setVisibility(View.GONE);
                    editNewOtpCode.setText("");
                    btnUpdate.setText(context.getResources().getString(R.string.title_Btn_Continuous));


                } else {
                    AppBase.initBack();
                }
                break;

            case R.id.img_Exit:
                AppBase.initClearPopback();
                break;

            case R.id.btn_Update:
                tvErrorMsg.setText("");
                switch (uiKey) {
                    case Const.FUNC_PHONE_VERIFY:
                        if (initcheckVerifyInput()) {
                            if (verifyPhoneStep == 0) {
                                initPhoneVerifyFunc();
                            } else {
                                if (initcheckVerifyInput2()) {
                                    initPhoneVerifyFunc();
                                }
                            }
                        }
                        break;
                    case Const.FUNC_EMAIL_EDIT:

                        if (initCheckEmailEditInput()) {

                            SecretQuestion secretQuestion = (SecretQuestion) spSecretQuestion.getSelectedItem();

                            Map<String, String> mapGetDetail = new HashMap<>();
                            mapGetDetail.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
                            mapGetDetail.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
                            mapGetDetail.put(ConstParam.PARAM_API_ACCOUNT_ID, VtcString.ACCOUNT_ID);
                            mapGetDetail.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);
                            mapGetDetail.put(ConstParam.PARAM_API_QUESTION_ID, String.valueOf(secretQuestion.getQuestionId()));
                            mapGetDetail.put(ConstParam.PARAM_API_ANSWER, String.valueOf(editSecretQuestion.getText()));
                            mapGetDetail.put(ConstParam.PARAM_API_CURRENT_EMAIL, String.valueOf(editCurrentEmail.getText()));
                            mapGetDetail.put(ConstParam.PARAM_API_NEW_EMAIL, String.valueOf(editNewEmail.getText()));


                            if (vtcConnection != null) {
                                vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_POST_UPDATE_EMAIL, RequestListener.API_CONNECTION_UPDATE_EMAIL + VtcHttpConnection.urlEncodeUTF8(mapGetDetail), true);
                            }
                        }
                        break;
                    case Const.FUNC_PHONE_EDIT:
                        if (sign.equals("")) {
                            if (initCheckPhoneEditInput()) {
                                editPhoneStep = 0;
                                initPhoneEditFunc();
                            }
                        } else {
                            if (initCheckPhoneEditInput2()) {
                                editPhoneStep = 1;
                                initPhoneEditFunc();
                            }
                        }

                        break;
                    case Const.FUNC_PHONE_REMOVE:

                        if (initCheckRemovePhoneInput()) {
                            Map<String, String> mapRemovePhone = new HashMap<>();
                            mapRemovePhone.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
                            mapRemovePhone.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
                            mapRemovePhone.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);
                            mapRemovePhone.put(ConstParam.PARAM_API_MOBILE, String.valueOf(editPhoneNo.getText()));
                            mapRemovePhone.put(ConstParam.PARAM_API_OTP, String.valueOf(editOtpCode.getText()));

                            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_REMOVE_PHONE, RequestListener.API_CONNECTION_REMOVE_PHONE + VtcHttpConnection.urlEncodeUTF8(mapRemovePhone), true);
                        }
                        break;
                }
                break;

        }
    }

    private boolean initcheckVerifyInput() {
        if (String.valueOf(editPhoneNo.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Phone_Null));
            return false;
        }
        return true;
    }

    private boolean initcheckVerifyInput2() {
        if (String.valueOf(editOtpCode.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Current_Otp_Null));
            return false;
        }
        return true;
    }


    private boolean initCheckEmailEditInput() {

        if (spSecretQuestion.getSelectedItemId() == 0) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Secret_Question_Null));
            return false;
        }

        if (String.valueOf(editSecretQuestion.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Answer_Null));
            return false;
        }

        if (String.valueOf(editCurrentEmail.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Current_Email_Null));
            return false;
        }

        if (String.valueOf(editNewEmail.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_New_Email_Null));
            return false;
        }
        return true;
    }

    private boolean initCheckPhoneEditInput() {
        if (String.valueOf(editPhoneNo.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Current_Phone_Null));
            return false;
        }

        if (String.valueOf(editNewPhoneNo.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_New_Phone_Null));
            return false;
        }

        if (String.valueOf(editOtpCode.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Current_Otp_Null2));
            return false;
        }

        return true;
    }

    private boolean initCheckPhoneEditInput2() {

        if (String.valueOf(editNewPhoneNo.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_New_Phone_Null));
            return false;
        }

        if (String.valueOf(editNewOtpCode.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_New_Otp_Null));
            return false;
        }

        return true;
    }

    private boolean initCheckRemovePhoneInput() {
        if (String.valueOf(editPhoneNo.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Current_Phone_Null));
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
        if (keyType == TypeActionConnection.TYPE_ACTION_GET_SECRET_QUESTION) {

        } else {
            tvErrorMsg.setText(msg);
        }
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_SECRET_QUESTION:
                if ((!info.equals("")) && (!info.equals("null"))) {
                    initQuestionHandle(info);
                }
                break;
            case TYPE_ACTION_POST_UPDATE_EMAIL:
                AppBase.initShowMessageAlert(getContext(), Const.ALERT_DONE, VtcString.LABEL_BTN_DONE, message);
                break;
            case TYPE_ACTION_REMOVE_PHONE:
                AppBase.initShowMessageAlert(getContext(), Const.ALERT_DONE, VtcString.LABEL_BTN_DONE, message);
                break;
            case TYPE_ACTION_VERIFY_PHONE_FIRST_STEP:
                editOtpCode.setText("");
                editPhoneNo.setEnabled(false);
                loutOtpCode.setVisibility(View.VISIBLE);
                tvOtpGuide.setVisibility(View.VISIBLE);
                btnUpdate.setText(context.getResources().getString(R.string.title_Btn_Update));
                sign = info;
                verifyPhoneStep = 1;
                break;
            case TYPE_ACTION_VERIFY_PHONE_SECOND_STEP:
                AppBase.initShowMessageAlert(getContext(), Const.ALERT_DONE, VtcString.LABEL_BTN_DONE, message);
                break;
            case TYPE_ACTION_EDIT_PHONE_FIRST_STEP:
                if ((!info.equals("")) && (!info.equals("null"))) {
                    sign = info;
                    editPhoneStep = 1;

                    loutEditPhone.setVisibility(View.GONE);
                    loutOtpCode.setVisibility(View.GONE);
                    editNewPhoneNo.setEnabled(false);

                    loutNewOtpCode.setVisibility(View.VISIBLE);
                    btnUpdate.setText(context.getResources().getString(R.string.title_Btn_Update));
                }
                break;
            case TYPE_ACTION_EDIT_PHONE_SECOND_STEP:
                AppBase.initShowMessageAlert(getContext(), Const.ALERT_DONE, VtcString.LABEL_BTN_DONE, message);
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
