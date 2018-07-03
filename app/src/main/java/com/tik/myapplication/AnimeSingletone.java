package com.tik.myapplication;

import android.annotation.SuppressLint;

import com.tik.myapplication.Parse.ParceJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnimeSingletone {

    public static class SingletonHolder {
        public static final AnimeSingletone HOLDER_INSTANCE = new AnimeSingletone();
    }

    public static AnimeSingletone getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    public static ArrayList<Anime> animes = new ArrayList<>();
    @SuppressLint("UseSparseArrays")
    public static Map<Integer, Anime> animeMap = new HashMap<>();

    public static void setAnimes(String json) {
        clearAnime();
        ParceJson parceJson = new ParceJson(json);
        // while (parceJson.nextJsonObject()) {
        parceJson.nextJsonObject();
        Anime anime = parceJson.mapAnime();
        animes.add(anime);
        animeMap.put(anime.getId(), anime);
        //}
    }

    private static void clearAnime() {
        animes = new ArrayList<>();
        animeMap = new HashMap<>();
    }

    public static ArrayList<String> getTitles() {
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i + 1 <= animes.size(); i++) {
            titles.add(animes.get(i).getTitle());
        }
        return titles;
    }

    public static String[] getImageUrls() {
        ArrayList<String> urls = new ArrayList<>();
        for (int i = 0; i + 1 <= animes.size(); i++) {
            urls.add(animes.get(i).getImg());
        }
        return urls.toArray(new String[]{});
    }
}
