package ir.hezareh.park;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

import ir.hezareh.park.Adapters.CompaniesRecycler;
import ir.hezareh.park.Adapters.CompanyCategoryMenuAdapter;
import ir.hezareh.park.DataLoading.networking;
import ir.hezareh.park.Util.GridSpacingItemDecoration;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.CompanyList;


public class Companies extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = Companies.class
            .getSimpleName();
    ProgressDialog progressDialog;
    CompaniesRecycler companiesRecycler;
    CompanyCategoryMenuAdapter categoryMenuAdapter;
    SlidingMenu menu;
    ListView CompanyListView;
    CardView companyHeaderCardView;
    TextView companyCategory;
    int Clicked = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView companyRecyclerView;




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
        companyHeaderCardView = (CardView) findViewById(R.id.card_view);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), GridSpacingItemDecoration.calculateNoOfColumns(getApplicationContext()));
        companyRecyclerView.addItemDecoration(new GridSpacingItemDecoration(GridSpacingItemDecoration.calculateNoOfColumns(getApplicationContext()), new Utils(getApplicationContext()).dpToPx(5), true));
        companyRecyclerView.setLayoutManager(mLayoutManager);
        companyRecyclerView.setItemAnimator(new DefaultItemAnimator());


        companyCategory.setTypeface(new Utils(getApplicationContext()).font_set("BHoma"));


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
                Clicked = position;
                new networking(getApplicationContext()).getCompanyList(new networking.CompanyListResponseListener() {
                    @Override
                    public void requestStarted() {

                    }

                    @Override
                    public void requestCompleted(List<CompanyList> companyLists) {
                        companiesRecycler = new CompaniesRecycler(getApplicationContext(), companyLists.get(Clicked).getCompanyList());
                        companyRecyclerView.setAdapter(companiesRecycler);

                        companyHeaderCardView.setVisibility(View.VISIBLE);
                        companyCategory.setText(companyLists.get(Clicked).getType());

                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void requestEndedWithError(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                        // hide the progress dialog
                        //hidepDialog();
                        //swipeRefreshLayout.setRefreshing(false);
                    }
                });
                menu.toggle();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        new networking(getApplicationContext()).getCompanyList(new networking.CompanyListResponseListener() {
                                            @Override
                                            public void requestStarted() {

                                            }

                                            @Override
                                            public void requestCompleted(List<CompanyList> companyLists) {
                                                categoryMenuAdapter = new CompanyCategoryMenuAdapter(getApplicationContext(), companyLists);
                                                CompanyListView.setAdapter(categoryMenuAdapter);

                                                companiesRecycler = new CompaniesRecycler(getApplicationContext(), companyLists.get(Clicked).getCompanyList());
                                                companyRecyclerView.setAdapter(companiesRecycler);

                                                companyHeaderCardView.setVisibility(View.VISIBLE);
                                                companyCategory.setText(companyLists.get(Clicked).getType());

                                                swipeRefreshLayout.setRefreshing(false);
                                            }

                                            @Override
                                            public void requestEndedWithError(VolleyError error) {
                                                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                                swipeRefreshLayout.setRefreshing(false);
                                                // hide the progress dialog
                                                //hidepDialog();
                                                //swipeRefreshLayout.setRefreshing(false);
                                            }
                                        });
                                    }
        });
    }

    @Override
    public void onRefresh() {
        new networking(getApplicationContext()).getCompanyList(new networking.CompanyListResponseListener() {
            @Override
            public void requestStarted() {

            }

            @Override
            public void requestCompleted(List<CompanyList> companyLists) {

                categoryMenuAdapter.notifyDataSetChanged();

                companiesRecycler.notifyDataSetChanged();

                companyHeaderCardView.setVisibility(View.VISIBLE);
                companyCategory.setText(companyLists.get(Clicked).getType());

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                // hide the progress dialog
                //hideDialog();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


}
