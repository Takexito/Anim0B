package com.tik.anim0b.manager;

import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;

public class TransitionManager {

    private static boolean isTransitGo;

    public static void onClickTransition(ViewGroup view, ViewGroup parent){
        TransitionSet set = new TransitionSet();
        set.addTransition(new ChangeBounds());
        set.addTransition(new Fade());

        // set.addTransition(new ChangeBounds());

        // выполняться они будут одновременно
        set.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        // уставим свою длительность анимации
        set.setDuration(1500);
        // и изменим Interpolator
        set.setInterpolator(new AnticipateOvershootInterpolator(1.0f));

        if(!isTransitGo) {
            android.transition.TransitionManager.beginDelayedTransition(view);
            view.setVisibility(ViewGroup.INVISIBLE);

            //MainActivity.VisibileDetailed(); //TODO: change without static method on MainActivity 1
            //view.setVisibility(ViewGroup.VISIBLE);
            //parent.setVisibility(View.INVISIBLE);
        }
        else {
            android.transition.TransitionManager.beginDelayedTransition(view);
            view.setVisibility(ViewGroup.VISIBLE);
            //MainActivity.invisibileDetailed(); //TODO: change without static method on MainActivity 2
            //parent.setVisibility(View.VISIBLE);
        }

        isTransitGo = !isTransitGo;
    }
}
