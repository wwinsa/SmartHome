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

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class TempActivity extends AppCompatActivity {

    private Button btn_n_plus,btn_n_minus,btn_s_plus,btn_s_minus,btn_light;
    private TextView tv_n,tv_s;

    private int num = 25 ;
    private int speed = 3 ;
    private ArrayList<Integer> temp = new ArrayList<Integer>();

    //chart
    private LineChartView mChartView;
    private LineChartData data;          // 折线图封装的数据类

    private ArrayList<PointValue> values, std;
    private ArrayList<Line> lines;
    private Line line, line2;


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
        mChartView = findViewById(R.id.chart_temp);

        tv_n.setText(Integer.toString(num));
        tv_s.setText(Integer.toString(speed));
        setListeners();

        temp = this.getIntent().getIntegerArrayListExtra("temp");
        Toast.makeText(this,temp.get(temp.size()-1).toString(),Toast.LENGTH_SHORT).show();

        values = new ArrayList<PointValue>();//折线上的点
        std = new ArrayList<PointValue>();

        for (int i = 0; i < temp.size(); i++){
            values.add(new PointValue(i, temp.get(i)));
            std.add(new PointValue(i,28));
        }

        drawchart();


    }

    private void drawchart(){

        //line
        line = new Line(values).setColor(Color.BLUE);//声明线并设置颜色
        line2 = new Line(std).setColor(Color.parseColor("#336633"));
        line.setCubic(false);//设置是平滑的还是直的
        line2.setHasPoints(false);
        lines = new ArrayList<Line>();
        lines.add(line2);
        lines.add(line);

        data = new LineChartData();
        //axis
        Axis axisX = new Axis();//x轴
        Axis axisY = new Axis();//y轴
        axisX.setName("时间");
        axisY.setName("温度");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        data.setLines(lines);
        mChartView.setLineChartData(data);//给图表设置数据
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
