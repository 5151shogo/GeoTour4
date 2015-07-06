package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.Locale;

import geotouer4.yoslab.net.myapplication.Utils.TwitterUtils;
import geotouer4.yoslab.net.myapplication.fragments.Tab1Fragment;
import twitter4j.Status;
import twitter4j.Twitter;



public class TabActivity extends ActionBarActivity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private TweetAdapter mAdapter;
    private Twitter mTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        System.out.println("onCreateの場所");

        // ボタンオブジェクトオブジェクト取得(戻る)
        Button button1 = (Button) findViewById(R.id.twitter_button);
        button1.setTag("twitter");
        button1.setOnClickListener(new ButtonClickListener());

        Button button2 = (Button)findViewById(R.id.Tab_camera);
        button2.setTag("camera");
        button2.setOnClickListener(new ButtonClickListener());

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_tab, menu);
//        return true;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_twitter_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        switch (item.getItemId()) {
//            case R.id.menu_refresh:
//                showToast("更新しました");
//                return true;
//            case R.id.menu_tweet:
//                Intent intent = new Intent(this, TweetActivity.class);
//                startActivity(intent);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                return true;
            case R.id.menu_tweet:
                Intent intent = new Intent(this, TweetActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //private void showToast(String text) {
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    //}


    // クリックリスナー定義
    class ButtonClickListener implements View.OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        public void onClick(View v) {
            System.out.println("ClickListrener");
            //Intent intent = getIntent();
            String tag = (String) v.getTag();
            if(tag.equals("twitter")) {
                TwitterActivity();
            }
            else if(tag.equals("camera")){
                CameraActivity();
            }
        }
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
            TextView name = (TextView) convertView.findViewById(R.id.twitter_name);
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



//    private void Oauth(){
//        System.out.println("OAuth認証きた");
//        if (!TwitterUtils.hasAccessToken(this)) {
//            System.out.println("OAuth認証Activityへ飛ぶ");
//            Intent intent = new Intent(this, TwitterOAuthActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            mAdapter = new TweetAdapter(this);
//            mTwitter = TwitterUtils.getTwitterInstance(this);
//        }
//    }


    private void TwitterActivity() {
        System.out.println("TabからTwitterへ");
        if(!TwitterUtils.hasAccessToken(this)){
            Intent intent = new Intent(TabActivity.this, TwitterOAuthActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(TabActivity.this, TweetActivity.class);
            startActivity(intent);
        }
    }

    private void CameraActivity() {
        System.out.println("TabからCameraへ");
        Intent intent = new Intent(TabActivity.this, CameraActivity.class);
        System.out.println("ddddd");
        startActivity(intent);
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        System.out.println("onTabSelectedの場所");
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        System.out.println("onTabUnselectedの場所");
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        System.out.println("onTabReselectedの場所");
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Tab1Fragment fragment = new Tab1Fragment();
            return fragment;
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
//            return PlaceholderFragment.newInstance(position + 1);

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            System.out.println("position="+position);
            switch (position) {
                case 0:
                    System.out.println("1番目");
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    System.out.println("2番目");
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    System.out.println("3番目");
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

//        public PlaceholderFragment() {
//        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //if(){
                View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
                return rootView;
            //}
//            else if(){
//                View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
//                return rootView;
//            }
//            else if(){
//                View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
//                return rootView;
//            }
//            else{
//                View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
//                return rootView;
//            }
        }
    }


}
