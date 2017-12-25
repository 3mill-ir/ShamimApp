package ir.hezareh.park;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import ir.hezareh.park.models.CompanyList;

public class Companies extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = Companies.class
            .getSimpleName();
    ArrayList<HashMap<String, String>> Shoura_List;
    ProgressDialog progressDialog;
    CompaniesRecycler myRecyclerAdapter;
    CompaniesRecycler myRecyclerAdapter1;
    SlidingMenu menu;
    ListView CompanyListView;
    List<CompanyList> CompanyList;
    TextView companyCategory;
    int Clicked = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView companyRecyclerView;

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);


        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.SLIDING_CONTENT);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadowright);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.company_siding_menu_layout);
        //menu.showMenu();


        CompanyListView = (ListView) findViewById(R.id.side_listview);
        companyCategory = ((TextView) findViewById(R.id.company_category));
        companyRecyclerView = (RecyclerView) findViewById(R.id.fanavar_recycler);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), calculateNoOfColumns(getApplicationContext()));
        companyRecyclerView.addItemDecoration(new GridSpacingItemDecoration(calculateNoOfColumns(getApplicationContext()), dpToPx(0), true));
        companyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        companyRecyclerView.setLayoutManager(mLayoutManager);
        companyRecyclerView.setItemAnimator(new DefaultItemAnimator());


        findViewById(R.id.drawer_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
                //menu.setMenu(R.layout.company_siding_menu_layout);
            }
        });
        ((TextView) findViewById(R.id.header_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));
        findViewById(R.id.header_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);


        CompanyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*myRecyclerAdapter = new CompaniesRecycler(getApplicationContext(), CompanyList.get(position).getCompanyList() );
                companyRecyclerView.setAdapter(myRecyclerAdapter);
                companyCategory.setVisibility(View.VISIBLE);
                companyCategory.setText(CompanyList.get(position).getType());
                companyCategory.setTypeface(new Utils(getApplicationContext()).font_set("BHoma"));*/
                Clicked = position;
                getCompanyList();
                menu.toggle();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        getCompanyList();
                                    }
                                }
        );


    }

    @Override
    public void onRefresh() {
        getCompanyList();
    }

    public void getCompanyList() {

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://arefnaghshin.ir/companylist", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    swipeRefreshLayout.setRefreshing(true);
                    String jsonResponse = new String(response.toString().getBytes("ISO-8859-1"));
                    Log.d(TAG, jsonResponse);
                    JSONArray jsonArray = response.getJSONArray("Root");
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Collection<CompanyList>>() {
                    }.getType();
                    CompanyList = gson.fromJson(new String(jsonArray.toString().getBytes("ISO-8859-1")), collectionType);

                    ArrayList<String> my = new ArrayList<>();
                    my.add(Utils.URL_encode("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg"));
                    my.add(Utils.URL_encode("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg"));
                    my.add(Utils.URL_encode("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg"));


                    ArrayList<String> text = new ArrayList<>();
                    text.add("about");
                    text.add("contact");
                    text.add("اسلایدر");


                    Log.d(TAG, CompanyList.get(0).getCompanyList().get(1).getName() + "");

                    CompanyListAdapter companyListAdapter = new CompanyListAdapter(getApplicationContext(), CompanyList);
                    CompanyListView.setAdapter(companyListAdapter);

                    myRecyclerAdapter = new CompaniesRecycler(getApplicationContext(), CompanyList.get(Clicked).getCompanyList());
                    companyRecyclerView.setAdapter(myRecyclerAdapter);

                    companyCategory.setVisibility(View.VISIBLE);
                    companyCategory.setText(CompanyList.get(Clicked).getType());
                    companyCategory.setTypeface(new Utils(getApplicationContext()).font_set("BHoma"));


                    // Sets the Toolbar to act as the ActionBar for this Activity window.
                    // Make sure the toolbar exists in the activity and is not null
                    //setSupportActionBar(toolbar);
                    swipeRefreshLayout.setRefreshing(false);

                    //Root_Layout.addView(mTopToolbar);

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
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                // hide the progress dialog
                //hidepDialog();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });


        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq);

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }

    }
}
