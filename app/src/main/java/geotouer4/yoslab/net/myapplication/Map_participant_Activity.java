package geotouer4.yoslab.net.myapplication;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import geotouer4.yoslab.net.myapplication.adapters.Spot_2Adapter;
import geotouer4.yoslab.net.myapplication.model.Flag;


public class Map_participant_Activity extends FragmentActivity {
    private GoogleMap mMap;
    private RequestQueue myQueue;
    private static final String TAG = Map_participant_Activity.class.getSimpleName();
    //private static String name;
    private static String id;
    private static Float latitude;
    private static Float longitude;
    public Spot_2Adapter mSpotAdapter;
    private static String explain;
    private static MarkerOptions options;
    SharedPreferences sp;
    SharedPreferences sp4;
    SharedPreferences sp5;
    int roots_length;
    int l = 0;
    private Flag flag;      // グローバル変数を扱うクラス
    String [] stringArray;
    Integer[] intArray;
    ArrayList<Integer> intArray2;
    String Spotid2;
    String guide_id;
    ProgressDialog dialog;//プログレスバー

    //地図を消すためのレイアウト
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_participant);

        // SharedPreferenceの初期化
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sp4 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sp5 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

//        Intent data = getIntent();
//        ArrayList<Integer> idList = data.getIntegerArrayListExtra("ID");//ArrayList型のidListにチェックされたチェックボックスの数値を取得
//        System.out.println("idList="+idList);


        Button button1 = (Button) findViewById(R.id.button1);
        button1.setTag("change");
        button1.setOnClickListener(new ButtonClickListener());


        // mapクラス関連の初期化
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }


        //LocationManagerの取得
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //GPSから現在地の情報を取得
        Location myLocate = locationManager.getLastKnownLocation("gps");
        System.out.println("myLocate=" + myLocate);

        //MapControllerの取得
        if (myLocate != null) {
            //現在地情報取得成功
//            CameraPosition cameraPos = new CameraPosition.Builder()
//                    .target(new LatLng(myLocate.getLatitude(), myLocate.getLongitude())).zoom(17.0f).bearing(0).build();

            //現在地までパッと移動
            //MapCtrl.setCenter(GP);
        } else {
            //現在地情報取得失敗時の処理
            Toast.makeText(this, "現在地取得できません！", Toast.LENGTH_SHORT).show();
        }



        //Fragmentの取得
        FragmentManager manager = getSupportFragmentManager();
        SupportMapFragment frag = (SupportMapFragment) manager.findFragmentById(R.id.map_frag);

        // GoogleMapの取得
        mMap = frag.getMap();

