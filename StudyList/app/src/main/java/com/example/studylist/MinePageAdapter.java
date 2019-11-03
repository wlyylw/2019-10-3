package com.example.studylist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MinePageAdapter  extends  RecyclerView.Adapter<MinePageAdapter.ViewHolder>{

    private List<MinePageInformation> list;

    static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView title1;
        TextView information1;
        TextView title2;
        TextView information2;
        public ViewHolder(View view)
        {
            super(view);
            title1 = view.findViewById(R.id.title1);
            information1 = view.findViewById(R.id.information1);
            title2 = view.findViewById(R.id.title2);
            information2 = view.findViewById(R.id.information2);
        }

    }
    public MinePageAdapter(List<MinePageInformation> list){
        this.list=list;
    }


    @NonNull
    @Override
    public MinePageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MinePageAdapter.ViewHolder holder, int position) {
        MinePageInformation minePageInformation = list.get(position);
        holder.title1.setText(minePageInformation.getTitle());
        holder.information1.setText(minePageInformation.getInformation());
        holder.title2.setText(minePageInformation.getTitle());
        holder.information2.setText(minePageInformation.getInformation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
