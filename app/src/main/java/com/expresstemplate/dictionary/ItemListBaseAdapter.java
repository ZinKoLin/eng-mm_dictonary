package com.expresstemplate.dictionary;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ItemListBaseAdapter extends BaseAdapter {
    private static List<Word> itemDetailsrrayList;
    Context contex;
    private LayoutInflater l_Inflater;

    static class ViewHolder {
        TextView txt_result;
        TextView txt_word;

        ViewHolder() {
        }
    }

    public ItemListBaseAdapter(Context context, List<Word> results) {
        this.contex = context;
        itemDetailsrrayList = results;
        this.l_Inflater = LayoutInflater.from(context);

    }

    public int getCount() {
        return itemDetailsrrayList.size();
    }

    public Object getItem(int position) {
        return itemDetailsrrayList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = this.l_Inflater.inflate(R.layout.cell, null);
            holder = new ViewHolder();
            holder.txt_word = convertView.findViewById(R.id.txtWord);
            holder.txt_result = convertView.findViewById(R.id.txtMeaning);

            final LinearLayout.LayoutParams layoutparams = (LinearLayout.LayoutParams) holder.txt_word.getLayoutParams();

            if (MainActivity.key.equals("main")) {

                layoutparams.height = 80;
                layoutparams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
                layoutparams.topMargin = 40;

                holder.txt_word.setLayoutParams(layoutparams);
                holder.txt_word.setGravity(View.TEXT_ALIGNMENT_CENTER);
                holder.txt_result.setVisibility(View.GONE);
            } else {
                holder.txt_result.setVisibility(View.VISIBLE);
            }

            if (MainActivity.key.equals("fav")) {
                holder.txt_result.setVisibility(View.VISIBLE);
            }
            if (MainActivity.key.equals("recent")) {
                holder.txt_result.setVisibility(View.VISIBLE);
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_word.setText(itemDetailsrrayList.get(position).getWord());
        holder.txt_result.setText(itemDetailsrrayList.get(position).getMeaning());

        holder.txt_word.setTypeface(MainActivity.tf);
        holder.txt_result.setTypeface(MainActivity.tf);

        return convertView;
    }
}
