package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import geotouer4.yoslab.net.myapplication.Utils.ImageUtil;

public class GuideActivity extends Activity{

	private static final String TAG = GuideActivity.class.getSimpleName();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		Log.d(TAG, "GideActivity start!" );
		
		// 画像をリサイズして読み込む
		Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.background2);
		Bitmap image2 = ImageUtil.resizeBitmapToDisplaySize(this, src);
		ImageView imageView = new ImageView(this);
		imageView = (ImageView)findViewById(R.id.background2);
		imageView.setImageBitmap(image2);
		
		// ボタンオブジェクトオブジェクト取得(戻る)
		Button button1 = (Button) findViewById(R.id.bt_back);
		button1.setTag("back1");
		button1.setOnClickListener(new ButtonClickListener());
		
		// ボタンオブジェクトオブジェクト取得(IDを入力する)
		Button button2 = (Button) findViewById(R.id.guide1);
		button2.setTag("guide_go");
		button2.setOnClickListener(new ButtonClickListener());

		// ボタンオブジェクトオブジェクト取得(新規登録)
		Button button3 = (Button) findViewById(R.id.participant1);
		button3.setTag("login");
		button3.setOnClickListener(new ButtonClickListener());
	}

	// クリックリスナー定義
	class ButtonClickListener implements OnClickListener {
		// onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
		public void onClick(View v) {
			//Intent intent = getIntent();
			String tag = (String) v.getTag();
			if (tag.equals("back1")) {
				finishActivity();

			} else if (tag.equals("guide_go")) {
				Log.d("test", "pppppp");
				new_recode();
			} else if (tag.equals("login")) {
				Log.d("test", "uuuuuuuuu");
				login();
			}
		}
	}

	// アクティビティ終了(画面クローズ)
	private void finishActivity() {
		finish();
	}

	public void new_recode() {

		//インテントの生成(呼び出すクラスの指定)
		Intent intent = new Intent(GuideActivity.this, Guide_id_Activity.class);
		Log.d("test","qqqqqqq");
		// 次のアクティビティの起動
		startActivity(intent);
		overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
	}
	
	public void login() {

		//インテントの生成(呼び出すクラスの指定)
		Intent intent = new Intent(GuideActivity.this, Login_Activity.class);

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
