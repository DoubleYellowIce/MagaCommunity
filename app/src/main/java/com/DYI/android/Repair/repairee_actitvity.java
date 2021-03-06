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
    private EquipBean[] equipBeans={new EquipBean(1,"????????????"),
            new EquipBean(2,"????????????"),
            new EquipBean(3,"????????????"),
            new EquipBean(4,"??????")
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
        textView.setText("????????????" + ": " + repaireeName);
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

    private void initTimePicker() {//Dialog ???????????????????????????
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy???-MM???-dd??? HH???");
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
                .isDialog(true) //????????????false ??????????????????DecorView ????????????????????????
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //?????????????????????????????????1???????????????6???????????????????????????7???
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .setLabel("???", "???", "???", "???", "???", "")
                .setCancelText("??????")
                .setSubmitText("??????")
                .setTitleText("??????????????????")
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
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//??????????????????
                dialogWindow.setGravity(Gravity.BOTTOM);//??????Bottom,????????????
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                MessageDialog.show(repairee_actitvity.this,"????????????","???????????????????????????","??????","??????").
                        setOkButton(new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                if(editTextTime.getText().toString().isEmpty()||editTextDes.getText().toString().isEmpty()||editTextEqu.getText().toString().isEmpty()||editTextPhoneNum.getText().toString().isEmpty()||editTextLoc.getText().toString().isEmpty()
                                ){
                                    MessageDialog.show(repairee_actitvity.this,"????????????","???????????????,?????????????????????????????????");
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
                                    MessageDialog.show(repairee_actitvity.this,"????????????",
                                            "????????????,????????????????????????????????????????????????????????????????????????") ;
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
                //?????????????????????????????????????????????
                String tx = options1Items.get(options1).getPickerViewText()+"???"+options1Items.get(options2).getPickerViewText()+"???"+options1Items.get(options3).getPickerViewText()+"???";
                editTextLoc.setText(tx);
            }
        })
                .setTitleText("????????????")
                .setCancelText("??????")
                .setSubmitText("??????")
                .setContentTextSize(20)//????????????????????????
                .setSelectOptions(0, 1)//???????????????
                .setBgColor(Color.WHITE)
                .isRestoreItem(true)//??????????????????????????????????????????????????????
                .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                .setLabels("???", "???", "???")
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
                //?????????????????????????????????????????????
                String tx = equipBeanArrayList.get(options1).getPickerViewText();
                editTextEqu.setText(tx);
            }
        })
                .setTitleText("??????????????????")
                .setCancelText("??????")
                .setSubmitText("??????")
                .setContentTextSize(20)//????????????????????????
                .setSelectOptions(0, 1)//???????????????
                .setBgColor(Color.WHITE)
                .isRestoreItem(true)//??????????????????????????????????????????????????????
                .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
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

