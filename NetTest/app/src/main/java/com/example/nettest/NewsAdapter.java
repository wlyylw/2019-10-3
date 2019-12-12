package com.example.nettest;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class NewsAdapter extends  RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private List<News> list;

    static  class ViewHolder extends RecyclerView.ViewHolder{
        View ClickView;
        TextView title;
        TextView source;
        TextView count;
        ImageView imageview;
        public ViewHolder(View view)
        {
            super(view);
            source   = view.findViewById(R.id.news_source);
            title = view.findViewById(R.id.news_title);
            count = view.findViewById(R.id.news_count);
            imageview = view.findViewById(R.id.index_news_img);
            ClickView =view;
        }

    }
    public NewsAdapter(List<News> list){
        this.list=list;
    }


    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_item_news,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.ClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
//                News News = list.get(position);
//                Toast.makeText(v.getContext(), News.getsource()+"的细节没写",Toast.LENGTH_SHORT).show();;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        News news = list.get(position);
        holder.title.setText(news.getTitle());
        holder.source.setText(news.getSource());
        holder.count.setText( news.getCommentCount() + "跟帖" );
        Glide.with(holder.ClickView).load(news.getImgsrc()).into(holder.imageview);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
