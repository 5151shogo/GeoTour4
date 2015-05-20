package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.URL;
import android.widget.ArrayAdapter;

public class Weather_Activity extends Activity {
    private LocationManager mLocationManager;
    private RequestQueue myQueue;
    private double latitude;
    private double longitude;
    private URL url;
    private InputStream is;
    private BufferedReader reader;
    private ListView lv;
    private String morn3;
    private String min3;
    private String night3;
    private String eve3;
    private String max3;
    private String day3;
    private String requestURL;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//        Button button1 = (Button) findViewById(R.id.bt_button);
//        button1.setTag("weather");
//        button1.setOnClickListener(new ButtonClickListener());

        myQueue = Volley.newRequestQueue(this);


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = mLocationManager.getBestProvider(criteria, true);
        // LocationListenerを登録
        mLocationManager.requestLocationUpdates(provider, 110, 0, listener);

        System.out.println("latitude === " + latitude);
        System.out.println("longitude === " + longitude);

        int id = 0;

        Spinner AREA1 = (Spinner)findViewById(R.id.sp_place);

        //選択された値を取得
        String selected = (String)AREA1.getSelectedItem();

        Log.e("test", "selected=" + selected);

        if(selected.equals("上富田町")){
            id=1;
            requestURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=33.7176947&lon=135.4543542&mode=json&cnt=7&199823890a1dd81d17944552d84ad932";
        }
        else if(selected.equals("北山村")) {
            id=2;
            requestURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=33.9619356&lon=135.9525103&mode=json&cnt=7&199823890a1dd81d17944552d84ad932";
        }
        else if(selected.equals("串本町")) {
            id=3;
            requestURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=33.5101941&lon=135.7605947&mode=json&cnt=7&199823890a1dd81d17944552d84ad932";
        }
        else if(selected.equals("古座川町")) {
            id=4;
            requestURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=33.6224962&lon=135.7350605&mode=json&cnt=7&199823890a1dd81d17944552d84ad932";
        }
        else if(selected.equals("新宮市")) {
            id=5;
            requestURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=33.7948539&lon=135.867848&mode=json&cnt=7&199823890a1dd81d17944552d84ad932";
        }
        else if(selected.equals("白浜町")) {
            id=6;
            requestURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=33.6264333&lon=135.4989435&mode=json&cnt=7&199823890a1dd81d17944552d84ad932";
        }
        else if(selected.equals("すさみ町")) {
            id=7;
            requestURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=33.5669814&lon=135.5639386&mode=json&cnt=7&199823890a1dd81d17944552d84ad932";
        }
        else if(selected.equals("太地町")) {
            id=8;
            requestURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=33.5910889&lon=135.9423199&mode=json&cnt=7&199823890a1dd81d17944552d84ad932";
        }
        else if(selected.equals("那智勝浦町")) {
            id=9;
            requestURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=33.6341395&lon=135.8830239&mode=json&cnt=7&199823890a1dd81d17944552d84ad932";
        }

        // ボタンオブジェクトオブジェクト取得(次へ)
        Button button1 = (Button) findViewById(R.id.bt_button);
        button1.setTag("go1");
        button1.setOnClickListener(new ButtonClickListener());

//        String message = String.format("最終緯度：" + latitude + "，最終経度：" + longitude);
//        Toast toast = Toast.makeText(Weather_Activity.this, message, Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.TOP, 0, 200);
//        toast.show();
//        System.out.println("最終緯度：" + latitude);
//        System.out.println("最終経度：" + longitude);


        String requestURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + latitude + "&lon=" + longitude + "&mode=json&cnt=7&199823890a1dd81d17944552d84ad932";
        System.out.println("aaaaaaaaaaaaa = " + requestURL);


