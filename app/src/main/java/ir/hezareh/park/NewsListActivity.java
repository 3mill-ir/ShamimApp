package ir.hezareh.park;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;


public class NewsListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView NewsRecycler;
    RecyclerViewAdapter myRecyclerAdapter;
    int screenHeight;
    private String url = "https://www.digikala.com/";
    private WebView webView;
    private ProgressBar progressBar;
    private float m_downX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        layout.setTitleEnabled(true);
        layout.setTitle("");
        screenHeight = new Utils(getApplicationContext()).getDisplayMetrics().heightPixels;

        //View logo = getLayoutInflater().inflate(R.layout.custom_toolbar, null);

        //Root_Layout = (LinearLayout) findViewById(R.id.main_layout);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.expand(webView);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        webView = (WebView) findViewById(R.id.webView);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        NewsRecycler = (RecyclerView) findViewById(R.id.news_recycler);

        myRecyclerAdapter = new RecyclerViewAdapter(getApplicationContext(), null);

        NewsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true));
        NewsRecycler.addItemDecoration(new EqualSpacingItemDecoration(10, EqualSpacingItemDecoration.HORIZONTAL));
        NewsRecycler.setItemAnimator(new DefaultItemAnimator());

        //LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,screenHeight/3);
        //webView.setLayoutParams(params);
        initWebView();
        //webView.loadUrl(url);

        Picasso.with(getApplicationContext())
                .load("https://www.planwallpaper.com/static/images/desktop-year-of-the-tiger-images-wallpaper.jpg")
                .fit()
                .into((ImageView) findViewById(R.id.news_detail_header));


        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                webView.loadUrl(url);
                //((ImageView) findViewById(R.id.news_detail_header)).setBackgroundResource(R.drawable.shadow);
                //renderPost()
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        webView.loadUrl(url);
        //((ImageView) findViewById(R.id.news_detail_header)).setBackgroundResource(R.drawable.shadow);
        //renderPost();

        swipeRefreshLayout.setRefreshing(false);

    }


    private void renderPost() {
        //webView.loadUrl("https://www.google.com/");
    }


    private void initWebView() {
        webView.setWebChromeClient(new MyWebChromeClient(this));
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
