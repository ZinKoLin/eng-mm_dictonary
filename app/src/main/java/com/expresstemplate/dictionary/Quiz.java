package com.expresstemplate.dictionary;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.expresstemplate.dictionary.Model_Class.Model_Result;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends AppCompatActivity {

    private static final String TAG = "Quiz--Page";
    DataManager dataManager;
    Word obj;
    TextView txtQuestion, txtDisplayQuestion;
    List<Word> item_data;
    RadioGroup radioGroup1;
    RelativeLayout ad, relWord, relTopLine;
    Button btnAns1, btnAns2, btnAns3, btnAns4, btn_next;
    int start = 1;
    GradientDrawable gd;
    String strQuestion;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    AlertDialogManager alert = new AlertDialogManager();
    private ConnectionDetector cd;
    int random;
    String strRandom;
    RelativeLayout rl_dialoguser;
    public static ArrayList<Model_Result> resultList = new ArrayList<>();
    private String istrue;
    private String strAnswer;
    private String answer1, answer2, answer3, answer4;
    private InterstitialAd interstitialAd;
    private int color;
    private boolean clicked1, clicked2, clicked3, clicked4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.setActionBar(this, "QUIZ");
        setContentView(R.layout.activity_quiz);

        rl_dialoguser = findViewById(R.id.rl_infodialog);
        AdView mAdView = findViewById(R.id.adView);

        if (checkInternet(Quiz.this)) {
            if (getResources().getString(R.string.ads_visibility).equals("yes")) {
                mAdView.setVisibility(View.VISIBLE);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            } else {
                mAdView.getLayoutParams().height = 0;
            }
        } else {
            mAdView.setVisibility(View.GONE);
        }

        ListView mDrawerList = findViewById(R.id.navList);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        MainActivity.addDrawerItems(mDrawerList, this, mDrawerLayout);
        setupDrawer();

        txtDisplayQuestion = findViewById(R.id.txtDisplayQuestion);
        relWord = findViewById(R.id.relWord);
        relTopLine = findViewById(R.id.relTopLine);
        txtQuestion = findViewById(R.id.txtQuestion);

        btnAns1 = findViewById(R.id.btnAns1);
        btnAns2 = findViewById(R.id.btnAns2);
        btnAns3 = findViewById(R.id.btnAns3);
        btnAns4 = findViewById(R.id.btnAns4);
        btn_next = findViewById(R.id.btn_next);

        dataManager = new DataManager(Quiz.this);
        item_data = new ArrayList<Word>();

        gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE); // Specify the shape of drawable
        gd.setColor(Color.WHITE); // make the background transparent,// Set the fill color of drawable
        gd.setStroke(1, Color.parseColor(MainActivity.myTheme)); // border width and color,// Create a 2 pixels width red colored border for drawable
        gd.setCornerRadius(15.0f); // border corner radius

        relTopLine.setBackgroundColor(Color.parseColor(MainActivity.myTheme));

        btn_next.setTypeface(MainActivity.tf);

        RadioQuestion();
        if (MainActivity.myTheme != null) {
            btn_next.setBackgroundColor(Color.parseColor(MainActivity.myTheme));
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addInResultList(item_data.get(random).getWord(), item_data.get(random).getMeaning(), strAnswer);

                btn_next.setVisibility(View.GONE);
                btnAns1.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                btnAns2.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                btnAns3.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                btnAns4.setBackgroundColor(getResources().getColor(R.color.option_cell_color));

                btnAns1.setEnabled(true);
                btnAns2.setEnabled(true);
                btnAns3.setEnabled(true);
                btnAns4.setEnabled(true);
                btn_next.setEnabled(false);

                if (start < 10) {
                    start++;
                    strQuestion = String.valueOf(start);

                    txtDisplayQuestion.setText(strQuestion + " of " + "10");
                    RadioQuestion();

                } else if (start == 10) {
                    btnAns1.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    btnAns2.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    btnAns3.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    btnAns4.setBackgroundColor(getResources().getColor(R.color.option_cell_color));

                    btnAns1.setEnabled(false);
                    btnAns2.setEnabled(false);
                    btnAns3.setEnabled(false);
                    btnAns4.setEnabled(false);

                    if (checkInternet(Quiz.this)) {
                        if (getString(R.string.ads_visibility).equals("yes")) {
                            interstitialAd();
                        } else {
                            Intent intent = new Intent(Quiz.this, ResultPage.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(Quiz.this, ResultPage.class);
                        startActivity(intent);
                        finish();
                    }
                }

                if (clicked1 == true) {
                    strAnswer = answer1;

                }
                if (clicked2 == true) {
                    strAnswer = answer2;

                }
                if (clicked3 == true) {
                    strAnswer = answer3;

                }
                if (clicked4 == true) {
                    strAnswer = answer4;

                }

            }
        });

        btnAns1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                clicked1 = true;
                clicked2 = false;
                clicked3 = false;
                clicked4 = false;

                answer1 = btnAns1.getText().toString();
                strAnswer = answer1;

                if (answer1 != null) {
                    if (answer1.equals(btnAns1.getTag()) == true) {
                        istrue = "yes";
                    } else {
                        istrue = "no";
                    }
                } else {

                }

                if (MainActivity.myTheme != null) {

                    if (btnAns1.isClickable()) {
                        int color = Color.parseColor(MainActivity.myTheme);
                        btnAns1.setBackgroundColor(color);


                    } else {
                        btnAns1.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    }
                }
                btn_next.setEnabled(true);
                btn_next.setVisibility(View.VISIBLE);
                if (btn_next.getVisibility() == View.VISIBLE) {
                    btn_next.setEnabled(true);
                }
                if (btnAns1.isClickable()) {
                    btnAns2.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    btnAns3.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    btnAns4.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                }
            }
        });

        btnAns2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                clicked2 = true;
                clicked1 = false;
                clicked3 = false;
                clicked4 = false;

                answer2 = btnAns2.getText().toString();
                strAnswer = answer2;

                if (answer2 != null) {
                    if (answer2.equals(btnAns2.getTag()) == true) {
                        istrue = "yes";
                    } else {
                        istrue = "no";
                    }
                } else {

                }

                if (MainActivity.myTheme != null) {

                    if (btnAns2.isClickable()) {
                        int color = Color.parseColor(MainActivity.myTheme);
                        btnAns2.setBackgroundColor(color);

                    } else {
                        btnAns2.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    }
                }

                btn_next.setEnabled(true);
                btn_next.setVisibility(View.VISIBLE);
                if (btn_next.getVisibility() == View.VISIBLE) {
                    btn_next.setEnabled(true);
                }
                if (btnAns2.isClickable()) {
                    btnAns1.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    btnAns3.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    btnAns4.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                }

            }
        });

        btnAns3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                answer3 = btnAns3.getText().toString();
                strAnswer = answer3;

                clicked3 = true;
                clicked2 = false;
                clicked1 = false;
                clicked4 = false;

                if (answer3 != null) {
                    if (answer3.equals(btnAns3.getTag()) == true) {
                        istrue = "yes";
                    } else {
                        istrue = "no";
                    }
                } else {

                }

                if (MainActivity.myTheme != null) {

                    if (btnAns3.isClickable()) {
                        int color = Color.parseColor(MainActivity.myTheme);
                        btnAns3.setBackgroundColor(color);

                    } else {
                        btnAns3.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    }
                }

                btn_next.setEnabled(true);
                btn_next.setVisibility(View.VISIBLE);
                if (btn_next.getVisibility() == View.VISIBLE) {
                    btn_next.setEnabled(true);
                }
                if (btnAns3.isClickable()) {
                    btnAns2.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    btnAns1.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    btnAns4.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                }

            }
        });

        btnAns4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                clicked4 = true;
                clicked2 = false;
                clicked3 = false;
                clicked1 = false;

                answer4 = btnAns4.getText().toString();

                strAnswer = answer4;

                if (answer4 != null) {
                    if (answer4.equals(btnAns4.getTag()) == true) {
                        istrue = "yes";
                    } else {
                        istrue = "no";
                    }
                } else {

                }

                if (MainActivity.myTheme != null) {

                    if (btnAns4.isClickable()) {
                        int color = Color.parseColor(MainActivity.myTheme);
                        btnAns4.setBackgroundColor(color);

                    } else {
                        btnAns4.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    }
                }

                btn_next.setEnabled(true);
                btn_next.setVisibility(View.VISIBLE);
                if (btn_next.getVisibility() == View.VISIBLE) {
                    btn_next.setEnabled(true);
                }
                if (btnAns4.isClickable()) {
                    btnAns2.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    btnAns3.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                    btnAns1.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
                }

            }
        });

    }

    private void interstitialAd() {

        interstitialAd = new InterstitialAd(Quiz.this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_id));
        AdRequest request = new AdRequest.Builder().build();
        interstitialAd.loadAd(request);

        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                try {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }
                } catch (Exception e) {
                    e.getMessage();
                    Log.e(TAG, "onAdLoaded: " + e.getMessage());
                }

            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

                Intent intent = new Intent(Quiz.this, ResultPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();

                Intent intent = new Intent(Quiz.this, ResultPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void RadioQuestion() {

        btnAns1.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
        btnAns2.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
        btnAns3.setBackgroundColor(getResources().getColor(R.color.option_cell_color));
        btnAns4.setBackgroundColor(getResources().getColor(R.color.option_cell_color));

        btnAns1.setTextColor(Color.WHITE);
        btnAns2.setTextColor(Color.WHITE);
        btnAns3.setTextColor(Color.WHITE);
        btnAns4.setTextColor(Color.WHITE);

        item_data.clear();
        item_data = dataManager.Quiz();
        random = Util.generateRandomNumberInRange(0, 3);
        Log.d("Random", "" + random);

        strRandom = String.valueOf(random);

        if (random == 0) {
            txtQuestion.setText(item_data.get(0).getWord());
            btnAns1.setTag(item_data.get(0).getMeaning());
            Log.d("ansTag1", item_data.get(0).getWord() + item_data.get(0).getMeaning());
        } else if (random == 1) {
            txtQuestion.setText(item_data.get(1).getWord());
            btnAns2.setTag(item_data.get(1).getMeaning());
            Log.d("ansTag2", item_data.get(0).getWord() + item_data.get(1).getMeaning());
        } else if (random == 2) {
            txtQuestion.setText(item_data.get(2).getWord());
            btnAns3.setTag(item_data.get(2).getMeaning());
            Log.d("ansTag3", item_data.get(0).getWord() + item_data.get(2).getMeaning());
        } else if (random == 3) {
            txtQuestion.setText(item_data.get(3).getWord());
            btnAns4.setTag(item_data.get(3).getMeaning());
            Log.d("ansTag4", item_data.get(0).getWord() + item_data.get(3).getMeaning());
        }

        if (random == 0) {
            istrue = "yes";
        } else {
            istrue = "no";
        }
        if (random == 1) {
            istrue = "yes";
        } else {
            istrue = "no";
        }
        if (random == 2) {
            istrue = "yes";
        } else {
            istrue = "no";
        }
        if (random == 3) {
            istrue = "yes";
        } else {
            istrue = "no";
        }

        btnAns1.setText(item_data.get(0).getMeaning());
        btnAns2.setText(item_data.get(1).getMeaning());
        btnAns3.setText(item_data.get(2).getMeaning());
        btnAns4.setText(item_data.get(3).getMeaning());

        btnAns1.setTransformationMethod(null);
        btnAns2.setTransformationMethod(null);
        btnAns3.setTransformationMethod(null);
        btnAns4.setTransformationMethod(null);
    }

    private void addInResultList(String word, String meaning, String strAnswer) {

        Model_Result model_result = new Model_Result();
        model_result.setWord(word);
        model_result.setAnswer(meaning);
        model_result.setAnswer(strAnswer);
        model_result.setIsTrue(istrue);
        resultList.add(model_result);
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
        Intent intent = new Intent(Quiz.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


    private boolean checkInternet(Quiz quiz) {
        ConnectionDetector cd = new ConnectionDetector(quiz);
        return cd.isConnectingToInternet();
    }
}
