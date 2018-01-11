package ir.hezareh.park;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = WebviewActivity.class
            .getSimpleName();
    SwipeRefreshLayout swipeRefreshLayout;
    WebView webView;
    private float m_downX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        webView = (WebView) findViewById(R.id.webView);

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,screenHeight/3);
        //webView.setLayoutParams(params);
        if (getIntent().getStringExtra("URL") != null) {
            initWebView();
        }

        //webView.loadUrl(url);


        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {


                swipeRefreshLayout.setRefreshing(true);

                if (getIntent().getStringExtra("URL") != null) {
                    webView.loadUrl(getIntent().getExtras().getString("URL"));
                }


                //renderPost()
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        webView.loadUrl(getIntent().getExtras().getString("URL"));
        //((ImageView) findViewById(R.id.news_detail_header)).setBackgroundResource(R.drawable.shadow_top);
        //renderPost();
        swipeRefreshLayout.setRefreshing(false);

    }


    private void renderPost() {
        //webView.loadUrl("https://www.google.com/");
    }


    private void initWebView() {
        webView.setWebChromeClient(new WebviewActivity.MyWebChromeClient(this));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //progressBar.setVisibility(View.VISIBLE);
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
                //progressBar.setVisibility(View.GONE);
                //NewsRecycler.setAdapter(myRecyclerAdapter);
                swipeRefreshLayout.setRefreshing(false);
                invalidateOptionsMenu();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                invalidateOptionsMenu();
            }
        });
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getPointerCount() > 1) {
                    //Multi touch detected
                    return true;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // save the x
                        m_downX = event.getX();
                    }
                    break;

                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        // set x so that it doesn't move
                        event.setLocation(m_downX, event.getY());
                    }
                    break;
                }

                return false;
            }
        });
    }


    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }


    }


}
