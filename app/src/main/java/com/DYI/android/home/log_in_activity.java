package com.DYI.android.home;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.DYI.android.bulletin.UsersLoginState;
import com.baronzhang.android.weather.R;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.TipDialog;

public class log_in_activity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_activity);
        EditText idEdittext= findViewById(R.id.ic_inputId);
        EditText passwordEdit= findViewById(R.id.ic_inputPassword);
        Button LogInButton= findViewById(R.id.ic_login);
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass= findViewById(R.id.checkbox);
        boolean isRemember =preferences.getBoolean("remember password",false);
        if (isRemember){
            String account=preferences.getString("account","");
            String password=preferences.getString("password","");
            idEdittext.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account=idEdittext.getText().toString();
                String passWord=passwordEdit.getText().toString();
                if (account.equals("user")&&passWord.equals("1")){
                    UsersLoginState.setIsManagerstateNegative();
                    password_Correct(account,passWord);
                }else if(account.equals("admin")&&passWord.equals("1")) {
                    UsersLoginState.setIsManagerstatePositive();
                    password_Correct(account,passWord);
                }else {
                    TipDialog.show(log_in_activity.this, "账号不存在或者密码错误", TipDialog.TYPE.WARNING);
                }
            }
        });
    }
    public void password_Correct(String account,String password){
        editor=preferences.edit();
        if (rememberPass.isChecked()){
            editor.putBoolean("remember password",true);
            editor.putString("account",account);
            editor.putString("password",password);
        }else {
            editor.clear();
        }
        editor.apply();
        Toast.makeText(log_in_activity.this,"密码正确，登录成功。",Toast.LENGTH_SHORT).show();
        UsersLoginState.setLoginstatePositive();
        Intent intent=new Intent(log_in_activity.this,MagaCommunity.class);
        startActivity(intent);
        finish();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            Intent intent=new Intent(log_in_activity.this,MagaCommunity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
