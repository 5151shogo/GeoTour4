package geotouer4.yoslab.net.myapplication;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import geotouer4.yoslab.net.myapplication.model.Flag;


public class TopActivity extends Activity{
	ImageView mImage;
	private static final String TAG = TopActivity.class.getSimpleName();
	private DataBaseActivity mDbHelper;
	private SQLiteDatabase db;  
	private static final String[] COLUMNS = {"_id", "name", "address", "tel"}; 
	private static final String[] TABLE_NAME = {"test"};
	public static final String COLUMN_ID = "_id";
	private Flag flag;

/**
 * 初回起動時の画面
 */
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT; 
	static final int REQUEST_CAPTURE_IMAGE = 100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top);

        //グローバル変数を扱うクラスを取得
        flag = (Flag)getApplication();
        //グローバル変数を扱うクラスの初期化
        flag.init();

		// 画像をリサイズして読み込む
		Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.background2);
		Bitmap image = ImageUtil.resizeBitmapToDisplaySize(this, src);
		ImageView imageView = new ImageView(this);
		imageView = (ImageView)findViewById(R.id.background1);
		imageView.setImageBitmap(image);
		
		
		//参加者用
		Button participant = (Button)findViewById(R.id.participant1);
		participant.setOnClickListener(new View.OnClickListener() {

			@Override//アニメーション
			public void onClick(View v) {
				Intent cacheIntent = new Intent(TopActivity.this, Map_participant_Activity.class);
				startActivity(cacheIntent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});

		//ガイド用
		Button guide = (Button)findViewById(R.id.guide1);
		guide.setOnClickListener(new View.OnClickListener() {

			@Override//アニメーション
			public void onClick(View v) {
				flag.transitionCount = 0;
				Intent cacheIntent = new Intent(TopActivity.this, GuideActivity.class);
				startActivity(cacheIntent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});

		Button weather = (Button)findViewById(R.id.weather);
		weather.setOnClickListener(new View.OnClickListener() {

			@Override//アニメーション
			public void onClick(View v) {
				flag.transitionCount = 0;
				Intent cacheIntent = new Intent(TopActivity.this, Weather_Activity.class);
				startActivity(cacheIntent);
				overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
			}
		});
		
		
		
		mkdirSD(TopActivity.this, null);
	}
	
	private void setDatabase() {
		mDbHelper = new DataBaseActivity(this);  
	    try {  
	        mDbHelper.createEmptyDataBase();  
	        db = mDbHelper.openDataBase();  
	    } catch (IOException ioe) {  
	        throw new Error("Unable to create database");  
	    } catch(SQLException sqle){  
	        throw sqle;  
	    }  
	}
	/**
	 * ディレクトリがなければ作成するよ．
	 * @param c context
	 */
	public static void mkdirSD(Context c, String dirName){
		// SD カード/パッケージ名 ディレクトリ生成
		File outDir = new File(Environment.getExternalStorageDirectory(), "GeoTour");
		
		// パッケージ名のディレクトリが SD カードになければ作成します。
		if (outDir.exists() == false) {
			outDir.mkdir();
		}
		
		if(dirName != null){
			outDir = new File(outDir, dirName);
			if (outDir.exists() == false) {
				outDir.mkdir();
			}
		}
	}
}
