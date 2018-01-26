package ir.hezareh.park.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ir.hezareh.park.R;
import ir.hezareh.park.Utils;
import ir.hezareh.park.models.CompanyList;


public class CompanyCategoryMenuAdapter extends BaseAdapter {

    private Context _context;
    private List<CompanyList> _listcompany;

    public CompanyCategoryMenuAdapter(Context context, List<CompanyList> listcompany) {
        this._context = context;
        this._listcompany = listcompany;
    }

    @Override
    public int getCount() {
        return _listcompany.size();
    }

    @Override
    public Object getItem(int position) {
        return _listcompany.get(position).getCompanyList();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater Inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Inflater.inflate(R.layout.menu_list_item, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.lblListItem);
        lblListHeader.setTypeface(new Utils(_context).font_set("iransans"), Typeface.NORMAL);

        lblListHeader.setText(_listcompany.get(position).getType());

        lblListHeader.setTextColor(Color.BLACK);

        return convertView;
    }
}
