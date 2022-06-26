package com.example.vmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumitemAdapter extends RecyclerView.Adapter<AlbumitemAdapter.MyViewHolderAlbumItem>{

    Context context;


    @NonNull
    @Override
    public MyViewHolderAlbumItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.music_item,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderAlbumItem holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public  class MyViewHolderAlbumItem extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView album_art;

        public MyViewHolderAlbumItem(View itemView) {
            super(itemView);
            this.textView = (TextView)itemView.findViewById(R.id.id_album_tv);
            this.album_art = (ImageView) itemView.findViewById(R.id.id_album_iv);

        }

    }
}
