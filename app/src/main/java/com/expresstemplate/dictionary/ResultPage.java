package com.expresstemplate.dictionary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.expresstemplate.dictionary.Adapter.Adapter_Result;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ResultPage extends AppCompatActivity {

    private ListView lv_result;
    private Button btn_restart;
    private AdView mAdView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    RelativeLayout rl_dialoguser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.setActionBar(this, "QUIZ RESULT");
        setContentView(R.layout.activity_result_page);

        rl_dialoguser = findViewById(R.id.rl_infodialog);


        ListView mDrawerList = findViewById(R.id.navLisT);
        mDrawerLayout = findViewById(R.id.drawer_layouT);

        MainActivity.addDrawerItems(mDrawerList, this, mDrawerLayout);
        setupDrawer();

        init();

        AdView mAdView = findViewById(R.id.ad_);

        if (checkInternet(ResultPage.this)) {
            if (getResources().getString(R.string.ads_visibility).equals("yes")) {
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            } else {

            }
        } else {
            mAdView.setVisibility(View.GONE);
        }


        if (MainActivity.myTheme != null) {
            btn_restart.setBackgroundColor(Color.parseColor(MainActivity.myTheme));
        }
    }

    private void init() {
        lv_result = (ListView) findViewById(R.id.lv_result);
        btn_restart = (Button) findViewById(R.id.btn_restart);
        btn_restart.setTypeface(MainActivity.tf);

        setUI();

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Quiz.resultList != null) {
                    Quiz.resultList.clear();
                }
                Intent intent = new Intent(ResultPage.this, Quiz.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("QUIZ RESULT");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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


    private void setUI() {

        Adapter_Result adapter_result = new Adapter_Result(ResultPage.this, Quiz.resultList);
        lv_result.setAdapter(adapter_result);
    }

    @Override
    public void onBackPressed() {
        rl_dialoguser.setVisibility(View.GONE);

        rl_dialoguser.setVisibility(View.GONE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ResultPage.this.finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private boolean checkInternet(ResultPage resultPage) {
        ConnectionDetector cd = new ConnectionDetector(resultPage);
        return cd.isConnectingToInternet();
    }
}
