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


public class Participant_id_Activity extends Activity{

	private static final String TAG = Participant_id_Activity.class.getSimpleName();
	HttpURLConnection urlCon = null; // httpのコネクションを管理するクラス
    private RequestQueue myQueue; // Volleyで通信を行うための変数
    String user_name;
    String user_id;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_participant_id);
		Log.d(TAG, "Participant_id_Activity start!" );
		
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
		new AlertDialog.Builder(Participant_id_Activity.this)
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








//		String response = net(net);
//		System.out.println(response);
		/*
		Uri uri1 = Uri.parse(net);
		Intent intent = new Intent(Intent.ACTION_VIEW,uri1);
		startActivity(intent);
		*/
		
		
		
		
//		try{
//			DefaultHttpClient httpClient = new DefaultHttpClient();
//		
//		HttpGet get = new HttpGet(net);
//		HttpResponse res = httpClient.execute(get);
//		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//		
//		int status = res.getStatusLine().getStatusCode();
//		Log.d("Tag","status="+status);
//			
//		
//		
//		
//		
//		}
//		catch(ClientProtocolException e){
//			e.printStackTrace();
//		}
//		catch(IOException e){
//			e.printStackTrace();
//		}
		
		
//		try {
//			// httpコネクションを確立し、urlを叩いて情報を取得
//			URL url3 = new URL(net);
//			urlCon = (HttpURLConnection)url3.openConnection();
//			urlCon.setRequestMethod("GET");
//			urlCon.connect();
			

//			// データを取得
//			in = urlCon.getInputStream();
//			
//			// InputStreamから取得したbyteデータを文字列にして保持するための変数
//			String str = new String();
//			// InputStreamからbyteデータを取得するための変数
//			byte[] buf = new byte[BUF_SIZE];
//			
//			// InputStreamからのデータを文字列として取得する
//			while(true) {
//				if( in.read(buf) <= 0)break;
//				str += new String(buf);
//				}
//			
//			// 結果をテキストビューに設定
//			resultTv.setText(str);
//			}
//		
//			catch (IOException ioe ){
//				ioe.printStackTrace();
//				Toast.makeText(this, "IOExceptionが発生しました。", Toast.LENGTH_SHORT).show();
//			}
//			catch(NetworkOnMainThreadException){
//				Toast.makeText(this, "NetworkOnMainThreadExceptionが発生しました。", Toast.LENGTH_SHORT).show();
//			}
//			finally {
//				try {
//					urlCon.disconnect();
//					in.close();
//				}
//					catch (IOException ioe ) {
//						ioe.printStackTrace();
//					}
			
		
		
		
		
		
		
		
		
		
		// ネットワーク接続チェック
//	    ConnectivityManager cm = (ConnectivityManager)getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//	    if ( ! HttpConnector.isConnected(cm) ) {
//	    	new AlertDialog.Builder(getActivity())
//	        .setMessage("インターネットに接続できませんでした。ネットワーク接続状況を確認してください。")
//	        .setPositiveButton("OK", null)
//	        .show();
//	    	return;
//	    }

	    // サーバーにリクエスト送信
//	    HttpConnector.RequestInfo requestInfo = new HttpConnector.RequestInfo();
//	    requestInfo.url = "https://example.com/sample.php";
//	    requestInfo.params.add(new HttpConnector.Param(HttpConnector.Param.TYPE_STRING, "key_param1", "value_param1"));
//	    requestInfo.params.add(new HttpConnector.Param(HttpConnector.Param.TYPE_STRING, "key_param2", "value_param2"));
//	    requestInfo.asyncCallback = new AsyncCallback() {     // コールバックメソッド
//	        @Override
//	        public void onGetExecute(InputStream responseIS) {
//	            BufferedReader reader = new BufferedReader(new InputStreamReader(responseIS));
//	            StringBuilder buf = new StringBuilder();
//	            String line;
//	            try {
//	                while ( (line = reader.readLine()) != null ) {
//	                    buf.append(line);
//	                }
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	                return;
//	            }
//	            String responseStr = buf.toString();
//
//	            JSONObject jsonObj = null;
//	            try {
//	                jsonObj = new JSONObject(response);
//	            } catch (JSONException e) {
//	                e.printStackTrace();
//	                return;
//	            }
//	            }
//	    };
//	    HttpConnector.Request(getActivity(), requestInfo);
		
		
//		private final String URL_API = net;
//		
//		// HTTPリクエスト管理Queueを生成
//		mQueue = Volley.newRequestQueue(this);
//		// リクエスト実行
//		mQueue.add(new JsonObjectRequest(Method.GET, URL_API, null, new Listener<JSONObject>() {
//			@Override
//			public void onResponse(JSONObject response) {
//				// --------------------------------------------
//				// JSONObjectのパース、List、Viewへの追加等
//				// --------------------------------------------
//				// ログ出力
//				Log.d("temakishiki", "response : " + response.toString());
//
//				try {
//					// 地区名を取得し、設定
//					String title = response.getString("title");
//					txtArea.setText(title);
//
//					// 予報情報の一覧を取得
//					JSONArray forecasts = response.getJSONArray("forecasts");
//					for (int i = 0; i < forecasts.length(); i++) {
//						// 予報情報を取得
//						JSONObject forecast = forecasts.getJSONObject(i);
//						// 日付
//						String date = forecast.getString("date");
//						// 予報
//						String telop = forecast.getString("telop");
//						
//						// リストビューに設定
//						adapter.add(date + ":" + telop);
//					}
//				} catch (JSONException e) {
//					Log.e("temakishiki", e.getMessage());
//				}
//			}
//		})); //new Response.ErrorListener() {
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				// --------------------------------------------
//				// エラー処理 error.networkResponseで確認
//				// --------------------------------------------
//				if (error.networkResponse != null) {
//					Log.e("temakishiki", "エラー : " + error.networkResponse.toString());
//				}
//			}
//		}));

	
		
		
//		AsyncHttpClient httpClient = new AsyncHttpClient();
//		
//		httpClient.prepareGet(net).execute(new MyAsyncCompletionHandler());
		
		
		
		next3();
		
		
	}
	
	void next3(){
		
		//インテントの生成(呼び出すクラスの指定)
		Intent intent = new Intent(Participant_id_Activity.this, Sign_up_participant_Activity.class);
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
