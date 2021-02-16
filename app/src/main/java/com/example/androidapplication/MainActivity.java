package com.example.androidapplication;

import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.androidapplication.Music;
import com.example.listviewintermediate.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listViewMusic;
    ArrayList<Music> musicArrayList;
    MusicAdapter adapter;

    private int resumePosition;
    private MediaPlayer musicOn;
    private int listPosition;
    Map<String, Integer> musicName;
    private ImageButton Pause, Play;
    private SeekBar musicBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initialize();
        chooseMusicPlay();

        // Sau này sẽ làm phát đọc dữ liệu SD card/Internal Storage
        /* Đoạn này dùng để thay thế sau */
        int music = R.raw.victory_tsfh;
        int music1 = R.raw.wide_putin_song;
        int music2 = R.raw.wings;
        musicName.put("Victory", music);
        musicName.put("Wide Putin", music1);
        musicName.put("Wings", music2);
        /* hết đoạn phò phạch */
        final Handler mHandler = new Handler();
        //Update Seekbar on UI thread
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                changeTimeText(musicOn.getCurrentPosition());
                mHandler.postDelayed(this, 1000);
                musicBar.setProgress((int)(( (double) musicOn.getCurrentPosition() + 1000)/musicOn.getDuration()*100));
                changeTime(musicBar);
            }

        });
        musicOn.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer music) {
            //    Toast.makeText(MainActivity.this, "END", Toast.LENGTH_SHORT).show();
                changeButton(Pause, Play);
            }
        });

    }

    public void Initialize(){

        listViewMusic = findViewById(R.id.listViewMusic);
        musicArrayList = new ArrayList<>();
        musicName = new HashMap<>();

        adapter = new MusicAdapter(this, R.layout.name_artist, musicArrayList);
        listViewMusic.setAdapter(adapter);

        musicArrayList.add(new Music("Victory", "Two Steps From Hell", "0:00", R.drawable.victory_tsfh));
        musicArrayList.add(new Music("Wide Putin", "Unknown", "0:00", R.drawable.wide_putin_song));
        musicArrayList.add(new Music("Wings", "Two Steps From Hell", "0:00", R.drawable.wings));

        musicBar = findViewById(R.id.seekBar);
        Pause = findViewById(R.id.button_pause);
        Play = findViewById(R.id.button_play);

        musicOn = MediaPlayer.create(this, R.raw.victory_tsfh);
        listPosition = 0;
        musicOn.start();
        musicOn.pause();
    }

    public void chooseMusicPlay(){
        listViewMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = musicArrayList.get(position).getName();
                String artist = musicArrayList.get(position).getArtist();
                if(musicName.containsKey(name)) {
                    musicOn.stop();
                    musicOn = MediaPlayer.create(getApplicationContext(), musicName.get(name));
                    changeScreen(position);
                    listPosition = position;
                }
                musicOn.start();
                changeButton(Play, Pause);
            }
        });

    }
    public void changeButton(ImageButton first, ImageButton last){
        first.setVisibility(View.INVISIBLE);
        last.setVisibility(View.VISIBLE);
    }
    // Playing the music
    public void musicPlay(View view) {
        musicOn.start();
        changeButton(Play, Pause);
    }
    // Có vẻ đoạn dưới này oke

    public void changeScreen(int position){
        int idImage = musicArrayList.get(position).getImage();
        String name = musicArrayList.get(position).getName();
        String artist = musicArrayList.get(position).getArtist();
        ImageView ImageNow = findViewById(R.id.image_now_playing);
        TextView TitleNow = findViewById(R.id.title_now_playing);
        TextView ArtistNow = findViewById(R.id.Artist_now_playing);
        TitleNow.setText(name);
        ArtistNow.setText(artist);
        ImageNow.setImageResource(idImage);
    }

    private void changeTimeText(int resume) {
        String newTime;
        double minutes, seconds;
        minutes = (double)resume / 1000 / 60;
        seconds = (int)((double)resume / 1000) % 60;
        String Seconds = Integer.toString((int)seconds);
        if(seconds < 10){
            Seconds = '0' + Seconds;
        }
        newTime = (int) minutes + ":" + Seconds;
        TextView textTime = findViewById(R.id.textTime);
        textTime.setText(newTime);
    }

    public void changeTime(SeekBar seekBar){

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int duration = musicOn.getDuration();
                int newPosition = seekBar.getProgress();
                int newProgress = (int)((double)newPosition/100*duration);
                musicOn.seekTo(newProgress);
                resumePosition = newProgress;
                changeTimeText(newProgress);
            }
        });
    }

    public void musicPause(View view) {
        if(musicOn.isPlaying()) {
            musicOn.pause();
            changeButton(Pause, Play);
            resumePosition = musicOn.getCurrentPosition();
            changeTimeText(resumePosition);
        }
    }

    public void seekToNext(View view) {

    }
    public void seekToPrev(View view){

    }
}

