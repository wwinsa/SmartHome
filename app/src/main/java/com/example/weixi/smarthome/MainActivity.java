package com.example.weixi.smarthome;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.weixi.smarthome.BlueToothActivity.REQUEST_ENABLE_BT;

public class MainActivity extends AppCompatActivity {

    private Button btn_temp,btn_humi,btn_smoke,btn_ch4,btn_send, btn_rep;

    //bluetooth
    private BluetoothAdapter bluetoothAdapter;
    // 选中发送数据的蓝牙设备，全局变量，否则连接在方法执行完就结束了
    private BluetoothDevice selectDevice;
    private BluetoothSocket clientSocket;


    // 获取到向设备写的输出流，全局变量，否则连接在方法执行完就结束了
    private InputStream is;// 获取到输入流
    private OutputStream os;

    private final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");


    //data
   private ArrayList<Integer> temp =new ArrayList<Integer>();
   private ArrayList<Integer> humi =new ArrayList<Integer>();
   private ArrayList<Integer> smoke =new ArrayList<Integer>();
   private ArrayList<Integer> ch4 =new ArrayList<Integer>();

   //thread
    boolean flag = false;
    String tmp1, tmp2, s , s2 = "0";
    int wx1, wx2, wx3;

    int sig = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initUI();
        initdata();
        setListeners();

        bt();

