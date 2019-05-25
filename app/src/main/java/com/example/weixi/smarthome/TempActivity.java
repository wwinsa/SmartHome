package com.example.weixi.smarthome;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TempActivity extends AppCompatActivity {

    private Button btn_n_plus,btn_n_minus,btn_s_plus,btn_s_minus,btn_light;
    private TextView tv_n,tv_s;

    private int num = 25 ;
    private int speed = 3 ;
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


        setListeners();
        tv_n.setText(Integer.toString(num));
        tv_s.setText(Integer.toString(speed));

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
                        btn_light.setBackgroundResource(R.drawable.btn_light_off);
                        flag = false;
                    }
                    else {
                        btn_light.setBackgroundResource(R.drawable.btn_light_on);
                        flag = true;
                    }

                    break;
                case R.id.btn_n_minus:
                    num --;
                    tv_n.setText(Integer.toString(num));
//                    Toast.makeText(TempActivity.this,"123",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_n_plus:
                    num ++;
                    tv_n.setText(Integer.toString(num));

                    break;
                case R.id.btn_smoke:
                    speed --;
                    tv_s.setText(Integer.toString(speed));
                    break;
                case R.id.btn_ch4:
                    speed --;
                    tv_s.setText(Integer.toString(speed));
                    break;
            }
        }
    }
}
