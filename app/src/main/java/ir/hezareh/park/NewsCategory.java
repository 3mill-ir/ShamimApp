package ir.hezareh.park;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import java.util.ArrayList;
import java.util.List;

import ir.hezareh.park.Adapters.EqualSpacingItemDecoration;
import ir.hezareh.park.Adapters.NewsCategoryAdapter;
import ir.hezareh.park.models.ModelComponent;


public class NewsCategory extends AppCompatActivity {

    public static final String TAG = NewsCategory.class.getSimpleName();
    private static final int REQUEST_PERMISSION_WRITE = 1001;
    TabLayout tabLayout;
    private boolean permissionGranted;
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

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    // Initiate request for permissions.
    private boolean checkPermissions() {

        if (!isExternalStorageReadable() || !isExternalStorageWritable()) {
            Toast.makeText(this, "This app only works on devices with usable external storage", Toast.LENGTH_SHORT).show();
            return false;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
            return false;
        } else {
            return true;
        }
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(this, "External storage permission granted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

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
            new networking().getNewsCategory(new networking.NewsCategoryResponseListener() {
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
                    //hideDialog();
                }
            }, getApplicationContext());
        } else {
            //DbHandler db = new DbHandler(getContext());

            //List<ModelComponent> allNews = db.getAllNews(getArguments().getInt(ARG_SECTION_NUMBER) - 1);
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), new OfflineDataLoader(getApplicationContext()).ReadOfflineNewsCategory());
            // Set up the ViewPager with the sections adapter.
            mViewPager.setAdapter(mSectionsPagerAdapter);
            tabLayout.setupWithViewPager(mViewPager);
            new Utils(getApplicationContext()).overrideFonts(tabLayout, "BHoma");
        }








        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        new Utils(getApplicationContext()).overrideFonts(findViewById(R.id.header_text), "BYekan");

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


        public void saveNewsToDB(ArrayList<ModelComponent> modelComponents1, int index) {

            DbHandler db = new DbHandler(getContext());
            ModelComponent modelComponent = new ModelComponent();
            ModelComponent.Item item = modelComponent.new Item();


            for (ModelComponent modelComponent1 : modelComponents1) {
                for (ModelComponent.Item Item : modelComponent1.getItem()) {
                    item.setID(Item.getID());
                    item.setImage(Item.getImage());
                    item.setText(Item.getText());
                    item.setDate(Item.getDate());
                    item.setContent(Item.getContent());
                    item.setFunctionality(Item.getFunctionality());
                    item.setUrl(Item.getUrl());
                    item.setLikes(Item.getLikes());
                    item.setDislikes(Item.getDislikes());
                    item.setComment(Item.getComment());

                    // Inserting NewsList

                    db.addNews(item, index);
                }
            }


            for (ModelComponent list1 : modelComponents1) {
                for (ModelComponent.Item savedItem : list1.getItem()) {
                    String log = "ID: " + savedItem.getID() + " ,Image: " + savedItem.getImage() + " ,Text: " + savedItem.getText()
                            + ",Date: " + savedItem.getDate() + ", Content:  " + savedItem.getContent() +
                            ", Functionality: " + savedItem.getFunctionality();
                    // Writing Contacts to log
                    Log.d("List of news ", log);
                }
            }
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

            if (new Utils(getActivity()).isConnectedToInternet()) {

                //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

                swipeRefreshLayout.setOnRefreshListener(this);

                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        new networking().getNewsCategory(new networking.NewsCategoryResponseListener() {
                            @Override
                            public void requestStarted() {

                            }

                            @Override
                            public void requestCompleted(ArrayList<ModelComponent> response) {
                                newsCategoryAdapter = new NewsCategoryAdapter(getActivity(), response, getArguments().getInt(ARG_SECTION_NUMBER) - 1);
                                NewsRecycler.setAdapter(newsCategoryAdapter);
                                swipeRefreshLayout.setRefreshing(false);

                                //saveNewsToDB(response, getArguments().getInt(ARG_SECTION_NUMBER) - 1);

                                List<ModelComponent.Item> items = new ArrayList<>();

                                for (ModelComponent component : response) {
                                    for (ModelComponent.Item item : component.getItem()) {

                                        items.add(item);
                                    }

                                }
                            }

                            @Override
                            public void requestEndedWithError(VolleyError error) {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }, getContext());
                    }
                });
            } else {
                newsCategoryAdapter = new NewsCategoryAdapter(getActivity(), new OfflineDataLoader(getContext()).ReadOfflineNewsCategory(), getArguments().getInt(ARG_SECTION_NUMBER) - 1);
                NewsRecycler.setAdapter(newsCategoryAdapter);
                swipeRefreshLayout.setRefreshing(false);

            }


            return rootView;
        }

        @Override
        public void onRefresh() {
            new networking().getNewsCategory(new networking.NewsCategoryResponseListener() {
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
                }
            }, getContext());
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
