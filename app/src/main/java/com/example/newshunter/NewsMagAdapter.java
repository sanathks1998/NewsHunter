package com.example.newshunter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.HashMap;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class NewsMagAdapter extends BaseAdapter {
    final int radius = 15;
    final int margin = 15;
    final Transformation transformation = new RoundedCornersTransformation(radius, margin);
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public NewsMagAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
    }
    public int getCount() {
        return data.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ListNewsMagViewHolder held = null;
        if (convertView == null) {
            held = new ListNewsMagViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.maglistviews, parent, false);
            held.imgse = (ImageView) convertView.findViewById(R.id.imgs);
            held.srcs = (TextView) convertView.findViewById(R.id.auth);
            held.heads = (TextView) convertView.findViewById(R.id.heads);
            held.subs = (TextView) convertView.findViewById(R.id.subtitle);
            convertView.setTag(held);
        } else {
            held = (ListNewsMagViewHolder) convertView.getTag();
        }
        held.imgse.setId(position);
        held.srcs.setId(position);
        held.heads.setId(position);
        held.subs.setId(position);


        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try{
            held.srcs.setText(song.get(MainActivity.srcs));
            held.heads.setText(song.get(MainActivity.heading));

            held.subs.setText(song.get(MainActivity.subtitles));

            if(song.get(MainActivity.imgs).toString().length() < 5)
            {
                held.imgse.setVisibility(View.GONE);
            }else{

                Picasso.get().load(song.get(MainActivity.imgs).toString())
                        .resize(350, 300)
                        . transform(transformation).into(held.imgse);
            }
        }catch(Exception e) {}
        return convertView;
    }
}

class ListNewsMagViewHolder {
    ImageView imgse;
    TextView srcs, heads, subs;
}
