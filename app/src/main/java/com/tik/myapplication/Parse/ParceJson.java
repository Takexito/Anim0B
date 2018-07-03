package com.tik.myapplication.Parse;

import com.tik.myapplication.Anime;
import com.tik.myapplication.Episode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParceJson {
    private String jsonString;
    private JSONObject json;

    public ParceJson(String json) {
        this.jsonString = json;
    }

    public boolean nextJsonObject() {
        try {
            //Object jsonTokener = new JSONTokener(jsonString).nextValue();
            //this.json = (JSONObject) jsonTokener;
            this.json = new JSONObject(jsonString);
            return this.json != null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Anime mapAnime() {
        try {
            int id = json.getInt("id");
            String title = json.getString("title");
            String img = json.getString("img");
            String description = json.getString("description");
            int maxEp = json.getInt("maxEp");
            int currEp = json.getInt("currEp");
            JSONArray episodes = json.getJSONArray("episodes");

            return new Anime(id, title, img, description, maxEp, currEp, mapEpisode(episodes));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Anime(0, "", "", "", 0, 0, new ArrayList<Episode>());
    }

    private ArrayList<Episode> mapEpisode(JSONArray episodes) throws JSONException {
        ArrayList<Episode> episodes1 = new ArrayList<>();
        for (int i = 0; i < episodes.length(); i++) {
            JSONObject jsonObject = episodes.getJSONObject(i);
            episodes1.add(new Episode(
                    jsonObject.getInt("animeId"),
                    jsonObject.getInt("num"),
                    jsonObject.getString("voicer"),
                    jsonObject.getString("url")
            ));
        }
        return episodes1;
    }
}
