package ir.hezareh.park.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ir.hezareh.park.R;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.sidemenu;


public class HomeSideMenuListAdapter extends BaseAdapter {

    private Context _context;
    private List<sidemenu> _listData; // header titles

    public HomeSideMenuListAdapter(Context context, List<sidemenu> listData) {
        this._context = context;
        this._listData = listData;
    }


    @Override
    public int getCount() {
        return _listData.size();
    }

    @Override
    public Object getItem(int position) {
        return _listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return _listData.get(position).getID();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater Inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Inflater.inflate(R.layout.menu_list_item, null);
        }

        TextView lblList = convertView.findViewById(R.id.lblListItem);

        lblList.setText(_listData.get(position).getName());

        lblList.setTextColor(_context.getResources().getColor(R.color.SecondaryText));

        if (_listData.get(position).getFunctionality() == null) {
            (convertView.findViewById(R.id.chevron_left_icon)).setVisibility(View.VISIBLE);
        }

        new Utils(_context).overrideFonts(convertView, "BYekan");

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }

}
