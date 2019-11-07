package com.example.smssd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class SecondActivity extends AppCompatActivity {
    EditText phonenumber;
    EditText password;
    String strphonenumber;
    String strpassword;
    List<MinePagePerson> list;
    final  static  int REQUEST_CODE_LOGIN = 510;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        list =  LitePal.findAll(MinePagePerson.class);
        SetObj();
    }

    public void On_Login(View view) {
        strpassword = password.getText().toString();
        strphonenumber = phonenumber.getText().toString();
        int flag_phonenumber=0;
        for(MinePagePerson minePagePerson: list)
        {
            Log.d("TestLitePal","密码: "+ minePagePerson.getPassword());
            Log.d("TestLitePal","电话: "+ minePagePerson.getPhonenumber());
            if ( minePagePerson.getPhonenumber().equals(strphonenumber))
            {
                flag_phonenumber=1;
                if(minePagePerson.getPassword().equals(strpassword))
                {
                    //TODO: 跳转逻辑
                    Intent intent = new Intent(SecondActivity.this,TempActivity.class);
                    startActivityForResult(intent,REQUEST_CODE_LOGIN);  //REQUEST_CODE
                }
            }
        }
        if(flag_phonenumber==1)
        {
            Toast.makeText(SecondActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(SecondActivity.this,"手机号码不存在",Toast.LENGTH_SHORT).show();
        }
    }
    private void SetObj()
    {
        password=findViewById(R.id.login_password);
        phonenumber=findViewById(R.id.login_phone_number);
    }
}
