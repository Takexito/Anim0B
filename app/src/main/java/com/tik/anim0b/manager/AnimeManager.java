package com.tik.anim0b.manager;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tik.anim0b.pojo.Anime;
import com.tik.anim0b.pojo.Episode;
import com.tik.anim0b.parse.ParceJson;

import java.util.ArrayList;

public class AnimeManager {

    public static class SingletonHolder {
        public static final AnimeManager HOLDER_INSTANCE = new AnimeManager();
    }

    public static AnimeManager getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    private static ArrayList<Anime> animes = new ArrayList<>();

    public static ArrayList<Anime> getAnimes(){
        return animes;
    }

    public static Anime getAnime(int animeId){
        return animes.get(animeId - 1);
    }

    public static String getTitle(int animeId){
        return animes.get(animeId - 1).getTitle();
    }

    public static int getCurrEp(int animeId){
        return animes.get(animeId - 1).getCurr_ep();
    }

    public static int getMaxEp(int animeId){
        return animes.get(animeId - 1).getMax_ep();
    }

    public static String getImgUrl(int animeId){
        return animes.get(animeId - 1).getImgUrl();
    }

    public static Episode getEpisode(int animeId, int episodeId){
        return animes.get(animeId - 1).getEpisode(episodeId);
    }

    public static ArrayList<Episode> getEpisodes(int animeId){
        return animes.get(animeId - 1).getEpisodes();
    }

    public static String getDescription(int animeId){
        return animes.get(animeId - 1).getDescription();
    }

    public static int getAnimesSize(){
        return animes.size();
    }

    public static void setAnime(String json){
        clearAnime();
        ParceJson parceJson = new ParceJson(json);
        parceJson.nextJsonObject();
        animes.addAll(parceJson.mapAnime());
    }

    public static void setEpisodes(String json){
        ParceJson parceJson = new ParceJson(json);
        parceJson.nextJsonObject();
        Episode episode = parceJson.mapEpisode();
        Anime anime = AnimeManager.animes.get(episode.getAnimeId() - 1);
        episode.setAnime(anime);
        anime.addEpisode(episode);
    }

    public static void setAnimeImage(ImageView imageView, String url){
        Glide
                .with(imageView)
                .load(url)
                .into(imageView);
    }

    public static String getSpinerLabel(int episodeNum, String voicer){
        return "Серия " + episodeNum + " " + voicer;
    }

    private static void clearAnime() {
        animes = new ArrayList<>();
    }

}
