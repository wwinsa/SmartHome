package com.example.weixi.smarthome;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TempActivity extends AppCompatActivity {

    private Button btn_n_plus,btn_n_minus,btn_s_plus,btn_s_minus,btn_light;
    private TextView tv_n,tv_s;
    private ListView lv_showdata;

    private int num = 25 ;
    private int speed = 3 ;
    private ArrayList<Integer> temp = new ArrayList<Integer>();
    private ArrayAdapter<Integer> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        btn_n_minus = findViewById(R.id.btn_n_minus);
        btn_n_plus = findViewById(R.id.btn_n_plus);
        btn_s_minus = findViewById(R.id.btn_s_minus);
        btn_s_plus = findViewById(R.id.btn_s_plus);
        btn_light = findViewById(R.id.btn_light);
        tv_n = findViewById(R.id.tv_n_num);
        tv_s = findViewById(R.id.tv_s_num);
        lv_showdata = findViewById(R.id.lv_showdata);

        tv_n.setText(Integer.toString(num));
        tv_s.setText(Integer.toString(speed));
        setListeners();

        temp = this.getIntent().getIntegerArrayListExtra("temp");
//        Toast.makeText(getApplication(), temp.get(0).toString(), Toast.LENGTH_SHORT).show();
        adapter = new ArrayAdapter<Integer>(TempActivity.this,android.R.layout.simple_expandable_list_item_1,temp);
        lv_showdata.setAdapter(adapter);
        Toast.makeText(getApplication(), "test", Toast.LENGTH_SHORT).show();

//        String strData = "";
//        for (int i : temp) {
//            strData = strData + "/t" + temp.get(i).toString();
//        }
//        Toast.makeText(getApplication(), strData, Toast.LENGTH_SHORT).show();

    }

    private void showIngraph(ArrayList<Integer> temp){
        //要花钱，有时间再回来看
        //https://www.cnblogs.com/huolongluo/p/5988644.html
        //https://blog.csdn.net/u011084603/article/details/50365477
    }
    private void showInListView(ArrayList<Integer> temp){

    }
    private void setListeners(){
        TempActivity.OnClick onClick = new TempActivity.OnClick();
        btn_n_minus.setOnClickListener(onClick);
        btn_n_plus.setOnClickListener(onClick);
        btn_s_minus.setOnClickListener(onClick);
        btn_s_plus.setOnClickListener(onClick);
        btn_light.setOnClickListener(onClick);
    }
    private class OnClick implements View.OnClickListener{
        public boolean flag = true;
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.btn_light:
                    if(flag){
                        btn_light.setBackgroundResource(R.drawable.btn_light_on);
                        flag = false;
                    }
                    else {
                        btn_light.setBackgroundResource(R.drawable.btn_light_off);
                        flag = true;
                    }

                    break;
                case R.id.btn_n_minus:
                    if(flag && num > 15){
                        num --;
                        tv_n.setText(Integer.toString(num));
//                    Toast.makeText(TempActivity.this,"123",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.btn_n_plus:
                    if(flag && num < 37){
                        num ++;
                        tv_n.setText(Integer.toString(num));
                    }


                    break;
                case R.id.btn_s_minus:
                    if(flag && speed >1){
                        speed --;
                        tv_s.setText(Integer.toString(speed));
                    }

                    break;
                case R.id.btn_s_plus:
                    if(flag && speed < 6){
                        speed ++;
                        tv_s.setText(Integer.toString(speed));
                    }
                    break;
            }
        }
    }
}
