package com.expresstemplate.dictionary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class AddWordDetailPage extends AppCompatActivity {

    private EditText editWord;
    private EditText editMeaning;

    private RelativeLayout ad;
    private String strAddWord = "1";
    private String English;
    private String Spanish;
    private String Update;
    private String NewWord;
    private String Id;
    private DataManager dataManager;
    static int adCount = 0;
    static InterstitialAd adIntertial;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    RelativeLayout rl_dialoguser;
    Activity activity;
    private ImageView img_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.setActionBar(this, "ADD WORD");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        actionBar.setTitle("ADD WORD");
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.drawable.ic_check_);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NewWord.equals("Update")) {

                    Log.d("Update1", "" + Update);

                    if (editWord.getText().length() != 0) {
                        Boolean isSuccess = dataManager.UpdateAddWord(editWord.getText().toString(),
                                editMeaning.getText().toString(), Id);
                        if (isSuccess == true) {
                            showAlertDialog(AddWordDetailPage.this, "Success", "Your word & meaning successfully update.", true);
                            editWord.setText("");
                            editMeaning.setText("");
                        } else {
                            showAlertDialog(AddWordDetailPage.this, "Fail", "Please try again. Error occur.", true);
                        }
                    } else {
                        showAlertDialog(AddWordDetailPage.this, "Input Required", "Word or Meaning cannot blank.", true);
                    }

                } else if (NewWord.equals("NewWord")) {

                    if (editWord.getText().length() != 0) {
                        Boolean isSuccess = dataManager.InsertWord(editWord.getText().toString(),
                                editMeaning.getText().toString(), strAddWord);
                        if (isSuccess == true) {
                            showAlertDialog(AddWordDetailPage.this, "Success", "Your word & meaning successfully saved.", true);
                            editWord.setText("");
                            editMeaning.setText("");
                        } else {
                            showAlertDialog(AddWordDetailPage.this, "Fail", "Please try again. Error occur.", true);
                        }
                    } else {
                        showAlertDialog(AddWordDetailPage.this, "Input Required", "Word or Meaning cannot blank.", true);
                    }
                }

            }
        });

        setContentView(R.layout.activity_add_word_detail_page);

        img_menu = (ImageView) findViewById(R.id.img_menu);
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AddWordDetailPage.this, "1111", Toast.LENGTH_SHORT).show();

            }
        });

        ListView mDrawerList = findViewById(R.id.navList);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        MainActivity.addDrawerItems(mDrawerList, this, mDrawerLayout);
        setupDrawer();
        mActivityTitle = "ADD WORD";

        rl_dialoguser = findViewById(R.id.rl_infodialog);

        editWord = findViewById(R.id.editWord);
        editMeaning = findViewById(R.id.editMeaning);

        ad = findViewById(R.id.ad);
        AdView mAdView = findViewById(R.id.adView);

        if (getResources().getString(R.string.ads_visibility).equals("yes")) {
            ad.setVisibility(View.VISIBLE);

            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        } else {
            ad.getLayoutParams().height = 0;
        }


        RelativeLayout relHeader = findViewById(R.id.relHeader);
        RelativeLayout relClear = findViewById(R.id.relClear);

        Button btnClear = findViewById(R.id.btnClear);
        Button btnSave = findViewById(R.id.btnSave);
        Button btn_reset = findViewById(R.id.btn_reset);

        RelativeLayout relLine = findViewById(R.id.relLine);
        RelativeLayout relTopLine = findViewById(R.id.relTopLine);

        TextView txtHeader = findViewById(R.id.txtHeader);


        txtHeader.setTypeface(MainActivity.tf);
        btnSave.setTypeface(MainActivity.tf);
        btnClear.setTypeface(MainActivity.tf);
        btn_reset.setTypeface(MainActivity.tf);


        if (MainActivity.myTheme != null) {

            relHeader.setBackgroundColor(Color.parseColor(MainActivity.myTheme));
            relLine.setBackgroundColor(Color.parseColor(MainActivity.myTheme));
            relTopLine.setBackgroundColor(Color.parseColor(MainActivity.myTheme));
            btn_reset.setBackgroundColor(Color.parseColor(MainActivity.myTheme));

            btnClear.setTextColor(Color.parseColor(MainActivity.myTheme));
            btnSave.setTextColor(Color.parseColor(MainActivity.myTheme));

        } else {

        }

        Intent i = getIntent();
        English = i.getStringExtra("english");
        Spanish = i.getStringExtra("spanish");
        Log.d("Update", "" + Update);
        Id = i.getStringExtra("id");
        NewWord = i.getStringExtra("Insert");
        Log.d("NewWord", "" + NewWord);

        editWord = findViewById(R.id.editWord);
        editMeaning = findViewById(R.id.editMeaning);

        dataManager = new DataManager(AddWordDetailPage.this);

        if (English != null) {

            editWord.setText(English);
            editMeaning.setText(Spanish);

        } else {

        }

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editWord != null || editMeaning != null) {
                    editWord.setText("");
                    editMeaning.setText("");
                }

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean isSuccess = dataManager.DeleteWord(Id);

                Intent i = new Intent(AddWordDetailPage.this, AdWord.class);
                startActivity(i);
            }
        });

        if (NewWord.equals("Update")) {

            btnClear.setVisibility(View.VISIBLE);

        } else if (NewWord.equals("NewWord")) {

            relClear.getLayoutParams().height = 0;

        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NewWord.equals("Update")) {

                    Log.d("Update1", "" + Update);

                    if (editWord.getText().length() != 0) {
                        Boolean isSuccess = dataManager.UpdateAddWord(editWord.getText().toString(),
                                editMeaning.getText().toString(), Id);
                        if (isSuccess == true) {
                            showAlertDialog(AddWordDetailPage.this, "Success", "Your word & meaning successfully update.", true);
                            editWord.setText("");
                            editMeaning.setText("");
                        } else {
                            showAlertDialog(AddWordDetailPage.this, "Fail", "Please try again. Error occur.", true);
                        }
                    } else {
                        showAlertDialog(AddWordDetailPage.this, "Input Required", "Word or Meaning cannot blank.", true);
                    }

                } else if (NewWord.equals("NewWord")) {

                    if (editWord.getText().length() != 0) {
                        Boolean isSuccess = dataManager.InsertWord(editWord.getText().toString(),
                                editMeaning.getText().toString(), strAddWord);
                        if (isSuccess == true) {
                            showAlertDialog(AddWordDetailPage.this, "Success", "Your word & meaning successfully saved.", true);
                            editWord.setText("");
                            editMeaning.setText("");
                        } else {
                            showAlertDialog(AddWordDetailPage.this, "Fail", "Please try again. Error occur.", true);
                        }
                    } else {
                        showAlertDialog(AddWordDetailPage.this, "Input Required", "Word or Meaning cannot blank.", true);
                    }
                }
            }

        });
    }

    private void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(AddWordDetailPage.this, AdWord.class);
        startActivity(intent);
        finish();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
