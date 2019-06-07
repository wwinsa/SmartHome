package com.example.weixi.smarthome;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class HumiActivity extends AppCompatActivity {

    private Button btn_1, btn_2, btn_3, btn_status;

    private ArrayList<Integer> humi = new ArrayList<Integer>();
    //chart
    private LineChartView mChartView;
    private LineChartData data;          // 折线图封装的数据类
    private ArrayList<PointValue> values;
    private ArrayList<Line> lines;
    private Line line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humi);


        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_status = findViewById(R.id.btn_status);
        mChartView = findViewById(R.id.chart_humi);
        setListeners();

        humi = this.getIntent().getIntegerArrayListExtra("humi");
        Toast.makeText(this,humi.get(humi.size()-1).toString(),Toast.LENGTH_SHORT).show();

        values = new ArrayList<PointValue>();//折线上的点

        for (int i = 0; i < humi.size(); i++){
            values.add(new PointValue(i, humi.get(i)));
        }

        drawchart();

    }

    private void drawchart(){
        //line
        line = new Line(values).setColor(Color.BLUE);//声明线并设置颜色
        line.setCubic(false);//设置是平滑的还是直的
        lines = new ArrayList<Line>();
        lines.add(line);

        data = new LineChartData();
        //axis
        Axis axisX = new Axis();//x轴
        Axis axisY = new Axis();//y轴
        axisX.setName("时间");
        axisY.setName("湿度");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        data.setLines(lines);
        mChartView.setLineChartData(data);//给图表设置数据
    }
    private void setListeners(){
        HumiActivity.OnClick onClick = new HumiActivity.OnClick();
        btn_status.setOnClickListener(onClick);
        btn_1.setOnClickListener(onClick);
        btn_2.setOnClickListener(onClick);
        btn_3.setOnClickListener(onClick);
    }
    private class OnClick implements View.OnClickListener {
        public boolean flag = true;

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btn_status:
                    if (flag) {
                        btn_status.setBackgroundResource(R.drawable.btn_light_on);
                        flag = false;
                    } else {
                        btn_status.setBackgroundResource(R.drawable.btn_light_off);
                        flag = true;
                    }

                    break;
                case R.id.btn_1:
                    if (flag) {
                        btn_1.setBackgroundResource(R.color.colorPrimary);
                        btn_2.setBackgroundResource(R.color.colorPrimaryDark);
                        btn_3.setBackgroundResource(R.color.colorPrimaryDark);
                    }

                    break;
                case R.id.btn_2:
                    if (flag) {
                        btn_2.setBackgroundResource(R.color.colorPrimary);
                        btn_1.setBackgroundResource(R.color.colorPrimaryDark);
                        btn_3.setBackgroundResource(R.color.colorPrimaryDark);
                    }

                    break;
                case R.id.btn_3:
                    if (flag) {
                        btn_3.setBackgroundResource(R.color.colorPrimary);
                        btn_2.setBackgroundResource(R.color.colorPrimaryDark);
                        btn_1.setBackgroundResource(R.color.colorPrimaryDark);
                    }

                    break;
            }
        }
    }






}
