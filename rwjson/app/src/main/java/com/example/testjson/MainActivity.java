package com.example.testjson;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 存取json数据
 * Date: 2019/10/9
 * Author：wlyylw
 * */
public class MainActivity extends AppCompatActivity {

    Button button1;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.te1);
        SetObj();
        WriteData();


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List list = ReadData();
//                Person person1 = (Person) list.get(0);
                Person person1 = (Person) list.get(0);
                Person person2 = (Person) list.get(1);
                Person person3 = (Person) list.get(2);
                Person person4 = (Person) list.get(3);
                Person person5 = (Person) list.get(4);
                textView1.setText(person1.getPasspeople());
                textView2.setText(person2.getPasspeople());
                textView3.setText(person3.getPasspeople());
                textView4.setText(person4.getFingernum());
                textView5.setText(person5.getFingernum());
            }
        });

//        list中读取出来后直接强转  不用再另外写这个
//        Gson gson = new Gson();
//        String jsonString = "{\"name\":\"张三\",\"age\":24,\"classa\":\"中国\"}";
//        String jsonStringB = "{'name':‘张三’,‘age’:24,‘classa’:‘中国’}";
//
//        Person stu = gson.fromJson(jsonString, Person.class);
//        Person stuB = gson.fromJson(jsonStringB, Person.class);



    }
    private void WriteData()
    {
        Gson gson = new Gson();
//        Person stu = new Person("张三",0,"中国");
//        String jsonObject = gson.toJson(stu);
        String jsonString1 =
        "{\"casename\":\"长沙杀人案\",\"fingernum\":1,\"sendtime\":\"22:10\",\"returntime\":\"23:10\",\"passpeople\":\"张三\",\"confirm\":\"确认\"}";
        String jsonString2 =
                "{\"casename\":\"长沙杀人案\",\"fingernum\":2,\"sendtime\":\"22:10\",\"returntime\":\"23:10\",\"passpeople\":\"张三\",\"confirm\":\"确认\"}";
        String jsonString3 =
                "{\"casename\":\"长沙杀人案\",\"fingernum\":3,\"sendtime\":\"22:10\",\"returntime\":\"23:10\",\"passpeople\":\"张三\",\"confirm\":\"确认\"}";
        String jsonString4 =
                "{\"casename\":\"长沙杀人案\",\"fingernum\":4,\"sendtime\":\"22:10\",\"returntime\":\"23:10\",\"passpeople\":\"张三\",\"confirm\":\"确认\"}";
        String jsonString5 =
                "{\"casename\":\"长沙杀人案\",\"fingernum\":5,\"sendtime\":\"22:10\",\"returntime\":\"23:10\",\"passpeople\":\"张三\",\"confirm\":\"确认\"}";
        FileOutputStream out;// 字节
        // 缓冲流
        BufferedWriter bufferedWriter = null;
        try{
            out =openFileOutput("ioi", Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(jsonString1);
            bufferedWriter.write("\n");
            bufferedWriter.write(jsonString2);
            bufferedWriter.write("\n");
            bufferedWriter.write(jsonString3);
            bufferedWriter.write("\n");
            bufferedWriter.write(jsonString4);
            bufferedWriter.write("\n");
            bufferedWriter.write(jsonString5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(bufferedWriter!=null)
            {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private List ReadData()
    {
        List<Person> list = new ArrayList<>();
        FileInputStream fileInputStream =null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try{
            try {
                fileInputStream = openFileInput("ioi");
                bufferedReader  = new BufferedReader(new InputStreamReader(fileInputStream));
                try {
                    String line = bufferedReader.readLine();
                    while (line!=null)
                    {
                        stringBuilder.append(line);
                        Gson gson = new Gson();
                        Person person = gson.fromJson(line,Person.class);
                        list.add(person);
                        line =bufferedReader.readLine();
                    }
                    Log.d("message",stringBuilder.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        finally {

            if(bufferedReader!=null)
            {
                try {
                    bufferedReader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }

    }

    private void SetObj()
    {
        textView1 = findViewById(R.id.lin1);
        textView2 = findViewById(R.id.lin2);
        textView3 = findViewById(R.id.lin3);
        textView4 = findViewById(R.id.lin4);
        textView5 = findViewById(R.id.lin5);
    }

}
