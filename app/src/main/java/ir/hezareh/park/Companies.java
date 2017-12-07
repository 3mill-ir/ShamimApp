package ir.hezareh.park;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class Companies extends AppCompatActivity {
    ArrayList<HashMap<String, String>> Shoura_List;
    ProgressDialog progressDialog;
    CompaniesRecycler myRecyclerAdapter;
    CompaniesRecycler myRecyclerAdapter1;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);

        recyclerView = (RecyclerView) findViewById(R.id.daneshbonyan_recycler);
        recyclerView1 = (RecyclerView) findViewById(R.id.fanavar_recycler);

        myRecyclerAdapter = new CompaniesRecycler(getApplicationContext(), null);
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), calculateNoOfColumns(getApplicationContext()));
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(getApplicationContext(), calculateNoOfColumns(getApplicationContext()));

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(calculateNoOfColumns(getApplicationContext()), dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myRecyclerAdapter);


        recyclerView1.addItemDecoration(new GridSpacingItemDecoration(calculateNoOfColumns(getApplicationContext()), dpToPx(5), true));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        recyclerView1.setLayoutManager(mLayoutManager1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(myRecyclerAdapter);
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
