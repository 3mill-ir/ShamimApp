package ir.hezareh.park;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import ir.hezareh.park.Component.Component;
import ir.hezareh.park.Component.MyPieChart;
import ir.hezareh.park.models.ModelComponent;

public class FanBazar extends AppCompatActivity {
    public static final String TAG = HomeScreen.class
            .getSimpleName();
    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_bazar);
        width = new Utils(getApplicationContext()).getDisplayMetrics().widthPixels;
        ((TextView) findViewById(R.id.header_text)).setText("فن بازار");
        ((TextView) findViewById(R.id.header_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));


        showComponents();
    }

    public void showComponents() {

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://arefnaghshin.ir/components", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String jsonResponse = new String(response.toString().getBytes("ISO-8859-1"));
                    Log.d(TAG, jsonResponse);
                    JSONArray jsonArray = response.getJSONArray("Root");
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Collection<ModelComponent>>() {
                    }.getType();
                    List<ModelComponent> components = gson.fromJson(new String(jsonArray.toString().getBytes("ISO-8859-1")), collectionType);


                    LinearLayout Root_Layout = (LinearLayout) findViewById(R.id.main_layout);

                    ArrayList<String> my = new ArrayList<>();
                    my.add(Utils.URL_encode("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg"));
                    my.add(Utils.URL_encode("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg"));
                    my.add(Utils.URL_encode("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg"));


                    ArrayList<String> text = new ArrayList<>();
                    text.add("about");
                    text.add("contact");
                    text.add("اسلایدر");


                    // Sets the Toolbar to act as the ActionBar for this Activity window.
                    // Make sure the toolbar exists in the activity and is not null
                    //setSupportActionBar(toolbar);

                    //Root_Layout.addView(mTopToolbar);


                    for (ModelComponent component : components) {
                        switch (component.getComponent()) {
                            case "slider":
                                Root_Layout.addView(new Component(FanBazar.this).Slider(width, 0, component.getItem()));
                                break;
                            case "ButtonGalleryRow":
                                Root_Layout.addView(new Component(FanBazar.this).GalleryButton(width, component, "GalleryButtons"));
                                break;
                            case "NewsList":
                                Root_Layout.addView(new Component(FanBazar.this).News(width, 0, component));
                                break;
                            case "RowButton":
                                Root_Layout.addView(new Component(FanBazar.this).ButtonsRow(width, component, my));
                                break;
                            case "GalleryButtonRow":
                                Root_Layout.addView(new Component(FanBazar.this).GalleryButton(width, component, "ButtonsGallery"));
                                break;
                            case "Diagram":
                                Root_Layout.addView(new MyPieChart(FanBazar.this, width, width / 2, component).getItem());
                                break;
                            case "Poll":
                                Root_Layout.addView(new Component(FanBazar.this).pollQuestion(width, 0, component));
                                break;
                        }
                    }


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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //hidepDialog();
            }
        });


        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        App.getInstance().addToRequestQueue(jsonObjReq);

    }
}
