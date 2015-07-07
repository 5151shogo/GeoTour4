package geotouer4.yoslab.net.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by s175029 on 2015/07/02.
 */
public class TabFragment extends Fragment {

    /*
     * フラグメントを作成するためのファクトリメソッド
     */
    public static TabFragment newInstance(String title) {
        TabFragment fragment = new TabFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        // フラグメントに渡す値をセット
        fragment.setArguments(args);

        return fragment;
    }

    /**
     *
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     *      android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TextViewを生成する
        TextView textView = new TextView(getActivity());
        // 幅、高さの設定
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(layout);
        // 文字の位置の設定
        textView.setGravity(Gravity.CENTER);
        // 文字を設定
        textView.setText(getArguments().getString("title"));
        // フォントサイズ変更
        textView.setTextSize(180.0f);

        //
        return textView;
    }
}

