package vtc.game.app.vcoin.vtcpay.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.strategy.intecom.vtc.tracking.SDKManager;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.common.VtcString;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.utils.Const;


/**
 * Created by ThuyChi on 9/16/2016.
 */
public class FMEditProfile extends Fragment implements View.OnClickListener {
    private View viewRoot;
    private Callback callback;
    private Dialog dlPassword;

    public FMEditProfile() {

    }

    @SuppressLint("ValidFragment")
    public FMEditProfile(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_profile_edit, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initController(view);
    }

    private void initController(View view) {


//        ImageView imgCancel = (ImageView) view.findViewById(R.id.img_Cancel);
//        imgCancel.setOnClickListener(this);

//        ImageView imgSave = (ImageView) view.findViewById(R.id.img_Save);
//        imgSave.setOnClickListener(this);
        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);

        RelativeLayout loutChange = (RelativeLayout) view.findViewById(R.id.lout_Change_Password);
        loutChange.setOnClickListener(this);

        TextView tvUserName = (TextView) view.findViewById(R.id.tv_Name);
        tvUserName.setText(VtcString.USER_NAME);

        TextView tvEmail = (TextView) view.findViewById(R.id.tv_Email);
        tvEmail.setText(SDKManager.userModel.getEmail());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lout_Change_Password:
                AppBase.callNewFragment(Const.UI_EDIT_PROFILE_CHANGE_PASSWORD, null, true);
                break;
            case R.id.img_Back:
                AppBase.initBack();
                break;
            default:
                break;
        }
    }
}
