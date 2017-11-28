package vtc.game.app.vcoin.vtcpay.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.adapter.AdtGameSpinning;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.common.VtcString;
import vtc.game.app.vcoin.vtcpay.connection.VtcConnection;
import vtc.game.app.vcoin.vtcpay.connection.VtcHttpConnection;
import vtc.game.app.vcoin.vtcpay.enums.TypeActionConnection;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.interfaces.RequestListener;
import vtc.game.app.vcoin.vtcpay.model.GameListItem;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;

/**
 * Created by ThuyChi on 10/5/2016.
 */
public class FMSpinning extends Fragment implements View.OnClickListener, RequestListener {
    private View viewRoot;
    private int startAngle = 0;
    private RelativeLayout loutCircle;
    private ProgressBar progressBar;
    private TextView tvTime, tvSeconds, tvMinutes, tvHours;
    private int pStatus = 600000;
    private Handler handler = new Handler();
    private Dialog dlReward, dlRewardItem;
    private VtcConnection vtcConnection;
    private long timeRemain;
    private AdtGameSpinning mAdapter;
    private RecyclerView rclViewItem;
    private TextView tvSpinningNoti;
    private ImageView rw1, rw2, rw3, rw4, rw5, rw6;
    private String gameID;
    private String rotationID;
    private RelativeLayout btnSpinning;
    private JSONArray jsonArrayDataItem;
    private ImageView imgDLAvatar;
    private TextView tvDLGameName;
    private TextView tvDLRewardType;
    private TextView tvDLRewardTitle;
    private TextView tvDLRewardDescription;
    private String gameName;
    private Callback callback;
    private String responseResurlt;
    private RelativeLayout loutCountDown;
    private CountDownTimer mCountDownTimer;
    private ImageView imgArrow;
    private LinearLayout loutWrap;
    private long maxTime = 0;

    public FMSpinning() {

    }

