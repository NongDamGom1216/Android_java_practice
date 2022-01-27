package org.techtown.mymediaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

public class Videoview extends AppCompatActivity {

    VideoView videoview;
    private Uri file;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //선택한 비디오 파일에 대하여 비디오뷰에서 실행한다.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);
        Intent i = this.getIntent();
        file = Uri.parse(i.getExtras().get("vidFile").toString());
        videoview = (VideoView)findViewById(R.id.videoview);
        videoview.setVideoURI(file);
        videoview.start();
    }
}