package vtc.game.app.vcoin.vtcpay.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.adapter.AdtGame;
import vtc.game.app.vcoin.vtcpay.adapter.AdtGiftcode;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.connection.VtcConnection;
import vtc.game.app.vcoin.vtcpay.connection.VtcHttpConnection;
import vtc.game.app.vcoin.vtcpay.enums.TypeActionConnection;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.interfaces.RequestListener;
import vtc.game.app.vcoin.vtcpay.model.GameTypeModel;
import vtc.game.app.vcoin.vtcpay.model.GiftcodeItem;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;


/**
 * Created by ThuyChi on 9/16/2016.
 */
public class FMSearch extends Fragment implements View.OnClickListener, RequestListener {
    private View viewRoot;
    private Callback callback;
    private ListView listView;
    private VtcConnection vtcConnection;
    private SearchView searchView;
    private AdtGiftcode mAdapterEvent;
    private AdtGame mAdapterGame;
    private TextView tvResultCount;
    private int searchFlag = 0;

    public FMSearch() {

    }

    @SuppressLint("ValidFragment")
    public FMSearch(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_search, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        bundle = this.getArguments();
        if (bundle != null) {
            searchFlag = bundle.getInt(ConstParam.BUNDLE_KEY_SEARCH);
        }

        vtcConnection = new VtcConnection(getContext(), this);

        listView = (ListView) view.findViewById(R.id.lst_Find);
        listView.setDivider(null);

        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);

        tvResultCount = (TextView) view.findViewById(R.id.tv_Result_Count);
        tvResultCount.setVisibility(View.GONE);

        final TextView tvguide = (TextView) view.findViewById(R.id.tv_Guide);

        searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
                tvguide.setVisibility(View.GONE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tvguide.setVisibility(View.VISIBLE);
                return false;
            }
        });


        if (searchFlag == 0) {
//            searchView.setQueryHint(getResources().getString(R.string.hint_Search_Game));
            tvguide.setText(getResources().getString(R.string.hint_Search_Game));
        } else if (searchFlag == 1) {
//            searchView.setQueryHint(getResources().getString(R.string.hint_Search_Event));
            tvguide.setText(getResources().getString(R.string.hint_Search_Event));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                AppBase.showLog("onQueryTextSubmit");
                if (searchFlag == 0) {
                    initSearchGame(query);
                } else if (searchFlag == 1) {
                    initSearchEvent(query);
                }
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                AppBase.showLog("onQueryTextChange");
                return false;
            }
        });
    }

    private void initSearchEvent(String query) {
        Map<String, String> mapFindEvent = new HashMap<>();
        mapFindEvent.put(ConstParam.PARAM_REQUEST_TITILE_EVENT, query);
        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_FIND_EVENT_BY_TITILE, RequestListener.API_CONNECTION_FIND_EVENT + VtcHttpConnection.urlEncodeUTF8(mapFindEvent), false);
    }

    private void initEventData(String data) {
        try {
            JSONArray jsonArrayGiftCode = new JSONArray(data);

            final List<GiftcodeItem> lst = new ArrayList<>();

            tvResultCount.setVisibility(View.VISIBLE);
            tvResultCount.setText("Có tất cả " + jsonArrayGiftCode.length() + " kết quả tìm được");

            AppBase.showLog("Length: " + jsonArrayGiftCode.length());
            for (int i = 0; i < jsonArrayGiftCode.length(); i++) {
                GiftcodeItem item = new GiftcodeItem();
                item.setTitileGiftcode(AppBase.checkStringNull(jsonArrayGiftCode.getJSONObject(i).optString(ConstParam.TITILE_EVENT)));
                item.setImgUrl(AppBase.checkStringNull(jsonArrayGiftCode.getJSONObject(i).optString(ConstParam.BANNER_EVENT)));
                item.setGiftcodeID(AppBase.checkStringNull(jsonArrayGiftCode.getJSONObject(i).optString(ConstParam.ID)));
                lst.add(item);
            }
            mAdapterEvent = new AdtGiftcode(getContext(), R.layout.tmp_item_giftcode, lst);
            mAdapterEvent.setOnClickItem(new AdtGiftcode.onClickItem() {
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
            listView.setAdapter(mAdapterEvent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initGameData(String data) {
        try {
            JSONArray jsonArrayGiftCode = new JSONArray(data);

            final List<GameTypeModel> lst = new ArrayList<>();

            tvResultCount.setVisibility(View.VISIBLE);
            tvResultCount.setText("Có tất cả " + jsonArrayGiftCode.length() + " kết quả tìm được");

            for (int i = 0; i < jsonArrayGiftCode.length(); i++) {
                GameTypeModel item = new GameTypeModel();
                item.setId(AppBase.checkStringNull(jsonArrayGiftCode.getJSONObject(i).optString(ConstParam.ID)));
                item.setGameName(AppBase.checkStringNull(jsonArrayGiftCode.getJSONObject(i).optString(ConstParam.NAME_GAME)));
                item.setPictureGame(AppBase.checkStringNull(jsonArrayGiftCode.getJSONObject(i).optString(ConstParam.PICTURE_GAME)));
                item.setStatus(jsonArrayGiftCode.getJSONObject(i).optInt(ConstParam.STATUS));
                lst.add(item);
            }
            mAdapterGame = new AdtGame(getContext(), R.layout.tmp_item_giftcode, lst);
            mAdapterGame.setOnClickItem(new AdtGame.onClickItem() {
                @Override
                public void onClickItem(int position, String gameID) {
                    AppBase.showLog("----------GAME----Position: " + position + "-----------Game ID: " + gameID);
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstParam.BUNDLE_KEY_GAME_DETAIL, gameID);
                    AppBase.callNewFragment(Const.UI_GAME_INFO, bundle, true);
                }
            });
            listView.setAdapter(mAdapterGame);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initSearchGame(String query) {
        Map<String, String> mapFindEvent = new HashMap<>();
        mapFindEvent.put(ConstParam.PARAM_REQUEST_GAME_NAME, query);
        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_FIND_GAME_BY_NAME, RequestListener.API_CONNECTION_FIND_GAME + VtcHttpConnection.urlEncodeUTF8(mapFindEvent), false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_Back:
                AppBase.initBack();
                searchView.clearFocus();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {

    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_FIND_EVENT_BY_TITILE:
                AppBase.showLog("onPostExecuteSuccess---TYPE_ACTION_FIND_EVENT_BY_TITILE: " + info);
                initEventData(info);
                break;
            case TYPE_ACTION_FIND_GAME_BY_NAME:
                AppBase.showLog("onPostExecuteSuccess---TYPE_ACTION_FIND_GAME_BY_NAME: " + info);
                initGameData(info);
                break;
        }
    }
}
