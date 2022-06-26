package com.example.vmusic;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.text.Layout;
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

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolderAlbum> {

    private Context mContext;
    //private ArrayList<MusicFiles> musicFiles ;
    Uri contenturi;

    public AlbumAdapter(Context context){
        this.mContext =context;
    }


    @NonNull
    @Override
    public MyViewHolderAlbum onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(mContext).inflate(R.layout.album_item,parent,false);
        return new MyViewHolderAlbum(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolderAlbum holder, final int position) {

        holder.textView.setText(musicFilesArrayList.get(position).getAlbum());

        contenturi = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,musicFilesArrayList.get(position).getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,""+musicFiles.get(position).getDuration(),Toast.LENGTH_SHORT).show();
                Intent albumIntent = new Intent(mContext,PlayerActivity.class);
                //playerIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                albumIntent.putExtra("position",position);
                albumIntent.putExtra("album",musicFilesArrayList.get(position).getAlbum());
                mContext.startActivity(albumIntent);
            }
        });

        byte[] art =getAlbumArt(contenturi);
        if (art != null){
            Glide.with(mContext).asBitmap()
                    .load(art)
                    .into(holder.album_art);
        } else {
            Glide.with(mContext).asDrawable()
                    .load(R.drawable.ic_baseline_music_note_24)
                    .into(holder.album_art);
        }

    }


    @Override
    public int getItemCount() {
        return musicFilesArrayList.size();
    }


    private byte[] getAlbumArt(Uri muri){
        MediaMetadataRetriever metadataRetriever =new MediaMetadataRetriever();
        metadataRetriever.setDataSource(mContext,muri);
        byte[] art =metadataRetriever.getEmbeddedPicture();
        metadataRetriever.release();
        return art;
    }

    public  class MyViewHolderAlbum extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView album_art;

        public MyViewHolderAlbum(View itemView) {
            super(itemView);
            this.textView = (TextView)itemView.findViewById(R.id.id_album_tv);
            this.album_art = (ImageView) itemView.findViewById(R.id.id_album_iv);

        }

    }
}
