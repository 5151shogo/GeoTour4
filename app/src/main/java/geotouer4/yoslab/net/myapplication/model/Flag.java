package geotouer4.yoslab.net.myapplication.model;

import android.app.Application;

public class Flag extends Application{
    // グローバルに扱う変数
    public int transitionCount;        // 画面遷移した回数

    /**
     * 変数を初期化する
     */
    public void init(){
        transitionCount = 0;
    }
}

