package com.DYI.android.bulletin;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.DYI.android.bulletin.Bulletin_add;
import com.DYI.android.home.MagaCommunity;
import com.baronzhang.android.weather.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.interfaces.OnInputDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.InputDialog;
import com.kongzue.dialog.v3.MessageDialog;
import com.kongzue.dialog.v3.Notification;
import com.rengwuxian.materialedittext.MaterialEditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;
public class bulletin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletin_board);
        Connector.getDatabase();
        androidx.appcompat.widget.Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
        FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.fab);

        if (!UsersLoginState.getIsManger())fab.hide();//如果用户不是管理员，添加公告按钮不可见
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            gotoAddBulletin();
            }
        });
        }
    private void gotoAddBulletin() {
        gotoAddbulletin();
    }
    private void gotoAddbulletin() {
        Intent intent = new Intent(bulletin.this, Bulletin_add.class);
        startActivity(intent);
        // 修复 Android 9.0 下 Activity 跳转动画导致的启动页闪屏的问题
        overridePendingTransition(0, 0);
        finish();
    }
    boolean passwordCorrectState=false;
    public boolean PasswordCorrectState(){
        //让用户输入管理员密码并设置登录状态
        InputDialog.show(bulletin.this, "温馨提示,您正在进行登录操作", "请输入管理员密码", "确定", "取消").
                setOnOkButtonClickListener(new OnInputDialogButtonClickListener() {
                    @Override
                    public boolean onClick(BaseDialog baseDialog, View v, String inputStr) {
                        if (inputStr.equals("1")) {
                            Toast.makeText(bulletin.this, "密码正确", Toast.LENGTH_SHORT).show();
                            UsersLoginState.setLoginstatePositive();
                            passwordCorrectState=true;
                        } else {
                            MessageDialog.show(bulletin.this, "提示", "密码错误", "确定");
                        }
                        return false;
                    }
                });
        return passwordCorrectState;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            Intent intent=new Intent(bulletin.this,MagaCommunity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}

