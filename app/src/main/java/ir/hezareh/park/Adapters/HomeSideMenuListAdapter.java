package ir.hezareh.park.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ir.hezareh.park.R;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.sidemenu;

import static ir.hezareh.park.HomeScreen.clickedID;


public class HomeSideMenuListAdapter extends BaseAdapter {

    public static final String TAG = HomeSideMenuListAdapter.class
            .getSimpleName();
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
        TextView lblList;

        if (convertView == null) {
            LayoutInflater Inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Inflater.inflate(R.layout.menu_list_item, null);
            lblList = convertView.findViewById(R.id.lblListItem);
                /*for (int sidemenu:global) {
                    if(sidemenu==_listData.get(position).getID());
                    Log.d(TAG, "getView: ffff"+sidemenu);
                    convertView.setBackgroundColor(_context.getResources().getColor(R.color.colorPrimary));
                }*/
            lblList.setTextColor(_context.getResources().getColor(R.color.SecondaryText));

            Log.d(TAG, "getView: clicked id " + clickedID);
            Log.d(TAG, "getView: listdata " + _listData.get(position).getID());
            if (clickedID != 0) {
                if (clickedID == _listData.get(position).getID()) {
                    convertView.setBackgroundColor(_context.getResources().getColor(R.color.colorPrimaryDark));
                    lblList.setTextColor(_context.getResources().getColor(R.color.white));
                }
            }
            lblList.setText(_listData.get(position).getName());

            if (_listData.get(position).getFunctionality() == null) {
                (convertView.findViewById(R.id.chevron_left_icon)).setVisibility(View.VISIBLE);
            }
        }

        new Utils(_context).overrideFonts(convertView, "BYekan");

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }

}
