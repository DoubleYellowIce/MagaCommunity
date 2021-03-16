package com.DYI.android.Repair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baronzhang.android.weather.R;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class repairee_actitvity extends AppCompatActivity {
    public static final String repairee_NAME = "repairee_name";
    public static final String repairee_IMAGE_ID = "repairee_image_id";
    public static final String repairee_skill = "repairee_skill";
    public TimePickerView pvTime;
    EditText editTextTime;
    EditText editTextPhoneNum;
    EditText editTextLoc;
    EditText editTextEqu;
    EditText editTextDes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairee_actitvity);
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
        initTimePicker();
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String repaireeName = intent.getStringExtra(repairee_NAME);
        int fruitImageId = intent.getIntExtra(repairee_IMAGE_ID, 0);
        String repaireeSkill = intent.getStringExtra(repairee_skill);
        TextView textView = (TextView) findViewById(R.id.repairee_name);
        textView.setText("技师名称" + ":" + repaireeName);
        editTextTime = (EditText) findViewById(R.id.time);
        editTextDes=(EditText) findViewById(R.id.description);
        editTextEqu=(EditText) findViewById(R.id.equipment);
        editTextLoc=(EditText) findViewById(R.id.location);
        editTextPhoneNum=(EditText) findViewById(R.id.phone_number);
        editTextTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pvTime.show();
                return false;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {

                Log.i("pvTime", "onTimeSelect");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月-dd号 HH点");
                String dateString = formatter.format(date);
                editTextTime.setText(dateString + "");


            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, true, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .setLabel("年", "月", "天", "时", "分", "")
                .setCancelText("取消")
                .setSubmitText("确定")
                .setTitleText("预约时间选择")
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                MessageDialog.show(repairee_actitvity.this,"温馨提示","是否已确定内容无误","确定","取消").
                        setOkButton(new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                if(editTextTime.getText().toString().isEmpty()||editTextDes.getText().toString().isEmpty()||editTextEqu.getText().toString().isEmpty()||editTextPhoneNum.getText().toString().isEmpty()||editTextLoc.getText().toString().isEmpty()
                                ){
                                    MessageDialog.show(repairee_actitvity.this,"温馨提示","表格未完整,请填写好表格后再提交。");
                                }
                                else if(!editTextTime.getText().toString().isEmpty()&&!editTextDes.getText().toString().isEmpty()&&!editTextEqu.getText().toString().isEmpty()&&!editTextPhoneNum.getText().toString().isEmpty()&&!editTextLoc.getText().toString().isEmpty())
                                {
                                    MessageDialog.show(repairee_actitvity.this,"温馨提示",
                                            "预约成功,请按预约时间在家等候，届时水电工将与您电话联系。") ;
                                }
                                return false;
                            }
                        });
                break;
            default:
                break;
        }
        return true;
    }
    }

