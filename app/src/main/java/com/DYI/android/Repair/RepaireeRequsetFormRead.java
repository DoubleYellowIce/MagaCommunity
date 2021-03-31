package com.DYI.android.Repair;

import androidx.appcompat.app.AppCompatActivity;
import com.baronzhang.android.weather.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RepaireeRequsetFormRead extends AppCompatActivity {
    //管理员读取订单界面
    String address;
    String phoneNum;
    String brokenEquip;
    String description;
    String time;
    TextView textAdd;
    TextView textPhone;
    TextView textBroken;
    TextView textDes;
    TextView textTime;

    public static void actionStart(Context context,String phoneNum, String address,String brokenEquip,String description,String time) {
        Intent intent = new Intent(context, RepaireeRequsetFormRead.class);
        intent.putExtra("user_address", address);
        intent.putExtra("phoneNum",phoneNum);
        intent.putExtra("brokenEquip",brokenEquip);
        intent.putExtra("description",description);
        intent.putExtra("time",time);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairee_requset_form_read);
        address=getIntent().getStringExtra("user_address");
        phoneNum=getIntent().getStringExtra("phoneNum");
        brokenEquip=getIntent().getStringExtra("brokenEquip");
        description=getIntent().getStringExtra("description");
        time=getIntent().getStringExtra("time");
        textAdd= findViewById(R.id.location);
        textAdd.append(address);
        textPhone= findViewById(R.id.phone_number);
        textPhone.append(phoneNum);
        textBroken= findViewById(R.id.equipment);
        textBroken.append(brokenEquip);
        textDes= findViewById(R.id.description);
        textDes.append(description);

        textTime=findViewById(R.id.time);
        textTime.append(time);

    }

}
