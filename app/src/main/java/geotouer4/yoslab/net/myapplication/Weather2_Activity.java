package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import geotouer4.yoslab.net.myapplication.model.Weather;
//import geotouer4.yoslab.net.myapplication.model.Weather;

public class Weather2_Activity extends Activity {
    private LocationManager mLocationManager;
    private RequestQueue myQueue;
    private double latitude;
    private double longitude;
    private URL url;
    private InputStream is;
    private BufferedReader reader;
    private ListView lv;
    public String requestURL;
    public int id;
    private ListView mListView;
    public ArrayList<Weather> weathers;
    static List<String> items = new ArrayList<String>();
    static ArrayAdapter<String> adapter;



    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather2);
        mListView = (ListView)findViewById(R.id.listView);

        items = new ArrayList();
//        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Intent data = getIntent();//前の画面で選択したデータを受けとる
        ArrayList<String> mainArray = data.getStringArrayListExtra("main");
        ArrayList<String> iconArray = data.getStringArrayListExtra("icon");
        ArrayList<Integer> maxArray = data.getIntegerArrayListExtra("max2");
        ArrayList<Integer> minArray = data.getIntegerArrayListExtra("min2");

        System.out.println("mainArray==============="+mainArray);

        myQueue = Volley.newRequestQueue(this);//ネットワーク通信

        for(int i=0;i<=6;i++) {

            if (mainArray.get(i).equals("Clear")) {
                System.out.println("晴れ！");

                items.add("最高気温：" + maxArray.get(i));
                items.add("最低気温：" + minArray.get(i));

                adapter = new ArrayAdapter<String>(this,
                        R.layout.row,
                        R.id.text_weather,
                        items);

                mListView.setAdapter(adapter);
            }

            else if (mainArray.get(i).equals("Clouds")) {
                System.out.println("曇り！");

                items.add("最高気温：" + maxArray.get(i));
                items.add("最低気温：" + minArray.get(i));

                adapter = new ArrayAdapter<String>(this,
                        R.layout.row2,
                        R.id.text_weather,
                        items);

                mListView.setAdapter(adapter);
            }

            else if (mainArray.get(i).equals("Rain")) {
                System.out.println("雨！");

                items.add("最高気温：" + maxArray.get(i));
                items.add("最低気温：" + minArray.get(i));


                adapter = new ArrayAdapter<String>(this,
                        R.layout.row3,
                        R.id.text_weather,
                        items);

                mListView.setAdapter(adapter);
            }

            else if (mainArray.get(i).equals("Snow")) {
                System.out.println("雪！");

                items.add("最高気温：" + maxArray.get(i));
                items.add("最低気温：" + minArray.get(i));

                adapter = new ArrayAdapter<String>(this,
                        R.layout.row4,
                        R.id.text_weather,
                        items);

                mListView.setAdapter(adapter);
            }
        }
    }
}

