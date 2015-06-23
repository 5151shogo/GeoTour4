package geotouer4.yoslab.net.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.os.Bundle;
import android.app.Fragment;

public class Main_Activity extends Activity {

    public class MainActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            final ActionBar actionBar = getActionBar();

            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.addTab(actionBar
                    .newTab()
                    .setText("TAB 1").setTabListener(
                                    new MainTabListener<Fragment1_Activity>
                                            (this,"f1",Fragment1_Activity.class
                                            )
                            )
            );

            actionBar.addTab(actionBar
                    .newTab()
                    .setText("TAB 2").setTabListener(new MainTabListener<Fragment2_Activity>
                                            (this,"f2",Fragment2_Activity.class)
                            )
            );
        }

        public class MainTabListener<T extends Fragment>
                implements ActionBar.TabListener {
            private Fragment fragment;
            private final Activity activity;
            private final String tag;
            private final Class<T> cls;
            public MainTabListener(Activity activity,
                                   String tag,
                                   Class<T> cls){
                this.activity = activity; this.tag = tag;
                this.cls = cls;
            }

            @Override public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

                if(fragment == null){
                    fragment = Fragment.instantiate(activity, cls.getName());
                    ft.add(android.R.id.content, fragment, tag);
                }
                else{
                    ft.attach(fragment);
                }
            }

            @Override public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                if(fragment != null){
                    ft.detach(fragment);
                }
            }
        }
    }
}