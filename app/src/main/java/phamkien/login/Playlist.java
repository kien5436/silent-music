package phamkien.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Hannibal Lecter on 02/19/19.
 */

public class Playlist extends Activity {

    private ListView lvPlaylist;
    private ArrayList<Song> playlist = new ArrayList<>();
    private ArrayAdapter<Song> songAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        loadView();
    }

    private void loadView() {
        lvPlaylist = (ListView) findViewById(R.id.lvPlaylist);
        createPlaylist();

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), playlist);
        lvPlaylist.setAdapter(customAdapter);
        lvPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                getSong(position);
            }
        });
    }

    private void createPlaylist() {
        for (int i = 0; i < 20; i++) {
            Song song = new Song("Song " + (i+1), "Anonymous");
            playlist.add(song);
        }
    }

    private void getSong(int position){
        Song song = playlist.get(position);
        Intent intent = new Intent(this, PlaySong.class);
        intent.putExtra("SONG", song.getName());
        intent.putExtra("SINGER", song.getSinger());
        startActivity(intent);
    }
}