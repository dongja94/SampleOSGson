package com.begentgroup.sampleosgson;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by Administrator on 2016-11-25.
 */

public class ProductAdapter extends ArrayAdapter<Product> {
    public ProductAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView itemView;
        if (convertView == null) {
            itemView = new ItemView(parent.getContext());
        } else {
            itemView = (ItemView)convertView;
        }
        itemView.setProduct(getItem(position));
        return itemView;
    }
}
