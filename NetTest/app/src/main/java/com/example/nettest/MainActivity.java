package com.example.nettest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.util.List;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    List<News> list;
    RecyclerView recyclerView;
    String Data = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SendRequest();

        recyclerView =  findViewById(R.id.display_news);
        LinearLayoutManager layoutManagerNews = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerNews);

        this.runOnUiThread(()->
        {
            //Data 提取Json中的数据
            Gson gson = new Gson();
            list = gson.fromJson(Data,new TypeToken<List<News>>(){}.getType());


            NewsAdapter newsAdapter = new NewsAdapter(list);
            recyclerView.setAdapter(newsAdapter);
        }
        );
    }
    private void SendRequest()
    {
        NewsSource newsSource = NewsSource.getInstance() ;
        try {
            new Thread(() -> {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(newsSource.getBaseurl()+newsSource.getAmusement())
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Data = response.body().string();

                    int length = Data.length();
                    Data = Data.substring(29,length - 2);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            Thread.sleep(1000);
        }
        catch (Exception e){

        }
    }






}

