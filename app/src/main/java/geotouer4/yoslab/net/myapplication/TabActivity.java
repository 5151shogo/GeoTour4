package geotouer4.yoslab.net.myapplication;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.ArrayList;

import geotouer4.yoslab.net.myapplication.fragments.Tab1Fragment;
import geotouer4.yoslab.net.myapplication.fragments.Tab2Fragment;
import geotouer4.yoslab.net.myapplication.fragments.TabFragment;

public class TabActivity extends ActionBarActivity {

    private static ArrayList<Fragment> mTabFragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        // action bar を取得する
        ActionBar ab = getSupportActionBar();

        // ActionBarのアイコンを表示しないようにする。 true:表示/false:非表示
        ab.setDisplayShowHomeEnabled(false);
        // ActionBarのタイトル名を表示しないようにする。true:表示/false:非表示
        ab.setDisplayShowTitleEnabled(false);

        // ActionBarのNavigationModeを設定する
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // タブに対するフラグメントインスタンスを生成
        mTabFragments.add(TabFragment.newInstance("1"));
        mTabFragments.add(Tab1Fragment.newInstance("2"));
        mTabFragments.add(Tab2Fragment.newInstance("3"));

        // リスナー生成
        MainTabListener listener = new MainTabListener(this);

        // タブにリスナーを追加する
        ActionBar.Tab tab1 = ab.newTab().setText("Tab1").setTabListener(listener);
        ActionBar.Tab tab2 = ab.newTab().setText("Tab2").setTabListener(listener);
        ActionBar.Tab tab3 = ab.newTab().setText("Tab3").setTabListener(listener);

        // タブを追加する
        ab.addTab(tab1);
        ab.addTab(tab2);
        ab.addTab(tab3);

        // デフォルトの状態選択を変更する
        ab.setSelectedNavigationItem(2);

    }

    /*
     * ActionBarのタブリスナー
     */
    public static class MainTabListener implements ActionBar.TabListener {

        private final Activity activity;

        public MainTabListener(Activity activity) {
            this.activity = activity;
        }

        /*
         * 選択されているタブが再度選択された場合に実行
         *
         * @see
         * android.support.v7.app.ActionBar.TabListener#onTabReselected(android.
         * support.v7.app.ActionBar.Tab,
         * android.support.v4.app.FragmentTransaction)
         */
        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            // TODO Auto-generated method stub
            Log.d("MainActivity", "onTabReselected " + tab.getText()
                    + " : position => " + tab.getPosition());
        }

        /*
         * タブが選択された場合に実行
         *
         * @see
         * android.support.v7.app.ActionBar.TabListener#onTabSelected(android
         * .support .v7.app.ActionBar.Tab,
         * android.support.v4.app.FragmentTransaction)
         */
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            // TODO Auto-generated method stub
            Log.d("MainActivity", "onTabSelected " + tab.getText()
                    + " : position => " + tab.getPosition());
            // Fragmentの置換
            ft.replace(R.id.tab_contents, mTabFragments.get(tab.getPosition()));

        }

        /*
         * タブの選択が外れた場合に実行
         *
         * @see
         * android.support.v7.app.ActionBar.TabListener#onTabUnselected(android.
         * support.v7.app.ActionBar.Tab,
         * android.support.v4.app.FragmentTransaction)
         */
        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            // TODO Auto-generated method stub
            Log.d("MainActivity", "onTabUnselected " + tab.getText()
                    + " : position => " + tab.getPosition());

            // Fragment削除
            // ft.remove(mFragment);
        }
    }
}




