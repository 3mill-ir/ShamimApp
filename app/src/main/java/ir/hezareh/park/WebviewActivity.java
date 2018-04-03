package ir.hezareh.park;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.VolleyError;

import ir.hezareh.park.DataLoading.networking;
import ir.hezareh.park.Util.Utils;

public class WebviewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = WebviewActivity.class
            .getSimpleName();
    SwipeRefreshLayout swipeRefreshLayout;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        swipeRefreshLayout = findViewById(R.id.swipeContainer);

        webView = findViewById(R.id.webView);

        ((TextView) findViewById(R.id.header_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));


        if (getIntent().getStringExtra("URL") != null) {
            initWebView();
        }


        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                if (getIntent().getExtras() != null) {
                    Log.d("webview", getIntent().getExtras() + "");
                    //webView.loadUrl(getIntent().getExtras().getString("URL"));
                    getWebView(getIntent().getExtras().getString("URL"));

                }
            }
        });
    }

    @Override
    public void onRefresh() {
        if (getIntent().getExtras() != null) {
            //webView.loadUrl(getIntent().getExtras().getString("URL"));
            getWebView(getIntent().getExtras().getString("URL"));
        }
    }


    private void getWebView(String URL) {
        new networking(getApplicationContext()).getWebview(new networking.webViewResponseListener() {
            @Override
            public void requestStarted() {
            }

            @Override
            public void requestCompleted(String response) {

                webView.loadDataWithBaseURL(null, response, "text/html", "utf-8", null);
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
            }
        }, URL);
    }


    private void initWebView() {
        webView.setWebChromeClient(new WebviewActivity.MyWebChromeClient(this));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                swipeRefreshLayout.setRefreshing(true);
                invalidateOptionsMenu();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
                //Log.e("can",webView.canScrollVertically(0)+"");
                invalidateOptionsMenu();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                swipeRefreshLayout.setRefreshing(false);
                invalidateOptionsMenu();
            }
        });
        //webView.clearCache(true);
        //webView.clearHistory();

        webView.setScrollContainer(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.canGoBack();
        webView.canGoForward();
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);

        webView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default


        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        if (!new Utils(getApplicationContext()).isConnectedToInternet()) { // loading offline
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }
    }

}
