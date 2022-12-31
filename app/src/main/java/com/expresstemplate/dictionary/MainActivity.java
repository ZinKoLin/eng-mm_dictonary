package com.expresstemplate.dictionary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int adCOUNT;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    static Typeface tf, tf_bold;
    EditText editText;
    ListView listViewData;
    String search = "";
    List<Word> item_data;
    DataManager dataManager;
    String Current, strCurrentDate, Datetime, strWord, strMeaning, CurrentTime, id, isFav;
    Context context = null;
    Image img;
    public static String key;
   // RelativeLayout relWord;

    AlertDialogManager alert = new AlertDialogManager();
    int MAINPOSITION = 0;
    RelativeLayout adLayout;
    SharedPreferences prefs;
    public static final String[] ArrayData = {"DICTIONARY", "ADD WORD", "FAVOURITE", "RECENT", "SETTING",
            "SHARE", "RATE APP"};

    public static final int[] ArrayImage = {R.drawable.translate, R.drawable.word
            , R.drawable.favorite, R.drawable.history, R.drawable.setting
            , R.drawable.sharefriend, R.drawable.rate};

    public static String myTheme;
    public static String cell;
    RelativeLayout rl_dialoguser;
    static int adCount = 0;
    static InterstitialAd ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = MainActivity.this.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        myTheme = prefs.getString("Color", "#" + Integer.toHexString(ContextCompat.getColor(this, R.color.DEFAULT_COLOR)));
        cell = prefs.getString("Color", "#" + Integer.toHexString(ContextCompat.getColor(this, R.color.DEFAULT_COLOR)));
        Log.e("theme....", "onCreate: " + myTheme + cell);

        MainActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tf = Typeface.createFromAsset(MainActivity.this.getAssets(), "fonts/Raleway-Medium.ttf");
        tf_bold = Typeface.createFromAsset(MainActivity.this.getAssets(), "fonts/Raleway-SemiBold_1.ttf");

        setActionBar(this, ArrayData[0]);
        setContentView(R.layout.activity_main);

        rl_dialoguser = findViewById(R.id.rl_infodialog);
        AdView mAdView = findViewById(R.id.adViewM);
        adLayout = findViewById(R.id.adRelay);

        if (checkInternet(MainActivity.this)) {
            if (getResources().getString(R.string.ads_visibility).equals("yes")) {
                adLayout.setVisibility(View.VISIBLE);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            } else {
                adLayout.getLayoutParams().height = 0;
            }
        } else {
            adLayout.setVisibility(View.GONE);
        }


        ListView mDrawerList = findViewById(R.id.navList);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems(mDrawerList, this, mDrawerLayout);
        setupDrawer();

        dataManager = new DataManager(MainActivity.this);

        listViewData = findViewById(R.id.listViewData);
        editText = findViewById(R.id.editText);
//        RelativeLayout relTitle = findViewById(R.id.relTitle);
//        TextView txtTitle = findViewById(R.id.txtTitle);
//        txtTitle.setTypeface(tf);

//        if (myTheme != null) {
//            txtTitle.setTextColor(Color.parseColor(myTheme));
//            relTitle.setBackgroundColor(Color.parseColor(myTheme));
//        } else {
//
//        }
//
//        item_data = new ArrayList<Word>();
//
//        SimpleDateFormat formatter = new SimpleDateFormat("dd.MMM.yyyy");//date format dd.MMM.yyyy
//        Calendar c = Calendar.getInstance();
//        Date resultdate1 = new Date(c.getTimeInMillis());
//        Current = formatter.format(resultdate1);
//        TextView textDate = findViewById(R.id.textDate);
//        textDate.setText(Current);
//
//        TextView textWord = findViewById(R.id.textWord);
//        TextView textManing = findViewById(R.id.textManing);
//
//        textWord.setTypeface(tf);
//        textManing.setTypeface(tf);
//
//        prefs = MainActivity.this.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
//        strWord = prefs.getString("Word", null);
//        strMeaning = prefs.getString("Meaning", null);
//        strCurrentDate = prefs.getString("CurrentDat", null);
//         id = prefs.getString("id", null);
//         isFav = prefs.getString("isFav", null);
//
//        relWord = findViewById(R.id.relWord);
//        relWord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent iv = new Intent(MainActivity.this, Favourite.class);
//                iv.putExtra("word", strWord);
//                iv.putExtra("meaning", strMeaning);
//                iv.putExtra("isFav", isFav);
//                iv.putExtra("id", id);
//                startActivity(iv);
//            }
//        });

