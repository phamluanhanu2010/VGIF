package vtc.game.app.vcoin.vtcpay.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.adapter.AdtNews;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.connection.VtcConnection;
import vtc.game.app.vcoin.vtcpay.enums.TypeActionConnection;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.interfaces.RequestListener;
import vtc.game.app.vcoin.vtcpay.model.NewsItem;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;


/**
 * Created by ThuyChi on 9/16/2016.
 */
public class FMNews extends Fragment implements View.OnClickListener, RequestListener, SwipeRefreshLayout.OnRefreshListener {
    private View viewRoot;
    private Callback callback;
    private ListView listViewNews;
    private AdtNews mAdapter;
    private VtcConnection vtcConnection;
    private Boolean isLoading = false;
    private int page;
    private int pageSize;
    private String subStr = "&pageSize=";
    private String sAPI = RequestListener.API_CONNECTION_GET_LIST_NEWS;
    private List<NewsItem> lst;
    private SwipeRefreshLayout swipeLayoutSimpleNews;

    public FMNews() {

    }

    @SuppressLint("ValidFragment")
    public FMNews(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppBase.showLog("onCreateView--------------------------FMNews");
        viewRoot = inflater.inflate(R.layout.ui_news, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        AppBase.showLog("onViewCreated--------------------------FMNews");
        super.onViewCreated(view, savedInstanceState);

        initLoadData();
    }

    private void initLoadData() {
        isLoading = false;
        lst = new ArrayList<>();
        page = 1;
        pageSize = 10;
        vtcConnection = new VtcConnection(getContext(), this);
        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_GAME_LIST_NEWS, initGetAPI(), false);
    }

    private String initGetAPI() {
        sAPI = RequestListener.API_CONNECTION_GET_LIST_NEWS + page + subStr + pageSize;
        return sAPI;
    }

    public void callBackMain() {
        AppBase.showLog("callBackMain--------------------------FMNews");

    }

    private void initController(String data) {
        swipeLayoutSimpleNews = (SwipeRefreshLayout) viewRoot.findViewById(R.id.swipeLayoutSimple_News);
        swipeLayoutSimpleNews.setOnRefreshListener(this);
        listViewNews = (ListView) viewRoot.findViewById(R.id.lst_News);
        listViewNews.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (!isLoading) {
                        isLoading = true;
                        AppBase.showLog("LISTVIEW END");
                        page = page + 1;
                        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_GAME_LIST_NEWS, initGetAPI(), false);
//                        loadMoreData();
                    }
                }
            }
        });

        initData(data);
    }

    private void initData(String data) {

        try {
            JSONArray jsonArrayList = new JSONArray(data);

            for (int i = 0; i < jsonArrayList.length(); i++) {
                NewsItem item = new NewsItem();
                item.setNewsTitile(AppBase.checkStringNull(jsonArrayList.getJSONObject(i).optString("Title")));
                item.setNewsID(AppBase.checkStringNull(jsonArrayList.getJSONObject(i).optString(ConstParam.NEWS_ID)));
                item.setImgUrl(AppBase.checkStringNull(jsonArrayList.getJSONObject(i).optString(ConstParam.IMAGE_URL)));
                item.setNewsPublishDate(AppBase.checkStringNull(jsonArrayList.getJSONObject(i).optString(ConstParam.TIME)));
                item.setNewsURL(AppBase.checkStringNull(jsonArrayList.getJSONObject(i).optString("Link")));
                item.setViewCount(jsonArrayList.getJSONObject(i).optInt(ConstParam.VIEW_COUNT));
                lst.add(item);
            }
            mAdapter = new AdtNews(getContext(), R.layout.tmp_item_news, lst);

            mAdapter.setOnClickItem(new AdtNews.onClickItem() {
                @Override
                public void onClickItem(int position, String newsUrl) {
                    AppBase.showLog("----------Position: " + position + "-----------newsUrl: " + newsUrl);
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstParam.BUNDLE_KEY_NEWS_DETAIL, newsUrl);
                    AppBase.callNewFragment(Const.UI_NEWS_DETAIL, bundle, true);
                }
            });
            listViewNews.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initLoadMore(String data) {
        try {
            JSONArray jsonArrayList = new JSONArray(data);

            if (jsonArrayList.length() == pageSize) {
                isLoading = false;
            } else isLoading = true;
            List<NewsItem> lstMore =new ArrayList<>();
            for (int i = 0; i < jsonArrayList.length(); i++) {
                NewsItem itemMore = new NewsItem();
                itemMore.setNewsTitile(AppBase.checkStringNull(jsonArrayList.getJSONObject(i).optString("Title")));
                itemMore.setNewsID(AppBase.checkStringNull(jsonArrayList.getJSONObject(i).optString(ConstParam.NEWS_ID)));
                itemMore.setImgUrl(AppBase.checkStringNull(jsonArrayList.getJSONObject(i).optString(ConstParam.IMAGE_URL)));
                itemMore.setNewsPublishDate(AppBase.checkStringNull(jsonArrayList.getJSONObject(i).optString(ConstParam.TIME)));
                itemMore.setNewsURL(AppBase.checkStringNull(jsonArrayList.getJSONObject(i).optString("Link")));
                itemMore.setViewCount(jsonArrayList.getJSONObject(i).optInt(ConstParam.VIEW_COUNT));
                lstMore.add(itemMore);
            }
            mAdapter.initData(lstMore);
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
            case TYPE_ACTION_GET_GAME_LIST_NEWS:
                if (swipeLayoutSimpleNews != null) {
                    swipeLayoutSimpleNews.setRefreshing(false);
                }
                break;
        }
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_GAME_LIST_NEWS:
                if (!reponseFullData.equals("")) {
                    if (page == 1) {
                        AppBase.showLog("TYPE_ACTION_GET_GAME_LIST_NEWS: " + reponseFullData);
                        initController(reponseFullData);
                        if (swipeLayoutSimpleNews != null) {
                            swipeLayoutSimpleNews.setRefreshing(false);
                        }
                    } else {
                        AppBase.showLog("TYPE_ACTION_GET_GAME_LIST_NEWS_MORE: " + reponseFullData);
                        initLoadMore(reponseFullData);
                    }
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        initLoadData();
    }
}
