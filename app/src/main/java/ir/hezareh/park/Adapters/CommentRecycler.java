package ir.hezareh.park.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import ir.hezareh.park.R;
import ir.hezareh.park.Utils;
import ir.hezareh.park.models.NewsDetails;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class CommentRecycler extends RecyclerView.Adapter<ir.hezareh.park.Adapters.CommentRecycler.MyViewHolder> {

    private Context mContext;
    private int lastPosition = -1;
    private List<NewsDetails.Comment> news;


    public CommentRecycler(Context context, List<NewsDetails.Comment> _news) {
        this.mContext = context;
        this.news = _news;
    }

    @Override
    public ir.hezareh.park.Adapters.CommentRecycler.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_layout, parent, false);

        return new CommentRecycler.MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final ir.hezareh.park.Adapters.CommentRecycler.MyViewHolder holder, final int position) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        holder.itemView.startAnimation(anim);

        Picasso.with(this.mContext).load(R.drawable.person)//HomeScreen.URL_encode("http://www.theappguruz.com/app/uploads/2015/12/grid-layout-manager.png"))//.placeholder(R.drawable.camera128)
                .fit()
                //.resize(5*height/10,5*height/10)
                //.transform(new CropCircleTransformation())
                .transform(new RoundedCornersTransformation(180, 0))
                .into(holder.profile_img);


        holder.title.setText(this.news.get(position).getText());
        //holder.title.setBackgroundColor(Color.YELLOW);
        holder.date.setText(this.news.get(position).getCreatedDateOnUTC());

        new Utils(mContext).overrideFonts(holder.item, "BHoma");

        final AtomicBoolean playAnimation = new AtomicBoolean(true);

    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public void onViewDetachedFromWindow(CommentRecycler.MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private LinearLayout item;
        private ImageView profile_img;
        private TextView date;

        private MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            item = view.findViewById(R.id.list_item);
            profile_img = view.findViewById(R.id.person);
            date = view.findViewById(R.id.date);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("Clicked", "position: " + String.valueOf(getAdapterPosition()));
            Toast.makeText(mContext, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }


}

