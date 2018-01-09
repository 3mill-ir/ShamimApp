package ir.hezareh.park;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mzelzoghbi.zgallery.ZGrid;
import com.mzelzoghbi.zgallery.entities.ZColor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
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

        getGallery();
    }

    public void getGallery() {

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://arefnaghshin.ir/gallery", null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                try {
                    String jsonResponse = new String(response.toString().getBytes("ISO-8859-1"));
                    Log.d("tag", jsonResponse);
                    JSONArray jsonArray = response.getJSONArray("Root");
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Collection<GalleryModel>>() {
                    }.getType();
                    final List<GalleryModel> Gallery = gson.fromJson(new String(jsonArray.toString().getBytes("ISO-8859-1")), collectionType);

                    GridView gridView = (GridView) findViewById(R.id.grid_view);
                    // Instance of ImageAdapter Class
                    gridView.setAdapter(new GalleryFolderAdapter(getApplicationContext(), Gallery));


                    Log.d("tag", Gallery.get(0).getItem().get(0).getImage() + "");


                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ArrayList<String> images_list = new ArrayList<>();
                            for (GalleryModel.Item item : Gallery.get(position).getItem()) {
                                images_list.add(item.getImage());
                            }
                            ZGrid.with(ir.hezareh.park.Gallery.this, images_list)
                                    .setToolbarColorResId(R.color.colorPrimary) // toolbar color
                                    .setTitle(Gallery.get(position).getFolder()) // toolbar title
                                    .setToolbarTitleColor(ZColor.WHITE) // toolbar title color
                                    .setSpanCount(3) // colums count
                                    .setGridImgPlaceHolder(R.color.colorPrimary) // color placeholder for the grid image until it loads
                                    .show();
                        }
                    });


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
                //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //hidepDialog();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq);

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
