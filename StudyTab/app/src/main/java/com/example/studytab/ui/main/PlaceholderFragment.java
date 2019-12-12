package com.example.studytab.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studytab.R;
import com.example.studytab.news.News;
import com.example.studytab.news.NewsAdapter;
import com.example.studytab.news.NewsSource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    List<News> list;
    RecyclerView recyclerView;
    String Data = null;
    NewsSource newsSource = NewsSource.getInstance() ;
    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        SendRequest();
        recyclerView = root.findViewById(R.id.display_news);
        LinearLayoutManager layoutManagerNews = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManagerNews);
        getActivity().runOnUiThread(()->
                {
                    //Data 提取Json中的数据
                    Gson gson = new Gson();
                    list = gson.fromJson(Data,new TypeToken<List<News>>(){}.getType());
                    NewsAdapter newsAdapter = new NewsAdapter(list);
                    recyclerView.setAdapter(newsAdapter);
                }
        );
        return root;
    }

    private void SendRequest() {

        try {
            new Thread(()-> {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(newsSource.getBaseurl() + newsSource.getAmusement())
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
                    Data = Data.substring(29, length - 2);


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