package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Tab1Fragment_Activity extends Activity {

    public class Tab1Fragment extends Fragment { // (5)

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment1, container, false);
            TextView textView = (TextView) view.findViewById(R.id.tab1);
            textView.setText("メッセージ1"); // (6)
            return view;
        }
    }
}