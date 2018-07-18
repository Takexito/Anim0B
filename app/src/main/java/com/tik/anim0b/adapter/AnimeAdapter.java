package com.tik.anim0b.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tik.anim0b.R;
import com.tik.anim0b.manager.ActivityManager;
import com.tik.anim0b.manager.AnimeManager;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private ImageView mImageView;
        private TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            mImageView = v.findViewById(R.id.list_img_title);
            mTextView = v.findViewById(R.id.list_text_title);
        }

        @Override
        public void onClick(View view) {
            ActivityManager.openFullNews(view.getContext(), (long) getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public AnimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeAdapter.ViewHolder holder, final int position) {
        AnimeManager.setAnimeImage(holder.mImageView, AnimeManager.getImgUrl(position + 1));
        holder.mTextView.setText(AnimeManager.getTitle(position + 1));
        //holder.mImageView.setImageResource(R.drawable.dw_a);
    }

    @Override
    public long getItemId(int i) {
        return AnimeManager.getAnime(i).hashCode();
    }

    @Override
    public int getItemCount() {
        return AnimeManager.getAnimesSize();
    }

//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            if(view == null){
//                view = getLayoutInflater().inflate(R.layout.list_item, viewGroup, false);
//            }
//            String url = AnimeManager.animes.get(i).img;
//
//            Glide
//                    .with(view)
//                    .load(url)
//                    .into((ImageView) view);
//            return view;
//        }
}