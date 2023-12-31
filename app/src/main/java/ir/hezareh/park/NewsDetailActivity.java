package ir.hezareh.park;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import ir.hezareh.park.Adapters.CommentRecycler;
import ir.hezareh.park.Adapters.EqualSpacingItemDecoration;
import ir.hezareh.park.Adapters.NewsComponentRecycler;
import ir.hezareh.park.Component.CommentDialog;
import ir.hezareh.park.DataLoading.DbHandler;
import ir.hezareh.park.DataLoading.OfflineDataLoader;
import ir.hezareh.park.DataLoading.networking;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.NewsDetails;

import static ir.hezareh.park.Util.Utils.MessageType.confirmation;
import static ir.hezareh.park.Util.Utils.MessageType.duplicate_entry;
import static ir.hezareh.park.Util.Utils.MessageType.network_error;
import static ir.hezareh.park.Util.Utils.MessageType.server_error;


public class NewsDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    public static final String TAG = NewsDetailActivity.class
            .getSimpleName();
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView commentRecyclerView;
    RecyclerView newsRecyclerView;
    int screenHeight;
    int width;
    TextView likesCount, commentsCount, dislikesCount, date;
    Button show_more, like_btn, dislike_btn;

    CommentRecycler commentRecycler;
    NewsComponentRecycler relatedNewsComponentRecycler;
    int ID;
    DbHandler dbHandler = new DbHandler(this);
    private WebView webView;
    private float m_downX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        layout.setTitleEnabled(true);
        layout.setTitle("");
        new Utils(getApplicationContext()).overrideFonts(findViewById(R.id.news), "BYekan");
        width = new Utils(getApplicationContext()).getDisplayMetrics().widthPixels;
        screenHeight = new Utils(getApplicationContext()).getDisplayMetrics().heightPixels;

        likesCount = (TextView) findViewById(R.id.likes);
        date = (TextView) findViewById(R.id.date_time);
        commentsCount = (TextView) findViewById(R.id.comments);
        dislikesCount = (TextView) findViewById(R.id.dislikes);
        show_more = (Button) findViewById(R.id.show_more);
        like_btn = (Button) findViewById(R.id.like_btn);
        dislike_btn = (Button) findViewById(R.id.dislike_btn);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentDialog dialogClass = new CommentDialog(NewsDetailActivity.this, ID);
                dialogClass.show();

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        webView = (WebView) findViewById(R.id.webView);
        commentRecyclerView = (RecyclerView) findViewById(R.id.comments_recycler);
        newsRecyclerView = (RecyclerView) findViewById(R.id.news_recycler);

        like_btn.setOnClickListener(this);
        dislike_btn.setOnClickListener(this);
        show_more.setOnClickListener(this);


        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true));
        newsRecyclerView.addItemDecoration(new EqualSpacingItemDecoration(10, EqualSpacingItemDecoration.HORIZONTAL));
        newsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
        commentRecyclerView.addItemDecoration(new EqualSpacingItemDecoration(10, EqualSpacingItemDecoration.VERTICAL));
        commentRecyclerView.setItemAnimator(new DefaultItemAnimator());


        initWebView();
        //webView.loadUrl(url);

        swipeRefreshLayout.setOnRefreshListener(this);


        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                if (getIntent().getExtras() != null) {
                    if (new Utils(getApplicationContext()).isConnectedToInternet()) {
                        new networking(getApplicationContext()).getNewsDetails(new networking.NewsDetailsResponseListener() {
                            @Override
                            public void requestStarted() {
                                swipeRefreshLayout.setRefreshing(true);
                            }

                            @Override
                            public void requestCompleted(NewsDetails newsDetails) {
                                addNews(newsDetails);
                                swipeRefreshLayout.setRefreshing(false);
                                ID = newsDetails.getID();
                                fab.setVisibility(View.VISIBLE);
                                like_btn.setVisibility(View.VISIBLE);
                                dislike_btn.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void requestEndedWithError(VolleyError error) {
                                new Utils(getApplicationContext()).showToast(server_error, NewsDetailActivity.this);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }, getIntent().getExtras().getString("URL"));


                    } else {
                        swipeRefreshLayout.setRefreshing(true);
                        addNews(new OfflineDataLoader(getApplicationContext()).ReadOfflineNewsDetails(getIntent().getExtras().getInt("NewsID")));
                        swipeRefreshLayout.setRefreshing(false);
                        ID = getIntent().getExtras().getInt("NewsID");
                    }
                }
            }
        });


    }

    @Override
    public void onRefresh() {


        renderPost();
        if (getIntent().getExtras() != null) {
            if (new Utils(getApplicationContext()).isConnectedToInternet()) {
                new networking(getApplicationContext()).getNewsDetails(new networking.NewsDetailsResponseListener() {
                    @Override
                    public void requestStarted() {
                        swipeRefreshLayout.setRefreshing(true);
                    }

                    @Override
                    public void requestCompleted(NewsDetails newsDetails) {
                        addNews(newsDetails);
                        swipeRefreshLayout.setRefreshing(false);
                        ID = newsDetails.getID();
                        findViewById(R.id.fab).setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void requestEndedWithError(VolleyError error) {
                        new Utils(getApplicationContext()).showToast(server_error, NewsDetailActivity.this);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, getIntent().getExtras().getString("URL"));
            } else {
                swipeRefreshLayout.setRefreshing(true);
                addNews(new OfflineDataLoader(getApplicationContext()).ReadOfflineNewsDetails(getIntent().getExtras().getInt("NewsID")));
                swipeRefreshLayout.setRefreshing(false);
                ID = getIntent().getExtras().getInt("NewsID");
            }
        }

    }

    private void addNews(NewsDetails newsDetails) {

        if (newsDetails != null) {

            commentRecycler = new CommentRecycler(getApplicationContext(), newsDetails.getComments());
            commentRecyclerView.setAdapter(commentRecycler);

            relatedNewsComponentRecycler = new NewsComponentRecycler(getApplicationContext(), newsDetails.getRelatedTopics(), false);
            newsRecyclerView.setAdapter(relatedNewsComponentRecycler);

            String text = "<html> <head>\n" +
                    "    <meta  charset=\"UTF-8\">\n" +
                    " <style type=\"text/css\">\n" +
                    "                /** Specify a font named \"MyFont\",\n" +
                    "                and specify the URL where it can be found: */\n" +
                    "                @font-face {\n" +
                    "                    font-family: \"MyFont\"; \n " +
                    "                    src: url('file:///android_asset/fonts/irsans.ttf');\n" +
                    "                }\n" +
                    "               @font-face {\n" +
                    "               font-family: \"BYekan\"; \n" +
                    "               src: url('file:///android_asset/fonts/BYekan.ttf'); \n" +
                    "               }\n" +
                    "               .text { font-family:\"MyFont\";  line-height: 30px; padding: 5px; font-size: 14px;}  \n" +
                    "               .header { font-family:\"BYekan\";  line-height: 25px; padding: 5px; font-size: 18px;}" +
                    "            </style>" +
                    "    <title>Title</title>\n" +
                    "</head> " +
                    " <p class=\"header\"  align=\"justify\"" +
                    " dir=\"rtl\" >" +
                    newsDetails.getTittle() +
                    " </p> " +
                    " <body>"
                    + "<p class=\"text\" align=\"justify\"" +
                    "dir=\"rtl\">"
                    + newsDetails.getDetail()
                    + "</p> "
                    + "</body></html>";

            webView.loadDataWithBaseURL(null, text, "text/html", "utf-8", null);

            //webView.loadUrl(newsDetails.getDetail());


            date.setVisibility(View.VISIBLE);
            likesCount.setVisibility(View.VISIBLE);
            dislikesCount.setVisibility(View.VISIBLE);
            commentsCount.setVisibility(View.VISIBLE);

            date.setText(newsDetails.getCreatedOnUTC());
            likesCount.setText(String.valueOf(newsDetails.getNumberOfLikes()));
            dislikesCount.setText(String.valueOf(newsDetails.getNumberOfDislikes()));
            commentsCount.setText(String.valueOf(newsDetails.getNumberOfComments()));

            Picasso.with(getApplicationContext())
                    .load(newsDetails.getImagePath())
                    .fit()
                    .into((ImageView) findViewById(R.id.news_detail_header));
        }

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
                //swipeRefreshLayout.setRefreshing(true);
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
                //swipeRefreshLayout.setRefreshing(false);
                invalidateOptionsMenu();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //swipeRefreshLayout.setRefreshing(false);
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

        if (!new Utils(getApplicationContext()).isConnectedToInternet()) { // loading offline
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        /*webView.setOnTouchListener(new View.OnTouchListener() {
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

        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_more:
                if (webView.getMeasuredHeight() == new Utils(getApplicationContext()).dpToPx(250)) {
                    webView.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    int height = webView.getMeasuredHeight();
                    Utils.expand(webView, height);
                    show_more.setText("نمایش کمتر");
                } else {
                    //initial height is 250dp
                    Utils.collapse(webView, new Utils(getApplicationContext()).dpToPx(250));
                    show_more.setText("نمایش بیشتر");
                }
                break;

            case R.id.like_btn:
                new Utils(getApplicationContext()).scaleView(like_btn, 0.3f, 1f);
                if (new Utils(getApplicationContext()).isConnectedToInternet()) {
                    if (dbHandler.addVote(ID, 1)) {
                        new networking(getApplicationContext()).postLike(ID, new networking.PostLikeResponseListener() {
                            @Override
                            public void requestStarted() {

                            }

                            @Override
                            public void requestCompleted(String response) {
                                Drawable mDrawable = getResources().getDrawable(R.drawable.ic_thumb_up_green_24dp);
                                mDrawable.setColorFilter(new
                                        PorterDuffColorFilter(Color.parseColor("#FF24DC00"), PorterDuff.Mode.MULTIPLY));
                                new Utils(getApplicationContext()).showToast(confirmation, NewsDetailActivity.this);
                            }

                            @Override
                            public void requestEndedWithError(VolleyError error) {
                                new Utils(getApplicationContext()).showToast(server_error, NewsDetailActivity.this);
                            }
                        });

                    } else {
                        new Utils(getApplicationContext()).showToast(duplicate_entry, NewsDetailActivity.this);
                    }
                } else {
                    new Utils(getApplicationContext()).showToast(network_error, NewsDetailActivity.this);
                }

                break;

            case R.id.dislike_btn:
                new Utils(getApplicationContext()).scaleView(dislike_btn, 0.3f, 1f);
                if (new Utils(getApplicationContext()).isConnectedToInternet()) {
                    if (dbHandler.addVote(ID, 1)) {
                        new networking(getApplicationContext()).postDislike(ID, new networking.PostDislikeResponseListener() {
                            @Override
                            public void requestStarted() {

                            }

                            @Override
                            public void requestCompleted(String response) {
                                dbHandler.addVote(ID, 1);
                                Drawable mDrawable = getResources().getDrawable(R.drawable.ic_thumb_down_red_24dp);
                                mDrawable.setColorFilter(new
                                        PorterDuffColorFilter(Color.parseColor("#FFDE0000"), PorterDuff.Mode.MULTIPLY));
                                new Utils(getApplicationContext()).showToast(confirmation, NewsDetailActivity.this);
                            }

                            @Override
                            public void requestEndedWithError(VolleyError error) {
                                new Utils(getApplicationContext()).showToast(server_error, NewsDetailActivity.this);

                            }
                        });
                    } else {
                        new Utils(getApplicationContext()).showToast(duplicate_entry, NewsDetailActivity.this);
                    }
                } else {
                    new Utils(getApplicationContext()).showToast(network_error, NewsDetailActivity.this);
                }
                break;

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


