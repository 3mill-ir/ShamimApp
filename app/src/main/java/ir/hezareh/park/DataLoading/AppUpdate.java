package ir.hezareh.park.DataLoading;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.android.volley.VolleyError;

import java.io.File;

import ir.hezareh.park.BuildConfig;
import ir.hezareh.park.Component.customAlertDialog;
import ir.hezareh.park.R;


public class AppUpdate {
    public static AppUpdateConfirmListener appUpdateConfirmListener;
    private Activity activity;

    public AppUpdate(Activity activity) {
        this.activity = activity;
    }

    public void check_Version() throws PackageManager.NameNotFoundException {
        PackageManager manager = activity.getPackageManager();
        PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
        final String version = info.versionName;

        final SharedPreferencesManager preferencesManager = new SharedPreferencesManager(activity);


        new networking(activity).updateCheck(new networking.UpdateCheckResponseListener() {
            @Override
            public void requestStarted() {

            }

            @Override
            public void requestCompleted(String availableVersion) {

                Log.i("compare result", String.valueOf(compareVersionNames(version, availableVersion)));
                if (compareVersionNames(version, availableVersion) == -1) {

                    customAlertDialog alertDialog = new customAlertDialog(activity, "بروزرسانی", activity.getString(R.string.update_message), "آره", "نه", new customAlertDialog.yesOrNoClicked() {
                        @Override
                        public void positiveClicked() {
                            if (appUpdateConfirmListener != null)
                                appUpdateConfirmListener.onAppUpdateConfirm(true);
                        }

                        @Override
                        public void negativeClicked() {
                            if (appUpdateConfirmListener != null)
                                appUpdateConfirmListener.onAppUpdateConfirm(false);
                        }
                    });
                    alertDialog.show();
                    Log.i("Version of Available", String.valueOf(availableVersion));
                    Log.i("Version of current App ", String.valueOf(version));
                }
            }

            @Override
            public void requestEndedWithError(VolleyError error) {

            }
        });

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

    public interface AppUpdateConfirmListener {
        void onAppUpdateConfirm(boolean confirm);
    }

    //receiver for downloading apk update
    public class DownloadReceiver extends ResultReceiver {

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
                    /*Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/park.apk")), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);*/


                    File toInstall = new File(Environment.getExternalStorageDirectory().getPath(), "park" + ".apk");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri apkUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", toInstall);
                        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                        intent.setData(apkUri);
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        activity.startActivity(intent);
                    } else {
                        Uri apkUri = Uri.fromFile(toInstall);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                    }


                }
            }
        }
    }

}
