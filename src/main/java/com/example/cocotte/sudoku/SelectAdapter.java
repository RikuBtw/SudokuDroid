package com.example.cocotte.sudoku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cocotte on 25/04/2018.
 */

public class SelectAdapter extends ArrayAdapter<SelectModel>{

    public SelectAdapter(List<SelectModel> data, Context context) {
        super(context, R.layout.list_select, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.list_select, null);
        }
        SelectModel item = getItem(position);
        if (item != null) {
            TextView name = view.findViewById(R.id.name);
            name.setLetterSpacing(0.1f);
            TextView level = view.findViewById(R.id.level);
            level.setLetterSpacing(0.1f);
            TextView percentage = view.findViewById(R.id.percentage);

            if (name != null) {
                name.setText(item.getName());
            }
            if (level != null) {
                level.setText(item.getLevel());
            }
            if (percentage != null) {
                percentage.setText(item.getPercentage());
            }
        }
        return view;
    }

}