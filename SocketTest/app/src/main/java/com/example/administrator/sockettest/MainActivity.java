package com.example.administrator.sockettest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.sockettest.util.Exit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ClientThread.onReceiveMessageListener {

    private ListView lv;
    private EditText et;
    private TextView send;
    private List<String> list;
    private List<String> newList;
    private MyAdapter adapter;
    Socket socket = null;
    private boolean flag = true;
    private boolean isConnecting ;
    private InputStream is;
    private OutputStream os;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        init();
        receiveMessage();
    }

    private BufferedReader reader;
    private BufferedWriter writer;
    private Handler handler = new Handler();

    private void receiveMessage() {
//        try {
//            socket = new Socket(Inet4Address.getByName("192.168.10.103"), 10091);
//            ClientThread thread = new ClientThread(socket);
//            thread.setOnReceiveMessageListener(this);
//            thread.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag){
                    try {
                        socket = new Socket(Inet4Address.getByName("192.168.10.102"),10091);
                        if (socket != null) {
                            postMessage("成功与服务器建立连接");
                            isConnecting = true;
                        }
                        Log.e("message", socket.toString());
                        try {
                            is = socket.getInputStream();
                            os = socket.getOutputStream();
                            reader = new BufferedReader(new InputStreamReader(is));
                            writer = new BufferedWriter(new OutputStreamWriter(os));

                            Thread.sleep(50);
                            while (flag) {
                                try {
                                    String message = reader.readLine();
//                                    Log.e("message", message);
                                    if (message == null) {
                                        isConnecting = false;
                                        postMessage("与服务器断开连接，正在重新连接中......");
                                        Exit.closeAll(writer,reader,socket, os, is);
                                        break;
                                    }
                                    newList.add(message);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            list.clear();
                                            list.addAll(newList);
                                            adapter.notifyDataSetChanged();
                                        }
                                    },0);

                                } catch (UnknownHostException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        //延迟1秒后重新与服务器建立连接，以免客户端压力太大。
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void postMessage(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        initData();
        adapter = new MyAdapter(list);
        lv.setAdapter(adapter);
        lv.setDividerHeight(10);
    }

    private void initData() {
        list = new ArrayList<>();
        newList = new ArrayList<>();
        list.add("这是第一条数据");

        newList.addAll(list);
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);

        et = (EditText) findViewById(R.id.et);
        send = (TextView) findViewById(R.id.send);
    }

    private void initEvent() {
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!isConnecting) {
            Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            return ;
        }
        try {
            writer.write(et.getText().toString());
            writer.newLine();
            writer.flush();
            Toast.makeText(MainActivity.this, "发送成功 message: " + et.getText().toString(), Toast.LENGTH_SHORT).show();
            et.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
        isConnecting = false;
        Exit.closeAll(writer,reader,socket,os,is);
    }

    public void update(String message) {
        list.add(message);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        },10);
    }

    @Override
    public void onReceiveMessage(String message) {
        update(message);
    }
}
