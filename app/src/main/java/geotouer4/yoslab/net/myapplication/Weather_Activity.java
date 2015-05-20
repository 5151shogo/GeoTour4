package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.URL;

public class Weather_Activity extends Activity {
    private LocationManager mLocationManager;
    private RequestQueue myQueue;
    private double latitude;
    private double longitude;
    private URL url;
    private InputStream is;
    private BufferedReader reader;
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Button button1 = (Button)findViewById(R.id.bt_button);
        button1.setTag("weather");
        button1.setOnClickListener(new ButtonClickListener());

        myQueue = Volley.newRequestQueue(this);
    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String tag = (String) v.getTag();
            if (tag.equals("weather")) {
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

                                    for(int i = 0;i<=6;i++) {
                                        JSONArray list = response.getJSONArray("list");//spots配列の中身がココへだーっと返ってく
                                        JSONObject all_list = list.getJSONObject(i);
                                        System.out.println("---------------------" + all_list);

                                        String dt = all_list.getString("dt");
                                        System.out.println("dt=======" + dt);
                                        JSONObject temp = all_list.getJSONObject("temp");
                                        System.out.println("temp=======" + temp);
                                        String morn = temp.getString("morn");
                                        System.out.println("morn=======" + morn);
                                        String min = temp.getString("min");
                                        System.out.println("min=======" + min);
                                        String night = temp.getString("night");
                                        System.out.println("night=======" + night);
                                        String eve = temp.getString("eve");
                                        System.out.println("eve=======" + eve);
                                        String max = temp.getString("max");
                                        System.out.println("max=======" + max);
                                        String day = temp.getString("day");
                                        System.out.println("day=======" + day);


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
                                    }



    //                            if(get_text == null){//もしnullが返ってきたら
    //                                text2.setText(name);
    //                            }
    //                            else{
    //                                text2.setText(get_text);
    //                            }
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


    @Override
    public void onResume() {
        super.onResume();
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        final String provider = mLocationManager.getBestProvider(criteria, true);//どっちかの最適なプロバイダーを使う(GPSかNETWORKか)
        // LocationListenerを登録
        mLocationManager.requestLocationUpdates(provider, 10000, 0, listener);

//        System.out.println("latitude = " + latitude);
//        System.out.println("longitude = " + longitude);
//
//
//        //myQueue = Volley.newRequestQueue(this);
//        String requestURL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + latitude + "&lon=" + longitude + "&mode=json&cnt=7&199823890a1dd81d17944552d84ad932";
//        System.out.println("reqaaaaaaaaaaaaa = "+requestURL);
//        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,requestURL,(JSONObject)null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        System.out.println("response =========== "+response);
//                        try {
//
//                            //JSONArray json_spots = response.getJSONArray("");//spots配列の中身がココへだーっと返ってく
//                            String cod = response.getString("cod");
//
//                            System.out.println("cod======="+cod);
//
////                            if(get_text == null){//もしnullが返ってきたら
////                                text2.setText(name);
////                            }
////                            else{
////                                text2.setText(get_text);
////                            }
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
//                });
//
//

//        try {
//            URL url = new URL(requestURL);
//            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
//            connect.setRequestMethod("GET");
//            InputStream is = connect.getInputStream();
//            int responseCode = connect.getResponseCode();
//            if (responseCode / 100 == 4 || responseCode / 100 == 5) {
//                is = connect.getErrorStream();
//            } else {
//                is = connect.getInputStream();
//            }
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while (null != (line = reader.readLine())) {
//                sb.append(line);
//            }
//            String data = sb.toString();
//            System.out.println("------------------------------");
//            System.out.println("data=" + data);
//            System.out.println("------------------------------");
//
//        } catch (IOException e) {
//            System.out.println("e=" + e);
//        } catch (NullPointerException e) {
//            System.out.println("e==" + e);
//        }
//    }






    }




//
//
//
//        try {
//            url = new URL(requestURL);
//        }catch(MalformedURLException e){
//            e.printStackTrace();
//        }
//
//        try{
//            System.out.println("url==== "+url);
//            URLConnection con = url.openConnection();
//            is = con.getInputStream();
//        }catch(IOException e){
//            System.out.println(e);
//        }
//
//        try{
//            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//        }catch(UnsupportedEncodingException e){
//            System.out.println(e);
//        }
//
//        StringBuilder sb = new StringBuilder();
//        String line;
//
//        try {
//            while (null != (line = reader.readLine())) {
//                sb.append(line);
//            }
//        }catch(IOException e){
//            System.out.println(e);
//        }
//
//        String data = sb.toString();
//
//    }

    public void onPause(){
        if(mLocationManager != null )
        {
            mLocationManager.removeUpdates(listener);
        }

        super.onPause();

        Log.v("Status", "onPause");
    }
}