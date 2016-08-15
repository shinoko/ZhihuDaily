package com.example.administrator.zhihudaily.model;

import java.util.List;

/**
 * Created by shinoko on 2016/8/15.
 */
public class ThemeList {

    private String limit;
    private String[] subscribed;
    private List<ThemeItem> others;

    public String[] getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(String[] subscribed) {
        this.subscribed = subscribed;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public List<ThemeItem> getOthers() {
        return others;
    }

    public void setOthers(List<ThemeItem> others) {
        this.others = others;
    }


}
