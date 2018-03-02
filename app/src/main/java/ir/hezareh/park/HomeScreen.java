package ir.hezareh.park;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import ir.hezareh.park.Adapters.HomeSideMenuListAdapter;
import ir.hezareh.park.Component.Component;
import ir.hezareh.park.Component.MyPieChart;
import ir.hezareh.park.DataLoading.AppUpdate;
import ir.hezareh.park.DataLoading.OfflineDataLoader;
import ir.hezareh.park.DataLoading.networking;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.Util.progressLoading;
import ir.hezareh.park.models.ModelComponent;
import ir.hezareh.park.models.sidemenu;

public class HomeScreen extends AppCompatActivity {

    public static final String TAG = HomeScreen.class
            .getSimpleName();
    private static long back_pressed = 0L;
    int width;
    ListView firstLevelListView;
    ListView secondLevelListView;

    ArrayList<sidemenu> list = new ArrayList<>();
    ArrayList<Integer> global = new ArrayList<>();
    progressLoading loading;
    int pos = 0;
    DrawerLayout drawer;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_list, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
            else
                new Utils(getApplicationContext()).showToast("exit", HomeScreen.this);
            back_pressed = System.currentTimeMillis();
        }
    }





    public void addComponents(List<ModelComponent> modelComponents) {
        LinearLayout Root_Layout = findViewById(R.id.main_layout);


        if (modelComponents != null) {
            for (ModelComponent component : modelComponents) {

                switch (component.getComponent()) {
                    case "slider":
                        Root_Layout.addView(new Component(HomeScreen.this).Slider(width, 0, component.getItem()));
                        break;
                    case "ButtonGalleryRow":
                        Root_Layout.addView(new Component(HomeScreen.this).GalleryButton(width, component, "GalleryButtons"));
                        break;
                    case "NewsList":
                        Root_Layout.addView(new Component(HomeScreen.this).News(width, 0, component));
                        break;
                    case "RowButton":
                        Root_Layout.addView(new Component(HomeScreen.this).ButtonsRow(width, component));
                        break;
                    case "GalleryButtonRow":
                        Root_Layout.addView(new Component(HomeScreen.this).GalleryButton(width, component, "ButtonsGallery"));
                        break;
                    case "Diagram":
                        Root_Layout.addView(new MyPieChart(HomeScreen.this, width, width / 2, component).getItem());
                        break;
                    case "Poll":
                        Root_Layout.addView(new Component(HomeScreen.this).pollQuestion(width, 0, component));
                        break;
                }
            }
        }

        new Utils(getApplicationContext()).overrideFonts(Root_Layout, "BYekan");


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        drawer = findViewById(R.id.drawer_layout);

        width = new Utils(getApplicationContext()).getDisplayMetrics().widthPixels;
        loading = new progressLoading(HomeScreen.this);
        //create a path for store offline reading later
        new OfflineDataLoader(getApplicationContext()).createExternalStoragePath();

        if (new Utils(getApplicationContext()).isConnectedToInternet()) {
            new networking(getApplicationContext()).getMainJson(new networking.MainJsonResponseListener() {
                @Override
                public void requestStarted() {
                    loading.show();
                }

                @Override
                public void requestCompleted(List<ModelComponent> modelComponents) {

                    addComponents(modelComponents);
                    loading.dismiss();
                }

                @Override
                public void requestEndedWithError(VolleyError error) {
                    // hide the progress dialog
                    loading.dismiss();
                    new Utils(getApplicationContext()).showToast("server_error", HomeScreen.this);
                    //swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            addComponents(new OfflineDataLoader(getApplicationContext()).ReadOfflineMainJson());
        }

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        //setSupportActionBar(toolbar);


        try {
            new AppUpdate(getApplicationContext()).check_Version();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.side_menu_header_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));
        ((TextView) findViewById(R.id.header_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));


        findViewById(R.id.drawer_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });


        firstLevelListView = findViewById(R.id.first_level_menu);
        secondLevelListView = findViewById(R.id.second_level_menu);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup secondLevelListViewHeader = (ViewGroup) inflater.inflate(R.layout.menulistheader, secondLevelListView, false);
        secondLevelListView.addHeaderView(secondLevelListViewHeader, null, false);


        secondLevelListViewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*for (int i = 0; i < global.size(); i++) {
                    Log.i("globals" + i, global.get(i).toString());
                }*/

                //saving the last position the user clicked for the time back is pressed

                if (pos == 0) {
                    firstLevelListView.setVisibility(View.VISIBLE);
                    secondLevelListView.setVisibility(View.GONE);
                    HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), new HomeScreen().getChildListMenuName(list, 0, true));
                    global.remove(pos);
                    firstLevelListView.setAdapter(sideMenuListAdapter);

                } else {
                    --pos;
                    HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(list, global.get(pos), false));
                    secondLevelListView.setAdapter(sideMenuListAdapter);
                    global.remove(pos + 1);
                }
                //Log.d("position", pos + "");

                /*for (int i = 0; i < global.size(); i++) {
                    Log.i("after remove globals" + i, global.get(i).toString());
                }*/

            }
        });
        if (new Utils(getApplicationContext()).isConnectedToInternet()) {
            new networking(getApplicationContext()).getMainSideMenu(new networking.SideMenuResponseListener() {
                @Override
                public void requestStarted() {

                }

                @Override
                public void requestCompleted(ArrayList<sidemenu> sidemenus) {

                    for (sidemenu _menu : sidemenus) {
                        list.add(_menu);
                        //Log.d("sidemenu1", _menu.getFunctionality().toString());

                    }
                    HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(sidemenus, 0, true));
                    firstLevelListView.setAdapter(sideMenuListAdapter);
                }

                @Override
                public void requestEndedWithError(VolleyError error) {
                    new Utils(getApplicationContext()).showToast("server_error", HomeScreen.this);
                }
            });
        }


        firstLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!getChildListMenuName(list, (int) id, false).isEmpty()) {
                    firstLevelListView.setVisibility(View.GONE);
                    secondLevelListView.setVisibility(View.VISIBLE);
                    //Log.e("ID", "" + (int) id);
                    global.add((int) id);
                    HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), new HomeScreen().getChildListMenuName(list, (int) id, false));
                    secondLevelListView.setAdapter(sideMenuListAdapter);

                    //new Component(getApplicationContext()).setClickListener(view,list.get(position).getFunctionality().toString(),list.get(position).getUrl().toString(),0);
                } else {

                    for (sidemenu sidemenu : list) {
                        if (sidemenu.getID() == (int) id) {
                            Log.d("Func", sidemenu.getFunctionality() + "");
                            Log.d("Url", sidemenu.getUrl() + "");
                            if (sidemenu.getFunctionality() != null) {
                                if (drawer.isDrawerOpen(GravityCompat.END)) {
                                    drawer.closeDrawer(GravityCompat.END);
                                }
                                setClickListener(sidemenu.getFunctionality().toString(), sidemenu.getUrl().toString(), 0, position - 1);
                            }
                        }
                    }
                }
            }
        });

        secondLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!getChildListMenuName(list, (int) id, false).isEmpty()) {
                    //pos is for storing last item ID clicked by user
                    ++pos;
                    global.add((int) id);
                    HomeSideMenuListAdapter menuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(list, (int) id, false));
                    secondLevelListView.setAdapter(menuListAdapter);
                } else {
                    for (sidemenu sidemenu : list) {
                        if (sidemenu.getID() == (int) id) {
                            Log.d("Func", sidemenu.getFunctionality() + "");
                            Log.d("Url", sidemenu.getUrl() + "");
                            if (sidemenu.getFunctionality() != null) {
                                if (drawer.isDrawerOpen(GravityCompat.END)) {
                                    drawer.closeDrawer(GravityCompat.END);
                                }
                                setClickListener(sidemenu.getFunctionality().toString(), sidemenu.getUrl().toString(), 0, position - 1);
                            }
                        }
                    }
                }

            }
        });

    }

    public void setClickListener(final String functionality, final String URL, final int ID, int currentItemPos) {


        Intent intent;
        switch (functionality) {
            case "Gallery":
                intent = new Intent(this, GalleryFolderActivity.class);
                intent.putExtra("URL", URL);
                startActivity(intent);
                overridePendingTransition(0, 0);
                //((Activity)context).finish();
                break;
            case "Fanbazar":
                intent = new Intent(this, FanBazar.class);
                intent.putExtra("URL", URL);
                startActivity(intent);
                overridePendingTransition(0, 0);
                //finish();
                break;
            case "CompanyList":
                intent = new Intent(this, Companies.class);
                intent.putExtra("URL", URL);
                startActivity(intent);
                overridePendingTransition(0, 0);
                //finish();
                break;
            case "NewsList":
                intent = new Intent(this, NewsCategory.class);
                intent.putExtra("URL", URL);
                intent.putExtra("ItemPos", currentItemPos);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                //finish();
                break;
            case "NewsDetails":
                intent = new Intent(this, NewsDetailActivity.class);
                intent.putExtra("URL", URL);
                intent.putExtra("NewsID", ID);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //((Activity) context).overridePendingTransition(0, 0);
                //finish();
                break;
            case "WebView":
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("URL", URL);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                //finish();
                break;

            default:
                break;
        }
    }


    public ArrayList<sidemenu> getChildListMenuName(ArrayList<sidemenu> list, int ID, boolean isRoot) {
        ArrayList<sidemenu> result = new ArrayList<>();

        for (sidemenu _menu : list) {

            if (_menu.getFMenuID() != null && !isRoot) {
                Double FmenuID = (Double) _menu.getFMenuID();
                if (FmenuID.intValue() == ID) {

                    //Log.d("FMenuID", _menu.getFMenuID() + "");
                    //Log.d("Name", _menu.getName());
                    //Log.d("ID", _menu.getID() + "");

                    result.add(_menu);
                }
            } else if (_menu.getFMenuID() == null && isRoot) {
                result.add(_menu);
            }
        }
        return result;
    }


}
