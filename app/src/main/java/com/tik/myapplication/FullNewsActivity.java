package com.tik.myapplication;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

//import com.tik.myapplication.Parse.ParseSite;

public class FullNewsActivity extends AppCompatActivity {

    private Anime mAnime;

    private ImageView mTitleImage;
    private Spinner mSpinner;
    private TextView mDescriptionText;
    private Button mStartButton;
    private ProgressBar mProgressBar;
    private ConstraintLayout mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);
        getWidget();
        mAnime = getAnime(getIntent().getLongExtra(ActivityManager.NEWS_ID, 0));
        // new StartVideoTask().execute(mAnime);
    }

    private Anime getAnime(long id) {
        return AnimeSingletone.animes.get((int) id);
    }

    protected void onStart() {
        super.onStart();
        parceData();
        createSpinner();
        mMainLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void parceData() {
        Glide
                .with(mTitleImage)
                .load(mAnime.getImg())
                .into(mTitleImage);
    }

    private void createSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getData());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setPrompt("Series");
    }

    private String[] getData() {
        ArrayList<String> data = new ArrayList<>();
        int l = mAnime.getEpisodesSize();
        for (int i = 1; i <= l; i++) {
            data.add("Серия " + mAnime.getEpisode(i - 1).getNum() + " " + mAnime.getEpisode(i - 1).getVoicer());
        }
        return data.toArray(new String[0]);
    }

    public void onStartButtonClick(View view) {
        ActivityManager.openFullScreen(this, mAnime.getEpisode(mSpinner.getSelectedItemPosition()).getUrl());
    }

    private void getWidget() {
        mTitleImage = findViewById(R.id.titleImage);
        mSpinner = findViewById(R.id.spinner);
        mDescriptionText = findViewById(R.id.descripText);
        mStartButton = findViewById(R.id.startButton);
        mProgressBar = findViewById(R.id.progressBar);
        mMainLayout = findViewById(R.id.mainLot);
    }

    @SuppressLint("StaticFieldLeak")
    private class StartVideoTask extends AsyncTask<Anime, Void, Void> {
        protected Void doInBackground(Anime... anime) {
            //ParseSite.getVideoLink(anime[(int) mSpinner.getSelectedItemId()]);
            //AnimeSingletone.animes.get((int) mSpinner.getSelectedItemId());
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mMainLayout.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);

        }
    }

}