//        拡大・縮小ボタンを表示
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // 通常の地図に変更
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //コンパスの有効化
        mMap.getUiSettings().setCompassEnabled(true);

        //現在位置を獲得する
        mMap.setMyLocationEnabled(true);

        //現在位置の獲得（パラメータ）
        Location t = mMap.getMyLocation();

        //くるくる
        showProgressDialog();

        //南に移動
        CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(new LatLng(33.54701289300004, 135.7205299620001), 9);
        mMap.moveCamera(camera);

        Log.d(TAG, "MapActivity start!");

        myQueue = Volley.newRequestQueue(this);

        //TODO:一回でとってくる

        for (int i = 0; i < 104; i++) {
            //String spotid2 = idList.get(i).toString();
            //System.out.println("spotid2="+spotid2);
            /*
                       MySQLにデータを取りに行く
            */
            String uri = "http://www2.yoslab.net/~taniguchi/api/select_spot_participant.php";
            myQueue.add(new JsonObjectRequest(Request.Method.GET, uri, (JSONObject)null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray json_spots = response.getJSONArray("spot");//spots配列の中身がココへだーっと返ってくる
                                System.out.println("json_spots = " + json_spots);


                                JSONObject json_object = new JSONObject();
                                json_object = json_spots.getJSONObject(l);
                                System.out.println(json_spots.getJSONObject(l));

                                String  spotid = json_object.getString("Spot_id");
                                String spotName = json_object.getString("name");
                                String spotLatitude = json_object.getString("latitude");
                                String spotLongitude = json_object.getString("longitude");
                                float lati_latitude = Float.parseFloat(spotLatitude);
                                float lon_longitude = Float.parseFloat(spotLongitude);

                                id = spotid;
                                latitude = lati_latitude;
                                longitude = lon_longitude;

                                System.out.println("id====" + id);
                                System.out.println("name====" + spotName);
                                System.out.println("latitude====" + latitude);
                                System.out.println("longitude====" + longitude);

                                l++;//json_objectの数のカウント
                                //地図にピン刺し
                                LatLng position = new LatLng(latitude, longitude);
                                String name_k = new String(spotName);
                                String spotspot_id = new String(spotid);

                                options = new MarkerOptions();
                                options.position(position);
                                options.title(name_k);
                                options.snippet(spotspot_id);
                                mMap.addMarker(options);//マーカーセット


                                /*
                                explainが上書きされる問題
                                 */


                                // if (mMap != null) {
                                mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
                                    @Override
                                    public View getInfoContents(Marker marker) {
                                        View view = getLayoutInflater().inflate(R.layout.info_window, null);

                                        String spotName_k = marker.getTitle();
                                        String spotid_k =marker.getSnippet();


                                        // タイトル設定
                                        TextView title = (TextView) view.findViewById(R.id.info_title);
                                        title.setText(spotName_k);


                                        id = spotid_k;
                                        System.out.println("id = "+id);
                                        return view;
                                    }

                                    @Override
                                    public View getInfoWindow(Marker marker) {
                                        return null;
                                    }
                                });

                                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick(Marker marker) {

                                        //TODO:spot_id確認

                                        //sharedPreferenceに保存
                                        String Spot_id = id;
                                        sp4.edit().putString("Spot_id", Spot_id).commit();

                                        // 保存できてるか確認
                                        System.out.println("参加者="+sp4.getString("Spot_id", "000"));

                                        //インテントの生成(呼び出すクラスの指定)
                                        Intent intent = new Intent(Map_participant_Activity.this, Detail_participant_Activity.class);
                                        intent.putExtra("NAME", marker.getTitle());
                                        System.out.println("marker.getTitle()=" + marker.getTitle());
                                        startActivity(intent);
                                    }
                                });
                                // }


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
        myQueue.start();




//        String user_name = spotid.toString();
//        String net = url + user_name + "&user_id=" + user_id;
//        Log.d("Tag","net="+net);


