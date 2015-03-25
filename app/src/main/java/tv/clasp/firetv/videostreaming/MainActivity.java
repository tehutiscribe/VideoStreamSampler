package tv.clasp.firetv.videostreaming;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends ActionBarActivity {

    static TextView tv;
    VideoView videoView;
    MediaController mc;
    final String LOG_TAG = this.getClass().getName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = getIntent().getStringExtra("link");
        final Uri adUri = Uri.parse(url);

        videoView = (VideoView) findViewById(R.id.ad_videoView);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                videoView.setVideoURI(adUri);
            }
        });
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                String msg = "UNHANDLED";

                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                        msg = "MEDIA_INFO_BAD_INTERLEAVING";
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        msg = "MEDIA_INFO_BUFFERING_START";
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        msg = "MEDIA_INFO_BUFFERING_END";
                        break;
                    case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                        msg = "MEDIA_INFO_NOT_SEEKABLE";
                        break;
                    case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                        msg = "MEDIA_INFO_VIDEO_TRACK_LAGGING";
                        break;
                    case MediaPlayer.MEDIA_INFO_UNKNOWN:
                        msg = "MEDIA_INFO_UNKNOWN";
                        break;
                    case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                        msg = "MEDIA_INFO_VIDEO_RENDERING_START";
                        break;
                    case MediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:
                        msg = "MEDIA_INFO_SUBTITLE_TIMED_OUT";
                        break;
                }
                Log.i(LOG_TAG, "onInfo() --- " + msg);
                return true;
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.i(LOG_TAG, "[VideoView OnPrepared Called]");

                videoView.start();

                Log.i(LOG_TAG, "[VideoView start() Called]");

                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        Log.i(LOG_TAG, "onBufferingUpdate() --- " + percent);
                    }
                });
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });
        videoView.setMediaController(new MediaController(this, true));

        mc = new MediaController(this, true);
        mc.setMediaPlayer(player);
        mc.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
                int i = videoView.getCurrentPosition() + 30000;
                videoView.seekTo(i);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
                int i = videoView.getCurrentPosition() - 30000;
                videoView.seekTo(i);
            }
        });
    }


    @Override
    protected void onPause() {
        tearDown();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        tearDown();
        super.onDestroy();
    }

    public void tearDown() {
        if (videoView != null) {
            videoView.stopPlayback();
            mc = null;
            videoView = null;
        }
    }

    MediaController.MediaPlayerControl player = new MediaController.MediaPlayerControl() {
        @Override
        public void start() {
            videoView.start();
        }

        @Override
        public void pause() {
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
            Toast.makeText(getApplicationContext(), "Seek To Position: " + pos, Toast.LENGTH_LONG).show();
        }

        @Override
        public boolean isPlaying() {
            return videoView.isPlaying();
        }

        @Override
        public int getBufferPercentage() {
            return 0;
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
            return videoView.getBufferPercentage();
        }
    };

}
