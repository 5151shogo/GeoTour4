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

public class ParticipantActivity extends Activity{

	private static final String TAG = ParticipantActivity.class.getSimpleName();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_participant);
		Log.d(TAG, "ParticipantActivity start!" );

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
		
		// ボタンオブジェクトオブジェクト取得(戻る)
		Button button2 = (Button) findViewById(R.id.participant_id);
		button2.setTag("participant_go");
		button2.setOnClickListener(new ButtonClickListener());

		// ボタンオブジェクトオブジェクト取得(次へ)
		Button button3 = (Button) findViewById(R.id.participant_new_record);
		button3.setTag("new_record");
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

			} else if (tag.equals("participant_go")) {
				Log.d("test", "qqqqqqq");
				goNext();
			} else if (tag.equals("new_record")) {
				Log.d("test", "jkjjjjjjjjj");
				goNext2();
			}
		}
	}

	// アクティビティ終了(画面クローズ)
	private void finishActivity() {
		finish();
	}

	public void goNext() {

		//インテントの生成(呼び出すクラスの指定)
		Intent intent = new Intent(ParticipantActivity.this, Login_participant_Activity.class);

		// 次のアクティビティの起動
		startActivity(intent);

	}
	
	public void goNext2() {

		//インテントの生成(呼び出すクラスの指定)
		Intent intent = new Intent(ParticipantActivity.this, Participant_id_Activity.class);

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
