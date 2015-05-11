package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login_Activity extends Activity{



	private static final String TAG = Login_Activity.class.getSimpleName();
    SharedPreferences sp;
    public EditText edit1;
    private static final String PREF_KEY = "preferenceTest";
    private static final String KEY_TEXT = "text";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Log.d(TAG, "Login_Activity start!" );

        // SharedPreferenceの初期化
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


//        edit1 = (EditText)findViewById(R.id.editText10);
//        TextView textView;
//        textView = (TextView) findViewById(TextView);
//        // SharedPreferences よりデータを取得
//        textView.setText(spr.getString("text", "No Data"));

	
        TextView text = (TextView)findViewById(R.id.st_login);
        String login_id = text.getText().toString();
        text.setText(login_id);

        Log.d(TAG, "Login_Activity start2!" );

        TextView text2 = (TextView)findViewById(R.id.st_login2);
        String login_id2 = text2.getText().toString();
        Log.d("Tag","login_id2="+login_id2);
        text2.setText(login_id2);

        Log.d(TAG, "Login_Activity start3!" );

        // ボタンオブジェクトオブジェクト取得(Top画面へ戻る)
        Button button1 = (Button) findViewById(R.id.bt_back);
        button1.setTag("back");
        button1.setOnClickListener(new ButtonClickListener());

        // ボタンオブジェクトオブジェクト取得(登録)
        Button button2 = (Button) findViewById(R.id.bt_go);
        button2.setTag("go");
        button2.setOnClickListener(new ButtonClickListener());
        }
	
	// クリックリスナー定義
	class ButtonClickListener implements OnClickListener {
		// onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
		public void onClick(View v) {
			//Intent intent = getIntent();
			String tag = (String) v.getTag();
			if (tag.equals("back")) {
                finish();
			}
			else if(tag.equals("go")){
				go();
			}
		}
	}

	void go(){

        // ユーザIDをSharedPreferenceに保存
        EditText mEditText = (EditText)findViewById(R.id.editText10);
        String guideId = mEditText.getText().toString();
        sp.edit().putString("guide_id", guideId).commit();

        // 保存できてるか確認
        System.out.println(sp.getString("guide_id", "Guest"));

		Intent intent = new Intent(Login_Activity.this,SelectActivity.class);
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