//        String uri = "http://www2.yoslab.net/~taniguchi/api/select_spot.php?spot_id="+Data;
//        myQueue.add(new JsonObjectRequest(Request.Method,GET,uri,null,
//
//        try{
//
//        }
//        catch(Exception e){
//            System.out.println(e);
//        }
//
//
//
//        ));
    }

    // クリックリスナー定義
    class ButtonClickListener implements View.OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        public void onClick(View v) {
            //Intent intent = getIntent();
            String tag = (String) v.getTag();
            if (tag.equals("change")) {

                TextView changetext = (TextView) findViewById(R.id.changetext);
                String text1 = changetext.getText().toString();
                changetext.setText(text1);

                EditText changeEdit = (EditText) findViewById(R.id.match);
                guide_id = changeEdit.getText().toString();

                //sharedPreferenceに保存
                sp5.edit().putString("guide_id", guide_id).commit();
                System.out.println("guide_id=="+sp5.getString("guide_id", "Guest"));

                change();
            }
        }
    }


    private void change() {

        //グローバル変数を扱うクラスを取得
        flag = (Flag) getApplication();
        //グローバル変数を扱うクラスの初期化
        flag.init();
        //グローバル変数の値変更(+1)
        flag.transitionCount++;




        //ここでDBに入ったchecked_pointを見る
        myQueue = Volley.newRequestQueue(this);
        String url = "http://www2.yoslab.net/~taniguchi/api/select_guide_checked_point.php?user_id=";
        //String user_id = sp.getString("guide_id", "Guest");
        String net = url + "'" + guide_id + "'";

        System.out.println("net=====" + net);


        myQueue.add(new JsonObjectRequest(Request.Method.GET, net, (JSONObject)null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            System.out.println("うぽつつつｔ");
                            JSONArray json_spots = response.getJSONArray("spots");//spots配列の中身がココへだーっと返ってくる
                            JSONObject json_spot = json_spots.getJSONObject(0);//配列中の0番目をjson_spotへ入れる

                            String checked_spots_id = json_spot.getString("checked_spots_id");
                            String a[] = checked_spots_id.split(",");
                            System.out.println("a============= "+a.length);
                            stringArray = new String[a.length];
                            intArray = new Integer[a.length];

                            intArray2 = new ArrayList<Integer>();
                            for(int k=0;k<a.length;k++){
                                stringArray[k] = a[k];
                                intArray2.add(k, Integer.parseInt(stringArray[k]));
                                System.out.println("intArray2[]==="+intArray2);

                            }


                            Intent intent = new Intent(Map_participant_Activity.this, MapActivity.class);

                            System.out.println("stringArray[]==="+stringArray);
                            System.out.println("intArray2[]==="+intArray2);
                            intent.putExtra("ID",intArray2);

                            startActivity(intent);
                            IdList.idLists.clear();
                            finish();









//                                spot.setName(checked_spots_id);
//                                System.out.println("checked_spots_id========"+checked_spots_id);
//                                _spots.add(spot); // 配列に要素を追加
//                                mSpotAdapter.addAll(_spots); // 配列をアダプターにセット


                        } catch (Exception e) {
                            String message = String.format("IDが違います");
                            Toast toast = Toast.makeText(Map_participant_Activity.this, message, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP, 0, 200);
                            toast.show();
                            System.out.println(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //エラー時の処理
                        String message = String.format("IDが違います");
                        Toast toast = Toast.makeText(Map_participant_Activity.this, message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 200);
                        toast.show();
                        System.out.println(error);
                    }
                }));
        myQueue.start();



//        Intent intent = new Intent(Map_participant_Activity.this, MapActivity.class);
//
//        System.out.println("stringArray[]==="+stringArray);
//        System.out.println("intArray[]==="+intArray);
//        intent.putExtra("ID",intArray);
//
//        startActivity(intent);
//        IdList.idLists.clear();
//        finish();
    }

    public void showProgressDialog() {//くるくる
        dialog = new ProgressDialog(this);
        dialog.setMessage("接続中...");
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMax(10);
        dialog.setCancelable(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

        dialog.show();
        new Thread(new Runnable(){
            public void run() {
                try {
                    for (int i = 0; i < dialog.getMax(); i++) {
                        dialog.setProgress(i);
                        Thread.sleep(200);
                    }
                } catch (Exception e) {

                }
                dialog.dismiss();
            }
        }).start();
    }
}


////	setUpMapIfNeeded();
//    Bundle extra = getIntent().getExtras();
//    //SpotIDの数を数える
//
//	// 付加情報から入力データ取得
////    for(int i = 0;i<=getIntent();i++){
//		Float _id = extra.getFloat("SELECTED_ID");
//		name = extra.getString("NAME");
//		latitude = extra.getFloat("SELECTED_latitude");
//		longitude = extra.getFloat("SELECTED_longitude");//String型のDataに保存
//		explain = extra.getString("SELECTED_explain");//String型のDataに保存
//		Log.d(TAG, "_id3 = "+_id);
//		Log.d(TAG, "name3 = "+name);
//		Log.d(TAG, "latutude3 = "+latitude);
//		Log.d(TAG, "longitude3=" + longitude);
//		Log.d(TAG, "explain3=" + explain);
//    	}
//    }

//}
//import android.app.FragmentManager;
//import android.content.Intent;
//import android.location.Location;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;



