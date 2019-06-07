package com.example.weixi.smarthome;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

public class Smoke2Activity extends AppCompatActivity {

    private Button btn_open, btn_close;
    private ArrayList<Integer> smoke =new ArrayList<Integer>();

    //chart
    private LineChartView mChartView;
    private LineChartData data;          // 折线图封装的数据类
    private ArrayList<PointValue> values, std;
    private ArrayList<Line> lines;
    private Line line , line2;

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
        setContentView(R.layout.activity_smoke2);

        mChartView = findViewById(R.id.chart_smoke);
        btn_close = findViewById(R.id.btn_close);
        btn_open = findViewById(R.id.btn_open);
        setListeners();

        smoke = this.getIntent().getIntegerArrayListExtra("smoke");
        Toast.makeText(this,smoke.get(smoke.size()-1).toString(),Toast.LENGTH_SHORT).show();

        values = new ArrayList<PointValue>();//折线上的点
        std = new ArrayList<PointValue>();

        for (int i = 0; i < smoke.size(); i++){
            values.add(new PointValue(i, smoke.get(i)));
            std.add(new PointValue(i, 20));
        }

        drawchart();


    }

    private void drawchart(){
        //line
        line = new Line(values).setColor(Color.BLUE);//声明线并设置颜色
        line2 = new Line(values).setColor(Color.parseColor("#336633"));//声明线并设置颜色
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
        axisY.setName("浓度");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        data.setLines(lines);
        mChartView.setLineChartData(data);//给图表设置数据
    }
    private void setListeners(){
        Smoke2Activity.OnClick onClick =new Smoke2Activity.OnClick();
        btn_open.setOnClickListener(onClick);
        btn_close.setOnClickListener(onClick);
    }
    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btn_open:
                    btn_open.setBackgroundResource(R.color.lightblue1);
                    btn_close.setBackgroundResource(R.color.darkblue1);
                    break;
                case R.id.btn_close:
                    btn_close.setBackgroundResource(R.color.lightblue1);
                    btn_open.setBackgroundResource(R.color.darkblue1);
                    break;
            }
        }
    }

}