//        if (strCurrentDate != null) {
//            if (strWord != null) {
//                textWord.setText(strWord);
//                textManing.setText(strMeaning);
//            }
//            if (Current.equals(strCurrentDate)) {
//                textWord.setText(strWord);
//                textManing.setText(strMeaning);
//            } else {
//                Word obj = dataManager.DayOfWord();
//                textWord.setText(obj.word);
//                textManing.setText(obj.meaning);
//                textWord.setTypeface(tf);
//                textManing.setTypeface(tf);
//
//                SharedPreferences.Editor editor = MainActivity.this.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE).edit();
//                editor.putString("id", obj.id);
//                editor.putString("isFav", obj.is_fav);
//                editor.putString("Word", obj.word);
//                editor.putString("Meaning", obj.meaning);
//                editor.putString("CurrentDat", Current);
//                editor.commit();
//            }
//        } else {
//            Word obj = dataManager.DayOfWord();
//            textWord.setText(obj.word);
//            textManing.setText(obj.meaning);
//            textWord.setTypeface(tf);
//            textManing.setTypeface(tf);
//
//            SharedPreferences.Editor editor = MainActivity.this.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE).edit();
//            editor.putString("id", obj.id);
//            editor.putString("isFav", obj.is_fav);
//            editor.putString("Word", obj.word);
//            editor.putString("Meaning", obj.meaning);
//            editor.putString("CurrentDat", Current);
//            editor.commit();
//        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString().replace("'","''");
                if (s.toString().length() > 0) {
                    item_data = dataManager.getAllSearchData(search);
                    listViewData.setAdapter(new ItemListBaseAdapter(MainActivity.this.getApplicationContext(), item_data));
                } else {
                    item_data = dataManager.getAllData();
                    listViewData.setAdapter(new ItemListBaseAdapter(MainActivity.this.getApplicationContext(), item_data));
                }
            }
        });
        key = "main";
        item_data = dataManager.getAllData();
        this.listViewData.setAdapter(new ItemListBaseAdapter(MainActivity.this.getApplicationContext(), this.item_data));
        listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                MAINPOSITION = position;

                ContinueIntent();

            }
        });
    }

    private void ContinueIntent() {
        Intent iv = new Intent(MainActivity.this, Favourite.class);
        iv.putExtra("word", item_data.get(MAINPOSITION).getWord());
        iv.putExtra("meaning", item_data.get(MAINPOSITION).getMeaning());
        iv.putExtra("isFav", item_data.get(MAINPOSITION).getIs_fav());
        iv.putExtra("id", item_data.get(MAINPOSITION).getId());
        startActivity(iv);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        rl_dialoguser.setVisibility(View.GONE);
        exitApp();

    }

    static void addDrawerItems(ListView mDrawerList, final Activity activity, final DrawerLayout mDrawerLayout) {
        LazyAdapter1 lazy1 = new LazyAdapter1(activity);
        mDrawerList.setAdapter(lazy1);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("position", "" + position);
                Intent i = new Intent();
                switch (position) {
                    case 0:
                        key = "main";
                        i.setClass(activity, MainActivity.class);
                        break;
//                    case 10:
//                        i.setClass(activity, Quiz.class);
//                        if (Quiz.resultList != null) {
//                            Quiz.resultList.clear();
//                        }
//                        break;
                    case 1:
                        i = new Intent(activity, AdWord.class);
                        i.putExtra("Insert", "NewWord");

                        break;
                    case 2: {
                        key = "fav";
                        i.setClass(activity, FavouriteList.class);
                        break;
                    }
                    case 3: {
                        key = "recent";
                        i.setClass(activity, HistoryList.class);
                        break;
                    }
                    case 4: {
                        i.setClass(activity, Setting.class);
                        break;
                    }
                    case 5:
                        // share app
                        i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        i.putExtra(Intent.EXTRA_TEXT, activity.getString(R.string.sharing_text) + "/" + "https://play.google.com/store/apps/details?id=" + activity.getPackageName());

                        break;
                    case 6:
                        //rate app

                        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName());
                        i = new Intent(Intent.ACTION_VIEW, uri);
                        break;
                    case 7:

                        //more app

                        Uri uri1 = Uri.parse("https://play.google.com/store/apps/developer?id=" + activity.getString(R.string.more_apps));
                        i = new Intent(Intent.ACTION_VIEW, uri1);
                        break;
                }

                activity.startActivity(i);
                activity.overridePendingTransition(0, 0);
                if (position < 6)
                    activity.finish();

            }
        });
    }

    static void onBackPress() {

        if (ad != null && ad.isLoaded()) {
            ad.show();
            adCount = 0;
        }
    }

    private void exitApp() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = getLayoutInflater().inflate(R.layout.dialog_exit_app, null, false);
        FloatingActionButton exit_fab = view.findViewById(R.id.exit_fab);
        TextView tx_exit = (TextView) view.findViewById(R.id.tx_exit);
        TextView tx_title = (TextView) view.findViewById(R.id.tx_title);
        TextView tx_exitapp = (TextView) view.findViewById(R.id.tx_exitapp);
        TextView tx_no = (TextView) view.findViewById(R.id.tx_no);



        tx_exit.setTypeface(tf_bold);
        tx_title.setTypeface(tf);
        tx_exitapp.setTypeface(tf_bold);
        tx_no.setTypeface(tf);
        tx_exitapp.setBackgroundColor(Color.parseColor(myTheme));
        exit_fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(myTheme)));

        tx_exitapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        tx_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.setContentView(view);
        dialog.show();

    }

    static void setInterstitial(Activity activity) {

        adCOUNT = Integer.parseInt(activity.getString(R.string.ads_counter));
        adCount++;

        if (!activity.getString(R.string.ads_visibility).equals("yes"))
            return;
        if (adCount == adCOUNT) {
            ad = new InterstitialAd(activity);
            ad.setAdUnitId(activity.getString(R.string.interstitial_id));
            ad.loadAd(new AdRequest.Builder().build());
        }
    }

    static void setActionBar(AppCompatActivity activity, String title) {
        //MainActivity.setFullScreen(activity);

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);

        androidx.appcompat.app.ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayOptions(actionBar.getDisplayOptions() | ActionBar.DISPLAY_SHOW_CUSTOM);

        FrameLayout relativeLayout = new FrameLayout(activity);

        FrameLayout.LayoutParams lprela = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int p = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, displaymetrics);
        lprela.setMargins(0, 0, p, 0);
        relativeLayout.setLayoutParams(lprela);

        TextView textview = new TextView(activity);
        FrameLayout.LayoutParams lpTextView = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        lpTextView.gravity = Gravity.START;

        textview.setTextColor(Color.WHITE);
        textview.setGravity(Gravity.CENTER);
        textview.setText(title);
        textview.setTextSize(18.0f);
        textview.setTypeface(tf);

        textview.setLayoutParams(lpTextView);

        relativeLayout.addView(textview);

        actionBar.setCustomView(relativeLayout);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(myTheme)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.parseColor(myTheme));
        }
    }

    private boolean checkInternet(MainActivity mainActivity) {
        ConnectionDetector cd = new ConnectionDetector(mainActivity);
        return cd.isConnectingToInternet();
    }

}
