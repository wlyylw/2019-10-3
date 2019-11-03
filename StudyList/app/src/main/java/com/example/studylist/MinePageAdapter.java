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
        TextView line3_title1;
        TextView line3_information1;
        TextView line3_title2;
        TextView line3_information2;
        TextView line3_title3;
        TextView line3_information3;


        TextView line5_title1;
        TextView line5_information1;
        TextView line5_title2;
        TextView line5_information2;
        TextView line5_title3;
        TextView line5_information3;
        TextView line5_title4;
        TextView line5_information4;
        TextView line5_title5;
        TextView line5_information5;

        TextView line4_title1;
        TextView line4_information1;
        TextView line4_title2;
        TextView line4_information2;
        TextView line4_title3;
        TextView line4_information3;
        TextView line4_title4;
        TextView line4_information4;

        public ViewHolder(View view)
        {
            super(view);

            line5_title1 = view.findViewById(R.id.mine_line5_title1);
            line5_information1 = view.findViewById(R.id.mine_line5_information1);
            line5_title2 = view.findViewById(R.id.mine_line5_title2);
            line5_information2 = view.findViewById(R.id.mine_line5_information2);
            line5_title3 = view.findViewById(R.id.mine_line5_title3);
            line5_information3 = view.findViewById(R.id.mine_line5_information3);
            line5_title4 = view.findViewById(R.id.mine_line5_title4);
            line5_information4 = view.findViewById(R.id.mine_line5_information4);
            line5_title5 = view.findViewById(R.id.mine_line5_title5);
            line5_information5 = view.findViewById(R.id.mine_line5_information5);

            line3_title1 = view.findViewById(R.id.mine_line3_title1);
            line3_information1 = view.findViewById(R.id.mine_line3_inforamtion1);
            line3_title2 = view.findViewById(R.id.mine_line3_title2);
            line3_information2 = view.findViewById(R.id.mine_line3_inforamtion2);
            line3_title3 = view.findViewById(R.id.mine_line3_title3);
            line3_information3 = view.findViewById(R.id.mine_line3_inforamtion3);

            line4_title1 = view.findViewById(R.id.mine_line4_title1);
            line4_information1 = view.findViewById(R.id.mine_line4_information1);
            line4_title2 = view.findViewById(R.id.mine_line4_title2);
            line4_information2 = view.findViewById(R.id.mine_line4_information2);
            line4_title3 = view.findViewById(R.id.mine_line4_title3);
            line4_information3 = view.findViewById(R.id.mine_line4_information3);
            line4_title4 = view.findViewById(R.id.mine_line4_title4);
            line4_information4 = view.findViewById(R.id.mine_line4_information4);

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
        holder.line3_title1.setText(minePageInformation.getLine3_title1());
        holder.line3_information1.setText(minePageInformation.getLine3_information_information1());
        holder.line3_title2.setText(minePageInformation.getLine3_title2());
        holder.line3_information2.setText(minePageInformation.getLine3_information_information2());
        holder.line3_title3.setText(minePageInformation.getLine3_title3());
        holder.line3_information3.setText(minePageInformation.getLine3_information_information3());

        holder.line5_title1.setText(minePageInformation.getLine5_title1());
        holder.line5_information1.setText(minePageInformation.getLine5_information_information1());
        holder.line5_title2.setText(minePageInformation.getLine5_title2());
        holder.line5_information2.setText(minePageInformation.getLine5_information_information2());
        holder.line5_title3.setText(minePageInformation.getLine5_title3());
        holder.line5_information3.setText(minePageInformation.getLine5_information_information3());
        holder.line5_title4.setText(minePageInformation.getLine5_title4());
        holder.line5_information4.setText(minePageInformation.getLine5_information_information4());
        holder.line5_title5.setText(minePageInformation.getLine5_title5());
        holder.line5_information5.setText(minePageInformation.getLine5_information_information5());

        holder.line4_title1.setText(minePageInformation.getLine4_title1());
        holder.line4_information1.setText(minePageInformation.getLine4_information_information1());
        holder.line4_title2.setText(minePageInformation.getLine4_title2());
        holder.line4_information2.setText(minePageInformation.getLine4_information_information2());
        holder.line4_title3.setText(minePageInformation.getLine4_title3());
        holder.line4_information3.setText(minePageInformation.getLine4_information_information3());
        holder.line4_title4.setText(minePageInformation.getLine4_title4());
        holder.line4_information4.setText(minePageInformation.getLine4_information_information4());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
