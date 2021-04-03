package com.DYI.android.Repair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baronzhang.android.weather.R;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class repairee_actitvity extends AppCompatActivity {
    public static final String repairee_NAME = "repairee_name";

    private ArrayList<BuildingBean> options1Items = new ArrayList<>();
    private ArrayList<EquipBean> equipBeanArrayList=new ArrayList<>();
    public TimePickerView pvTime;
    private OptionsPickerView pvOptions;
    private String repaireeName;
    private OptionsPickerView equOptions;
    private boolean isChecked=false;
    private EquipBean[] equipBeans={new EquipBean(1,"供水相关"),
            new EquipBean(2,"供电相关"),
            new EquipBean(3,"供气相关"),
            new EquipBean(4,"其它")
    };
    EditText editTextTime;
    EditText editTextPhoneNum;
    EditText editTextLoc;
    EditText editTextEqu;
    EditText editTextDes;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairee_actitvity);
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
        addBuildingBean();
        addEquipBean();
        initTimePicker();
        initOptionPicker();
        initEquOptionPicker();
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        repaireeName = intent.getStringExtra(repairee_NAME);
        TextView textView = (TextView) findViewById(R.id.repairee_name);
        textView.setText("技师名称" + ": " + repaireeName);
        checkBox=findViewById(R.id.is_urgent);
        editTextTime = (EditText) findViewById(R.id.time);
        editTextDes=(EditText) findViewById(R.id.description);
        editTextEqu=(EditText) findViewById(R.id.equipment);
        editTextLoc=(EditText) findViewById(R.id.location);
        editTextPhoneNum=(EditText) findViewById(R.id.phone_number);
        editTextLoc.setInputType(InputType.TYPE_NULL);
        editTextTime.setInputType(InputType.TYPE_NULL);
        editTextEqu.setInputType(InputType.TYPE_NULL);
        editTextEqu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                equOptions.show();
                return false;
            }
        });
        editTextLoc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pvOptions.show();
                return false;
            }
        });
        editTextTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pvTime.show();
                return false;
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isChecked=true;
                }else{
                    isChecked=false;
                }
            }
        });
    }
    public void addEquipBean(){
        for (int i=0;i<equipBeans.length;i++){
            equipBeanArrayList.add(equipBeans[i]);
        }

    }
    public void addBuildingBean(){
        ArrayList<String> strings=new ArrayList<String>();
        for (int i=1;i<=10;i++){
            BuildingBean buildingBean=new BuildingBean(i,i+"");
            options1Items.add(buildingBean);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月-dd号 HH点");
                String dateString = formatter.format(date);
                editTextTime.setText(dateString + "");
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
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

                                    RepairRequestForm repairRequestForm=new RepairRequestForm();
                                    repairRequestForm.setRepaireeName(repaireeName);
                                    repairRequestForm.setAddress(editTextLoc.getText().toString());
                                    repairRequestForm.setDetailDescription(editTextDes.getText().toString());
                                    repairRequestForm.setPhoneNum(editTextPhoneNum.getText().toString());
                                    repairRequestForm.setTime(editTextTime.getText().toString());
                                    repairRequestForm.setBrokenEquipment(editTextEqu.getText().toString());
                                    if (isChecked)repairRequestForm.setIsUrgent();
                                    repairRequestForm.save();
                                    MessageDialog.show(repairee_actitvity.this,"温馨提示",
                                            "预约成功,请按预约时间在家等候，届时水电工将与您电话联系。") ;
                                    Intent intent=new Intent(repairee_actitvity.this,RepairWelcomeActivity.class);
                                    startActivity(intent);
                                    finish();
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
    private void initOptionPicker() {
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+"楼"+options1Items.get(options2).getPickerViewText()+"层"+options1Items.get(options3).getPickerViewText()+"户";
                editTextLoc.setText(tx);
            }
        })
                .setTitleText("楼栋选择")
                .setCancelText("取消")
                .setSubmitText("确定")
                .setContentTextSize(20)//设置滚轮文字大小
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("栋", "层", "户")
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                    }
                })
                .build();
        pvOptions.setSelectOptions(0,0,0);
        pvOptions.setNPicker(options1Items,options1Items,options1Items);
    }
    private void initEquOptionPicker() {
        equOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = equipBeanArrayList.get(options1).getPickerViewText();
                editTextEqu.setText(tx);
            }
        })
                .setTitleText("故障设备选择")
                .setCancelText("取消")
                .setSubmitText("确定")
                .setContentTextSize(20)//设置滚轮文字大小
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                    }
                })
                .build();
        equOptions.setSelectOptions(0,0,0);
        equOptions.setPicker(equipBeanArrayList);
    }
}

