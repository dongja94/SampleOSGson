package com.begentgroup.sampleosgson;

/**
 * Created by Administrator on 2016-11-24.
 */
public class Product {
    String webUrl;
    int charge;
    int downloadCount;
    String description;
    String thumbnailUrl;
    String name;
    String categoryCode;
    float score;
    String tinyUrl;
    String productId;

    @Override
    public String toString() {
        return name;
    }
}
