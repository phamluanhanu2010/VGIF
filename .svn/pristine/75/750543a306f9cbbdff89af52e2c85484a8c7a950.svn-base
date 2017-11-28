package vtc.game.app.vcoin.vtcpay.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.adapter.AdtGiftcode;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.connection.VtcConnection;
import vtc.game.app.vcoin.vtcpay.enums.TypeActionConnection;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.interfaces.RequestListener;
import vtc.game.app.vcoin.vtcpay.model.GiftcodeItem;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;


/**
 * Created by ThuyChi on 9/16/2016.
 */
public class FMGiftcode extends Fragment implements View.OnClickListener, RequestListener, SwipeRefreshLayout.OnRefreshListener {
    private View viewRoot;
    private Callback callback;
    private ListView listViewGiftcode;
    private AdtGiftcode mAdapter;
    private VtcConnection vtcConnection;
    private SwipeRefreshLayout swipeRefreshLayoutEvents;

    public FMGiftcode() {

    }

    @SuppressLint("ValidFragment")
    public FMGiftcode(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_giftcode, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vtcConnection = new VtcConnection(getContext(), this);
        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_GAME_LIST_GIFTCODE, RequestListener.API_CONNECTION_GET_LIST_GIFTCODE, false);
    }


    public void callBackMain() {
        AppBase.showLog("callBackMain--------------------------FMGiftcode");
    }

    private void initController(String data) {
        swipeRefreshLayoutEvents = (SwipeRefreshLayout) viewRoot.findViewById(R.id.swipeFreshLayout_Events);
        swipeRefreshLayoutEvents.setOnRefreshListener(this);
        listViewGiftcode = (ListView) viewRoot.findViewById(R.id.lst_Giftcode);
        listViewGiftcode.setDivider(null);
        initData(data);
    }

    private void initData(String data) {
        try {
            JSONArray jsonArrayGiftCode = new JSONArray(data);

            final List<GiftcodeItem> lst = new ArrayList<>();


            for (int i = 0; i < jsonArrayGiftCode.length(); i++) {
                GiftcodeItem item = new GiftcodeItem();
                item.setTitileGiftcode(AppBase.checkStringNull(jsonArrayGiftCode.getJSONObject(i).optString(ConstParam.TITILE_EVENT)));
                item.setImgUrl(AppBase.checkStringNull(jsonArrayGiftCode.getJSONObject(i).optString(ConstParam.BANNER_EVENT)));
                item.setGiftcodeID(AppBase.checkStringNull(jsonArrayGiftCode.getJSONObject(i).optString(ConstParam.ID)));


                lst.add(item);
            }
            mAdapter = new AdtGiftcode(getContext(), R.layout.tmp_item_giftcode, lst);
            mAdapter.setOnClickItem(new AdtGiftcode.onClickItem() {
                @Override
                public void onClickItem(int position, String eventID) {
                    AppBase.showLog("----------Giftcode----Position: " + position + "-----------eventID: " + eventID);
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstParam.BUNDLE_KEY_EVENT_DETAIL, eventID);
                    AppBase.callNewFragmentCallBack(Const.UI_SHARE_GIFTCODE, bundle, true, new Callback() {
                        @Override
                        public <T> void onHandlerCallBack(int key, T... t) {
                            callback.onHandlerCallBack(0);
                        }
                    });
                }
            });
            listViewGiftcode.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_GAME_LIST_GIFTCODE:
                if (swipeRefreshLayoutEvents != null) {
                    swipeRefreshLayoutEvents.setRefreshing(false);
                }
                break;
        }
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_GAME_LIST_GIFTCODE:
                if (!info.equals("")) {
                    AppBase.showLog("TYPE_ACTION_GET_GAME_LIST_GIFTCODE: " + info);
                    initController(info);
                    if (swipeRefreshLayoutEvents != null) {
                        swipeRefreshLayoutEvents.setRefreshing(false);
                    }
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        if (vtcConnection != null) {
            vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_GAME_LIST_GIFTCODE, RequestListener.API_CONNECTION_GET_LIST_GIFTCODE, false);
        }
    }
}
