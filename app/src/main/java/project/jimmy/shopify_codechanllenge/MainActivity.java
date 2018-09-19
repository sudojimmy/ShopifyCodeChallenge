package project.jimmy.shopify_codechanllenge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final DataStore dataStore = DataStore.dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.tag_list_view);

        Context content = this;
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            TagListAdapter tagListAdapter = new TagListAdapter(content);
            listView.setAdapter(tagListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dataStore.setTagByPosition(position);
                    Intent intent = new Intent(content, ProductListActivity.class);
                    startActivity(intent);
                }
            });
        }, 3000); // Waiting for DataStore fetching data from url. Not a quite good solution though.

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

    }

    private class TagListAdapter extends BaseAdapter {
        private Context context;
        private List<String> tags = dataStore.getTags();

        TagListAdapter(Context content) {
            this.context = content;
        }

        @Override
        public int getCount() {
            return tags.size();
        }

        @Override
        public Object getItem(int position) {
            return tags.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(context);
            textView.setText(tags.get(position));
            textView.setBackgroundColor(position % 2 == 0 ? 0xbbbbbbbb : 0xffffffff);
            textView.setTextSize(22);
            return textView;
        }
    }

}
