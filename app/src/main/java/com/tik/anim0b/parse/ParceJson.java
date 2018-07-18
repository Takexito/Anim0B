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

    public boolean nextJsonObject() {
        try {
            this.json = new JSONObject(jsonString);
            return this.json != null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Anime> mapAnime() {
        List<Anime> animes = new ArrayList<>();
        try {
            JSONArray jsonArray = json.getJSONArray("animes");
            int id = 0;
            String title = "";
            String img = "";
            String description = "";
            int maxEp = 0;
            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id = jsonObject.getInt("id");
                title = jsonObject.getString("title");
                img = jsonObject.getString("img");
                description = jsonObject.getString("description");
                maxEp = jsonObject.getInt("maxEp");
                animes.add(new Anime(id, title, img, description, maxEp));
            }
            return animes;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return animes;
    }

    public Episode mapEpisode(){
        try {
            int animeId = json.getInt("animeId");
            int num = json.getInt("num");
            String voicer = json.getString("name");
            String url = json.getString("url");
            return new Episode(animeId, num, voicer, url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
            return new Episode(0, 0, "", "");
        }
}
