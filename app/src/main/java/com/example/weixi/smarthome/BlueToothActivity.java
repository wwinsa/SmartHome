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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


import static com.example.weixi.smarthome.Params.NAME;

public class BlueToothActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private IntentFilter intentFilter;
//    private MyBtReceiver btReceiver;

    private TextView tv_test, tv_test2, tv_test3;
    private EditText et;

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
//    //输入信息
//    private int text = 1110 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        tv_test = findViewById(R.id.tv_test);
        tv_test2 = findViewById(R.id.tv_test2);
        tv_test3 = findViewById(R.id.tv_test3);
        et = findViewById(R.id.et);

//        String stringport = et.getText().toString().trim();
//        text = Integer.parseInt(stringport);//把port转换成int整形

//        et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //String获取信息
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        // 蓝牙未打开，询问打开
        if (!bluetoothAdapter.isEnabled()) {
            Intent turnOnBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnBtIntent, Params.REQUEST_ENABLE_BT);
        }


//        intentFilter = new IntentFilter();
//        btReceiver = new MyBtReceiver();
//        //监听 搜索开始，搜索结束，发现新设备 3条广播
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);

        bluetoothAdapter.startDiscovery();
        // 因为蓝牙搜索到设备和完成搜索都是通过广播来告诉其他应用的
        // 这里注册找到设备和完成搜索广播
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

//
//
////        getActivity().registerReceiver(btReceiver, intentFilter);
//        Context context = getApplicationContext();
//        Intent intent = getIntent();
//        btReceiver.onReceive(context,intent);



//        Handler uiHandler =new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//
//                switch (msg.what) {
//                    case Params.MSG_REV_A_CLIENT:
//                        Log.e(TAG, "--------- uihandler set device name, go to data frag");
//                        BluetoothDevice clientDevice = (BluetoothDevice) msg.obj;
//                        dataTransFragment.receiveClient(clientDevice);
//                        viewPager.setCurrentItem(1);
//                        break;
//                    case Params.MSG_CONNECT_TO_SERVER:
//                        Log.e(TAG, "--------- uihandler set device name, go to data frag");
//                        BluetoothDevice serverDevice = (BluetoothDevice) msg.obj;
//                        dataTransFragment.connectServer(serverDevice);
//                        viewPager.setCurrentItem(1);
//                        break;
//                    case Params.MSG_SERVER_REV_NEW:
//                        String newMsgFromClient = msg.obj.toString();
//                        dataTransFragment.updateDataView(newMsgFromClient, Params.REMOTE);
//                        break;
//                    case Params.MSG_CLIENT_REV_NEW:
//                        String newMsgFromServer = msg.obj.toString();
//                        dataTransFragment.updateDataView(newMsgFromServer, Params.REMOTE);
//                        break;
//                    case Params.MSG_WRITE_DATA:
//                        String dataSend = msg.obj.toString();
//                        dataTransFragment.updateDataView(dataSend, Params.ME);
//                        deviceListFragment.writeData(dataSend);
//                        break;
//
//                }
//            }
//        };
        tv_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // 获取到这个设备的信息
//                String s = arrayAdapter.getItem(position);
//                // 对其进行分割，获取到这个设备的地址
//                String address = s.substring(s.indexOf(":") + 1).trim();
//                Log.d("TAG",address);
                // 判断当前是否还是正在搜索周边设备，如果是则暂停搜索
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }
                // 如果选择设备为空则代表还没有选择设备
                //通过MAC获得蓝牙设备
                selectDevice = bluetoothAdapter.getRemoteDevice("98:D3:32:70:8B:76");
                if(selectDevice != null){
                    tv_test3.setText("已连接");
                }
