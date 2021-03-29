package com.DYI.android.Repair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.baronzhang.android.weather.R;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class RepairMangerActivity extends AppCompatActivity {
    //维修界面管理员活动
    private ArrayList<String> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_manger);
        ArrayAdapter<ArrayList> arrayListArrayAdapter=new ArrayAdapter<ArrayList>(RepairMangerActivity.this,    ,data);
    }
    public void addData(){
        List<RepairRequestForm> repairRequestForms= LitePal.findAll(RepairRequestForm.class);
        for (RepairRequestForm repairRequestForm:repairRequestForms){
            data.add(repairRequestForm.getAddress());
        }
    }
}
