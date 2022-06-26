package com.example.vmusic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    TabLayout tabLayout;
    public static ArrayList<MusicFiles> musicFilesArrayList;

   // ArrayList<Fragment> fragmentArrayList =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        permission();




//        fragmentArrayList = new ArrayList<Fragment>();
//        fragmentArrayList.add(new MusicFrag());
//        fragmentArrayList.add(new AlbumFrag());


        //MusicFiles mf =(MusicFiles) musicFilesArrayList.get(1);
        //Toast.makeText(getApplicationContext(),"v"+mf.getTitles(),Toast.LENGTH_SHORT).show();
        //Log.d("com.vaibh","not null mainList."+musicFilesArrayList.get(2).getTitles());
        //Log.d("com.vaibhav","vai.."+musicFilesArrayList.size());


    }

    private void initView(){
        viewPager =(ViewPager2)findViewById(R.id.viewpager);
        tabLayout =(TabLayout) findViewById(R.id.tablayout);

        viewPager.setAdapter(new MyViewPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                if( position == 0){
                    tab.setText("Music");
                }
                else if (position ==1){
                    tab.setText("Album");
                }

            }
        }).attach();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode ==1){

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"permisstion granted...",Toast.LENGTH_SHORT).show();
                musicFilesArrayList = getSongs(getApplicationContext());
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }

        }
    }

    private void permission(){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            Toast.makeText(this,"permisstion granted",Toast.LENGTH_SHORT).show();
            musicFilesArrayList = getSongs(getApplicationContext());

        }
    }

    public  ArrayList<MusicFiles> getSongs(Context context){
        ArrayList<MusicFiles> musicTemp = new ArrayList<>();

        Uri uri =MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection ={
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media._ID
        };

        Cursor cursor =context.getContentResolver().query(uri,projection,null,null,null);

        if (cursor != null){
            Log.d("com.vaibhav","not null cursor..");

            while(cursor.moveToNext()){

                String path = cursor.getString(4);
                String album = cursor.getString(0);
                String artist = cursor.getString(1);
                String duration = cursor.getString(2);
                String title = cursor.getString(3);
                long id = cursor.getLong(5);

//                Log.d("com.vaibhav","not null title.."+title);
//                Log.d("com.vaibhav","not null artist.."+artist);
//                //Log.d("com.vaibhav","not null path.."+path);
//                Log.d("com.vaibhav","not null album.."+album);


                MusicFiles musicFiles = new MusicFiles(path,title,artist,album,duration,id);
                musicTemp.add(musicFiles);
                //Log.d("com.vaibhav","not null album.."+musicFiles.getTitles());

            }
            cursor.close();
        }
        //Log.d("com.vaibhav","not null teplist."+musicTemp.get(2).getTitles());
        return musicTemp;
    }
}

class MyViewPagerAdapter extends FragmentStateAdapter{

    //ArrayList<Fragment> fragmentArrayList;

    public MyViewPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        //this.fragmentArrayList =fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment =null;
        if( position == 0){
            fragment =new MusicFrag();
        }
        else if (position == 1){
            fragment = new AlbumFrag();
        }


        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}