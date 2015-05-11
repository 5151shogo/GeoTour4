package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;


public class Guide_id_Activity extends Activity{

	private static final String TAG = Guide_id_Activity.class.getSimpleName();
	HttpURLConnection urlCon = null; // httpのコネクションを管理するクラス
    private RequestQueue myQueue; // Volleyで通信を行うための変数
    private String user_name;
    private String user_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide_id);
		Log.d(TAG, "Guide_id_Activity start!" );
		
		TextView text = (TextView)findViewById(R.id.touroku);
		String touroku2 = text.getText().toString();
		text.setText(touroku2);
		
		TextView text2 = (TextView)findViewById(R.id.name_1);
		String name = text2.getText().toString();
		Log.d("Tag","name="+text2);
		text2.setText(name);

		
//		TextView text3 = (TextView)findViewById(R.id.sex);
//		String sex = text3.getText().toString();
//		text3.setText(sex);
		
		TextView text4 = (TextView)findViewById(R.id.user_ID);
		String user_ID = text4.getText().toString();
		text4.setText(user_ID);
		
		


		
		// ボタンオブジェクトオブジェクト取得(戻る)
		Button button1 = (Button) findViewById(R.id.bt_back);
		button1.setTag("back1");
		button1.setOnClickListener(new ButtonClickListener());
		
		// ボタンオブジェクトオブジェクト取得(登録)
		Button button2 = (Button) findViewById(R.id.bt_tou);
		button2.setTag("next");
		button2.setOnClickListener(new ButtonClickListener());
		
		
	}

	// クリックリスナー定義
	class ButtonClickListener implements OnClickListener {
		// onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
		public void onClick(View v) {
			//Intent intent = getIntent();
			String tag = (String) v.getTag();
			if (tag.equals("back1")) {
				finish();
			}
			else if(tag.equals("next")){
				next();
			}
		}
	}
			
	void next(){
		new AlertDialog.Builder(Guide_id_Activity.this)
        .setTitle("登録しますか？")
        .setPositiveButton(
          "Yes", 
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {  
            	next2();
            }
          })
        .setNegativeButton(
          "No", 
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {  
            }
        })
        .show();
	}
	
	void next2(){
		
		//名前取得
		EditText edit = (EditText)findViewById(R.id.editText1);
		SpannableStringBuilder sp = (SpannableStringBuilder)edit.getText();
		Log.d("onCreate", sp.toString());

		//ID取得
		EditText edit2 = (EditText)findViewById(R.id.editText2);
		SpannableStringBuilder sp2 = (SpannableStringBuilder)edit2.getText();
		Log.d("onCreate", sp2.toString());

        myQueue = Volley.newRequestQueue(this);
		String url = "http://www2.yoslab.net/~taniguchi/index.php";
		user_name = sp.toString();
		user_id = sp2.toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("false")) {

                        } else {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                // POSTするパラメーター

                params.put("user_name", user_name);
                params.put("user_id",user_id);
                return params;
            }
        };
        myQueue.add(postRequest);
        myQueue.start();


//        //TODO:Volley送るだけのとききれいに(日本語も文字化けする)
//
//                myQueue.add(new JsonObjectRequest(Request.Method.POST, net, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            System.out.println("SUCCESS!!!");
//                        } catch (Exception e) {
//                            System.out.println(e);
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //エラー時の処理
//                        System.out.println(error);
//                    }
//                }));


		next3();
	}
	
	void next3(){
		
		//インテントの生成(呼び出すクラスの指定)
		Intent intent = new Intent(Guide_id_Activity.this, Sign_up_Activity.class);
		// 次のアクティビティの起動
		startActivity(intent);
		//アニメーション
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
