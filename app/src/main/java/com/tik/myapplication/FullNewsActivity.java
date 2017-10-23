package com.tik.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tik.myapplication.Parse.ParseSite;

public class FullNewsActivity extends AppCompatActivity {

    String link, title;
    Intent intent;
    Intent videoIntent;

    ImageView titleImage;
    Spinner spinner;
    TextView descriptionText;
    Button startButton;

    ArrayAdapter<String> adapter;
    String[] data = {"one", "two", "three", "four", "five"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);
        intent = getIntent();
        MyTask myTask = new MyTask();
        myTask.execute();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getWidget();
        videoIntent = new Intent(this, FullscreenActivity.class);

        spinner.setPrompt("Series");

    }

    public void onStartButtonClick(View view){
        startActivity(videoIntent);
    }

    private void getWidget(){
        titleImage = (ImageView) findViewById(R.id.titleImage);
        spinner = (Spinner) findViewById(R.id.spinner);
        descriptionText = (TextView) findViewById(R.id.descripText);
        startButton = (Button) findViewById(R.id.startButton);

        spinner.setAdapter(adapter);
    }

    class MyTask extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... params) {
            link = ParseSite.HOST + ParseSite.getTitlesLinks(intent.getLongExtra(MainActivity.NEWS_ID, (long) 0));
            link = ParseSite.getVideoLink(link);
            title = link + " " + intent.getStringExtra(MainActivity.NEWS_TITLE);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            videoIntent.putExtra("videoUrl", link);


        }
    }

}
