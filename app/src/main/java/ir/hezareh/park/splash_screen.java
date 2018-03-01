package ir.hezareh.park;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import ir.hezareh.park.Util.Utils;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(4000);
        findViewById(R.id.background_splash).setAnimation(alphaAnimation);
        alphaAnimation.start();


        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0,
                Animation.RELATIVE_TO_PARENT, 1f,
                Animation.RELATIVE_TO_PARENT, 0f);
        findViewById(R.id.splash_logo).setAnimation(translateAnimation);
        translateAnimation.setDuration(2000);
        translateAnimation.start();


        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0f, 1.2f, // Start and end values for the X axis scaling
                1f, 2f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        scaleAnimation.setFillAfter(true); // Needed to keep the result of the animation
        scaleAnimation.setDuration(2000);
        scaleAnimation.setStartOffset(2000);
        findViewById(R.id.splash_text).setAnimation(scaleAnimation);
        scaleAnimation.start();
        ((TextView) findViewById(R.id.splash_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));


        new Timer().schedule(new TimerTask() {
            public void run() {
                startActivity(new Intent(splash_screen.this, HomeScreen.class));
                finish();
            }
        }, 4000);//4000

    }
}
