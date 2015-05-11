package geotouer4.yoslab.net.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import geotouer4.yoslab.net.myapplication.adapters.SpotAdapter;
import geotouer4.yoslab.net.myapplication.model.Flag;


public class MapActivity extends FragmentActivity {
    private GoogleMap mMap;
    private RequestQueue myQueue;
    private static final String TAG = MapActivity.class.getSimpleName();
    private static String name;
    private static Float latitude;
    private static Float longitude;
    private static String explain;
    private static MarkerOptions options;
    public SpotAdapter mSpotAdapter;
    SharedPreferences sp4;
    SharedPreferences sp5;
    String Spotid2;
    private static String id;
    int k = 0;
    String spotname[] = new String[10];
    private Flag flag;


    //地図を消すためのレイアウト
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        sp4 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sp5 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        //グローバル変数を扱うクラスを取得
        flag = (Flag)getApplication();

        System.out.println("flag=="+flag.transitionCount);


//        if (flag.transitionCount!= 0) {//ガイドのとき

//        if(flag.transitionCount == 0) {
            Intent data = getIntent();
            ArrayList<Integer> idList = data.getIntegerArrayListExtra("ID");//ArrayList型のidListにチェックされたチェックボックスの数値を取得
            System.out.println("idList=" + idList);

//        }
//        else if(flag.transitionCount != 0){
//            Intent data = getIntent();
//
//
//        }
            // SharedPreferenceの初期化
            sp4 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

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
                CameraPosition cameraPos = new CameraPosition.Builder()
                        .target(new LatLng(myLocate.getLatitude(), myLocate.getLongitude())).zoom(17.0f).bearing(0).build();
                CameraUpdate camera = CameraUpdateFactory.newCameraPosition(cameraPos);
                mMap.animateCamera(camera);
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

            Log.d(TAG, "MapActivity start!");

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
//        setUpMapIfNeeded();


            myQueue = Volley.newRequestQueue(this);


            for (int i = 0; i < idList.size(); i++) {
                String spotid2 = idList.get(i).toString();
                System.out.println("spotid2=" + spotid2);
                Spotid2 = spotid2;


            /*
                       MySQLにデータを取りに行く
            */
                String uri = "http://www2.yoslab.net/~taniguchi/api/select_spot.php?Spotid=" + spotid2;
                myQueue.add(new JsonObjectRequest(Request.Method.GET, uri, (JSONObject)null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray json_spots = response.getJSONArray("spot");//spots配列の中身がココへだーっと返ってくる
                                    System.out.println("json_spots = " + json_spots);


                                    JSONObject json_object = new JSONObject();
                                    System.out.println(json_spots.getJSONObject(0));
                                    json_object = json_spots.getJSONObject(0);
                                    String  spot__id = json_object.getString("Spotid");
                                    String spotName = json_object.getString("name");
                                    String spotLatitude = json_object.getString("latitude");
                                    String spotLongitude = json_object.getString("longitude");
                                    String spotExplain = json_object.getString("explain");
                                    float lati_latitude = Float.parseFloat(spotLatitude);
                                    float lon_longitude = Float.parseFloat(spotLongitude);

                                    id = spot__id;
                                    latitude = lati_latitude;
                                    longitude = lon_longitude;
                                    explain = spotExplain;


                                    String name_k = new String(spotName);
                                    System.out.println("spotName==" + spotName);

                                    //地図にピン刺し
                                    LatLng position = new LatLng(latitude, longitude);
                                    String spotspot_id = new String(spot__id);

                                    options = new MarkerOptions();
                                    options.position(position);
                                    options.title(name_k);
                                    options.snippet(spotspot_id);

                                    mMap.addMarker(options);//マーカーセット


                                    if (mMap != null) {
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
                                                System.out.println("id(((((((((((( = "+id);
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

                                                // ユーザIDをSharedPreferenceに保存

                                                //sharedPreferenceに保存
//                                                String Spot_id = (Spotid2).toString();
//                                                sp4.edit().putString("Spot_id", Spot_id).commit();
//
//                                                // 保存できてるか確認
//                                                System.out.println(sp4.getString("Spot_id", "000"));

                                                if(flag.transitionCount == 0) {//ガイド
                                                    System.out.println("GGGGGGGGGGGGGGGGGGGGGG");
                                                    String Spot_id = id;
                                                    sp4.edit().putString("Spot_id", Spot_id).commit();

                                                    // 保存できてるか確認
                                                    System.out.println("ガイド="+sp4.getString("Spot_id", "000"));
                                                    //インテントの生成(呼び出すクラスの指定)
                                                    Intent intent = new Intent(MapActivity.this, DetailActivity.class);
                                                    intent.putExtra("NAME", marker.getTitle());
                                                    intent.putExtra("EXPLAIN", explain);
                                                    startActivity(intent);
                                                }
                                                else{//参加者
                                                    System.out.println("WWwwwwwwwwwwwwwwwwww");
                                                    //sharedPreferenceに保存
                                                    String Spot_id = id;
                                                    sp5.edit().putString("Spot_id2", Spot_id).commit();

                                                    // 保存できてるか確認
                                                    System.out.println("参加者="+sp5.getString("Spot_id2", "000"));

                                                    //インテントの生成(呼び出すクラスの指定)
                                                    Intent intent = new Intent(MapActivity.this, Detail_participant_Activity.class);
                                                    intent.putExtra("NAME", marker.getTitle());
                                                    System.out.println("marker.getTitle()=" + marker.getTitle());
                                                    startActivity(intent);

//                                                    String Spot_id = id;
//                                                    sp5.edit().putString("Spot_id2", Spot_id).commit();
//
//                                                    // 保存できてるか確認
//                                                    System.out.println(sp5.getString("asasaassasaasaas======"+"Spot_id2", "000"));
//
//                                                    Intent intent = new Intent(MapActivity.this, Detail_participant_Activity.class);
//                                                    intent.putExtra("NAME",marker.getTitle());
//                                                    System.out.println("marker.getTitle()="+marker.getTitle());
//                                                    startActivity(intent);
                                                }
                                            }
                                        });
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

        }
//    }


        @Override
        protected void onResume () {
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

    private void setUpMap() {//現在地ボタンの表示
        mMap.setMyLocationEnabled(true);
    }
}
