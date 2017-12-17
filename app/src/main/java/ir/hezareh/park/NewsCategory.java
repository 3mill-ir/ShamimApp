package ir.hezareh.park;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class NewsCategory extends AppCompatActivity {


    public static final String TAG = NewsCategory.class.getSimpleName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /*public List<News> getNNews() {
        return NNews;
    }

    public void setNNews(List<News> NNews) {
        this.NNews = NNews;
    }*/
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_category);


        Log.i(TAG, "onCreate: ");


// ... do something with the int list


        // Instantiate the RequestQueue.
        /*RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://arefnaghshin.ir/myjson.txt";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Log.e("response",response.toString());
                        *//*News responseModel = gson.fromJson(response,
                                News.class);*//*
                        //List<News> responses = responseModel.getClass();

                        //Log.e("response",responses.toString());

                        //News[] enums = gson.fromJson(response, News[].class);


                        //Type collectionType = new TypeToken<Collection<News>>(){}.getType();
                        //Collection<Root> enums = gson.fromJson(response, collectionType);

                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));

                        //Log.e("enums",enums);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

        stringRequest.setShouldCache(false);*/

        // Adding request to request queue
        //App.getInstance().addToRequestQueue(stringRequest);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://arefnaghshin.ir/mymenu.txt", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("Menu1");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Gson gson = new Gson();

                        Type collectionType = new TypeToken<Collection<ir.hezareh.park.Menu>>() {
                        }.getType();
                        List<ir.hezareh.park.Menu> enums = gson.fromJson(jsonArray.toString(), collectionType);

                        //JSONObject questionMark = searchResult.getJSONObject("question_mark");
                        Iterator keys = response.keys();

                        while (keys.hasNext()) {
                            // loop to get the dynamic key
                            String currentDynamicKey = (String) keys.next();

                            Log.i("PostActivity", currentDynamicKey);

                            // get the value of the dynamic key
                            JSONArray currentDynamicValue = response.getJSONArray(currentDynamicKey);

                            // do something here with the value...
                            Log.i("PostActivity", enums.get(0).getMenu1().get(0).getText() + " posts loaded.");
                            for (ir.hezareh.park.Menu post : enums) {

                                Log.i("PostActivity", post.toString());
                            }
                        }

                        //List<Root> posts = Arrays.asList(gson.fromJson(jsonArray.toString(), Root[].class));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),
                //       error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //hidepDialog();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq);
        RequestQueue queue1 = Volley.newRequestQueue(this);

        JsonArrayRequest req = new JsonArrayRequest("http://arefnaghshin.ir/mymenu.txt",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("dzz", "dzz1");
                       /* Log.d(TAG, response.toString());
                        Gson gson = new Gson();

                        Type collectionType = new TypeToken<Collection<Menu>>(){}.getType();
                        List<Menu> enums = gson.fromJson(response.toString(), collectionType);


                        List<Menu> posts = Arrays.asList(gson.fromJson(response.toString(), Menu[].class));

                        Log.i("PostActivity", enums.size() + " posts loaded.");
                        for (Menu post : posts) {
                            Log.i("PostActivity",  post.toString());
                        }*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),
                //        error.getMessage(), Toast.LENGTH_SHORT).show();
                //hidepDialog();
            }
        });
        req.setShouldCache(false);

        queue1.add(req);


        // Adding request to request queue
        App.getInstance().addToRequestQueue(req);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
        private static final String ARG_SECTION_NUMBER = "section_number";
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        RecyclerView NewsRecycler;
        SwipeRefreshLayout swipeRefreshLayout;
        MyRecyclerAdapter myRecyclerAdapter;

        public PlaceholderFragment() {

        }


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public List<Root> makeJsonObjectRequest() {

            //showpDialog();
            //final News news=new News();

            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    "http://arefnaghshin.ir/myjson.txt", null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    String utf8string;
                    try {
                        utf8string = new String(response.toString().getBytes("ISO-8859-1"));

                        Log.d(TAG, utf8string);

                        JSONArray jsonArray = response.getJSONArray("Root");

                        Gson gson = new Gson();

                        Type collectionType = new TypeToken<Collection<Root>>() {
                        }.getType();
                        List<Root> news = gson.fromJson(String.valueOf(jsonArray), collectionType);

                        //List<News> news = Arrays.asList(gson.fromJson(response.toString(), News[].class));

                        Log.i("PostActivity", news.size() + " posts loaded.");
                        for (Root news1 : news) {
                            Log.i("PostActivity", news1.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //hidepDialog();
                    myRecyclerAdapter.notifyDataSetChanged();
                    myRecyclerAdapter.notifyItemInserted(0);

                    swipeRefreshLayout.setRefreshing(false);

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    //Toast.makeText(getApplicationContext(),
                    //       error.getMessage(), Toast.LENGTH_SHORT).show();
                    // hide the progress dialog
                    //hidepDialog();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
            jsonObjReq.setShouldCache(false);

            // Adding request to request queue
            App.getInstance().addToRequestQueue(jsonObjReq);

            return null;

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



            View rootView = inflater.inflate(R.layout.fragment_news_category, container, false);
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                //makeJsonObjectRequest();

                NewsRecycler = (RecyclerView) rootView.findViewById(R.id.news_recycler);


                NewsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                NewsRecycler.addItemDecoration(new EqualSpacingItemDecoration(5, EqualSpacingItemDecoration.VERTICAL));
                NewsRecycler.setItemAnimator(new DefaultItemAnimator());

                //Log.i(TAG, "onCreateView: "+ new NewsCategory().getNNews().get(0).getImage());
                //makeJsonObjectRequest();

                myRecyclerAdapter = new MyRecyclerAdapter(getActivity(), null, null);

                swipeRefreshLayout = rootView.findViewById(R.id.refresh_layout);
                NewsRecycler.setAdapter(myRecyclerAdapter);
                //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                swipeRefreshLayout.setOnRefreshListener(this);

                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        makeJsonObjectRequest();
                    }
                });
                //new Utils(getActivity()).showAlertDialog("ارسال","ارسال درخواست؟",true);
            }
            return rootView;
        }


        @Override
        public void onRefresh() {
            makeJsonObjectRequest();
        }
    }

    public class ResponseModel {

        private List<Integer> response = new ArrayList<Integer>();

        public List<Integer> getResponse() {
            return response;
        }

        @Override
        public String toString() {
            return "ResponseModel [response=" + response + "]";
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "فرهنگی";
                case 1:
                    return "ورزشی";
                case 2:
                    return "خارجی";
            }
            return null;
        }

    }

}
