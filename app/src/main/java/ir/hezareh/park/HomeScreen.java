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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    public static final String TAG = HomeScreen.class
            .getSimpleName();
    int width;
    ListView firstLevelListView;
    ListView secondLevelListView;
    ListView thirdLevelListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listDataChildChild;
    ArrayList<String> my;
    ArrayList<sidemenu> list = new ArrayList<>();
    ArrayList<Integer> global;
    int pos = 0;

    //ListAdapter listAdapter12;


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
        global = new ArrayList<>();

        my.add("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg");
        my.add("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg");
        my.add("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg");
        my.add("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg");
        my.add("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg");
        my.add("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg");
        my.add("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg");
        my.add("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg");
        my.add("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg");
        my.add("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg");
        my.add("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg");
        my.add("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg");
        my.add("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg");
        my.add("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg");
        my.add("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg");



        //getComponents();

        // Serialization
        BagOfPrimitives obj1 = new BagOfPrimitives();
        final Gson gson = new Gson();
        String json = gson.toJson(obj1);

        Log.e("json model", json);

        String[] anotherStr = gson.fromJson("[\"abc\"]", String[].class);
        Integer one = gson.fromJson("1", Integer.class);
        String[] anotherStr1 = gson.fromJson("[\"abc\",\"aaa\"]", String[].class);

        Log.e("anotherStr1", anotherStr1[1]);
        Log.e("One", one + "");
        //Collection<Integer> ints = .immutableList(1,2,3,4,5);


        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listDataChildChild = new HashMap<String, List<String>>();


        width = new Utils(getApplicationContext()).getDisplayMetrics().widthPixels;

        LinearLayout Root_Layout = (LinearLayout) findViewById(R.id.main_layout);


        ArrayList<String> my = new ArrayList<>();
        my.add(Utils.URL_encode("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg"));
        my.add(Utils.URL_encode("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg"));
        my.add(Utils.URL_encode("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg"));


        ArrayList<String> text = new ArrayList<>();
        text.add("about");
        text.add("contact");
        text.add("اسلایدر");


        ArrayList<String> Orders = new ArrayList<>();
        Orders.add("NewsRecycler");
        Orders.add("Slider");
        Orders.add("Gallery");
        Orders.add("PieChart");
        Orders.add("poll");
        Orders.add("Button_Group");
        Orders.add("ROW");

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        //setSupportActionBar(toolbar);

        //Root_Layout.addView(mTopToolbar);


        for (String item : Orders) {
            switch (item) {
                case "Slider":
                    Root_Layout.addView(new Component(this).Slider(width, 0, my));
                    break;
                case "Gallery":
                    Root_Layout.addView(new Component(this).GalleryButton(width, my, "GalleryButtons", text, my));
                    break;
                case "NewsRecycler":
                    Root_Layout.addView(new Component(this).News(width, 0));
                    break;
                case "Button_Group":
                    Root_Layout.addView(new Component(this).ButtonsRow(width, text, my));
                    break;
                case "ROW":
                    Root_Layout.addView(new Component(this).GalleryButton(width, my, "ButtonsGallery", text, my));
                    break;
                case "PieChart":
                    Root_Layout.addView(new MyPieChart(this, width, width / 2).getItem());
                    break;
                case "poll":
                    Root_Layout.addView(new Component(this).pollQuestion(width, 0, text, "آیا از عملکرد شهردار خود راضی هستید؟آیا از عملکرد شهردار خود راضی هستید"));
                    break;
            }
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
        thirdLevelListView = (ListView) findViewById(R.id.third_level_menu);

        LayoutInflater myinflater = getLayoutInflater();

        ViewGroup secondLevelListViewHeader = (ViewGroup) myinflater.inflate(R.layout.menulistheader, secondLevelListView, false);
        secondLevelListView.addHeaderView(secondLevelListViewHeader, null, false);


        /*ViewGroup thirdLevelListViewHeader = (ViewGroup) myinflater.inflate(R.layout.menulistheader, thirdLevelListView, false);
        thirdLevelListView.addHeaderView(thirdLevelListViewHeader, null, false);
*/

        secondLevelListViewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < global.size(); i++) {
                    Log.i("gloabs" + i, global.get(i).toString());
                }


                //Log.i("global",getChildListmenuName(list,global.get(--pos),false).get(0).getName()+"");
                if (pos == 0) {
                    firstLevelListView.setVisibility(View.VISIBLE);
                    secondLevelListView.setVisibility(View.GONE);
                    ListAdapter listAdapter = new ListAdapter(getApplicationContext(), new HomeScreen().getChildListmenuName(list, 13, true));
                    global.remove(pos);
                    firstLevelListView.setAdapter(listAdapter);
                    //--pos;

                } else {
                    --pos;
                    //firstLevelListView.setVisibility(View.GONE);
                    //secondLevelListView.setVisibility(View.VISIBLE);

                    ListAdapter listAdapter12 = new ListAdapter(getApplicationContext(), getChildListmenuName(list, global.get(pos), false));
                    secondLevelListView.setAdapter(listAdapter12);
                    global.remove(pos + 1);
                }
                Log.d("position", pos + "");
                for (int i = 0; i < global.size(); i++) {
                    Log.i("after gloabs" + i, global.get(i).toString());
                }

            }
        });
        /*thirdLevelListViewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondLevelListView.setVisibility(View.VISIBLE);
                thirdLevelListView.setVisibility(View.GONE);
            }
        });*/


        // Instantiate the RequestQueue.
        /*RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://3mill.ir/css/menu.txt";

         Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            String t=new String(response.getBytes("ISO-8859-1"));
                            Log.d(TAG, t);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Log.e(TAG,"That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);*/
        this.makeJsonArrayRequest();
        //this.makeJsonObjectRequest();


        firstLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position1, long id) {

                firstLevelListView.setVisibility(View.GONE);
                secondLevelListView.setVisibility(View.VISIBLE);
                global.add((int) id);
                //ListAdapter listAdapter1 = new ListAdapter(getApplicationContext(), listDataChild.get(listDataHeader.get(position1)));
                ListAdapter listAdapter1 = new ListAdapter(getApplicationContext(), new HomeScreen().getChildListmenuName(list, new HomeScreen().getChildListmenuName(list, 13, true).get(position1).getID(), false));
                secondLevelListView.setAdapter(listAdapter1);
                Log.e("IDDDDD", "" + new HomeScreen().getChildListmenuName(list, 13, true).get(position1).getID());

                for (sidemenu _menu : new HomeScreen().getChildListmenuName(list, position1, false)) {

                    Log.e("menuu", "" + new HomeScreen().getChildListmenuName(list, position1, false));

                }
                Log.d("position", pos + "");

            }
        });

        secondLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //secondLevelListView.setVisibility(View.GONE);
                //thirdLevelListView.setVisibility(View.VISIBLE);
                ++pos;
                Log.e("IDDDDD", "" + (int) id);
                global.add((int) id);
                ListAdapter listAdapter12 = new ListAdapter(getApplicationContext(), getChildListmenuName(list, (int) id, false));
                secondLevelListView.setAdapter(listAdapter12);

                Log.d("position", pos + "");

                //Log.e("IDDDDD",""+new HomeScreen().getChildListmenuName(list,list.get(position).getID(),false));

                //Log.e("inner position clicked", position - 1 + "");//because of header it should be minus 1
                //Log.e("outer position clicked", position1 + "");
                //Log.e("third level item", listDataChildChild.get(listDataChild.get(listDataHeader.get(position1)).get(position - 1)) + "");

                //ListAdapter listAdapter2 = new ListAdapter(getApplicationContext(), listDataChildChild.get(listDataChild.get(listDataHeader.get(position1)).get(position - 1)));
                //thirdLevelListView.setAdapter(listAdapter2);
            }
        });




    }

    private void makeJsonArrayRequest() {


        JsonArrayRequest req = new JsonArrayRequest("http://arefnaghshin.ir/myyy.txt",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());


                        String jsonArray = "[  \n" +
                                "   {  \n" +
                                "      \"$id\":\"1\",\n" +
                                "      \"ID\":11,\n" +
                                "      \"Name\":\"ss2\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":3.0,\n" +
                                "      \"Status\":true,\n" +
                                "      \"Language\":\"FA\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"None\",\n" +
                                "      \"MetaKeywords\":\"hy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"1\",\n" +
                                "      \"ID\":90,\n" +
                                "      \"Name\":\"navad \",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":3.0,\n" +
                                "      \"Status\":true,\n" +
                                "      \"Language\":\"FA\",\n" +
                                "      \"F_MenuID\":11,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"None\",\n" +
                                "      \"MetaKeywords\":\"hy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },{  \n" +
                                "      \"$id\":\"1\",\n" +
                                "      \"ID\":91,\n" +
                                "      \"Name\":\"navad o yek\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":3.0,\n" +
                                "      \"Status\":true,\n" +
                                "      \"Language\":\"FA\",\n" +
                                "      \"F_MenuID\":90,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"None\",\n" +
                                "      \"MetaKeywords\":\"hy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },{  \n" +
                                "      \"$id\":\"1\",\n" +
                                "      \"ID\":95,\n" +
                                "      \"Name\":\"navad o yek12\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":3.0,\n" +
                                "      \"Status\":true,\n" +
                                "      \"Language\":\"FA\",\n" +
                                "      \"F_MenuID\":90,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"None\",\n" +
                                "      \"MetaKeywords\":\"hy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"2\",\n" +
                                "      \"ID\":13,\n" +
                                "      \"Name\":\"ss12\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":true,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":12,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"yy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"3\",\n" +
                                "      \"ID\":14,\n" +
                                "      \"Name\":\"ss1313\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":1.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":13,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"yy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"4\",\n" +
                                "      \"ID\":15,\n" +
                                "      \"Name\":\"ss\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"yy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"5\",\n" +
                                "      \"ID\":16,\n" +
                                "      \"Name\":\"ss\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"yy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"6\",\n" +
                                "      \"ID\":17,\n" +
                                "      \"Name\":\"ss\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"yy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"7\",\n" +
                                "      \"ID\":18,\n" +
                                "      \"Name\":\"ss\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"hy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"8\",\n" +
                                "      \"ID\":21,\n" +
                                "      \"Name\":\"ss\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"yy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"9\",\n" +
                                "      \"ID\":22,\n" +
                                "      \"Name\":\"ssnemone\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"yy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"10\",\n" +
                                "      \"ID\":23,\n" +
                                "      \"Name\":\"dsdsf\",\n" +
                                "      \"Description\":\"sdfsdf\",\n" +
                                "      \"Weight\":22.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"ee\",\n" +
                                "      \"MetaKeywords\":\"ee\",\n" +
                                "      \"MetaDescription\":\"ss\",\n" +
                                "      \"MetaTittle\":\"sdsd\",\n" +
                                "      \"MetaSeoName\":\"sd\",\n" +
                                "      \"DisplayInFooter\":false,\n" +
                                "      \"DisplayInSidebar\":true\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"11\",\n" +
                                "      \"ID\":24,\n" +
                                "      \"Name\":\"dsdsf\",\n" +
                                "      \"Description\":\"sdfsdf\",\n" +
                                "      \"Weight\":22.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"ee\",\n" +
                                "      \"MetaKeywords\":\"ee\",\n" +
                                "      \"MetaDescription\":\"ss\",\n" +
                                "      \"MetaTittle\":\"sdsd\",\n" +
                                "      \"MetaSeoName\":\"sd\",\n" +
                                "      \"DisplayInFooter\":false,\n" +
                                "      \"DisplayInSidebar\":true\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"12\",\n" +
                                "      \"ID\":25,\n" +
                                "      \"Name\":\"dsdsfsaat1217\",\n" +
                                "      \"Description\":\"sdfsdf\",\n" +
                                "      \"Weight\":22.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"ee\",\n" +
                                "      \"MetaKeywords\":\"ee\",\n" +
                                "      \"MetaDescription\":\"ss\",\n" +
                                "      \"MetaTittle\":\"aa\",\n" +
                                "      \"MetaSeoName\":\"aa\",\n" +
                                "      \"DisplayInFooter\":false,\n" +
                                "      \"DisplayInSidebar\":true\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"13\",\n" +
                                "      \"ID\":26,\n" +
                                "      \"Name\":\"ss2\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"hy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"14\",\n" +
                                "      \"ID\":27,\n" +
                                "      \"Name\":\"ss2addhamin alan az sherkat\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"hy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"15\",\n" +
                                "      \"ID\":28,\n" +
                                "      \"Name\":\"ss2addhamin alan az sherkat33\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"hy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"16\",\n" +
                                "      \"ID\":29,\n" +
                                "      \"Name\":\"s\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"hy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"17\",\n" +
                                "      \"ID\":30,\n" +
                                "      \"Name\":\"s#\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"hy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"18\",\n" +
                                "      \"ID\":31,\n" +
                                "      \"Name\":\"menuupdates\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":null,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"h\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"19\",\n" +
                                "      \"ID\":32,\n" +
                                "      \"Name\":\"ss2\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":15,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"hy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"20\",\n" +
                                "      \"ID\":33,\n" +
                                "      \"Name\":\"in alan up shode az server\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":16,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"hy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"21\",\n" +
                                "      \"ID\":34,\n" +
                                "      \"Name\":\"in alan add shode az server\",\n" +
                                "      \"Description\":\"ss\",\n" +
                                "      \"Weight\":11.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"gg\",\n" +
                                "      \"F_MenuID\":16,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"rrr\",\n" +
                                "      \"Type\":\"tt\",\n" +
                                "      \"MetaKeywords\":\"hy\",\n" +
                                "      \"MetaDescription\":\"yy\",\n" +
                                "      \"MetaTittle\":\"yy\",\n" +
                                "      \"MetaSeoName\":\"uuu\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":false\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"22\",\n" +
                                "      \"ID\":35,\n" +
                                "      \"Name\":\"اخبار1\",\n" +
                                "      \"Description\":\"fsafdfdsfadfsdafsdafsafsadfsdgsg\",\n" +
                                "      \"Weight\":2.0,\n" +
                                "      \"Status\":true,\n" +
                                "      \"Language\":\"EN\",\n" +
                                "      \"F_MenuID\":15,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"RTTH053POQ1J.jpg\",\n" +
                                "      \"Type\":\"Static\",\n" +
                                "      \"MetaKeywords\":\"اخبار\",\n" +
                                "      \"MetaDescription\":\"اخبار\",\n" +
                                "      \"MetaTittle\":\"اخبار\",\n" +
                                "      \"MetaSeoName\":\"اخبار\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":true\n" +
                                "   },\n" +
                                "   {  \n" +
                                "      \"$id\":\"23\",\n" +
                                "      \"ID\":36,\n" +
                                "      \"Name\":\"خبر عمومی\",\n" +
                                "      \"Description\":\"بسشلشبلشبسب بل ثقلثفلقث فثقفثفصث\",\n" +
                                "      \"Weight\":1.0,\n" +
                                "      \"Status\":false,\n" +
                                "      \"Language\":\"FA\",\n" +
                                "      \"F_MenuID\":35,\n" +
                                "      \"F_UserID\":\"89a3b463-ac70-4f14-a9d6-a4c660720ebd\",\n" +
                                "      \"Image\":\"MR4LBRR7HITK.jpg\",\n" +
                                "      \"Type\":\"Dynamic\",\n" +
                                "      \"MetaKeywords\":\"خبر\",\n" +
                                "      \"MetaDescription\":\"خبر\",\n" +
                                "      \"MetaTittle\":\"خبر\",\n" +
                                "      \"MetaSeoName\":\"خبر\",\n" +
                                "      \"DisplayInFooter\":true,\n" +
                                "      \"DisplayInSidebar\":true\n" +
                                "   }\n" +
                                "]";





                        try {
                            JSONArray jsonArray1 = new JSONArray(jsonArray);
                            // Parsing json array response
                            // loop through each json object
                            ArrayList<sidemenu> Root = new ArrayList<>();
                            ArrayList<sidemenu> child = new ArrayList<>();

                            for (int i = 0; i < jsonArray1.length(); i++) {

                                JSONObject sidemenu = (JSONObject) jsonArray1.get(i);

                                sidemenu _menu = new sidemenu();

                                int ID = sidemenu.getInt("ID");
                                String name = sidemenu.getString("Name");
                                String F_MenuID = sidemenu.getString("F_MenuID");

                                if (F_MenuID.equals("null")) {
                                    Log.e("Root:", name);
                                    try {
                                        _menu.setName(new String(name.getBytes("ISO-8859-1")));
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    _menu.setID(ID);
                                    _menu.setFMenuID(F_MenuID);
                                    Root.add(_menu);
                                    list.add(_menu);
                                } else {
                                    try {
                                        _menu.setName(new String(name.getBytes("ISO-8859-1")));
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    _menu.setID(ID);
                                    _menu.setFMenuID(F_MenuID);
                                    child.add(_menu);
                                    list.add(_menu);
                                }

                            }

                            for (int j = 0; j < Root.size(); j++) {
                                Log.d("list 3", list.get(j).getID() + "");
                                Log.e("Root F_menu", Root.get(j).getID() + "");
                                for (int i = 0; i < child.size(); i++) {
                                    if (child.get(i).getFMenuID().equals(String.valueOf(Root.get(j).getID()))) {
                                        Log.e("Child ID", child.get(i).getFMenuID() + "");
                                        Log.e("existed", child.get(i).getName());
                                    }
                                }
                            }
                            for (sidemenu _menu : new HomeScreen().getChildListmenuName(list, 13, false)) {

                                Log.e("menuu", "" + _menu.getName());

                            }
                            ListAdapter listAdapter = new ListAdapter(getApplicationContext(), new HomeScreen().getChildListmenuName(list, 13, true));

                            firstLevelListView.setAdapter(listAdapter);

                            //new HomeScreen().getChildListmenuName(list,16);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        //hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
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
            if (_menu.getFMenuID() != "null" && !isRoot) {
                if (Integer.valueOf((String) _menu.getFMenuID()) == ID) {
                    Log.d("found!FMenuID", _menu.getFMenuID() + "");
                    try {
                        Log.d("found!getName", new String(_menu.getName().getBytes("ISO-8859-1")) + "");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Log.d("found!ID", _menu.getID() + "");

                    result.add(_menu);
                }
            } else if (_menu.getFMenuID() == "null" && isRoot) {
                result.add(_menu);
            }

        }
        return result;
    }



    public void makeJsonObjectRequest() {

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://arefnaghshin.ir/menu.txt", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //String utf8string=new String(response,"UTF-8");
                try {
                    String jsonResponse = new String(response.toString().getBytes("ISO-8859-1"));
                    Log.d(TAG, jsonResponse);

                    //parsing 3 level listview and check for null items
                    JSONArray jsonArray = response.getJSONArray("Root");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        List<String> submenu = new ArrayList<String>();

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Menu1 = jsonObject.getString("Menu1");

                        listDataHeader.add(new String(Menu1.getBytes("ISO-8859-1")));

                        Log.i("FirstLevelMenu", new String(Menu1.getBytes("ISO-8859-1")));

                        if (!jsonObject.isNull("List")) {

                            JSONArray list = jsonObject.getJSONArray("List");

                            for (int j = 0; j < list.length(); j++) {

                                List<String> submenu2 = new ArrayList<String>();

                                JSONObject jsonObject1 = list.getJSONObject(j);

                                String Menu2 = jsonObject1.getString("Menu2");

                                Log.i("SecondMenu", new String(Menu2.getBytes("ISO-8859-1")));

                                if (!jsonObject1.isNull("List")) {

                                    JSONArray list2 = jsonObject1.getJSONArray("List");

                                    for (int k = 0; k < list2.length(); k++) {

                                        JSONObject jsonObject2 = list2.getJSONObject(k);

                                        String Menu3 = jsonObject2.getString("Menu3");

                                        Log.i("ThirdLevelMenu", new String(Menu3.getBytes("ISO-8859-1")));

                                        submenu2.add(new String(Menu3.getBytes("ISO-8859-1")));

                                    }
                                    //listDataChildChild.put(listDataChild.get(listDataHeader.get(i)).get(j),submenu2);
                                }
                                submenu.add(new String(Menu2.getBytes("ISO-8859-1")));

                                listDataChild.put(listDataHeader.get(i), submenu);

                                //Log.e("ThirdLevelMenu", (listDataChild.get(listDataHeader.get(i)).get(j)));

                                listDataChildChild.put(listDataChild.get(listDataHeader.get(i)).get(j), submenu2);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //final ListAdapter listAdapter = new ListAdapter(getApplicationContext(), listDataHeader);
                //firstLevelListView.setAdapter(listAdapter);
                //hidepDialog();
                //NewsCategory.myRecyclerAdapter.notifyDataSetChanged();
                //NewsCategory.myRecyclerAdapter.notifyItemInserted(0);

                //swipeRefreshLayout.setRefreshing(false);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //hidepDialog();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void getComponents() {

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://arefnaghshin.ir/json.txt", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //String utf8string=new String(response,"UTF-8");
                try {
                    String jsonResponse = new String(response.toString().getBytes("ISO-8859-1"));
                    Log.d(TAG, jsonResponse);

                    //parsing 3 level listview and check for null items
                    JSONArray jsonArray = response.getJSONArray("Root");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        List<String> componentNameList = new ArrayList<String>();

                        JSONObject component = jsonArray.getJSONObject(i);

                        String componentName = component.getString("Component");

                        componentNameList.add(new String(componentName.getBytes("ISO-8859-1")));

                        Log.i("Component name", new String(componentName.getBytes("ISO-8859-1")));


                        for (String item : componentNameList) {
                            if (item.equals("Slider")) {
                                JSONArray sliderList = component.getJSONArray("slide");
                                for (int j = 0; j < sliderList.length(); j++) {
                                    Log.i("Slider List", sliderList.get(i).toString());
                                }
                            }

                            if (item.equals("RowButton")) {
                                JSONArray sliderList = component.getJSONArray("Button");
                                for (int j = 0; j < sliderList.length(); j++) {
                                    JSONObject jsonObject = sliderList.getJSONObject(j);
                                    String ButtonText = jsonObject.getString("text");
                                    String ButtonImage = jsonObject.getString("Image");
                                    //Log.i("Button List:",new String(BtoString().getBytes("ISO-8859-1")));
                                }
                            }
                            /*if(item.equals("ButtonGalleryRow"))
                            {
                                JSONArray sliderList=component.getJSONArray("Button");
                                for (int j=0;j<sliderList.length();j++)
                                {
                                    Log.i("Button List:",sliderList.get(i).toString());
                                }
                                JSONArray sliderList=component.getJSONArray("Button");
                                for (int j=0;j<sliderList.length();j++)
                                {
                                    Log.i("Button List:",sliderList.get(i).toString());
                                }
                            }*/
                        }



                        /*if (!component.isNull("slide")) {

                            JSONArray list = component.getJSONArray("slide");

                            for (int j = 0; j < list.length(); j++) {

                                List<String> submenu2 = new ArrayList<String>();

                                JSONObject jsonObject1 = list.getJSONObject(j);

                                String Menu2 = jsonObject1.getString("Menu2");

                                Log.i("SecondMenu", new String(Menu2.getBytes("ISO-8859-1")));

                                if (!jsonObject1.isNull("List")) {

                                    JSONArray list2 = jsonObject1.getJSONArray("List");

                                    for (int k = 0; k < list2.length(); k++) {

                                        JSONObject jsonObject2 = list2.getJSONObject(k);

                                        String Menu3 = jsonObject2.getString("Menu3");

                                        Log.i("ThirdLevelMenu", new String(Menu3.getBytes("ISO-8859-1")));

                                        submenu2.add(new String(Menu3.getBytes("ISO-8859-1")));

                                    }
                                    //listDataChildChild.put(listDataChild.get(listDataHeader.get(i)).get(j),submenu2);
                                }
                                submenu.add(new String(Menu2.getBytes("ISO-8859-1")));
 }
                        }*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //final ListAdapter listAdapter = new ListAdapter(getApplicationContext(), listDataHeader);
                //firstLevelListView.setAdapter(listAdapter);
                //hidepDialog();
                //NewsCategory.myRecyclerAdapter.notifyDataSetChanged();
                //NewsCategory.myRecyclerAdapter.notifyItemInserted(0);

                //swipeRefreshLayout.setRefreshing(false);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //hidepDialog();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq);
    }

    class BagOfPrimitives {
        private int value1 = 1;
        private String value2 = "abc";
        private transient int value3 = 3;
        BagOfPrimitives() {
            // no-args constructor
        }
    }


}
