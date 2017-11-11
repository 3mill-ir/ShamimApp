package ir.hezareh.park;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by rf on 27/08/2017.
 */


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<HashMap<String, String>> RequestsList;
    private int lastPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public  ImageView thumbnail;
        public  ProgressBar img_progress;
        public Typeface BYekan;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            img_progress=(ProgressBar)view.findViewById(R.id.image_progressbar);
            BYekan=Typeface.createFromAsset(mContext.getAssets(), "fonts/BYekan.ttf");
        }
    }


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
        HashMap<String, String> Request;

        //Log.i("my tag", " " + position);
        //setAnimation(holder.itemView, position);

        //Animation animation= AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? android.R.anim.slide_in_left : android.R.anim.slide_out_right);
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);

        holder.itemView.startAnimation(anim);


        //Request = RequestsList.get(position);
        holder.title.setText("تیتر خبر");


        // check for states

        Picasso.with(this.mContext).load("https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg?w=940&h=650&auto=compress&cs=tinysrgb")//.placeholder(R.drawable.camera128)
                //.fit()
                .resize(4*Utils.getDisplayMetrics(mContext).widthPixels/10,6*Utils.getDisplayMetrics(mContext).widthPixels/10)
                //.transform(new CropCircleTransformation())
                //.transform(new RoundedCornersTransformation(40,0))
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
                });/*new Target() {

                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        holder.image.setImageBitmap(bitmap);

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        holder.img_progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });*/
        //lastPosition = position;

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


}