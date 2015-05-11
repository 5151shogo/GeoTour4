package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;



public class Detail_participant_Activity extends Activity {

    private static final String TAG = Detail_participant_Activity.class.getSimpleName();
    private String imageSavePlace;
    private static final int REQUEST_GALLERY = 0;
    static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int CAMERA_REQUEST_CODE = 1;
    private Uri uri;
    private String ImageURI;
    SharedPreferences sp;
    SharedPreferences sp2;
    SharedPreferences sp3;
    SharedPreferences sp4;
    SharedPreferences sp5;
    int roots_length;
    int RESULT_PICK_FILENAME = 1;
    private RequestQueue myQueue; // Volleyで通信を行うための変数
    String name;
    TextView text2;
    String guide_id;//Sp入れるやつ
    String spot_id;
    String guide_id2;//DBからとってきたやつ
    private String Spot_picture_name;//DBからとってきたやつ

    // カメラ周りの変数定義
    private static String pictureName;
    private static String filename;
    private static Bitmap image;
    Button mButtonOpen;
    ImageView mImageView;
    FileOutputStream out;
    String user_id;
    String spotid;
    String text3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_participant);
        Log.d(TAG, "Detail_participant_Activity start!");

        // SharedPreferenceの初期化
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sp2 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sp3 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sp4 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sp5 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");

        TextView text = (TextView) findViewById(R.id.detail);
        text.setText(name);

        text2 = (TextView) findViewById(R.id.memo2);
        myQueue = Volley.newRequestQueue(this);
        String url = "http://www2.yoslab.net/~taniguchi/api/catch_text.php?user_id=";
        String user_id = sp.getString("user_id", "Guest");
        String spotid = sp2.getString("Spot_id", "000");
        String net3 = url + "'" + user_id + "'&spotid=" + spotid;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,net3,(JSONObject)null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray json_spots = response.getJSONArray("spots");//spots配列の中身がココへだーっと返ってく
                            JSONObject json_spot = json_spots.getJSONObject(0);//配列中身を0から順にjson_spotへ入れる
                            String get_text = json_spot.getString("save_text");
                            System.out.println("get_text======="+get_text);


                            if(get_text == null){//もしnullが返ってきたら
                                text2.setText(name);
                            }
                            else{
                                text2.setText(get_text);
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
                });


        // ボタンオブジェクトオブジェクト取得(地図画面へ)
        Button button1 = (Button) findViewById(R.id.map);
        button1.setTag("back1");
        button1.setOnClickListener(new ButtonClickListener());

        //ボタンオブジェクト作成(カメラ)
        Button button2 = (Button) findViewById(R.id.camera);
        button2.setTag("camera");
        button2.setOnClickListener(new ButtonClickListener());

        System.out.println("guide_id="+guide_id);
       //ボタンオブジェクト作成(ガイドの写真を見る機能)
        Button button3 = (Button) findViewById(R.id.watch);
        button3.setTag("watch");
        button3.setOnClickListener(new ButtonClickListener());

        //ボタンオブジェクト作成(保存)
        Button button4 = (Button) findViewById(R.id.save);
        button4.setTag("save");
        button4.setOnClickListener(new ButtonClickListener());
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
            } else if (tag.equals("camera")) {
                cameraActivity();
            } else if (tag.equals("watch")) {
                WatchActivity();
            }
            else if (tag.equals("save")){
                saveActivity();
            }
        }
    }


    // アクティビティ終了(画面クローズ)
    private void finishActivity() {
        finish();
    }


    private void cameraActivity() {//カメラアクティビティ

        Intent intent = new Intent(Detail_participant_Activity.this, Camera_participant_Activity.class);
        startActivity(intent);
    }

    private void WatchActivity() {

        guide_id = sp3.getString("guide_id", "Guest");
        spot_id=sp5.getString("Spot_id2", "000");
        System.out.println("spot_id==========++++++++++++======"+spot_id);
        if (guide_id == "Guest") {
            System.out.println("ぬるぽん");
            String message = String.format("地図画面でガイドのIDを入力してください");
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
            toast.show();

        }

        System.out.println("guide_id=====" + guide_id);
        myQueue = Volley.newRequestQueue(this);
        String url2 = "http://www2.yoslab.net/~taniguchi/api/DownLoad.php?guide_id=";
        String net2 = url2 + "'" + guide_id + "'";//最終


        myQueue.add(new JsonObjectRequest(Request.Method.GET,net2,(JSONObject)null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray json_spots = response.getJSONArray("spots");//spots配列の中身がココへだーっと返ってくる
                            roots_length = json_spots.length();//spots配列の要素がいくら入っているか

                            for(int i=0;i<roots_length;i++) {

                                JSONObject json_spot = json_spots.getJSONObject(i);//配列中身を0から順にjson_spotへ入れる

                                String guide_id2 = json_spot.getString("user_id");
                                String Spot_picture_name = json_spot.getString("Spot_picture_name");

                                //imgを呼び出す
                                String Image_uri = "http://www2.yoslab.net/~taniguchi/api/img/";
                                ImageURI = Image_uri + guide_id2 + "/" + Spot_picture_name;
                                System.out.println("ImageURI ===== " + ImageURI);
                                if (Spot_picture_name.equals(spot_id + "_101.jpg")) {
                                  expression(ImageURI);
                                }
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

        myQueue.start();
    }

//        String Image_uri = "http://www2.yoslab.net/~taniguchi/api/img/";
//
//
//        String guide = Image_uri + guide_id2;
//        String image_jpg = Spot_picture_name;
//
//        final ImageView imageView = (ImageView)findViewById(R.id.guide_image);
//        System.out.println("ImageURI ===== "+ImageURI);
//
//        ImageRequest request = new ImageRequest(
//                ImageURI,
//                new Response.Listener<Bitmap>() {
//                    @Override
//                    public void onResponse(Bitmap response) {
//                        imageView.setImageBitmap(response);
//                        System.out.println(imageView.getWidth());
//                    }
//                },
//                // 最大の幅、指定無しは0
//                0,
//                0,
//                Bitmap.Config.ARGB_8888,
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        );
//
//        myQueue.add(request);
//        myQueue.start();


    public void expression(String Image__uri){//画像表示するところ
        final ImageView imageView = (ImageView)findViewById(R.id.guide_image);
        System.out.println("Image__uri ===== "+Image__uri);

        ImageRequest request = new ImageRequest(
                Image__uri,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                        System.out.println(imageView.getWidth());
                    }
                },
                // 最大の幅、指定無しは0
                0,
                0,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        myQueue.add(request);
        myQueue.start();
    }


    private void saveActivity(){
        //写真保存

        user_id = sp.getString("user_id", "Guest");
        spotid = sp2.getString("Spot_id", "000");
        System.out.println("spotid~~~~~~~~~~~~"+spotid);
        if(user_id == ""){
            new AlertDialog.Builder(Detail_participant_Activity.this)
                    .setTitle("ログインしてください")
                    .setPositiveButton(
                            "キャンセル",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                    .setNegativeButton(
                            "ログインする",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Detail_participant_Activity.this,ParticipantActivity.class);
                                    startActivity(intent);
                                }
                            })
                    .show();
        }

        else {
            //メモ取得
            EditText text = (EditText) findViewById(R.id.memo2);
            SpannableStringBuilder sp3 = (SpannableStringBuilder) text.getText();
            Log.d("Tag", sp3.toString());

            myQueue = Volley.newRequestQueue(this);//通信開始
            String url = "http://www2.yoslab.net/~taniguchi/api/watch_spot.php";


            text3 = sp3.toString();

            new AlertDialog.Builder(Detail_participant_Activity.this)
                    .setTitle("保存しました")
                    .setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })

                    .show();
//        String net = url + user_id + "&spotid=" + spotid + "&save_text=" + text2;
//        System.out.println("text======="+net);

//        TODO:Volley送るだけのとききれいに

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

                    params.put("user_id", user_id);
                    params.put("spotid", spotid);
                    params.put("save_text", text3);
                    return params;
                }
            };
            myQueue.add(postRequest);
            myQueue.start();
        }

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
