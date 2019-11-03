package com.example.studylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MinePageService minePageService;
    private List<MinePageInformation> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        minePageService = MinePageService.getInstance();
        list = minePageService.getMineService();
        recyclerView = findViewById(R.id.recycler_view_three);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MinePageAdapter adapter = new MinePageAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
