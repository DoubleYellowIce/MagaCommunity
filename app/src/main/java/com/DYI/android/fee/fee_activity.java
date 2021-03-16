package com.DYI.android.fee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import com.DYI.android.home.Icon;
import com.DYI.android.home.MyAdapter;
import com.baronzhang.android.weather.R;
import java.util.ArrayList;

import lecho.lib.hellocharts.view.LineChartView;

public class fee_activity extends AppCompatActivity {
    private GridView grid_photo;
    private BaseAdapter mAdapter = null;
    private ArrayList<Icon> mData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_activity);
        grid_photo = (GridView) findViewById(R.id.grid_photo);
        mData = new ArrayList<Icon>();
        mData.add(new Icon(R.drawable.ic_month ,"当月水电费"));
        mData.add(new Icon(R.drawable.ic_year, "全年水电费"));
        mAdapter = new MyAdapter<Icon>(mData, R.layout.item_grid_icon) {
            @Override
            public void bindView(MyAdapter.ViewHolder holder, Icon obj) {
                holder.setImageResource(R.id.img_icon, obj.getiId());
                holder.setText(R.id.txt_icon, obj.getiName());
            }
        };
        grid_photo.setAdapter(mAdapter);
        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(mContext, "你点击了~" + position + "~项", Toast.LENGTH_SHORT).show();
                if (position==0){

                }
                if (position==1){
                goto_charActivity();
                }
            }
        });
    }

    private void goto_charActivity() {
        Intent intent = new Intent(fee_activity.this, BarChartActivityMultiDataset.class);
        startActivity(intent);
        finish();
    }
}
