package geotouer4.yoslab.net.myapplication.adapters;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import geotouer4.yoslab.net.myapplication.fragments.Tab1Fragment;
import geotouer4.yoslab.net.myapplication.fragments.TabFragment;

/**
 * Created by s175029 on 2015/07/23.
 */
public class TopPageAdapter extends FragmentPagerAdapter {


    public TopPageAdapter(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new TabFragment();
                break;
            case 1:
                fragment = new Tab1Fragment();
                break;
            default:
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "one";
            case 1:
                return "two";
            default:
                return "three";
        }
    }


    @Override
    public int getCount() {
        return 2;
    }

}
