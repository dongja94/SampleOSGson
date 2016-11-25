package com.begentgroup.sampleosgson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016-11-24.
 */
public class Products {
    @SerializedName("product")
    List<Product> productList;
}
