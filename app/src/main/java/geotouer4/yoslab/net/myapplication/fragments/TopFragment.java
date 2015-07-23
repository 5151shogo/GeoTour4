package geotouer4.yoslab.net.myapplication.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import geotouer4.yoslab.net.myapplication.Detail_participant_Activity;
import geotouer4.yoslab.net.myapplication.PagerSlidingTabStrip;
import geotouer4.yoslab.net.myapplication.R;
import geotouer4.yoslab.net.myapplication.TabActivity;
import geotouer4.yoslab.net.myapplication.adapters.TopPageAdapter;
import twitter4j.Twitter;

public class TopFragment extends Fragment {
//
//    @InjectView(R.id.top_game_page_tabs)
    PagerSlidingTabStrip mTabs;

//    @InjectView(R.id.top_game_view_pager)
    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, null);
//        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewPager.setAdapter(new TopPageAdapter(getResources(), getChildFragmentManager()));
        mTabs.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

}