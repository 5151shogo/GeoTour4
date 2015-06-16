package geotouer4.yoslab.net.myapplication;

import android.app.Activity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Udp_get_Activity extends Activity{
    public static void main(String[] args) throws Exception {
        byte[] buf = new byte[256];
        int portNumber = 49152;//ポート番号
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        DatagramSocket recSocket = new DatagramSocket(portNumber);//UDP受信用ソケット

        System.out.println("受信待機状態");
        recSocket.receive(packet);//受信& wait
        int len = packet.getLength();//受信バイト数取得
        String msg = new String(buf, 0, len, "MS932");
        System.out.println(msg + ":以上" + len + "byte を受信しました。");

        recSocket.close();
    }
}
