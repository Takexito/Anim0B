package com.tik.anim0b.pojo;

import java.util.ArrayList;

public class Anime {
    private int titleId;
    private String title;

    private int max_ep;
    private int curr_ep;
    private String imgUrl;
    private int imgResource;
    private String description;

    private ArrayList<Episode> episodes;

    public int getTitleId() {
        return titleId;
    }

    public String getTitle() {
        return title;
    }

    public int getMax_ep() {
        return max_ep;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMax_ep(int max_ep) {
        this.max_ep = max_ep;
    }

    public void setCurr_ep(int curr_ep) {
        this.curr_ep = curr_ep;
    }

    public int getCurr_ep() {
        return curr_ep;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Episode getEpisode(int id) {
        return episodes.get(id);
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public String getDescription() {
        return description;
    }

    public void addEpisode(Episode episode) {
        episodes.add(episode);
        curr_ep++;
    }

    public void clearEpisode() {
        episodes.clear();
        curr_ep = 0;
    }

    //public int getEpisodesSize() {
      //  return episodes.size();
    //}

    public Anime(int id, String title, String imgUrl, int max_ep) {
        this.titleId = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.max_ep = max_ep;
        this.curr_ep = 0;
        this.episodes = new ArrayList<>();
    }

}
