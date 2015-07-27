package geotouer4.yoslab.net.myapplication.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

import java.util.List;

import geotouer4.yoslab.net.myapplication.Detail_participant_Activity;
import geotouer4.yoslab.net.myapplication.R;
import geotouer4.yoslab.net.myapplication.TabActivity;
import geotouer4.yoslab.net.myapplication.Twitter_Main_Activity;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;




    public class Tab1Fragment extends ListFragment {
        public static final String ARG_PAGE = "ARG_PAGE";


        private int mPage;

//        public static Tab1Fragment newInstance(int page) {
//            Bundle args = new Bundle();
//            args.putInt(ARG_PAGE, page);
//            Tab1Fragment fragment = new Tab1Fragment();
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            mPage = getArguments().getInt(ARG_PAGE);
//        }


        public TabActivity parent;
        public Twitter mTwitter;
        public TweetAdapter mAdapter;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        // 第３引数のbooleanは"container"にreturnするViewを追加するかどうか
//        //trueにすると最終的なlayoutに再度、同じView groupが表示されてしまうのでfalseでOKらしい
//        return inflater.inflate(R.layout.fragment_tab1, container, false);
//    }


        //@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_tab1, container, false);
//        list_tweet = (ListView) view.findViewById(R.id.twitter_list);
//        list_tweet.setAdapter(mAdapter);
            TL_watch();


            // 第３引数のbooleanは"container"にreturnするViewを追加するかどうか
            Button btnMove = (Button) view.findViewById(R.id.twitter_button2);
            btnMove.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Twitter_Main_Activity.class);
                    startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public void onAttach(Activity activity) {
            parent = (TabActivity) activity;
            super.onAttach(activity);
        }

        public void TL_watch() {
            reloadTimeLine();
            // ここでタップされた時のリスナー登録
            System.out.println("おーい");
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
                        System.out.println("タイムライン取得失敗");
                        //showToast("タイムラインの取得に失敗しました。。。");
                    }
                }
            };
            task.execute();
        }


//    private void showToast(String text) {
//        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.activity_twitter_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_refresh:
                    //reloadTimeLine();
                    return true;
                case R.id.menu_tweet:
                    //Intent intent = new Intent(this, TweetActivity.class);
                    //startActivity(intent);
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
                SmartImageView icon = (SmartImageView) convertView.findViewById(R.id.icon);
                icon.setImageUrl(item.getUser().getProfileImageURL());
                return convertView;
            }
        }
    }
//}



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
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.activity_twitter_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_refresh:
////                reloadTimeLine();
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
//            TextView name = (TextView) convertView.findViewById(R.id.name);
//            name.setText(item.getUser().getName());
//            TextView screenName = (TextView) convertView.findViewById(R.id.screen_name);
//            screenName.setText("@" + item.getUser().getScreenName());
//            TextView text = (TextView) convertView.findViewById(R.id.text);
//            text.setText(item.getText());
//            SmartImageView icon = (SmartImageView) convertView.findViewById(R.id.icon);
//            icon.setImageUrl(item.getUser().getProfileImageURL());
//            return convertView;
//        }
//    }
//}
//}