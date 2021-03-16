package com.DYI.android.home;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.DYI.android.bulletin.UsersLoginState;
import com.baronzhang.android.weather.R;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.TipDialog;

public class log_in_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_activity);
        EditText idEdittext= (EditText) findViewById(R.id.ic_inputId);
        EditText passwordEdit=(EditText) findViewById(R.id.ic_inputPassword);
        Button LogInButton=(Button) findViewById(R.id.ic_login);
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account=idEdittext.getText().toString();
                String passWord=passwordEdit.getText().toString();
                if (account.equals("user")&&passWord.equals("1")){
                    UsersLoginState.setIsManagerstateNegative();
                    password_Correct();
                }else if(account.equals("admin")&&passWord.equals("1")) {
                    UsersLoginState.setIsManagerstatePositive();
                    password_Correct();
                }else {
                    TipDialog.show(log_in_activity.this, "账号不存在或者密码错误", TipDialog.TYPE.WARNING);
                }
            }
        });
    }
    public void password_Correct(){
        Toast.makeText(log_in_activity.this,"密码正确，登录成功。",Toast.LENGTH_SHORT).show();
        UsersLoginState.setLoginstatePositive();
        Intent intent=new Intent(log_in_activity.this,MagaCommunity.class);
        startActivity(intent);

    }
}
