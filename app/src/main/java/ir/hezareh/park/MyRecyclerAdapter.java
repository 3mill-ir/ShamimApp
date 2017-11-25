package ir.hezareh.park;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


/**
 * Created by rf on 27/08/2017.
 */


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<HashMap<String, String>> RequestsList;
    private int lastPosition = -1;

    public MyRecyclerAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        //this.RequestsList = data;
        this.mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        holder.itemView.startAnimation(anim);

        int height = Utils.getDisplayMetrics(mContext).widthPixels;

        LinearLayout.LayoutParams ItemLayout = new LinearLayout.LayoutParams(5 * height / 10, LinearLayout.LayoutParams.WRAP_CONTENT);
        holder.item.setLayoutParams(ItemLayout);


        RelativeLayout.LayoutParams ThumbnailLayout = new RelativeLayout.LayoutParams(5 * height / 10, 4 * height / 10);
        holder.thumbnail.setLayoutParams(ThumbnailLayout);


        holder.title.setText("تیتر خبر");
        holder.title.setBackgroundColor(Color.YELLOW);


        Picasso.with(this.mContext).load(HomeScreen.URL_encode("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeMlm9p4uVeGfSw-_JrUviRXqoHFPwIUhY6PUkTAiN1KtSJIPixg"))//HomeScreen.URL_encode("http://www.theappguruz.com/app/uploads/2015/12/grid-layout-manager.png"))//.placeholder(R.drawable.camera128)
                .fit()
                //.resize(5*height/10,5*height/10)
                //.transform(new CropCircleTransformation())
                .transform(new RoundedCornersTransformation(20, 0))
                .into(holder.thumbnail, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.img_progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.img_progress.setVisibility(View.GONE);
                        //holder.thumbnail.setImageResource(R.drawable.corrupted);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView thumbnail;
        public ProgressBar img_progress;
        public Typeface BYekan;
        public CardView cardView;
        public LinearLayout item;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            thumbnail = view.findViewById(R.id.thumbnail);
            img_progress = view.findViewById(R.id.image_progressbar);
            cardView = view.findViewById(R.id.card_view);
            item = view.findViewById(R.id.list_item);
            view.setOnClickListener(this);

            BYekan = Typeface.createFromAsset(mContext.getAssets(), "fonts/BYekan.ttf");
        }

        @Override
        public void onClick(View v) {
            Log.i("Clicked", "position: " + String.valueOf(getAdapterPosition()));
            Toast.makeText(mContext, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {
        Context mContext;

        public SendPostRequest(Context mContext) {

            this.mContext = mContext;
        }

        protected void onPreExecute() {
        }

        protected String doInBackground(String... URL) {

            try {

                java.net.URL url = new URL(URL[0]); // here is your URL path


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(60000 /* milliseconds */);
                conn.setConnectTimeout(60000 /* milliseconds */);
                conn.setRequestMethod("POST");

                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);


                int responseCode = conn.getResponseCode();
                Log.i("MY TAG", " " + responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    Log.i("MY TAG", "Connected!");
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));


                    StringBuffer sb = new StringBuffer("");
                    String line = "";


                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    //Log.i("MY tag:", sb.toString());
                    in.close();


                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);

                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {


            try {
                String MSG = result;
                //replacing backslash with null and removing first and end quotation marks
                JSONObject jsonObj = new JSONObject(MSG);


                String Component = jsonObj.getString("Component");

                // looping through All Contacts
                /*for (int i = 0; i < array.length(); i++) {
                    JSONObject c = array.getJSONObject(i);
                    String Key = c.getString("Key");
                    String Text = c.getString("Text");

                    // tmp hash map for single Msg
                    HashMap<String, String> Msg = new HashMap<>();

                    // adding each child node to HashMap key => value
                    Msg.put("key", Key);
                    Msg.put("text", Text);


                    Log.i("myStatus:", Msg.get("key"));


                }*/
            } catch (final JSONException e) {
                Log.e("JJ", "Json parsing error: " + e.getMessage());
                // runOnUiThread(new Runnable() {
                // @Override
                //  public void run() {
                //Toast.makeText(getApplicationContext(),
                //        "Json parsing error: " + e.getMessage(),
                //      Toast.LENGTH_LONG).show();
            }
            // });
        }
    }



}