package tv.clasp.firetv.videostreaming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.*;


public class ListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final List<Video> videos = Arrays.asList(
                new Video("Dangerous Assignment", "https://archive.org/download/DangerousAssignment1952/DangerousAssignmentEpisode34TheStolenLetter1952.mp4"),
                new Video("Big Buck Bunny 320x180 MP4", "http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"),
                new Video("Popcornflix Movie 1", "http://c.brightcove.com/services/mobile/streaming/index/master.m3u8?videoId=3869060243001&pubId=3692957913001"),
                new Video("Popcornflix Movie 2", "http://c.brightcove.com/services/mobile/streaming/index/master.m3u8?videoId=3974080447001"),
                new Video("Popcornflix Movie 3", "http://c.brightcove.com/services/mobile/streaming/index/master.m3u8?videoId=3836124309001&pubId=3692957913001")
        );

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<Video>(this, android.R.layout.simple_list_item_1, videos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setText(videos.get(position).name);
                return tv;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ListActivity.this, BrightCoveVideoActivity.class);
                i.putExtra("link", videos.get(position).url);
                startActivity(i);
            }
        });
    }

    class Video {
        public String name, url;

        public Video(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }
}
