package geotouer4.yoslab.net.myapplication.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import geotouer4.yoslab.net.myapplication.R;
import geotouer4.yoslab.net.myapplication.TabActivity;

public class Tab1Fragment extends Fragment {

//    //private TweetAdapter mAdapter;
//    private Twitter mTwitter;
    private TabActivity parent;
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        // 第３引数のbooleanは"container"にreturnするViewを追加するかどうか
        Button btnMove = (Button) view.findViewById(R.id.twitter_button2);
        btnMove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                parent.move();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        parent = (TabActivity) activity;
        super.onAttach(activity);
    }
}
//
//
//
//
//    private void reloadTimeLine() {
//        AsyncTask<Void, Void, List<Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
//            @Override
//            protected List<twitter4j.Status> doInBackground(Void... params) {
//                try {
//                    return mTwitter.getHomeTimeline();
//                } catch (TwitterException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(List<twitter4j.Status> result) {
//                if (result != null) {
//                    mAdapter.clear();
//                    for (twitter4j.Status status : result) {
//                        mAdapter.add(status);
//                    }
//                    //getListView().setSelection(0);
//                } else {
//                    //showToast("タイムラインの取得に失敗しました。。。");
//                }
//            }
//        };
//        task.execute();
//    }
//
//
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        MenuInflater inflater = getMenuInflater();
////        inflater.inflate(R.menu.activity_twitter_main, menu);
////        return super.onCreateOptionsMenu(menu);
////    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_refresh:
//                reloadTimeLine();
//                return true;
//            case R.id.menu_tweet:
////                Intent intent = new Intent(this, TweetActivity.class);
////                startActivity(intent);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//
//
//    private class TweetAdapter extends ArrayAdapter<Status> {
//
//        private LayoutInflater mInflater;
//
//        public TweetAdapter(Context context) {
//            super(context, android.R.layout.simple_list_item_1);
//            mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                convertView = mInflater.inflate(R.layout.list_item_tweet, null);
//            }
//            Status item = getItem(position);
//            TextView name = (TextView) convertView.findViewById(R.id.twitter_name);
//            name.setText(item.getUser().getName());
//            TextView screenName = (TextView) convertView.findViewById(R.id.screen_name);
//            screenName.setText("@" + item.getUser().getScreenName());
//            TextView text = (TextView) convertView.findViewById(R.id.text);
//            text.setText(item.getText());
//            SmartImageView icon = (SmartImageView)convertView.findViewById(R.id.icon);
//            icon.setImageUrl(item.getUser().getProfileImageURL());
//            return convertView;
//        }
//    }
//}