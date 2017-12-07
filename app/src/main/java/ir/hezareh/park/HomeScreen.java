package ir.hezareh.park;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeScreen extends AppCompatActivity {
    public static final int FIRST_LEVEL_COUNT = 6;
    public static final int SECOND_LEVEL_COUNT = 4;
    public static final int THIRD_LEVEL_COUNT = 20;
    int width;
    DrawerLayout drawer;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listDataChildChild;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public static String URL_encode(String URL) {
        final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
        return Uri.encode(URL, ALLOWED_URI_CHARS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_list, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        width = Utils.getDisplayMetrics(getApplicationContext()).widthPixels;

        LinearLayout Root_Layout = (LinearLayout) findViewById(R.id.main_layout);


        ArrayList<String> my = new ArrayList<>();
        my.add(URL_encode("https://i.ytimg.com/vi/IwxBAwobISo/maxresdefault.jpg"));
        my.add(URL_encode("https://ak9.picdn.net/shutterstock/videos/12871889/thumb/1.jpg"));
        my.add(URL_encode("https://i.pinimg.com/736x/2c/d6/85/2cd6857b8ae17c36e9e6dab2c11bf02c--earth-hd-florida-georgia.jpg"));


        ArrayList<String> text = new ArrayList<>();
        text.add("about");
        text.add("contact");
        text.add("اسلایدر");


        ArrayList<String> Orders = new ArrayList<>();

        Orders.add("NewsRecycler");
        Orders.add("poll");
        Orders.add("Slider");
        Orders.add("Gallery");
        Orders.add("PieChart");
        Orders.add("Button_Group");
        Orders.add("ROW");

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        //setSupportActionBar(toolbar);

        //Root_Layout.addView(mTopToolbar);


        for (String item : Orders) {
            switch (item) {
                case "Slider":
                    Root_Layout.addView(new Component(this).Slider(width, 0, my));
                    break;
                case "Gallery":
                    Root_Layout.addView(new Component(this).GalleryButton(width, my, "GalleryButtons", text, my));
                    break;
                case "NewsRecycler":
                    Root_Layout.addView(new Component(this).News(width, 0));
                    break;
                case "Button_Group":
                    Root_Layout.addView(new Component(this).ButtonsRow(width, text, my));
                    break;
                case "ROW":
                    Root_Layout.addView(new Component(this).GalleryButton(width, my, "ButtonsGallery", text, my));
                    break;
                case "PieChart":
                    Root_Layout.addView(new MyPieChart(this, width, width / 2).getItem());
                    break;
                case "poll":
                    Root_Layout.addView(new Component(this).pollQuestion(width, 0, text, "آیا از عملکرد شهردار خود راضی هستید؟آیا از عملکرد شهردار خود راضی هستید"));
                    break;
            }
        }
        findViewById(R.id.drawer_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawerLayout) findViewById(R.id.drawer_layout)).openDrawer(GravityCompat.END);
            }
        });

        prepareListData();

        expListView = (ExpandableListView) findViewById(R.id.expanded_menu);
        expListView.setAdapter(new ParentLevel(this, listDataHeader, listDataChild));

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expanded_menu);

        // preparing list data
        prepareListData();

        listAdapter = new MenuExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listDataChildChild = new HashMap<String, List<String>>();
        // Adding child data
        listDataHeader.add("250 فیلم برتر");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("پدر خوانده");
        top250.add("پدر خوانده قسمت 2");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");


        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);

        List<String> Shawshank = new ArrayList<String>();
        Shawshank.add("Shawshank1");
        Shawshank.add("Shawshank2");
        Shawshank.add("Shawshank3");

        listDataChildChild.put(top250.get(0), Shawshank);


    }

    public class ParentLevel extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;

        public ParentLevel(Context context, List<String> listDataHeader,
                           HashMap<String, List<String>> listChildData) {

            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(getApplicationContext());
            secondLevelELV.setAdapter(new SecondLevelAdapter(_context));
            secondLevelELV.setGroupIndicator(getResources().getDrawable(R.drawable.ic_access_time_black_24dp));
            return secondLevelELV;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.menu_list_item, null);
                TextView text = (TextView) convertView.findViewById(R.id.lblListItem);
                text.setText(headerTitle);
            }
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }


        public class SecondLevelExpandableListView extends ExpandableListView {

            public SecondLevelExpandableListView(Context context) {
                super(context);
            }

            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations.
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }

        public class SecondLevelAdapter extends BaseExpandableListAdapter {

            private Context _context;
            private List<String> _listDataHeader; // header titles
            private HashMap<String, List<String>> _listDataChildChild;
            private HashMap<String, List<String>> _listDataChild;

            public SecondLevelAdapter(Context context) {
                this._context = context;
                this._listDataHeader = listDataHeader;
                this._listDataChild = listDataChild;
                this._listDataChildChild = listDataChildChild;
            }

            @Override
            public Object getGroup(int groupPosition) {
                //Log.e("TAG",String.valueOf(_listDataChildChild.get("The Shawshank Redemption").size()));
                //Log.e("TAG2",String.valueOf(_listDataChildChild.get(_listDataChild.get(_listDataHeader.get(groupPosition)).get(0))));
                //Log.e("TAG2",String.valueOf(_listDataChild.get(_listDataHeader.get(groupPosition)).get(0)));
                return this._listDataChild.get(this._listDataHeader.get(groupPosition)); //this._listDataHeader.get(groupPosition);
            }

            @Override
            public int getGroupCount() {
                Log.e("TAG33", String.valueOf(this._listDataChild.get("Now Showing").size()));
                return this._listDataChild.get("Now Showing").size();
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                String headerTitle = (String) getGroup(groupPosition);
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.menu_list_item, null);
                    TextView lblListHeader = (TextView) convertView
                            .findViewById(R.id.lblListItem);
                    lblListHeader.setTypeface(null, Typeface.BOLD);
                    lblListHeader.setText(headerTitle);
                }
                return convertView;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return this._listDataChild.get(this._listDataChildChild.get(groupPosition))
                        .get(childPosition);
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                final String childText = (String) getChild(groupPosition, childPosition);
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.menu_list_item, null);

                    TextView txtListChild = (TextView) convertView
                            .findViewById(R.id.lblListItem);

                    txtListChild.setText(childText);
                }
                return convertView;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return this._listDataChildChild.get("The Shawshank Redemption").size();
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        }
    }
}
