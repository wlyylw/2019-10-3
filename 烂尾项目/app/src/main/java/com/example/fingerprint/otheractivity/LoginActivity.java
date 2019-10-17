package com.example.fingerprint.otheractivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fingerprint.R;
import com.example.fingerprint.mainui.MainUiHome;

public class LoginActivity extends AppCompatActivity {
    EditText userNameEditText;
    EditText passwordEditText;
    TextView userNameTextView;
    TextView passwordTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SetObj();
    }
    public void On_LoginButton_Click(View view) {
        String username = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (username == null || username.length() == 0) {
            Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            userNameTextView.setTextColor(Color.rgb(255, 0, 0));
            return;
        } else {
            userNameTextView.setTextColor(Color.rgb(0, 0, 0));
        }
        if (password == null || password.length() == 0) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            passwordTextView.setTextColor(Color.rgb(255, 0, 0));
            return;
        } else {
            passwordTextView.setTextColor(Color.rgb(0, 0, 0));
        }
        UserService userService = UserService.getInstance();
        if (!userService.Login(username, password)) {
            Toast.makeText(this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
            return;
        }
//        Intent intent;
//        intent = new Intent(this, MainUiHome.class);//应该可以直接到mine
//        startActivity(intent);  //TODO：还要传数据
        Intent intent = new Intent(this, MainUiHome.class);
        intent.putExtra("LoginExtra",1);
        startActivity(intent);
        finish();
    }

    public void SetObj() {
        userNameTextView = findViewById(R.id.userNameTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
    }

}
