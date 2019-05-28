package com.example.weixi.smarthome;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

public class Ch4Activity extends AppCompatActivity {

    private Button btn_open, btn_close;
    private ArrayList<Integer> ch4 =new ArrayList<Integer>();

    //chart
    private LineChartView mChartView;
    private LineChartData data;          // 折线图封装的数据类
    private ArrayList<PointValue> values;
    private ArrayList<Line> lines;
    private Line line;

    private boolean hasAxes = true;       //是否有轴，x和y轴
    private boolean hasAxesNames = true;   //是否有轴的名字
    private boolean hasLines = true;       //是否有线（点和点连接的线）
    private boolean hasPoints = true;       //是否有点（每个值的点）
    private ValueShape shape = ValueShape.CIRCLE;    //点显示的形式，圆形，正方向，菱形
    private boolean isFilled = false;                //是否是填充
    private boolean hasLabels = false;               //每个点是否有名字
    private boolean isCubic = false;                 //是否是立方的，线条是直线还是弧线
    private boolean hasLabelForSelected = false;       //每个点是否可以选择（点击效果）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch4);

        mChartView = findViewById(R.id.chart_ch4);
        btn_close = findViewById(R.id.btn_close);
        btn_open = findViewById(R.id.btn_open);
        setListeners();


        initdata();

        values = new ArrayList<PointValue>();//折线上的点

        for (int i = 0; i < ch4.size(); i++){
            values.add(new PointValue(i, ch4.get(i)));
        }

        drawchart();


    }
    private void initdata(){
        ch4.add(30);
        ch4.add(16);
        ch4.add(25);
        ch4.add(23);
        ch4.add(23);
        ch4.add(18);
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
        axisY.setName("浓度");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        data.setLines(lines);
        mChartView.setLineChartData(data);//给图表设置数据
    }
    private void setListeners(){
        Ch4Activity.OnClick onClick = new Ch4Activity.OnClick();
        btn_open.setOnClickListener(onClick);
        btn_close.setOnClickListener(onClick);
    }
    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btn_open:
                    btn_open.setBackgroundResource(R.color.colorPrimary);
                    btn_close.setBackgroundResource(R.color.colorPrimaryDark);
                    break;
                case R.id.btn_close:
                    btn_close.setBackgroundResource(R.color.colorPrimary);
                    btn_open.setBackgroundResource(R.color.colorPrimaryDark);
                    break;
            }
        }
    }
}