//import android.app.ActionBar;
//import android.app.FragmentTransaction;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.view.Menu;
//
//import geotouer4.yoslab.net.myapplication.fragments.Tab1Fragment;
//import geotouer4.yoslab.net.myapplication.fragments.Tab2Fragment;
//import geotouer4.yoslab.net.myapplication.fragments.TabFragment;
//
//public class TabActivity extends FragmentActivity implements ActionBar.TabListener {
//
//    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tab);
//
//        // Set up the action bar.
//        final ActionBar actionBar = getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//        // For each of the sections in the app, add a tab to the action bar.
//        actionBar.addTab(actionBar.newTab().setText(R.string.title_section1).setTabListener(this));
//        actionBar.addTab(actionBar.newTab().setText(R.string.title_section2).setTabListener(this));
//        actionBar.addTab(actionBar.newTab().setText(R.string.title_section3).setTabListener(this));
//    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
//            getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_tab, menu);
//        return true;
//    }
//
//
//
//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//    }
//
//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//
//        if (tab.getPosition() == 0) {
//            TabFragment simpleListFragment = new TabFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, simpleListFragment).commit();
//        }
//        else if (tab.getPosition() == 1) {
//            Tab1Fragment androidlidt = new Tab1Fragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, androidlidt).commit();
//        }
//        else {
//            Tab2Fragment androidversionlist = new Tab2Fragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, androidversionlist).commit();
//        }
//    }
//
//    @Override
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//    }
//}


//        // ボタンオブジェクトオブジェクト取得(戻る)
//        Button button1 = (Button) findViewById(R.id.twitter_button);
//        button1.setTag("twitter");
//        button1.setOnClickListener(new ButtonClickListener());
//
//        Button button2 = (Button)findViewById(R.id.Tab_camera);
//        button2.setTag("camera");
//        button2.setOnClickListener(new ButtonClickListener());

