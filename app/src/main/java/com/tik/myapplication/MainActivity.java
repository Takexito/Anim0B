package com.tik.myapplication;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


//import com.tik.myapplication.Parse.ParseSite;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();
        createRecyclerView();

        // запускаем новый поток для парсинга данных
        new ParseTask().execute();
    }

    private void getView() {
        mRecyclerView = findViewById(R.id.animeView);
        mProgressBar = findViewById(R.id.progressBar);
    }

    private void createRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
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

    // поток для парсинга
    @SuppressLint("StaticFieldLeak")
    class ParseTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            //ParseSite.setAnimeTitles();
            AnimeSingletone.setAnimes("{\n" +
                    "  \"id\":1,\n" +
                    "  \"title\":\"DxD\",\n" +
                    "  \"img\":\"http://images.sgcafe.net/2018/03/DZHL8JxVMAEA9AH.jpg\",\n" +
                    "  \"description\":\"the best anime of the year!\",\n" +
                    "  \"maxEp\":12,\n" +
                    "  \"currEp\":2,\n" +
                    "  \"episodes\":[\n" +
                    "    {\n" +
                    "      \"animeId\":1,\n" +
                    "      \"num\":1,\n" +
                    "      \"voicer\":\"Gachigasm\",\n" +
                    "      \"url\":\"//smotret-anime.ru/translations/embed/1782237\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"animeId\":1,\n" +
                    "      \"num\":1,\n" +
                    "      \"voicer\":\"anidub\",\n" +
                    "      \"url\":\"//video.sibnet.ru/shell.php?videoid=3284343\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"animeId\":1,\n" +
                    "      \"num\":2,\n" +
                    "      \"voicer\":\"Gachigasm\",\n" +
                    "      \"url\":\"//smotret-anime.ru/translations/embed/1795626\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"animeId\":1,\n" +
                    "      \"num\":2,\n" +
                    "      \"voicer\":\"anidub\",\n" +
                    "      \"url\":\"//video.sibnet.ru/shell.php?videoid=3309689\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}\n");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            mRecyclerView.setAdapter(new AnimeAdapter());
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

}