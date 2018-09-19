package project.jimmy.shopify_codechanllenge;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import project.jimmy.shopify_codechanllenge.types.Product;

public class ProductListActivity extends AppCompatActivity {
    private final DataStore dataStore = DataStore.dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ListView listView = findViewById(R.id.product_list_view);
        listView.setAdapter(new ProductListAdapter());

        TextView title = findViewById(R.id.productListTitleText);
        title.setText("Tag: " + dataStore.getTag());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
    }

    private class ProductListAdapter extends BaseAdapter {
        private List<Product> products = dataStore.getProducts();

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public Object getItem(int position) {
            return products.get(position);
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

            Product product = products.get(position);
            if (product.imageBitMap != null) {
                imageView.setImageBitmap(product.imageBitMap);
            }
            textView_title.setText(product.title);
            textView_inventory.setText("Total Inventory: " + product.total_inventory);
            return view;
        }
    }
}
