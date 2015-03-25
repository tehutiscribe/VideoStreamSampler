package tv.clasp.firetv.videostreaming;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import com.brightcove.player.analytics.Analytics;
import com.brightcove.player.media.DeliveryType;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.SeamlessVideoView;

public class BrightCoveVideoActivity extends ActionBarActivity {

    public static final String LOG_TAG = BrightCoveVideoActivity.class.getName();
    SeamlessVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_on_video_player);

        final String videoUrl = getIntent().getStringExtra("link");
        videoView = (SeamlessVideoView) findViewById(R.id.bc_videoView);

        Analytics analytics = videoView.getAnalytics();
        analytics.setAccount("3693957913001");

        MediaController mc = new MediaController(this, true);
        mc.setMediaPlayer(controller);
        mc.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Previous Click");
                videoView.pause();
                int i = videoView.getCurrentPosition() + 30000;
                videoView.seekTo(i);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Forward Click");
                videoView.pause();
                int i = videoView.getCurrentPosition() - 30000;
                videoView.seekTo(i);
            }
        });

//        videoView.setMediaController(mc);
        videoView.add(Video.createVideo(videoUrl, DeliveryType.HLS));
        videoView.start();
    }

    MediaController.MediaPlayerControl controller = new MediaController.MediaPlayerControl() {
        @Override
        public void start() {
            Log.d(LOG_TAG, "start()");
            videoView.start();
        }

        @Override
        public void pause() {
            Log.d(LOG_TAG, "pause()");
            videoView.pause();
        }

        @Override
        public int getDuration() {
            return videoView.getDuration();
        }

        @Override
        public int getCurrentPosition() {
            return videoView.getCurrentPosition();
        }

        @Override
        public void seekTo(int pos) {
            Log.d(LOG_TAG, "seekTo(" + pos + ")");
            videoView.seekTo(pos);
        }

        @Override
        public boolean isPlaying() {
            Log.d(LOG_TAG, "isPlaying()");
            return true;
        }

        public int getBufferPercentage() {
            return videoView.getBufferPercentage();
        }

        @Override
        public boolean canPause() {
            return true;
        }

        @Override
        public boolean canSeekBackward() {
            return true;
        }

        @Override
        public boolean canSeekForward() {
            return true;
        }

        @Override
        public int getAudioSessionId() {
            return 0;
        }
    };
}
