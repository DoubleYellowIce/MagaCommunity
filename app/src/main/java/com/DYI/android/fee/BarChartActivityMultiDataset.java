
package com.DYI.android.fee;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.DYI.android.bulletin.UsersLoginState;
import com.DYI.android.bulletin.bulletin;
import com.DYI.android.home.MagaCommunity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.DYI.android.fee.MyMarkerView;
import com.DYI.android.fee.DemoBase;
import com.baronzhang.android.weather.R;
import com.kongzue.dialog.v3.MessageDialog;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BarChartActivityMultiDataset extends DemoBase implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {
    private boolean YearDataState=true;
    private SeekBar seekBarX, seekBarY;
    public static int getMonth(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.MONTH);
    }

    public static int getDay(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.DATE);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.switch_buttom,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
                case R.id.year_fee:
                if(!YearDataState){MonthSwitchYear(11,30,true);
                setTitle("年水电费");
                YearDataState=true;
                }
                break;
            case R.id.month_fee:
                if (YearDataState){MonthSwitchYear(getDay()-1,1,false);
                    setTitle(getMonth()+1+"月水电费");
                    YearDataState=false;
                }

            default:
                break;
        }
        return true;
    }

    private BarChart chart;
    private TextView tvX, tvY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_barchart);

        setTitle("年水电费");
        androidx.appcompat.widget.Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvX = findViewById(R.id.tvXMax);
        tvX.setTextSize(10);
        tvY = findViewById(R.id.tvYMax);

        seekBarX = findViewById(R.id.seekBar1);
        seekBarX.setMax(11);
        seekBarX.setOnSeekBarChangeListener(this);

        seekBarY = findViewById(R.id.seekBar2);
        seekBarY.setOnSeekBarChangeListener(this);

        chart = findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);
        chart.getDescription().setEnabled(false);

//        chart.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawBarShadow(false);

        chart.setDrawGridBackground(false);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        seekBarX.setProgress(12);//初始数据量
        seekBarY.setProgress(100);//初始数据最大值

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setTypeface(tfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);
        XAxis xAxis = chart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setGranularity(1f);

        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelCount(31);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setValueFormatter(new ValueFormatter() {
//            public String  getFormattedValue(int value, AxisBase axis) {
//
//                return String.valueOf((int) value);
//
//                }
//
//        });

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setValueFormatter(new LargeValueFormatter());

        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);
    }
    public void MonthSwitchYear(int Datamount,int Datamax,boolean YearData){
        float groupSpace = 0.08f;
        float barSpace = 0.06f; // x4 DataSet
        float barWidth = 0.4f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
        // （0.4 + 0.06）*2 +0.08=1.00
        int groupCount = Datamount+1 ;
        int startMonth = 1;
        int endMonth = startMonth + groupCount;



        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();


        float randomMultiplier = Datamax * 0.01f;

        for (int i = startMonth; i < endMonth; i++) {

            values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            if (YearData&&i>getMonth())break;
        }

        BarDataSet set1, set2;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) chart.getData().getDataSetByIndex(1);
            set1.setValues(values1);
            set2.setValues(values2);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values1, "水费");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(values2, "电费");
            set2.setColor(Color.rgb(164, 228, 251));
            BarData data = new BarData(set1, set2);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(tfLight);
            chart.setData(data);
        }

        // specify the width each bar should have
        chart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        chart.getXAxis().setAxisMinimum(startMonth);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        chart.getXAxis().setAxisMaximum(startMonth + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(startMonth, groupSpace, barSpace);
        chart.invalidate();

    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        float groupSpace = 0.08f;
        float barSpace = 0.06f; // x4 DataSet
        float barWidth = 0.4f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
        // （0.4 + 0.06）*2 +0.08=1.00
        int groupCount = seekBarX.getProgress()+1 ;
        int startMonth = 1;
        int endMonth = startMonth + groupCount;

        tvX.setText(String.format(Locale.CHINESE, "%s-%s", startMonth+"月", endMonth-1+"月"));
        //第一个seekbar右边的文字
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();


        float randomMultiplier = seekBarY.getProgress() * 1f;

        for (int i = startMonth; i < endMonth; i++) {

            values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            if (i>getMonth())break;
        }

        BarDataSet set1, set2;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) chart.getData().getDataSetByIndex(1);
            set1.setValues(values1);
            set2.setValues(values2);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values1, "水费");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(values2, "电费");
            set2.setColor(Color.rgb(164, 228, 251));
            BarData data = new BarData(set1, set2);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(tfLight);
            chart.setData(data);
        }
        chart.setVisibleXRangeMaximum(3);
        // specify the width each bar should have
        chart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        chart.getXAxis().setAxisMinimum(startMonth);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        chart.getXAxis().setAxisMaximum(startMonth + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(startMonth, groupSpace, barSpace);
        chart.invalidate();
    }


    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "BarChartActivityMultiDataset");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Activity", "Selected: " + e.toString() + ", dataSet: " + h.getDataSetIndex());

//        Toast.makeText(BarChartActivityMultiDataset.this, e.getX()+""+e.getY()+"", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected() {
        Log.i("Activity", "Nothing selected.");
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            Intent intent=new Intent(BarChartActivityMultiDataset.this, MagaCommunity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
