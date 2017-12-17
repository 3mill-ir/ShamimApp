package ir.hezareh.park;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class CompaniesRecycler extends RecyclerView.Adapter<CompaniesRecycler.MyViewHolder> {

    private Context mContext;
    private ArrayList<HashMap<String, String>> RequestsList;
    private int lastPosition = -1;

    public CompaniesRecycler(Context context, ArrayList<HashMap<String, String>> data) {
        //this.RequestsList = data;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_company, parent, false);

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

        int height = new Utils(mContext).getDisplayMetrics().widthPixels;

        //LinearLayout.LayoutParams ItemLayout = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //holder.item.setLayoutParams(ItemLayout);


        RelativeLayout.LayoutParams ThumbnailLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 4 * height / 10);
        //holder.thumbnail.setLayoutParams(ThumbnailLayout);


        holder.title.setText("تیتر خبر");
        //holder.title.setBackgroundColor(Color.YELLOW);
        final AtomicBoolean playAnimation = new AtomicBoolean(true);

        Picasso.with(this.mContext).load(Utils.URL_encode("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeMlm9p4uVeGfSw-_JrUviRXqoHFPwIUhY6PUkTAiN1KtSJIPixg"))//HomeScreen.URL_encode("http://www.theappguruz.com/app/uploads/2015/12/grid-layout-manager.png"))//.placeholder(R.drawable.camera128)
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


}