        myQueue.add(new JsonObjectRequest(Request.Method.GET, requestURL, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //for(int i = 0;i<=6;i++) {
                            JSONArray list = response.getJSONArray("list");//spots配列の中身がココへだーっと返ってく
                            JSONObject all_list = list.getJSONObject(0);
                            System.out.println("---------------------" + all_list);

                            String dt = all_list.getString("dt");
                            System.out.println("dt=======" + dt);
                            JSONObject temp = all_list.getJSONObject("temp");
                            System.out.println("temp=======" + temp);
                            String morn = temp.getString("morn");
                            float morn1 = Float.parseFloat(morn);
                            float morn2 = morn1 - 273.15f;
                            morn3 = String.valueOf(morn2);
                            System.out.println("morn2=======" + morn2);
                            String min = temp.getString("min");
                            float min1 = Float.parseFloat(min);
                            float min2 = min1 - 273.15f;
                            min3 = String.valueOf(min2);
                            System.out.println("min2=======" + min2);
                            String night = temp.getString("night");
                            float night1 = Float.parseFloat(night);
                            float night2 = night1 - 273.15f;
                            night3 = String.valueOf(night2);
                            System.out.println("night2=======" + night2);
                            String eve = temp.getString("eve");
                            float eve1 = Float.parseFloat(eve);
                            float eve2 = eve1 - 273.15f;
                            eve3 = String.valueOf(eve2);
                            System.out.println("eve2=======" + eve2);
                            String max = temp.getString("max");
                            float max1 = Float.parseFloat(max);
                            float max2 = max1 - 273.15f;
                            max3 = String.valueOf(max2);
                            System.out.println("max2=======" + max2);
                            String day = temp.getString("day");
                            float day1 = Float.parseFloat(day);
                            float day2 = day1 - 273.15f;
                            day3 = String.valueOf(day2);
                            System.out.println("day2=======" + day2);


                            String pressure = all_list.getString("pressure");
                            System.out.println("pressure=======" + pressure);
                            String humidity = all_list.getString("humidity");
                            System.out.println("humidity=======" + humidity);


                            JSONArray weather = all_list.getJSONArray("weather");
                            System.out.println("weather=======" + weather);
                            JSONObject all_weather = weather.getJSONObject(0);
                            System.out.println("all_weather=======" + all_weather);
                            String id = all_weather.getString("id");
                            System.out.println("id=======" + id);
                            String main = all_weather.getString("main");
                            System.out.println("main=======" + main);
                            String description = all_weather.getString("description");
                            System.out.println("description=======" + description);
                            String icon = all_weather.getString("icon");
                            System.out.println("icon=======" + icon);


                            String speed = all_list.getString("speed");
                            System.out.println("speed=======" + speed);
                            String deg = all_list.getString("deg");
                            System.out.println("deg=======" + deg);
                            String clouds = all_list.getString("clouds");
                            System.out.println("clouds=======" + clouds);


//                            String[] member = {day3,max3,min3};
//                            lv = (ListView) findViewById(R.id.listView1);
                            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_weather, member);
                            //lv.setAdapter(member);
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


//        String[] member = {day3,max3,min3};
//        lv = (ListView) findViewById(R.id.listView1);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, member);
//
//        lv.setAdapter(adapter);


//                String[] member = {morn3,min3,night3,eve3,max3,day3};
//                lv = (ListView) findViewById(R.id.listView1);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_list_item_1, member);
//
//                lv.setAdapter(adapter);


    }

