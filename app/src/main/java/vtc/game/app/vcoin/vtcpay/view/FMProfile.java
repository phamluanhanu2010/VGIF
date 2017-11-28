package vtc.game.app.vcoin.vtcpay.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.strategy.intecom.vtc.tracking.SDKManager;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.common.VtcString;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;

/**
 * Created by ThuyChi on 10/5/2016.
 */
public class FMProfile extends Fragment implements View.OnClickListener {
    private View viewRoot;
    private TextView tvUserName;
    private TextView tvEmail;
    private Callback callback;
    private ImageView imgAvatar;

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

        LinearLayout loutHistory = (LinearLayout) view.findViewById(R.id.lout_Profile_History);
        loutHistory.setOnClickListener(this);

        LinearLayout loutGameList = (LinearLayout) view.findViewById(R.id.lout_Profile_Game_List);
        loutGameList.setOnClickListener(this);

        LinearLayout loutSupport = (LinearLayout) view.findViewById(R.id.lout_Profile_Support);
        loutSupport.setOnClickListener(this);

        LinearLayout loutIntro = (LinearLayout) view.findViewById(R.id.lout_Profile_Intro);
        loutIntro.setOnClickListener(this);

        LinearLayout loutExit = (LinearLayout) view.findViewById(R.id.lout_Profile_Exit);
        loutExit.setOnClickListener(this);

        tvUserName = (TextView) view.findViewById(R.id.tv_Username);

        tvEmail = (TextView) view.findViewById(R.id.tv_Email);

    }

    public void callBackMain() {
        AppBase.showLog("callBackMain--------------------------FMProfile");
        initController();
    }

    private void initController() {

        tvUserName.setText(SDKManager.userModel.getAccountName());
        tvEmail.setText(SDKManager.userModel.getEmail());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_Edit:

                break;
            case R.id.lout_Profile_History:
                Bundle bundleHis = new Bundle();
                bundleHis.putString(ConstParam.BUNDLE_KEY_SPINNING_HISTORY, VtcString.SPINNING_HISTORY_ACCOUNT);
                AppBase.callNewFragment(Const.UI_HISTORY, bundleHis, true);
                break;
            case R.id.lout_Profile_Support:
                Bundle bundle = new Bundle();
                bundle.putString(ConstParam.BUNDLE_KEY_WEBVIEW_LINK, "https://vtcgame.vn/");
                bundle.putString(ConstParam.BUNDLE_KEY_WEBVIEW_TITILE, getResources().getString(R.string.title_View_SUPPORT));
                AppBase.callNewFragment(Const.UI_GUIDE, bundle, true);
                break;
            case R.id.lout_Profile_Intro:
                Bundle bundleIntro = new Bundle();
                bundleIntro.putString(ConstParam.BUNDLE_KEY_WEBVIEW_LINK, "https://vtcgame.vn/");
                bundleIntro.putString(ConstParam.BUNDLE_KEY_WEBVIEW_TITILE, getResources().getString(R.string.title_View_INTRO));
                AppBase.callNewFragment(Const.UI_GUIDE, bundleIntro, true);
                break;
            case R.id.lout_Profile_Exit:
                callback.onHandlerCallBack(0);
                break;
            default:
                break;
        }
    }
}
