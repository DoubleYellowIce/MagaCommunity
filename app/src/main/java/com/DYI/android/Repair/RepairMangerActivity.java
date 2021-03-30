package com.DYI.android.Repair;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baronzhang.android.weather.R;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class RepairMangerActivity extends AppCompatActivity {
    //维修界面管理员活动
    private List<RepairRequestForm> RepairRequestForms=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_manger);
        addData();
        RepaireeRequestFormAdapter repaireeRequestFormAdapter=new RepaireeRequestFormAdapter(RepairMangerActivity.this,R.layout.repairee_form_item,RepairRequestForms);
        ListView listView=(ListView) findViewById(R.id.list_view);
        listView.setAdapter(repaireeRequestFormAdapter);
    }
    public void addData(){
        List<RepairRequestForm> repairRequestForms= LitePal.findAll(RepairRequestForm.class);
        for (RepairRequestForm repairRequestForm:repairRequestForms){
            RepairRequestForms.add(repairRequestForm);
            Log.d("1hhhh","2" +repairRequestForm.getAddress());
        }
    }
}
