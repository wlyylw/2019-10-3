package com.example.studylist;

import java.util.ArrayList;

public class MinePageService {
    ArrayList<MinePageInformation> list;

    private static  MinePageService instance;

    public static MinePageService getInstance() {
        if(instance==null)
            instance = new MinePageService();
        return  instance;
    }

    public ArrayList<MinePageInformation> getMineService(){
        return list;
    }

    private  MinePageService()
    {
        list = new ArrayList<>();
        InitEntity();
    }

    private void InitEntity() {
        MinePageInformation minePageInformation = new MinePageInformation
                ("我的关注","用户/圈子 >","我的消息","回复/赞/通知 >","我的已购","购买的课程/直播 >",
                "金币商城","点击就送500金币 >","京东特供","新人领188元红包 >","易览天下","三分钟了解天下事 >","用户鉴帖","邀你鉴定跟帖质量 >",
                        "我的钱包","", "免流量看新闻","","意见反馈","","扫一扫","","设置",""
                );

        list.add(minePageInformation);

    }
}
