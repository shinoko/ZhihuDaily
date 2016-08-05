package com.example.administrator.zhihudaily.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shinoko on 2016/8/5.
 */
public class News {

    private String date;
    private String title;
    private List<String> images;
    private String ga_prefix;
    private int type;
    private int id;
    private boolean multipic;

    private boolean isRead = false;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }


    public static ArrayList<News> getTestList(){
        ArrayList<News> list = new ArrayList<News>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                News n = new News();
                int day = 5-i;
                n.setDate("8月"+day+"日");
                n.setTitle("标题标题标题标题标题标题标题标题标题标题标题标题"+i+"-"+j);
                list.add(n);
            }
        }

        return list;
    }

}
