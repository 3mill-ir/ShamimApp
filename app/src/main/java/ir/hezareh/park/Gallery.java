package ir.hezareh.park;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mzelzoghbi.zgallery.ZGrid;
import com.mzelzoghbi.zgallery.entities.ZColor;

import java.util.ArrayList;

public class Gallery extends AppCompatActivity {
    ArrayList<String> my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        my = new ArrayList<>();
        my.add("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg");
        my.add("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg");
        my.add("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg");
        my.add("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg");
        my.add("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg");
        my.add("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg");
        my.add("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg");
        my.add("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg");
        my.add("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg");
        my.add("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg");
        my.add("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg");
        my.add("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg");
        my.add("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg");
        my.add("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg");
        my.add("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg");


        ZGrid.with(this, my)
                .setToolbarColorResId(R.color.colorPrimary) // toolbar color
                .setTitle("Zak Gallery") // toolbar title
                .setToolbarTitleColor(ZColor.WHITE) // toolbar title color
                .setSpanCount(3) // colums count
                .setGridImgPlaceHolder(R.color.colorPrimary) // color placeholder for the grid image until it loads
                .show();


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
