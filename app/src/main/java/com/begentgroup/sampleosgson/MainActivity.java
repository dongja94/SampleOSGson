package com.begentgroup.sampleosgson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText keywordView;
    ListView listView;
    ArrayAdapter<Product> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keywordView = (EditText)findViewById(R.id.edit_keyword);
        listView = (ListView)findViewById(R.id.list_search);
//        mAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1);
        mAdapter = new ProductAdapter(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);

        Button btn = (Button)findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = keywordView.getText().toString();
                if (!TextUtils.isEmpty(keyword)) {
//                    new MyTStoreSearchTask().execute(keyword);
                    TStoreSearchRequest request = new TStoreSearchRequest(keyword);
                    NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<SearchResult>() {
                        @Override
                        public void onSuccess(NetworkRequest<SearchResult> request, SearchResult result) {
                           mAdapter.addAll(result.products.productList);
                        }

                        @Override
                        public void onFail(NetworkRequest<SearchResult> request, int errorCode, String errorMessage, Throwable e) {
                            Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public static final String SEARCH_URL = "http://apis.skplanetx.com/tstore/products?count=&order=D&page=&searchKeyword=%s&version=1";
    class MyTStoreSearchTask extends AsyncTask<String, Integer, SearchResult> {
        @Override
        protected SearchResult doInBackground(String... params) {
            String keyword = params[0];
            try {
                String urlText = String.format(SEARCH_URL, URLEncoder.encode(keyword, "utf-8"));
                URL url = new URL(urlText);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("appKey", "9db79957-e1d8-34ca-a342-2fcdf60c50be");
                int code = conn.getResponseCode();
                if (code >= 200 && code < 300) {
//                    StringBuilder sb = new StringBuilder();
//                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                    String line;
//                    while((line = br.readLine()) != null) {
//                        sb.append(line).append("\n\r");
//                    }
//                    return sb.toString();
                    Gson gson = new Gson();
                    InputStreamReader isr = new InputStreamReader(conn.getInputStream());

                    Type type = new TypeToken<TStoreResult<SearchResult>>(){}.getType();
                    TStoreResult<SearchResult> result = gson.fromJson(isr, type);
                    return result.tstore;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(SearchResult s) {
            if (s != null) {
                mAdapter.addAll(s.products.productList);
            } else {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
