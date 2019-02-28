package phamkien.silentMusic;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Hannibal Lecter on 02/19/19.
 */

public class PlaylistActivity extends AppCompatActivity {

    private SongAdapter songAdapter;
    private RecyclerView rvPlaylist;
    private ArrayList<Song> songs = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        loadView();
    }

    private void loadView() {

        findSongs();
        songAdapter = new SongAdapter(this, songs);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        rvPlaylist = (RecyclerView) findViewById(R.id.rvPlaylist);
        rvPlaylist.setLayoutManager(layoutManager);
        rvPlaylist.setAdapter(songAdapter);
    }

    private void findSongs() {

        String[] projection = {
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.TITLE_KEY,
            MediaStore.Audio.Media._ID
        };

        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Audio.Media.TITLE_KEY + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                long id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                int duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                Song song = new Song(id, title, artist);
                songs.add(song);
            }
        }
        cursor.close();
    }
}