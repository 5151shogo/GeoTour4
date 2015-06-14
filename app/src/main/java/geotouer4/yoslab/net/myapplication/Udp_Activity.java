package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import geotouer4.yoslab.net.myapplication.model.socketUDP;

public class Udp_Activity extends Activity {

    private ToneGenerator toneGenerator = new ToneGenerator(
            AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME);
    private StringBuffer cbuf = new StringBuffer(128);
    int n;
    private DatagramSocket ds;
    private DatagramPacket dp;
    private String IP_remote;
    private int port_remote;
    private RequestQueue myQueue;
    private TextView tv;
    private TextView tv2;
    public socketUDP Listener = new socketUDP();
    public String receiveValue;
    private String[] socketSplit = new String[100];
    public TextView txtError;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

//        LinearLayout layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        setContentView(layout);
//        txtError = new TextView(this);
//        txtError.setTextSize(15);
//        layout.addView(txtError);
//
//
//        myQueue = Volley.newRequestQueue(this);
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    InetAddress host = InetAddress.getByName("133.42.155.239");
//                    int port = 11000;
//                    String message = "AAA";
//                    byte[] data = message.getBytes();
//                    DatagramSocket ds = new DatagramSocket(); //ソケット作成
//                    DatagramPacket dp = new DatagramPacket(data, data.length, host, port);
//                    ds.send(dp);
//                    ds.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//}







            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            final Button button1 = new Button(this);
            Button button2 = new Button(this);
            tv = new TextView(this);
            tv2 = new TextView(this);
            tv.setText("カウンタ");
            button1.setText("send to PC");
            button2.setText("-");

            //レイアウト
            linearLayout.addView(button1, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(button2, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(tv, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(tv2, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            setContentView(linearLayout);

            IP_remote = "133.42.155.239"; //IPアドレス
            port_remote = 1234;       //ポート番号

            // Button1 がクリックされた時に呼び出されるコールバックを登録
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    n++;
                    // 文字列を作る
                    cbuf.append(n);
                    tv.setText(cbuf.toString());  // tv には final が必要
                    cbuf.delete(0, 99);
                    toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);




                    // UDP　送信
                    runOnUiThread(new Runnable(){
                        public void run() {
                            try {
                                InetAddress host = InetAddress.getByName(IP_remote);
                                String message = "send by Android " + n + " \n";  // 送信メッセージ
                                ds = new DatagramSocket();  //DatagramSocket 作成
                                byte[] data = message.getBytes();
                                dp = new DatagramPacket(data, data.length, host, port_remote);  //DatagramPacket 作成
                                ds.send(dp);
                                System.out.println("Success Send");
                            } catch (Exception e) {
//                                System.err.println("Exception : " + e);
                                System.out.println("failed Send");
                            }
                        }
                    });
                }
            });

            // カウンタの減少
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    n--;
                    cbuf.append(n);
                    tv.setText(cbuf.toString());
                    cbuf.delete(0, 99);
                    toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
                }
            });

            n=0;        // カウント値の初期値
            try{
                InetAddress host = InetAddress.getByName(IP_remote);      // IPアドレス
                String message = "Send by Android";  // 送信メッセージ
                ds = new DatagramSocket();  //DatagramSocket 作成
                byte[] data = message.getBytes();
                dp = new DatagramPacket(data, data.length, host, port_remote);  //DatagramPacket 作成
                System.out.println("Success shokika");
            }catch(Exception e){
//                System.err.println("Exception : " + e);
                System.out.println("failed shokika");
            }
            // end OnCreat()
        }
    }
