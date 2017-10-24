package com.tik.myapplication.Parse;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tik on 02.08.2017.
 */

public class ParseSite {

    public final static String HOST = "https://play.shikimori.org/animes/status/ongoing"; //"https://anim1r.000webhostapp.com";

    /**
     * Method that return link on video
     *
     * This method return link on video from page, for video player.
     *
     * @author Tik
     * @version 2017.1024
     * @since 1.0
     */
    public static String getVideoLink(String url) {
        Elements anime_titles_links = null;
        String link = "";
        Document doc = null;//Здесь хранится будет разобранный html документ
        try {
            Connection connection = Jsoup.connect(url);
            doc = connection.get();
        } catch (IOException e) {e.printStackTrace();}


        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc != null) {
            //Находим заголовки тайтлов
            anime_titles_links = doc.select("div.player-area");
            anime_titles_links = anime_titles_links.select("iframe");
            link = anime_titles_links.attr("src");
        }
        return link;
    }

    public static List<String> getSeriaLinks(String link){
        Element anime_titles_link;
        List<String> links = new ArrayList<>();
        Document doc = null; //Здесь хранится будет разобранный html документ

        links.add(link);

        try {
            Connection connection = Jsoup.connect(link);
            doc = connection.get();
        } catch (IOException e) {e.printStackTrace();}

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc != null) {
            anime_titles_link = doc.select("a.next").first();
            link = "http:" + anime_titles_link.attr("href");
            if (link.charAt(link.length() - 1) != '1') links.addAll(ParseSite.getSeriaLinks(link));
        }
        return links;
    }
    /**
     * Method that return link on anime page
     *
     * This method return link on anime page of selected anime title
     *
     * @author Tik
     * @version 2017.1024
     * @since 1.0
     */
    public static String getTitlesLinks(long id) {
        Elements anime_titles_links = null;
        List<String> links = null;
        Document doc = null; //Здесь хранится будет разобранный html документ
        try {
            Connection connection = Jsoup.connect(HOST);
            doc = connection.get();
        } catch (IOException e) {e.printStackTrace();}

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc != null) {
            //Находим заголовки тайтлов
            anime_titles_links = doc.select(".cover");
            anime_titles_links = anime_titles_links.select("a");
            links = anime_titles_links.eachAttr("href");
        }
        return "https://play.shikimori.org"+links.get((int)id);
    }


    /**
     * Method that return page title
     *
     * This method return main page's title
     *
     * @author Tik
     * @version 2017.1024
     * @since 1.0
     */
    public static String getPageTitle(){

        String title = "error!";
        Document doc = null;//Здесь хранится будет разобранный html документ
        try {
            doc = Jsoup.connect(HOST).get();
        } catch (IOException e) {e.printStackTrace();}

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc!=null){
            title = doc.title();
        }
        return title;
    }


    /**
     * Method that return anime titles list
     *
     * This Method parse anime titles, from main page and return list of titles
     *
     * @author Tik
     * @version 2017.1024
     * @since 1.0
     */
    public static Elements getAnimeTitles(){
        Elements anime_titles = null;
        Document doc = null;//Здесь хранится будет разобранный html документ
        try {
            doc = Jsoup.connect(HOST).get();
        } catch (IOException e) {e.printStackTrace();}

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc!=null){
            //Находим заголовки тайтлов
            anime_titles = doc.select(".cover");
        }
        return anime_titles;
    }


    //побочка
    public static Elements getAnimeTitles(String URL){
        Elements anime_titles = null;
        Document doc = null;//Здесь хранится будет разобранный html документ
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {e.printStackTrace();}

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc!=null){
            //Находим заголовки тайтлов
            anime_titles = doc.select("div#film");
            anime_titles = anime_titles.select(".title");
        }
        return anime_titles;
    }

    public static String getPageTitle(String URL){
        String title = "error!";
        Document doc = null;//Здесь хранится будет разобранный html документ
        try {
            Connection connection = Jsoup.connect(URL);
            connection.request(connection.request().followRedirects(true));
            doc = connection.get();
            String lol = "lolll";
        }
        catch (IOException e) {
            Log.e("PPC", e.getStackTrace().toString());}

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc!=null){
            title = doc.title();
        }
        return title;
    }

    public static List<String> getTitlesLinks(){
        Elements anime_titles_links = null;
        List<String> links = null;
        Document doc = null;//Здесь хранится будет разобранный html документ
        try {
            doc = Jsoup.connect(HOST).get();
        } catch (IOException e) {e.printStackTrace();}

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc!=null){
            //Находим заголовки тайтлов
            anime_titles_links = doc.select("div#film");
            anime_titles_links = anime_titles_links.select(".title");
            anime_titles_links = anime_titles_links.select("a");
            links = anime_titles_links.eachAttr("href");
        }
        return links;
    }
}