//                if (selectDevice == null) {
//                    //通过地址获取到该设备
//                    selectDevice = bluetoothAdapter.getRemoteDevice(address);
//                }
                // 这里需要try catch一下，以防异常抛出
                try {
                    // 判断客户端接口是否为空
                    if (clientSocket == null) {
                        // 获取到客户端接口
                        clientSocket = selectDevice
                                .createRfcommSocketToServiceRecord(MY_UUID);
                        // 向服务端发送连接
                        clientSocket.connect();
                        // 获取到输出流，向外写数据
                        os = clientSocket.getOutputStream();
                        is = clientSocket.getInputStream();
                        toast("socket是空");
                    }
                    // 判断是否拿到输出流
                    if (os != null) {
                        // 需要发送的信息
                        int text = 1110 ;
                        // 以utf-8的格式发送出去
                        os.write(text);
//                        tv_test2.setText(text);
                        toast("发送信息成功，请查收");
                    }
                    // 吐司一下，告诉用户发送成功
                    toast("我不知道发送信息成功没有");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    // 如果发生异常则告诉用户发送失败
                    toast("发送信息失败");
                }

            }
        });

//        // 实例接收客户端传过来的数据线程
//        thread = new AcceptThread();
//        // 线程开始
//        thread.start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                byte[] buffer = new byte[1024];
//                int len;
//                String content;
//                try {
//                    //读数据
//                    while ((len=is.read(buffer)) != -1) {
//                        content=new String(buffer, 0, len);
//                        Message message = new Message();
//                        message.what = Params.MSG_CLIENT_REV_NEW;
//                        message.obj = content;
//                        //更新 ui
//                        handler.sendMessage(message);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Params.REQUEST_ENABLE_BT: {
                //用户打开蓝牙
                if (resultCode == RESULT_OK) {
                    //显示已绑定蓝牙设备
                    showBondDevice();
//                    toast("connect");
                }
                break;
            }
            case Params.REQUEST_ENABLE_VISIBILITY: {
                //设置该蓝牙设备可被其他设备发现,600是设置的设备可发现时间（博客最后有简单说明）
                if (resultCode == 600) {
                    toast("蓝牙已设置可见");
                } else if (resultCode == RESULT_CANCELED) {
                    toast("蓝牙设置可见失败,请重试");
                }
                break;
            }
        }
    }


    private void showBondDevice() {

        Set<BluetoothDevice> tmp = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice d : tmp) {
            tv_test.setText(tmp.toString());
        }

    }

    //广播接收器
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                toast("开始搜索 ...");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                toast("搜索结束");
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //获得发现的设备
                BluetoothDevice device =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    tv_test2.setText(device.toString());
                    toast("08241124");

                }
            }
        }
    };

    // 创建handler，因为我们接收是采用线程来接收的，在线程中无法操作UI，所以需要handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            // 通过msg传递过来的信息，吐司一下收到的信息
             Toast.makeText(BlueToothActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
             tv_test3.setText(msg.obj.toString());
//            re_msg.setText((String)msg.obj);
        }
    };



//    // 服务端接收信息线程
//    private class AcceptThread extends Thread {
//        private BluetoothServerSocket serverSocket;// 服务端接口
//        private BluetoothSocket socket;// 获取到客户端的接口
//        private InputStream is;// 获取到输入流
//        private OutputStream os;// 获取到输出流
//
//        public AcceptThread() {
//            try {
//                // 通过UUID监听请求，然后获取到对应的服务端接口
//                serverSocket = bluetoothAdapter
//                        .listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        public void run() {
//            try {
//                // 接收其客户端的接口
//                socket = serverSocket.accept();
//                // 获取到输入流
//                is = socket.getInputStream();
//                // 获取到输出流
//                os = socket.getOutputStream();
//
//                // 无线循环来接收数据
//                while (true) {
//                    // 创建一个128字节的缓冲
//                    byte[] buffer = new byte[128];
//                    // 每次读取128字节，并保存其读取的角标
//                    int count = is.read(buffer);
//                    // 创建Message类，向handler发送数据
//                    Message msg = new Message();
//                    // 发送一个String的数据，让他向上转型为obj类型
//                    msg.obj = new String(buffer, 0, count, "utf-8");
//                    // 发送数据
//                    handler.sendMessage(msg);
//                }
//            } catch (Exception e) {
//                // TODO: handle exception
//                e.printStackTrace();
//            }
//
//        }
//    }


    /**
     * Toast 提示
     */
    public void toast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
