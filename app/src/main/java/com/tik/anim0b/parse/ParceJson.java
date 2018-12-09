package com.tik.anim0b.parse;

import com.tik.anim0b.pojo.Anime;
import com.tik.anim0b.pojo.Episode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ParceJson {
    private String jsonString;
    private JSONObject json;

    public ParceJson(String json) {
        this.jsonString = json;
    }

    public List<Anime> mapAnime() {
        List<Anime> animes = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            int id;
            String title;
            String img;
            int maxEp;
            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id = jsonObject.getInt("id");
                title = jsonObject.getString("title");
                img = jsonObject.getString("img");//"http://cdn.seasonvar.ru/oblojka/498.jpg"; //
                maxEp = 10;//jsonObject.getInt("curEp");
                animes.add(new Anime(id, title, img, maxEp));
            }
            return animes;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return animes;
    }

    public List<Episode> mapEpisode() {
        List<Episode> episodes = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int animeId = jsonObject.getInt("animeId");
                int num = jsonObject.getInt("num");
                int titleId = jsonObject.getInt("titleId");
                String voicer = jsonObject.getString("name");
                String url = jsonObject.getString("url");
                episodes.add(new Episode(jsonObject.getInt("id"), animeId, titleId, num, voicer, url));
            }
            return episodes;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        episodes.add(new Episode(0, 0, 0, 0, "", ""));
        return episodes;
    }
}
