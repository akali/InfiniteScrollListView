package com.example.aqali.infinitescrolllistview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

	private ListView listView;
	private ArrayList<String> arr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView);

		arr = new ArrayList<>();

		final String[] names = new String[]{"John", "Brown", "Black", "Luck"};

		final Random rnd = new Random();

		for (int i = 0; i < 20; ++i)
			arr.add(names[0]);

		View footer = getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
		final ProgressBar progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);

		final ListAdapter adapter = new ListAdapter(this, arr);

		listView.setAdapter(adapter);

		listView.setOnScrollListener(new AbsListView.OnScrollListener() {
			boolean isLoading = false;
			@Override
			public void onScrollStateChanged(AbsListView absListView, int scrollState) {}

			@Override
			public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemsCount, int totalItemsCount) {
				boolean toAdd = firstVisibleItem + visibleItemsCount >= totalItemsCount && !isLoading;
				if (toAdd) {
					load(totalItemsCount - 1);
				}
			}

			private void load(int itemsCount) {
				isLoading = true;
				if (itemsCount > 50) {
					isLoading = false;
					progressBar.setVisibility(View.GONE);
					return;
				}
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < 20; ++i)
							adapter.addElement(names[0]);

						adapter.notifyDataSetChanged();
						isLoading = false;
					}
				}, 2000);
			}
		});
		listView.addFooterView(footer);
	}
}
