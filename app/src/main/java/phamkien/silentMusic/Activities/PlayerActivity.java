package phamkien.silentMusic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import phamkien.silentMusic.Adapters.AdAdapter;
import phamkien.silentMusic.Adapters.HotSongsAdapter;
import phamkien.silentMusic.Adapters.PlayerPagerAdapter;
import phamkien.silentMusic.Fragments.DiscFragment;
import phamkien.silentMusic.Fragments.PlaylistInPlayerFragment;
import phamkien.silentMusic.Models.SongModel;
import phamkien.silentMusic.R;

public class PlayerActivity extends CustomActivity {

    public static ArrayList<SongModel> songList = new ArrayList<>();
    private static PlayerPagerAdapter playerPagerAdapter;
    private TextView tvCurDuration, tvDuration;
    private ViewPager vpPlayer;
    private ImageButton btnShuffle, btnPlay, btnPrev, btnNext, btnRepeat;
    private SeekBar seekbar;
    private DiscFragment discFragment;
    private PlaylistInPlayerFragment playlistInPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        tvCurDuration = findViewById(R.id.tvCurDuration);
        tvDuration = findViewById(R.id.tvDuration);
        vpPlayer = findViewById(R.id.vpPlayer);
        seekbar = findViewById(R.id.seekbar);
        btnShuffle = findViewById(R.id.btnShuffle);
        btnPlay = findViewById(R.id.btnPlay);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnRepeat = findViewById(R.id.btnRepeat);
        setToolbar();

        getDataTransferred();

        // bind disc fragment and playlist fragment to player viewpager
        discFragment = new DiscFragment();
        playlistInPlayerFragment = new PlaylistInPlayerFragment();

        playerPagerAdapter = new PlayerPagerAdapter(getSupportFragmentManager());
        playerPagerAdapter.addFragment(playlistInPlayerFragment);
        playerPagerAdapter.addFragment(discFragment);
        vpPlayer.setAdapter(playerPagerAdapter);
    }

    protected void getDataTransferred() {

        songList.clear();

        Intent intent = getIntent();
        if (intent.hasExtra(AdAdapter.adSong)) {

            SongModel song = intent.getParcelableExtra(AdAdapter.adSong);
            songList.add(song);
        }
        else if (intent.hasExtra(HotSongsAdapter.hotSong)) {

            SongModel song = intent.getParcelableExtra(HotSongsAdapter.hotSong);
            songList.add(song);
        }
    }
}
