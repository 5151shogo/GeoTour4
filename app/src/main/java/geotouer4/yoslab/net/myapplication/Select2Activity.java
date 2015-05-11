package geotouer4.yoslab.net.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import geotouer4.yoslab.net.myapplication.adapters.SpotAdapter;
import geotouer4.yoslab.net.myapplication.model.Spot;

public class Select2Activity extends Activity {

    private RequestQueue myQueue; // Volleyで通信を行うための変数
    SharedPreferences sp;
    private ListView mListView;
    public SpotAdapter mSpotAdapter;
    public ArrayList<Spot> spots;
    int roots_length;
    int spotId;
    String select_spot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select2);

        // SharedPreferenceの初期化
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Intent data = getIntent();//前の画面で選択したデータを受けとる
        int Data = data.getIntExtra("int_ID", 0);//Integer型のDataに保存

        // リスト表示するために必要な変数の初期化
        spots = new ArrayList<Spot>();
        mSpotAdapter = new SpotAdapter(Select2Activity.this, 0, spots);
        mListView = (ListView) findViewById(R.id.point_list);
        mListView.setAdapter(mSpotAdapter);

        // ボタンオブジェクトオブジェクト取得(戻る)
        Button button1 = (Button) findViewById(R.id.bt_back);
        button1.setTag("back");
        button1.setOnClickListener(new ButtonClickListener());

        // ボタンオブジェクトオブジェクト取得(次へ)
        Button button2 = (Button) findViewById(R.id.bt_go);
        button2.setTag("go");
        button2.setOnClickListener(new ButtonClickListener());


        /*-------------------------------
         * VolleyでSpot取得
         *----------------------------*/
        myQueue = Volley.newRequestQueue(this);

        // TODO: 調査．tryの中にArrayList<Spot> _spots = new ArrayList<Spot>();　を書かずに正しいリストを表示させる．
        String uri = "http://www2.yoslab.net/~taniguchi/api/get_spots_by_name.php?town_id=" + Data;
        myQueue.add(new JsonObjectRequest(Request.Method.GET, uri, (JSONObject)null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray json_spots = response.getJSONArray("spots");//spots配列の中身がココへだーっと返ってくる
                            roots_length = json_spots.length();//spots配列の要素がいくら入っているか
                            for (int i = 0; i < roots_length; i++) {

                                Spot spot = new Spot();
                                ArrayList<Spot> _spots = new ArrayList<Spot>();

                                JSONObject json_spot = json_spots.getJSONObject(i);//配列中身を0から順にjson_spotへ入れる

                                String spotName = json_spot.getString("name");
                                spotId = json_spot.getInt("id");
                                spot.setId(spotId);
                                spot.setName(spotName);
                                _spots.add(spot); // 配列に要素を追加
                                mSpotAdapter.addAll(_spots); // 配列をアダプターにセット

                            }

                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //エラー時の処理
                        System.out.println(error);
                    }
                }));

    }


    class ButtonClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            String tag = (String) v.getTag();
            if (tag.equals("back")) {
                finishActivity();
            } else if (tag.equals("go")) {
                next();
            }

        }
    }


    void finishActivity() {

        finish();
    }

    void next() {
        String url = "http://www2.yoslab.net/~taniguchi/api/update_checked_point.php?user_id=";
        //SharedPreferenceの値
        String user_id = sp.getString("guide_id", "Guest");

        ArrayList<Integer> idList = IdList.idLists;
        String spotid2 = idList.get(0).toString();
        try {
            if (idList.size() == 1) {
                select_spot = "'" + spotid2 + "'";
            }

            else if (idList.size() == 2) {
                String spotid3 = idList.get(1).toString();
                select_spot = "'" + spotid2 + "," + spotid3 + "'";
            }
            else if (idList.size() == 3) {
                String spotid3 = idList.get(1).toString();
                String spotid4 = idList.get(2).toString();
                select_spot = "'" + spotid2 + "," + spotid3 + "," + spotid4 + "'";
            }
            else if (idList.size() == 4) {
                String spotid3 = idList.get(1).toString();
                String spotid4 = idList.get(2).toString();
                String spotid5 = idList.get(3).toString();
                select_spot = "'" + spotid2 + "," + spotid3 + "," + spotid4 + "," + spotid5 +"'";
            }
            else if (idList.size() == 5) {
                String spotid3 = idList.get(1).toString();
                String spotid4 = idList.get(2).toString();
                String spotid5 = idList.get(3).toString();
                String spotid6 = idList.get(4).toString();
                select_spot = "'" + spotid2 + "," + spotid3 + "," + spotid4 + "," + spotid5 + "," + spotid6 +"'";
            }
            else if (idList.size() == 6) {
                String spotid3 = idList.get(1).toString();
                String spotid4 = idList.get(2).toString();
                String spotid5 = idList.get(3).toString();
                String spotid6 = idList.get(4).toString();
                String spotid7 = idList.get(5).toString();
                select_spot = "'" + spotid2 + "," + spotid3 + "," + spotid4 + "," + spotid5 + "," + spotid6 +","+ spotid7 + "'";
            }
            else if(idList.size() == 7){
                String spotid3 = idList.get(1).toString();
                String spotid4 = idList.get(2).toString();
                String spotid5 = idList.get(3).toString();
                String spotid6 = idList.get(4).toString();
                String spotid7 = idList.get(5).toString();
                String spotid8 = idList.get(6).toString();
                select_spot = "'" + spotid2 + "," + spotid3 + "," + spotid4 + "," + spotid5 + "," + spotid6 +","+ spotid7 + ","+ spotid8 +"'";
            }
            else {
                String message = String.format("1日では回れません");
                Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
                toast.show();
                finish();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        //Volley通信
        myQueue = Volley.newRequestQueue(this);
        //最終URL(net)
        System.out.println("select_spot=========="+select_spot);
        String net = url + "'" + user_id + "'&ids_str="+ select_spot;

        myQueue.add(new JsonObjectRequest(Request.Method.GET, net, (JSONObject)null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                           System.out.println("SUCCESS!!!");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //エラー時の処理
                        System.out.println(error);
                    }
                }));

        Intent intent = new Intent(Select2Activity.this, MapActivity.class);
        intent.putExtra("ID", IdList.idLists);


        startActivity(intent);
        IdList.idLists.clear();
        finish();
    }
}





			