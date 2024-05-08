//作者：HZF
package com.ff.access;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        Button buttonSend = findViewById(R.id.button_one);
//        buttonSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendDataAsync("9E0422751A2503000000000000000000000000000000000000000000000000000000017B9D");
//            }
//
//        });
//    }
//    private void sendDataAsync(String zf) {
//        new SendDataAsyncTask().execute(zf);
//    }
    }

    public void fei(View zf) {
        sendDataAsync("9E0422751A2503000000000000000000000000000000000000000000000000000000017B9D");
    }

    private void sendDataAsync(String zf) {
        new SendDataAsyncTask().execute(zf);

    }


    private class SendDataAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            DatagramSocket udpClient = null;
            try {
                udpClient = new DatagramSocket();
//                InetAddress serverAddress = InetAddress.getByName("125.88.11.6");
//                InetAddress serverAddress = InetAddress.getByName("10.10.2.21");
                InetAddress serverAddress = InetAddress.getByName("192.168.15.88");
                //               final int serverPort = 7000;
//                final int serverPort = 8088;
                final int serverPort = 60000;
                byte[] sendData = hexStringToByteArray(params[0]);
                DatagramPacket packet = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                udpClient.send(packet);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (udpClient != null && !udpClient.isClosed()) {
                    udpClient.close();
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                showToast("开锁成功，已发送指令");
            } else {
                showToast("开锁失败，请检查网络连接或服务器设置或重启软件");
            }
        }
    }

    private void showToast(String zf) {
        Toast.makeText(this, zf, Toast.LENGTH_SHORT).show();
    }


    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}



