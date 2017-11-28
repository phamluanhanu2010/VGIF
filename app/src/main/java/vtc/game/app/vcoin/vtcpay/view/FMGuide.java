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
import android.widget.ImageView;
import android.widget.TextView;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;


/**
 * Created by ThuyChi on 9/16/2016.
 */
public class FMGuide extends Fragment implements View.OnClickListener {
    private View viewRoot;
    private Callback callback;
    private WebView webView;
    private String link;
    private String titile;
    private ImageView imgBack;

    public FMGuide() {

    }

    @SuppressLint("ValidFragment")
    public FMGuide(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.ui_webview, container, false);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new Bundle();
        bundle = this.getArguments();
        if (bundle != null) {
            link = bundle.getString(ConstParam.BUNDLE_KEY_WEBVIEW_LINK);
            titile = bundle.getString(ConstParam.BUNDLE_KEY_WEBVIEW_TITILE);
        }
        initController(view);
    }

    private void initController(View view) {

        imgBack = (ImageView) view.findViewById(R.id.img_Back);
        imgBack.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imgBack.setImageAlpha(Const.BACKGROUND_ALPHA_VALUE);
            imgBack.setClickable(false);
        }

        ImageView imgClose = (ImageView) view.findViewById(R.id.img_Close);
        imgClose.setOnClickListener(this);

        TextView tvTitleView = (TextView) view.findViewById(R.id.tv_Titile_View);
        tvTitleView.setText(titile);

        webView = (WebView) view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        if (link != null) {
            startWebView(getContext(), webView, link);
        }

    }

    public void startWebView(final Context mContext, final WebView webView, final String url) {
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
            case R.id.img_Close:
                AppBase.initClearPopback();
                break;
            default:
                break;
        }
    }
}
