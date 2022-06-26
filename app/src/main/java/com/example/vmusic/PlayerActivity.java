package com.example.vmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media.AudioAttributesCompat;

import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.vmusic.MainActivity.musicFilesArrayList;

public class PlayerActivity extends AppCompatActivity {
    TextView songname ,artist,duration_end,getDuration_plyed;
    ImageView albumArt,playpause;
    SeekBar seekBar;
    ArrayList<MusicFiles> musicFiles =new ArrayList<>();
    static MediaPlayer mediaPlayer;
    static Uri uri;
    private Handler handler = new Handler();
    int position;
    int stopedTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        musicFiles =musicFilesArrayList;
        initViews();
        ongetIntent();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //Toast.makeText(getApplicationContext(),"seekbar progress:"+i,Toast.LENGTH_SHORT).show();
                if(mediaPlayer != null && b){
                    mediaPlayer.seekTo(i*1000);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    int currentSeekbarPosition =mediaPlayer.getCurrentPosition()/1000;
                    seekBar.setProgress(currentSeekbarPosition);
                    getDuration_plyed.setText(formmtedTime(currentSeekbarPosition));


                }
                handler.postDelayed(this,1000);
            }
        });



//        double min =((Long.parseLong("278011"))/1000);
//        long durationend_min= TimeUnit.MICROSECONDS.toMinutes(Long.parseLong("278011"));

        //duration_end.setText( Long.toString(durationend_min));

       // mediaPlayer = new MediaPlayer();

//        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                .setUsage(AudioAttributes.USAGE_MEDIA)
//                .build()
//        );
//
//        try {
//            mediaPlayer.setDataSource(getApplicationContext(),uri);
//            mediaPlayer.prepare();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                mediaPlayer.start();
//
//            }
//        }).start();



        //long end =(mediaPlayer.getDuration()/1000);
        //duration_end.setText(Long.toString(end));







    }

    private void ongetIntent(){
        Intent intent =getIntent();
        position =intent.getIntExtra("position",-1);

        songname.setText(musicFiles.get(position).getTitles());

        uri =ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,musicFiles.get(position).getId());
        artist.setText(musicFiles.get(position).getArtiest());

        MediaMetadataRetriever metadataRetriever =new MediaMetadataRetriever();
        metadataRetriever.setDataSource(getApplicationContext(),uri);
        byte[] art =metadataRetriever.getEmbeddedPicture();
        metadataRetriever.release();
        if (art != null){
            Glide.with(getApplicationContext()).asBitmap()
                    .load(art)
                    .into(albumArt);
        } else {
            Glide.with(getApplicationContext()).asDrawable()
                    .load(R.drawable.ic_baseline_music_note_24)
                    .into(albumArt);
        }


        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
            playpause.setImageResource(R.drawable.ic_baseline_pause);

        } else {
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
            playpause.setImageResource(R.drawable.ic_baseline_pause);
        }
        seekBar.setMax(mediaPlayer.getDuration()/1000);
        duration_end.setText(formmtedTime(mediaPlayer.getDuration()/1000));

    }

    private String formmtedTime(int currentTime){
        String totalOut;
        String totalOut0;

        String sec =String.valueOf(currentTime % 60);
        String min =String.valueOf(currentTime /60);
        totalOut = min +":" + sec;
        totalOut0 = min +":"+"0" + sec;
        if(sec.length() ==1){
            return totalOut0;
        }else{
            return totalOut;
        }
    }

    public void playNext(View viewnext){

        if(viewnext.getId() == R.id.id_btn_next){
            position = position + 1;
        } else
            position = position - 1;

        uri =ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,musicFiles.get(position).getId());
        songname.setText(musicFiles.get(position).getTitles());

        MediaMetadataRetriever metadataRetriever =new MediaMetadataRetriever();
        metadataRetriever.setDataSource(getApplicationContext(),uri);
        byte[] art =metadataRetriever.getEmbeddedPicture();
        metadataRetriever.release();
        if (art != null){
            Glide.with(getApplicationContext()).asBitmap()
                    .load(art)
                    .into(albumArt);
        } else {
            Glide.with(getApplicationContext()).asDrawable()
                    .load(R.drawable.ic_baseline_music_note_24)
                    .into(albumArt);
        }

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
            playpause.setImageResource(R.drawable.ic_baseline_pause);

        } else {
            mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
            playpause.setImageResource(R.drawable.ic_baseline_pause);
        }
        seekBar.setMax(mediaPlayer.getDuration()/1000);
        duration_end.setText(formmtedTime(mediaPlayer.getDuration()/1000));

    }

    public void playPause(View view){
        if(!mediaPlayer.isPlaying()){
            //Toast.makeText(getApplicationContext(),"yes not"+mediaPlayer.getCurrentPosition(),Toast.LENGTH_SHORT).show();

           // Toast.makeText(getApplicationContext(),"not p:",Toast.LENGTH_SHORT).show();
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
            mediaPlayer.start();
            playpause.setImageResource(R.drawable.ic_baseline_pause);

        }else if (mediaPlayer.isPlaying()){
            //Toast.makeText(getApplicationContext(),"yes "+mediaPlayer.getCurrentPosition(),Toast.LENGTH_SHORT).show();
            stopedTime = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            playpause.setImageResource(R.drawable.ic_baseline_play);

        }

    }

    private void initViews(){
        songname =(TextView)findViewById(R.id.songname);
        artist =(TextView)findViewById(R.id.id_artist);
        duration_end =(TextView)findViewById(R.id.id_duration_end);
        getDuration_plyed =(TextView)findViewById(R.id.id_duration_start);
        seekBar =(SeekBar) findViewById(R.id.id_seekbar);
        albumArt =(ImageView) findViewById(R.id.imageView);
        playpause =(ImageView) findViewById(R.id.imageView2);


    }
}