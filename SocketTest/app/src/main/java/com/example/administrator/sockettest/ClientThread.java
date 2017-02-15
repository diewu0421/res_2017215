package com.example.administrator.sockettest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2017/2/14.
 */
public class ClientThread extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    public Boolean flag = true;
    private onReceiveMessageListener listener;

    public ClientThread(Socket socket) {
        this.socket = socket;

        try {

            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public interface onReceiveMessageListener {
        void onReceiveMessage(String message);
    }

    public void setOnReceiveMessageListener(onReceiveMessageListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        super.run();
        try {
            socket = new Socket(Inet4Address.getByName("192.168.10.103"), 10091);
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (flag) {
            try {

                String message = reader.readLine();
                listener.onReceiveMessage(message);

//                list.add(message);
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.notifyDataSetChanged();
//                    }
//                });

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
