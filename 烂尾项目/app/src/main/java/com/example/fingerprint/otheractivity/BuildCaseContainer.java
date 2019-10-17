package com.example.fingerprint.otheractivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.fingerprint.R;
import com.example.fingerprint.bannerview.BannerItem;
import com.example.fingerprint.bannerview.DataUtils;
import com.example.fingerprint.viewpager.DepthPageTransformer;
import com.example.fingerprint.adapters.ViewPagerAdapter;
import com.lany.banner.BannerView;
import com.lany.banner.SimpleBannerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BuildCaseContainer extends AppCompatActivity {

    private ViewPager viewPager;

    private List<View> viewList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_bulid_case_container);
        viewPager = findViewById(R.id.otheractivity_bulid_case_viewpage);
        viewList = new ArrayList<>();
        LayoutInflater layoutInflater =getLayoutInflater().from(BuildCaseContainer.this);
        View viewFirst  = layoutInflater.inflate(R.layout.activity_build_case_first,null);
        View viewSecond = layoutInflater.inflate(R.layout.activity_build_case_second,null);
        viewList.add(viewFirst);
        viewList.add(viewSecond);
        viewPager.setAdapter(new ViewPagerAdapter(viewList));
        viewPager.setPageTransformer(true,new DepthPageTransformer());
        Toolbar toolbar = viewFirst.findViewById(R.id.otheractivity_toolbar_build_case_first);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                finish();
            }
        });


        BannerView bannerView = viewSecond.findViewById(R.id.build_case_second_banner_view);
        List<BannerItem> items2 =new ArrayList<>();
        BannerItem item = new BannerItem();
        item.setPic("http://f.hiphotos.baidu.com/image/pic/item/4ec2d5628535e5dd623069aa7cc6a7efce1b62a3.jpg");
        items2.add(item);
        items2.addAll(DataUtils.getItems());
        bannerView.setAdapter(new SimpleBannerAdapter<BannerItem>(items2) {

            @Override
            public void bindImage(ImageView imageView, BannerItem item) {
                RequestOptions options = new RequestOptions()
                        .fitCenter()
                        .dontAnimate()

                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                Glide.with(BuildCaseContainer.this)
                        .setDefaultRequestOptions(options)
                        .load(item.getPic())
                        .into(imageView);
            }

            @Override
            public void bindTitle(TextView titleText, BannerItem item) {
                titleText.setText("" + item.getTitle());

            }


        });
    }
    }


