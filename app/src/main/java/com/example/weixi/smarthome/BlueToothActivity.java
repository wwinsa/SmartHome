package com.example.weixi.smarthome;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class BlueToothActivity extends AppCompatActivity {

    final String TAG = "DeviceListFragment";
    private BluetoothAdapter bluetoothAdapter;
    private IntentFilter intentFilter;
    private MyBtReceiver btReceiver;

    private TextView tv_test, tv_test2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        tv_test = findViewById(R.id.tv_test);
        tv_test2 = findViewById(R.id.tv_test2);

        // 蓝牙未打开，询问打开
        if (!bluetoothAdapter.isEnabled()) {
            Intent turnOnBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnBtIntent, Params.REQUEST_ENABLE_BT);
        }

//        bluetoothAdapter.startDiscovery();
//        intentFilter = new IntentFilter();
//        btReceiver = new MyBtReceiver();
//        //监听 搜索开始，搜索结束，发现新设备 3条广播
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
//
//
////        getActivity().registerReceiver(btReceiver, intentFilter);
//        Context context = getApplicationContext();
//        Intent intent = getIntent();
//        btReceiver.onReceive(context,intent);

        //通过MAC获得蓝牙设备
        bluetoothAdapter.getRemoteDevice("98:D3:32:70:8B:76");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Params.REQUEST_ENABLE_BT: {
                //用户打开蓝牙
                if (resultCode == RESULT_OK) {
                    //显示已绑定蓝牙设备
                    showBondDevice();
                    toast("connect");
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
    private class MyBtReceiver extends BroadcastReceiver {
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
                    Log.e(TAG, "---------------- " + device.getName());
                }
            }
        }
    }
    /**
     * Toast 提示
     */
    public void toast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
