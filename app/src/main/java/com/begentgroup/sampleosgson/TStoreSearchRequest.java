package com.begentgroup.sampleosgson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016-11-25.
 */

public class TStoreSearchRequest extends NetworkRequest<SearchResult> {
    String keyword;
    public TStoreSearchRequest(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("apis.skplanetx.com")
                .addPathSegments("tstore/products")
                .addQueryParameter("order","D")
                .addQueryParameter("searchKeyword", keyword)
                .addQueryParameter("version","1")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("appKey", "9db79957-e1d8-34ca-a342-2fcdf60c50be")
                .build();
        return request;
    }

    @Override
    protected SearchResult parse(ResponseBody body) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<TStoreResult<SearchResult>>(){}.getType();
        TStoreResult<SearchResult> result = gson.fromJson(body.charStream(), type);
        return result.tstore;
    }
}
