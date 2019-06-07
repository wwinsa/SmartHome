package com.example.weixi.smarthome;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;


public class R2Activity extends AppCompatActivity {

    //columnchart
    private ColumnChartView chart;            //柱状图的自定义View
    private ColumnChartData data;             //存放柱状图数据的对象
    private ArrayList<ArrayList<SubcolumnValue>> values;
    private ArrayList<SubcolumnValue> subvalues;

    //

    //linechart
    private LineChartView mChartView;
    private LineChartData linedata;          // 折线图封装的数据类
    private ArrayList<PointValue> linevalues;
    private ArrayList<Line> lines;
    private Line line;


    private boolean hasAxes = true;            //是否有坐标轴
    private boolean hasAxesNames = true;       //是否有坐标轴的名字
    private boolean hasLabels = false;          //柱子上是否显示标识文字
    private boolean hasLabelForSelected = true;    //柱子被点击时，是否显示标识的文字


    //data
    private ArrayList<Integer> temp =new ArrayList<Integer>();
    private ArrayList<Integer> humi =new ArrayList<Integer>();
    private ArrayList<Integer> ch4 =new ArrayList<Integer>();
    private ArrayList<Integer> smoke =new ArrayList<Integer>();
    private ArrayList<Integer> score =new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initView();

        ch4 = this.getIntent().getIntegerArrayListExtra("ch4");
        smoke = this.getIntent().getIntegerArrayListExtra("smoke");

        values = new ArrayList<ArrayList<SubcolumnValue>>();

        for(int i = 0; i < ch4.size() || i < smoke.size(); i ++ ){
            subvalues = new ArrayList<SubcolumnValue>();
            subvalues.add(new SubcolumnValue(smoke.get(i), ChartUtils.pickColor()));
            subvalues.add(new SubcolumnValue(ch4.get(i), ChartUtils.pickColor()));
            values.add(subvalues);
        }

        linevalues = new ArrayList<PointValue>();//折线上的点
        //std = new ArrayList<PointValue>();//折线上的点

//        for (int i = 0; i < ch4.size()|| i < smoke.size(); i++){
////            int score = 100 ;
////            if(ch4.get(i) > 20 ){
////                score = score - 5;
////            }
////            if (smoke.get(i) > 20){
////                score = score - 5;
////            }
//            linevalues.add(new PointValue(i, 50));
//        }
        for (int i = 0; i < ch4.size(); i++){
            linevalues.add(new PointValue(i, ch4.get(i)));
        }

        //initData();
        drawlinechart();

    }
    private void drawlinechart(){
        //line
        line = new Line(linevalues).setColor(Color.BLUE);//声明线并设置颜色
        line.setCubic(false);//设置是平滑的还是直的
        lines = new ArrayList<Line>();
        lines.add(line);

        linedata = new LineChartData();
        //axis
        Axis axisX = new Axis();//x轴
        Axis axisY = new Axis();//y轴
        axisX.setName("时间");
        axisY.setName("分数");
        linedata.setAxisXBottom(axisX);
        linedata.setAxisYLeft(axisY);

        linedata.setLines(lines);
        mChartView.setLineChartData(linedata);//给图表设置数据
    }

    private void initView() {
        chart = (ColumnChartView) findViewById(R.id.chart_report);
        mChartView = (LineChartView)findViewById(R.id.chart_result);
    }

    private void initData() {
        generateDefaultData();

    }


    /**
     * 默认显示的数据
     */
    private void generateDefaultData() {
        List<Column> columns = new ArrayList<Column>();

        for(int i = 0; i < values.size(); i ++){
            Column column1 = new Column(values.get(i));
            column1.setHasLabels(hasLabels);
            column1.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column1);
        }




        data = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);

    }


    Toast toast;

    public void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }



}
