package com.example.exoplayerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private StyledPlayerView styledPlayerView;
    private TextView audioSpeed;
    private ExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
        initializeExoPlayer();
        setSpeedListener();
    }

    public void initializeExoPlayer(){
        Uri soundUri = Uri.fromFile(new File("//android_asset/happy-life.mp3"));

        exoPlayer = new ExoPlayer.Builder(this).build();
        styledPlayerView.setPlayer(exoPlayer);

        MediaItem mediaItem = MediaItem.fromUri(soundUri);

        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);
    }

    private void setSpeedListener() {
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                Player.Listener.super.onPlaybackParametersChanged(playbackParameters);
                float speed = playbackParameters.speed;
                String s = String.valueOf(speed);
                audioSpeed.setText(s + " x");
            }
        });

        audioSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAudioSpeed();
            }
        });
    }

    private void initLayout() {
        styledPlayerView = findViewById(R.id.player_view);
        audioSpeed = findViewById(R.id.audio_speed);
    }

    private void setAudioSpeed(){
        float speed = exoPlayer.getPlaybackParameters().speed;

        if (speed == 1.0f){
            speed = 1.25f;
            audioSpeed.setText("1.25 x");
        } else if (speed == 1.25f){
            speed = 1.5f;
            audioSpeed.setText("1.5 x");
        } else if (speed == 1.5f){
            speed = 2.0f;
            audioSpeed.setText("2.0 x");
        } else if (speed == 2.0f){
            speed = 1.0f;
            audioSpeed.setText("1.0 x");
        }

        exoPlayer.setPlaybackParameters(new PlaybackParameters(speed));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (exoPlayer == null){
            initializeExoPlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}