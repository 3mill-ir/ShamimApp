package ir.hezareh.park.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import ir.hezareh.park.GalleryImagesActivity;
import ir.hezareh.park.R;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.GalleryModel;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class GalleryFolderAdapter extends RecyclerView.Adapter<GalleryFolderAdapter.MyViewHolder> {
    public static final String FOLDER_KEY = "folderName";
    private Context mContext;
    private List<GalleryModel> folderGalleryList;
    private int lastPosition = -1;

    public GalleryFolderAdapter(Context _context, List<GalleryModel> GalleryFoldersList) {
        this.mContext = _context;
        this.folderGalleryList = GalleryFoldersList;
    }

    @Override
    public GalleryFolderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_folder_gallery, parent, false);

        return new GalleryFolderAdapter.MyViewHolder(itemView);
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final GalleryFolderAdapter.MyViewHolder holder, final int position) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        holder.itemView.startAnimation(anim);

        //holder.website.setTypeface(new Utils(mContext).font_set("BYekan"), Typeface.ITALIC);


        final AtomicBoolean playAnimation = new AtomicBoolean(true);

        Picasso.with(this.mContext).load(R.drawable.pictures)//HomeScreen.URL_encode("http://www.theappguruz.com/app/uploads/2015/12/grid-layout-manager.png"))//.placeholder(R.drawable.camera128)
                //.fit()
                //.resize(5*height/10,5*height/10)
                //.transform(new CropCircleTransformation())
                .transform(new RoundedCornersTransformation(20, 0))
                .into(holder.folderIcon, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (playAnimation.get()) {
                            //holder.img_progress.setVisibility(View.GONE);
                            Animation fadeOut = new AlphaAnimation(0, 1);
                            fadeOut.setInterpolator(new AccelerateInterpolator());
                            fadeOut.setDuration(1000);
                            holder.folderIcon.startAnimation(fadeOut);
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
                            //holder.img_progress.setVisibility(View.GONE);
                            Animation fadeOutPlaceholder = new AlphaAnimation(1, 0);
                            fadeOutPlaceholder.setInterpolator(new AccelerateInterpolator());
                            fadeOutPlaceholder.setDuration(1000);
                            holder.folderIcon.setImageResource(R.drawable.corrupted);
                            holder.folderIcon.startAnimation(fadeOutPlaceholder);
                            playAnimation.set(false);
                        }
                    }
                });

        holder.folderName.setText(folderGalleryList.get(position).getFolderName());
        holder.imagesCount.setText(String.valueOf(folderGalleryList.get(position).getImagesCount()));

    }

    @Override
    public int getItemCount() {
        return folderGalleryList.size();
    }

    @Override
    public void onViewDetachedFromWindow(GalleryFolderAdapter.MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout item;
        //private ProgressBar img_progress;
        TextView folderName;
        TextView imagesCount;
        ImageView folderIcon;

        private MyViewHolder(View view) {
            super(view);
            item = view.findViewById(R.id.folder_gallery);
            folderName = view.findViewById(R.id.folder_name);
            imagesCount = view.findViewById(R.id.images_count);
            folderIcon = view.findViewById(R.id.folder_icon);
            //img_progress = view.findViewById(R.id.image_progressbar);

            folderName.setTextColor(Color.BLACK);
            imagesCount.setTextColor(Color.BLACK);
            view.setOnClickListener(this);

            new Utils(mContext).overrideFonts(item, "BYekan");

        }

        @Override
        public void onClick(View v) {
            Log.i("Clicked", "position: " + String.valueOf(getAdapterPosition()));
            //Toast.makeText(mContext, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, GalleryImagesActivity.class);
            intent.putExtra(FOLDER_KEY, folderGalleryList.get(getAdapterPosition()).getFolderName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }
}




