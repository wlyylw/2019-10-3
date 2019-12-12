package com.example.studytab.news;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studytab.R;


public class DetailNews extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        Intent intent = getIntent();
        textView = findViewById(R.id.TestUrl);
        String url = intent.getStringExtra("detail_url");
        textView.setText(url);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl(url);
        //TODO:解析数据


    }
}
