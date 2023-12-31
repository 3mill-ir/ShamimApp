package ir.hezareh.park.Component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.hezareh.park.Adapters.EqualSpacingItemDecoration;
import ir.hezareh.park.Adapters.NewsComponentRecycler;
import ir.hezareh.park.Companies;
import ir.hezareh.park.DataLoading.SharedPreferencesManager;
import ir.hezareh.park.DataLoading.networking;
import ir.hezareh.park.FanBazar;
import ir.hezareh.park.GalleryFolderActivity;
import ir.hezareh.park.NewsCategory;
import ir.hezareh.park.NewsDetailActivity;
import ir.hezareh.park.R;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.WebviewActivity;
import ir.hezareh.park.models.Item;
import ir.hezareh.park.models.ModelComponent;

import static ir.hezareh.park.HomeScreen.global;
import static ir.hezareh.park.Util.Utils.MessageType.confirmation;
import static ir.hezareh.park.Util.Utils.MessageType.duplicate_entry;
import static ir.hezareh.park.Util.Utils.MessageType.server_error;


public class Component {
    private Context context;
    private SharedPreferencesManager preferencesManager;

    public Component(Context _context) {
        this.context = _context;
        preferencesManager = new SharedPreferencesManager(context);

    }

    public RelativeLayout Slider(int width, int height, List<Item> Items) {
        RelativeLayout Slider = new RelativeLayout(context);
        RelativeLayout.LayoutParams Slider_Layout = new RelativeLayout.LayoutParams(width, width / 2);
        Slider.setLayoutParams(Slider_Layout);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View child = inflater.inflate(R.layout.custom_slider_layout, null);


        com.daimajia.slider.library.SliderLayout ImageSlider = child.findViewById(R.id.slider);
        for (Item item : Items) {
            DefaultSliderView demoSlider = new DefaultSliderView(context);
            demoSlider//.description()
                    .image(Utils.URL_encode(item.getImage().toString()))
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            /*customAlertDialog alertDialog = new customAlertDialog((Activity) context, "بروزرسانی", context.getString(R.string.update_message), "آره", "نه", new customAlertDialog.yesOrNoClicked() {
                                @Override
                                public void positiveClicked() {

                                }

                                @Override
                                public void negativeClicked() {
                                }
                            });
                            alertDialog.show();*/
                        }
                    });
            ImageSlider.addSlider(demoSlider);
            //ImageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            ImageSlider.setCustomIndicator((PagerIndicator) child.findViewById(R.id.custom_indicator));
            ImageSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
            ImageSlider.setCustomAnimation(new DescriptionAnimation());

        }
        Slider.setBackgroundResource(R.drawable.back);
        Slider.addView(child);
        return Slider;
    }

    public RecyclerView News(int width, int height, ModelComponent modelComponent) {
        RecyclerView NewsRecycler = new RecyclerView(context);

        NewsComponentRecycler newsComponentRecycler = new NewsComponentRecycler(context, modelComponent, true);

        LinearLayout.LayoutParams NewsRecyclerLayoutParams = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        NewsRecyclerLayoutParams.gravity = Gravity.CENTER_VERTICAL;
        NewsRecycler.setLayoutParams(NewsRecyclerLayoutParams);

        NewsRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
        NewsRecycler.addItemDecoration(new EqualSpacingItemDecoration(10, EqualSpacingItemDecoration.HORIZONTAL));
        NewsRecycler.setItemAnimator(new DefaultItemAnimator());
        NewsRecycler.setAdapter(newsComponentRecycler);

        return NewsRecycler;
    }

    public View pollQuestion(int screenWidth, int height, final ModelComponent modelComponent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View pollQuestionLayout = inflater.inflate(R.layout.poll_question_item, null);
        TextView questionText = pollQuestionLayout.findViewById(R.id.question_text);
        questionText.setText(modelComponent.getQuestion());


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

        new Utils(context).animateText(questionText, screenWidth);


        final RadioGroup radioGroupAnswers = pollQuestionLayout.findViewById(R.id.radio_group_answers);
        radioGroupAnswers.setOrientation(LinearLayout.VERTICAL);
        radioGroupAnswers.setGravity(Gravity.END);
        //radioGroupAnswers.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


        for (Item item : modelComponent.getItem()) {
            RadioButton Choice = new RadioButton(context);
            Choice.setText(item.getText());
            Choice.setTypeface(new Utils(context).font_set("BYekan"));
            //Choice.setLeft(100);
            Choice.setGravity(Gravity.CENTER);
            //Choice.setHighlightColor(Color.YELLOW);
            Choice.setId(View.generateViewId());
            radioGroupAnswers.addView(Choice);
        }
        final Button pollButton = pollQuestionLayout.findViewById(R.id.poll_btn);
        pollButton.setEnabled(false);
        radioGroupAnswers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                final RadioButton Choice = pollQuestionLayout.findViewById(checkedId);


                Choice.setTextColor(Color.parseColor("#fe9c02"));

                int count = group.getChildCount();

                for (int i = 0; i < count; i++) {
                    View radioButton = group.getChildAt(i);
                    if (radioButton instanceof RadioButton) {
                        if (radioButton != Choice) {
                            ((RadioButton) radioButton).setTextColor(Color.BLACK);
                        }
                    }

                }
                Log.e("index", modelComponent.getItem().get(radioGroupAnswers.indexOfChild(Choice)).getID() + "");

                if (radioGroupAnswers.getCheckedRadioButtonId() != -1) {
                    pollButton.setEnabled(true);
                }



                pollButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (preferencesManager.ParticipatedInPoll(false)) {
                            new networking(context).postPoll(modelComponent.getItem().get(radioGroupAnswers.indexOfChild(Choice)).getID(), new networking.PostPollListener() {
                                @Override
                                public void requestStarted() {

                                }

                                @Override
                                public void requestCompleted(String response) {

                                    preferencesManager.ParticipatedInPoll(true);
                                    new Utils(context).showToast(confirmation, (Activity) context);
                                }

                                @Override
                                public void requestEndedWithError(VolleyError error) {
                                    new Utils(context).showToast(server_error, (Activity) context);

                                }
                            });
                        } else {
                            new Utils(context).showToast(duplicate_entry, (Activity) context);
                        }
                    }


                });
            }
        });


        return pollQuestionLayout;
    }


    public RelativeLayout GalleryButton(int width, final ModelComponent modelComponent, String Order) {
        RelativeLayout GalleryButtonRowLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams GalleryButtonRowLayoutParams = new RelativeLayout.LayoutParams(width, 2 * width / 3);
        GalleryButtonRowLayout.setLayoutParams(GalleryButtonRowLayoutParams);
        GalleryButtonRowLayout.setId(View.generateViewId());

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View upperChild = inflater.inflate(R.layout.item_button_row, null);
        LinearLayout.LayoutParams ButtonParams = new LinearLayout.LayoutParams(width / 3, width / 3);
        upperChild.setLayoutParams(ButtonParams);
        ((TextView) upperChild.findViewById(R.id.TextButton)).setText(modelComponent.getButtonItem().get(0).getText());

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

        setClickListener(upperChild, modelComponent.getButtonItem().get(0).getFunctionality(), modelComponent.getButtonItem().get(0).getUrl(), modelComponent.getButtonItem().get(0).getF_MenuID());


        View lowerChild = inflater.inflate(R.layout.item_button_row, null);
        lowerChild.setLayoutParams(ButtonParams);
        ((TextView) lowerChild.findViewById(R.id.TextButton)).setText(modelComponent.getButtonItem().get(1).getText());

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

        setClickListener(lowerChild, modelComponent.getButtonItem().get(1).getFunctionality(), modelComponent.getButtonItem().get(1).getUrl(), modelComponent.getButtonItem().get(1).getF_MenuID());


        com.daimajia.slider.library.SliderLayout GalleryLayout = new SliderLayout(context);
        RelativeLayout.LayoutParams GalleryLayoutParams = new RelativeLayout.LayoutParams(2 * width / 3, 2 * width / 3);
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
                    .image(Utils.URL_encode(item.getImage().toString()))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {

                        }
                    });
            setOnSliderClickListener(demoSlider, modelComponent.getGalleryItem().get(0).getFunctionality());

            GalleryLayout.addSlider(demoSlider);
            GalleryLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Top);
            GalleryLayout.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
            GalleryLayout.setCustomAnimation(new DescriptionAnimation());

        }

        GalleryLayout.setBackgroundResource(R.drawable.back);

        TextView GalleryLayoutText = new TextView(context);
        RelativeLayout.LayoutParams GalleryLayoutTextParams = new RelativeLayout.LayoutParams(2 * width / 3, RelativeLayout.LayoutParams.WRAP_CONTENT);

        GalleryLayoutTextParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, GalleryLayout.getId());
        if (Order.equals("ButtonsGallery")) {
            GalleryLayoutTextParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        } else if (Order.equals("GalleryButtons")) {
            GalleryLayoutTextParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        }
        GalleryLayoutText.setLayoutParams(GalleryLayoutTextParams);
        GalleryLayoutText.setPadding(new Utils(context).dpToPx(3), new Utils(context).dpToPx(7), new Utils(context).dpToPx(3), new Utils(context).dpToPx(7));
        GalleryLayoutText.setBackgroundResource(R.drawable.shadow_bottom);
        GalleryLayoutText.setGravity(Gravity.CENTER);
        GalleryLayoutText.setTextColor(Color.WHITE);
        GalleryLayoutText.setText("گالری تصاویر");


        GalleryButtonRowLayout.addView(UpperButtonLayout);
        GalleryButtonRowLayout.addView(LowerButtonLayout);
        GalleryButtonRowLayout.addView(GalleryLayout);
        GalleryButtonRowLayout.addView(GalleryLayoutText);

        return GalleryButtonRowLayout;
    }

    public LinearLayout ButtonsRow(int width, final ModelComponent modelComponent) {
        LinearLayout ButtonsRow = new LinearLayout(context);
        LinearLayout.LayoutParams ButtonRowLayout = new LinearLayout.LayoutParams(width, width / 3);

        ButtonsRow.setLayoutParams(ButtonRowLayout);
        //ButtonsRow.setBackgroundColor(Color.DKGRAY);
        ButtonsRow.setOrientation(LinearLayout.HORIZONTAL);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View leftChild = inflater.inflate(R.layout.item_button_row, null);
        LinearLayout.LayoutParams ButtonParams = new LinearLayout.LayoutParams(width / 3, width / 3);
        leftChild.setLayoutParams(ButtonParams);


        ((TextView) leftChild.findViewById(R.id.TextButton)).setText(modelComponent.getItem().get(0).getText());
        Picasso.with(context)
                .load(modelComponent.getItem().get(0).getImage().toString())
                .fit()
                .into((ImageView) leftChild.findViewById(R.id.ButtonImage));
        LinearLayout LeftButtonLayout = new LinearLayout(context);
        LeftButtonLayout.setLayoutParams(ButtonParams);
        LeftButtonLayout.setGravity(Gravity.CENTER);
        LeftButtonLayout.addView(leftChild);
        setClickListener(LeftButtonLayout, modelComponent.getItem().get(0).getFunctionality(), modelComponent.getItem().get(0).getUrl(), modelComponent.getItem().get(0).getF_MenuID());


        View middleChild = inflater.inflate(R.layout.item_button_row, null);
        middleChild.setLayoutParams(ButtonParams);
        TextView textButtonMiddle = middleChild.findViewById(R.id.TextButton);


        textButtonMiddle.setText(modelComponent.getItem().get(1).getText());
        Picasso.with(context)
                .load(modelComponent.getItem().get(1).getImage().toString())
                .fit()
                .into((ImageView) middleChild.findViewById(R.id.ButtonImage));
        LinearLayout MiddleButtonLayout = new LinearLayout(context);
        MiddleButtonLayout.setLayoutParams(ButtonParams);
        MiddleButtonLayout.setGravity(Gravity.CENTER);
        MiddleButtonLayout.addView(middleChild);
        setClickListener(MiddleButtonLayout, modelComponent.getItem().get(1).getFunctionality(), modelComponent.getItem().get(1).getUrl(), modelComponent.getItem().get(1).getF_MenuID());


        View rightChild = inflater.inflate(R.layout.item_button_row, null);
        rightChild.setLayoutParams(ButtonParams);
        rightChild.setId(View.generateViewId());
        ((TextView) rightChild.findViewById(R.id.TextButton)).setText(modelComponent.getItem().get(2).getText());


        Picasso.with(context)
                .load(modelComponent.getItem().get(2).getImage().toString())
                .fit()
                .into((ImageView) rightChild.findViewById(R.id.ButtonImage));
        LinearLayout RightButtonLayout = new LinearLayout(context);
        RightButtonLayout.setLayoutParams(ButtonParams);
        RightButtonLayout.setGravity(Gravity.CENTER);
        RightButtonLayout.addView(rightChild);
        setClickListener(RightButtonLayout, modelComponent.getItem().get(2).getFunctionality(), modelComponent.getItem().get(2).getUrl(), modelComponent.getItem().get(2).getF_MenuID());

        ButtonsRow.addView(LeftButtonLayout);
        ButtonsRow.addView(MiddleButtonLayout);
        ButtonsRow.addView(RightButtonLayout);


        return ButtonsRow;
    }

    public void setClickListener(View view, final String functionality, final String URL, final int ID) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (functionality) {
                    case "Gallery":
                        intent = new Intent(context, GalleryFolderActivity.class);
                        intent.putExtra("URL", URL);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        //((Activity)context).finish();
                        break;
                    case "Fanbazar":
                        intent = new Intent(context, FanBazar.class);
                        intent.putExtra("URL", URL);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        //finish();
                        break;
                    case "CompanyList":
                        intent = new Intent(context, Companies.class);
                        intent.putExtra("URL", URL);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        //finish();
                        break;
                    case "NewsList":
                        intent = new Intent(context, NewsCategory.class);
                        intent.putExtra("URL", URL);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        //finish();
                        break;
                    case "NewsDetails":
                        intent = new Intent(context, NewsDetailActivity.class);
                        intent.putExtra("URL", URL);
                        intent.putExtra("NewsID", ID);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        //((Activity) context).overridePendingTransition(0, 0);
                        //finish();
                        break;
                    case "WebView":
                        intent = new Intent(context, WebviewActivity.class);
                        intent.putExtra("URL", URL);
                        intent.putExtra("Button", "1");
                        if (ID != 0) {
                            global.clear();
                            global.add(ID);
                        }
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        //finish();
                        break;

                    default:
                        break;
                }
            }
        });

    }

    public void setOnSliderClickListener(DefaultSliderView view, final String functionality) {

        view.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Intent intent;
                switch (functionality) {
                    case "Gallery":
                        intent = new Intent(context, GalleryFolderActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        //((Activity)context).finish();
                        break;
                    case "Fanbazar":
                        intent = new Intent(context, FanBazar.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        //finish();
                        break;
                    case "CompanyList":
                        intent = new Intent(context, Companies.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        //finish();
                        break;
                    case "NewsList":
                        intent = new Intent(context, NewsCategory.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        //finish();
                        break;
                    case "NewsDetails":
                        intent = new Intent(context, NewsDetailActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        //finish();
                        break;
                    case "WebView":
                        intent = new Intent(context, WebviewActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(0, 0);
                        //finish();
                        break;

                    default:
                        break;
                }
            }
        });

    }

}
