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
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import ir.hezareh.park.Adapters.CommentRecycler;
import ir.hezareh.park.Adapters.EqualSpacingItemDecoration;
import ir.hezareh.park.Adapters.NewsComponentRecycler;
import ir.hezareh.park.models.ModelComponent;
import ir.hezareh.park.models.NewsDetails;


public class NewsDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = WebviewActivity.class
            .getSimpleName();
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView commentRecyclerView;
    RecyclerView newsRecyclerView;
    int screenHeight;
    int width;
    NewsDetails newsDetails;
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

        width = new Utils(getApplicationContext()).getDisplayMetrics().widthPixels;
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
        commentRecyclerView = (RecyclerView) findViewById(R.id.comments_recycler);
        newsRecyclerView = (RecyclerView) findViewById(R.id.news_recycler);

        //initWebView();
        //webView.loadUrl(url);

        Picasso.with(getApplicationContext())
                .load("https://file.digi-kala.com/digikala/Image/Webstore/Banner/1396/10/11/c728f1bf.jpg")
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
                //webView.loadUrl(url);
                //renderPost()
                getNewsDetails(new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(List<ModelComponent> result) {

                    }

                    @Override
                    public void onSuccessResponseNewsDetails(NewsDetails newsDetails) {
                        addNews();
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
        getNewsDetails(new VolleyCallback() {
            @Override
            public void onSuccessResponse(List<ModelComponent> result) {

            }

            @Override
            public void onSuccessResponseNewsDetails(NewsDetails newsDetails) {
                addNews();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    private void addNews() {
        CommentRecycler commentRecycler = new CommentRecycler(getApplicationContext(), newsDetails.getRelatedTopics().getItem());

        LinearLayout.LayoutParams CommentsRecyclerLayoutParams = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        CommentsRecyclerLayoutParams.gravity = Gravity.CENTER_VERTICAL;
        commentRecyclerView.setLayoutParams(CommentsRecyclerLayoutParams);

        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
        commentRecyclerView.addItemDecoration(new EqualSpacingItemDecoration(10, EqualSpacingItemDecoration.VERTICAL));
        commentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        commentRecyclerView.setAdapter(commentRecycler);


        NewsComponentRecycler newsComponentRecycler = new NewsComponentRecycler(getApplicationContext(), newsDetails.getRelatedTopics());


        LinearLayout.LayoutParams NewsRecyclerLayoutParams = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        NewsRecyclerLayoutParams.gravity = Gravity.CENTER_VERTICAL;
        newsRecyclerView.setLayoutParams(NewsRecyclerLayoutParams);

        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true));
        newsRecyclerView.addItemDecoration(new EqualSpacingItemDecoration(10, EqualSpacingItemDecoration.VERTICAL));
        newsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        newsRecyclerView.setAdapter(newsComponentRecycler);

        String text = "<html> <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "<style type=\"text/css\">\n" +
                "                /** Specify a font named \"MyFont\",\n" +
                "                and specify the URL where it can be found: */\n" +
                "                @font-face {\n" +
                "                    font-family: \"MyFont\"; \n " +
                "                    src: url('file:///android_asset/fonts/irsans.ttf');\n" +
                "                }\n" +
                "                p { font-family:\"MyFont\";  line-height: 30px; padding: 5px; font-size: 14px;}" +
                "            </style>" +
                "    <title>Title</title>\n" +
                "</head>  <body>"
                + "<p align=\"justify\"" +
                "dir=\"rtl\">"
                + newsDetails.getDescription()
                + "</p> "
                + "</body></html>";


        webView.loadDataWithBaseURL(null, text, "text/html", "utf-8", null);

        ((TextView) findViewById(R.id.date_time)).setText(newsDetails.getCreatedOnUTC());
        ((TextView) findViewById(R.id.likes)).setText(String.valueOf(newsDetails.getNumberOfLikes()));
        ((TextView) findViewById(R.id.dislikes)).setText(String.valueOf(newsDetails.getNumberOfDislikes()));
        ((TextView) findViewById(R.id.comments)).setText(String.valueOf(newsDetails.getNumberOfComments()));

    }


    private void renderPost() {
        //webView.loadUrl("https://www.google.com/");
    }

    public void getNewsDetails(final VolleyCallback callback) {

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://arefnaghshin.ir/newsdetails", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String jsonResponse = new String(response.toString().getBytes("ISO-8859-1"));
                    Log.d(TAG, jsonResponse);
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<NewsDetails>() {
                    }.getType();
                    newsDetails = gson.fromJson(new String(response.toString().getBytes("ISO-8859-1")), collectionType);


                    // Sets the Toolbar to act as the ActionBar for this Activity window.
                    // Make sure the toolbar exists in the activity and is not null
                    //setSupportActionBar(toolbar);

                    //Root_Layout.addView(mTopToolbar);


                    Log.d(TAG, newsDetails.getDescription() + "");



                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                callback.onSuccessResponseNewsDetails(newsDetails);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //hidepDialog();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq);

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
