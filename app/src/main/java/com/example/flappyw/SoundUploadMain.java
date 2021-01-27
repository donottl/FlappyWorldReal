package com.example.flappyw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SoundUploadMain extends AppCompatActivity{
    private ArrayList<Music> arrayList;
    private CustomMusicAdapter adapter;
    private ListView songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_upload_main);

        Button clickButton6 = (Button) findViewById(R.id.BackButtonCreate);
        if (clickButton6 != null) {
            clickButton6.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    BackPressed();
                }
            });
        }


        songList = (ListView) findViewById(R.id.songList);
        arrayList = new ArrayList<>();

        try {
            //Songs einfügen mit name singer und song
            arrayList.add(new Music("Sound 1", "1", R.raw.sound1));
            arrayList.add(new Music("Sound 2", "2", R.raw.sound4));

            adapter = new CustomMusicAdapter(this, R.layout.custom_music_item, arrayList);
            songList.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("FEEEEEEEEEEEEEEEE");
        }
    }

    public void BackPressed() {
        CustomMusicAdapter.stopstop();
        Intent backmain2 = new Intent(SoundUploadMain.this,MainActivity.class);
        startActivity(backmain2);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);
    }

    public void onBackPressed() {
        CustomMusicAdapter.stopstop();
        super.onBackPressed();
        Intent backmain2 = new Intent(SoundUploadMain.this,MainActivity.class);
        startActivity(backmain2);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);
    }

}

