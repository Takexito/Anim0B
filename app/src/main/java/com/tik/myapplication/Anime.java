package com.tik.myapplication;

import java.util.ArrayList;

public class Anime {
    private int id;
    private String title;
    private int max_ep;
    private int curr_ep;
    private String img;
    private String description;

    private ArrayList<Episode> episodes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMax_ep() {
        return max_ep;
    }

    public void setMax_ep(int max_ep) {
        this.max_ep = max_ep;
    }

    public int getCurr_ep() {
        return curr_ep;
    }

    public void setCurr_ep(int curr_ep) {
        this.curr_ep = curr_ep;
    }

    public String getImg() {
        return img;

    }

    public void setImg() {
        if (this.id % 2 == 1) this.img = "http://images.sgcafe.net/2018/03/DZHL8JxVMAEA9AH.jpg";
        else
            this.img = "https://magnitudeanimereviews.files.wordpress.com/2016/02/lhucm4g.jpg?w=550&h=826";
    }

    public Episode getEpisode(int id) {
        return episodes.get(id);
    }

    public void addEpisode(Episode episode) {
        episodes.add(episode);
    }

    public int getEpisodesSize() {
        return episodes.size();
    }

    public Anime(int id, String title, int max_ep, int curr_ep) {
        this.id = id;
        this.title = title;
        this.max_ep = max_ep;
        this.curr_ep = curr_ep;
        setImg();
        episodes = new ArrayList<>();
    }

    public Anime(int id, String title, String img, String description, int max_ep, int curr_ep, ArrayList<Episode> episodes) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.description = description;
        this.max_ep = max_ep;
        this.curr_ep = curr_ep;
        this.episodes = episodes;
    }
}
