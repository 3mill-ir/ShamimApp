package ir.hezareh.park;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.hezareh.park.Adapters.HomeSideMenuListAdapter;
import ir.hezareh.park.Component.Component;
import ir.hezareh.park.Component.MyPieChart;
import ir.hezareh.park.Component.customAlertDialog;
import ir.hezareh.park.Component.progressLoading;
import ir.hezareh.park.DataLoading.AppUpdate;
import ir.hezareh.park.DataLoading.DownloadService;
import ir.hezareh.park.DataLoading.OfflineDataLoader;
import ir.hezareh.park.DataLoading.SharedPreferencesManager;
import ir.hezareh.park.Util.Utils;
import ir.hezareh.park.models.ModelComponent;
import ir.hezareh.park.models.sidemenu;

import static ir.hezareh.park.SplashScreen.MESSAGE_KEY;
import static ir.hezareh.park.SplashScreen.MODEL_SIDEMENU_KEY;
import static ir.hezareh.park.Util.Utils.MessageType.exit;
import static ir.hezareh.park.Util.Utils.MessageType.network_error;
import static ir.hezareh.park.Util.Utils.MessageType.server_error;
import static ir.hezareh.park.Util.Utils.MessageType.server_ok;

public class HomeScreen extends AppCompatActivity implements AppUpdate.AppUpdateConfirmListener {

    public static final String TAG = HomeScreen.class
            .getSimpleName();
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 99;
    public static ArrayList<Integer> global = new ArrayList<>();
    public static int pos = 0;
    public static int clickedID = 0;
    private static long back_pressed = 0L;
    private static ArrayList<sidemenu> list;
    int width;
    ListView firstLevelListView;
    ListView secondLevelListView;
    ViewGroup secondLevelListViewHeader;
    progressLoading loading;
    DrawerLayout drawer;
    HomeSideMenuListAdapter sideMenuListAdapter;
    SharedPreferencesManager preferencesManager;


