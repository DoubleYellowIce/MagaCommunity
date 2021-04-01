package com.DYI.android.Repair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.baronzhang.android.weather.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
    TextView textAdd;
    TextView textPhone;
    TextView textBroken;
    TextView textDes;
    TextView textTime;
    EditText editText_name;
    static Boolean fromManger=false;
    private String repaireeName;
    public static final String repairee_NAME = "repairee_name";

    public static void actionStart(Context context,String phoneNum, String address,String brokenEquip,String description,String time) {
        Intent intent = new Intent(context, RepaireeRequsetFormRead.class);
        intent.putExtra("user_address", address);
        intent.putExtra("phoneNum",phoneNum);
        intent.putExtra("brokenEquip",brokenEquip);
        intent.putExtra("description",description);
        intent.putExtra("time",time);
        fromManger=true;
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairee_requset_form_read);
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("代处理名单");
        Intent intent = getIntent();
        repaireeName = intent.getStringExtra(repairee_NAME);
        editText_name= findViewById(R.id.edit_repairee_name);
        editText_name.setText(repaireeName);
        address=getIntent().getStringExtra("user_address");
        phoneNum=getIntent().getStringExtra("phoneNum");
        brokenEquip=getIntent().getStringExtra("brokenEquip");
        description=getIntent().getStringExtra("description");
        time=getIntent().getStringExtra("time");
        textAdd= findViewById(R.id.location);
        textPhone= findViewById(R.id.phone_number);
        textBroken= findViewById(R.id.equipment);
        textDes= findViewById(R.id.description);
        textTime=findViewById(R.id.time);
        if (fromManger){
            textAdd.append(address);
            textPhone.append(phoneNum);
            textBroken.append(brokenEquip);
            textDes.append(description);
            textTime.append(time);
            fromManger=false;
        }
        editText_name.setInputType(InputType.TYPE_NULL);
        editText_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent=new Intent(RepaireeRequsetFormRead.this,RepairWelcomeActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }
}
