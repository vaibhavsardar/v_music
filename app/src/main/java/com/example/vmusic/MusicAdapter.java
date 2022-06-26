package com.example.vmusic;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.vmusic.MainActivity.musicFilesArrayList;


public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {

    ArrayList<MusicFiles> musicFiles ;
    Context mContext;
    //String mflag;
    Uri uria;

    public MusicAdapter(ArrayList<MusicFiles> musicFiles, Context mContext) {
        this.musicFiles = musicFiles;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicAdapter.MyViewHolder holder,final int position) {
        holder.textView.setText(musicFilesArrayList.get(position).getTitles());

        uria = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,musicFilesArrayList.get(position).getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,""+musicFiles.get(position).getDuration(),Toast.LENGTH_SHORT).show();
                Intent playerIntent = new Intent(mContext,PlayerActivity.class);
                playerIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                playerIntent.putExtra("position",position);
//                playerIntent.putExtra("artiest",musicFilesArrayList.get(position).getArtiest());
//                playerIntent.putExtra("duration_end",musicFilesArrayList.get(position).getDuration());
                mContext.startActivity(playerIntent);
            }
        });

        byte[] art =getAlbumArt(uria);
        if (art != null){
            Glide.with(mContext).asBitmap()
                    .load(art)
                    .into(holder.album_art);
        } else {
            Glide.with(mContext).asDrawable()
                    .load(R.drawable.ic_baseline_music_note_24)
                    .into(holder.album_art);
        }

        //Log.d("com.vaibha","vai.."+musicFiles.get(position).getTitles());

        //Toast.makeText(mContext,"vai"+musicFiles.get(position).getTitles(),Toast.LENGTH_SHORT).show();


    }

    private byte[] getAlbumArt(Uri uria){
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(mContext,uria);
        byte[] art =metadataRetriever.getEmbeddedPicture();
        metadataRetriever.release();
        return art;
    }

    @Override
    public int getItemCount() {
        //Log.d("com.vaibha","arrry size.."+musicFiles.size());

        return musicFilesArrayList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView album_art;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView)itemView.findViewById(R.id.tv_item);
            this.album_art = (ImageView) itemView.findViewById(R.id.imageItem);

        }

    }
}
