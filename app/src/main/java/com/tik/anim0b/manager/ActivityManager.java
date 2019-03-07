package com.tik.anim0b.manager;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.tik.anim0b.activity.FullNewsActivity;
import com.tik.anim0b.activity.FullScreenActivity;


public class ActivityManager {
    public static final String NEWS_ID = "com.tik.anim1r.NEWS_ID";
    public static final String VIDEO_URL = "com.tik.anim1r.VIDEO_URL";

    public static void openFullNews(Context context, long id) {
        id++;
        Toast toast = Toast.makeText(context, "Start activity! " + id , Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(context, FullNewsActivity.class);
        intent.putExtra(NEWS_ID, id);
        context.startActivity(intent);
    }

    public static void openFullScreen(Context context, String url) {
        Intent videoIntent = new Intent(context, FullScreenActivity.class);
        videoIntent.putExtra(VIDEO_URL, url);
        context.startActivity(videoIntent);
    }
}
