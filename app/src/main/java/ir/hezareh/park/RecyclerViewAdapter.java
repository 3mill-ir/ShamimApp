package ir.hezareh.park;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;

    private ArrayList<String> stringArrayList;
    private Activity activity;

    public RecyclerViewAdapter(Activity _activity, ArrayList<String> strings) {
        this.activity = _activity;
        //this.stringArrayList = strings;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_small_item, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            //Inflating header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_small_item, parent, false);
            return new HeaderViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_footer, parent, false);
            return new FooterViewHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.headerTitle.setText("Header View");
            headerHolder.headerTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "You clicked at Header View!", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.footerText.setText("ادامه مطلب ...");
            footerHolder.footerText.setTypeface(Utils.font_set("iransans", activity));
            footerHolder.footerText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent k = new Intent(activity, Companies.class);
                    activity.startActivity(k);
                    Toast.makeText(activity, "You clicked at Footer View", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.title.setText("Recycler Item" + position);


            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(500);
            holder.itemView.startAnimation(anim);

            int height = Utils.getDisplayMetrics(activity).widthPixels;

            LinearLayout.LayoutParams ItemLayout = new LinearLayout.LayoutParams(5 * height / 10, LinearLayout.LayoutParams.WRAP_CONTENT);
            itemViewHolder.item.setLayoutParams(ItemLayout);

            RelativeLayout.LayoutParams ThumbnailLayout = new RelativeLayout.LayoutParams(5 * height / 10, 4 * height / 10);
            itemViewHolder.thumbnail.setLayoutParams(ThumbnailLayout);


            itemViewHolder.title.setText("تیتر خبر");
            itemViewHolder.title.setBackgroundColor(Color.YELLOW);

            Picasso.with(this.activity).load(HomeScreen.URL_encode("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeMlm9p4uVeGfSw-_JrUviRXqoHFPwIUhY6PUkTAiN1KtSJIPixg"))//HomeScreen.URL_encode("http://www.theappguruz.com/app/uploads/2015/12/grid-layout-manager.png"))//.placeholder(R.drawable.camera128)
                    .fit()
                    //.resize(5*height/10,5*height/10)
                    //.transform(new CropCircleTransformation())
                    .transform(new RoundedCornersTransformation(20, 0))
                    .into(itemViewHolder.thumbnail, new Callback() {
                        @Override
                        public void onSuccess() {
                            itemViewHolder.img_progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            //holder.img_progress.setVisibility(View.GONE);
                            itemViewHolder.thumbnail.setImageResource(R.drawable.corrupted);
                        }
                    });
            itemViewHolder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent k = new Intent(activity, NewsCategory.class);
                    activity.startActivity(k);


                    Toast.makeText(activity, "You clicked at item " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ITEM;/*return TYPE_HEADER;*/
        } else if (position == /*stringArrayList.size()*/ 2 + 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }


    @Override
    public int getItemCount() {
        // return stringArrayList.size() + 2;
        return 4;
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle;

        public HeaderViewHolder(View view) {
            super(view);
            headerTitle = view.findViewById(R.id.title);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        TextView footerText;
        LinearLayout footerLayout;

        public FooterViewHolder(View view) {
            super(view);
            footerText = view.findViewById(R.id.footerText);
            footerLayout = view.findViewById(R.id.footerLayout);
            cardView = view.findViewById(R.id.card_view);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public ProgressBar img_progress;
        public Typeface BYekan;
        public CardView cardView;
        public LinearLayout item;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            img_progress = itemView.findViewById(R.id.image_progressbar);
            cardView = itemView.findViewById(R.id.card_view);
            item = itemView.findViewById(R.id.list_item);
            //itemView.setOnClickListener((View.OnClickListener) activity);
            BYekan = Typeface.createFromAsset(activity.getAssets(), "fonts/BYekan.ttf");
        }
    }

}