package ir.hezareh.park;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;

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


        new networking().getMainJson(new networking.MainJsonResponseListener() {
            @Override
            public void requestStarted() {

            }

            @Override
            public void requestCompleted(List<ModelComponent> modelComponents) {
                LinearLayout Root_Layout = (LinearLayout) findViewById(R.id.main_layout);

                for (ModelComponent component : modelComponents) {
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
                            Root_Layout.addView(new Component(FanBazar.this).ButtonsRow(width, component));
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
                new Utils(getApplicationContext()).overrideFonts(Root_Layout, "BYekan");
            }

            @Override
            public void requestEndedWithError(VolleyError error) {
                //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //hideDialog();
                //swipeRefreshLayout.setRefreshing(false);
            }
        }, getApplicationContext());

    }

}
