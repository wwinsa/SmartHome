package com.example.weixi.smarthome;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.weixi.smarthome.BlueToothActivity.REQUEST_ENABLE_BT;

public class MainActivity extends AppCompatActivity {

    private Button btn_temp,btn_humi,btn_smoke,btn_ch4,btn_bt;

    //bluetooth
    private BluetoothAdapter bluetoothAdapter;
    // 选中发送数据的蓝牙设备，全局变量，否则连接在方法执行完就结束了
    private BluetoothDevice selectDevice;
    private BluetoothSocket clientSocket;
    // 获取到向设备写的输出流，全局变量，否则连接在方法执行完就结束了
    private InputStream is;// 获取到输入流

    private final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");


    //data
   private ArrayList<Integer> temp =new ArrayList<Integer>();
   private int humi = 25 ;
   private int smoke = 25 ;
   private int meth = 25 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initUI();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 蓝牙未打开，询问打开
        if (!bluetoothAdapter.isEnabled()) {
            Intent turnOnBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnBtIntent, REQUEST_ENABLE_BT);
        }
        selectDevice = bluetoothAdapter.getRemoteDevice("98:D3:32:70:8B:76");
        if(selectDevice != null){
            Toast.makeText(this,"已连接",Toast.LENGTH_SHORT).show();
        }

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
//                        handler.sendMessage(msg);

                        temp.add(count);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }
        }).start();

        receivetemp();

        setListeners();
        btn_temp.setText("温度："+temp.get(temp.size()-1)+"度");
        btn_humi.setText("湿度"+humi);
        btn_smoke.setText("烟雾浓度"+smoke);
        btn_ch4.setText("甲醛浓度"+meth);
    }
    private void receivetemp(){
        temp.add(16);
        temp.add(25);
        temp.add(30);
        temp.add(28);
        temp.add(27);
        temp.add(20);
    }
    private void initUI(){
        btn_bt = findViewById(R.id.btn_bt);
        btn_temp = findViewById(R.id.btn_temp);
        btn_humi = findViewById(R.id.btn_humi);
        btn_smoke = findViewById(R.id.btn_smoke);
        btn_ch4 = findViewById(R.id.btn_ch4);

    }
    private void setListeners(){
        OnClick onClick = new OnClick();
        btn_temp.setOnClickListener(onClick);
        btn_humi.setOnClickListener(onClick);
        btn_smoke.setOnClickListener(onClick);
        btn_ch4.setOnClickListener(onClick);
        btn_bt.setOnClickListener(onClick);
    }
    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = null;
            Bundle bundle = new Bundle();

            switch (view.getId()){
                case R.id.btn_bt:
                    intent = new Intent(MainActivity.this,BlueToothActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_temp:
                    intent = new Intent(MainActivity.this,TempActivity.class);
                    bundle.putIntegerArrayList("temp",temp);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.btn_humi:
                    intent = new Intent(MainActivity.this,HumiActivity.class);
                    bundle.putIntegerArrayList("temp",temp);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    break;
                case R.id.btn_smoke:
                    intent = new Intent(MainActivity.this,Smoke2Activity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_ch4:
                    intent = new Intent(MainActivity.this,Ch4Activity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
