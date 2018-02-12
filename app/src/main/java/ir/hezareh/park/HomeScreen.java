package ir.hezareh.park;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
import ir.hezareh.park.models.ModelComponent;
import ir.hezareh.park.models.sidemenu;

public class HomeScreen extends AppCompatActivity {

    public static final String TAG = HomeScreen.class
            .getSimpleName();
    int width;
    ListView firstLevelListView;
    ListView secondLevelListView;

    ArrayList<sidemenu> list = new ArrayList<>();
    ArrayList<Integer> global = new ArrayList<>();

    int pos = 0;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_list, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        //setSupportActionBar(toolbar);


        width = new Utils(getApplicationContext()).getDisplayMetrics().widthPixels;

        new networking().getMainJson(new networking.MainJsonResponseListener() {
            @Override
            public void requestStarted() {

            }

            @Override
            public void requestCompleted(List<ModelComponent> modelComponents) {
                LinearLayout Root_Layout = (LinearLayout) findViewById(R.id.main_layout);

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
                new Utils(getApplicationContext()).overrideFonts(Root_Layout, "BYekan");
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //hidepDialog();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

        try {
            new AppUpdate(getApplicationContext()).check_Version();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



        ((TextView) findViewById(R.id.header_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));

        findViewById(R.id.drawer_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawerLayout) findViewById(R.id.drawer_layout)).openDrawer(GravityCompat.END);
            }
        });


        firstLevelListView = (ListView) findViewById(R.id.first_level_menu);
        secondLevelListView = (ListView) findViewById(R.id.second_level_menu);

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

        new networking().getMainSideMenu(new networking.SideMenuResponseListener() {
            @Override
            public void requestStarted() {

            }

            @Override
            public void requestCompleted(ArrayList<sidemenu> sidemenus) {

                for (sidemenu _menu : sidemenus) {
                    list.add(_menu);
                    //Log.d("sidemenu1", _menu.getName());
                }

                HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(sidemenus, 0, true));
                firstLevelListView.setAdapter(sideMenuListAdapter);
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                //hidepDialog();
            }
        });



        firstLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                firstLevelListView.setVisibility(View.GONE);
                secondLevelListView.setVisibility(View.VISIBLE);
                //Log.e("ID", "" + (int) id);
                global.add((int) id);
                HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), new HomeScreen().getChildListMenuName(list, (int) id, false));
                secondLevelListView.setAdapter(sideMenuListAdapter);
                //Log.d("position", pos + "");
            }
        });

        secondLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //pos is for storing last item ID clicked by user
                ++pos;
                global.add((int) id);
                HomeSideMenuListAdapter menuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(list, (int) id, false));
                secondLevelListView.setAdapter(menuListAdapter);
            }
        });


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
