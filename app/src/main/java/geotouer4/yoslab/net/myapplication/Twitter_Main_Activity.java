package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

import java.util.List;

import geotouer4.yoslab.net.myapplication.Utils.TwitterUtils;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Twitter_Main_Activity extends ListActivity {

	private TweetAdapter mAdapter;
	private Twitter mTwitter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentViewより前にWindowにActionBar表示を設定
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		Oauth();
	}

	private void Oauth(){
		mAdapter = new TweetAdapter(this);
		setListAdapter(mAdapter);

		mTwitter = TwitterUtils.getTwitterInstance(this);
		reloadTimeLine();
	}


	private void reloadTimeLine() {
		AsyncTask<Void, Void, List<Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				try {
					return mTwitter.getHomeTimeline();
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<twitter4j.Status> result) {
				if (result != null) {
					mAdapter.clear();
					for (twitter4j.Status status : result) {
						mAdapter.add(status);
					}
					getListView().setSelection(0);
				} else {
					showToast("タイムラインの取得に失敗しました。。。");
				}
			}
		};
		task.execute();
	}


	private void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_twitter_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh:
				reloadTimeLine();
				return true;
			case R.id.menu_tweet:
				Intent intent = new Intent(this, TweetActivity.class);
				startActivity(intent);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}



	private class TweetAdapter extends ArrayAdapter<Status> {

		private LayoutInflater mInflater;

		public TweetAdapter(Context context) {
			super(context, android.R.layout.simple_list_item_1);
			mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_tweet, null);
			}
			Status item = getItem(position);
			TextView name = (TextView) convertView.findViewById(R.id.name);
			name.setText(item.getUser().getName());
			TextView screenName = (TextView) convertView.findViewById(R.id.screen_name);
			screenName.setText("@" + item.getUser().getScreenName());
			TextView text = (TextView) convertView.findViewById(R.id.text);
			text.setText(item.getText());
			SmartImageView icon = (SmartImageView)convertView.findViewById(R.id.icon);
			icon.setImageUrl(item.getUser().getProfileImageURL());
			return convertView;
		}
	}
}