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
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class ReportActivity extends AppCompatActivity {

    private ColumnChartView chart;            //柱状图的自定义View
    private ColumnChartData data;             //存放柱状图数据的对象
    private ArrayList<ArrayList<SubcolumnValue>> values;
    private ArrayList<SubcolumnValue> subvalues;


    private boolean hasAxes = true;            //是否有坐标轴
    private boolean hasAxesNames = true;       //是否有坐标轴的名字
    private boolean hasLabels = false;          //柱子上是否显示标识文字
    private boolean hasLabelForSelected = true;    //柱子被点击时，是否显示标识的文字


    //data
    private ArrayList<Integer> temp =new ArrayList<Integer>();
    private ArrayList<Integer> humi =new ArrayList<Integer>();
    private ArrayList<Integer> ch4 =new ArrayList<Integer>();
    private ArrayList<Integer> smoke =new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        ch4 = this.getIntent().getIntegerArrayListExtra("ch4");
        smoke = this.getIntent().getIntegerArrayListExtra("smoke");

        values = new ArrayList<ArrayList<SubcolumnValue>>();

        for(int i = 0; i < ch4.size() || i < smoke.size(); i ++ ){
            subvalues = new ArrayList<SubcolumnValue>();
            subvalues.add(new SubcolumnValue(smoke.get(i), ChartUtils.pickColor()));
            subvalues.add(new SubcolumnValue(ch4.get(i), ChartUtils.pickColor()));
            values.add(subvalues);
        }


        initView();
        initData();

    }
    private void initView() {
        chart = (ColumnChartView) findViewById(R.id.chart_report);

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