    @SuppressLint("ValidFragment")
    public FMSpinning(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_spinning, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppBase.showLog("onViewCreated - FMSpinning");

        vtcConnection = new VtcConnection(getContext(), this);

        rw1 = (ImageView) view.findViewById(R.id.img_Reward_1);
        rw1.setOnClickListener(this);

        rw2 = (ImageView) view.findViewById(R.id.img_Reward_2);
        rw2.setOnClickListener(this);

        rw3 = (ImageView) view.findViewById(R.id.img_Reward_3);
        rw3.setOnClickListener(this);

        rw4 = (ImageView) view.findViewById(R.id.img_Reward_4);
        rw4.setOnClickListener(this);

        rw5 = (ImageView) view.findViewById(R.id.img_Reward_5);
        rw5.setOnClickListener(this);

        rw6 = (ImageView) view.findViewById(R.id.img_Reward_6);
        rw6.setOnClickListener(this);

        loutCountDown = (RelativeLayout) view.findViewById(R.id.lout_CountDown);
        loutCountDown.setVisibility(View.GONE);

        loutWrap = (LinearLayout) view.findViewById(R.id.lout_Spinning_wrap);

        btnSpinning = (RelativeLayout) view.findViewById(R.id.btn_Spinning);
        btnSpinning.setOnClickListener(this);

        imgArrow = (ImageView) view.findViewById(R.id.img_arrow);

        tvSpinningNoti = (TextView) view.findViewById(R.id.tv_Spinning_Noti);

        rclViewItem = (RecyclerView) view.findViewById(R.id.rclView_Item);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rclViewItem.setLayoutManager(layoutManager);
        rclViewItem.setItemAnimator(new DefaultItemAnimator());

        loutCircle = (RelativeLayout) view.findViewById(R.id.lout_circle);
        loutCircle.setOnClickListener(this);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        tvTime = (TextView) view.findViewById(R.id.tv_Time);

        tvHours = (TextView) view.findViewById(R.id.tv_hours);
        tvMinutes = (TextView) view.findViewById(R.id.tv_minutes);
        tvSeconds = (TextView) view.findViewById(R.id.tv_Seconds);

        dlReward = new Dialog(getContext());
        dlReward.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlReward.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dlReward.setContentView(R.layout.dl_reward);
        dlReward.setCancelable(false);

        dlRewardItem = new Dialog(getContext());
        dlRewardItem.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlRewardItem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dlRewardItem.setContentView(R.layout.dl_reward_item);
        dlRewardItem.setCancelable(true);

        imgDLAvatar = (ImageView) dlRewardItem.findViewById(R.id.img_Avatar_Game);
        tvDLGameName = (TextView) dlRewardItem.findViewById(R.id.tv_Game_Name);
        tvDLRewardType = (TextView) dlRewardItem.findViewById(R.id.tv_Reward_Type);
        tvDLRewardDescription = (TextView) dlRewardItem.findViewById(R.id.tv_Reward_Description);
        tvDLRewardTitle = (TextView) dlRewardItem.findViewById(R.id.tv_Titile);
        tvDLRewardTitle.setVisibility(View.GONE);

        ImageView imgClosePopup = (ImageView) dlRewardItem.findViewById(R.id.img_Close_Popup);
        imgClosePopup.setOnClickListener(this);

        LinearLayout loutWrap = (LinearLayout) dlReward.findViewById(R.id.lout_Wrap);
        loutWrap.setOnClickListener(this);

        ImageView imgLeftArrow = (ImageView) view.findViewById(R.id.img_Left_Arrow);
        imgLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rclViewItem.smoothScrollBy(-Const.RECYCLERVIEW_SMOOTH, 0);
            }
        });

        ImageView imgRightArrow = (ImageView) view.findViewById(R.id.img_Right_Arrow);
        imgRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rclViewItem.smoothScrollBy(Const.RECYCLERVIEW_SMOOTH, 0);
            }
        });

        callBackMain();
    }

    public void callBackMain() {
        if (rw1 != null) {
            rw1.setImageBitmap(null);
            rw1.setVisibility(View.GONE);
        }
        if (rw2 != null) {
            rw2.setImageBitmap(null);
            rw2.setVisibility(View.GONE);
        }
        if (rw3 != null) {
            rw3.setImageBitmap(null);
            rw3.setVisibility(View.GONE);
        }
        if (rw4 != null) {
            rw4.setImageBitmap(null);
            rw4.setVisibility(View.GONE);
        }
        if (rw5 != null) {
            rw5.setImageBitmap(null);
            rw5.setVisibility(View.GONE);
        }
        if (rw6 != null) {
            rw6.setImageBitmap(null);
            rw6.setVisibility(View.GONE);
        }

        gameID = null;
        Animation rotation = new RotateAnimation(0, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        loutCircle.startAnimation(rotation);
        AppBase.showLog("callBackMain--------------------------FMSpinning");
        initGetSpinningInfo();
    }

    private void initGetSpinningInfo() {
        Map<String, String> mapSpinningInfo = new HashMap<>();
        mapSpinningInfo.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
        mapSpinningInfo.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapSpinningInfo.put(ConstParam.PARAM_REQUEST_ACCOUNT_ID, VtcString.ACCOUNT_ID);
        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_SPINNING_INFO, RequestListener.API_CONNECTION_GET_SPINNING_INFO + VtcHttpConnection.urlEncodeUTF8(mapSpinningInfo), false);
    }

    private void initData(JSONArray data) {
        AppBase.showLog("Data length: " + data.length());
        try {
            final List<GameListItem> lst = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                GameListItem item = new GameListItem();
                item.setGameName(AppBase.checkStringNull(data.getJSONObject(i).optString(ConstParam.NAME_GAME)));
                item.setGameID(AppBase.checkStringNull(data.getJSONObject(i).optString(ConstParam.GAME_ID)));
                item.setPictureUrl(AppBase.checkStringNull(data.getJSONObject(i).optString(ConstParam.PICTURE_GAME)));
                AppBase.showLog("Data length: " + data.getJSONObject(i).optString(ConstParam.NAME_GAME));
                lst.add(item);
            }

            mAdapter = new AdtGameSpinning(lst);
            mAdapter.setOnClickListener(new AdtGameSpinning.onClickListener() {
                @Override
                public void onClickItem(GameListItem item) {
                    AppBase.showLog("Callback Game spinning: " + item.getGameID());
                    gameName = item.getGameName();
                    initGame(item.getGameID());
                }
            });
            rclViewItem.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initGame(String gameID) {
        Map<String, String> mapChooseGame = new HashMap<String, String>();
        mapChooseGame.put(ConstParam.PARAM_REQUEST_GAME_ID, gameID);
        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_CHOOSE_GAME, RequestListener.API_CONNECTION_GET_CHOOSE_GAME + VtcHttpConnection.urlEncodeUTF8(mapChooseGame), false);

    }

    private void initGetResult() {
        Map<String, String> mapResult = new HashMap<String, String>();
        mapResult.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
        mapResult.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapResult.put(ConstParam.PARAM_REQUEST_ACCOUNT_ID, VtcString.ACCOUNT_ID);
        mapResult.put(ConstParam.PARAM_REQUEST_ROTATION_ID, rotationID);
        mapResult.put(ConstParam.PARAM_REQUEST_GAME_ID, gameID);
        mapResult.put(ConstParam.PARAM_REQUEST_GAME_NAME, gameName);
        mapResult.put(ConstParam.PARAM_REQUEST_DEVICE_TOKEN, VtcString.DEVICE_TOKEN);
        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_SPINNING_RESURL, RequestListener.API_CONNECTION_GET_SPINNING_RESULT + VtcHttpConnection.urlEncodeUTF8(mapResult), null, true, false, "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Spinning:
                if (timeRemain < 0) {
                    callback.onHandlerCallBack(0);
                } else if (timeRemain == 0) {
                    if (gameID != null) {
                        initGetResult();
                        btnSpinning.setClickable(false);
                        loutWrap.setClickable(true);
                    } else {
                        Toast.makeText(getContext(), VtcString.SPINNING_ERROR_CHOOSE_GAME, Toast.LENGTH_SHORT).show();
                    }
                } else if (timeRemain > 0) {
                    if (gameID != null) {
                        AppBase.showLog("game ID: " + gameID);
                    } else {
                        AppBase.showLog("game ID is null ");
                    }
                }
                break;
            case R.id.lout_Wrap:
                dlReward.dismiss();
                break;
            case R.id.img_Close_Popup:
                dlRewardItem.dismiss();
                break;
            case R.id.img_Reward_1:
                if (jsonArrayDataItem != null) {
                    try {
                        for (int i = 0; i < jsonArrayDataItem.length(); i++) {

                            if (jsonArrayDataItem.getJSONObject(i).optInt(ConstParam.TYPE_REWARD) == 1) {
                                AppBase.setImageWithCorner(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.PICTURE_REWARD), imgDLAvatar);
                                tvDLGameName.setText(gameName);
                                tvDLRewardType.setText(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.TYPE_REWARD_STRING));
                                tvDLRewardDescription.setText(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.REWARD_DESCRIPTION));
                                dlRewardItem.show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.img_Reward_2:
                if (jsonArrayDataItem != null) {
                    try {
                        for (int i = 0; i < jsonArrayDataItem.length(); i++) {

                            if (jsonArrayDataItem.getJSONObject(i).optInt(ConstParam.TYPE_REWARD) == 2) {
                                AppBase.setImageWithCorner(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.PICTURE_REWARD), imgDLAvatar);
                                tvDLGameName.setText(gameName);
                                tvDLRewardType.setText(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.TYPE_REWARD_STRING));
                                tvDLRewardDescription.setText(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.REWARD_DESCRIPTION));
                                dlRewardItem.show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.img_Reward_3:
                if (jsonArrayDataItem != null) {
                    try {
                        for (int i = 0; i < jsonArrayDataItem.length(); i++) {

                            if (jsonArrayDataItem.getJSONObject(i).optInt(ConstParam.TYPE_REWARD) == 3) {
                                AppBase.setImageWithCorner(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.PICTURE_REWARD), imgDLAvatar);
                                tvDLGameName.setText(gameName);
                                tvDLRewardType.setText(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.TYPE_REWARD_STRING));
                                tvDLRewardDescription.setText(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.REWARD_DESCRIPTION));
                                dlRewardItem.show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.img_Reward_4:
                if (jsonArrayDataItem != null) {
                    try {
                        for (int i = 0; i < jsonArrayDataItem.length(); i++) {

                            if (jsonArrayDataItem.getJSONObject(i).optInt(ConstParam.TYPE_REWARD) == 4) {
                                AppBase.setImageWithCorner(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.PICTURE_REWARD), imgDLAvatar);
                                tvDLGameName.setText(gameName);
                                tvDLRewardType.setText(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.TYPE_REWARD_STRING));
                                tvDLRewardDescription.setText(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.REWARD_DESCRIPTION));
                                dlRewardItem.show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.img_Reward_5:
                if (jsonArrayDataItem != null) {
                    try {
                        for (int i = 0; i < jsonArrayDataItem.length(); i++) {

                            if (jsonArrayDataItem.getJSONObject(i).optInt(ConstParam.TYPE_REWARD) == 5) {
                                AppBase.setImageWithCorner(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.PICTURE_REWARD), imgDLAvatar);
                                tvDLGameName.setText(gameName);
                                tvDLRewardType.setText(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.TYPE_REWARD_STRING));
                                tvDLRewardDescription.setText(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.REWARD_DESCRIPTION));
                                dlRewardItem.show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.img_Reward_6:
                if (jsonArrayDataItem != null) {
                    try {
                        for (int i = 0; i < jsonArrayDataItem.length(); i++) {
                            if (jsonArrayDataItem.getJSONObject(i).optInt(ConstParam.TYPE_REWARD) == 6) {
                                AppBase.setImageWithCorner(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.PICTURE_REWARD), imgDLAvatar);
                                tvDLGameName.setText(gameName);
                                tvDLRewardType.setText(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.TYPE_REWARD_STRING));
                                tvDLRewardDescription.setText(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.REWARD_DESCRIPTION));
                                dlRewardItem.show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void animatRotate(int angle) {
        AppBase.showLog("Random Number: " + String.valueOf(angle));

        Animator.AnimatorListener animatorListener
                = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                AppBase.showLog("onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                AppBase.showLog("onAnimationEnd");
                initAnimationEndHandle();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                AppBase.showLog("onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                AppBase.showLog("onAnimationRepeat");
            }
        };

        ObjectAnimator rotation = ObjectAnimator.ofFloat(loutCircle, "rotation", (float) startAngle, angle + 5400);
        rotation.setDuration(10000);
        rotation.addListener(animatorListener);
        rotation.setInterpolator(new FastOutSlowInInterpolator());
        rotation.start();
        startAngle = angle;
    }

    private int initSetValue(int n) {
        int i = 0;
        Random randomNum = new Random();
        switch (n) {
            case 2:
                i = randomNum.nextInt(25 - 5) + 5;
                return i;
            case 1:
                i = randomNum.nextInt(85 - 35) + 35;
                return i;
            case 3:
                i = randomNum.nextInt(325 - 275) + 275;
                return i;
            case 4:
                i = randomNum.nextInt(265 - 215) + 215;
                return i;
            case 5:
                i = randomNum.nextInt(205 - 155) + 155;
                return i;
            case 6:
                i = randomNum.nextInt(145 - 95) + 95;
                return i;
        }
        return -1;
    }

    private void initCountDown(final int timeRemain, long maxTime) {
        AppBase.showLog("timeRemain: " + String.valueOf(timeRemain));
        loutCircle.getBackground().setAlpha(50);
        btnSpinning.setVisibility(View.GONE);
        imgArrow.setVisibility(View.GONE);
        loutCountDown.setVisibility(View.VISIBLE);

        AppBase.showLog(String.valueOf(timeRemain));
        if (mCountDownTimer != null)
            mCountDownTimer.cancel();
        createCountDownTimer(timeRemain, maxTime);
    }

    /**
     * call create a countdown timer method
     *
     * @param time    remian time to get a spin
     * @param maxTime max time to get a spin
     */
    private void createCountDownTimer(final int time, long maxTime) {
        final int maxTimeSpinning = (int) maxTime;
        progressBar.setSecondaryProgress(100); // Secondary Progress
        progressBar.setMax(maxTimeSpinning); // Maximum Progress
        progressBar.setProgress(maxTimeSpinning - time);   // Main Progress

        mCountDownTimer = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int) ((maxTimeSpinning - time) + (time - millisUntilFinished)));

                long count = millisUntilFinished;
                long hours = count / (60 * 60 * 1000);
                count -= hours * (60 * 60 * 1000);
                long minutes = count / (60 * 1000);
                count -= minutes * (60 * 1000);
                long seconds = count / 1000;

                tvHours.setText("" + String.format("%02d:", hours));
                tvMinutes.setText("" + String.format("%02d:", minutes));
                tvSeconds.setText("" + String.format("%02d", seconds));
            }

            public void onFinish() {
                timeRemain = 0;
                loutCircle.getBackground().setAlpha(255);
                btnSpinning.setVisibility(View.VISIBLE);
                imgArrow.setVisibility(View.VISIBLE);
                loutCountDown.setVisibility(View.GONE);
                tvSpinningNoti.setText(getResources().getString(R.string.title_Spinning_Quay));
                tvSpinningNoti.setTextSize(22);
            }

        }.start();
    }

    private void initFillRewardItem() {
        if (jsonArrayDataItem.length() > 0) {
            try {
                rw1.setVisibility(View.VISIBLE);
                rw2.setVisibility(View.VISIBLE);
                rw3.setVisibility(View.VISIBLE);
                rw4.setVisibility(View.VISIBLE);
                rw5.setVisibility(View.VISIBLE);
                rw6.setVisibility(View.VISIBLE);

                for (int i = 0; i < jsonArrayDataItem.length(); i++) {
                    int typeReward = jsonArrayDataItem.getJSONObject(i).optInt(ConstParam.TYPE_REWARD);
                    switch (typeReward) {
                        case 1:
                            AppBase.setImageWithCornerItemSpinning(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.PICTURE_REWARD), rw1);
                            break;
                        case 2:
                            AppBase.setImageWithCornerItemSpinning(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.PICTURE_REWARD), rw2);
                            break;
                        case 3:
                            AppBase.setImageWithCornerItemSpinning(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.PICTURE_REWARD), rw3);
                            break;
                        case 4:
                            AppBase.setImageWithCornerItemSpinning(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.PICTURE_REWARD), rw4);
                            break;
                        case 5:
                            AppBase.setImageWithCornerItemSpinning(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.PICTURE_REWARD), rw5);
                            break;
                        case 6:
                            AppBase.setImageWithCornerItemSpinning(jsonArrayDataItem.getJSONObject(i).optString(ConstParam.PICTURE_REWARD), rw6);
                            break;

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initAnimationEndHandle() {
        if (responseResurlt != null) {
            try {
                ImageView imgRsAvatar = (ImageView) dlReward.findViewById(R.id.img_Avatar);
                TextView tvRsGameName = (TextView) dlReward.findViewById(R.id.tv_Game_Name);
                TextView tvRsRewardType = (TextView) dlReward.findViewById(R.id.tv_Reward_Type);
                TextView tvRsRewardSerial = (TextView) dlReward.findViewById(R.id.tv_Reward_Serial);
                TextView tvRsRewardTitile = (TextView) dlReward.findViewById(R.id.tv_Titile);
                TextView tvRsRewardSub = (TextView) dlReward.findViewById(R.id.tv_Des);
                TextView tvDLRewardPinCode = (TextView) dlReward.findViewById(R.id.tv_Reward_Pin_Code);
                JSONObject jsonObjectData = new JSONObject(responseResurlt);
                if (jsonObjectData.optInt(ConstParam.TYPE_REWARD) != 1) {
                    imgRsAvatar.setVisibility(View.VISIBLE);
                    tvRsGameName.setVisibility(View.VISIBLE);
                    tvRsRewardType.setVisibility(View.VISIBLE);
                    tvRsRewardSerial.setVisibility(View.VISIBLE);
                    tvRsRewardTitile.setVisibility(View.VISIBLE);
                    tvRsRewardSub.setVisibility(View.VISIBLE);

                    tvRsRewardSub.setText(getResources().getString(R.string.spinning_Reward_String));
                    tvRsRewardSub.setTextSize(16);
                    AppBase.setImageWithCorner(jsonObjectData.optString(ConstParam.PICTURE_REWARD), imgRsAvatar);
                    tvRsGameName.setText(jsonObjectData.optString(ConstParam.NAME_GAME));
                    tvRsRewardType.setText(jsonObjectData.optString(ConstParam.TYPE_REWARD_STRING));

                    if (jsonObjectData.optInt(ConstParam.ROOT_TYPE_REWARD) == 0) {
                        tvDLRewardPinCode.setVisibility(View.GONE);
                        tvRsRewardSerial.setText(jsonObjectData.optString(ConstParam.CODE_SERIAL));
                        dlReward.show();
                    } else if (jsonObjectData.optInt(ConstParam.ROOT_TYPE_REWARD) == 1) {
                        tvDLRewardPinCode.setVisibility(View.VISIBLE);
                        tvDLRewardPinCode.setText("P/N: " + jsonObjectData.optString(ConstParam.CODE_PIN));
                        tvRsRewardSerial.setText("Serial: " + jsonObjectData.optString(ConstParam.CODE_SERIAL));
                        dlReward.show();
                    }
                } else {
                    tvDLRewardPinCode.setVisibility(View.GONE);
                    imgRsAvatar.setVisibility(View.GONE);
                    tvRsGameName.setVisibility(View.GONE);
                    tvRsRewardType.setVisibility(View.GONE);
                    tvRsRewardSerial.setVisibility(View.GONE);
                    tvRsRewardTitile.setVisibility(View.GONE);
                    tvRsRewardSub.setVisibility(View.VISIBLE);
                    tvRsRewardSub.setText(jsonObjectData.optString(ConstParam.TYPE_REWARD_STRING));
                    tvRsRewardSub.setTextSize(18);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.FILL_PARENT);
                    params.weight = 1.0f;
                    params.gravity = Gravity.CENTER;

                    tvRsRewardSub.setLayoutParams(params);
                    dlReward.show();
                }
//                callBackMain();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        btnSpinning.setClickable(true);
        loutWrap.setClickable(false);
        timeRemain = maxTime;
        initCountDown((int) timeRemain, maxTime);
    }

    @Override
    public void onResume() {
        super.onResume();
        AppBase.showLog("onResume - FMSpinning");
    }

    @Override
    public void onPause() {
        super.onPause();
        AppBase.showLog("onPause - FMSpinning");
    }

    @Override
    public void onStop() {
        super.onStop();
        AppBase.showLog("onStop - FMSpinning");
    }

    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_SPINNING_RESURL:
                switch (errorcode) {
                    case "201":
                        initGetSpinningInfo();
                        break;
                    case "204":
                        initGetSpinningInfo();
                        break;
                    case "205":
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        initGetSpinningInfo();
                        break;
                }
                break;
        }
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_SPINNING_INFO:
                AppBase.showLog("TYPE_ACTION_GET_SPINNING_INFO: " + info);
                try {
                    JSONObject jsonObjectData = new JSONObject(info);
                    JSONArray jsonArrayData = null;
                    String std = jsonObjectData.optString(ConstParam.GAMES);

                    jsonArrayData = new JSONArray(std);

                    timeRemain = jsonObjectData.optLong(ConstParam.REMAIN_TIME_TURN);


                    if (jsonObjectData.optLong(ConstParam.MAX_TIME) != 0) {
                        maxTime = jsonObjectData.optLong(ConstParam.MAX_TIME);
                    } else {
                        maxTime = Const.MAX_TIME_SPINNING;
                    }
                    AppBase.showLog("MAX TIME: " + maxTime);

                    if (timeRemain < 0) {
                        btnSpinning.setVisibility(View.VISIBLE);
                        imgArrow.setVisibility(View.VISIBLE);
                        loutCountDown.setVisibility(View.GONE);
                        tvSpinningNoti.setText(getResources().getString(R.string.title_Spinning_Login));
                        tvSpinningNoti.setTextSize(10);
                    } else if (timeRemain == 0) {
                        loutCircle.getBackground().setAlpha(255);
                        btnSpinning.setVisibility(View.VISIBLE);
                        imgArrow.setVisibility(View.VISIBLE);
                        loutCountDown.setVisibility(View.GONE);
                        tvSpinningNoti.setText(getResources().getString(R.string.title_Spinning_Quay));
                        tvSpinningNoti.setTextSize(22);
                    } else if (timeRemain > 0) {
                        initCountDown((int) timeRemain, maxTime);
                    }
                    AppBase.showLog("timeRemain:" + timeRemain);
                    initData(jsonArrayData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case TYPE_ACTION_GET_CHOOSE_GAME:
                AppBase.showLog("TYPE_ACTION_GET_CHOOSE_GAME: " + info);
                try {
                    JSONObject jsonObjectData = new JSONObject(info);
                    jsonArrayDataItem = new JSONArray(jsonObjectData.optString(ConstParam.REWARD_ITEM));
                    gameID = jsonObjectData.optString(ConstParam.GAME_ID);
                    rotationID = jsonObjectData.optString(ConstParam.ID);
                    initFillRewardItem();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case TYPE_ACTION_GET_SPINNING_RESURL:
                AppBase.showLog("TYPE_ACTION_GET_SPINNING_RESURL: " + info);
                responseResurlt = info;
                if (info != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(info);
                        int type = jsonObject.optInt(ConstParam.TYPE_REWARD);
                        animatRotate(initSetValue(type));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                animatRotate();
                break;
        }
    }
}
