package phamkien.silentMusic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import phamkien.silentMusic.Adapters.PlaylistsAdapter;
import phamkien.silentMusic.Models.PlaylistModel;
import phamkien.silentMusic.R;

public class SongListActivity extends CustomActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        setToolbar();

        getDataTransferred();

    }

    protected void getDataTransferred() {

        Intent intent = getIntent();

        if (intent.hasExtra(PlaylistsAdapter.sPlaylist)) {
            PlaylistModel playlist = (PlaylistModel) intent.getSerializableExtra(PlaylistsAdapter.sPlaylist);
            Log.d("test", "SongListActivity: " + playlist.getName());
        }
    }
}
