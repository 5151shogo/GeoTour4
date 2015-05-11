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
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import geotouer4.yoslab.net.myapplication.adapters.SpotAdapter;
import geotouer4.yoslab.net.myapplication.model.Spot;

public class Login_participant_Activity extends Activity{



	private static final String TAG = Login_participant_Activity.class.getSimpleName();
    private RequestQueue myQueue; // Volleyで通信を行うための変数
    private ListView mListView;
    public SpotAdapter mSpotAdapter;
    public ArrayList<Spot> spots;
    int roots_length;
    SharedPreferences sp;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_participant);
        Log.d(TAG, "Login_participant_Activity start!");

        // SharedPreferenceの初期化
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


        TextView text = (TextView) findViewById(R.id.st_login_participant);
        String login_id = text.getText().toString();
        text.setText(login_id);

        TextView text2 = (TextView) findViewById(R.id.st_login2);
        String login_id2 = text2.getText().toString();
        Log.d("Tag", "login_id2=" + login_id2);
        text2.setText(login_id2);

        Log.d(TAG, "Login_participant_Activity start!");

        // ボタンオブジェクトオブジェクト取得(Top画面へ戻る)
        Button button1 = (Button) findViewById(R.id.bt_back);
        button1.setTag("back");
        button1.setOnClickListener(new ButtonClickListener());

        // ボタンオブジェクトオブジェクト取得(登録)
        Button button2 = (Button) findViewById(R.id.bt_go);
        button2.setTag("go");
        button2.setOnClickListener(new ButtonClickListener());


//        myQueue = Volley.newRequestQueue(this);
//        // TODO: 調査．tryの中にArrayList<Spot> _spots = new ArrayList<Spot>();　を書かずに正しいリストを表示させる．
//        String uri = "http://www2.yoslab.net/~taniguchi/api/select_spot_participant.php";
//        myQueue.add(new JsonObjectRequest(Request.Method.GET, uri, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray json_spots = response.getJSONArray("spot");//spot配列の中身がココへだーっと返ってくる
//                            roots_length = json_spots.length();//spots配列の要素がいくら入っているか
//                            for (int i = 0; i < roots_length; i++) {
//
//                                Spot spot = new Spot();
//                                ArrayList<Spot> _spots = new ArrayList<Spot>();
//
//                                JSONObject json_spot = json_spots.getJSONObject(i);//配列中身を0から順にjson_spotへ入れる
//
//                                String spotName = json_spot.getString("name");
//                                float lati = json_spot.getfloat("latitude");
//                                float longi = json_spot.getfloat("longitude");
//                                spot.setName(spotName);
//                                _spots.add(spot); // 配列に要素を追加
//                                mSpotAdapter.addAll(_spots); // 配列をアダプターにセット
//
//                            }
//
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
        EditText mEditText = (EditText)findViewById(R.id.editText1);
        String userId = mEditText.getText().toString();
        sp.edit().putString("user_id", userId).commit();

		Intent intent = new Intent(Login_participant_Activity.this,Map_participant_Activity.class);
        intent.putExtra("ID",IdList.idLists);
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
