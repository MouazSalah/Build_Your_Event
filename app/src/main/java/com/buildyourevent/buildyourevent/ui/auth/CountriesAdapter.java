package com.buildyourevent.buildyourevent.ui.auth;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.buildyourevent.buildyourevent.model.auth.countries.CountryData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CountriesAdapter extends ArrayAdapter<CountryData>
{

    private LayoutInflater flater;


    public CountriesAdapter(@NonNull Context context, List<CountryData> list) {
        super(context, android.R.layout.simple_spinner_dropdown_item,
                android.R.id.text1,list);
    }



    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {

        return rowview(convertView, position);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
        return rowview(convertView, position);
    }

    private View rowview(View convertView, int position) {

        CountryData rowItem = getItem(position);

        viewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(android.R.layout.simple_spinner_dropdown_item, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(
                    android.R.id.text1);
            rowview.setTag(holder);
        } else {
            holder = (viewHolder) rowview.getTag();
        }
        holder.txtTitle.setText(rowItem.getCountryName());

        return rowview;
    }

    private class viewHolder {
        TextView txtTitle;
    }
}
