package com.example.fingerprint.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> ViewList;

    public ViewPagerAdapter(List<View> viewList)
    {
        this.ViewList = viewList;
    }

    @Override
    public int getCount() {
        return ViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        container.addView(ViewList.get(position));
        return ViewList.get(position);
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(ViewList.get(position));
    }
}
