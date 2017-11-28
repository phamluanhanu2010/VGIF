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
import android.widget.ListView;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.adapter.AdtNoti;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;


/**
 * Created by ThuyChi on 9/16/2016.
 */
public class FMChangePassword extends Fragment implements View.OnClickListener {
    private View viewRoot;
    private Callback callback;
    private ListView listViewEmail;
    private AdtNoti mAdapter;
    private Dialog dlPassword;

    public FMChangePassword() {

    }

    @SuppressLint("ValidFragment")
    public FMChangePassword(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_profile_change_password, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initController(view);
    }

    private void initController(View view) {
        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_Back:
                AppBase.initBack();
                break;
            default:
                break;
        }
    }
}
