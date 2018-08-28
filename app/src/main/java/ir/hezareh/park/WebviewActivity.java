package ir.hezareh.park;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.util.ArrayList;

import ir.hezareh.park.Adapters.HomeSideMenuListAdapter;
import ir.hezareh.park.Component.progressLoading;
import ir.hezareh.park.DataLoading.networking;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.sidemenu;

import static ir.hezareh.park.HomeScreen.clickedID;
import static ir.hezareh.park.HomeScreen.global;
import static ir.hezareh.park.HomeScreen.pos;

public class WebviewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = WebviewActivity.class
            .getSimpleName();
    SwipeRefreshLayout swipeRefreshLayout;
    WebView webView;
    String url;
    String isFromMenu;
    int width;
    ListView firstLevelListView;
    ListView secondLevelListView;
    ArrayList<sidemenu> list;
    progressLoading loading;
    DrawerLayout drawer;
    HomeSideMenuListAdapter sideMenuListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        swipeRefreshLayout = findViewById(R.id.swipeContainer);

        webView = findViewById(R.id.webView);

        ((TextView) findViewById(R.id.header_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));


        if (getIntent().getExtras() != null) {
            initWebView();
            url = getIntent().getStringExtra("URL");
            isFromMenu = getIntent().getStringExtra("Button");
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
                    if (isFromMenu.equals("0"))
                        webView.loadUrl(url);
                    else
                        getWebView(getIntent().getExtras().getString("URL"));
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSideMenu();

    }

    private void setSideMenu() {
        drawer = findViewById(R.id.drawer_layout);

        new Utils(getApplicationContext()).overrideFonts(drawer, "BYekan");

        findViewById(R.id.drawer_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        firstLevelListView = findViewById(R.id.first_level_menu);
        secondLevelListView = findViewById(R.id.second_level_menu);

        width = new Utils(getApplicationContext()).getDisplayMetrics().widthPixels;
        loading = new progressLoading(WebviewActivity.this);
        //create a path for store offline reading later


        LayoutInflater inflater = getLayoutInflater();
        final ViewGroup secondLevelListViewHeader = (ViewGroup) inflater.inflate(R.layout.menulistheader, secondLevelListView, false);
        secondLevelListView.addHeaderView(secondLevelListViewHeader, null, false);

        if (HomeScreen.getList() != null) {
            list = new ArrayList<>(HomeScreen.getList());
            Log.d(TAG, "setSideMenu: " + global.size());
            if (global.size() > 0) {
                Log.d(TAG, "setSideMenu: " + global.get(pos));

                firstLevelListView.setVisibility(View.GONE);
                secondLevelListView.setVisibility(View.VISIBLE);

                sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), new HomeScreen().getChildListMenuName(list, global.get(pos), false));
                secondLevelListView.setAdapter(sideMenuListAdapter);
            }
        }

        secondLevelListViewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //saving the last position the user clicked for the time back is pressed
                Log.d(TAG, "onClick: " + pos);
                if (pos == 0) {
                    firstLevelListView.setVisibility(View.VISIBLE);
                    secondLevelListView.setVisibility(View.GONE);
                    sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), new HomeScreen().getChildListMenuName(list, 0, true));
                    HomeScreen.global.remove(pos);
                    firstLevelListView.setAdapter(sideMenuListAdapter);

                } else {
                    --pos;
                    sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), new HomeScreen().getChildListMenuName(list, HomeScreen.global.get(pos), false));
                    secondLevelListView.setAdapter(sideMenuListAdapter);
                    HomeScreen.global.remove(pos + 1);
                }
            }
        });


        firstLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!new HomeScreen().getChildListMenuName(list, (int) id, false).isEmpty()) {
                    firstLevelListView.setVisibility(View.GONE);
                    secondLevelListView.setVisibility(View.VISIBLE);
                    //Log.e("ID", "" + (int) id);
                    HomeScreen.global.add((int) id);
                    sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), new HomeScreen().getChildListMenuName(list, (int) id, false));
                    secondLevelListView.setAdapter(sideMenuListAdapter);

                    //new Component(getApplicationContext()).setClickListener(view,list.get(position).getFunctionality().toString(),list.get(position).getUrl().toString(),0);
                } else {
                    clickedID = (int) id;

                    for (sidemenu sidemenu : list) {
                        if (sidemenu.getID() == (int) id) {
                            Log.d("Func", sidemenu.getFunctionality() + "");
                            Log.d("Url", sidemenu.getUrl() + "");
                            if (sidemenu.getFunctionality() != null) {
                                if (drawer.isDrawerOpen(GravityCompat.END)) {
                                    drawer.closeDrawer(GravityCompat.END);
                                }
                                if (sidemenu.getFunctionality().toString().equals("WebView")) {
                                    Log.d(TAG, "onItemClick: this is first level of webview************");
                                    webView.loadUrl(sidemenu.getUrl().toString());
                                    firstLevelListView.setAdapter(sideMenuListAdapter);
                                } else if (sidemenu.getFunctionality().toString().equals("NewsList")) {
                                    Intent intent = new Intent(getApplicationContext(), NewsCategory.class);
                                    intent.putExtra("URL", sidemenu.getUrl().toString());
                                    intent.putExtra("ItemPos", position - 1);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(0, 0);
                                }
                            }
                        }
                    }
                }
            }
        });

        secondLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!new HomeScreen().getChildListMenuName(list, (int) id, false).isEmpty()) {
                    //pos is for storing last item ID clicked by user
                    ++pos;
                    HomeScreen.global.add((int) id);
                    sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(),
                            new HomeScreen().getChildListMenuName(list, (int) id, false));
                    secondLevelListView.setAdapter(sideMenuListAdapter);
                } else {
                    for (sidemenu sidemenu : list) {
                        if (sidemenu.getID() == (int) id) {
                            Log.d("Func", sidemenu.getFunctionality() + "");
                            Log.d("Url", sidemenu.getUrl() + "");
                            if (sidemenu.getFunctionality() != null) {
                                if (drawer.isDrawerOpen(GravityCompat.END)) {
                                    drawer.closeDrawer(GravityCompat.END);
                                }
                                if (sidemenu.getFunctionality().toString().equals("WebView")) {
                                    webView.loadUrl(sidemenu.getUrl().toString());
                                    secondLevelListView.setAdapter(sideMenuListAdapter);
                                } else if (sidemenu.getFunctionality().toString().equals("NewsList")) {
                                    Intent intent = new Intent(getApplicationContext(), NewsCategory.class);
                                    intent.putExtra("URL", sidemenu.getUrl().toString());
                                    intent.putExtra("ItemPos", position - 1);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(0, 0);
                                }
                            }
                        }
                    }
                    clickedID = (int) id;
                }

            }
        });
    }

    @Override
    public void onRefresh() {
        if (getIntent().getExtras() != null) {
            if (isFromMenu.equals("0"))
                webView.loadUrl(url);
            else
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
