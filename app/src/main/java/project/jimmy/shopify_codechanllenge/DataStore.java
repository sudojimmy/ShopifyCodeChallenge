package project.jimmy.shopify_codechanllenge;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import project.jimmy.shopify_codechanllenge.types.Product;
import project.jimmy.shopify_codechanllenge.types.Table;

public class DataStore {
    public static final String URL = "https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";
    public static final DataStore dataStore = new DataStore();
    private Table table;
    private String tag = "";
    private List<String>tags = new ArrayList<>();
    DataStore() {
        new Thread() {
            public void run() {
                try {
                    final OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder().url(URL).build();

                    Response response = client.newCall(request).execute();
                    Gson gson = new Gson();
                    table = gson.fromJson(response.body().string(), Table.class);
                    Log.d("DEBUG", "table constructed successfully");

                    Set<String> set = new HashSet<>();
                    table.products.forEach(product -> {
                        product.tagList = Arrays.asList(product.tags.split("\\s*, \\s*"));
                        set.addAll(product.tagList);
                        product.total_inventory = product
                                .variants
                                .stream()
                                .map(variant -> variant.inventory_quantity)
                                .reduce(0, Integer::sum);
                        try {
                            product.imageBitMap = BitmapFactory
                                    .decodeStream((InputStream) new URL(product.image.src).getContent());
                        } catch (IOException e) { // should check alternative src in the real case
                            Log.d("ERROR", "Image bitmap construct fail");
                            e.printStackTrace();
                        }
                    });
                    tags.addAll(set);
                    Collections.sort(tags);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("ERROR", "table constructed fail");
                    System.exit(1);
                }
            }
        }.start();

    }

    public void setTagByPosition(int position) {
        this.tag = tags.get(position);
    }

    public List<Product> getProducts() {
        return table.products
                .stream()
                .filter(product -> product.tagList.contains(tag))
                .collect(Collectors.toList());
    }

    public List<String> getTags() {
        return tags;
    }

    public String getTag() {
        return tag;
    }
}
