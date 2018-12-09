package com.tik.anim0b.manager;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tik.anim0b.parse.ParceJson;
import com.tik.anim0b.pojo.Anime;
import com.tik.anim0b.pojo.Episode;

import java.util.ArrayList;
import java.util.List;

public class AnimeManager {

    private static ArrayList<Anime> animes = new ArrayList<>();

    public static ArrayList<Anime> getAnimes(){
        return animes;
    }

    public static Anime getAnime(int animeId){
        return animes.get(animeId);
    }

    public static String getTitle(int animeId){
        return animes.get(animeId).getTitle();
    }

    public static int getCurrEp(int animeId){
        return animes.get(animeId).getCurr_ep();
    }

    public static int getMaxEp(int animeId){
        return animes.get(animeId).getMax_ep();
    }

    public static String getImgUrl(int animeId){
        return animes.get(animeId).getImgUrl();
    }

    public static Episode getEpisode(int animeId, int episodeId){
        return animes.get(animeId).getEpisode(episodeId);
    }

    public static void clearEpisodes(int animeId) {
        animes.get(animeId).clearEpisode();
    }

    public static int getEpisodesSize(int animeId) {
        return animes.get(animeId).getEpisodes().size();
    }

    public static ArrayList<Episode> getEpisodes(int animeId){
        return animes.get(animeId).getEpisodes();
    }

    public static String getDescription(int animeId){
        return animes.get(animeId).getDescription();
    }

    public static int getAnimesSize(){
        return animes.size();
    }

    public static void setAnime(String json){
        if (animes.size() != 0) clearAnime();
        ParceJson parceJson = new ParceJson(json);
        animes.addAll(parceJson.mapAnime());
    }

    public static void setEpisodes(String json){
        ParceJson parceJson = new ParceJson(json);
        List<Episode> episodes = parceJson.mapEpisode();
        for (int i = 0; i < episodes.size(); i++) {
            Episode episode = episodes.get(i);
            Anime anime = AnimeManager.animes.get(episode.getAnimeId());
            episode.setAnime(anime);
            anime.addEpisode(episode);
            anime.setMax_ep(i);
        }
    }

    public static int getAnimeIndex(Anime anime) {
        return AnimeManager.animes.indexOf(anime);
    }

    public static void setAnimeImage(ImageView imageView, String url){
        Glide
                .with(imageView)
                .load(url)
                .into(imageView);
    }

    public static String getSpinerLabel(int episodeNum, String voicer){
        return "Серия " + (episodeNum) + " " + getNameVoicer(voicer);
    }

    private static String getNameVoicer(String voicer) {
        int i = voicer.indexOf("(");
        if (i == -1)
            return voicer;
        else return voicer.substring(0, i - 1);
    }

    private static void clearAnime() {
        animes = new ArrayList<>();
    }

}
