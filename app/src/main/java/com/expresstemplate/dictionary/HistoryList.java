package com.expresstemplate.dictionary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class HistoryList extends AppCompatActivity {

    ListView listViewData;
    List<Word> item_data;
    DataManager dataManager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    boolean interstitialCanceled;
    RelativeLayout ad, ad1;
    RelativeLayout rl_dialoguser;
    private TextView Text;
    private TextView text;
    private RelativeLayout relData;
    private TextView textR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.setActionBar(this, "RECENT SEARCH");

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        actionBar.setTitle("RECENT SEARCH");
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.drawable.ic_delete);
        androidx.appcompat.app.ActionBar.LayoutParams layoutParams = new androidx.appcompat.app.ActionBar.LayoutParams(
                androidx.appcompat.app.ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(HistoryList.this).create();
                alertDialog.setTitle("Recent Searched");
                alertDialog.setMessage("Do you want to clear the recent search ?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        item_data = dataManager.getDeleteHistoryData();
                        listViewData.setAdapter(new ItemListBaseAdapter(HistoryList.this.getApplicationContext(), item_data));

                        textR.setVisibility(View.VISIBLE);
                        relData.setVisibility(View.GONE);
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();

            }
        });

        setContentView(R.layout.activity_history_list);

        rl_dialoguser = findViewById(R.id.rl_infodialog);

        ad = findViewById(R.id.ad);
        AdView mAdView = findViewById(R.id.adView);

        if (checkInternet(HistoryList.this)) {
            if (getResources().getString(R.string.ads_visibility).equals("yes")) {
                ad.setVisibility(View.VISIBLE);

                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
                interstitialCanceled = false;


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

        dataManager = new DataManager(HistoryList.this);
        listViewData = findViewById(R.id.listViewData);
        relData = findViewById(R.id.relData);
        text = (TextView) findViewById(R.id.Text);
        textR = (TextView) findViewById(R.id.textR);

        item_data = new ArrayList<Word>();
        item_data = dataManager.getAllHistoryData();
        this.listViewData.setAdapter(new ItemListBaseAdapter(HistoryList.this.getApplicationContext(), this.item_data));

    }

    @Override
    public void onResume() {
        super.onResume();
        item_data = dataManager.getAllHistoryData();
        if (item_data.size() == 0) {

            textR.setVisibility(View.VISIBLE);
            relData.setVisibility(View.GONE);
        } else {

            textR.setVisibility(View.GONE);
            relData.setVisibility(View.VISIBLE);
            this.listViewData.setAdapter(new ItemListBaseAdapter(HistoryList.this.getApplicationContext(), this.item_data));
        }
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
        Intent intent = new Intent(HistoryList.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private boolean checkInternet(HistoryList historyList) {
        ConnectionDetector cd = new ConnectionDetector(historyList);
        return cd.isConnectingToInternet();
    }
}
