package ir.hezareh.park;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.util.List;

import ir.hezareh.park.Adapters.GalleryFolderAdapter;
import ir.hezareh.park.DataLoading.OfflineDataLoader;
import ir.hezareh.park.DataLoading.networking;
import ir.hezareh.park.Util.GridSpacingItemDecoration;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.GalleryModel;

import static ir.hezareh.park.Util.Utils.MessageType.server_error;


public class GalleryFolderActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    public static final String TAG = GalleryFolderActivity.class.getSimpleName();
    public RecyclerView GalleryRecycler;
    public SwipeRefreshLayout swipeRefreshLayout;
    GalleryFolderAdapter galleryFolderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_folders);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        GalleryRecycler = findViewById(R.id.gallery_recycler);
        swipeRefreshLayout = findViewById(R.id.refresh_layout);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), GridSpacingItemDecoration.calculateNoOfColumns(getApplicationContext()));
        GalleryRecycler.addItemDecoration(new GridSpacingItemDecoration(GridSpacingItemDecoration.calculateNoOfColumns(getApplicationContext()), new Utils(getApplicationContext()).dpToPx(5), true));
        GalleryRecycler.setLayoutManager(mLayoutManager);
        GalleryRecycler.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout.setOnRefreshListener(this);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (new Utils(getApplicationContext()).isConnectedToInternet()) {

                    new networking(getApplicationContext()).getFolderGallery(new networking.FolderGalleryResponseListener() {
                        @Override
                        public void requestStarted() {

                        }

                        @Override
                        public void requestCompleted(List<GalleryModel> Gallery) {
                            galleryFolderAdapter = new GalleryFolderAdapter(getApplicationContext(), Gallery);
                            GalleryRecycler.setAdapter(galleryFolderAdapter);
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void requestEndedWithError(VolleyError error) {
                            new Utils(getApplicationContext()).showToast(server_error, GalleryFolderActivity.this);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                } else {
                    OfflineDataLoader offlineDataLoader = new OfflineDataLoader(getApplicationContext());
                    if (offlineDataLoader.ReadOfflineFolderGallery() != null) {
                        galleryFolderAdapter = new GalleryFolderAdapter(getApplicationContext(), new OfflineDataLoader(getApplicationContext()).ReadOfflineFolderGallery());
                        GalleryRecycler.setAdapter(galleryFolderAdapter);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }

            }
        });

        ((TextView) findViewById(R.id.header_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));
        ((TextView) findViewById(R.id.header_text)).setText("گالری تصاویر");
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
    public void onRefresh() {
        new networking(getApplicationContext()).getFolderGallery(new networking.FolderGalleryResponseListener() {
            @Override
            public void requestStarted() {

            }

            @Override
            public void requestCompleted(List<GalleryModel> Gallery) {
                galleryFolderAdapter = new GalleryFolderAdapter(getApplicationContext(), Gallery);
                GalleryRecycler.setAdapter(galleryFolderAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                new Utils(getApplicationContext()).showToast(server_error, GalleryFolderActivity.this);

            }
        });
    }
}
