package com.tik.anim0b.parse;


import com.tik.anim0b.manager.AnimeManager;

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


    public static void clearJson() {
        jsonAnimes = new JSONArray();
        jsonEpisodes = new JSONArray();
        jsonAllEpisodes = new JSONArray();
    }

    public static String getTitlesJson() {

        getTitle();
        return jsonAnimes.toString();
    }

    public static String getEpisodesJson(Integer animeId, int num) {
        int titleId = AnimeManager.getAnime(animeId).getTitleId();

        getFunDub(animeId, titleId, num, HOST + titleId + '-'                                 //make link as https://play.shikimori.org/animes/
                + AnimeManager.getAnime(animeId).getTitle());                               // 38528-quanzhi-fashi-3rd-season/video_online/2

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

    public static void getTitle() {
        Elements anime_titles_links;
        Elements imgsLink;
        List<String> links;
        List<String> imgs;
        Document doc = null;
        //load 10 page of Anime Titles
        for (int i = 1; i <= 10; i++) {
            try {
                Connection connection = Jsoup.connect("https://play.shikimori.org/page/" + i);
                doc = connection.get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (doc != null) {
                anime_titles_links = doc.select(".cover");
                imgsLink = anime_titles_links.select("img");
                imgs = imgsLink.eachAttr("src");
                links = anime_titles_links.eachAttr("data-href");
                for (String link : links) {
                    //https://play.shikimori.org/animes/33064-uchuu-senkan-yamato-2202-ai-no-senshi-tachi/video_online
                    addAnimeToJson(link.substring(0, link.indexOf('-')).substring(link.lastIndexOf('/') + 1), //get anime Id
                            link.substring(link.indexOf('-') + 1), //get title
                            imgs.get(links.indexOf(link)) //get img
                    );
                }
            }
        }

    }

    public static Integer getEpNum(String titleLink) {
        String result;
        int num = 0;
        Element episodesNum;
        Document doc = null; //Здесь хранится будет разобранный html документ
        try {
            Connection connection = Jsoup.connect(titleLink);
            doc = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (doc != null) {
            episodesNum = doc.select(".b-video_variant").last();
            if (episodesNum != null) {
                result = episodesNum.select("a").attr("href");
                num = Integer.parseInt(result.substring(result.lastIndexOf("/") + 1));
            }
        }

        return num;
    }


    public static String getVideoUrl(String link) {
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

    public static String getFunDub(int animeId, int titleId, int epNum, String titleLink) {
        try {
            String url = titleLink + "/video_online/" + epNum;

            int id;
            String voicer;
            Element allEpisodes;
            Elements episodes;
            Document doc = null; //Здесь хранится будет разобранный html документ
            try {
                Connection connection = Jsoup.connect(url);
                doc = connection.get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert doc != null;
            boolean flag = true;
            for (int i = 0; i < 2; i++) {
                if (flag) allEpisodes = doc.select("div[data-kind=\"fandub\"]").last();
                else allEpisodes = doc.select("div[data-kind=\"subtitles\"]").last();
                if (allEpisodes != null) {
                    episodes = allEpisodes.select(".b-video_variant");
                    for (Element episode : episodes) {
                        JSONObject jsonEpisode = new JSONObject();
                        id = Integer.parseInt(episode.attr("data-video_id"));
                        String hosting = episode.select(".video-hosting").text();
                        String auth = episode.select(".video-author").text();
                        voicer = hosting + " " + auth;
                        String link = episode.select("a").attr("href");
                        jsonEpisode.put("id", id);
                        jsonEpisode.put("name", voicer);
                        jsonEpisode.put("animeId", animeId);
                        jsonEpisode.put("num", epNum);
                        jsonEpisode.put("titleId", titleId);
                        jsonEpisode.put("url", link);
                        jsonAllEpisodes.put(jsonEpisode);
                    }
                }
                flag = !flag;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

}