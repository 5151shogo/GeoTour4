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
import android.widget.TextView;

public class Sign_up_Activity extends Activity{

	private static final String TAG = Sign_up_Activity.class.getSimpleName();
    SharedPreferences sp2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		Log.d(TAG, "Sign_up_Activity start!" );

        // SharedPreferenceの初期化
        sp2 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

		TextView text = (TextView)findViewById(R.id.kanryou);
		String kanryou = text.getText().toString();
		text.setText(kanryou);
		
		TextView text2 = (TextView)findViewById(R.id.kanryou2);
		String kanryou2 = text2.getText().toString();
		text2.setText(kanryou2);
		
		// ボタンオブジェクトオブジェクト取得(ログインする)
		Button button1 = (Button) findViewById(R.id.bt_login);
		button1.setTag("login");
		button1.setOnClickListener(new ButtonClickListener());
	}
	
	// クリックリスナー定義
	class ButtonClickListener implements OnClickListener {
		// onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
		public void onClick(View v) {
			//Intent intent = getIntent();
			String tag = (String) v.getTag();
			if (tag.equals("login")) {
				next();
			}
		}
	}

	void next(){
		Intent intent = new Intent(Sign_up_Activity.this,Login_Activity.class);
        System.out.println(sp2.toString());
		startActivity(intent);
		overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
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