//        // Set up the action bar.
//        final ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//
//        // Create the adapter that will return a fragment for each of the three
//        // primary sections of the activity.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//
//        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//
//        // When swiping between different sections, select the corresponding
//        // tab. We can also use ActionBar.Tab#select() to do this if we have
//        // a reference to the Tab.
//        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                actionBar.setSelectedNavigationItem(position);
//            }
//        });
//
//        // For each of the sections in the app, add a tab to the action bar.
//        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
//            // Create a tab with text corresponding to the page title defined by
//            // the adapter. Also specify this Activity object, which implements
//            // the TabListener interface, as the callback (listener) for when
//            // this tab is selected.
//            actionBar.addTab(
//                    actionBar.newTab()
//                            .setText(mSectionsPagerAdapter.getPageTitle(i))
//                            .setTabListener(this));
//        }
//    }
//
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.menu_tab, menu);
////        return true;
////    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.activity_twitter_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        // Handle action bar item clicks here. The action bar will
////        // automatically handle clicks on the Home/Up button, so long
////        // as you specify a parent activity in AndroidManifest.xml.
////        int id = item.getItemId();
////
////        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
////        switch (item.getItemId()) {
////            case R.id.menu_refresh:
////                showToast("更新しました");
////                return true;
////            case R.id.menu_tweet:
////                Intent intent = new Intent(this, TweetActivity.class);
////                startActivity(intent);
////                return true;
////        }
////        return super.onOptionsItemSelected(item);
////    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_refresh:
//                return true;
//            case R.id.menu_tweet:
//                Intent intent = new Intent(this, TweetActivity.class);
//                startActivity(intent);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    //private void showToast(String text) {
//        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//    //}
//
//
//    // クリックリスナー定義
//    class ButtonClickListener implements View.OnClickListener {
//        // onClickメソッド(ボタンクリック時イベントハンドラ)
//        @Override
//        public void onClick(View v) {
//            System.out.println("ClickListrener");
//            //Intent intent = getIntent();
//            String tag = (String) v.getTag();
//            if(tag.equals("twitter")) {
//                TwitterActivity();
//            }
//            else if(tag.equals("camera")){
//                CameraActivity();
//            }
//        }
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
//
//
//
////    private void Oauth(){
////        System.out.println("OAuth認証きた");
////        if (!TwitterUtils.hasAccessToken(this)) {
////            System.out.println("OAuth認証Activityへ飛ぶ");
////            Intent intent = new Intent(this, TwitterOAuthActivity.class);
////            startActivity(intent);
////            finish();
////        } else {
////            mAdapter = new TweetAdapter(this);
////            mTwitter = TwitterUtils.getTwitterInstance(this);
////        }
////    }
//
//
//    private void TwitterActivity() {
//        System.out.println("TabからTwitterへ");
//        if(!TwitterUtils.hasAccessToken(this)){
//            Intent intent = new Intent(TabActivity.this, TwitterOAuthActivity.class);
//            startActivity(intent);
//        }
//        else{
//            Intent intent = new Intent(TabActivity.this, TweetActivity.class);
//            startActivity(intent);
//        }
//    }
//
//    private void CameraActivity() {
//        System.out.println("TabからCameraへ");
//        Intent intent = new Intent(TabActivity.this, CameraActivity.class);
//        System.out.println("ddddd");
//        startActivity(intent);
//    }
//
//
//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//        System.out.println("onTabSelectedの場所");
//        mViewPager.setCurrentItem(tab.getPosition());
//    }
//
//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//        System.out.println("onTabUnselectedの場所");
//    }
//
//    @Override
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//        System.out.println("onTabReselectedの場所");
//    }
//
//    /**
//     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
//     * one of the sections/tabs/pages.
//     */
//    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//        public SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            // getItem is called to instantiate the fragment for the given page.
//            // Return a PlaceholderFragment (defined as a static inner class below).
////            return PlaceholderFragment.newInstance(position + 1);
//            switch (position) {
//                case 0:
//                    TabFragment simpleListFragment = new TabFragment();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container, simpleListFragment).commit();
////                    TabFragment fragment = new TabFragment();
////                    Bundle args = new Bundle();
////                    args.putInt(PlaceholderFragment.ARG_SECTION_NUMBER, position + 1);
////                    fragment.setArguments(args);
////                    return PlaceholderFragment.newInstance(position + 1);
//
//                case 1:
//                    Tab1Fragment simpleListFragment1 = new Tab1Fragment();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container, simpleListFragment1).commit();
////                    Tab1Fragment fragment2 = new Tab1Fragment();
////                    Bundle args2 = new Bundle();
////                    args2.putInt(PlaceholderFragment.ARG_SECTION_NUMBER, position + 2);
////                    fragment2.setArguments(args2);
////                    return PlaceholderFragment.newInstance(position + 2);
//
//                case 2:
//                    Tab2Fragment simpleListFragment2 = new Tab2Fragment();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container, simpleListFragment2).commit();
////                    Tab2Fragment fragment3 = new Tab2Fragment();
////                    Bundle args3 = new Bundle();
////                    args3.putInt(PlaceholderFragment.ARG_SECTION_NUMBER, position + 3);
////                    fragment3.setArguments(args3);
////                    return PlaceholderFragment.newInstance(position + 3);
//
//
//                default:
//                    return null;
//            }
//
//        }
//
//        @Override
//        public int getCount() {
//            // Show 3 total pages.
//            return 3;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            Locale l = Locale.getDefault();
//            System.out.println("position="+position);
//            switch (position) {
//                case 0:
//                    System.out.println("1番目");
//                    return getString(R.string.title_section1).toUpperCase(l);
//                case 1:
//                    System.out.println("2番目");
//                    return getString(R.string.title_section2).toUpperCase(l);
//                case 2:
//                    System.out.println("3番目");
//                    return getString(R.string.title_section3).toUpperCase(l);
//            }
//            return null;
//        }
//    }
//
//
//    public static class PlaceholderFragment extends Fragment {
//
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
////        public PlaceholderFragment() {
////        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            //if(){
//                View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
//                return rootView;
//            //}
////            else if(){
////                View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
////                return rootView;
////            }
////            else if(){
////                View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
////                return rootView;
////            }
////            else{
////                View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
////                return rootView;
////            }
//        }
//    }
//
//
//}
