package com.example.administrator.zhihudaily.bean;

import java.util.ArrayList;

/**
 * Created by shinoko on 2016/8/5.
 */
public class BannerNews {

    private String title;
    private String image;
    private String ga_prefix;
    private int type;
    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public static ArrayList<BannerNews> getTestList(){
        ArrayList<BannerNews> list = new ArrayList<BannerNews>();

        String[] titles = {"26 岁学习钢琴晚不晚，听听钢琴家的建议",
                "整点儿奥运 ·「明天开学，里约你的作业写完了吗？」",
                "读读日报 24 小时热门 TOP 5 · 亲历里约枪击"};
        String[] images = {"http://pic1.zhimg.com/a80d65841158c381afd919707d8f43f8.jpg",
                "http://pic4.zhimg.com/831e9592c4fe9672551db9d58f461c1b.jpg",
                "http://pic2.zhimg.com/f459e7effa7c6a2be75df907f87d6179.jpg"};

        for (int i = 0; i < 3; i++) {
            BannerNews bn = new BannerNews();
            bn.setTitle(titles[i]);
            bn.setImage(images[i]);
        }

        return list;
    }


}
