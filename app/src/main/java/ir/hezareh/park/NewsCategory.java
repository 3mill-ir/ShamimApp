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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_category);



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
        public RecyclerView NewsRecycler;
        public SwipeRefreshLayout swipeRefreshLayout;
        public MyRecyclerAdapter myRecyclerAdapter;
        List<Root> newslist;

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

        public void makeJsonObjectRequest() {

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
                        newslist = new ArrayList<>(news);
                        Log.i("Post", news.size() + " posts loaded.");
                        for (Root news1 : newslist) {
                            Log.i("Post", news1.toString());
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
                    myRecyclerAdapter = new MyRecyclerAdapter(getActivity(), null, newslist);

                    myRecyclerAdapter.notifyDataSetChanged();

                    NewsRecycler.setAdapter(myRecyclerAdapter);

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
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_news_category, container, false);
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

                NewsRecycler = rootView.findViewById(R.id.news_recycler);
                swipeRefreshLayout = rootView.findViewById(R.id.refresh_layout);

                NewsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                NewsRecycler.addItemDecoration(new EqualSpacingItemDecoration(5, EqualSpacingItemDecoration.VERTICAL));
                NewsRecycler.setItemAnimator(new DefaultItemAnimator());


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
