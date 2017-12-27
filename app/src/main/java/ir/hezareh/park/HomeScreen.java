package ir.hezareh.park;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
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

    ArrayList<String> my;
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


        my = new ArrayList<>();


        showComponents();


        my.add("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg");
        my.add("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg");
        my.add("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg");
        my.add("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg");


        width = new Utils(getApplicationContext()).getDisplayMetrics().widthPixels;


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

                if (pos == 0) {
                    firstLevelListView.setVisibility(View.VISIBLE);
                    secondLevelListView.setVisibility(View.GONE);
                    HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), new HomeScreen().getChildListmenuName(list, 0, true));
                    global.remove(pos);
                    firstLevelListView.setAdapter(sideMenuListAdapter);

                } else {
                    --pos;

                    HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListmenuName(list, global.get(pos), false));
                    secondLevelListView.setAdapter(sideMenuListAdapter);
                    global.remove(pos + 1);
                }
                //Log.d("position", pos + "");

                /*for (int i = 0; i < global.size(); i++) {
                    Log.i("after remove globals" + i, global.get(i).toString());
                }*/

            }
        });

        this.makeJsonArrayRequest();
        //this.makeJsonObjectRequest();


        firstLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                firstLevelListView.setVisibility(View.GONE);
                secondLevelListView.setVisibility(View.VISIBLE);
                //Log.e("ID", "" + (int) id);
                global.add((int) id);
                HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), new HomeScreen().getChildListmenuName(list, (int) id, false));
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
                HomeSideMenuListAdapter menuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListmenuName(list, (int) id, false));
                secondLevelListView.setAdapter(menuListAdapter);
            }
        });


    }

    private void makeJsonArrayRequest() {


        JsonArrayRequest req = new JsonArrayRequest("http://parkapi.3mill.ir/api/menues/GetMenu",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            Gson gson = new Gson();

                            Type collectionType = new TypeToken<Collection<sidemenu>>() {
                            }.getType();
                            ArrayList<sidemenu> sidemenuList = gson.fromJson(response.toString(), collectionType);

                            for (sidemenu _menu : sidemenuList) {
                                list.add(_menu);
                                //Log.d("sidemenu1", _menu.getName());
                            }

                            HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListmenuName(sidemenuList, 0, true));

                            firstLevelListView.setAdapter(sideMenuListAdapter);


                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        //hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                //hidepDialog();
            }
        });

        // Adding request to request queue
        req.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(req);
    }

    public ArrayList<sidemenu> getChildListmenuName(ArrayList<sidemenu> list, int ID, boolean isRoot) {
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


    public void showComponents() {

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://arefnaghshin.ir/components", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String jsonResponse = new String(response.toString().getBytes("ISO-8859-1"));
                    Log.d(TAG, jsonResponse);
                    JSONArray jsonArray = response.getJSONArray("Root");
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Collection<ModelComponent>>() {
                    }.getType();
                    List<ModelComponent> components = gson.fromJson(new String(jsonArray.toString().getBytes("ISO-8859-1")), collectionType);


                    LinearLayout Root_Layout = (LinearLayout) findViewById(R.id.main_layout);

                    ArrayList<String> my = new ArrayList<>();
                    my.add(Utils.URL_encode("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg"));
                    my.add(Utils.URL_encode("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg"));
                    my.add(Utils.URL_encode("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg"));


                    ArrayList<String> text = new ArrayList<>();
                    text.add("about");
                    text.add("contact");
                    text.add("اسلایدر");


                    // Sets the Toolbar to act as the ActionBar for this Activity window.
                    // Make sure the toolbar exists in the activity and is not null
                    //setSupportActionBar(toolbar);

                    //Root_Layout.addView(mTopToolbar);

                    //for (ModelComponent component : components) {
                    //for (ModelComponent.Item item : component.getItems()) {

                    Log.d(TAG, components.get(5).getQuestion() + "");
                    //}
                    //}


                    for (ModelComponent component : components) {
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
                                Root_Layout.addView(new Component(HomeScreen.this).ButtonsRow(width, component, my));
                                break;
                            case "GalleryButtonRow":
                                Root_Layout.addView(new Component(HomeScreen.this).GalleryButton(width, component, "ButtonsGallery"));
                                break;
                            case "Diagram":
                                Root_Layout.addView(new MyPieChart(HomeScreen.this, width, width / 2).getItem());
                                break;
                            case "PollQuestion":
                                Root_Layout.addView(new Component(HomeScreen.this).pollQuestion(width, 0, component));
                                break;
                        }
                    }


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

}
