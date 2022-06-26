package com.example.vmusic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.vmusic.MainActivity.musicFilesArrayList;

public class MusicFrag extends Fragment {
    RecyclerView recyclerView;
    MusicAdapter musicAdapter;

    public MusicFrag() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("com.vaibhav","arrry f size.."+musicFilesArrayList.size());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_music, container, false);

        //Log.d("com.vaibav","arrry cv size.."+musicFilesArrayList.size());

        recyclerView =(RecyclerView) view.findViewById(R.id.recyeview);

        if (musicFilesArrayList.size() >1){
            musicAdapter = new MusicAdapter(musicFilesArrayList,getContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(musicAdapter);
        }


        return view;
    }
}