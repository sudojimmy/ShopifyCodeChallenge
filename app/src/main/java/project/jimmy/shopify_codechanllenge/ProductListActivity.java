package project.jimmy.shopify_codechanllenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataStore ds = DataStore.dataStore;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Log.d("PRODUCT SIZE", ""+ds.getProducts().size());
        for (Product p: ds.getProducts()) {
            Log.d("TAG", ""+p.total_inventory);
        }

        ListView listView = findViewById(R.id.product_list_view);
        listView.setAdapter(new ProductListAdapter(this));

        TextView title = findViewById(R.id.productListTitleText);
        title.setText("Tag: " + ds.getTag());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
    }

    private class ProductListAdapter extends BaseAdapter {
        private Context context;
        private List<Product> product = DataStore.dataStore.getProducts();

        ProductListAdapter(Context content) {
            this.context = content;
        }

        @Override
        public int getCount() {
            return product.size();
        }

        @Override
        public Object getItem(int position) {
            return product.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.product_row_layout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView textView_title = (TextView) view.findViewById(R.id.titleTextView);
            TextView textView_inventory = (TextView) view.findViewById(R.id.inventoryTextView);

            if (product.get(position).imageBitMap != null) {
                imageView.setImageBitmap(product.get(position).imageBitMap);
            }
            textView_title.setText(product.get(position).title);
            textView_inventory.setText("Total Inventory: " + product.get(position).total_inventory);
            return view;
        }
    }
}
