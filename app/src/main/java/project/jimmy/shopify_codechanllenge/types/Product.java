package project.jimmy.shopify_codechanllenge.types;

import android.graphics.Bitmap;

import java.util.List;

public class Product {
    public String admin_graphql_api_id;
    public String body_html;
    public String created_at;
    public String handle;
    public long id;
    public Image image;
    public List<Image>images;
    public List<Option>options;
    public String product_type;
    public String published_at;
    public String published_scope;
    public String tags;
    public List<String>tagList;
    public String template_suffix;
    public String title;
    public String updated_at;
    public List<Variant>variants;
    public String vendor;
    public int total_inventory;
    public Bitmap imageBitMap;
}
