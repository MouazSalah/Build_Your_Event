package com.buildyourevent.buildyourevent.ui.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.buildyourevent.buildyourevent.model.data.category.CategoryData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<CategoryData>
{
    private LayoutInflater flater;

    public CategorySpinnerAdapter(Activity context, int resouceId, int textviewId, List<CategoryData> list) {

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

        CategoryData rowItem = getItem(position);

        CategorySpinnerAdapter.viewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            holder = new CategorySpinnerAdapter.viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(android.R.layout.simple_spinner_dropdown_item, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(
                    android.R.id.text1);
            rowview.setTag(holder);
        } else {
            holder = (CategorySpinnerAdapter.viewHolder) rowview.getTag();
        }
        holder.txtTitle.setText(rowItem.getCategoryName());

        return rowview;
    }

    private class viewHolder {
        TextView txtTitle;
    }
}


