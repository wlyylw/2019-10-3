package com.example.studylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<MinePageInformation> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MinePageInformation minePageInformation1 = new MinePageInformation("Title1","Infoimation1");
        MinePageInformation minePageInformation2 = new MinePageInformation("Title2","Infoimation2");
        MinePageInformation minePageInformation3 = new MinePageInformation("Title3","Infoimation3");
        list.add(minePageInformation1);
        list.add(minePageInformation2);
        list.add(minePageInformation3);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MinePageAdapter adapter = new MinePageAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