    private final LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {//位置情報が正しく更新されたときに呼び出されるメソッド
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {//プロバイダーのステータスが変わったときに呼び出されるメソッド

        }

        @Override
        public void onProviderEnabled(String provider) {//ユーザにより引数に渡されたプロバイダーが有効化されたときに呼び出されるメソッド

        }

        @Override
        public void onProviderDisabled(String provider) {//ユーザにより引数に渡されたプロバイダーが無効化されたときに呼び出されるメソッド

        }
    };


    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String tag = (String) v.getTag();
            if (tag.equals("weather")) {//ここから
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                criteria.setPowerRequirement(Criteria.POWER_LOW);
                String provider = mLocationManager.getBestProvider(criteria, true);
                // LocationListenerを登録
                mLocationManager.requestLocationUpdates(provider, 110, 0, listener);

                System.out.println("latitude === " + latitude);
                System.out.println("longitude === " + longitude);

                String message = String.format("最終緯度：" + latitude + "，最終経度：" + longitude);
                Toast toast = Toast.makeText(Weather_Activity.this, message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 200);
                toast.show();
                System.out.println("最終緯度：" + latitude);
                System.out.println("最終経度：" + longitude);




                String requestURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + latitude + "&lon=" + longitude + "&mode=json&cnt=7&199823890a1dd81d17944552d84ad932";
                System.out.println("aaaaaaaaaaaaa = "+requestURL);


                myQueue.add(new JsonObjectRequest(Request.Method.GET, requestURL, (JSONObject)null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    //for(int i = 0;i<=6;i++) {
                                        JSONArray list = response.getJSONArray("list");//spots配列の中身がココへだーっと返ってく
                                    JSONObject all_list = list.getJSONObject(0);
                                        System.out.println("---------------------" + all_list);

                                        String dt = all_list.getString("dt");
                                        System.out.println("dt=======" + dt);
                                    JSONObject temp = all_list.getJSONObject("temp");
                                        System.out.println("temp=======" + temp);
                                        String morn = temp.getString("morn");
                                        float morn1 = Float.parseFloat(morn);
                                        float morn2 = morn1 - 273.15f;
                                        System.out.println("morn2=======" + morn2);
                                        String min = temp.getString("min");
                                        float min1 = Float.parseFloat(min);
                                        float min2 = min1 - 273.15f;
                                        System.out.println("min2=======" + min2);
                                        String night = temp.getString("night");
                                        float night1 = Float.parseFloat(night);
                                        float night2 = night1 - 273.15f;
                                        System.out.println("night2=======" + night2);
                                        String eve = temp.getString("eve");
                                        float eve1 = Float.parseFloat(eve);
                                        float eve2 = eve1 - 273.15f;
                                        System.out.println("eve2=======" + eve2);
                                    String max = temp.getString("max");
                                        float max1 = Float.parseFloat(max);
                                    float max2 = max1 - 273.15f;
                                        System.out.println("max2=======" + max2);
                                        String day = temp.getString("day");
                                    float day1 = Float.parseFloat(day);
                                        float day2 = day1 - 273.15f;
                                        System.out.println("day2=======" + day2);


                                        String pressure = all_list.getString("pressure");
                                        System.out.println("pressure=======" + pressure);
                                        String humidity = all_list.getString("humidity");
                                        System.out.println("humidity=======" + humidity);


                                        JSONArray weather = all_list.getJSONArray("weather");
                                        System.out.println("weather=======" + weather);
                                        JSONObject all_weather = weather.getJSONObject(0);
                                        System.out.println("all_weather=======" + all_weather);
                                        String id = all_weather.getString("id");
                                        System.out.println("id=======" + id);
                                        String main = all_weather.getString("main");
                                        System.out.println("main=======" + main);
                                        String description = all_weather.getString("description");
                                        System.out.println("description=======" + description);
                                        String icon = all_weather.getString("icon");
                                        System.out.println("icon=======" + icon);


                                        String speed = all_list.getString("speed");
                                        System.out.println("speed=======" + speed);
                                        String deg = all_list.getString("deg");
                                        System.out.println("deg=======" + deg);
                                        String clouds = all_list.getString("clouds");
                                        System.out.println("clouds=======" + clouds);










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
            }
        }
}

//    @Override
//    public void onResume() {
//        super.onResume();
//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//        criteria.setPowerRequirement(Criteria.POWER_LOW);
//        final String provider = mLocationManager.getBestProvider(criteria, true);//どっちかの最適なプロバイダーを使う(GPSかNETWORKか)
//        // LocationListenerを登録
//        mLocationManager.requestLocationUpdates(provider, 10000, 0, listener);
//
//    }
//
//    public void onPause(){
//        if(mLocationManager != null )
//        {
//            mLocationManager.removeUpdates(listener);
//        }
//
//        super.onPause();
//
//        Log.v("Status", "onPause");
//    }
//            }
//        }

