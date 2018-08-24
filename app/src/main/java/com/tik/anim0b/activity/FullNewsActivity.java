package com.tik.anim0b.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.tik.anim0b.R;
import com.tik.anim0b.manager.ActivityManager;
import com.tik.anim0b.manager.AnimeManager;
import com.tik.anim0b.parse.ParseSite;

import java.util.ArrayList;

public class FullNewsActivity extends AppCompatActivity {

//    private final static String JSON =
//            "{\"id\":0,\"num\":1,\"animeId\":1,\"name\":\"AniDUB (Ancord \\u0026 n_o_i_r)\",\"url\":\"https://play.shikimori.org/animes/5114-fullmetal-alchemist-brotherhood/video_online/1/1543213\"} \n" +
//            "{\"id\":0,\"num\":1,\"animeId\":1,\"name\":\"CGinfo\",\"url\":\"https://play.shikimori.org/animes/5114-fullmetal-alchemist-brotherhood/video_online/1/1613696\"} \n" +
//            "{\"id\":0,\"num\":1,\"animeId\":1,\"name\":\"CGinfo / 30 голосая озвучка (это не шутка)\",\"url\":\"https://play.shikimori.org/animes/5114-fullmetal-alchemist-brotherhood/video_online/1/868809\"} \n" +
//            "{\"id\":0,\"num\":1,\"animeId\":1,\"name\":\"MCA\",\"url\":\"https://play.shikimori.org/animes/5114-fullmetal-alchemist-brotherhood/video_online/1/873475\"}";

    private int mAnimeId;

    private ImageView mTitleImage;
    private Spinner mSpinner;
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
        new StartVideoTask().execute(mAnimeId);
        mDescriptionText.setText(AnimeManager.getDescription(mAnimeId));

    }

    private void getWidget() {
        mTitleImage = findViewById(R.id.titleImage);
        mSpinner = findViewById(R.id.spinner);
        mDescriptionText = findViewById(R.id.descripText);
        //mStartButton = findViewById(R.id.startButton);
        mProgressBar = findViewById(R.id.progressBar);
        mMainLayout = findViewById(R.id.mainLot);
    }

    public void onStartButtonClick(View view) {
        ActivityManager.openFullScreen(this, AnimeManager.getEpisode(mAnimeId, mSpinner.getSelectedItemPosition()).getUrl());
    }

    @SuppressLint("StaticFieldLeak")
    private class StartVideoTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            String json = ParseSite.getEpisodesJson(AnimeManager.getAnime(integers[0]));
            AnimeManager.setEpisodes(json);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            createSpinner();
            AnimeManager.setAnimeImage(mTitleImage, AnimeManager.getImgUrl(mAnimeId));
            mMainLayout.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void createSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getSpinnerData());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setPrompt("Series");
    }

    private String[] getSpinnerData() {
        ArrayList<String> data = new ArrayList<>();
        int l = AnimeManager.getEpisodesSize(mAnimeId);
        for (int i = 1; i < l; i++) {
            data.add(AnimeManager.getSpinerLabel(AnimeManager.getEpisode(mAnimeId, i).getNum(),
                    AnimeManager.getEpisode(mAnimeId, i).getVoicer()));
        }
        return data.toArray(new String[0]);
    }


}
