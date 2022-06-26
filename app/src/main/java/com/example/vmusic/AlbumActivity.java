package com.example.vmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.vmusic.MainActivity.musicFilesArrayList;

public class AlbumActivity extends AppCompatActivity {

    ImageView album_iv;
    RecyclerView recyclerView;
    ArrayList<MusicFiles> albumList = new ArrayList<>();
    MusicAdapter musicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        album_iv =(ImageView) findViewById(R.id.id_albumActivity_iv);



//        for(int i = 0; musicFilesArrayList.size() > i; i++){
//            if(musicFilesArrayList.get(i).getAlbum().equals(intent.getStringExtra("album"))){
//                albumList.add(musicFilesArrayList.get(i));
//            }
//        }

        recyclerView =(RecyclerView)findViewById(R.id.recyeview_album_item);

        if (musicFilesArrayList.size() >0){
            musicAdapter = new MusicAdapter(musicFilesArrayList,this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(musicAdapter);
        }

        Intent intent = getIntent();
        Uri uri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,musicFilesArrayList.get(intent.getIntExtra("position",0)).getId());

        byte[] art =getAlbumArt(uri);
        if (art != null){
            Glide.with(this).asBitmap()
                    .load(art)
                    .into(album_iv);
        } else {
            Glide.with(this).asDrawable()
                    .load(R.drawable.ic_baseline_music_note_24)
                    .into(album_iv);
        }

    }

    private byte[] getAlbumArt(Uri muri){
        MediaMetadataRetriever metadataRetriever =new MediaMetadataRetriever();
        metadataRetriever.setDataSource(this,muri);
        byte[] art =metadataRetriever.getEmbeddedPicture();
        metadataRetriever.release();
        return art;
    }
}