        btn_temp.setText("温度："+temp.get(temp.size()-1)+"度");
        btn_humi.setText("湿度"+humi.get(humi.size()-1));
        btn_smoke.setText("烟雾浓度"+smoke.get(smoke.size()-1));
        btn_ch4.setText("甲醛浓度"+ch4.get(ch4.size()-1));
    }



    private void bt(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 蓝牙未打开，询问打开
        if (!bluetoothAdapter.isEnabled()) {
            Intent turnOnBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnBtIntent, REQUEST_ENABLE_BT);
        }
        //通过地址绑定进行蓝牙连接
        selectDevice = bluetoothAdapter.getRemoteDevice("98:D3:32:70:8B:76");
        if(selectDevice != null){
            Toast.makeText(this,"已连接",Toast.LENGTH_SHORT).show();
        }

        //receive
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
                        Log.d("Connect","connected");
                        os = clientSocket.getOutputStream();
                    }

                    // 无线循环来接收数据
                    while (true) {
//                        // 创建一个128字节的缓冲
//                        byte[] buffer = new byte[128];
//
//                        // 每次读取128字节，并保存其读取的角标
//                        int count = is.read(buffer);

//                        //数据分段
//                        //手动整合
//                        if(flag){
//                            tmp2 = new String(buffer,0,count,"utf-8");
//
//                            s =tmp1 + tmp2;
//                            Log.d("UBG",s);
//                            for(int i =0 ; i < s.length(); i++){
//                                if(s.charAt(i) > '0' && s.charAt(i) < '9'){
//                                    s2 = s2 + s.charAt(i);
//                                }
//                            }
//
//                            temp.add(Integer.parseInt(s2));
//                            flag = false;
//                        }
//                        else{
//                            flag = true;
//                            s2 = "0";
//                            tmp1 = new String(buffer,0,count,"utf-8");
//                        }
//
//                        // 创建Message类，向handler发送数据
//                        Message msg = new Message();
//                        // 发送一个String的数据，让他向上转型为obj类型
//                        msg.obj = s;
//                        // 发送数据
//                        handler.sendMessage(msg);

                        int tmp , t2, t3;

                        tmp =  is.read();
                        Log.d("INT->",String.valueOf(tmp));

                        if(tmp == 1){
                            t2 =  is.read();
                            t3 = is.read();
                            Log.d("INT2->",String.valueOf(t3));
                            ch4.add(t3);
                            s = String.valueOf(t3);
                        }
                        if(tmp == 2){
                            t2 =  is.read();
                            t3 = is.read();
                            Log.d("INT3->",String.valueOf(t3));
                            smoke.add(t3);
                            s = String.valueOf(t3);
                        }


                        //数据分段
                        //手动整合
//                        if(flag){
//                            wx2 = is.read();
//                            tmp2 = String.valueOf(wx2);
//
//                            s =tmp1 + tmp2;
//                            Log.d("UBG",s);
//                            for(int i =0 ; i < s.length(); i++){
//                                if(s.charAt(i) > '0' && s.charAt(i) < '9'){
//                                    s2 = s2 + s.charAt(i);
//                                }
//                            }
//
//                            temp.add(Integer.parseInt(s2));
//                            flag = false;
//                        }
//                        else if(flag == false){
//                            flag = true;
//                            s2 = "0";
//                            wx1 = is.read();
//                            tmp1 = String.valueOf(wx1);
//
//                        }

                        // 创建Message类，向handler发送数据
                        Message msg = new Message();
                        // 发送一个String的数据，让他向上转型为obj类型
                        msg.obj = s;
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




    private void initdata(){
        temp.add(25);
        temp.add(27);
        temp.add(30);
        temp.add(35);
        temp.add(23);

        humi.add(25);
        humi.add(27);
        humi.add(30);
        humi.add(35);
        humi.add(23);

        smoke.add(3);
        smoke.add(25);
        smoke.add(10);
        ch4.add(10);
        ch4.add(21);
        ch4.add(24);

    }
    private void initUI(){

        btn_temp = findViewById(R.id.btn_temp);
        btn_humi = findViewById(R.id.btn_humi);
        btn_smoke = findViewById(R.id.btn_smoke);
        btn_ch4 = findViewById(R.id.btn_ch4);
        btn_send = findViewById(R.id.btn_send);
        btn_rep = findViewById(R.id.btn_rep);

    }
    private void setListeners(){
        OnClick onClick = new OnClick();
        btn_temp.setOnClickListener(onClick);
        btn_humi.setOnClickListener(onClick);
        btn_smoke.setOnClickListener(onClick);
        btn_ch4.setOnClickListener(onClick);

        btn_send.setOnClickListener(onClick);
        btn_rep.setOnClickListener(onClick);
    }
    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = null;
            Bundle bundle = new Bundle();

            switch (view.getId()){
                case R.id.btn_temp:
                    intent = new Intent(MainActivity.this,TempActivity.class);
                    bundle.putIntegerArrayList("temp",temp);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.btn_humi:
                    intent = new Intent(MainActivity.this,HumiActivity.class);
                    bundle.putIntegerArrayList("humi",humi);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    break;
                case R.id.btn_smoke:
                    intent = new Intent(MainActivity.this,Smoke2Activity.class);
                    bundle.putIntegerArrayList("smoke",smoke);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.btn_ch4:
                    intent = new Intent(MainActivity.this,Ch4Activity.class);
                    bundle.putIntegerArrayList("ch4",ch4);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.btn_rep:
                    intent = new Intent(MainActivity.this,ReportActivity.class);
                    bundle.putIntegerArrayList("smoke",smoke);
                    intent.putExtras(bundle);
                    bundle.putIntegerArrayList("ch4",ch4);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.btn_send:
                    //send
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 获取到输出流，向外写数据
                                os = clientSocket.getOutputStream();
                                // 判断是否拿到输出流
                                if (os != null) {
                                    // 需要发送的信息
                                    byte[] text = {2};
                                    // 以utf-8的格式发送出去
                                    os.write(text);
                                }
                                toast("发送信息成功，请查收");
                            } catch (Exception e) {
                                // TODO: handle exception
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
            }
        }
    }

    // 创建handler，因为我们接收是采用线程来接收的，在线程中无法操作UI，所以需要handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            //通过msg传递过来的信息，吐司一下收到的信息
            Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
            btn_temp.setText("温度："+temp.get(temp.size()-1)+"度");
            btn_humi.setText("湿度："+humi.get(humi.size()-1));
            btn_smoke.setText("烟雾浓度"+smoke.get(smoke.size()-1));
            btn_ch4.setText("甲醛浓度"+ch4.get(ch4.size()-1));

        }
    };

    public void toast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
