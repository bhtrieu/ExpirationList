package com.example.grocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TwoColumnListAdapter extends ArrayAdapter<Item> {

    private LayoutInflater mInflater;
    private ArrayList<Item> items;
    private int mViewResourceId;

    public TwoColumnListAdapter(Context context, int textViewResourceId, ArrayList<Item> items){
        super(context, textViewResourceId, items);
        this.items = items;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    /*
    Create the multiple column listview
     */
    public View getView(int position, View convertView, ViewGroup parent){
        convertView = mInflater.inflate(mViewResourceId, null);

        Item item = items.get(position);

        if (item != null){
            TextView itemName = (TextView) convertView.findViewById(R.id.textItemName);
            TextView date = (TextView) convertView.findViewById(R.id.textDate);
            if (itemName != null) {
                itemName.setText(item.getItemName());
            }
            if (date != null) {
                date.setText(item.returnDate());
            }
        }


        return convertView;
    }
}
