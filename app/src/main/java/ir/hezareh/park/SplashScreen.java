package ir.hezareh.park;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ir.hezareh.park.Component.customAlertDialog;
import ir.hezareh.park.DataLoading.SharedPreferencesManager;
import ir.hezareh.park.DataLoading.networking;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.ModelComponent;
import ir.hezareh.park.models.sidemenu;

import static ir.hezareh.park.Util.Utils.MessageType.network_error;
import static ir.hezareh.park.Util.Utils.MessageType.server_error;
import static ir.hezareh.park.Util.Utils.MessageType.server_ok;

public class SplashScreen extends AppCompatActivity {

    public static final String MODEL_COMPONENT_KEY = "component_key";
    public static final String MODEL_SIDEMENU_KEY = "sidemenu_key";
    public static final String MESSAGE_KEY = "message_key";
    public static final String OFFLINE_MODE_KEY = "offline_mode_key";
    ScaleAnimation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferencesManager preferencesManager = new SharedPreferencesManager(getApplicationContext());


        if (preferencesManager.showSplashForOnce()) {

            ImageView parkLogo = findViewById(R.id.splash_logo);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
            alphaAnimation.setDuration(2000);
            findViewById(R.id.background_splash).setAnimation(alphaAnimation);
            alphaAnimation.start();


            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, 0,
                    Animation.RELATIVE_TO_PARENT, 1f,
                    Animation.RELATIVE_TO_PARENT, 0f);
            parkLogo.setAnimation(translateAnimation);
            translateAnimation.setDuration(1000);
            translateAnimation.setFillAfter(true);
            translateAnimation.start();


            scaleAnimation = new ScaleAnimation(
                    0f, 1.2f, // Start and end values for the X axis scaling
                    0f, 2f, // Start and end values for the Y axis scaling
                    Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                    Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
            scaleAnimation.setFillAfter(true); // Needed to keep the result of the animation
            scaleAnimation.setDuration(1000);
            scaleAnimation.setStartOffset(1000);
            findViewById(R.id.splash_text).setAnimation(scaleAnimation);
            ((TextView) findViewById(R.id.splash_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));
            scaleAnimation.start();

            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    getMainData();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            preferencesManager.setShowSplashForOnce(false);

        } else {
            startActivity(new Intent(getApplicationContext(), HomeScreen.class));
            finish();
        }
    }

    private void getMainData() {
        final ProgressBar progressBar = findViewById(R.id.progressBar);


        if (new Utils(getApplicationContext()).isConnectedToInternet()) {
            new networking(getApplicationContext()).getMainJson(new networking.MainJsonResponseListener() {
                @Override
                public void requestStarted() {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void requestCompleted(final List<ModelComponent> modelComponents) {

                    new networking(getApplicationContext()).getMainSideMenu(new networking.SideMenuResponseListener() {
                        @Override
                        public void requestStarted() {

                        }

                        //request completed for both menu and main page
                        @Override
                        public void requestCompleted(ArrayList<sidemenu> sidemenus) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(MODEL_COMPONENT_KEY, (Serializable) modelComponents);
                            bundle.putSerializable(MODEL_SIDEMENU_KEY, sidemenus);
                            bundle.putSerializable(MESSAGE_KEY, server_ok);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void requestEndedWithError(VolleyError error) {

                        }
                    });

                }

                @Override
                public void requestEndedWithError(VolleyError error) {

                    new Utils(getApplicationContext()).showToast(server_error, SplashScreen.this);

                    // hide the progress dialog
                    progressBar.setVisibility(View.INVISIBLE);
                    //server error in getting first page
                    customAlertDialog alertDialog = new customAlertDialog(SplashScreen.this, "مشکل سرور", getString(R.string.server_error_message), "حالت آفلاین", "تلاش مجدد", new customAlertDialog.yesOrNoClicked() {
                        @Override
                        public void positiveClicked() {
                            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(MESSAGE_KEY, server_error);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void negativeClicked() {
                            getMainData();
                        }
                    });
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
            });
        } else {
            customAlertDialog alertDialog = new customAlertDialog(SplashScreen.this, "اتصال شبکه", getString(R.string.network_error_message), "حالت آفلاین", "تلاش مجدد", new customAlertDialog.yesOrNoClicked() {
                @Override
                public void positiveClicked() {
                    if (new Utils(getApplicationContext()).checkCache()) {
                        Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(MESSAGE_KEY, network_error);
                        bundle.putBoolean(OFFLINE_MODE_KEY, true);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        customAlertDialog alertDialog = new customAlertDialog(SplashScreen.this, "حالت آفلاین", getString(R.string.cache_error_message), "خروج", null, new customAlertDialog.yesOrNoClicked() {
                            @Override
                            public void positiveClicked() {
                                System.exit(0);
                            }

                            @Override
                            public void negativeClicked() {
                                //null
                            }
                        });
                        alertDialog.setCancelable(false);
                        alertDialog.show();
                    }

                }

                @Override
                public void negativeClicked() {
                    getMainData();
                }
            });
            alertDialog.setCancelable(false);
            alertDialog.show();

            //new Utils(getApplicationContext()).showToast(network_error, SplashScreen.this);
        }
    }

}
