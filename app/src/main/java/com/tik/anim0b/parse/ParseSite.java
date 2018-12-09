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


    public static void clearJson() {
        jsonAnimes = new JSONArray();
        jsonEpisodes = new JSONArray();
        jsonAllEpisodes = new JSONArray();
    }
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

    public static String getEpisodesJson(Anime anime, int num) {
//        String string = "";
//        try {
//            string = Jsoup.connect("http://188.134.25.56:4567/episodes/" + anime.getId()).ignoreContentType(true).execute().body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //getEpisode(AnimeManager.getAnimeIndex(anime), anime.getId(), HOST + anime.getId() + '-' + anime.getTitle());
        getFunDub(AnimeManager.getAnimeIndex(anime), anime.getId(), num, HOST + anime.getId() + '-' + anime.getTitle());
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
        Elements anime_titles_links;
        Elements imgsLink;
        List<String> links;
        List<String> imgs;
        Document doc = null; //Здесь хранится будет разобранный html документ
        for (int i = 1; i <= 10; i++) {
            try {
                Connection connection = Jsoup.connect("https://play.shikimori.org/page/" + i);
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
        }

        return result;
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
            } else {
            }


        }

        return num;
    }


    public static String getFunDub(int idd, int titleId, int epNum, String titleLink) {
        //for (int i = 0; i < jsonEpisodes.length(); i++) {
            try {
                //JSONObject json = (JSONObject) jsonEpisodes.get(i);
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
                allEpisodes = doc.select("div[data-kind=\"fandub\"]").last();
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
                        jsonEpisode.put("idd", idd);
                        jsonEpisode.put("num", epNum);
                        jsonEpisode.put("animeId", titleId);
                        jsonEpisode.put("url", link);
                        jsonAllEpisodes.put(jsonEpisode);
                    }
                }


                allEpisodes = doc.select("div[data-kind=\"subtitles\"]").last();
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
                        jsonEpisode.put("idd", idd);
                        jsonEpisode.put("num", epNum);
                        jsonEpisode.put("animeId", titleId);
                        jsonEpisode.put("url", link);
                        jsonAllEpisodes.put(jsonEpisode);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        // }
        return "";
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
}