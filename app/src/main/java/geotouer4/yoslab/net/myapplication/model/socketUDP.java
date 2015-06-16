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

    //�ʃX���b�h�Ŏ��s���قڕK�{
    public String getMessage(){

        Thread thread = new Thread() {
            public void run() {
                //5100�ԃ|�[�g���Ď�����UDP�\�P�b�g�𐶐�
                //DatagramSocket recieveSocket = null;
                try {
                    receiveSocket = new DatagramSocket(5100);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                //�󂯕t����f�[�^�o�b�t�@��UDP�p�P�b�g���쐬
                byte receiveBuffer[] = new byte[1024];

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);


                try {
                    receiveSocket.receive(receivePacket);

                } catch (IOException e) {
                    e.printStackTrace();
                }


                //��M�����f�[�^�����O�֏o��
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

    //�ʃX���b�h�Ŏ��s����ق����悢
    public void sendMessage(String message){
        //UDP�\�P�b�g�Ŏ�����IP��5100�ԃ|�[�g���w��
        InetSocketAddress remoteAddress = new InetSocketAddress("192.168.0.10",5100);

        //UDP�p�P�b�g�Ɋ܂߂�f�[�^
        byte[] sendBuffer = message.getBytes();

        //UDP�p�P�b�g
        DatagramPacket sendPacket;

        try {
            //DatagramPacket�C���X�^���X�𐶐����āCUDP�p�P�b�g�𑗐M
            sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, remoteAddress);
            new DatagramSocket().send(sendPacket);
        }catch (SocketException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
