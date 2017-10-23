package com.tik.myapplication.Parse;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tik on 02.08.2017.
 */

public class ParseSite {

    public final static String HOST = "https://anim1r.000webhostapp.com";

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
            anime_titles_links = doc.select("iframe#film_main");
            link = anime_titles_links.attr("src");
        }
        return link;
    }

    public static String getTitlesLinks(long id) {
        Elements anime_titles_links = null;
        List<String> links = null;
        Document doc = null;//Здесь хранится будет разобранный html документ
        try {
            Connection connection = Jsoup.connect(HOST);
            doc = connection.get();
        } catch (IOException e) {e.printStackTrace();}


        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc != null) {
            //Находим заголовки тайтлов
            anime_titles_links = doc.select("div#film");
            anime_titles_links = anime_titles_links.select(".title");
            anime_titles_links = anime_titles_links.select("a");
            links = anime_titles_links.eachAttr("href");
        }
        return links.get((int)id);
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

    public static String getPageTitle(String URL){
        String title = "error!";
        Document doc = null;//Здесь хранится будет разобранный html документ
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {e.printStackTrace();}

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc!=null){
            title = doc.title();
        }
        return title;
    }

    public static Elements getAnimeTitles(){
        Elements anime_titles = null;
        Document doc = null;//Здесь хранится будет разобранный html документ
        try {
            doc = Jsoup.connect(HOST).get();
        } catch (IOException e) {e.printStackTrace();}

        //Если всё считалось, что вытаскиваем из считанного html документа заголовок
        if (doc!=null){
            //Находим заголовки тайтлов
            anime_titles = doc.select("div#film");
            anime_titles = anime_titles.select(".title");
        }
        return anime_titles;
    }

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
}
