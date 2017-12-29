package ir.hezareh.park;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;

/**
 * Created by rf on 03/12/2017.
 */

public class AppUpdate {
    private Context _context;

    public AppUpdate(Context context) {
        this._context = context;
    }

    public void check_Version() throws PackageManager.NameNotFoundException {
        PackageManager manager = _context.getPackageManager();
        PackageInfo info = manager.getPackageInfo(_context.getPackageName(), 0);
        final String version = info.versionName;

        final SharedPreferencesManager preferencesManager = new SharedPreferencesManager(_context);


        RequestQueue queue = Volley.newRequestQueue(_context);
        String url = "http://3mill.ir/download/appcheck?path=mobile/android/park&secretKey=3mill186";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        String availableVersion = response.replaceAll("^\"|\"$", "");
                        Log.i("Response is ", availableVersion);

                        Log.i("compare result", String.valueOf(compareVersionNames(version, availableVersion)));
                        if (compareVersionNames(version, availableVersion) == -1) {

                            Log.i("Version of Available", String.valueOf(availableVersion));
                            Log.i("Version of current App ", String.valueOf(version));

                            AlertDialog.Builder builder = new AlertDialog.Builder(_context);
                            builder.setMessage("نسخه جدید اپلیکیشن موجود است \n تمایل به بروزرسانی دارید؟")
                                    .setCancelable(false);
                            builder.setPositiveButton("آره", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(_context, DownloadService.class);
                                    intent.putExtra("url", "http://3mill.ir/download/AppSend?path=mobile/android/park&secretkey=3mill186");
                                    intent.putExtra("receiver", new DownloadReceiver(new Handler()));
                                    _context.startService(intent);
                                    preferencesManager.set_showUpdateDialog(false);
                                }
                            });
                            builder.setNegativeButton("نه", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    preferencesManager.set_showUpdateDialog(false);
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "That didn't work!");
            }
        });
        stringRequest.setShouldCache(false);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private int compareVersionNames(String oldVersionName, String newVersionName) {

        int res = 0;
        try {
            String[] oldNumbers = oldVersionName.split("\\.");
            String[] newNumbers = newVersionName.split("\\.");

            // To avoid IndexOutOfBounds
            int maxIndex = Math.min(oldNumbers.length, newNumbers.length);

            for (int i = 0; i < maxIndex; i++) {

                int oldVersionPart = Integer.valueOf(oldNumbers[i]);
                int newVersionPart = Integer.valueOf(newNumbers[i]);

                if (oldVersionPart < newVersionPart) {
                    res = -1;
                    break;
                } else if (oldVersionPart > newVersionPart) {
                    res = 1;
                    break;
                }
            }

            // If versions are the same so far, but they have different length...
            if (res == 0 && oldNumbers.length != newNumbers.length) {
                res = (oldNumbers.length > newNumbers.length) ? 1 : -1;
            }
        } catch (Exception e) {
            Log.e("error", e.toString());
        }

        return res;
    }

    //reciver for downloading apk update
    private class DownloadReceiver extends ResultReceiver {

        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == DownloadService.UPDATE_PROGRESS) {
                int progress = resultData.getInt("progress");
                //Log.i("percent",String.valueOf(progress));
                //mProgressDialog.setProgress(progress);
                if (progress == 100) {
                    //mProgressDialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/park.apk")), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    _context.startActivity(intent);

                }
            }
        }
    }

}
