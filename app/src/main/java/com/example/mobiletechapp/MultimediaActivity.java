package com.example.mobiletechapp;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MultimediaActivity extends AppCompatActivity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Uri fileUri;
    int position = 0;
    MediaPlayer mediaPlayer;
    VideoView videoView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multimedia);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        videoView = findViewById(R.id.videoView);
    }

    public void audioOn(View view) {
        try {
            Uri audioUri = Uri.parse("android.resource://"
                    + getPackageName() + "/"
                    + R.raw.fur_elise);

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(getApplicationContext(), audioUri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            Log.e("Error", e.getMessage() != null ? e.getMessage() : "Audio error");
            e.printStackTrace();
        }
    }

    public void audioPause(View view) {
        if (mediaPlayer == null) {
            return;
        }

        if (mediaPlayer.isPlaying()) {
            position = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }

    public void audioResume(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
            mediaPlayer.start();
        }
    }

    public void audioOff(View view) {
        if (mediaPlayer == null) {
            return;
        }

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        mediaPlayer.release();
        mediaPlayer = null;
        position = 0;
    }

    public void videoOn(View view) {
        videoView = findViewById(R.id.videoView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Buffering...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        try {
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            videoView.setVideoPath(
                    "android.resource://" + getPackageName() + "/" + R.raw.mov_bbb
            );
        } catch (Exception e) {
            Log.e("Error", e.getMessage() != null ? e.getMessage() : "Video error");
            e.printStackTrace();
        }

        videoView.requestFocus();
        videoView.setOnPreparedListener(mp -> {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            videoView.start();
        });
    }

    public void videoOff(View view) {
        if (videoView != null && videoView.isPlaying()) {
            videoView.stopPlayback();
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        progressDialog = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (videoView != null) {
            videoView.stopPlayback();
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}