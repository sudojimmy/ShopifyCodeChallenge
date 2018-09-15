package project.jimmy.shopify_codechanllenge;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataStore {
    public static final String URL = "https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";
    public static final DataStore dataStore = new DataStore();
    private Table table;
    DataStore() {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(URL).build();

        final Response[] response = {null};

        new Thread() {
            public void run() {
                try {
                    response[0] = client.newCall(request).execute();
                    try {
                        Gson gson = new Gson();
                        table = gson.fromJson(response[0].body().string(), Table.class);
                        Log.d("DEBUG", "table constructed successfully");
                    } catch (IOException e) {
                        Log.d("ERROR", "");
                        System.exit(1);
                    }
                } catch (Exception e) {
                    Log.d("ERROR", "");
                    System.exit(2);
                }
            }
        }.start();

    }

    public Table getTable() {
        return table;
    }
}
