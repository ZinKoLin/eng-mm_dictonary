package com.expresstemplate.dictionary.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.expresstemplate.dictionary.MainActivity;
import com.expresstemplate.dictionary.Model_Class.Model_Result;
import com.expresstemplate.dictionary.R;
import com.expresstemplate.dictionary.ResultPage;

import java.util.ArrayList;

public class Adapter_Result extends BaseAdapter {

    ArrayList<Model_Result> resultArrayList;
    LayoutInflater inflater;
    Activity activity;

    public Adapter_Result(ResultPage resultPage, ArrayList<Model_Result> resultList) {
        activity = resultPage;
        this.resultArrayList = resultList;
        inflater = LayoutInflater.from(resultPage);

    }

    @Override
    public int getCount() {
        return resultArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_result, parent, false);
        TextView tx_num = (TextView) convertView.findViewById(R.id.tx_num);
        TextView tx_word = (TextView) convertView.findViewById(R.id.tx_word);
        TextView tx_meaning = (TextView) convertView.findViewById(R.id.tx_meaning);
        TextView tx_ans = (TextView) convertView.findViewById(R.id.tx_ans);
        ImageView img_correct = (ImageView) convertView.findViewById(R.id.img_correct);
        ImageView img_wrong = (ImageView) convertView.findViewById(R.id.img_wrong);
        CardView cell_card = (CardView) convertView.findViewById(R.id.cell_card);

        tx_num.setText(String.valueOf(position + 1));
        tx_word.setText(resultArrayList.get(position).getWord());
        tx_meaning.setText(resultArrayList.get(position).getAnswer());
        tx_ans.setText(resultArrayList.get(position).getMeaning());

        if (resultArrayList.get(position).getIsTrue().equals("yes")) {
            img_correct.setVisibility(View.VISIBLE);
            img_wrong.setVisibility(View.GONE);

        } else {
            img_correct.setVisibility(View.GONE);
            img_wrong.setVisibility(View.VISIBLE);
        }

        if (MainActivity.myTheme != null) {

            tx_num.setTextColor(Color.parseColor(MainActivity.myTheme));
            tx_word.setTextColor(Color.parseColor(MainActivity.myTheme));
        }
        cell_card.setCardBackgroundColor(activity.getColor(R.color.cell_color));

        return convertView;
    }
}