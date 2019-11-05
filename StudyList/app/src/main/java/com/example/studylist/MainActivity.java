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

    MinePageDynamicService minePageDynamicService;
    RecyclerView recyclerViewDynamic;

    MinePagePersonService minePagePersonService;
    RecyclerView recyclerViewPerson;

    private List<MinePageInformation> list = new ArrayList<>();
    private List<MineDynamic> listMineDynamic = new ArrayList<>();
    private List<MinePagePerson> listMinePerson = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //Information
//        minePageService = MinePageService.getInstance();
//        list = minePageService.getMineService();
//        recyclerView = findViewById(R.id.recycler_view_three);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        MinePageAdapter adapter = new MinePageAdapter(list);
//        recyclerView.setAdapter(adapter);

        //Dynamic
        minePageDynamicService = MinePageDynamicService.getInstance();
        listMineDynamic = minePageDynamicService.getList();
        recyclerViewDynamic = findViewById(R.id.recycler_view_mine_dynamic);
        LinearLayoutManager layoutManagerDynamic = new LinearLayoutManager(this);
        layoutManagerDynamic.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewDynamic.setLayoutManager(layoutManagerDynamic);
        MinePageDynamicAdapter minePageDynamicAdapter =new MinePageDynamicAdapter(listMineDynamic);
        recyclerViewDynamic.setAdapter(minePageDynamicAdapter);

        //Person
        minePagePersonService = MinePagePersonService.getInstance();
        listMinePerson = minePagePersonService.getList();
        recyclerViewPerson = findViewById(R.id.recycler_view_mine_person);
        RecyclerView.LayoutManager layoutManagerPerson = new LinearLayoutManager(this);
        recyclerViewPerson.setLayoutManager(layoutManagerPerson);
        MinePagePersonAdapter minePagePersonAdapter = new MinePagePersonAdapter(listMinePerson);
        recyclerViewPerson.setAdapter(minePagePersonAdapter);



    }
}
