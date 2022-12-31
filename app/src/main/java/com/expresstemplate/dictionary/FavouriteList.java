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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class FavouriteList extends AppCompatActivity {

    ListView listViewData;
    List<Word> item_data;
    DataManager dataManager;
    boolean isFavouriteList = false;
    Button btnClear;
    RelativeLayout relList, relText;
    int MAINPOSITION = 0;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    RelativeLayout ad, ad1;
    RelativeLayout rl_dialoguser;
    private TextView textF;
    RelativeLayout relData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.setActionBar(this, "FAVOURITE");

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        final ImageView imageView = new ImageView(actionBar.getThemedContext());

        actionBar.setTitle("FAVOURITE");
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

                AlertDialog alertDialog = new AlertDialog.Builder(FavouriteList.this).create();
                alertDialog.setTitle("Favorite");
                alertDialog.setMessage("Do you want to clear favourite list ?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        item_data = dataManager.getDeleteFavouriteData();
                        listViewData.setAdapter(new ItemListBaseAdapter(FavouriteList.this.getApplicationContext(), item_data));

                        textF.setVisibility(View.VISIBLE);
                        relData.setVisibility(View.GONE);

                        if (item_data.size() == 0) {
                            imageView.setClickable(false);
                        }
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

        setContentView(R.layout.activity_favourite_list);

        rl_dialoguser = findViewById(R.id.rl_infodialog);

        ad = findViewById(R.id.ad);
        ad1 = findViewById(R.id.ad1);
        AdView mAdView = findViewById(R.id.adView);
        AdView mAdView1 = findViewById(R.id.adView1);

        if (checkInternet(FavouriteList.this)) {

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

        dataManager = new DataManager(FavouriteList.this);

        relList = findViewById(R.id.relList);
        relText = findViewById(R.id.relText);
        textF = findViewById(R.id.textF);
        relData = findViewById(R.id.relData);



        listViewData = findViewById(R.id.listViewData);
        item_data = new ArrayList<Word>();
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
        Intent iv = new Intent(FavouriteList.this, Favourite.class);
        iv.putExtra("word", item_data.get(MAINPOSITION).getWord());
        iv.putExtra("meaning", item_data.get(MAINPOSITION).getMeaning());
        iv.putExtra("isFav", item_data.get(MAINPOSITION).getIs_fav());
        iv.putExtra("id", item_data.get(MAINPOSITION).getId());
        iv.putExtra("Favorite", "Favorite");
        startActivity(iv);
    }

    @Override
    public void onResume() {
        super.onResume();

        item_data = dataManager.getAllFavouriteData();
        if (item_data.size() == 0) {

            textF.setVisibility(View.VISIBLE);

            relData.setVisibility(View.GONE);
        } else {

            textF.setVisibility(View.GONE);

            relData.setVisibility(View.VISIBLE);
            this.listViewData.setAdapter(new ItemListBaseAdapter(FavouriteList.this.getApplicationContext(), this.item_data));
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
        Intent intent = new Intent(FavouriteList.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


    private boolean checkInternet(FavouriteList favouriteList) {
        ConnectionDetector cd = new ConnectionDetector(favouriteList);
        return cd.isConnectingToInternet();
    }
}
