//package geotouer4.yoslab.net.myapplication;
//
//import android.app.Activity;
//import android.os.Bundle;
//
//import org.json.JSONObject;
//
//import main.java.com.android.volley.Request;
//import main.java.com.android.volley.RequestQueue;
//import main.java.com.android.volley.Response;
//import main.java.com.android.volley.VolleyError;
//import main.java.com.android.volley.toolbox.JsonObjectRequest;
//import main.java.com.android.volley.toolbox.Volley;
//
//public class Test extends Activity {
//
//	// ログ出力時のタグ名
//	private static final String TAG_LOG = "Log";
//	// Volleyでリクエスト時に設定するタグ名。キャンセル時に利用する。
//	private static final Object TAG_REQUEST_QUEUE = new Object();
//	// リクエスト先
//	private String uri = "http://yokohamarally.prodrb.com/api/get_root_by_id.php?id=1";
//	// RequestQueueのインスタンス用
//	private RequestQueue mRequestQueue;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_test);
//
//		mRequestQueue = Volley.newRequestQueue(this);
//
//		mRequestQueue.add(new JsonObjectRequest(Request.Method.GET, uri, null,
//				new Response.Listener<JSONObject>() {
//					@Override
//					public void onResponse(JSONObject response) {
//						try {
//
//							// adapterに反映、追加
//						} catch (Exception e) {
//						}
//
//					}
//
//				}, new Response.ErrorListener() {
//					@Override
//					public void onErrorResponse(VolleyError error) {
//						// エラー時の処理
//						System.out.println(error);
//					}
//
//				}));
//	}
//
//	private RequestQueue newRequestQueue(Test test) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
