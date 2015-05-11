package geotouer4.yoslab.net.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class SelectActivity extends Activity{

	private static final String TAG = SelectActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);
		Log.d(TAG, "SelectActivity start!" );
		
		// ボタンオブジェクトオブジェクト取得(戻る)
		Button button1 = (Button) findViewById(R.id.bt_back);
		button1.setTag("back1");
		button1.setOnClickListener(new ButtonClickListener());

		// ボタンオブジェクトオブジェクト取得(次へ)
		Button button2 = (Button) findViewById(R.id.bt_go);
		button2.setTag("go1");
		button2.setOnClickListener(new ButtonClickListener());



        //sharedpreferenceの取得がうまくいかん！
//        TextView textView;
//        textView = (TextView) findViewById(R.id.share);
//        EditText edit1 = (EditText)findViewById(R.id.editText10);
//        SharedPreferences spr = PreferenceManager.getDefaultSharedPreferences(this);
//        // SharedPreferences よりデータを取得
//        textView.setText(spr.getString("KEY_TEXT",edit1.getText().toString()));


	}

	// クリックリスナー定義
	class ButtonClickListener implements OnClickListener {
		// onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
		public void onClick(View v) {
			//Intent intent = getIntent();
			String tag = (String) v.getTag();
			if (tag.equals("back1")) {
                System.out.println("back1");
				finishActivity();

			} else if (tag.equals("go1")) {
				Log.d("test", "pppppp");
				goNext();
			}
		}
	}

	// アクティビティ終了(画面クローズ)
	private void finishActivity() {
		finish();
	}

	public void goNext() {

       int id = 0;

		Spinner AREA1 = (Spinner)findViewById(R.id.sp_place);

		//選択された値を取得
		String selected = (String)AREA1.getSelectedItem();

		Log.e("test","selected="+selected);

            if(selected.equals("上富田町")){
                id=1;
            }
            else if(selected.equals("北山村")) {
                id=2;
            }
            else if(selected.equals("串本町")) {
                id=3;
            }
            else if(selected.equals("古座川町")) {
                id=4;
            }
            else if(selected.equals("新宮市")) {
                id=5;
            }
            else if(selected.equals("白浜町")) {
                id=6;
            }
            else if(selected.equals("すさみ町")) {
                id=7;
            }
            else if(selected.equals("太地町")) {
                id=8;
            }
            else if(selected.equals("那智勝浦町")) {
                id=9;
            }

		//インテントの生成(呼び出すクラスの指定)
		Intent intent = new Intent(SelectActivity.this, Select2Activity.class);

		//入力データをインテントに指定
        intent.putExtra("int_ID", id);

        System.out.println("int_ID="+id);

		// 次のアクティビティの起動
		startActivity(intent);

	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
	}
}
