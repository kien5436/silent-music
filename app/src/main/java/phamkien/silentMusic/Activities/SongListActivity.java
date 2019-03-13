package phamkien.silentMusic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import phamkien.silentMusic.Adapters.PlaylisAdapter;
import phamkien.silentMusic.Adapters.PlaylistsAdapter;
import phamkien.silentMusic.Models.PlaylistModel;
import phamkien.silentMusic.Models.SongModel;
import phamkien.silentMusic.R;
import phamkien.silentMusic.Services.APIService;
import phamkien.silentMusic.Services.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongListActivity extends CustomActivity {

    private RecyclerView rvSongList;
    private Button btnPlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        // check network signal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnPlay = findViewById(R.id.btnPlay);
        rvSongList = findViewById(R.id.rvSongList);
        setToolbar();

        getDataTransferred();

    }

    protected void getDataTransferred() {

        Intent intent = getIntent();

//        PlaylistModel playlist = (PlaylistModel) intent.getSerializableExtra(PlaylistsAdapter.sPlaylist);
//        Log.d("test", "SongListActivity: " + playlist.getName());
        if (intent.hasExtra(PlaylistsAdapter.sPlaylist)) {

            PlaylistModel playlist = (PlaylistModel) intent.getSerializableExtra(PlaylistsAdapter.sPlaylist);
            DataService dataService = APIService.getService();
            Call<List<SongModel>> callback = dataService.getSongsInPlaylist(playlist.getPid());
            callback.enqueue(new Callback<List<SongModel>>() {
                @Override
                public void onResponse(Call<List<SongModel>> call, Response<List<SongModel>> response) {

                    final ArrayList<SongModel> songList = (ArrayList<SongModel>) response.body();
                    PlaylisAdapter playlistAdapter = new PlaylisAdapter(getApplicationContext(), songList);
                    rvSongList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rvSongList.setAdapter(playlistAdapter);

                    btnPlay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            startActivity(
                                new Intent(getApplicationContext(), PlayerActivity.class)
                                    .putExtra(getResources().getString(R.string.songs), songList)
                            );

                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<SongModel>> call, Throwable t) {
                    Log.e("err", "SongListActi error: " + t.getMessage());
                }
            });
        }
    }
}
