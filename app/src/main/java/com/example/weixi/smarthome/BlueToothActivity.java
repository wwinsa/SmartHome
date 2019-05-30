package com.example.weixi.smarthome;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.opengl.ETC1;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;



public class BlueToothActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    public static final int REQUEST_ENABLE_BT = 11;
    public static final String NAME = "QiaoJimBluetooth";
    public static final int MSG_REV_A_CLIENT = 33;
    public static final int MSG_CLIENT_REV_NEW = 347;

    private TextView tv_test, tv_test2, tv_test3;
    private ArrayList<Integer> data = new ArrayList<Integer>();
    private ArrayAdapter<Integer> adapter;
    private ListView lv_showdata;

    // 选中发送数据的蓝牙设备，全局变量，否则连接在方法执行完就结束了
    private BluetoothDevice selectDevice;
    // 获取到选中设备的客户端串口，全局变量，否则连接在方法执行完就结束了
    private BluetoothSocket clientSocket;
    // 获取到向设备写的输出流，全局变量，否则连接在方法执行完就结束了
    private OutputStream os;
    // 服务端利用线程不断接受客户端信息
//    private AcceptThread thread;

    private InputStream is;// 获取到输入流

    private final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");

    private String tmp2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        tv_test = findViewById(R.id.tv_test);
        tv_test2 = findViewById(R.id.tv_test2);
        tv_test3 = findViewById(R.id.tv_test3);
        lv_showdata = findViewById(R.id.lv_showdata);

        // 蓝牙未打开，询问打开
        if (!bluetoothAdapter.isEnabled()) {
            Intent turnOnBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnBtIntent, REQUEST_ENABLE_BT);
        }

        selectDevice = bluetoothAdapter.getRemoteDevice("98:D3:32:70:8B:76");
        if(selectDevice != null){
            tv_test3.setText("已连接");
        }

//        tv_test2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //通过MAC获得蓝牙设备
//
//                // 这里需要try catch一下，以防异常抛出
//                try {
//                    // 判断客户端接口是否为空
//                    if (clientSocket == null) {
//                        // 获取到客户端接口
//                        clientSocket = selectDevice
//                                .createRfcommSocketToServiceRecord(MY_UUID);
//                        // 向服务端发送连接
//                        clientSocket.connect();
//                        // 获取到输出流，向外写数据
//                        os = clientSocket.getOutputStream();
//                        is = clientSocket.getInputStream();
//
//                    }
//
//                    // 判断是否拿到输出流
//                    if (os != null) {
//                        // 需要发送的信息
//                        int text = 1101;
//                        // 以utf-8的格式发送出去
//                        os.write(text);
////                        tv_test2.setText(text);
//                        toast("发送信息成功，请查收");
//                    }
//                    // 吐司一下，告诉用户发送成功
//                    toast("我不知道发送信息成功没有");
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                    // 如果发生异常则告诉用户发送失败
//                    toast("发送信息失败");
//                }
//
//            }
//        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 判断客户端接口是否为空
                    if (clientSocket == null) {
                        // 获取到客户端接口
                        clientSocket = selectDevice
                                .createRfcommSocketToServiceRecord(MY_UUID);
                        // 向服务端发送连接
                        clientSocket.connect();
                        // 获取到输入流
                        is = clientSocket.getInputStream();

                    }

                    // 无线循环来接收数据
                    while (true) {
                        // 创建一个128字节的缓冲
                        byte[] buffer = new byte[128];
                        // 每次读取128字节，并保存其读取的角标
                        int count = is.read(buffer);
                        // 创建Message类，向handler发送数据
                        Message msg = new Message();
                        // 发送一个String的数据，让他向上转型为obj类型
                        msg.obj = new String(buffer, 0, count, "utf-8");
                        // 发送数据
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }
        }).start();

    }



    // 创建handler，因为我们接收是采用线程来接收的，在线程中无法操作UI，所以需要handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            //通过msg传递过来的信息，吐司一下收到的信息
             Toast.makeText(BlueToothActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
            //不能显示1位数据
             String tmp = ((String) msg.obj).toString();
             if(tmp.length() == 1){
                 tmp2 = tmp;
             }else{
                 tmp = tmp2 + tmp;
                 data.add(Integer.parseInt(tmp));
                 adapter = new ArrayAdapter<Integer>(BlueToothActivity.this,android.R.layout.simple_expandable_list_item_1,data);
                 lv_showdata.setAdapter(adapter);
             }

        }
    };



    /**
     * Toast 提示
     */
    public void toast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
