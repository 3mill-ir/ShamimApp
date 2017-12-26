package ir.hezareh.park.Component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import ir.hezareh.park.Adapters.EqualSpacingItemDecoration;
import ir.hezareh.park.Adapters.NewsComponentRecycler;
import ir.hezareh.park.Companies;
import ir.hezareh.park.FanBazar;
import ir.hezareh.park.R;
import ir.hezareh.park.SignIn_SignUp;
import ir.hezareh.park.Utils;
import ir.hezareh.park.models.ModelComponent;


public class Component {
    Context context;

    public Component(Context _context) {
        this.context = _context;
    }


    public RelativeLayout Slider(int width, int height, List<ModelComponent.Item> Items) {
        RelativeLayout Slider = new RelativeLayout(context);
        RelativeLayout.LayoutParams Slider_Layout = new RelativeLayout.LayoutParams(width, width / 2);
        Slider.setLayoutParams(Slider_Layout);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.custom_slider_layout, null);


        com.daimajia.slider.library.SliderLayout ImageSlider = child.findViewById(R.id.slider);
        for (ModelComponent.Item item : Items) {
            DefaultSliderView demoSlider = new DefaultSliderView(context);
            demoSlider//.description()
                    .image(item.getImage().toString())
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            /*Intent k = new Intent(context, NewsDetailActivity.class);
                            context.startActivity(k);*/
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

    public RecyclerView News(int width, int height, ModelComponent modelComponent) {
        RecyclerView NewsRecycler = new RecyclerView(context);

        NewsComponentRecycler newsComponentRecycler = new NewsComponentRecycler(context, modelComponent.getItem());

        LinearLayout.LayoutParams NewsRecyclerLayoutParams = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        NewsRecyclerLayoutParams.gravity = Gravity.CENTER_VERTICAL;
        NewsRecycler.setLayoutParams(NewsRecyclerLayoutParams);

        NewsRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
        NewsRecycler.addItemDecoration(new EqualSpacingItemDecoration(10, EqualSpacingItemDecoration.HORIZONTAL));
        NewsRecycler.setItemAnimator(new DefaultItemAnimator());
        NewsRecycler.setAdapter(newsComponentRecycler);

        return NewsRecycler;
    }


    public LinearLayout pollQuestion(int screenWidth, int height, ModelComponent modelComponent) {
        //ScrollView PollQuestionScrollView = new ScrollView(context);
        //PollQuestionScrollView.setVerticalScrollBarEnabled(true);
        LinearLayout PollQuestionLayout = new LinearLayout(context);

        PollQuestionLayout.setOrientation(LinearLayout.VERTICAL);
        PollQuestionLayout.setBackgroundResource(R.drawable.item_background);
        LinearLayout.LayoutParams PollQuestionLayoutParams = new LinearLayout.LayoutParams(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        PollQuestionLayoutParams.setMargins(0, 20, 0, 20);
        //PollQuestionLayout.setMinimumHeight(screenWidth/2);
        ///PollQuestionLayout.setGravity(Gravity.CENTER);
        PollQuestionLayout.setLayoutParams(PollQuestionLayoutParams);
        //PollQuestionScrollView.setLayoutParams(PollQuestionLayoutParams);

        TextView questionText = new TextView(context);
        LinearLayout.LayoutParams questionTextLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        questionTextLayoutParams.gravity = Gravity.CENTER;
        questionText.setLayoutParams(questionTextLayoutParams);

        questionText.setText(modelComponent.getQuestion());
        questionText.setGravity(Gravity.CENTER);
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

        questionText.setTypeface(new Utils(context).font_set("irsans"));
        Paint textPaint = questionText.getPaint();
        String text = questionText.getText().toString();//get text
        int textWidth = Math.round(textPaint.measureText(text));//measure the text size
        ViewGroup.LayoutParams params = questionText.getLayoutParams();
        params.width = textWidth;
        //questionText.setTextDirection();

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


        RadioGroup radioGroupAnswers = new RadioGroup(context);
        radioGroupAnswers.setOrientation(LinearLayout.VERTICAL);
        radioGroupAnswers.setGravity(Gravity.END);
        //radioGroupAnswers.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        LinearLayout.LayoutParams answerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        radioGroupAnswers.setLayoutParams(answerParams);

        //radioGroupAnswers.setGravity(Gravity.START);
        //radioGroupAnswers.setBackgroundColor(Color.YELLOW);
        radioGroupAnswers.setPadding(0, 0, 20, 20);

        for (ModelComponent.Item item : modelComponent.getItem()) {
            RadioButton Choice = new RadioButton(context);
            Choice.setText(item.getText());
            Choice.setTypeface(new Utils(context).font_set("BYekan"));
            //Choice.setLeft(100);
            //Choice.setLayoutParams(params11);
            Choice.setGravity(Gravity.CENTER);
            //Choice.setHighlightColor(Color.YELLOW);
            Choice.setId(View.generateViewId());
            radioGroupAnswers.addView(Choice);
        }
        PollQuestionLayout.addView(questionText);
        PollQuestionLayout.addView(radioGroupAnswers);

        //PollQuestionScrollView.addView(PollQuestionLayout);

        return PollQuestionLayout;
    }

    public RelativeLayout GalleryButton(int width, ModelComponent modelComponent, String Order) {
        RelativeLayout GalleryButtonRowLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams GalleryButtonRowLayoutParams = new RelativeLayout.LayoutParams(width, 2 * width / 3);
        GalleryButtonRowLayout.setLayoutParams(GalleryButtonRowLayoutParams);
        GalleryButtonRowLayout.setId(View.generateViewId());

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View upperChild = inflater.inflate(R.layout.item_button_row, null);
        LinearLayout.LayoutParams ButtonParams = new LinearLayout.LayoutParams(width / 3, width / 3);
        upperChild.setLayoutParams(ButtonParams);
        ((TextView) upperChild.findViewById(R.id.TextButton)).setText(modelComponent.getButtonItem().get(0).getText());
        ((TextView) upperChild.findViewById(R.id.TextButton)).setTypeface(new Utils(context).font_set("BHoma"));
        Picasso.with(context).load(modelComponent.getButtonItem().get(0).getImage().toString()).fit()
                .into((ImageView) upperChild.findViewById(R.id.ButtonImage));


        LinearLayout UpperButtonLayout = new LinearLayout(context);
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
        upperChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(context, FanBazar.class);
                context.startActivity(k);
            }
        });


