package com.expresstemplate.dictionary;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Calendar;

public class Setting extends AppCompatActivity {

    Switch mySwitch;
    int newMinute = 0;
    Calendar mcurrentTime;
    String Current, strCurrentDate, strNotification, Datetime, strWord,
            strMeaning, CurrentTime, strNotificationTime, myTheme;
    Button btnOrange, btnRed, btnGreen, btnDarkBLue, btnDarkRed, btnCoffie, btnLightBlue, btnBlue;
    TextView txtDescriptionOFF, txtDescriptionON;
    Typeface tf;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    InterstitialAd mInterstitialAd;
    boolean interstitialCanceled;
    AlertDialogManager alert = new AlertDialogManager();
    private ConnectionDetector cd;
    RelativeLayout ad;
    View layout12;
    RelativeLayout rl_dialoguser;
    public static String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = Setting.this.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        myTheme = prefs.getString("Color", null);

        if (myTheme != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor(myTheme));
            }
        }

        tf = Typeface.createFromAsset(Setting.this.getAssets(), "fonts/Raleway-Medium.ttf");

        if (myTheme != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayOptions(actionBar.getDisplayOptions() | ActionBar.DISPLAY_SHOW_CUSTOM);

            final RelativeLayout relativeLayout = new RelativeLayout(this);

            RelativeLayout.LayoutParams lprela = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            relativeLayout.setLayoutParams(lprela);

            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams lpImage = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            lpImage.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, Gravity.RIGHT
                    | Gravity.CENTER_VERTICAL);

            image.setImageResource(R.drawable.logo);
            image.setVisibility(View.GONE);
            image.setPadding(0, 13, 1, 13);
            //Setting the parameters on the Image
            image.setLayoutParams(lpImage);

            //adding imageview to relative layout
            relativeLayout.addView(image);

            TextView textview = new TextView(this);
            RelativeLayout.LayoutParams lpTextView = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lpTextView.addRule(RelativeLayout.CENTER_VERTICAL, image.getId());

            textview.setTextColor(Color.WHITE);
            textview.setGravity(Gravity.START);
            textview.setText("SETTING");
            textview.setTextSize(18.0f);
            textview.setTypeface(tf);
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int width = displaymetrics.widthPixels;
            textview.setPadding(0, 0, 76, 0);
            textview.setWidth(width);

            textview.setLayoutParams(lpTextView);
            relativeLayout.addView(textview);
            actionBar.setCustomView(relativeLayout);

            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(myTheme)));

        } else {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayOptions(actionBar.getDisplayOptions() | ActionBar.DISPLAY_SHOW_CUSTOM);

            final RelativeLayout relativeLayout = new RelativeLayout(this);

            RelativeLayout.LayoutParams lprela = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            relativeLayout.setLayoutParams(lprela);

            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams lpImage = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            lpImage.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, Gravity.RIGHT
                    | Gravity.CENTER_VERTICAL);

            image.setImageResource(R.drawable.logo);
            image.setVisibility(View.GONE);
            image.setPadding(0, 13, 1, 13);
            //Setting the parameters on the Image
            image.setLayoutParams(lpImage);

            //adding imageview to relative layout
            relativeLayout.addView(image);

            TextView textview = new TextView(this);
            RelativeLayout.LayoutParams lpTextView = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lpTextView.addRule(RelativeLayout.CENTER_VERTICAL, image.getId());

            textview.setTextColor(Color.WHITE);
            textview.setGravity(Gravity.CENTER);
            textview.setText("SETTING");
            textview.setTextSize(18.0f);
            textview.setTypeface(tf);
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int width = displaymetrics.widthPixels;
            textview.setPadding(0, 0, 76, 0);
            textview.setWidth(width);

            textview.setLayoutParams(lpTextView);
            relativeLayout.addView(textview);
            actionBar.setCustomView(relativeLayout);

            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4E93C6")));

        }

        setContentView(R.layout.activity_setting);

        rl_dialoguser = findViewById(R.id.rl_infodialog);

        ad = findViewById(R.id.ad);
        AdView mAdView = findViewById(R.id.adView);

        if (checkInternet(Setting.this)) {
            if (getResources().getString(R.string.ads_visibility).equals("yes")) {
                ad.setVisibility(View.VISIBLE);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            } else {
                ad.getLayoutParams().height = 0;
            }
        } else {
            ad.setVisibility(View.GONE);
        }


        ListView mDrawerList = findViewById(R.id.navList);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        String mActivityTitle = getTitle().toString();

        mDrawerList = findViewById(R.id.navList);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        MainActivity.addDrawerItems(mDrawerList, this, mDrawerLayout);
        setupDrawer();

        RelativeLayout relTopLine = findViewById(R.id.relTopLine);
        RelativeLayout relTopLineTheme = findViewById(R.id.relTopLineTheme);
        TextView txtHeader = findViewById(R.id.txtHeader);
        TextView txtThemeHeader = findViewById(R.id.txtThemeHeader);
        if (myTheme != null) {
            txtHeader.setTextColor(Color.parseColor(myTheme));
            txtThemeHeader.setTextColor(Color.parseColor(myTheme));
            relTopLine.setBackgroundColor(Color.parseColor(myTheme));
            relTopLineTheme.setBackgroundColor(Color.parseColor(myTheme));
        } else {

        }

        txtDescriptionOFF = findViewById(R.id.txtDescriptionOFF);
        txtDescriptionON = findViewById(R.id.txtDescriptionON);
        mySwitch = findViewById(R.id.mySwitch);
        mySwitch.setChecked(false);
        prefs = Setting.this.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        strWord = prefs.getString("Word", null);
        strMeaning = prefs.getString("Meaning", null);
        strCurrentDate = prefs.getString("CurrentDat", null);
        mySwitch.setChecked(prefs.getBoolean("NameOfThingToSave", false));

        if (mySwitch.isChecked() == false) {
            txtDescriptionOFF.setVisibility(View.VISIBLE);
            txtDescriptionON.setVisibility(View.GONE);
        } else {
            txtDescriptionON.setVisibility(View.VISIBLE);
            txtDescriptionOFF.setVisibility(View.GONE);
        }

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(Setting.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String strTime = String.valueOf(selectedMinute);
                            if (strTime.length() <= 1) {
                                strTime = "0" + strTime;
                            } else {
                                strTime = strTime;
                            }

                            mcurrentTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                            mcurrentTime.set(Calendar.MINUTE, selectedMinute);


                            txtDescriptionON.setVisibility(View.VISIBLE);
                            txtDescriptionOFF.setVisibility(View.GONE);

                            Intent myIntent = new Intent(Setting.this, MyReceiver.class);
                            myIntent.putExtra("Word", strWord);
                            myIntent.putExtra("Meaning", strMeaning);

                            PendingIntent pendingIntent;
                           // pendingIntent =  PendingIntent.getBroadcast(Setting.this, 0, myIntent, 0);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                pendingIntent = PendingIntent.getBroadcast(Setting.this,
                                        0, myIntent,  PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                            }else {
                                pendingIntent = PendingIntent.getBroadcast(Setting.this,
                                        0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            }


                            AlarmManager alarmManager = (AlarmManager) Setting.this.getSystemService(Setting.this.ALARM_SERVICE);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, mcurrentTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                            SharedPreferences.Editor editor = Setting.this.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE).edit();
                            editor.putBoolean("NameOfThingToSave", true);
                            editor.commit();
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();

                } else {
                    SharedPreferences.Editor editor = Setting.this.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("NameOfThingToSave", false);
                    editor.commit();
                    txtDescriptionOFF.setVisibility(View.VISIBLE);
                    txtDescriptionON.setVisibility(View.GONE);
                }

            }
        });

        if (mySwitch.isChecked()) {

        } else {

        }

        btnOrange = findViewById(R.id.btnOrange);
        btnRed = findViewById(R.id.btnRed);
        btnGreen = findViewById(R.id.btnGreen);
        btnDarkBLue = findViewById(R.id.btnDarkBLue);
        btnDarkRed = findViewById(R.id.btnDarkRed);
        btnCoffie = findViewById(R.id.btnCoffie);
        btnLightBlue = findViewById(R.id.btnLightBlue);
        btnBlue = findViewById(R.id.btnBlue);

        btnOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                changeTheme("#E26103");
                key = "#E26103";
                changeCell(key);
            }
        });

        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                changeTheme("#DE5145");
                key = "#DE5145";
            }
        });

        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme("#0db846");
                key = "#0db846";
                changeCell(key);
            }
        });

        btnDarkBLue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme("#2b0979");
                key = "#2b0979";
                changeCell(key);
            }
        });


        btnCoffie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme("#795b09");
                key = "#795b09";
            }
        });
        btnDarkRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme("#780979");
                key = "#780979";
                changeCell(key);
            }
        });
        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme("#015AE6");
                key = "#015AE6";
                changeCell(key);
            }
        });
        btnLightBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme("#096d79");
                key = "#096d79";
                changeCell(key);
            }
        });

    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        rl_dialoguser.setVisibility(View.GONE);
        Intent intent = new Intent(Setting.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    void changeTheme(String color) {
        MainActivity.myTheme = color;
        SharedPreferences.Editor editor = Setting.this.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE).edit();
        editor.putString("Color", color);
        editor.commit();
        Intent intent = new Intent(Setting.this, Setting.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    void changeCell(String color) {
        MainActivity.cell = color;
        SharedPreferences.Editor editor = Setting.this.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE).edit();
        editor.putString("key", color);
        key = color;
        editor.commit();

    }

    private boolean checkInternet(Setting setting) {
        ConnectionDetector cd = new ConnectionDetector(setting);
        return cd.isConnectingToInternet();
    }

}
