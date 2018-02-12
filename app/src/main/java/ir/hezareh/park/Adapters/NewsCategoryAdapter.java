package ir.hezareh.park.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
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

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import ir.hezareh.park.NewsDetailActivity;
import ir.hezareh.park.R;
import ir.hezareh.park.Utils;
import ir.hezareh.park.models.ModelComponent;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class NewsCategoryAdapter extends RecyclerView.Adapter<NewsCategoryAdapter.MyViewHolder> {

    private Context mContext;
    private int lastPosition = -1;
    private List<ModelComponent> news;
    private int index;


    public NewsCategoryAdapter(Context context, List<ModelComponent> _news, int _index) {
        this.mContext = context;
        this.news = _news;
        this.index = _index;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_large_item, parent, false);

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

        int widthPixels = new Utils(mContext).getDisplayMetrics().widthPixels;

        LinearLayout.LayoutParams ItemLayout = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        holder.item.setLayoutParams(ItemLayout);

        holder.date.setText(this.news.get(index).getItem().get(position).getDate().toString());

        holder.likes.setText(this.news.get(index).getItem().get(position).getLikes());

        holder.dislikes.setText(this.news.get(index).getItem().get(position).getDislikes());

        holder.comments.setText(this.news.get(index).getItem().get(position).getComment());

        //RelativeLayout.LayoutParams ThumbnailLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 4 * widthPixels / 10);
        //holder.thumbnail.setLayoutParams(ThumbnailLayout);


        holder.title.setText(this.news.get(index).getItem().get(position).getText());

        holder.content.setText(this.news.get(index).getItem().get(position).getContent().toString());

        final AtomicBoolean playAnimation = new AtomicBoolean(true);

        Picasso.with(this.mContext).load(Utils.URL_encode(news.get(index).getItem().get(position).getImage().toString()))//HomeScreen.URL_encode("http://www.theappguruz.com/app/uploads/2015/12/grid-layout-manager.png"))//.placeholder(R.drawable.camera128)
                .fit()
                //.resize(5*height/10,5*height/10)
                //.transform(new CropCircleTransformation())
                .transform(new RoundedCornersTransformation(20, 0))
                .into(holder.thumbnail, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (playAnimation.get()) {
                            holder.img_progress.setVisibility(View.GONE);
                            Animation fadeOut = new AlphaAnimation(0, 1);
                            fadeOut.setInterpolator(new AccelerateInterpolator());
                            fadeOut.setDuration(1000);
                            holder.thumbnail.startAnimation(fadeOut);
                            playAnimation.set(false);
                        }
                        /*Animation fadeOutPlaceholder = new AlphaAnimation(1, 0);
                        fadeOutPlaceholder.setInterpolator(new AccelerateInterpolator());
                        fadeOutPlaceholder.setDuration(1000);
                        placeHolderImageView.startAnimation(fadeOutPlaceholder);*/
                    }

                    @Override
                    public void onError() {
                        if (playAnimation.get()) {
                            holder.img_progress.setVisibility(View.GONE);
                            Animation fadeOutPlaceholder = new AlphaAnimation(1, 0);
                            fadeOutPlaceholder.setInterpolator(new AccelerateInterpolator());
                            fadeOutPlaceholder.setDuration(1000);
                            holder.thumbnail.setImageResource(R.drawable.corrupted);
                            holder.thumbnail.startAnimation(fadeOutPlaceholder);
                            playAnimation.set(false);
                        }
                    }

                });


    }

    @Override
    public int getItemCount() {
        return news.get(index).getItem().size();
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView content;
        private ImageView thumbnail;
        private ProgressBar img_progress;
        private LinearLayout item;
        private TextView date;
        private TextView likes;
        private TextView dislikes;
        private TextView comments;

        private MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            thumbnail = view.findViewById(R.id.thumbnail);
            img_progress = view.findViewById(R.id.image_progressbar);
            item = view.findViewById(R.id.list_item);
            date = view.findViewById(R.id.date);
            likes = view.findViewById(R.id.likes);
            dislikes = view.findViewById(R.id.dislikes);
            comments = view.findViewById(R.id.comments);
            content = view.findViewById(R.id.content);

            new Utils(mContext).overrideFonts(item, "BYekan");
            new Utils(mContext).overrideFonts(content, "iransans");


            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("Clicked", "position: " + String.valueOf(getAdapterPosition()));
            Toast.makeText(mContext, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, NewsDetailActivity.class);
            intent.putExtra("URL", news.get(index).getItem().get(getAdapterPosition()).getUrl());
            mContext.startActivity(intent);
        }
    }


}