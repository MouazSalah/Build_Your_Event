package com.buildyourevent.buildyourevent.ui.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SubCategorySpinnerAdapter extends ArrayAdapter<SubCategoryData>
{
    private LayoutInflater flater;

    public SubCategorySpinnerAdapter(Activity context, int resouceId, int textviewId, List<SubCategoryData> list) {

        super(context, resouceId, textviewId, list);
        flater = context.getLayoutInflater();
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

        SubCategoryData rowItem = getItem(position);

        SubCategorySpinnerAdapter.viewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            holder = new SubCategorySpinnerAdapter.viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(android.R.layout.simple_spinner_dropdown_item, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(
                    android.R.id.text1);
            rowview.setTag(holder);
        } else {
            holder = (SubCategorySpinnerAdapter.viewHolder) rowview.getTag();
        }
        holder.txtTitle.setText(rowItem.getSubcategoryName());

        return rowview;
    }

    private class viewHolder {
        TextView txtTitle;
    }
}



