package geotouer4.yoslab.net.myapplication.model;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Created by s175029 on 2015/06/14.
 */
public class socketUDP {

    private DatagramSocket receiveSocket;
    private String receiveData;
    private String message;

    //別スレッドで実行がほぼ必須
    public String getMessage(){

        Thread thread = new Thread() {
            public void run() {
                //5100番ポートを監視するUDPソケットを生成
                //DatagramSocket recieveSocket = null;
                try {
                    receiveSocket = new DatagramSocket(5100);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                //受け付けるデータバッファとUDPパケットを作成
                byte receiveBuffer[] = new byte[1024];

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);


                try {
                    receiveSocket.receive(receivePacket);

                } catch (IOException e) {
                    e.printStackTrace();
                }


                //受信したデータをログへ出力
                message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                Log.d("TEST", message);
            }
        };
        thread.start();

        try {
            thread.join();
        }catch(InterruptedException e) {
            Log.d("TEST",e.toString());
        }

        return message;
    }

    //別スレッドで実行するほうがよい
    public void sendMessage(String message){
        //UDPソケットで自分のIPで5100番ポートを指定
        InetSocketAddress remoteAddress = new InetSocketAddress("133.42.155.239",5100);

        //UDPパケットに含めるデータ
        byte[] sendBuffer = message.getBytes();

        //UDPパケット
        DatagramPacket sendPacket;

        try {
            //DatagramPacketインスタンスを生成して，UDPパケットを送信
            sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, remoteAddress);
            new DatagramSocket().send(sendPacket);
        }catch (SocketException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