        View lowerChild = inflater.inflate(R.layout.item_button_row, null);
        lowerChild.setLayoutParams(ButtonParams);
        ((TextView) lowerChild.findViewById(R.id.TextButton)).setText(modelComponent.getButtonItem().get(1).getText());
        ((TextView) lowerChild.findViewById(R.id.TextButton)).setTypeface(new Utils(context).font_set("BHoma"));
        Picasso.with(context.getApplicationContext())
                .load(modelComponent.getButtonItem().get(1).getImage().toString())
                .fit()
                .into((ImageView) lowerChild.findViewById(R.id.ButtonImage));

        LinearLayout LowerButtonLayout = new LinearLayout(context);
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
        LowerButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(context, Companies.class);
                context.startActivity(k);
            }
        });


        com.daimajia.slider.library.SliderLayout GalleryLayout = new SliderLayout(context);
        RelativeLayout.LayoutParams GalleryLayoutParams = new RelativeLayout.LayoutParams(2 * width / 3, 2 * width / 3 - width / 12);
        if (Order.equals("ButtonsGallery")) {
            GalleryLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        } else if (Order.equals("GalleryButtons")) {
            GalleryLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        }

        GalleryLayout.setLayoutParams(GalleryLayoutParams);
        GalleryLayout.setId(View.generateViewId());


        for (ModelComponent.GalleryItem item : modelComponent.getGalleryItem()) {
            DefaultSliderView demoSlider = new DefaultSliderView(context);
            demoSlider//.description()
                    .image(item.getImage().toString())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Intent k = new Intent(context, SignIn_SignUp.class);
                            context.startActivity(k);
                        }
                    });
            GalleryLayout.addSlider(demoSlider);
            GalleryLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            GalleryLayout.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
            GalleryLayout.setCustomAnimation(new DescriptionAnimation());

        }
        TextView GalleryLayoutText = new TextView(context);
        RelativeLayout.LayoutParams GalleryLayoutTextParams = new RelativeLayout.LayoutParams(2 * width / 3, width / 12);
        GalleryLayoutTextParams.addRule(RelativeLayout.BELOW, GalleryLayout.getId());
        if (Order.equals("ButtonsGallery")) {
            GalleryLayoutTextParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        } else if (Order.equals("GalleryButtons")) {
            GalleryLayoutTextParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        }
        GalleryLayoutText.setLayoutParams(GalleryLayoutTextParams);
        GalleryLayoutText.setBackgroundResource(R.drawable.back);
        GalleryLayoutText.setGravity(Gravity.CENTER);
        GalleryLayoutText.setText("گالری تصاویر");
        GalleryLayoutText.setTypeface(new Utils(context).font_set("BHoma"));


        GalleryButtonRowLayout.addView(UpperButtonLayout);
        GalleryButtonRowLayout.addView(LowerButtonLayout);
        GalleryButtonRowLayout.addView(GalleryLayout);
        GalleryButtonRowLayout.addView(GalleryLayoutText);

        return GalleryButtonRowLayout;
    }

    public LinearLayout ButtonsRow(int width, ModelComponent modelComponent, final ArrayList<String> urls) {
        LinearLayout ButtonsRow = new LinearLayout(context);
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View leftChild = inflater.inflate(R.layout.item_button_row, null);
        LinearLayout.LayoutParams ButtonParams = new LinearLayout.LayoutParams(width / 3, width / 3);
        leftChild.setLayoutParams(ButtonParams);
        ((TextView) leftChild.findViewById(R.id.TextButton)).setText(modelComponent.getItem().get(0).getText());
        ((TextView) leftChild.findViewById(R.id.TextButton)).setTypeface(new Utils(context).font_set("BHoma"));
        Picasso.with(context)
                .load(modelComponent.getItem().get(0).getImage().toString())
                .fit()
                .into((ImageView) leftChild.findViewById(R.id.ButtonImage));
        LinearLayout LeftButtonLayout = new LinearLayout(context);
        LeftButtonLayout.setLayoutParams(ButtonParams);
        LeftButtonLayout.setGravity(Gravity.CENTER);
        LeftButtonLayout.addView(leftChild);


        View middleChild = inflater.inflate(R.layout.item_button_row, null);
        middleChild.setLayoutParams(ButtonParams);
        ((TextView) middleChild.findViewById(R.id.TextButton)).setText(modelComponent.getItem().get(1).getText());
        ((TextView) middleChild.findViewById(R.id.TextButton)).setTypeface(new Utils(context).font_set("BHoma"));
        Picasso.with(context)
                .load(modelComponent.getItem().get(1).getImage().toString())
                .fit()
                .into((ImageView) middleChild.findViewById(R.id.ButtonImage));
        LinearLayout MiddleButtonLayout = new LinearLayout(context);
        MiddleButtonLayout.setLayoutParams(ButtonParams);
        MiddleButtonLayout.setGravity(Gravity.CENTER);
        MiddleButtonLayout.addView(middleChild);


        View rightChild = inflater.inflate(R.layout.item_button_row, null);
        rightChild.setLayoutParams(ButtonParams);
        rightChild.setId(View.generateViewId());
        ((TextView) rightChild.findViewById(R.id.TextButton)).setText(modelComponent.getItem().get(2).getText());
        ((TextView) rightChild.findViewById(R.id.TextButton)).setTypeface(new Utils(context).font_set("BHoma"));
        //rightChild.findViewById(R.id.TextButton).setBackgroundResource(R.drawable.back2);
        Picasso.with(context)
                .load(modelComponent.getItem().get(2).getImage().toString())
                .fit()
                .into((ImageView) rightChild.findViewById(R.id.ButtonImage));
        LinearLayout RightButtonLayout = new LinearLayout(context);
        RightButtonLayout.setLayoutParams(ButtonParams);
        RightButtonLayout.setGravity(Gravity.CENTER);
        RightButtonLayout.addView(rightChild);

        rightChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZGrid.with((Activity) context, urls)
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
