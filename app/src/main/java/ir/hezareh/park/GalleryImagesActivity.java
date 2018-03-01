package ir.hezareh.park;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import ir.hezareh.park.Adapters.GalleryFolderAdapter;
import ir.hezareh.park.Adapters.GalleryImagesAdapter;
import ir.hezareh.park.DataLoading.OfflineDataLoader;
import ir.hezareh.park.DataLoading.networking;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.GalleryModel;

public class GalleryImagesActivity extends AppCompatActivity {
    public static final String GALLERY_KEY = "Gallery";
    public static final String POSITION_KEY = "Position";
    private String TAG = MainActivity.class.getSimpleName();
    private ArrayList<GalleryModel> galleryModels;
    private ProgressDialog pDialog;
    private GalleryImagesAdapter mAdapter;
    private RecyclerView recyclerView;
    private int _position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_images);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        recyclerView = findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        galleryModels = new ArrayList<>();


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        OfflineDataLoader offlineDataLoader = new OfflineDataLoader(getApplicationContext());


        if (getIntent().getExtras() != null) {
            if (new Utils(getApplicationContext()).isConnectedToInternet()) {

                new networking(getApplicationContext()).getImagesGallery(new networking.ImagesGalleryResponseListener() {
                    @Override
                    public void requestStarted() {

                    }

                    @Override
                    public void requestCompleted(List<GalleryModel> Gallery) {

                        mAdapter = new GalleryImagesAdapter(getApplicationContext(), Gallery);
                        recyclerView.setAdapter(mAdapter);
                        galleryModels = new ArrayList<>(Gallery);
                    }

                    @Override
                    public void requestEndedWithError(VolleyError error) {
                        new Utils(getApplicationContext()).showToast("server_error", GalleryImagesActivity.this);
                    }
                }, getIntent().getExtras().getString(GalleryFolderAdapter.FOLDER_KEY));
            } else {

                if (offlineDataLoader.ReadOfflineImageGalleryToStorage(String.valueOf(getIntent().getExtras().getString(GalleryFolderAdapter.FOLDER_KEY))) != null) {

                    Log.d("Position", String.valueOf(_position));
                    mAdapter = new GalleryImagesAdapter(getApplicationContext(), offlineDataLoader.ReadOfflineImageGalleryToStorage(String.valueOf(getIntent().getExtras().getString(GalleryFolderAdapter.FOLDER_KEY))));
                    recyclerView.setAdapter(mAdapter);
                    galleryModels = new ArrayList<>(offlineDataLoader.ReadOfflineImageGalleryToStorage(String.valueOf(getIntent().getExtras().getString(GalleryFolderAdapter.FOLDER_KEY))));

                }
            }
        }

        recyclerView.addOnItemTouchListener(new GalleryImagesAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryImagesAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                _position = position;
                Bundle bundle = new Bundle();
                bundle.putSerializable(GALLERY_KEY, galleryModels);
                bundle.putInt(POSITION_KEY, position);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

}
