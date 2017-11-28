package vtc.game.app.vcoin.vtcpay.view.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
 * Created by ThuyChi on 10/5/2016.
 */
public class FMAccountSecurity extends Fragment implements View.OnClickListener, RequestListener {
    private View viewRoot;
    private VtcConnection vtcConnection;
    private String mobile = "";

    public FMAccountSecurity() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_account_security, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vtcConnection = new VtcConnection(getContext(), this);

        TextView tvTitleView = (TextView) view.findViewById(R.id.tv_Titile_View);
        tvTitleView.setText(getResources().getString(R.string.title_View_Account_Security));

        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);

        ImageView imgExit = (ImageView) view.findViewById(R.id.img_Exit);
        imgExit.setOnClickListener(this);

        RelativeLayout loutSmsPlus = (RelativeLayout) view.findViewById(R.id.lout_SMS_Plus);
        loutSmsPlus.setOnClickListener(this);
        loutSmsPlus.setVisibility(View.GONE);

        RelativeLayout loutLoginSecurity = (RelativeLayout) view.findViewById(R.id.lout_Login_Security);
        loutLoginSecurity.setOnClickListener(this);
        loutLoginSecurity.setVisibility(View.GONE);

        RelativeLayout loutChangePass = (RelativeLayout) view.findViewById(R.id.lout_Change_Password);
        loutChangePass.setOnClickListener(this);
        loutChangePass.setVisibility(View.GONE);

        RelativeLayout loutFreezeVcoin = (RelativeLayout) view.findViewById(R.id.lout_Freeze_Vcoin);
        loutFreezeVcoin.setOnClickListener(this);
        loutFreezeVcoin.setVisibility(View.GONE);

        RelativeLayout loutSecretQuestion = (RelativeLayout) view.findViewById(R.id.lout_Secret_Question);
        loutSecretQuestion.setOnClickListener(this);
        loutSecretQuestion.setVisibility(View.GONE);


        initLayoutStatus(loutSmsPlus, Const.TAB_SMS_PLUS);
        initLayoutStatus(loutLoginSecurity, Const.TAB_LOGIN_SECURITY);
        initLayoutStatus(loutChangePass, Const.TAB_EDIT_PASSWORD);
        initLayoutStatus(loutFreezeVcoin, Const.TAB_FREEZE_VCOIN);
        initLayoutStatus(loutSecretQuestion, Const.TAB_EDIT_SECRET_QUESTION);

        initGetAccountDetail();
    }

    private void initLayoutStatus(RelativeLayout lout, int status) {
        if (status == Const.HIDE_VALUE) {
            lout.setVisibility(View.GONE);
        } else {
            lout.setVisibility(View.VISIBLE);
        }
    }

    private void initGetAccountDetail() {
        Map<String, String> mapGetDetail = new HashMap<>();
        mapGetDetail.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapGetDetail.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);

        if (vtcConnection != null) {
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_ACCOUNT_DETAIL, RequestListener.API_CONNECTION_GET_DETAIL + VtcHttpConnection.urlEncodeUTF8(mapGetDetail), false, false);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_Back:
                AppBase.initBack();
                break;
            case R.id.img_Exit:
                AppBase.initClearPopback();
                break;
            case R.id.lout_Secret_Question:
                Bundle bundle = new Bundle();
                bundle.putString(ConstParam.BUNDLE_KEY_MOBILE, mobile);
                AppBase.callNewFragment(Const.UI_TAB_SECRET_QUESTION, bundle, true);
                break;
            case R.id.lout_Freeze_Vcoin:
                AppBase.callNewFragment(Const.UI_TAB_FREEZE_VCOIN, null, true);
                break;
            case R.id.lout_Change_Password:
                AppBase.callNewFragment(Const.UI_TAB_CHANGE_PASSWORD, null, true);
                break;
            case R.id.lout_Login_Security:
                AppBase.callNewFragment(Const.UI_TAB_LOGIN_SECURITY, null, true);
                break;
            case R.id.lout_SMS_Plus:
                AppBase.callNewFragment(Const.UI_TAB_SMS_PLUS, null, true);
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
                try {
                    JSONObject jsonDetail = new JSONObject(info);
                    if (jsonDetail != null) {
                        int phoneVerify = jsonDetail.optInt(ConstParam.VERIFY_TYPE);
                        if (phoneVerify == 3 || phoneVerify == 4) {
                            Const.PHONE_NUMBER_IS_ACTIVED = 1;
                        } else {
                            Const.PHONE_NUMBER_IS_ACTIVED = 0;
                        }

                        String accountEmail = AppBase.initCheckString(jsonDetail, ConstParam.ACCOUNT_EMAIL);
                        if ((accountEmail != null) && (!accountEmail.equals("")) && (!accountEmail.equals("null"))) {
                            Const.EMAIL_IS_ACTIVED = 1;
                        } else {
                            Const.EMAIL_IS_ACTIVED = 0;
                        }

                        mobile = jsonDetail.optString(ConstParam.MOBILE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
