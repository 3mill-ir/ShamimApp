package ir.hezareh.park.Component;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

import ir.hezareh.park.R;
import ir.hezareh.park.Util.Utils;


public class customAlertDialog extends Dialog implements android.view.View.OnClickListener {

    private Activity _activity;
    //public Dialog d;
    private String _message;
    private String _positive;
    private String _negative;
    private String _title;
    private yesOrNoClicked _yesOrNoClicked;

    public customAlertDialog(Activity activity, String title, String message, String positive, String negative, yesOrNoClicked yesOrNoClicked) {
        super(activity);
        // TODO Auto-generated constructor stub
        this._activity = activity;
        this._message = message;
        this._positive = positive;
        this._negative = negative;
        this._title = title;
        this._yesOrNoClicked = yesOrNoClicked;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_alert_dialog);


        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        Button positive = findViewById(R.id.positive);
        Button negative = findViewById(R.id.negative);

        ((TextView) findViewById(R.id.questionText)).setText(_message);
        ((TextView) findViewById(R.id.alertTitle)).setText(_title);


        positive.setText(_positive);
        negative.setText(_negative);
        if (_positive == null) {
            positive.setVisibility(View.GONE);
        }
        if (_negative == null) {
            negative.setVisibility(View.GONE);
        }

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0f, 1f, // Start and end values for the X axis scaling
                0f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        scaleAnimation.setFillAfter(true); // Needed to keep the result of the animation
        scaleAnimation.setDuration(300);
        findViewById(R.id.alertDialog_layout).setAnimation(scaleAnimation);
        scaleAnimation.start();


        new Utils(_activity).overrideFonts(findViewById(R.id.alertDialog_layout), "BHoma");

        positive.setOnClickListener(this);
        negative.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.positive:
                dismiss();
                _yesOrNoClicked.positiveClicked();
                break;
            case R.id.negative:
                dismiss();
                _yesOrNoClicked.negativeClicked();
                break;
            default:
                break;
        }
    }

    public interface yesOrNoClicked {
        void positiveClicked();

        void negativeClicked();
    }

}

