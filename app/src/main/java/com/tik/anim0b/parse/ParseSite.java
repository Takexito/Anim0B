package com.tik.anim0b.parse;

import com.tik.anim0b.manager.AnimeManager;
import com.tik.anim0b.pojo.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class ParseSite {
    public static JSONArray jsonAnimes = new JSONArray();
    public static JSONArray jsonEpisodes = new JSONArray();
    public static JSONArray jsonAllEpisodes = new JSONArray();
    private static String HOST = "https://play.shikimori.org/animes/";

    public static String getTitlesJson() {
//        String string = "";
//        try {
//            string = Jsoup.connect("http://188.134.25.56:4567/anime").ignoreContentType(true).execute().body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        getTitle();
        return jsonAnimes.toString();
    }

    public static String getEpisodesJson(Anime anime) {
//        String string = "";
//        try {
//            string = Jsoup.connect("http://188.134.25.56:4567/episodes/" + anime.getId()).ignoreContentType(true).execute().body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        getEpisode(AnimeManager.getAnimeIndex(anime), anime.getId(), HOST + anime.getId() + '-' + anime.getTitle());
        getFunDub();
        return jsonAllEpisodes.toString();
    }

    public static void addAnimeToJson(String id, String title, String img) {

        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("title", title);
            json.put("img", img);
            jsonAnimes.put(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void addEpisodeToJson(int idd, int id, int num, int titleId, String voicer, String url) {

        JSONObject json = new JSONObject();
        try {
            json.put("idd", idd);
            json.put("id", id);
            json.put("num", num);
            json.put("animeId", titleId);
            json.put("name", voicer);
            json.put("url", url);
            jsonEpisodes.put(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getTitle() {
        String result = "";
        Elements anime_titles_links = null;
        Elements imgsLink;
        List<String> links = null;
        List<String> imgs = null;
        Document doc = null; //Здесь хранится будет разобранный html документ
        try {
            Connection connection = Jsoup.connect("https://play.shikimori.org/");
            doc = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc != null) {
            //Находим заголовки тайтлов
            anime_titles_links = doc.select(".cover");
            imgsLink = anime_titles_links.select("img");
            imgs = imgsLink.eachAttr("src");
            links = anime_titles_links.eachAttr("data-href");
            for (String link : links) {
                addAnimeToJson(link.substring(0, link.indexOf('-')).substring(link.lastIndexOf('/') + 1), link.substring(link.indexOf('-') + 1), imgs.get(links.indexOf(link)));
            }
        }

        return result;
    }

    public static String getEpisode(int idd, int titleId, String titleLink) {
        String result = "";
        Elements episodesLink = null;
        List<String> links = null;
        Document doc = null; //Здесь хранится будет разобранный html документ
        try {
            Connection connection = Jsoup.connect(titleLink);
            doc = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc != null) {
            //Находим заголовки тайтлов
            episodesLink = doc.select(".c-anime_video_episodes");
            episodesLink = episodesLink.select(".b-video_variant");
            links = episodesLink.select("a").eachAttr("href");
            for (String link : links) {
                addEpisodeToJson(idd, 1, links.indexOf(link) + 1, titleId, "", link);
            }
        }

        return result;
    }

    public static String getFunDub() {
        for (int i = 0; i < jsonEpisodes.length(); i++) {
            try {
                JSONObject json = (JSONObject) jsonEpisodes.get(i);
                String url = (String) json.get("url");

                int id = 0;
                String voicer = "";
                Element allEpisodes;
                Elements episodes;
                Document doc = null; //Здесь хранится будет разобранный html документ
                try {
                    Connection connection = Jsoup.connect(url);
                    doc = connection.get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                allEpisodes = doc.select("div[data-kind=\"all\"]").last();
                episodes = allEpisodes.select(".b-video_variant");
                for (Element episode : episodes) {
                    id = Integer.parseInt(episode.attr("data-video_id"));
                    voicer = episode.select("video-hosting").text() + " " + episode.select("video-author").text();
                    String link = episode.select("a").attr("href");
                    url = getVideoUrl(link);
                    json.remove("id");
                    json.put("id", id);
                    json.put("name", voicer);
                    json.remove("url");
                    json.put("url", url);
                    jsonAllEpisodes.put(json);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private static String getVideoUrl(String link) {
        String result;
        Document doc = null; //Здесь хранится будет разобранный html документ
        try {
            Connection connection = Jsoup.connect(link);
            doc = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = doc.select("div.player-area").select("iframe").attr("src");
        return result;
    }
}