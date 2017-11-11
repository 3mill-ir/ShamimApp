package ir.hezareh.park;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        int width=Utils.getDisplayMetrics(getApplicationContext()).widthPixels;


        LinearLayout Root_Layout=(LinearLayout) findViewById(R.id.main_layout);



        RelativeLayout Row_Layout=new RelativeLayout(this);
        RelativeLayout.LayoutParams row_params = new RelativeLayout.LayoutParams(width, 2*width/3);
        Row_Layout.setLayoutParams(row_params);
        Row_Layout.setId(View.generateViewId());


        LinearLayout Gallery=new LinearLayout(this);
        LinearLayout.LayoutParams gallery_params = new LinearLayout.LayoutParams(2*width/3, 2*width/3);
        Gallery.setLayoutParams(gallery_params);
        Gallery.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        Gallery.setId(View.generateViewId());



        LinearLayout btn_gallery1=new LinearLayout(this);
        RelativeLayout.LayoutParams btn1_row_layout = new RelativeLayout.LayoutParams(width/3, width/3);
        btn1_row_layout.addRule(RelativeLayout.RIGHT_OF,Gallery.getId());
        btn_gallery1.setLayoutParams(btn1_row_layout);
        btn_gallery1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn_gallery1.setId(View.generateViewId());


        LinearLayout btn_gallery2=new LinearLayout(this);
        RelativeLayout.LayoutParams btn2_row_layout = new RelativeLayout.LayoutParams(width/3, width/3);
        btn2_row_layout.addRule(RelativeLayout.BELOW,btn_gallery1.getId());
        btn2_row_layout.addRule(RelativeLayout.ALIGN_PARENT_END);
        btn_gallery2.setLayoutParams(btn2_row_layout);
        btn_gallery2.setBackgroundColor(Color.CYAN);
        btn_gallery2.setId(View.generateViewId());


        Row_Layout.addView(Gallery);
        Row_Layout.addView(btn_gallery1);
        Row_Layout.addView(btn_gallery2);


        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ImageView imageView=new ImageView(this);
        imageView.setLayoutParams(params2);
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

        ArrayList<String> my=new ArrayList<>();
        my.add("https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg?w=940&h=650&auto=compress&cs=tinysrgb");
        my.add("https://images.pexels.com/photos/68510/blue-red-painted-brick-68510.jpeg?w=940&h=650&auto=compress&cs=tinysrgb");
        my.add("https://images.pexels.com/photos/425202/pexels-photo-425202.jpeg?w=940&h=650&auto=compress&cs=tinysrgb");
        my.add("https://images.pexels.com/photos/240572/pexels-photo-240572.jpeg?w=940&h=650&auto=compress&cs=tinysrgb");



        com.daimajia.slider.library.SliderLayout ImageSlider = new SliderLayout(this);
        RelativeLayout.LayoutParams Slider_Layout = new RelativeLayout.LayoutParams(width, width/2);
        ImageSlider.setLayoutParams(Slider_Layout);

        for (int i = 0; i < my.size(); i++) {
            //Log.e(TAG, "init: " + slider_list.get(i).get("slider_title"));
            DefaultSliderView demoSlider = new DefaultSliderView(this);
            demoSlider//.description(slider_list.get(i).get("slider_title"))
                    .image(my.get(i))
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            //Intent k = new Intent(HomeScreen.this, Kanded_list_News.class);
                            //k.putExtra("website", website);
                            //k.putExtra("value_theme", value_theme);
                            //startActivity(k);
                            //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        }
                    });
            ImageSlider.addSlider(demoSlider);
            ImageSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
            ImageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            ImageSlider.setCustomAnimation(new DescriptionAnimation());
        }


        //SliderLayout Slider=new SliderLayout(this);

        LinearLayout News=new LinearLayout(this);
        RecyclerView NewsRecycler=new RecyclerView(this);
        LinearLayout.LayoutParams News_Layout = new LinearLayout.LayoutParams(width, width);
        News.setGravity(Gravity.CENTER_VERTICAL);

        //News_Layout.gravity= Gravity.CENTER_VERTICAL;
        News.setLayoutParams(News_Layout);
        News.setBackgroundColor(Color.GREEN);


        //LinearLayout my1=new LinearLayout(this);
        /*RelativeLayout.LayoutParams params90 = new RelativeLayout.LayoutParams(100, 100);

        //NewsRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        my1.setLayoutParams(params90);
        my1.setBackgroundColor(Color.BLUE);
        my1.setGravity(Gravity.CENTER_VERTICAL);*/





        MyRecyclerAdapter myRecyclerAdapter=new MyRecyclerAdapter(getApplicationContext(),null);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RelativeLayout.LayoutParams r=new RelativeLayout.LayoutParams(width, 2*width/3);

        //r.addRule(Gravity.CENTER_VERTICAL);
        //NewsRecycler.setLayoutParams(r);

        NewsRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        NewsRecycler.addItemDecoration(new EqualSpacingItemDecoration(20, EqualSpacingItemDecoration.HORIZONTAL)); // 16px. In practice, you'll want to use getDimensionPixelSize
        NewsRecycler.setItemAnimator(new DefaultItemAnimator());
        NewsRecycler.setAdapter(myRecyclerAdapter);
        News.addView(NewsRecycler);






        LinearLayout Button_Group=new LinearLayout(this);
        RelativeLayout.LayoutParams Button_Group_Layout = new RelativeLayout.LayoutParams(width, width/3);
        Button_Group.setLayoutParams(Button_Group_Layout);
        Button_Group.setBackgroundColor(Color.DKGRAY);


        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(300, 400);
        ImageButton imageButton=new ImageButton(this);
        imageButton.setLayoutParams(params4);
        imageButton.setImageDrawable((getResources().getDrawable(R.mipmap.ic_launcher)));

        //Root_Layout.addView(Row_Layout);



        //Root_Layout.addView(imageButton);
        //Root_Layout.addView(imageView);


        ArrayList<String>  Orders= new ArrayList<>();
        Orders.add("News");
        Orders.add("Button_Group");
        Orders.add("Row_Layout");
        Orders.add("Slider");






        for (String item:Orders
                ) {
            switch (item)
            {
                case "Slider":
                    Root_Layout.addView(ImageSlider);
                    break;
                case "Row_Layout":
                    Root_Layout.addView(Row_Layout);
                    break;
                case "News":
                    Root_Layout.addView(News);
                    break;
                case "Button_Group":
                    Root_Layout.addView(Button_Group);
                    break;
            }
        }
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

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }
}
