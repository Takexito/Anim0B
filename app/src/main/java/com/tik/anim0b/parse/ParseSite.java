//package com.tik.myapplication.Parse;
//
//import com.tik.myapplication.pojo.Anime;
//import com.tik.myapplication.AnimeManager;
//import com.tik.myapplication.pojo.Episode;
//
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Tik on 02.08.2017.
// */
//
//public class ParseSite {
//
//    public final static String HOST = "https://play.shikimori.org/animes/status/ongoing"; //"https://anim1r.000webhostapp.com";
//
//    /**
//     * Method that return link on video
//     * <p>
//     * This method return link on video from page, for video player, without 'http:' prefix
//     *
//     * @author Tik
//     * @version 2017.1024
//     * @since 1.0
//     */
//
//    public static void getVideoLink(Anime anime) {
//        Element element;
//        String string;
//        Document doc = null;//Здесь хранится будет разобранный html документ
//        try {
//            Connection connection = Jsoup.connect("http://188.134.25.56:4567/episodes/" + anime.getId());
//            doc = connection.get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
//        if (doc != null) {
//            element = doc.body();
//            string = element.text();
//            getEpisodes(string, anime);
//        }
//    }
//
//
//    private static void getEpisodes(String str, Anime anime) {
//        int index = str.indexOf("|");
//
//        int i = 0;
//        //1 DxD anidub a-1,v-1,n-1| 1 DxD meduza a-1 v-2 n-1| 2 DxD anidub a1 v1 n2| 2 DxD meduza a1 v2 n2|
//        while (index > 0) {
//            String string = str.substring(0, index);
//            anime.addEpisode(createEpisode(string, anime));
//            str = str.substring(index + 1);
//            index = str.indexOf("|");
//            i++;
//        }
//    }
//
//    private static Episode createEpisode(String str, Anime anime){
//        int num;
//        String voicer;
//        String url;
//
//        int index = str.indexOf("!");
//        num = Integer.parseInt(str.substring(0, index));
//        str = str.substring(index + 1);
//
//        index = str.indexOf("!");
//        voicer = str.substring(0, index);
//        str = str.substring(index + 1);
//
//        index = str.indexOf("!");
//        url = str.substring(0, str.length());
//
//        return new Episode(anime,voicer,url,num);
//    }
//
//
//    public static void setAnimeTitles() {
//        Element anime_element = null;
//        String string = "";
//        ArrayList<String> anime_titles = new ArrayList<>();
//        Document doc = null;//Здесь хранится будет разобранный html документ
//        try {
//            doc = Jsoup.connect("http://188.134.25.56:4567/anime").get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
//        if (doc != null) {
//            //Находим заголовки тайтлов
//            anime_element = doc.body();
//            string = anime_element.text();
//            getTitles(anime_titles, string);
//            for (int i = 0; i + 1 <= anime_titles.size(); i++) {
//                AnimeManager.animes.add(new Anime(i + 1, anime_titles.get(i), 2, 1));
//            }
//        }
//    }
//
//    private static void getTitles(ArrayList<String> list, String str) {
//        int index2 = str.indexOf("|");
//        while (index2 > 0) {
//            String string = str.substring(0, index2);
//            str = str.substring(index2 + 1);
//            list.add(string);
//            index2 = str.indexOf("|");
//        }
//    }
//
//
//    public static String getVideoLin(String url) {
//        Elements anime_titles_links = null;
//        String link = "";
//        Document doc = null;//Здесь хранится будет разобранный html документ
//        try {
//            Connection connection = Jsoup.connect(url);
//            doc = connection.get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
//        if (doc != null) {
//            anime_titles_links = doc.select("div.player-area"); //Находим div с плеером
//            anime_titles_links = anime_titles_links.select("iframe");// Take iframe
//            link = anime_titles_links.attr("src");// Take link without http:
//        }
//        return link;
//    }
//
//    public static List<String> getSeriaLinks(String link) {
//        Element anime_titles_link;
//        List<String> links = new ArrayList<>();
//        Document doc = null; //Здесь хранится будет разобранный html документ
//
//        links.add(link);
//
//        try {
//            Connection connection = Jsoup.connect(link);
//            doc = connection.get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
//        if (doc != null) {
//            anime_titles_link = doc.select("a.next").first();
//            link = "http:" + anime_titles_link.attr("href");
//            if (link.charAt(link.length() - 1) != '1') links.addAll(ParseSite.getSeriaLinks(link));
//        }
//        return links;
//    }
//
//    /**
//     * Method that return link on anime page
//     * <p>
//     * This method return link on anime page of selected anime title
//     *
//     * @author Tik
//     * @version 2017.1024
//     * @since 1.0
//     */
//    public static String getTitlesLinks(long id) {
//        Elements anime_titles_links = null;
//        List<String> links = null;
//        Document doc = null; //Здесь хранится будет разобранный html документ
//        try {
//            Connection connection = Jsoup.connect(HOST);
//            doc = connection.get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
//        if (doc != null) {
//            //Находим заголовки тайтлов
//            anime_titles_links = doc.select(".cover");
//            anime_titles_links = anime_titles_links.select("a");
//            links = anime_titles_links.eachAttr("href");
//        }
//        return "https://play.shikimori.org" + links.get((int) id);
//    }
//
//
//    /**
//     * Method that return page title
//     * <p>
//     * This method return main page's title
//     *
//     * @author Tik
//     * @version 2017.1024
//     * @since 1.0
//     */
//    public static String getPageTitle() {
//
//        String title = "error!";
//        Document doc = null;//Здесь хранится будет разобранный html документ
//        try {
//            doc = Jsoup.connect(HOST).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
//        if (doc != null) {
//            title = doc.title();
//        }
//        return title;
//    }
//
//
//    /**
//     * Method that return anime titles list
//     * <p>
//     * This Method parse anime titles, from main page and return list of titles
//     *
//     * @author Tik
//     * @version 2017.1024
//     * @since 1.0
//     */
//    public static Elements getAnime() {
//        Elements anime_titles = null;
//        Document doc = null;//Здесь хранится будет разобранный html документ
//        try {
//            doc = Jsoup.connect(HOST).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
//        if (doc != null) {
//            //Находим заголовки тайтлов
//            anime_titles = doc.select(".cover");
//        }
//        return anime_titles;
//    }
//
//    public static ArrayList<String> getAnimeTitless() {
//        Element anime_element = null;
//        String string = "";
//        ArrayList<String> anime_titles = new ArrayList<>();
//        Document doc = null;//Здесь хранится будет разобранный html документ
//        try {
//            doc = Jsoup.connect("http://185.159.129.2:4567/anime").get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
//        if (doc != null) {
//            //Находим заголовки тайтлов
//            anime_element = doc.body();
//            string = anime_element.text();
//            getTitles(anime_titles, string);
//        }
//
//        return anime_titles;
//    }
//}
//
