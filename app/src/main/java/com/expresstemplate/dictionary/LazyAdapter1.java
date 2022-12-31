package com.expresstemplate.dictionary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Redixbit on 12-02-2018.
 */

public class LazyAdapter1 extends BaseAdapter {

    private final Typeface tf;
    private Activity activity;
    private LayoutInflater inflater = null;
    DataManager dataManager;

    public LazyAdapter1(Activity guide_Fragment) {
        activity = guide_Fragment;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        tf = Typeface.createFromAsset(activity.getAssets(), "fonts/Raleway-Medium.ttf");

    }

    @Override
    public int getCount() {
        return MainActivity.ArrayData.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.drawercell, null);
        }

        ColorFilter cf;

        RelativeLayout relLine = vi.findViewById(R.id.relLine);
        RelativeLayout rel_wid = vi.findViewById(R.id.rel_wid);

        TextView topic_name = vi.findViewById(R.id.txtDrawer);
        topic_name.setText(MainActivity.ArrayData[position]);
        topic_name.setTypeface(tf);


        ImageView imageView = vi.findViewById(R.id.imageView);
        imageView.setImageResource(MainActivity.ArrayImage[position]);

        dataManager = new DataManager(activity);
        int i = dataManager.getCount();

        if (i < 100){
            if (position == 1){
                rel_wid.setVisibility(View.GONE);
            }
        }else {
            if (position == 1){
                rel_wid.setVisibility(View.VISIBLE);
            }

        }

        if (MainActivity.myTheme != null) {
            cf = new PorterDuffColorFilter(Color.parseColor(MainActivity.myTheme), PorterDuff.Mode.SRC_IN);
            imageView.setColorFilter(cf);
            relLine.setBackgroundColor(Color.parseColor(MainActivity.myTheme));
            topic_name.setTypeface(MainActivity.tf);
            topic_name.setTextColor(Color.parseColor(MainActivity.myTheme));
        } else {
            cf = new PorterDuffColorFilter(ContextCompat.getColor(activity, R.color.DEFAULT_COLOR), PorterDuff.Mode.SRC_IN);
            imageView.setColorFilter(cf);
            relLine.setBackgroundColor(ContextCompat.getColor(activity, R.color.DEFAULT_COLOR));
        }

        return vi;
    }
}