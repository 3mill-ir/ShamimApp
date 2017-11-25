package ir.hezareh.park;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;

/**
 * Created by rf on 10/11/2017.
 */

public class Utils {
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static Typeface font_set(String font, Context context) {
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
}
