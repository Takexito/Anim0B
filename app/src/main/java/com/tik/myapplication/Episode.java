package com.tik.myapplication;

public class Episode {
    private Anime anime;
    private String voicer;
    private String url;

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public String getVoicer() {
        return voicer;
    }

    public void setVoicer(String voicer) {
        this.voicer = voicer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private int num;

    public Episode(Anime anime, String voicer, String url, int num) {
        this.anime = anime;
        this.voicer = voicer;
        this.url = url;
        this.num = num;
    }

    public Episode(int animeId, int num, String voicer, String url) {
        this.anime = AnimeSingletone.animeMap.get(animeId);
        this.voicer = voicer;
        this.url = url;
        this.num = num;
    }
}