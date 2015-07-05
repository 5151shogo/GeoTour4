package geotouer4.yoslab.net.myapplication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import geotouer4.yoslab.net.myapplication.R;

/**
 * Created by s175029 on 2015/06/29.
 */
public class Tab2Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 第３引数のbooleanは"container"にreturnするViewを追加するかどうか
        //trueにすると最終的なlayoutに再度、同じView groupが表示されてしまうのでfalseでOKらしい
        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }
}