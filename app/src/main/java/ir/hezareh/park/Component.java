package ir.hezareh.park;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.mzelzoghbi.zgallery.ZGrid;
import com.mzelzoghbi.zgallery.entities.ZColor;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ir.hezareh.park.models.Item;


public class Component {
    Context activity;

    public Component(Context _context) {
        this.activity = _context;
    }


    public RelativeLayout Slider(int width, int height, List<Item> Items) {
        RelativeLayout Slider = new RelativeLayout(activity);
        RelativeLayout.LayoutParams Slider_Layout = new RelativeLayout.LayoutParams(width, width / 2);
        Slider.setLayoutParams(Slider_Layout);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.custom_slider_layout, null);


        com.daimajia.slider.library.SliderLayout ImageSlider = child.findViewById(R.id.slider);
        for (Item item : Items) {
            DefaultSliderView demoSlider = new DefaultSliderView(activity);
            demoSlider//.description()
                    .image(item.getImage())
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Intent k = new Intent(activity, NewsListActivity.class);
                            activity.startActivity(k);
                        }
                    });
            ImageSlider.addSlider(demoSlider);
            //ImageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            ImageSlider.setCustomIndicator((PagerIndicator) child.findViewById(R.id.custom_indicator));
            ImageSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
            ImageSlider.setCustomAnimation(new DescriptionAnimation());

        }
        Slider.addView(child);
        return Slider;
    }

    public RecyclerView News(int width, int height) {
        RecyclerView NewsRecycler = new RecyclerView(activity);

        RecyclerViewAdapter myRecyclerAdapter = new RecyclerViewAdapter(activity, null);

        LinearLayout.LayoutParams NewsRecyclerLayoutParams = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        NewsRecyclerLayoutParams.gravity = Gravity.CENTER_VERTICAL;
        NewsRecycler.setLayoutParams(NewsRecyclerLayoutParams);

        NewsRecycler.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true));
        NewsRecycler.addItemDecoration(new EqualSpacingItemDecoration(10, EqualSpacingItemDecoration.HORIZONTAL));
        NewsRecycler.setItemAnimator(new DefaultItemAnimator());
        NewsRecycler.setAdapter(myRecyclerAdapter);

        return NewsRecycler;
    }


    public LinearLayout pollQuestion(int screenWidth, int height, ArrayList<String> data, String QuestionText) {
        //ScrollView PollQuestionScrollView = new ScrollView(activity);
        //PollQuestionScrollView.setVerticalScrollBarEnabled(true);
        LinearLayout PollQuestionLayout = new LinearLayout(activity);
        PollQuestionLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams PollQuestionLayoutParams = new LinearLayout.LayoutParams(screenWidth, screenWidth / 2);
        //PollQuestionLayoutParams.gravity=Gravity.END;
        PollQuestionLayout.setLayoutParams(PollQuestionLayoutParams);
        //PollQuestionLayout.setGravity(Gravity.END);
        //PollQuestionScrollView.setLayoutParams(PollQuestionLayoutParams);

        TextView questionText = new TextView(activity);
        ViewGroup.LayoutParams questionTextLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        questionText.setLayoutParams(questionTextLayoutParams);
        questionText.setText(QuestionText);
        questionText.setGravity(Gravity.END);
        /*questionText.setSingleLine(true);
        //questionText.setEllipsize(TextUtils.TruncateAt.END);
        questionText.setEllipsize(TextUtils.TruncateAt.MARQUEE);

        questionText.setMarqueeRepeatLimit(-1);
        questionText.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        //questionText.setTextDirection(View.TEXT_DIRECTION_RTL);
        questionText.setGravity(Gravity.END);


        *//*Animation animationToRight = new TranslateAnimation(-200,200, 0, 0);
        Animation animationToLeft = new TranslateAnimation(200, -200, 0, 0);

        animationToLeft.setDuration(1200);
        animationToLeft.setRepeatMode(Animation.RESTART);
        animationToLeft.setRepeatCount(Animation.INFINITE);*//*

        questionText.setFocusable(true);
        questionText.setFocusableInTouchMode(true);
        questionText.setHorizontallyScrolling(true);
        questionText.setSelected(true);*/

        questionText.setTypeface(new Utils(activity).font_set("irsans"));
        Paint textPaint = questionText.getPaint();
        String text = questionText.getText().toString();//get text
        int textWidth = Math.round(textPaint.measureText(text));//measure the text size
        ViewGroup.LayoutParams params = questionText.getLayoutParams();
        params.width = textWidth;
        questionText.setLayoutParams(params); //refine

        /*DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;*/

        //this is optional. do not scroll if text is shorter than screen width
        //remove this won't effect the scroll
        if (textWidth <= screenWidth /*screenWidth*/) {
            //All text can fit in screen.
            //return;
        } else {
            //set the animation

            TranslateAnimation slide = new TranslateAnimation(-textWidth, screenWidth, 0, 0);
            slide.setDuration(textWidth * 5 + screenWidth);
            slide.setRepeatCount(Animation.INFINITE);
            slide.setRepeatMode(Animation.RESTART);
            slide.setInterpolator(new LinearInterpolator());
            questionText.startAnimation(slide);
        }


        RadioGroup radioGroupAnswers = new RadioGroup(activity);
        radioGroupAnswers.setOrientation(LinearLayout.HORIZONTAL);
        //radioGroupAnswers.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        LinearLayout.LayoutParams answerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //params11.gravity=Gravity.START;
        radioGroupAnswers.setLayoutParams(answerParams);
        //radioGroupAnswers.setGravity(Gravity.START);
        radioGroupAnswers.setBackgroundColor(Color.YELLOW);

        for (String item : data) {
            RadioButton Choice = new RadioButton(activity);
            Choice.setText(item);
            //Choice.setLeft(100);
            //Choice.setLayoutParams(params11);
            Choice.setGravity(Gravity.CENTER);
            Choice.setHighlightColor(Color.YELLOW);
            Choice.setId(View.generateViewId());
            radioGroupAnswers.addView(Choice);
        }
        PollQuestionLayout.addView(questionText);
        PollQuestionLayout.addView(radioGroupAnswers);

        //PollQuestionScrollView.addView(PollQuestionLayout);

        return PollQuestionLayout;
    }

    public RelativeLayout GalleryButton(int width, List<Item> Items, String Order, ArrayList<String> ButtonsText, ArrayList<String> ButtonsURLs) {
        RelativeLayout GalleryButtonRowLayout = new RelativeLayout(activity);
        RelativeLayout.LayoutParams GalleryButtonRowLayoutParams = new RelativeLayout.LayoutParams(width, 2 * width / 3);
        GalleryButtonRowLayout.setLayoutParams(GalleryButtonRowLayoutParams);
        GalleryButtonRowLayout.setId(View.generateViewId());

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View upperChild = inflater.inflate(R.layout.item_button_row, null);
        LinearLayout.LayoutParams ButtonParams = new LinearLayout.LayoutParams(width / 3, width / 3);
        upperChild.setLayoutParams(ButtonParams);
        ((TextView) upperChild.findViewById(R.id.TextButton)).setText(ButtonsText.get(0));
        Picasso.with(activity).load(ButtonsURLs.get(0)).fit()
                .into((ImageView) upperChild.findViewById(R.id.ButtonImage));


        LinearLayout UpperButtonLayout = new LinearLayout(activity);
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

        View lowerChild = inflater.inflate(R.layout.item_button_row, null);
        lowerChild.setLayoutParams(ButtonParams);
        ((TextView) lowerChild.findViewById(R.id.TextButton)).setText(ButtonsText.get(1));
        Picasso.with(activity.getApplicationContext()).load(ButtonsURLs.get(1)).fit()
                .into((ImageView) lowerChild.findViewById(R.id.ButtonImage));

        LinearLayout LowerButtonLayout = new LinearLayout(activity);
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


        com.daimajia.slider.library.SliderLayout GalleryLayout = new SliderLayout(activity);
        RelativeLayout.LayoutParams GalleryLayoutParams = new RelativeLayout.LayoutParams(2 * width / 3, 2 * width / 3 - width / 12);
        if (Order.equals("ButtonsGallery")) {
            GalleryLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        } else if (Order.equals("GalleryButtons")) {
            GalleryLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        }

        GalleryLayout.setLayoutParams(GalleryLayoutParams);
        GalleryLayout.setId(View.generateViewId());


        for (Item item : Items) {
            DefaultSliderView demoSlider = new DefaultSliderView(activity);
            demoSlider//.description()
                    .image(item.getImage())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Intent k = new Intent(activity, FanBazar.class);
                            activity.startActivity(k);
                        }
                    });
            GalleryLayout.addSlider(demoSlider);
            GalleryLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            GalleryLayout.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
            GalleryLayout.setCustomAnimation(new DescriptionAnimation());

        }
        TextView GalleryLayoutText = new TextView(activity);
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

    public LinearLayout ButtonsRow(int width, ArrayList<String> ButtonsText, final ArrayList<String> ButtonsURLs) {
        LinearLayout ButtonsRow = new LinearLayout(activity);
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

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View leftChild = inflater.inflate(R.layout.item_button_row, null);
        LinearLayout.LayoutParams ButtonParams = new LinearLayout.LayoutParams(width / 3, width / 3);
        leftChild.setLayoutParams(ButtonParams);
        ((TextView) leftChild.findViewById(R.id.TextButton)).setText(ButtonsText.get(0));
        Picasso.with(activity).load(ButtonsURLs.get(0)).fit()
                .into((ImageView) leftChild.findViewById(R.id.ButtonImage));
        LinearLayout LeftButtonLayout = new LinearLayout(activity);
        LeftButtonLayout.setLayoutParams(ButtonParams);
        LeftButtonLayout.setGravity(Gravity.CENTER);
        LeftButtonLayout.addView(leftChild);


        View middleChild = inflater.inflate(R.layout.item_button_row, null);
        middleChild.setLayoutParams(ButtonParams);
        ((TextView) middleChild.findViewById(R.id.TextButton)).setText(ButtonsText.get(1));
        Picasso.with(activity).load(ButtonsURLs.get(1)).fit()
                .into((ImageView) middleChild.findViewById(R.id.ButtonImage));
        LinearLayout MiddleButtonLayout = new LinearLayout(activity);
        MiddleButtonLayout.setLayoutParams(ButtonParams);
        MiddleButtonLayout.setGravity(Gravity.CENTER);
        MiddleButtonLayout.addView(middleChild);


        View rightChild = inflater.inflate(R.layout.item_button_row, null);
        rightChild.setLayoutParams(ButtonParams);
        rightChild.setId(View.generateViewId());
        ((TextView) rightChild.findViewById(R.id.TextButton)).setText(ButtonsText.get(2));
        //rightChild.findViewById(R.id.TextButton).setBackgroundResource(R.drawable.back2);
        Picasso.with(activity).load(ButtonsURLs.get(2)).fit()
                .into((ImageView) rightChild.findViewById(R.id.ButtonImage));
        LinearLayout RightButtonLayout = new LinearLayout(activity);
        RightButtonLayout.setLayoutParams(ButtonParams);
        RightButtonLayout.setGravity(Gravity.CENTER);
        RightButtonLayout.addView(rightChild);

        rightChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZGrid.with((Activity) activity, ButtonsURLs)
                        .setToolbarColorResId(R.color.colorPrimary) // toolbar color
                        .setTitle("گالری تصاویر") // toolbar title
                        .setToolbarTitleColor(ZColor.WHITE) // toolbar title color
                        .setSpanCount(3) // colums count
                        .setGridImgPlaceHolder(R.color.colorPrimary) // color placeholder for the grid image until it loads
                        .show();
            }
        });

        ButtonsRow.addView(LeftButtonLayout);
        ButtonsRow.addView(MiddleButtonLayout);
        ButtonsRow.addView(RightButtonLayout);


        return ButtonsRow;
    }


}
