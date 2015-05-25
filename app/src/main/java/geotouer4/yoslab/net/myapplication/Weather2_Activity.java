package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.URL;

import geotouer4.yoslab.net.myapplication.adapters.WeatherAdapter;

public class Weather2_Activity extends Activity {
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
    public WeatherAdapter mWeatherAdapter;
    ImageLoader mImageLoader;


    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather2);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//        Intent data = getIntent();//前の画面で選択したデータを受けとる
//        int Data = data.getIntExtra("int_ID", 0);//Integer型のDataに保存

        // Volley に用意された ImageView の拡張クラス NetworkImageView を生成する
        NetworkImageView imageView = new NetworkImageView(this);
        setContentView(imageView);

        myQueue = Volley.newRequestQueue(this);

        // 指定したURLから画像を取得する
        String url = "http://openweathermap.org/img/w/04d.png";
        imageView.setImageUrl(url, new ImageLoader(myQueue, new ImageLoader.ImageCache() {
            // キャッシュ処理が必要な場合はここに記述します
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        }));
    }
}

