package ir.hezareh.park;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.DisplayMetrics;

/**
 * Created by rf on 10/11/2017.
 */

public class Utils {
    private Context context;

    public Utils(Context _context) {
        this.context = _context;
    }

    public static String URL_encode(String URL) {
        final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
        return Uri.encode(URL, ALLOWED_URI_CHARS);
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

    public boolean isConnectingToInternet() {
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

    public void showAlertDialog(String title, String message,
                                Boolean status) {
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
}
