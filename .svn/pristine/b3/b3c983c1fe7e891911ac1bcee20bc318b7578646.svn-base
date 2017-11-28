package vtc.game.app.vcoin.vtcpay.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.adapter.AdtHistory;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.common.VtcString;
import vtc.game.app.vcoin.vtcpay.connection.VtcConnection;
import vtc.game.app.vcoin.vtcpay.connection.VtcHttpConnection;
import vtc.game.app.vcoin.vtcpay.enums.TypeActionConnection;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.interfaces.RequestListener;
import vtc.game.app.vcoin.vtcpay.model.HistoryItem;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;


/**
 * Created by ThuyChi on 9/16/2016.
 */
public class FMHistory extends Fragment implements View.OnClickListener, RequestListener, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {
    private View viewRoot;
    private Callback callback;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private TextView tvFromDate;
    private TextView tvToDate;
    private ListView listViewHistory;
    private AdtHistory mAdapter;
    private VtcConnection vtcConnection;
    private Date fromDate;
    private Date toDate;
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    private String stdFromDate;
    private String stdToDate;
    private String typeHistory = "";
    private String stdFromDateAPI;
    private String stdToDateAPI;
    private SwipeRefreshLayout swipeRefreshLayoutHis;
    private int page = 0;
    private int limit = 50;
    private Boolean isLoading = false;

    public FMHistory() {

    }

    @SuppressLint("ValidFragment")
    public FMHistory(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_history, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        bundle = this.getArguments();
        if (bundle.getString(ConstParam.BUNDLE_KEY_SPINNING_HISTORY) != null) {
            typeHistory = bundle.getString(ConstParam.BUNDLE_KEY_SPINNING_HISTORY);
        }

        initController(view);
    }

    private void initController(View view) {
        vtcConnection = new VtcConnection(getContext(), this);

        swipeRefreshLayoutHis = (SwipeRefreshLayout) view.findViewById(R.id.swipeFreshLayout_History);
        swipeRefreshLayoutHis.setOnRefreshListener(this);

        ImageView imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);

        listViewHistory = (ListView) view.findViewById(R.id.lst_History);
        listViewHistory.setOnScrollListener(this);

        TextView tvView = (TextView) view.findViewById(R.id.tv_View);
        tvView.setOnClickListener(this);

        RelativeLayout loutFromDate = (RelativeLayout) view.findViewById(R.id.lout_Date_From);
        loutFromDate.setOnClickListener(this);

        RelativeLayout loutToDate = (RelativeLayout) view.findViewById(R.id.lout_Date_To);
        loutToDate.setOnClickListener(this);

        tvFromDate = (TextView) view.findViewById(R.id.tv_From_Date);
        tvToDate = (TextView) view.findViewById(R.id.tv_To_Date);

        Calendar calendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getContext(), new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                AppBase.showLog("year:" + year + "monthOfYear" + monthOfYear + "dayOfMonth" + dayOfMonth);

