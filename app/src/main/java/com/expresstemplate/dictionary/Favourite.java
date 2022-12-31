package com.expresstemplate.dictionary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Locale;

public class Favourite extends Activity {

    DataManager dataManager;
    ImageView btnFavorite, btnUnFavourite;
    String Id, Word, Meaning, isFav, Favorite;
    RelativeLayout ad;
    RelativeLayout relativeLayout;
    private ImageView speak_word;
    private ImageView btnMeaningSpeak;
    public TextToSpeech tts,ttsHindi;
    private SharedPreferences prefs;
    public static String myTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.setInterstitial(this);
        setContentView(R.layout.activity_favourite);

        prefs = Favourite.this.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        myTheme = prefs.getString("Color", "#" + Integer.toHexString(ContextCompat.getColor(this, R.color.DEFAULT_COLOR)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(myTheme));
        }

        relativeLayout = findViewById(R.id.relScreenShot);
        speak_word = (ImageView) findViewById(R.id.speak_word);
        //btnMeaningSpeak = findViewById(R.id.btnMeaningSpeak);
        btnFavorite = findViewById(R.id.btnFavourite);
        btnUnFavourite = findViewById(R.id.btnUnFavourite);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status != TextToSpeech.ERROR) {

                    tts.setLanguage(Locale.getDefault());

                }
            }
        });

        ttsHindi = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status != TextToSpeech.ERROR) {

                    ttsHindi.setLanguage(Locale.getDefault());
                    ttsHindi.setLanguage(new Locale(getString(R.string.tts_locale)));

                }
            }
        });

        ImageView btnShare = findViewById(R.id.btnShare);
        btnShare.setImageResource(R.drawable.share_);
        btnFavorite.setImageResource(R.drawable.fav_);
        RelativeLayout relTitle = findViewById(R.id.relTitle);
        if (MainActivity.myTheme != null) {
            relTitle.setBackgroundColor(Color.parseColor(MainActivity.myTheme));

        } else {

        }

        ad = findViewById(R.id.ad);
        AdView mAdView = findViewById(R.id.adView);

        if (checkInternet(Favourite.this)) {
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


        Intent i = getIntent();
        Word = i.getStringExtra("word");
        Meaning = i.getStringExtra("meaning");
        Id = i.getStringExtra("id");
        isFav = i.getStringExtra("isFav");
        Favorite = i.getStringExtra("Favorite");
        Log.d("Favorite", "" + Favorite);


        TextView txtWord = findViewById(R.id.txtWord);
        TextView txtMeaning = findViewById(R.id.txtMeaning);
        txtWord.setText(Word);

      //scrollmode
        txtMeaning.setMovementMethod(new ScrollingMovementMethod());

       // txtMeaning.setText(Meaning);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtMeaning.setText(Html.fromHtml(Meaning, Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtMeaning.setText(Html.fromHtml(Meaning));
        }
        txtWord.setTypeface(MainActivity.tf);
        txtMeaning.setTypeface(MainActivity.tf);


        dataManager = new DataManager(Favourite.this);

        btnFavorite.setVisibility(View.INVISIBLE);
        btnUnFavourite.setVisibility(View.VISIBLE);
        if (isFav.equals("1")) {
            btnFavorite.setVisibility(View.VISIBLE);
            btnUnFavourite.setVisibility(View.INVISIBLE);
        } else if (isFav.equals("0")) {
            btnFavorite.setVisibility(View.INVISIBLE);
            btnUnFavourite.setVisibility(View.VISIBLE);
        }

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                btnFavorite.setVisibility(View.INVISIBLE);
                btnUnFavourite.setVisibility(View.VISIBLE);
                dataManager.UnFavouriteWord(Id);

            }
        });

        btnUnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                btnFavorite.setVisibility(View.VISIBLE);
                btnUnFavourite.setVisibility(View.INVISIBLE);
                dataManager.FavouriteWord(Id);

            }
        });

        if (Favorite != null) {
            if (!Favorite.equals("Favorite")) {
                //Insert into History Table
                String string = Meaning.toString().replace("'","''");

                dataManager.InsertHistoryWord(Word, string);
            }
        } else {
            //Insert into History Table
            String string = Meaning.toString().replace("'","''");
            dataManager.InsertHistoryWord(Word, string);
        }

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AndroidSolved");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Word: " + Word + " and Meaning: " + Meaning + "\nhttps://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });

        speak_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tts.speak(Word, TextToSpeech.QUEUE_ADD, null);


            }
        });

//        btnMeaningSpeak.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ttsHindi.speak(Meaning, TextToSpeech.QUEUE_ADD, null);
//            }
//        });
    }


    @Override
    public void onBackPressed() {
        MainActivity.onBackPress();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    private boolean checkInternet(Favourite favourite) {
        ConnectionDetector cd = new ConnectionDetector(favourite);
        return cd.isConnectingToInternet();
    }

}
