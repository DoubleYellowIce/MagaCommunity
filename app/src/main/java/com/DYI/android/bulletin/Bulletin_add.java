package com.DYI.android.bulletin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.DYI.android.bulletin.Bulltins;
import com.DYI.android.bulletin.BulltinsContentActivity;
import com.DYI.android.home.MagaCommunity;
import com.baronzhang.android.weather.R;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;
import com.kongzue.dialog.v3.Notification;
import com.kongzue.dialog.v3.TipDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.litepal.LitePal;

public class Bulletin_add extends AppCompatActivity {
    private SharedPreferences pref;
    MaterialEditText name;
    MaterialEditText content;
    String newsTitle;
    String newsContent;
    public static boolean editState=false;
    public static void actionStart(Context context, String newsTitle, String newsContent) {
        Intent intent = new Intent(context, Bulletin_add.class);
        intent.putExtra("news_title", newsTitle);
        intent.putExtra("news_content", newsContent);
        context.startActivity(intent);
        editState=true;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulltin_add);
        androidx.appcompat.widget.Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (editState){
            if (newsTitle==null&&newsContent==null) {
                newsTitle = getIntent().getStringExtra("news_title"); // 获取传入的新闻标题
                newsContent = getIntent().getStringExtra("news_content");
            }
            name= (MaterialEditText) findViewById(R.id.ic_name);
            content=(MaterialEditText) findViewById(R.id.ic_content);
            name.setText(newsTitle);
            content.setText(newsContent);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.send:
                name= (MaterialEditText) findViewById(R.id.ic_name);
                content=(MaterialEditText) findViewById(R.id.ic_content);
                MessageDialog.show(Bulletin_add.this, "温馨提示", "是否已确定内容无误？", "确定", "再看看")
                        .setOkButton(new OnDialogButtonClickListener() {  //仅需要对需要处理的按钮进行操作
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                String str1;
                                String str2;
                                str1=name.getText().toString();
                                str2=content.getText().toString();
                                Bulltins bulltins=new Bulltins();
                                bulltins.setTitle(str1);
                                bulltins.setContent(str2);
                                if (editState){
                                    Log.d("Bulletin_add.class",""+newsTitle+newsContent);
                                    bulltins.updateAll("title = ? and content = ?",""+newsTitle,""+newsContent);
                                    Notification.show(Bulletin_add.this, "提示", "更新成功，如果公告未自动更新，请手动下拉刷新。");
                                    editState=false;
                                }else {
                                    bulltins.save();
                                }
                                Intent intent=new Intent(Bulletin_add.this,bulletin.class);
                                startActivity(intent);
                                finish();
                                //处理确定按钮事务
                                return false;    //可以通过 return 决定点击按钮是否默认自动关闭对话框
                            }
                        });
                break;
            default:
                break;
        }
        return true;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            if (!editState){
                MessageDialog.show(Bulletin_add.this, "温馨提示", "是否保存草稿？", "保存", "丢弃").
                        setOkButton(new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                Intent intent=new Intent(Bulletin_add.this,bulletin.class);
                                startActivity(intent);
                                return false;
                            }
                        }).setCancelButton(new OnDialogButtonClickListener() {
                    @Override
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        Intent intent=new Intent(Bulletin_add.this,bulletin.class);
                        startActivity(intent);
                        finish();
                        return false;
                    }
                });
            }
            else {
                Intent intent=new Intent(Bulletin_add.this,bulletin.class);
                startActivity(intent);
                finish();
            }
        }
        return false;
    }
}
