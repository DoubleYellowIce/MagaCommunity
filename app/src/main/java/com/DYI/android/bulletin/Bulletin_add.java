package com.DYI.android.bulletin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.baronzhang.android.weather.R;
import com.bumptech.glide.Glide;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.MessageDialog;
import com.kongzue.dialog.v3.Notification;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import com.luck.picture.lib.entity.LocalMedia;
import com.rengwuxian.materialedittext.MaterialEditText;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.List;

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
                newsTitle = getIntent().getStringExtra("news_title"); // ???????????????????????????
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
                MessageDialog.show(Bulletin_add.this, "????????????", "??????????????????????????????", "??????", "?????????")
                        .setOkButton(new OnDialogButtonClickListener() {  //?????????????????????????????????????????????
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
                                    Notification.show(Bulletin_add.this, "??????", "?????????????????????????????????????????????????????????????????????");
                                    editState=false;
                                }else {
                                    bulltins.save();
                                }
                                Intent intent=new Intent(Bulletin_add.this,bulletin.class);
                                startActivity(intent);
                                finish();
                                //????????????????????????
                                return false;    //???????????? return ???????????????????????????????????????????????????
                            }
                        });
                break;


            default:
                break;
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        name= (MaterialEditText) findViewById(R.id.ic_name);
        content=(MaterialEditText) findViewById(R.id.ic_content);
        String str1=name.getText().toString();
        String str2=content.getText().toString();
        if (keyCode == KeyEvent.KEYCODE_BACK) {//????????????????????????
            if (!editState&&!str1.isEmpty()&&!str2.isEmpty()){
                MessageDialog.show(Bulletin_add.this, "????????????", "?????????????????????", "??????", "??????").
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
