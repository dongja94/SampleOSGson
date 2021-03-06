package com.begentgroup.sampleosgson;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-11-25.
 */

public class ItemView extends FrameLayout {
    public ItemView(Context context) {
        super(context);
        init();
    }
    @BindView(R.id.image_icon)
    ImageView iconView;
    @BindView(R.id.text_title)
    TextView titleView;

    private void init() {
        inflate(getContext(), R.layout.item_view, this);
//        iconView = (ImageView)findViewById(R.id.image_icon);
//        titleView = (TextView)findViewById(R.id.text_title);
        ButterKnife.bind(this);
    }

    Product product;
    public void setProduct(Product product) {
        this.product = product;
        titleView.setText(product.name);
        Glide.with(getContext())
                .load(product.thumbnailUrl)
                .into(iconView);
    }
}
