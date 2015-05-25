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
import android.widget.Spinner;
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
    private ListView lv;
    public String morn3;
    public String min3;
    public String night3;
    public String eve3;
    public String max3;
    public String day3;
    public String requestURL;
    public int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        myQueue = Volley.newRequestQueue(this);

        // ボタンオブジェクトオブジェクト取得(次へ)
        Button button1 = (Button) findViewById(R.id.bt_button);
        button1.setTag("weather");
        button1.setOnClickListener(new ButtonClickListener());
    }


    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            String tag = (String) v.getTag();
            if (tag.equals("weather")) {//ここから

                Spinner AREA1 = (Spinner)findViewById(R.id.sp_place2);

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

                System.out.println("kkkkkkaaaaaaaaaaaaa = "+requestURL);

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



//
//                                    String[] member = {morn3,min3,night3,eve3,max3,day3};
//                lv = (ListView) findViewById(R.id.listView1);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_list_item_1, member);
//
//                lv.setAdapter(adapter);

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

