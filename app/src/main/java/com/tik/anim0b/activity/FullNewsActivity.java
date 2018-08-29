package com.tik.anim0b.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tik.anim0b.R;
import com.tik.anim0b.manager.ActivityManager;
import com.tik.anim0b.manager.AnimeManager;
import com.tik.anim0b.parse.ParseSite;

import java.util.ArrayList;

public class FullNewsActivity extends AppCompatActivity {


    private int mAnimeId;
    private static String videoLink;

    private ImageView mTitleImage;
    private TextView mDescriptionText;
    //private Button mStartButton;
    private ProgressBar mProgressBar;
    private ConstraintLayout mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);
        getWidget();
        mAnimeId = (int) getIntent().getLongExtra(ActivityManager.NEWS_ID, 0);
        mDescriptionText.setText(AnimeManager.getDescription(mAnimeId));
        new NewTask().execute(mAnimeId);

    }

    private void getWidget() {
        mTitleImage = findViewById(R.id.titleImage);
        mDescriptionText = findViewById(R.id.descripText);
        //mStartButton = findViewById(R.id.startButton);
        mProgressBar = findViewById(R.id.progressBar);
        mMainLayout = findViewById(R.id.mainLot);
    }

    public void start() {
        ActivityManager.openFullScreen(this, getVideoLink());
    }

    public void onStartButtonClick(View view) {
        //new ParseVideoTask().execute();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите серию");
        builder.setAdapter(new ArrayAdapter(this, android.R.layout.select_dialog_item, spData()), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new StartVideoTask().execute(mAnimeId, i);
            }
        });

        builder.create().show();

    }

    @SuppressLint("StaticFieldLeak")
    private class NewTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            AnimeManager.getAnime(integers[0]).setCurr_ep(ParseSite.getEpNum(
                    "https://play.shikimori.org/animes/"
                            + AnimeManager.getAnime(integers[0]).getId()
                            + '-'
                            + AnimeManager.getAnime(integers[0]).getTitle()
                    )
            );
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            AnimeManager.setAnimeImage(mTitleImage, AnimeManager.getImgUrl(mAnimeId));
            mMainLayout.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ParseVideoTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            setVideoLink(ParseSite.getVideoUrl(AnimeManager.getEpisode(mAnimeId, integers[0]).getUrl()));
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            start();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class StartVideoTask extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            String json = ParseSite.getEpisodesJson(AnimeManager.getAnime(integers[0]), integers[1] + 1);//mSpinner.getSelectedItemPosition());
            AnimeManager.setEpisodes(json);
            ParseSite.clearJson();
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            AlertDialog.Builder builder = new AlertDialog.Builder(FullNewsActivity.this);
            builder.setTitle("Выберите вариант");
            builder.setAdapter(new ArrayAdapter(FullNewsActivity.this, android.R.layout.select_dialog_item, getSpinnerData()), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new ParseVideoTask().execute(i);
                }
            });
            builder.create().show();

        }
    }

    private String[] getSpinnerData() {
        ArrayList<String> data = new ArrayList<>();
        int l = AnimeManager.getEpisodesSize(mAnimeId);
        for (int i = 0; i < l; i++) {
            data.add(AnimeManager.getSpinerLabel(
                    AnimeManager.getEpisode(mAnimeId, i).getNum(),
                    AnimeManager.getEpisode(mAnimeId, i).getVoicer()
                    )
            );
        }
        return data.toArray(new String[0]);
    }

    private String[] spData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 1; i <= AnimeManager.getAnime(mAnimeId).getCurr_ep(); i++) {
            data.add("Серия " + String.valueOf(i));
        }
        return data.toArray(new String[0]);
    }

    private static void setVideoLink(String link) {
        videoLink = link;
    }

    private static String getVideoLink() {
        return videoLink;
    }

}
