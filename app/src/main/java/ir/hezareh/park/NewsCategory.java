package ir.hezareh.park;

import android.os.Bundle;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import java.util.ArrayList;
import java.util.List;

import ir.hezareh.park.Adapters.EqualSpacingItemDecoration;
import ir.hezareh.park.Adapters.NewsCategoryAdapter;
import ir.hezareh.park.DataLoading.OfflineDataLoader;
import ir.hezareh.park.DataLoading.networking;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.ModelComponent;


public class NewsCategory extends AppCompatActivity {

    public static final String TAG = NewsCategory.class.getSimpleName();
    TabLayout tabLayout;
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

        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        if (new Utils(getApplicationContext()).isConnectedToInternet()) {
            new networking(getApplicationContext()).getNewsCategory(new networking.NewsCategoryResponseListener() {
                @Override
                public void requestStarted() {

                }

                @Override
                public void requestCompleted(ArrayList<ModelComponent> response) {
                    //hideDialog();

                    //checkPermissions();
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), response);
                    // Set up the ViewPager with the sections adapter.
                    mViewPager.setAdapter(mSectionsPagerAdapter);
                    tabLayout.setupWithViewPager(mViewPager);
                    new Utils(getApplicationContext()).overrideFonts(tabLayout, "BHoma");

                }

                @Override
                public void requestEndedWithError(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    new Utils(getApplicationContext()).showToast("server_error", NewsCategory.this);
                }
            });
        } else {
            //DbHandler db = new DbHandler(getContext());

            //List<ModelComponent> allNews = db.getAllNews(getArguments().getInt(ARG_SECTION_NUMBER) - 1);
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), new OfflineDataLoader(getApplicationContext()).ReadOfflineNewsCategory());
            // Set up the ViewPager with the sections adapter.
            mViewPager.setAdapter(mSectionsPagerAdapter);
            tabLayout.setupWithViewPager(mViewPager);
            new Utils(getApplicationContext()).overrideFonts(tabLayout, "BHoma");
        }


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        new Utils(getApplicationContext()).overrideFonts(findViewById(R.id.header_text), "BYekan");
        ((TextView) findViewById(R.id.header_text)).setText("لیست اخبار");

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
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */


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
        public NewsCategoryAdapter newsCategoryAdapter;

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


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_news_category, container, false);
            //if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

            NewsRecycler = rootView.findViewById(R.id.news_recycler);
            swipeRefreshLayout = rootView.findViewById(R.id.refresh_layout);
            NewsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            NewsRecycler.addItemDecoration(new EqualSpacingItemDecoration(5, EqualSpacingItemDecoration.VERTICAL));
            NewsRecycler.setItemAnimator(new DefaultItemAnimator());

            swipeRefreshLayout.setOnRefreshListener(this);

            if (new Utils(getActivity()).isConnectedToInternet()) {

                //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        new networking(getContext()).getNewsCategory(new networking.NewsCategoryResponseListener() {
                            @Override
                            public void requestStarted() {

                            }

                            @Override
                            public void requestCompleted(ArrayList<ModelComponent> response) {
                                newsCategoryAdapter = new NewsCategoryAdapter(getActivity(), response, getArguments().getInt(ARG_SECTION_NUMBER) - 1);
                                NewsRecycler.setAdapter(newsCategoryAdapter);
                                swipeRefreshLayout.setRefreshing(false);

                            }
                            @Override
                            public void requestEndedWithError(VolleyError error) {
                                swipeRefreshLayout.setRefreshing(false);
                                new Utils(getActivity()).showToast("server_error", getActivity());
                            }
                        });
                    }
                });
            } else {

                swipeRefreshLayout.setRefreshing(true);
                newsCategoryAdapter = new NewsCategoryAdapter(getActivity(), new OfflineDataLoader(getContext()).ReadOfflineNewsCategory(), getArguments().getInt(ARG_SECTION_NUMBER) - 1);
                NewsRecycler.setAdapter(newsCategoryAdapter);
                swipeRefreshLayout.setRefreshing(false);

            }

            return rootView;
        }

        @Override
        public void onRefresh() {

            new networking(getContext()).getNewsCategory(new networking.NewsCategoryResponseListener() {
                @Override
                public void requestStarted() {

                }

                @Override
                public void requestCompleted(ArrayList<ModelComponent> response) {
                    newsCategoryAdapter = new NewsCategoryAdapter(getActivity(), response, getArguments().getInt(ARG_SECTION_NUMBER) - 1);
                    NewsRecycler.setAdapter(newsCategoryAdapter);
                    swipeRefreshLayout.setRefreshing(false);

                }

                @Override
                public void requestEndedWithError(VolleyError error) {
                    swipeRefreshLayout.setRefreshing(false);
                    new Utils(getActivity()).showToast("server_error", getActivity());

                }
            });
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        ArrayList<String> categoryName;

        public SectionsPagerAdapter(FragmentManager fm, final List<ModelComponent> modelComponents) {
            super(fm);
            categoryName = new ArrayList<>();

            if (modelComponents != null) {
                for (ModelComponent item : modelComponents) {
                    categoryName.add(item.getCategory().toString());
                }
            }

            //mSectionsPagerAdapter.notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Log.e("position of", String.valueOf(position + 1));
            return PlaceholderFragment.newInstance(position + 1);

        }

        @Override
        public int getCount() {
            return categoryName.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            /*switch (position) {
                case 0:
                    return "فرهنگی";
                case 1:
                    return "ورزشی";
                case 2:
                    return "خارجی";
            }*/
            return categoryName.get(position);
            //return null;
        }

    }

}
