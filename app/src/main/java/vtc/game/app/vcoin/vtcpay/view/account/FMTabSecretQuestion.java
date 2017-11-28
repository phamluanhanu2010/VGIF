package vtc.game.app.vcoin.vtcpay.view.account;


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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
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
 * Created by LuanPV on 3/6/2017.
 */

public class FMTabSecretQuestion extends Fragment implements View.OnClickListener, RequestListener, AdapterView.OnItemSelectedListener {
    private View viewRoot;
    private EditText editOtpCode, editPhoneNo, editAnswer;
    private TextView tvErrorMsg;
    private VtcConnection vtcConnection;
    private LinearLayout loutUpdate, loutEdit;
    private Spinner spSecretQuestion;
    private String mobile;


    public FMTabSecretQuestion() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_tab_secret_question, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        bundle = this.getArguments();
        if (bundle != null) {
            mobile = bundle.getString(ConstParam.BUNDLE_KEY_MOBILE);
        }

        vtcConnection = new VtcConnection(getContext(), this);

        TextView tvTitleView = (TextView) view.findViewById(R.id.tv_Titile_View);
        tvTitleView.setText(getResources().getString(R.string.title_Secu_Secret_Question));

        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);

        ImageView imgExit = (ImageView) view.findViewById(R.id.img_Exit);
        imgExit.setOnClickListener(this);

        Button btnUpdate = (Button) view.findViewById(R.id.btn_Update);
        btnUpdate.setOnClickListener(this);

        Button btnUpdateProfile = (Button) view.findViewById(R.id.btn_Update_Profile);
        btnUpdateProfile.setOnClickListener(this);
        btnUpdateProfile.setVisibility(View.GONE);

        TextView tvOtpGuide = (TextView) view.findViewById(R.id.tv_Otp_Guide);
        tvOtpGuide.setText(Html.fromHtml(VtcString.GUIDE_OTP));

        editOtpCode = (EditText) view.findViewById(R.id.edit_Otp_Code);
        editPhoneNo = (EditText) view.findViewById(R.id.edit_Input_Phone_No);
        editPhoneNo.setText(mobile);
        editAnswer = (EditText) view.findViewById(R.id.edit_Secret_Answer);

        loutEdit = (LinearLayout) view.findViewById(R.id.lout_Edit);
        loutUpdate = (LinearLayout) view.findViewById(R.id.lout_Update);
        loutEdit.setVisibility(View.GONE);
        loutUpdate.setVisibility(View.VISIBLE);

        spSecretQuestion = (Spinner) view.findViewById(R.id.sp_Secret_Question);


        tvErrorMsg = (TextView) view.findViewById(R.id.tv_Error_Message);
        TextView tvPhoneActiveGuide = (TextView) view.findViewById(R.id.tv_Phone_Active_Msg);

        if (Const.PHONE_NUMBER_IS_ACTIVED == 0) {
            loutEdit.setVisibility(View.GONE);
            loutUpdate.setVisibility(View.VISIBLE);
            if (Const.EMAIL_IS_ACTIVED == 0) {
                tvPhoneActiveGuide.setText(getResources().getString(R.string.label_Update_Profile_Msg));
            } else {
                tvPhoneActiveGuide.setText(getResources().getString(R.string.label_Phone_Active_Msg));
            }

            if (Const.ACCOUNT_VERIFY_PHONE_NO == Const.HIDE_VALUE) {
                btnUpdateProfile.setVisibility(View.GONE);
            } else {
                btnUpdateProfile.setVisibility(View.VISIBLE);
            }
        } else {
            btnUpdateProfile.setVisibility(View.VISIBLE);
            loutEdit.setVisibility(View.VISIBLE);
            loutUpdate.setVisibility(View.GONE);
            initGetSecretQuestion();
        }
    }

    private void initGetSecretQuestion() {
        if (vtcConnection != null) {
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_SECRET_QUESTION, RequestListener.API_CONNECTION_GET_SECRET_QUESTION, true);
        }
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
                if (initCheckInput()) {
                    initUpdateHandle();
                }
                break;
            case R.id.btn_Update_Profile:
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

    private boolean initCheckInput() {
        if (String.valueOf(editPhoneNo.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Current_Phone_Null));
            return false;
        }

        if (String.valueOf(editOtpCode.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Current_Otp_Null));
            return false;
        }

        if (spSecretQuestion.getSelectedItemId() == 0) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Secret_Question_Null));
            return false;
        }

        if (String.valueOf(editAnswer.getText()).equals("")) {
            tvErrorMsg.setText(getResources().getString(R.string.error_Answer_Null));
            return false;
        }

        return true;
    }

    private void initUpdateHandle() {

        SecretQuestion secretQuestion = (SecretQuestion) spSecretQuestion.getSelectedItem();
        tvErrorMsg.setText("");
        Map<String, String> mapUpdateQuestion = new HashMap<>();
        mapUpdateQuestion.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapUpdateQuestion.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
        mapUpdateQuestion.put(ConstParam.PARAM_API_CLIENT_IP, VtcString.IP_ADDRESS);
        mapUpdateQuestion.put(ConstParam.PARAM_API_MOBILE, String.valueOf(editPhoneNo.getText()));
        mapUpdateQuestion.put(ConstParam.PARAM_API_NEW_ANSWER, String.valueOf(editAnswer.getText()));

        mapUpdateQuestion.put(ConstParam.PARAM_API_NEW_QUESTION_ID, String.valueOf(secretQuestion.getQuestionId()));
        mapUpdateQuestion.put(ConstParam.PARAM_API_SECURE_CODE, String.valueOf(editOtpCode.getText()));
        mapUpdateQuestion.put(ConstParam.PARAM_API_TYPE, "1");

        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_UPDATE_QUESTION_BY_OTP, RequestListener.API_CONNECTION_UPDATE_QUESTION_BY_OTP + VtcHttpConnection.urlEncodeUTF8(mapUpdateQuestion), true);
    }

    private void initQuestionHandle(String data) {
        try {
            JSONArray jsonArrayData = new JSONArray(data);
            ArrayList<SecretQuestion> question = new ArrayList<>();

            question.add(new SecretQuestion("Câu hỏi bảo mật mới", 0));
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

    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {
        if (keyType == TypeActionConnection.TYPE_ACTION_GET_SECRET_QUESTION) {

        } else {
            tvErrorMsg.setText(msg);
        }
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        if (keyType == TypeActionConnection.TYPE_ACTION_GET_SECRET_QUESTION) {
            if ((!info.equals("")) && (!info.equals("null"))) {
                initQuestionHandle(info);
            }
        } else {
            AppBase.initShowMessageAlert(getContext(), Const.ALERT_DONE, VtcString.LABEL_BTN_DONE, message);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
