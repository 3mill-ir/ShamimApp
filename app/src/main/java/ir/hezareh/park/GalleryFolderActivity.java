package ir.hezareh.park;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import ir.hezareh.park.Adapters.GalleryFolderAdapter;
import ir.hezareh.park.Adapters.HomeSideMenuListAdapter;
import ir.hezareh.park.Component.progressLoading;
import ir.hezareh.park.DataLoading.OfflineDataLoader;
import ir.hezareh.park.DataLoading.networking;
import ir.hezareh.park.Util.GridSpacingItemDecoration;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.GalleryModel;
import ir.hezareh.park.models.sidemenu;

import static ir.hezareh.park.HomeScreen.clickedID;
import static ir.hezareh.park.HomeScreen.global;
import static ir.hezareh.park.HomeScreen.pos;
import static ir.hezareh.park.Util.Utils.MessageType.server_error;


public class GalleryFolderActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = GalleryFolderActivity.class.getSimpleName();
    public RecyclerView GalleryRecycler;
    public SwipeRefreshLayout swipeRefreshLayout;
    GalleryFolderAdapter galleryFolderAdapter;

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
        setContentView(R.layout.activity_gallery_folders);

        setSideMenu();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        GalleryRecycler = findViewById(R.id.gallery_recycler);
        swipeRefreshLayout = findViewById(R.id.refresh_layout);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), GridSpacingItemDecoration.calculateNoOfColumns(getApplicationContext()));
        GalleryRecycler.addItemDecoration(new GridSpacingItemDecoration(GridSpacingItemDecoration.calculateNoOfColumns(getApplicationContext()), new Utils(getApplicationContext()).dpToPx(5), true));
        GalleryRecycler.setLayoutManager(mLayoutManager);
        GalleryRecycler.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout.setOnRefreshListener(this);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (new Utils(getApplicationContext()).isConnectedToInternet()) {

                    new networking(getApplicationContext()).getFolderGallery(new networking.FolderGalleryResponseListener() {
                        @Override
                        public void requestStarted() {

                        }

                        @Override
                        public void requestCompleted(List<GalleryModel> Gallery) {
                            galleryFolderAdapter = new GalleryFolderAdapter(getApplicationContext(), Gallery);
                            GalleryRecycler.setAdapter(galleryFolderAdapter);
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void requestEndedWithError(VolleyError error) {
                            new Utils(getApplicationContext()).showToast(server_error, GalleryFolderActivity.this);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                } else {
                    OfflineDataLoader offlineDataLoader = new OfflineDataLoader(getApplicationContext());
                    if (offlineDataLoader.ReadOfflineFolderGallery() != null) {
                        galleryFolderAdapter = new GalleryFolderAdapter(getApplicationContext(), new OfflineDataLoader(getApplicationContext()).ReadOfflineFolderGallery());
                        GalleryRecycler.setAdapter(galleryFolderAdapter);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }

            }
        });

        ((TextView) findViewById(R.id.header_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));
        ((TextView) findViewById(R.id.header_text)).setText("گالری تصاویر");

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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRefresh() {
        new networking(getApplicationContext()).getFolderGallery(new networking.FolderGalleryResponseListener() {
            @Override
            public void requestStarted() {

            }

            @Override
            public void requestCompleted(List<GalleryModel> Gallery) {
                galleryFolderAdapter = new GalleryFolderAdapter(getApplicationContext(), Gallery);
                GalleryRecycler.setAdapter(galleryFolderAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                new Utils(getApplicationContext()).showToast(server_error, GalleryFolderActivity.this);

            }
        });
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
        loading = new progressLoading(GalleryFolderActivity.this);
        //create a path for store offline reading later


        LayoutInflater inflater = getLayoutInflater();
        ViewGroup secondLevelListViewHeader = (ViewGroup) inflater.inflate(R.layout.menulistheader, secondLevelListView, false);
        secondLevelListView.addHeaderView(secondLevelListViewHeader, null, false);


        if (HomeScreen.getList() != null) {
            list = new ArrayList<>(HomeScreen.getList());

            firstLevelListView.setVisibility(View.VISIBLE);
            secondLevelListView.setVisibility(View.GONE);

            sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), new HomeScreen().getChildListMenuName(list, 0, true));
            firstLevelListView.setAdapter(sideMenuListAdapter);
        }

        secondLevelListViewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saving the last position the user clicked for the time back is pressed
                if (pos == 0) {
                    firstLevelListView.setVisibility(View.VISIBLE);
                    secondLevelListView.setVisibility(View.GONE);
                    HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), new HomeScreen().getChildListMenuName(list, 0, true));
                    global.remove(pos);
                    firstLevelListView.setAdapter(sideMenuListAdapter);

                } else {
                    --pos;
                    HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), new HomeScreen().getChildListMenuName(list, global.get(pos), false));
                    secondLevelListView.setAdapter(sideMenuListAdapter);
                    global.remove(pos + 1);
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
                    global.add((int) id);
                    sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), new HomeScreen().getChildListMenuName(list, (int) id, false));
                    secondLevelListView.setAdapter(sideMenuListAdapter);

                    //new Component(getApplicationContext()).setClickListener(view,list.get(position).getFunctionality().toString(),list.get(position).getUrl().toString(),0);
                } else {

                    for (ir.hezareh.park.models.sidemenu sidemenu : list) {
                        if (sidemenu.getID() == (int) id) {
                            Log.d("Func", sidemenu.getFunctionality() + "");
                            Log.d("Url", sidemenu.getUrl() + "");
                            if (sidemenu.getFunctionality() != null) {
                                if (drawer.isDrawerOpen(GravityCompat.END)) {
                                    drawer.closeDrawer(GravityCompat.END);
                                }
                                if (sidemenu.getFunctionality().toString().equals("WebView")) {
                                    Intent intent = new Intent(getApplicationContext(), WebviewActivity.class);
                                    intent.putExtra("URL", sidemenu.getUrl().toString());
                                    //0 is for detect if is from side menu
                                    intent.putExtra("Button", "0");
                                    startActivity(intent);
                                    finish();
                                } else if (sidemenu.getFunctionality().toString().equals("NewsList")) {
                                    Log.d(TAG, "onItemClick: " + (int) id);
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

        secondLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!new HomeScreen().getChildListMenuName(list, (int) id, false).isEmpty()) {
                    //pos is for storing last item ID clicked by user
                    ++pos;
                    global.add((int) id);
                    sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(),
                            new HomeScreen().getChildListMenuName(list, (int) id, false));
                    secondLevelListView.setAdapter(sideMenuListAdapter);
                } else {
                    for (ir.hezareh.park.models.sidemenu sidemenu : list) {
                        if (sidemenu.getID() == (int) id) {
                            Log.d("Func", sidemenu.getFunctionality() + "");
                            Log.d("Url", sidemenu.getUrl() + "");
                            if (sidemenu.getFunctionality() != null) {
                                if (drawer.isDrawerOpen(GravityCompat.END)) {
                                    drawer.closeDrawer(GravityCompat.END);
                                }
                                //webView.loadUrl(sidemenu.getUrl().toString());
                                //webView.loadUrl(sidemenu.getUrl().toString());
                                if (sidemenu.getFunctionality().toString().equals("WebView")) {
                                    Intent intent = new Intent(getApplicationContext(), WebviewActivity.class);
                                    intent.putExtra("URL", sidemenu.getUrl().toString());
                                    //0 is for detect if is from side menu
                                    intent.putExtra("Button", "0");
                                    startActivity(intent);
                                    finish();
                                } else if (sidemenu.getFunctionality().toString().equals("NewsList")) {
                                    Log.d(TAG, "onItemClick: " + (int) id);
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
}
