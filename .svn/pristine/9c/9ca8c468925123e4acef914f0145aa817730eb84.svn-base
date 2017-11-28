package vtc.game.app.vcoin.vtcpay.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.adapter.AdtGameList;
import vtc.game.app.vcoin.vtcpay.adapter.AdtGameType;
import vtc.game.app.vcoin.vtcpay.adapter.AdtSlidesBaner;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.connection.VtcConnection;
import vtc.game.app.vcoin.vtcpay.enums.TypeActionConnection;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.interfaces.RequestListener;
import vtc.game.app.vcoin.vtcpay.model.BanerItem;
import vtc.game.app.vcoin.vtcpay.model.GameListItem;
import vtc.game.app.vcoin.vtcpay.model.GameTypeItem;
import vtc.game.app.vcoin.vtcpay.model.GameTypeModel;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;


/**
 * Created by ThuyChi on 9/16/2016.
 */
public class FMGameList extends Fragment implements View.OnClickListener, RequestListener {
    private View viewRoot;
    private Callback callback;
    private AdtGameList mAdapter;
    private AdtSlidesBaner banerAdapter;
    private List<GameListItem> gameList = new ArrayList<>();
    private List<GameTypeItem> gameTypeList = new ArrayList<>();
    private AdtGameType gameTypeAdapter;
    private ViewPager pagerBaner;
    private RelativeLayout loutContainerBaner;
    private CirclePageIndicator indicatorBaner;
    private VtcConnection vtcConnection;
    private ListView listViewGameType;

    public FMGameList() {

    }

    @SuppressLint("ValidFragment")
    public FMGameList(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppBase.showLog("onCreateView-----------FMGameList");
        viewRoot = inflater.inflate(R.layout.ui_game_list, container, false);
        listViewGameType = (ListView) viewRoot.findViewById(R.id.lstView_GameType);
        return viewRoot;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppBase.showLog("onViewCreated-----------FMGameList");
//        listViewGameType = (ListView) view.findViewById(R.id.lstView_GameType);
//        initController(view);
        vtcConnection = new VtcConnection(getContext(), this);
        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_LIST_GAME, RequestListener.API_CONNECTION_GET_LIST_GAME, false);
    }

    public void callBackMain() {
        AppBase.showLog("callBackMain--------------------------FMGameList");

//        vtcConnection = new VtcConnection(getContext(), this);
//        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_LIST_GAME, RequestListener.API_CONNECTION_GET_LIST_GAME, false);
    }

    private void initController(View view, String data) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.ui_header_game_list, listViewGameType, false);
        pagerBaner = (ViewPager) header.findViewById(R.id.pager_Baner);
        loutContainerBaner = (RelativeLayout) header.findViewById(R.id.lout_Container_Baner);
        loutContainerBaner.getLayoutParams().height = (int) (Const.DISPLAY_WIDTH * 9 / 16);
        indicatorBaner = (CirclePageIndicator) header.findViewById(R.id.indicator_Baner);
        JSONArray jsonBanerLst = null;
        try {
            JSONObject jsonData = new JSONObject(data);
            JSONArray jsonTypeAndGame = jsonData.getJSONArray(ConstParam.LIST_TYPE_AND_GAME);
            jsonBanerLst = jsonData.getJSONArray(ConstParam.LIST_BANNER);
            AppBase.showLog("jsonTypeAndGame: " + jsonTypeAndGame.toString());
            AppBase.showLog("jsonBanerLst: " + jsonBanerLst.toString());


            GameTypeModel gameTypeModel = GameTypeModel.getParseGameData(jsonTypeAndGame);
            if (jsonBanerLst != null) {

                AppBase.showLog("getHeaderViewsCount: " + listViewGameType.getHeaderViewsCount());
                if (listViewGameType.getHeaderViewsCount() == 0) {
                    listViewGameType.addHeaderView(header, null, false);
                    loutContainerBaner.setVisibility(LinearLayout.VISIBLE);
                    final List<BanerItem> lstBaner = new ArrayList<>();

                    for (int i = 0; i < jsonBanerLst.length(); i++) {
                        BanerItem itemBaner = new BanerItem();
                        itemBaner.setImgUrlBaner(jsonBanerLst.optJSONObject(i).optString(ConstParam.BANNER_FILE_NAME));
                        itemBaner.setId(jsonBanerLst.optJSONObject(i).optString(ConstParam.ID));
                        itemBaner.setUrlBanner(jsonBanerLst.optJSONObject(i).optString(ConstParam.LINK));
                        lstBaner.add(itemBaner);
                    }
                    banerAdapter = new AdtSlidesBaner(this.getChildFragmentManager(), lstBaner, getActivity());
                    pagerBaner.setAdapter(banerAdapter);

                    indicatorBaner.setViewPager(pagerBaner);

                    ((CirclePageIndicator) indicatorBaner).setSnap(true);
                }


            } else {
                // không có dữ liệu banner
                loutContainerBaner.setVisibility(LinearLayout.GONE);
            }

            gameTypeAdapter = new AdtGameType(getContext(), R.layout.tmp_item_game_type, gameTypeModel);
            listViewGameType.setAdapter(gameTypeAdapter);
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

    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        AppBase.showLog("keyType:" + keyType.toString());
        AppBase.showLog("response:" + info);
        AppBase.showLog("reponseFullData:" + reponseFullData);
        switch (keyType) {
            case TYPE_ACTION_GET_LIST_GAME:
                if (!info.equals("")) {
                    initController(viewRoot, info);
                }
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
