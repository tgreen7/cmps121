package com.example.cs121.final_project.Recipes;


/**
 * Created by Taoh on 3/4/2016.
 */


import java.util.ArrayList;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cs121.final_project.R;

public class RecipeListAdapter extends BaseAdapter {
    public ArrayList<MetaInfo> list;
    Activity activity;

    public RecipeListAdapter(Activity activity, ArrayList<MetaInfo> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
        ImageView beer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.recipe_list_row, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.SecondText);
            holder.beer = (ImageView) convertView.findViewById(R.id.BeerImage);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        MetaInfo info = list.get(position);
        holder.txtFirst.setText(info.name);
        holder.txtSecond.setText(info.style);
        if(info.color >= 40) {
            holder.beer.setImageResource(R.drawable.beer_40_up);
        } else if (info.color >= 35) {
            holder.beer.setImageResource(R.drawable.beer_35);
        } else if (info.color >= 29) {
            holder.beer.setImageResource(R.drawable.beer_29);
        } else if (info.color >= 24) {
            holder.beer.setImageResource(R.drawable.beer_24);
        } else if (info.color >= 20) {
            holder.beer.setImageResource(R.drawable.beer_20);
        } else if (info.color >= 17) {
            holder.beer.setImageResource(R.drawable.beer_17);
        } else if (info.color >= 13) {
            holder.beer.setImageResource(R.drawable.beer_13);
        } else if (info.color >= 10) {
            holder.beer.setImageResource(R.drawable.beer_10);
        } else if (info.color >= 8) {
            holder.beer.setImageResource(R.drawable.beer_8);
        } else if (info.color >= 6) {
            holder.beer.setImageResource(R.drawable.beer_6);
        } else if (info.color >= 4) {
            holder.beer.setImageResource(R.drawable.beer_4);
        } else if (info.color >= 2) {
            holder.beer.setImageResource(R.drawable.beer_2_3);
        }

        return convertView;
    }
}
