package ir.hezareh.park;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import ir.hezareh.park.Adapters.CommentRecycler;
import ir.hezareh.park.Adapters.EqualSpacingItemDecoration;
import ir.hezareh.park.Adapters.NewsComponentRecycler;
import ir.hezareh.park.models.NewsDetails;


public class NewsDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = WebviewActivity.class
            .getSimpleName();
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView commentRecyclerView;
    RecyclerView newsRecyclerView;
    int screenHeight;
    int width;
    TextView likesCount;
    TextView commentsCount;
    TextView dislikesCount;
    TextView date;
    Button show_more;
    CommentRecycler commentRecycler;
    NewsComponentRecycler relatedNewsComponentRecycler;
    int ID;
    private String url = "https://www.digikala.com/";
    private WebView webView;
    private ProgressBar progressBar;
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
                swipeRefreshLayout.setRefreshing(true);
                new networking().getNewsDetails(new networking.NewsDetailsResponseListener() {
                    @Override
                    public void requestStarted() {

                    }

                    @Override
                    public void requestCompleted(NewsDetails newsDetails) {
                        addNews(newsDetails);
                        swipeRefreshLayout.setRefreshing(false);
                        ID = newsDetails.getID();
                        fab.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void requestEndedWithError(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });


    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        //webView.loadUrl(url);
        //renderPost();

        new networking().getNewsDetails(new networking.NewsDetailsResponseListener() {
            @Override
            public void requestStarted() {

            }

            @Override
            public void requestCompleted(NewsDetails newsDetails) {
                addNews(newsDetails);
                swipeRefreshLayout.setRefreshing(false);
                ID = newsDetails.getID();
                findViewById(R.id.fab).setVisibility(View.VISIBLE);

                //Log.e("can",webView.canScrollVertically(0)+"");
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void addNews(NewsDetails newsDetails) {

        commentRecycler = new CommentRecycler(getApplicationContext(), newsDetails.getComments());
        commentRecyclerView.setAdapter(commentRecycler);

        relatedNewsComponentRecycler = new NewsComponentRecycler(getApplicationContext(), newsDetails.getRelatedTopics());
        newsRecyclerView.setAdapter(relatedNewsComponentRecycler);

        String text = "<html> <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "<style type=\"text/css\">\n" +
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
                + newsDetails.getDescription()
                + "</p> "
                + "</body></html>";

        webView.loadDataWithBaseURL(null, text, "text/html", "utf-8", null);

        date.setVisibility(View.VISIBLE);
        likesCount.setVisibility(View.VISIBLE);
        dislikesCount.setVisibility(View.VISIBLE);
        commentsCount.setVisibility(View.VISIBLE);

        date.setText(newsDetails.getCreatedOnUTC());
        likesCount.setText(String.valueOf(newsDetails.getNumberOfLikes()));
        dislikesCount.setText(String.valueOf(newsDetails.getNumberOfDislikes()));
        commentsCount.setText(String.valueOf(newsDetails.getNumberOfComments()));

        Picasso.with(getApplicationContext())
                .load("https://file.digi-kala.com/digikala/Image/Webstore/Banner/1396/11/1/3cde18e7.jpg")
                .fit()
                .into((ImageView) findViewById(R.id.news_detail_header));
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
                //webView.loadUrl(url);
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //progressBar.setVisibility(View.GONE);
                //NewsRecycler.setAdapter(myRecyclerAdapter);
                swipeRefreshLayout.setRefreshing(false);
                //Log.e("can",webView.canScrollVertically(0)+"");
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

    public void onShowMoreClicked(View view) {

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
    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;

        }
    }
}


