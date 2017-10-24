package com.tik.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.tik.myapplication.Parse.ParseSite;

import org.jsoup.select.Elements;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private ListView listView;
    private ProgressBar progressBar;
    private ArrayList<String> titles = new ArrayList<>();
    ArrayAdapter<String> adapter;
    protected static final String NEWS_TITLE = "com.tik.anim1r.NEWS_TITLE";
    protected static final String NEWS_ID = "com.tik.anim1r.NEWS_ID";
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView1);
        progressBar = findViewById(R.id.progressBar);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);
        MyTask mt = new MyTask();
        mt.execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posY, long id) {
                openFullNews(titles.get((int) id), id);
            }
        });
    }

    private void openFullNews(String title, long id){
        Intent intent = new Intent(this, FullNewsActivity.class);
        intent.putExtra(NEWS_TITLE, title);
        intent.putExtra(NEWS_ID, id);
        //intent.putExtra("videoUrl", "http://fs.myvi.ru/video/1688140.mp4?uid=&puid=&ref=&d=4392&rnd=662526491&sig=31e7af42f2a4e3dbfb26b0a60c947cb3");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class MyTask extends AsyncTask<Void, Void, Void> {

        Elements anime_titles;//Тут храним значение заголовка сайта
        String title;

        @Override
        protected Void doInBackground(Void... params) {
            anime_titles = ParseSite.getAnimeTitles();
            title = ParseSite.getPageTitle();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            titles = (ArrayList<String>) anime_titles.eachText();
            adapter.addAll(titles);
            listView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}