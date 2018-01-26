package ir.hezareh.park;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mzelzoghbi.zgallery.ZGrid;
import com.mzelzoghbi.zgallery.entities.ZColor;

import java.util.ArrayList;
import java.util.List;

import ir.hezareh.park.Adapters.GalleryFolderAdapter;
import ir.hezareh.park.models.GalleryModel;


public class Gallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);


        ((TextView) findViewById(R.id.header_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));


        new networking().getFolderGallery(new networking.FolderGalleryResponseListener() {
            @Override
            public void requestStarted() {

            }

            @Override
            public void requestCompleted(final List<GalleryModel> folderList) {
                GridView gridView = (GridView) findViewById(R.id.grid_view);
                // Instance of ImageAdapter Class
                gridView.setAdapter(new GalleryFolderAdapter(getApplicationContext(), folderList));

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        new networking().getImagesGallery(folderList.get(position).getFolderName(), new networking.ImagesGalleryResponseListener() {
                            @Override
                            public void requestStarted() {

                            }

                            @Override
                            public void requestCompleted(List<GalleryModel> Gallery) {

                                ArrayList<String> images_list = new ArrayList<>();

                                for (GalleryModel item : Gallery) {
                                    images_list.add(item.getImage());
                                }
                                ZGrid.with(ir.hezareh.park.Gallery.this, images_list)
                                        .setToolbarColorResId(R.color.colorPrimary) // toolbar color
                                        .setTitle(folderList.get(position).getFolderName()) // toolbar title
                                        .setToolbarTitleColor(ZColor.WHITE) // toolbar title color
                                        .setSpanCount(3) // columns count
                                        .setGridImgPlaceHolder(R.color.colorPrimary) // color placeholder for the grid image until it loads
                                        .show();
                            }

                            @Override
                            public void requestEndedWithError(VolleyError error) {

                            }
                        });
                    }
                });
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //hideDialog();
                //swipeRefreshLayout.setRefreshing(false);
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
}
