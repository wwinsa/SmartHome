package com.example.weixi.smarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Smoke2Activity extends AppCompatActivity {

    private EditText et;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smoke2);

        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv_et);

        String text = et.getText().toString();
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
//        tv.setText(text);
    }
}
