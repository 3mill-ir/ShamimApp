package ir.hezareh.park.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ir.hezareh.park.R;
import ir.hezareh.park.Utils;
import ir.hezareh.park.models.GalleryModel;


public class GalleryFolderAdapter extends BaseAdapter {
    Context context;
    List<GalleryModel> galleryList;

    public GalleryFolderAdapter(Context _context, List<GalleryModel> GalleryList) {
        this.context = _context;
        this.galleryList = GalleryList;
    }

    @Override
    public int getCount() {
        return galleryList.size();
    }

    @Override
    public Object getItem(int position) {
        return galleryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater Inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Inflater.inflate(R.layout.item_folder_gallery, null);

        }
        TextView folderName = convertView.findViewById(R.id.folder_name);
        TextView imagesCount = convertView.findViewById(R.id.images_count);

        folderName.setText(galleryList.get(position).getFolderName());
        folderName.setTextColor(Color.WHITE);

        imagesCount.setText(String.valueOf(galleryList.get(position).getImagesCount()));
        imagesCount.setTextColor(Color.WHITE);

        new Utils(context).overrideFonts(convertView, "BYekan");

        return convertView;
    }


}
