package ir.hezareh.park.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import ir.hezareh.park.FanBazar;
import ir.hezareh.park.R;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.CompanyList;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class CompaniesRecycler extends RecyclerView.Adapter<CompaniesRecycler.MyViewHolder> {

    private Context mContext;
    private List<CompanyList.CompanyInfo> _companyList;
    private int lastPosition = -1;

    public CompaniesRecycler(Context context, List<CompanyList.CompanyInfo> companyList) {
        this._companyList = companyList;
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

        holder.company_name.setText(_companyList.get(position).getName());

        holder.CEO.setText(_companyList.get(position).getCEO());

        holder.website.setText(_companyList.get(position).getWebsite());

        //holder.website.setTypeface(new Utils(mContext).font_set("BYekan"), Typeface.ITALIC);

        holder.website.setMovementMethod(new LinkMovementMethod());

        holder.website.setClickable(true);
        holder.website.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='" + _companyList.get(position).getWebsite() + "'>" + _companyList.get(position).getWebsite() + " </a>";
        holder.website.setText(Html.fromHtml(text));

        final AtomicBoolean playAnimation = new AtomicBoolean(true);

        Picasso.with(this.mContext).load(Utils.URL_encode(_companyList.get(position).getLogo()))//HomeScreen.URL_encode("http://www.theappguruz.com/app/uploads/2015/12/grid-layout-manager.png"))//.placeholder(R.drawable.camera128)
                .fit()
                //.resize(5*height/10,5*height/10)
                //.transform(new CropCircleTransformation())
                .transform(new RoundedCornersTransformation(20, 0))
                .into(holder.company_logo, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (playAnimation.get()) {
                            holder.img_progress.setVisibility(View.GONE);
                            Animation fadeOut = new AlphaAnimation(0, 1);
                            fadeOut.setInterpolator(new AccelerateInterpolator());
                            fadeOut.setDuration(1000);
                            holder.company_logo.startAnimation(fadeOut);
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
                            holder.company_logo.setImageResource(R.drawable.corrupted);
                            holder.company_logo.startAnimation(fadeOutPlaceholder);
                            playAnimation.set(false);
                        }
                    }
                });

        new Utils(mContext).overrideFonts(holder.itemView, "BYekan");
    }

    @Override
    public int getItemCount() {
        return _companyList.size();
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout item;
        private TextView company_name;
        private ImageView company_logo;
        private TextView website;
        private TextView CEO;
        private ProgressBar img_progress;

        public MyViewHolder(View view) {
            super(view);
            company_name = view.findViewById(R.id.company_name);
            company_logo = view.findViewById(R.id.company_logo);
            website = view.findViewById(R.id.website);
            CEO = view.findViewById(R.id.CEO);
            img_progress = view.findViewById(R.id.image_progressbar);
            item = view.findViewById(R.id.list_item);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Log.i("Clicked", "position: " + String.valueOf(getAdapterPosition()));
            //Toast.makeText(mContext, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, FanBazar.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }


}