package com.example.flappyw;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomMusicAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Music> arrayList;
    private static MediaPlayer mediaPlayer;
    private Boolean flag = true;


    public CustomMusicAdapter(Context context, int layout, ArrayList<Music> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtName, txtSinger;
        ImageView ivPlay, ivStop;
        Button choiceBtn;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(layout, null);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            // viewHolder.txtSinger =(TextView) convertView.findViewById(R.id.txtSinger);
            viewHolder.ivPlay =(ImageView) convertView.findViewById(R.id.ivPlay);
            // viewHolder.ivStop = (ImageView) convertView.findViewById(R.id.ivStop);
            viewHolder.choiceBtn = (Button) convertView.findViewById(R.id.choiceBtn);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Music music = arrayList.get(position);
        viewHolder.txtName.setText(music.getName());
        // viewHolder.txtSinger.setText(music.getSinger());

        //Play Music
        viewHolder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (flag) {
                        mediaPlayer = MediaPlayer.create(context, music.getSong());
                        flag = false;
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    System.out.println("ASFUASGDIG");
                }
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        viewHolder.ivPlay.setImageResource(R.drawable.ic_play);
                        flag = true;
                    } else {
                        mediaPlayer.start();
                        viewHolder.ivPlay.setImageResource(R.drawable.ic_stop);
                    }
            }
        });

        //Stop player
        /*viewHolder.ivStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    flag = true;
                }
                viewHolder.ivPlay.setImageResource(R.drawable.ic_play);

            }
        }); */

        viewHolder.choiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            viewHolder.ivPlay.setImageResource(R.drawable.ic_play);
                            flag = true;
                        }
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    System.out.println("choice Button MediaPlayer Stopf Fail");
                }

                if (flag){
                    try {

                        Constants.NoSong=0;
                        Constants.LiveSong = 0;
                        Constants.LiveSong = music.getSong();
                        System.out.println(Constants.LiveSong);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        System.out.println("Adapter Fail");
                    }

                }
               // viewHolder.choiceBtn.setEnabled(false);
            }
        });
        return convertView;
    }

    public static void stopstop(){
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying() == true) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            System.out.println("CustomMusicAdapter MediaPlayer Stop Fail");
        }
    }

}
