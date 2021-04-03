package com.DYI.android.Repair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.baronzhang.android.weather.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RepaireeRequsetFormRead extends AppCompatActivity {
    //管理员读取订单界面
    String address;
    String phoneNum;
    String brokenEquip;
    String description;
    String time;
    String name;
    TextView textAdd;
    TextView textPhone;
    TextView textBroken;
    TextView textDes;
    TextView textTime;
    TextView isUrgent;
    TextView Text_name;
    public Boolean isChecked=false;
    public Bundle bundle=new Bundle();
    static Boolean fromManger=false;
     String repaireeName;
    public static final String repairee_NAME = "repairee_name";

    public static void actionStart(Context context,String phoneNum, String address,String brokenEquip,String description,String time,String name,boolean isChecked) {
        Intent intent = new Intent(context, RepaireeRequsetFormRead.class);
        intent.putExtra("user_address", address);
        intent.putExtra("phoneNum",phoneNum);
        intent.putExtra("brokenEquip",brokenEquip);
        intent.putExtra("description",description);
        intent.putExtra("time",time);
        intent.putExtra("name",name);
        intent.putExtra("isCheck",isChecked);
        fromManger=true;
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (bundle!=null){
            onRestoreInstanceState(bundle);
        }
        setContentView(R.layout.activity_repairee_requset_form_read);
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("代处理名单");
        isChecked=getIntent().getBooleanExtra("isCheck",true);
        address=getIntent().getStringExtra("user_address");
        phoneNum=getIntent().getStringExtra("phoneNum");
        brokenEquip=getIntent().getStringExtra("brokenEquip");
        description=getIntent().getStringExtra("description");
        time=getIntent().getStringExtra("time");
        repaireeName=getIntent().getStringExtra("name");
        textAdd= findViewById(R.id.location);
        textPhone= findViewById(R.id.phone_number);
        textBroken= findViewById(R.id.equipment);
        Text_name=findViewById(R.id.repairee_name);
        textDes= findViewById(R.id.description);
        textTime=findViewById(R.id.time);
        isUrgent=findViewById(R.id.is_urgent);
        if (fromManger){
            textAdd.append(address);
            textPhone.append(phoneNum);
            textBroken.append(brokenEquip);
            textDes.append(description);
            Text_name.append(repaireeName);
            textTime.append(time);
            if (isChecked)isUrgent.append("是");
            else{
                isUrgent.append("否");
            }
            fromManger=false;

        }

    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("1","调用了保存函数"+address+brokenEquip+time+description+phoneNum);
        savedInstanceState.putString("save_address", address);
        savedInstanceState.putString("save_broken", brokenEquip);
        savedInstanceState.putString("save_phone", phoneNum);
        savedInstanceState.putString("save_Des", description);
        savedInstanceState.putString("save_time", time);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("","调用了恢复函数");
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        address = savedInstanceState.getString("save_address");
        brokenEquip = savedInstanceState.getString("save_broken");
        phoneNum=savedInstanceState.getString("save_phone");
        description = savedInstanceState.getString("save_Des");
        time=savedInstanceState.getString("save_time");
    }
}