/*		
		
		// mapクラス関連の初期化
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        if (mMap != null) {
        	mMap.setMyLocationEnabled(true);
        }
        //Fragmentの取得
        FragmentManager manager = getSupportFragmentManager();
        SupportMapFragment frag = (SupportMapFragment) manager.findFragmentById(R.id.map_frag);
        // GoogleMapの取得
        mMap = frag.getMap();
        
        Log.d(TAG, "MapActivity start!" );
		setUpMapIfNeeded();
        Bundle extra = getIntent().getExtras();
		// 付加情報から入力データ取得
		Float _id = extra.getFloat("SELECTED_ID");
		name = extra.getString("NAME");
		latitude = extra.getFloat("SELECTED_latitude");		
		longitude = extra.getFloat("SELECTED_longitude");//String型のDataに保存		
		explain = extra.getString("SELECTED_explain");//String型のDataに保存
		Log.d(TAG, "_id3 = "+_id);
		Log.d(TAG, "name3 = "+name);
		Log.d(TAG, "latutude3 = "+latitude);
		Log.d(TAG, "longitude3=" + longitude);
		Log.d(TAG, "explain3=" + explain);

//        if(name.equals("橋杭岩")){
	        // ピンを立てる
	        //LatLng position = new LatLng(latitude, longitude);

			LatLng position = new LatLng(latitude, longitude);
	    	options = new MarkerOptions();
	        options.position(position);
	        mMap.addMarker(options);//マーカーセット
	        
	        
	        if (mMap != null) {
		        mMap.setInfoWindowAdapter(new InfoWindowAdapter(){
		        @Override
		        public View getInfoContents(Marker marker){
		        	
		        	
			        View view = getLayoutInflater().inflate(R.layout.info_window, null);
			       
			        
				        if(name.equals("橋杭岩")){
				        	
				        	options.title("橋杭岩");
					        options.snippet("大きな岩がそそり立つ");
					        //画像設定
					        ImageView img = (ImageView)view.findViewById(R.id.info_image);
					        img.setImageResource(R.drawable.bridgepile100); 
					        
				        }
				        else if(name.equals("海金剛")){
				        	options.title("海金剛");
					        options.snippet("とがった3つの岩が魅力的");
					        //画像設定
							ImageView img = (ImageView)view.findViewById(R.id.info_image);
							img.setImageResource(R.drawable.haegeumgang100);
							
				        }
				        
				        // タイトル設定
				        TextView title = (TextView)view.findViewById(R.id.info_title);
				        title.setText(options.getTitle());
				        
				        
				       // snippet設定
				        TextView snippet = (TextView)view.findViewById(R.id.info_snippet);
				        snippet.setText(options.getSnippet());
				        
				        return view;
		        }
		        @Override
		        public View getInfoWindow(Marker marker) {
		        	return null;
		        }
		       });
	        
	        mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){
	        	@Override
	        	public void onInfoWindowClick(Marker marker) {
	        		//インテントの生成(呼び出すクラスの指定)
					Intent intent = new Intent(MapActivity.this, DetailActivity.class);
					intent.putExtra("SELECTED_explain2",explain.toString());
					System.out.println("explain="+explain);
					startActivity(intent);
	        	}
	        });
          }
	        
	      
		
		// 拡大・縮小ボタンを表示
		mMap.getUiSettings().setZoomControlsEnabled(true);

		// 通常の地図に変更
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		//コンパスの有効化
		mMap.getUiSettings().setCompassEnabled(true);

		//現在位置を獲得する
		mMap.setMyLocationEnabled(true);

		//現在位置の獲得（パラメータ）
		Location l = mMap.getMyLocation();

		
		
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
		           .findFragmentById(R.id.map);
		 
		   if (savedInstanceState == null) {
		       // First incarnation of this activity.
		       mapFragment.setRetainInstance(true);
		   } else {
		       // Reincarnated activity. The obtained map is the same map instance in the previous
		       // activity life cycle. There is no need to reinitialize it.
		       mMap = mapFragment.getMap();
		   }
		   setUpMapIfNeeded();
	}


	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.txt_titlebar) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}



	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

	private void setUpMap() {
    	mMap.setMyLocationEnabled(true);
    }
}

*/
    //}
