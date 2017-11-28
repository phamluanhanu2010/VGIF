package vtc.game.app.vcoin.vtcpay.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.connection.VtcConnection;
import vtc.game.app.vcoin.vtcpay.enums.TypeActionConnection;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.interfaces.RequestListener;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;


/**
 * Created by ThuyChi on 9/16/2016.
 */
public class FMDetailNews extends Fragment implements View.OnClickListener, RequestListener {
    private View viewRoot;
    private Callback callback;
    private WebView webView;
    private String link;
    private ImageView imgBack;
    private ImageView imgNext;
    private FrameLayout loutAds;
    private FrameLayout loutCloseAds;
    private ImageView imgBanerAds;
    private VtcConnection vtcConnection;
    private JSONArray jsonArrayData;

    public FMDetailNews() {

    }

    @SuppressLint("ValidFragment")
    public FMDetailNews(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_news_detail, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new Bundle();
        bundle = this.getArguments();
        if (bundle != null) {
            link = bundle.getString(ConstParam.BUNDLE_KEY_NEWS_DETAIL);
        }
        initController(view);
    }

    private void initController(View view) {
        vtcConnection = new VtcConnection(getContext(), this);
        vtcConnection.onExcuteProcess(TypeActionConnection.TYPE_ACTION_GET_LIST_ADVERTISING, RequestListener.API_CONNECTION_GET_ADS_LIST, false);

        imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imgBack.setImageAlpha(Const.BACKGROUND_ALPHA_VALUE);
            imgBack.setClickable(false);
        }

        imgNext = (ImageView) view.findViewById(R.id.img_Next);
        imgNext.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imgNext.setImageAlpha(Const.BACKGROUND_ALPHA_VALUE);
            imgNext.setClickable(false);
        }

        loutAds = (FrameLayout) view.findViewById(R.id.lout_Ads);
        loutAds.setOnClickListener(this);

        loutCloseAds = (FrameLayout) view.findViewById(R.id.lout_Close_Ads);
        loutCloseAds.setOnClickListener(this);

        loutAds.setVisibility(View.GONE);
        loutCloseAds.setVisibility(View.GONE);

        imgBanerAds = (ImageView) view.findViewById(R.id.img_Banner_Ads);

//        AppBase.setImageWithUrl(null, imgBanerAds);

        ImageView imgClose = (ImageView) view.findViewById(R.id.img_Close);
        imgClose.setOnClickListener(this);

        webView = (WebView) view.findViewById(R.id.webView_News);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        if (link != null) {
            startWebView(getContext(), webView, link);
        }
    }

    private void startWebView(final Context mContext, final WebView webView, String url) {
        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(mContext);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();

                }
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            public void onPageFinished(WebView view, String url) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (webView.canGoBack()) {
                        imgBack.setImageAlpha(Const.BACKGROUND_ALPHA_FULL);
                        imgBack.setClickable(true);
                    } else {
                        imgBack.setImageAlpha(Const.BACKGROUND_ALPHA_VALUE);
                        imgBack.setClickable(false);
                    }

                    if (webView.canGoForward()) {
                        imgNext.setImageAlpha(Const.BACKGROUND_ALPHA_FULL);
                        imgNext.setClickable(true);
                    } else {
                        imgNext.setImageAlpha(Const.BACKGROUND_ALPHA_VALUE);
                        imgNext.setClickable(false);
                    }
                }
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                AppBase.showLog("URL: " + url);
                AppBase.showLog("Titile: " + view.getTitle());
                String tittile = view.getTitle();
                if (tittile != null && !tittile.isEmpty()) {
                    // doSomething
                    // handleCallBack(tittile);
                }
            }
        });

        // Javascript inabled on webview
        //webView.getSettings().setJavaScriptEnabled(true);

        //Load url in webview
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_Back:
                if (webView.canGoBack()) {
                    webView.goBack();
                }
                break;
            case R.id.img_Next:
                if (webView.canGoForward()) {
                    webView.goForward();
                }
                break;
            case R.id.img_Close:
                AppBase.initBack();
                break;
            case R.id.lout_Ads:
                try {
                    if ((jsonArrayData != null) && jsonArrayData.length() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ConstParam.BUNDLE_KEY_WEBVIEW_LINK, jsonArrayData.getJSONObject(0).optString(ConstParam.LINK));
                        bundle.putString(ConstParam.BUNDLE_KEY_WEBVIEW_TITILE, getResources().getString(R.string.title_View_ADS));
                        AppBase.callNewFragment(Const.UI_GUIDE, bundle, true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.lout_Close_Ads:
                loutAds.setVisibility(View.GONE);
                loutCloseAds.setVisibility(View.GONE);
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
            case TYPE_ACTION_GET_LIST_ADVERTISING:
                AppBase.showLog("TYPE_ACTION_GET_LIST_ADVERTISING: " + info);
                try {
                    jsonArrayData = new JSONArray(info);
                    if (jsonArrayData.length() > 0 && jsonArrayData != null) {
                        loutAds.setVisibility(View.VISIBLE);
                        loutCloseAds.setVisibility(View.VISIBLE);
                        AppBase.setImageWithUrl(jsonArrayData.getJSONObject(0).optString(ConstParam.BANNER_FILE_NAME), imgBanerAds);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
