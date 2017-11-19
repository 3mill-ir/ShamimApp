package ir.hezareh.park;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {
    int width;

    public static String URL_encode(String URL) {
        final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
        return Uri.encode(URL, ALLOWED_URI_CHARS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        width = Utils.getDisplayMetrics(getApplicationContext()).widthPixels;

        LinearLayout Root_Layout=(LinearLayout) findViewById(R.id.main_layout);


        ArrayList<String> my=new ArrayList<>();
        my.add(URL_encode("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg"));
        my.add(URL_encode("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg"));
        my.add(URL_encode("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg"));


        RelativeLayout Slider = new RelativeLayout(this);
        RelativeLayout.LayoutParams Slider_Layout = new RelativeLayout.LayoutParams(width, width/2);
        Slider.setLayoutParams(Slider_Layout);
        View child = getLayoutInflater().inflate(R.layout.custom_slider_layout, null);


        com.daimajia.slider.library.SliderLayout ImageSlider = child.findViewById(R.id.slider);
        for (int i = 0; i < my.size(); i++) {
            DefaultSliderView demoSlider = new DefaultSliderView(this);
            demoSlider//.description()
                    .image(my.get(i))
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            /*Intent k = new Intent(HomeScreen.this, Kanded_list_News.class);
                            k.putExtra("value_theme", value_theme);
                            startActivity(k);
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);*/
                        }
                    });
            ImageSlider.addSlider(demoSlider);
            //ImageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            ImageSlider.setCustomIndicator((PagerIndicator) child.findViewById(R.id.custom_indicator));
            ImageSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
            ImageSlider.setCustomAnimation(new DescriptionAnimation());
        }
        Slider.addView(child);


        //RecyclerView for News
        RecyclerView NewsRecycler=new RecyclerView(this);
        MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(getApplicationContext(), null);
        LinearLayout.LayoutParams NewsRecyclerLayoutParams = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        NewsRecyclerLayoutParams.gravity = Gravity.CENTER_VERTICAL;
        NewsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        NewsRecycler.addItemDecoration(new EqualSpacingItemDecoration(10, EqualSpacingItemDecoration.HORIZONTAL));
        NewsRecycler.setItemAnimator(new DefaultItemAnimator());
        NewsRecycler.setLayoutParams(NewsRecyclerLayoutParams);
        NewsRecycler.setAdapter(myRecyclerAdapter);


        ArrayList<String> text = new ArrayList<>();
        text.add("about");
        text.add("contact");
        text.add("Park");


        ArrayList<String> Orders = new ArrayList<>();
        Orders.add("Slider");
        Orders.add("NewsRecycler");

        Orders.add("Button_Group");

        Orders.add("Gallery");
        Orders.add("Button_Group");
        Orders.add("ROW");

        for (String item : Orders) {
            switch (item) {
                case "Slider":
                    Root_Layout.addView(Slider);
                    break;
                case "Gallery":
                    Root_Layout.addView(GalleryButton(my, "GalleryButtons", text, my));
                    break;
                case "NewsRecycler":
                    Root_Layout.addView(NewsRecycler);
                    break;
                case "Button_Group":
                    Root_Layout.addView(ButtonsRow(text, my));
                    break;
                case "ROW":
                    Root_Layout.addView(GalleryButton(my, "ButtonsGallery", text, my));
                    break;
            }
        }
    }

    public RelativeLayout GalleryButton(ArrayList<String> data, String Order, ArrayList<String> ButtonsText, ArrayList<String> ButtonsURLs) {
        RelativeLayout GalleryButtonRowLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams GalleryButtonRowLayoutParams = new RelativeLayout.LayoutParams(width, 2 * width / 3);
        GalleryButtonRowLayout.setLayoutParams(GalleryButtonRowLayoutParams);
        GalleryButtonRowLayout.setId(View.generateViewId());


        View upperChild = getLayoutInflater().inflate(R.layout.item_button_row, null);
        LinearLayout.LayoutParams ButtonParams = new LinearLayout.LayoutParams(width / 3, width / 3);
        upperChild.setLayoutParams(ButtonParams);
        ((TextView) upperChild.findViewById(R.id.TextButton)).setText(ButtonsText.get(0));
        Picasso.with(getApplicationContext()).load(ButtonsURLs.get(0)).fit()
                .into((ImageView) upperChild.findViewById(R.id.ButtonImage));


        LinearLayout UpperButtonLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams UpperButtonLayoutParams = new RelativeLayout.LayoutParams(width / 3, width / 3);
        if (Order.equals("ButtonsGallery")) {
            UpperButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        } else if (Order.equals("GalleryButtons")) {
            UpperButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        }
        UpperButtonLayout.setLayoutParams(UpperButtonLayoutParams);
        //UpperButtonLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        UpperButtonLayout.setId(View.generateViewId());
        UpperButtonLayout.setGravity(Gravity.CENTER);
        UpperButtonLayout.addView(upperChild);

        View lowerChild = getLayoutInflater().inflate(R.layout.item_button_row, null);
        lowerChild.setLayoutParams(ButtonParams);
        ((TextView) lowerChild.findViewById(R.id.TextButton)).setText(ButtonsText.get(1));
        Picasso.with(getApplicationContext()).load(ButtonsURLs.get(1)).fit()
                .into((ImageView) lowerChild.findViewById(R.id.ButtonImage));

        LinearLayout LowerButtonLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams LowerButtonLayoutParams = new RelativeLayout.LayoutParams(width / 3, width / 3);
        LowerButtonLayoutParams.addRule(RelativeLayout.BELOW, UpperButtonLayout.getId());


        if (Order.equals("ButtonsGallery")) {
            LowerButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        } else if (Order.equals("GalleryButtons")) {
            LowerButtonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        }

        LowerButtonLayout.setLayoutParams(LowerButtonLayoutParams);
        //LowerButtonLayout.setBackgroundColor(Color.CYAN);
        LowerButtonLayout.setId(View.generateViewId());
        LowerButtonLayout.setGravity(Gravity.CENTER);
        LowerButtonLayout.addView(lowerChild);


        com.daimajia.slider.library.SliderLayout GalleryLayout = new SliderLayout(this);
        RelativeLayout.LayoutParams GalleryLayoutParams = new RelativeLayout.LayoutParams(2 * width / 3, 2 * width / 3 - width / 12);
        if (Order.equals("ButtonsGallery")) {
            GalleryLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        } else if (Order.equals("GalleryButtons")) {
            GalleryLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        }

        GalleryLayout.setLayoutParams(GalleryLayoutParams);
        GalleryLayout.setId(View.generateViewId());


        for (int i = 0; i < data.size(); i++) {
            DefaultSliderView demoSlider = new DefaultSliderView(this);
            demoSlider//.description()
                    .image(data.get(i))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            /*Intent k = new Intent(HomeScreen.this, Kanded_list_News.class);
                            k.putExtra("value_theme", value_theme);
                            startActivity(k);
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);*/
                        }
                    });
            GalleryLayout.addSlider(demoSlider);
            GalleryLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            GalleryLayout.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
            GalleryLayout.setCustomAnimation(new DescriptionAnimation());
        }
        TextView GalleryLayoutText = new TextView(this);
        RelativeLayout.LayoutParams GalleryLayoutTextParams = new RelativeLayout.LayoutParams(2 * width / 3, width / 12);
        GalleryLayoutTextParams.addRule(RelativeLayout.BELOW, GalleryLayout.getId());
        if (Order.equals("ButtonsGallery")) {
            GalleryLayoutTextParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        } else if (Order.equals("GalleryButtons")) {
            GalleryLayoutTextParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        }
        GalleryLayoutText.setLayoutParams(GalleryLayoutTextParams);
        GalleryLayoutText.setBackgroundColor(Color.YELLOW);
        GalleryLayoutText.setGravity(Gravity.CENTER);
        GalleryLayoutText.setText("Gallery");


        GalleryButtonRowLayout.addView(UpperButtonLayout);
        GalleryButtonRowLayout.addView(LowerButtonLayout);
        GalleryButtonRowLayout.addView(GalleryLayout);
        GalleryButtonRowLayout.addView(GalleryLayoutText);

        return GalleryButtonRowLayout;
    }

    public LinearLayout ButtonsRow(ArrayList<String> ButtonsText, ArrayList<String> ButtonsURLs) {
        LinearLayout ButtonsRow = new LinearLayout(this);
        LinearLayout.LayoutParams ButtonRowLayout = new LinearLayout.LayoutParams(width, width / 3);
        ButtonsRow.setLayoutParams(ButtonRowLayout);
        //ButtonsRow.setBackgroundColor(Color.DKGRAY);
        ButtonsRow.setOrientation(LinearLayout.HORIZONTAL);


        /*Button LeftButton=new Button(this);
        LinearLayout.LayoutParams LeftButtonParams = new LinearLayout.LayoutParams(width/3-20, width/3-20);
        LeftButtonLayoutParams.setMargins(10,10,10,10);
        LeftButton.setLayoutParams(LeftButtonParams);
        LeftButton.setGravity(Gravity.CENTER);

        LeftButton.setBackgroundResource(R.drawable.customborder1);
        LeftButton.setPadding(0,20,0,0);
        //resizing image
        Bitmap InitialBitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.microphone)).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(InitialBitmap, 100, 100, false);
        LeftButton.setCompoundDrawablesWithIntrinsicBounds(null,new BitmapDrawable(getResources(), bitmapResized),null,null);
        LeftButton.setText("عضویت در پارک");
        LeftButton.setTextSize(14);*/


        View leftChild = getLayoutInflater().inflate(R.layout.item_button_row, null);
        LinearLayout.LayoutParams ButtonParams = new LinearLayout.LayoutParams(width / 3, width / 3);
        leftChild.setLayoutParams(ButtonParams);
        ((TextView) leftChild.findViewById(R.id.TextButton)).setText(ButtonsText.get(0));
        Picasso.with(getApplicationContext()).load(ButtonsURLs.get(0)).fit()
                .into((ImageView) leftChild.findViewById(R.id.ButtonImage));
        LinearLayout LeftButtonLayout = new LinearLayout(this);
        LeftButtonLayout.setLayoutParams(ButtonParams);
        LeftButtonLayout.setGravity(Gravity.CENTER);
        LeftButtonLayout.addView(leftChild);


        View middleChild = getLayoutInflater().inflate(R.layout.item_button_row, null);
        middleChild.setLayoutParams(ButtonParams);
        ((TextView) middleChild.findViewById(R.id.TextButton)).setText(ButtonsText.get(1));
        Picasso.with(getApplicationContext()).load(ButtonsURLs.get(1)).fit()
                .into((ImageView) middleChild.findViewById(R.id.ButtonImage));
        LinearLayout MiddleButtonLayout = new LinearLayout(this);
        MiddleButtonLayout.setLayoutParams(ButtonParams);
        MiddleButtonLayout.setGravity(Gravity.CENTER);
        MiddleButtonLayout.addView(middleChild);


        View rightChild = getLayoutInflater().inflate(R.layout.item_button_row, null);
        rightChild.setLayoutParams(ButtonParams);
        ((TextView) rightChild.findViewById(R.id.TextButton)).setText(ButtonsText.get(2));
        //rightChild.findViewById(R.id.TextButton).setBackgroundResource(R.drawable.back2);
        Picasso.with(getApplicationContext()).load(ButtonsURLs.get(2)).fit()
                .into((ImageView) rightChild.findViewById(R.id.ButtonImage));
        LinearLayout RightButtonLayout = new LinearLayout(this);
        RightButtonLayout.setLayoutParams(ButtonParams);
        RightButtonLayout.setGravity(Gravity.CENTER);
        RightButtonLayout.addView(rightChild);


        ButtonsRow.addView(LeftButtonLayout);
        ButtonsRow.addView(MiddleButtonLayout);
        ButtonsRow.addView(RightButtonLayout);
        return ButtonsRow;
    }
}
