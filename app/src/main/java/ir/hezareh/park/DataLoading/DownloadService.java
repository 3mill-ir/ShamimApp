package ir.hezareh.park.DataLoading;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.support.v7.app.NotificationCompat;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import ir.hezareh.park.R;

public class DownloadService extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String urlToDownload = intent.getStringExtra("url");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        try {
            //make a notification for progress download
            NotificationCompat.Builder mBuilder;

            int id = 1;
            NotificationManager
                    mNotifyManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setContentTitle("پارک علم و فناوری")
                    .setContentText("در حال دانلود بروزرسانی")
                    .setSmallIcon(R.drawable.ic_file_download_black_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_file_download_black_24dp));
            //.setAutoCancel(true);

            URL url = new URL(urlToDownload);
            URLConnection connection = url.openConnection();
            connection.connect();
            // this will be useful so that you can show a typical 0-100% progress bar
            int fileLength = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(connection.getInputStream());
            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/park.apk");

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                Bundle resultData = new Bundle();
                resultData.putInt("progress", (int) (total * 100 / fileLength));
                mBuilder.setProgress(100, (int) (total * 100 / fileLength), false);
                //Log.i("percent",String.valueOf((int) (total * 100 / fileLength)));
                // Displays the progress bar for the first time.
                mNotifyManager.notify(id, mBuilder.build());
                receiver.send(UPDATE_PROGRESS, resultData);
                output.write(data, 0, count);
            }
            mBuilder.setContentText("اتمام دانلود")
                    // Removes the progress bar
                    .setProgress(0, 0, false);
            mNotifyManager.notify(id, mBuilder.build());

            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle resultData = new Bundle();
        resultData.putInt("progress", 100);
        receiver.send(UPDATE_PROGRESS, resultData);
    }
}