    public static ArrayList<sidemenu> getList() {
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_list, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis())
                System.exit(0);//super.onBackPressed();
            else
                new Utils(getApplicationContext()).showToast(exit, HomeScreen.this);
            back_pressed = System.currentTimeMillis();
        }
    }


    public void addComponents(List<ModelComponent> modelComponents) {
        LinearLayout Root_Layout = findViewById(R.id.main_layout);

        if (modelComponents != null) {
            for (ModelComponent component : modelComponents) {

                switch (component.getComponent()) {
                    case "slider":
                        Root_Layout.addView(new Component(HomeScreen.this).Slider(width, 0, component.getItem()));
                        break;
                    case "ButtonGalleryRow":
                        Root_Layout.addView(new Component(HomeScreen.this).GalleryButton(width, component, "GalleryButtons"));
                        break;
                    case "NewsList":
                        Root_Layout.addView(new Component(HomeScreen.this).News(width, 0, component));
                        break;
                    case "RowButton":
                        Root_Layout.addView(new Component(HomeScreen.this).ButtonsRow(width, component));
                        break;
                    case "GalleryButtonRow":
                        Root_Layout.addView(new Component(HomeScreen.this).GalleryButton(width, component, "ButtonsGallery"));
                        break;
                    case "Diagram":
                        Root_Layout.addView(new MyPieChart(HomeScreen.this, width, width / 2, component).getItem());
                        break;
                    case "Poll":
                        Root_Layout.addView(new Component(HomeScreen.this).pollQuestion(width, 0, component));
                        break;
                }
            }
        }

        new Utils(getApplicationContext()).overrideFonts(Root_Layout, "BYekan");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        drawer = findViewById(R.id.drawer_layout);

        ((TextView) findViewById(R.id.side_menu_header_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));
        ((TextView) findViewById(R.id.header_text)).setTypeface(new Utils(getApplicationContext()).font_set("BYekan"));

        findViewById(R.id.drawer_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

        firstLevelListView = findViewById(R.id.first_level_menu);
        secondLevelListView = findViewById(R.id.second_level_menu);

        width = new Utils(getApplicationContext()).getDisplayMetrics().widthPixels;
        loading = new progressLoading(HomeScreen.this);
        //create a path for store offline reading later
        preferencesManager = new SharedPreferencesManager(getApplicationContext());

        new OfflineDataLoader(getApplicationContext()).createExternalStoragePath();


        if (preferencesManager.showDialogForOnce()) {
            try {
                new AppUpdate(HomeScreen.this).check_Version();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        LayoutInflater inflater = getLayoutInflater();
        secondLevelListViewHeader = (ViewGroup) inflater.inflate(R.layout.menulistheader, secondLevelListView, false);
        secondLevelListView.addHeaderView(secondLevelListViewHeader, null, false);


        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle.getSerializable(MESSAGE_KEY) == server_ok) {

                @SuppressWarnings("unchecked")
                ArrayList<ModelComponent> modelComponents =
                        (ArrayList<ModelComponent>) bundle.getSerializable(SplashScreen.MODEL_COMPONENT_KEY);
                addComponents(modelComponents);

                @SuppressWarnings("unchecked")
                ArrayList<sidemenu> sidemenus =
                        (ArrayList<sidemenu>) bundle.getSerializable(MODEL_SIDEMENU_KEY);
                if (sidemenus != null) {
                    list = new ArrayList<>(sidemenus);
                }

            } else if (bundle.getSerializable(MESSAGE_KEY) == network_error ||
                    bundle.getSerializable(MESSAGE_KEY) == server_error) {

                if (new Utils(getApplicationContext()).checkCache()) {
                    list = new ArrayList<>(new OfflineDataLoader(getApplicationContext()).ReadOfflineMainMenu());

                    //sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(list, 0, true));
                    //firstLevelListView.setAdapter(sideMenuListAdapter);

                    addComponents(new OfflineDataLoader(getApplicationContext()).ReadOfflineMainJson());
                } else {
                    customAlertDialog alertDialog = new customAlertDialog(HomeScreen.this, "حالت آفلاین", getString(R.string.cache_error_message), "خروج", null, new customAlertDialog.yesOrNoClicked() {
                        @Override
                        public void positiveClicked() {
                            System.exit(0);
                        }

                        @Override
                        public void negativeClicked() {
                            //null
                        }
                    });
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
            }

        }

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        //setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        App.getInstance().setUpdateConfirmListener(this);

        global.clear();
        pos = 0;
        clickedID = 0;
        if (global.size() > 0) {
            Log.d(TAG, "setSideMenu: " + global.get(pos));

            firstLevelListView.setVisibility(View.GONE);
            secondLevelListView.setVisibility(View.VISIBLE);

            HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(list, global.get(pos), false));
            secondLevelListView.setAdapter(sideMenuListAdapter);
        } else {
            firstLevelListView.setVisibility(View.VISIBLE);
            secondLevelListView.setVisibility(View.GONE);
            Log.d(TAG, "onResume: " + list);
            HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(list, 0, true));
            firstLevelListView.setAdapter(sideMenuListAdapter);
        }


        secondLevelListViewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saving the last position the user clicked for the time back is pressed
                if (pos == 0) {
                    firstLevelListView.setVisibility(View.VISIBLE);
                    secondLevelListView.setVisibility(View.GONE);
                    HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(list, 0, true));
                    global.remove(pos);
                    firstLevelListView.setAdapter(sideMenuListAdapter);

                } else {
                    --pos;
                    HomeSideMenuListAdapter sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(list, global.get(pos), false));
                    secondLevelListView.setAdapter(sideMenuListAdapter);
                    global.remove(pos + 1);
                }
                //Log.d("position", pos + "");

                /*for (int i = 0; i < global.size(); i++) {
                    Log.i("after remove globals" + i, global.get(i).toString());
                }*/
            }
        });
        /*if (new Utils(getApplicationContext()).isConnectedToInternet()) {
            new networking(getApplicationContext()).getMainSideMenu(new networking.SideMenuResponseListener() {
                @Override
                public void requestStarted() {

                }

                @Override
                public void requestCompleted(ArrayList<sidemenu> sidemenus) {

                    list = new ArrayList<>(sidemenus);
                    sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(list, 0, true));
                    firstLevelListView.setAdapter(sideMenuListAdapter);
                }

                @Override
                public void requestEndedWithError(VolleyError error) {
                    new Utils(getApplicationContext()).showToast(server_error, HomeScreen.this);
                }
            });
        } else {
            if (new OfflineDataLoader(getApplicationContext()).ReadOfflineMainMenu() != null) {
                list = new ArrayList<>(new OfflineDataLoader(getApplicationContext()).ReadOfflineMainMenu());
                sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(list, 0, true));
                firstLevelListView.setAdapter(sideMenuListAdapter);
            }

        }*/


        firstLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!getChildListMenuName(list, (int) id, false).isEmpty()) {
                    firstLevelListView.setVisibility(View.GONE);
                    secondLevelListView.setVisibility(View.VISIBLE);
                    global.add((int) id);

                    Log.d(TAG, "onItemClick: " + global);
                    sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(list, (int) id, false));
                    secondLevelListView.setAdapter(sideMenuListAdapter);

                    //new Component(getApplicationContext()).setClickListener(view,list.get(position).getFunctionality().toString(),list.get(position).getUrl().toString(),0);
                } else {
                    for (sidemenu sidemenu : list) {
                        if (sidemenu.getID() == (int) id) {
                            Log.d("Func", sidemenu.getFunctionality() + "");
                            Log.d("Url", sidemenu.getUrl() + "");
                            if (sidemenu.getFunctionality() != null) {
                                if (drawer.isDrawerOpen(GravityCompat.END)) {
                                    drawer.closeDrawer(GravityCompat.END);
                                }
                                Double d;
                                if (sidemenu.getFMenuID() != null) {
                                    d = Double.valueOf(sidemenu.getFMenuID().toString());
                                    setClickListener(sidemenu.getFunctionality().toString(), sidemenu.getUrl().toString(), d.intValue(), position - 1);
                                } else
                                    setClickListener(sidemenu.getFunctionality().toString(), sidemenu.getUrl().toString(), 0, position - 1);
                            }
                        }
                    }
                    clickedID = (int) id;
                }
            }
        });

        secondLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!getChildListMenuName(list, (int) id, false).isEmpty()) {
                    //pos is for storing last item ID clicked by user
                    ++pos;
                    global.add((int) id);
                    Log.d(TAG, "onItemClick: " + global);

                    sideMenuListAdapter = new HomeSideMenuListAdapter(getApplicationContext(), getChildListMenuName(list, (int) id, false));
                    secondLevelListView.setAdapter(sideMenuListAdapter);
                } else {
                    for (sidemenu sidemenu : list) {
                        if (sidemenu.getID() == (int) id) {
                            Log.d("Func", sidemenu.getFunctionality() + "");
                            Log.d("Url", sidemenu.getUrl() + "");
                            if (sidemenu.getFunctionality() != null) {
                                if (drawer.isDrawerOpen(GravityCompat.END)) {
                                    drawer.closeDrawer(GravityCompat.END);
                                }
                                Double d;
                                if (sidemenu.getFMenuID() != null) {
                                    d = Double.valueOf(sidemenu.getFMenuID().toString());
                                    setClickListener(sidemenu.getFunctionality().toString(), sidemenu.getUrl().toString(), d.intValue(), position - 1);
                                } else
                                    setClickListener(sidemenu.getFunctionality().toString(), sidemenu.getUrl().toString(), 0, position - 1);
                            }
                        }
                        if ((int) id == sidemenu.getID()) {
                            Log.d(TAG, "onItemClick: pokhh" + (int) id);
                            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        }
                    }
                    clickedID = (int) id;
                }

            }
        });
    }

    public void setClickListener(final String functionality, final String URL, final int ID, int currentItemPos) {
        Intent intent;
        switch (functionality) {
            case "Gallery":
                intent = new Intent(this, GalleryFolderActivity.class);
                intent.putExtra("URL", URL);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case "Fanbazar":
                intent = new Intent(this, FanBazar.class);
                intent.putExtra("URL", URL);
                startActivity(intent);
                overridePendingTransition(0, 0);
                //finish();
                break;
            case "CompanyList":
                intent = new Intent(this, Companies.class);
                intent.putExtra("URL", URL);
                startActivity(intent);
                overridePendingTransition(0, 0);
                //finish();
                break;
            case "NewsList":
                intent = new Intent(this, NewsCategory.class);
                intent.putExtra("URL", URL);
                intent.putExtra("ItemPos", currentItemPos);
                //intent.putExtra("FMenuID", ID);
                pos++;
                global.add(ID);
                startActivity(intent);
                overridePendingTransition(0, 0);
                //finish();
                break;
            case "NewsDetails":
                intent = new Intent(this, NewsDetailActivity.class);
                intent.putExtra("URL", URL);
                intent.putExtra("NewsID", ID);
                startActivity(intent);
                //((Activity) context).overridePendingTransition(0, 0);
                //finish();
                break;
            case "WebView":
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("URL", URL);
                //0 is for detect if is from side menu
                intent.putExtra("Button", "0");
                Bundle bundle = new Bundle();
                bundle.putSerializable(MODEL_SIDEMENU_KEY, list);
                bundle.putSerializable(MESSAGE_KEY, server_ok);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                //finish();
                break;

            default:
                break;
        }
    }


    public ArrayList<sidemenu> getChildListMenuName(ArrayList<sidemenu> list, int ID, boolean isRoot) {
        ArrayList<sidemenu> result = new ArrayList<>();

        for (sidemenu _menu : list) {

            if (_menu.getFMenuID() != null && !isRoot) {
                Double FmenuID = (Double) _menu.getFMenuID();
                if (FmenuID.intValue() == ID) {
                    result.add(_menu);
                }
            } else if (_menu.getFMenuID() == null && isRoot) {
                result.add(_menu);
            }
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {

                        AppUpdate appUpdate = new AppUpdate(this);
                        Intent intent = new Intent(this, DownloadService.class);
                        intent.putExtra("url", "http://3mill.ir/download/AppSend?path=mobile/android/Park&secretkey=3mill186");
                        intent.putExtra("receiver", appUpdate.new DownloadReceiver(new Handler()));
                        startService(intent);
                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public void onAppUpdateConfirm(boolean confirm) {
        Log.i(TAG, "onAppUpdateConfirm: " + confirm);
        if (confirm) {
            if (checkStoragePermission()) {
                AppUpdate appUpdate = new AppUpdate(this);
                Intent intent = new Intent(this, DownloadService.class);
                intent.putExtra("url", "http://3mill.ir/download/AppSend?path=mobile/android/park&secretkey=3mill186");
                intent.putExtra("receiver", appUpdate.new DownloadReceiver(new Handler()));
                startService(intent);
            }
        }
        preferencesManager.setShowDialogForOnce(false);
    }

    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }*/

    public boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                builder.setTitle(R.string.write_storage_permission_title);
                builder.setMessage(R.string.write_storage_permission_message);

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        ActivityCompat.requestPermissions(HomeScreen.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.setIcon(R.mipmap.park_logo);

                AlertDialog dialog = builder.create();
                dialog.show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(HomeScreen.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
            return false;
        } else {

            return true;
        }
    }


}
