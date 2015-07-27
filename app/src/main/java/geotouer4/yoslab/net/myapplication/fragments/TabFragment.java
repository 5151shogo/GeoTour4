package geotouer4.yoslab.net.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import geotouer4.yoslab.net.myapplication.R;

/**
 * Created by s175029 on 2015/07/02.
 */
public class TabFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static TabFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText("Fragment #" + mPage);
        return view;
    }
}
//public class TabFragment extends Fragment {
//    /*
//    * フラグメントを作成するためのファクトリメソッド
//    */
//    public static TabFragment newInstance(String title) {
//        TabFragment fragment = new TabFragment();
//        Bundle args = new Bundle();
//       args.putString("title", title);
//
//       // フラグメントに渡す値をセット
//       fragment.setArguments(args);
//       return fragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        //レイアウトを作る
//        LinearLayout layout1 = new LinearLayout(getActivity());
//        //レイアウトは縦に並ぶ
//        layout1.setOrientation(LinearLayout.VERTICAL);
//        TextView textview = new TextView(getActivity());
//        textview.setText("ツイート内容");
//        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT);
//        textview.setLayoutParams(layout);
//        textview.setGravity(Gravity.LEFT);
//        return layout1;
//    }
//
//}