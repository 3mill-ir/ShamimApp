package ir.hezareh.park.Util;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import ir.hezareh.park.DataLoading.OfflineDataLoader;
import ir.hezareh.park.R;


public class Utils {
    private Context context;


    public Utils(Context _context) {
        this.context = _context;
    }

    public static String URL_encode(String URL) {
        final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
        return Uri.encode(URL, ALLOWED_URI_CHARS);
    }

    public static void expand(final View v, int targetHeight) {
        int prevHeight = v.getHeight();
        Log.d("height", prevHeight + "");
        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());

        int duration = (int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density);
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int targetHeight) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        int duration = (int) (prevHeight / v.getContext().getResources().getDisplayMetrics().density);
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void buttonEffect(View button) {
        button.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

    public int dpToPx(int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public DisplayMetrics getDisplayMetrics() {
        return context.getResources().getDisplayMetrics();
    }

    public Typeface font_set(String font) {
        switch (font) {
            case "BYekan":
                return Typeface.createFromAsset(context.getAssets(), "fonts/BYekan.ttf");
            case "BHoma":
                return Typeface.createFromAsset(context.getAssets(), "fonts/BHoma.ttf");
            case "iransans":
                return Typeface.createFromAsset(context.getAssets(), "fonts/irsans.ttf");
            default:
                return Typeface.createFromAsset(context.getAssets(), "fonts/irsans.ttf");
        }
    }

    public void overrideFonts(final View v, String font) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(child, font);
                }
            } else if (v instanceof TextView) {
                if (font.equals("BYekan")) {
                    ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/BYekan.ttf"));

                } else if (font.equals("BHoma")) {
                    ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/BHoma.ttf"));
                } else if (font.equals("iransans")) {
                    ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/irsans.ttf"));
                }
            }
        } catch (Exception e) {
        }
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public void animateText(TextView textView, int screenWidth) {
        //this function animates Text if does not fit on the Screen

        Paint textPaint = textView.getPaint();
        String text = textView.getText().toString();//get text
        int textWidth = Math.round(textPaint.measureText(text));//measure the text size
        ViewGroup.LayoutParams params = textView.getLayoutParams();
        params.width = textWidth;

        textView.setLayoutParams(params); //refine

        //this is optional. do not scroll if text is shorter than screen width
        //remove this won't effect the scroll

        if (textWidth <= screenWidth) {
            //All text can fit in screen.
            //return;
        } else {
            //set the animation

            TranslateAnimation slide = new TranslateAnimation(-textWidth, screenWidth, 0, 0);
            slide.setDuration(textWidth * 5 + screenWidth);
            slide.setRepeatCount(Animation.INFINITE);
            slide.setRepeatMode(Animation.RESTART);
            slide.setInterpolator(new LinearInterpolator());
            textView.startAnimation(slide);
        }
    }

    public void showToast(MessageType messageType, Activity activity) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null) {
            View layout = inflater.inflate(R.layout.custom_toast_layout,
                    (ViewGroup) activity.findViewById(R.id.toast_layout_root));

            TextView text = layout.findViewById(R.id.text);
            Drawable d;
            switch (messageType) {
                case network_error:
                    d = activity.getResources().getDrawable(R.drawable.ic_signal_wifi_off_black_24dp);
                    text.setText("تنظیمات اینترنت را بررسی نمائید");
                    break;

                case server_error:
                    d = activity.getResources().getDrawable(R.drawable.ic_error_outline_black_24dp);
                    text.setText("سرور در دسترس نیست، بعداً تلاش کنید");
                    break;

                case confirmation:
                    d = activity.getResources().getDrawable(R.drawable.ic_done_black_24dp);
                    text.setText("نظر شما ثبت گردید");
                    break;

                case duplicate_entry:
                    d = activity.getResources().getDrawable(R.drawable.ic_error_outline_black_24dp);
                    text.setText("شما قبلا در نظرسنجی شرکت کرده اید");
                    break;

                case exit:
                    d = activity.getResources().getDrawable(R.drawable.ic_exit_to_app_black_24dp);
                    text.setText("برای خروج از برنامه دوباره فشار دهید");
                    break;
                default:
                    d = activity.getResources().getDrawable(R.drawable.ic_signal_wifi_off_black_24dp);
            }

            text.setCompoundDrawablePadding(dpToPx(5));
            text.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);


            text.setTypeface(font_set("BYekan"));
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
    }

    public void showAlertDialog(String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if (status != null)
            // Setting alert dialog icon
            alertDialog.setIcon((status) ? R.drawable.ic_check_black_24dp : R.drawable.ic_clear_black_24dp);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                0.3f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(500);
        v.startAnimation(anim);
    }

    public boolean checkCache() {
        return new OfflineDataLoader(context).ReadOfflineMainMenu() != null &&
                new OfflineDataLoader(context).ReadOfflineMainJson() != null;
    }

    public enum MessageType {
        network_error,
        server_error,
        server_ok,
        confirmation,
        duplicate_entry,
        exit
    }


}
