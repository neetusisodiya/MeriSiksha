package com.muravtech.merishiksha.student_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.jaedongchicken.ytplayer.YoutubePlayerView;
import com.jaedongchicken.ytplayer.model.YTParams;
import com.muravtech.merishiksha.R;

public class VideoPlayer extends AppCompatActivity {
    ImageView img_back;
    String url;
    String[] separated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        url=getIntent().getStringExtra("url");
      if(url.contains("=")) {
          separated = url.split("=");
      }else {
          separated = url.split("https://youtu.be/");
      }
        Log.e("TAG", "url>>>>>>: "+separated[1]);

        YoutubePlayerView youtubePlayerView = (YoutubePlayerView) findViewById(R.id.youtubePlayerView);
         img_back =findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Control values
        // see more # https://developers.google.com/youtube/player_parameters?hl=en
        YTParams params = new YTParams();
        // params.setControls(0); // hide control
        // params.setVolume(100); // volume control
        // params.setPlaybackQuality(PlaybackQuality.small); // video quality control


        // initialize YoutubePlayerCallBackListener with Params and VideoID
        // youtubePlayerView.initialize("WCchr07kLPE", params, new YoutubePlayerView.YouTubeListener())

        // initialize YoutubePlayerCallBackListener with Params and Full Video URL
        // To Use - avoid UMG block!!!! but you'd better make own your server for your real service.
        // youtubePlayerView.initializeWithCustomURL("p1Zt47V3pPw" or "http://jaedong.net/youtube/p1Zt47V3pPw", params, new YoutubePlayerView.YouTubeListener())


        // make auto height of youtube. if you want to use 'wrap_content'
       // youtubePlayerView.setAutoPlayerHeight(this);
        // initialize YoutubePlayerCallBackListener and VideoID
        youtubePlayerView.initialize(separated[1], new YoutubePlayerView.YouTubeListener() {

            @Override
            public void onReady() {
                // when player is ready.
            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {
                /**
                 * YoutubePlayerView.STATE
                 *
                 * UNSTARTED, ENDED, PLAYING, PAUSED, BUFFERING, CUED, NONE
                 *
                 */
            }

            @Override
            public void onPlaybackQualityChange(String arg) {
            }

            @Override
            public void onPlaybackRateChange(String arg) {
            }

            @Override
            public void onError(String error) {
            }

            @Override
            public void onApiChange(String arg) {
            }

            @Override
            public void onCurrentSecond(double second) {
                // currentTime callback
            }

            @Override
            public void onDuration(double duration) {
                // total duration
            }

            @Override
            public void logs(String log) {
                // javascript debug log. you don't need to use it.
            }
        });


        // psuse video
        youtubePlayerView.pause();
        // play video when it's ready
        youtubePlayerView.play();

    }
}