package com.tik.anim0b.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.tik.anim0b.R;
import com.tik.anim0b.adapter.AnimeAdapter;
import com.tik.anim0b.manager.AnimeManager;
import com.tik.anim0b.manager.TransitionManager;
import com.tik.anim0b.parse.ParseSite;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private static ConstraintLayout mDeatailed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getView();
        createRecyclerView();

        new ParseTask().execute();
    }

    private void getView() {
        mRecyclerView = findViewById(R.id.animeView);
        mProgressBar = findViewById(R.id.progressBar);
    }

    private void createRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void onStartButtonClick(View view){
        TransitionManager.onClickTransition(mRecyclerView, null);
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

    @SuppressLint("StaticFieldLeak")
    class ParseTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String json = ParseSite.getTitlesJson();
            AnimeManager.setAnime(json);
            ParseSite.clearJson();
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

    public static void VisibileDetailed(){
        mDeatailed.setVisibility(View.VISIBLE);
    }

    public static void invisibileDetailed(){
        mDeatailed.setVisibility(View.INVISIBLE);
    }

}