                try {
                    int month = monthOfYear + 1;
                    stdFromDate = dayOfMonth + "/" + month + "/" + year;
                    stdFromDateAPI = year + "/" + month + "/" + dayOfMonth;
                    tvFromDate.setText(stdFromDate);
                    fromDate = formatDate.parse(stdFromDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

        toDatePickerDialog = new DatePickerDialog(getContext(), new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                AppBase.showLog("year:" + year + "monthOfYear" + monthOfYear + "dayOfMonth" + dayOfMonth);

                try {
                    int month = monthOfYear + 1;
                    stdToDate = dayOfMonth + "/" + month + "/" + year;
                    stdToDateAPI = year + "/" + month + "/" + dayOfMonth;
                    tvToDate.setText(stdToDate);
                    toDate = formatDate.parse(stdToDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
    }

    private void initData(JSONArray jsonArrayPackage) {
        List<HistoryItem> lst = new ArrayList<>();
        if (lst != null) {
            for (int i = 0; i < jsonArrayPackage.length(); i++) {
                HistoryItem item = new HistoryItem();
                long date = jsonArrayPackage.optJSONObject(i).optLong(ConstParam.RECEIVE_DATE);
                item.setDate(AppBase.longToDateTime(date));
                item.setGame(jsonArrayPackage.optJSONObject(i).optString(ConstParam.NAME_GAME));
                item.setUser(jsonArrayPackage.optJSONObject(i).optString(ConstParam.ACCOUNT_NAME));
                item.setReward(jsonArrayPackage.optJSONObject(i).optString(ConstParam.TYPE_REWARD_STRING));
                lst.add(item);
            }
            mAdapter = new AdtHistory(getContext(), R.layout.tmp_item_history, lst);
            listViewHistory.setAdapter(mAdapter);
            listViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AppBase.showLog(String.valueOf(position));
                }
            });
        }
    }

    private void initLoadMore(JSONArray jsonArrayPackage) {
        List<HistoryItem> lstMore = new ArrayList<>();
        for (int i = 0; i < jsonArrayPackage.length(); i++) {
            HistoryItem item = new HistoryItem();
            long date = jsonArrayPackage.optJSONObject(i).optLong(ConstParam.RECEIVE_DATE);
            item.setDate(AppBase.longToDateTime(date));
            item.setGame(jsonArrayPackage.optJSONObject(i).optString(ConstParam.NAME_GAME));
            item.setUser(jsonArrayPackage.optJSONObject(i).optString(ConstParam.ACCOUNT_NAME));
            item.setReward(jsonArrayPackage.optJSONObject(i).optString(ConstParam.TYPE_REWARD_STRING));
            lstMore.add(item);
        }
        mAdapter.initLoadMoreData(lstMore);
        if (jsonArrayPackage.length() > 0) {
            isLoading = false;
        } else {
            isLoading = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lout_Date_From:
                fromDatePickerDialog.show();
                break;

            case R.id.lout_Date_To:
                toDatePickerDialog.show();
                break;
            case R.id.tv_View:
                initGetData(0, limit);
                break;
            case R.id.img_Back:
                AppBase.initBack();
                break;
            default:
                break;
        }
    }

    private void initGetData(int mPage, int mLimit) {
        if ((toDate != null) && (fromDate != null)) {
            if (toDate.after(fromDate)) {
                if (typeHistory.equals(VtcString.SPINNING_HISTORY_ACCOUNT)) {
                    getRewardOfAccount(stdFromDateAPI, stdToDateAPI, mPage, mLimit);
                } else if (typeHistory.equals(VtcString.SPINNING_HISTORY_ALL)) {
                    getRewardAll(stdFromDateAPI, stdToDateAPI, mPage, mLimit);
                }

            } else {
                Toast.makeText(getContext(), "Chọn ngày chưa đúng", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Chưa chọn ngày", Toast.LENGTH_SHORT).show();
        }
    }

    private void getRewardOfAccount(String fromDate, String toDate, int currentPage, int litmit) {
        Map<String, String> mapHistoryAccount = new HashMap<>();
        mapHistoryAccount.put(ConstParam.PARAM_REQUEST_FROM, fromDate);
        mapHistoryAccount.put(ConstParam.PARAM_REQUEST_TO, toDate);
        mapHistoryAccount.put(ConstParam.PARAM_REQUEST_PAGE, String.valueOf(currentPage));
        mapHistoryAccount.put(ConstParam.PARAM_REQUEST_LIMIT, String.valueOf(litmit));
        mapHistoryAccount.put(ConstParam.PARAM_REQUEST_ACCESS_TOKEN, VtcString.ACCESS_TOKEN);
        mapHistoryAccount.put(ConstParam.PARAM_REQUEST_USERNAME, VtcString.USER_NAME);
        mapHistoryAccount.put(ConstParam.PARAM_REQUEST_ACCOUNT_ID, VtcString.ACCOUNT_ID);

        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_REWARD_ACCOUNT, RequestListener.API_CONNECTION_GET_LIST_REWARD_ACCOUNT + VtcHttpConnection.urlEncodeUTF8(mapHistoryAccount), false);
    }

    private void getRewardAll(String fromDate, String toDate, int currentPage, int litmit) {
        Map<String, String> mapHistoryAll = new HashMap<>();
        mapHistoryAll.put(ConstParam.PARAM_REQUEST_FROM, fromDate);
        mapHistoryAll.put(ConstParam.PARAM_REQUEST_TO, toDate);
        mapHistoryAll.put(ConstParam.PARAM_REQUEST_PAGE, String.valueOf(currentPage));
        mapHistoryAll.put(ConstParam.PARAM_REQUEST_LIMIT, String.valueOf(litmit));

        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_REWARD_ALL, RequestListener.API_CONNECTION_GET_LIST_REWARD_ALL + VtcHttpConnection.urlEncodeUTF8(mapHistoryAll), false);

    }

    @Override
    public void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData) {
        if (swipeRefreshLayoutHis != null) {
            swipeRefreshLayoutHis.setRefreshing(false);
        }
    }

    @Override
    public void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData) {
        switch (keyType) {
            case TYPE_ACTION_GET_REWARD_ALL:
                if (swipeRefreshLayoutHis != null) {
                    swipeRefreshLayoutHis.setRefreshing(false);
                }
                AppBase.showLog("TYPE_ACTION_GET_REWARD_ALL: " + info);
                try {
                    JSONArray jsonArrayData = new JSONArray(info);
                    if (jsonArrayData.length() > 0) {
                        if (page == 0) {
                            initData(jsonArrayData);
                        } else {
                            initLoadMore(jsonArrayData);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case TYPE_ACTION_GET_REWARD_ACCOUNT:
                if (swipeRefreshLayoutHis != null) {
                    swipeRefreshLayoutHis.setRefreshing(false);
                }
                AppBase.showLog("TYPE_ACTION_GET_REWARD_ALL: " + info);
                try {
                    JSONArray jsonArrayData = new JSONArray(info);
                    if (jsonArrayData.length() > 0) {
                        if (page == 0) {
                            initData(jsonArrayData);
                        } else {
                            initLoadMore(jsonArrayData);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void onRefresh() {
        initGetData(page, limit);
    }

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
                initGetData(page, limit);
            }
        }
    }
}
