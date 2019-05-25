package com.example.weixi.smarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn_temp,btn_humi,btn_smoke,btn_ch4;
    //data
   private ArrayList<Integer> temp =new ArrayList<Integer>();
    private int humi = 25 ;
    private int smoke = 25 ;
    private int meth = 25 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_temp = findViewById(R.id.btn_temp);
        btn_humi = findViewById(R.id.btn_humi);
        btn_smoke = findViewById(R.id.btn_smoke);
        btn_ch4 = findViewById(R.id.btn_ch4);
        inittemp(25);
        inittemp(30);
        inittemp(16);
        setListeners();
        btn_temp.setText("温度："+temp.get(temp.size()-1)+"度");
        btn_humi.setText("湿度"+humi);
        btn_smoke.setText("烟雾浓度"+smoke);
        btn_ch4.setText("甲醛浓度"+meth);
    }
    private void inittemp(int num){
        temp.add(num);
    }
    private void setListeners(){
        OnClick onClick = new OnClick();
        btn_temp.setOnClickListener(onClick);
        btn_humi.setOnClickListener(onClick);
        btn_smoke.setOnClickListener(onClick);
        btn_ch4.setOnClickListener(onClick);
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
