package phamkien.silentMusic.Activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import phamkien.silentMusic.Adapters.PlayerPagerAdapter;
import phamkien.silentMusic.Fragments.DiscFragment;
import phamkien.silentMusic.Fragments.PlaylistInPlayerFragment;
import phamkien.silentMusic.Models.SongModel;
import phamkien.silentMusic.R;

public class PlayerActivity extends CustomActivity implements View.OnClickListener {

    public static ArrayList<SongModel> songList = new ArrayList<>();
    private static MediaPlayer mediaPlayer = null;
    private static PlayerPagerAdapter playerPagerAdapter;
    private int curPos = 0;
    private boolean repeat = false, random = false;
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
        // check network signal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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

        Log.d("test", "PlayerActivity oncreate: " + mediaPlayer);
        if (mediaPlayer != null) {

            mediaPlayer.stop();
//            mediaPlayer.release();
            songList.clear();
        }

        getDataTransferred();

        // bind disc fragment and playlist fragment to player viewpager
        discFragment = new DiscFragment();
        playlistInPlayerFragment = new PlaylistInPlayerFragment();

        playerPagerAdapter = new PlayerPagerAdapter(getSupportFragmentManager());
        playerPagerAdapter.addFragment(playlistInPlayerFragment);
        playerPagerAdapter.addFragment(discFragment);
        vpPlayer.setAdapter(playerPagerAdapter);
        vpPlayer.setCurrentItem(1);
        discFragment = (DiscFragment) playerPagerAdapter.getItem(1);

        playFirstSong();

        onClickListener();
    }

    private void onClickListener() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (playerPagerAdapter.getItem(1) != null && songList.size() > 0) {

                    discFragment.setDisc(songList.get(0).getImage(), songList.get(0).getName(), songList.get(0).getArtists());
                    handler.removeCallbacks(this);
                }
                else handler.postDelayed(this, 300);
            }
        }, 300);

        btnPlay.setOnClickListener(this);
        btnRepeat.setOnClickListener(this);
        btnShuffle.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void playFirstSong() {

        if (songList.size() > 0) new PlayerService().execute(songList.get(0).getLink());
    }

    protected void getDataTransferred() {

        songList.clear();

        Intent intent = getIntent();
        String singleSong = getResources().getString(R.string.song);
        String multiSong = getResources().getString(R.string.songs);

        if (intent.hasExtra(singleSong)) {

            SongModel song = intent.getParcelableExtra(singleSong);
            songList.add(song);
        }
        else if (intent.hasExtra(multiSong)) {

            songList = intent.getParcelableArrayListExtra(multiSong);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnPlay:
                playSong(curPos, false);
                break;

            case R.id.btnRepeat:
                changeBtnRepeat();
                playRepeat();
                break;

            case R.id.btnShuffle:
                changeBtnShuffle();
                playShuffle();
                break;

            case R.id.btnNext:
                playNext();
                break;

            case R.id.btnPrev:
                playPrev();
                break;
        }
    }

    private void playShuffle() {


    }

    private void playRepeat() {


    }

    private void changeBtnRepeat() {

        if (!repeat)
            btnRepeat.setImageResource(R.drawable.ic_repeat_one);

        repeat = !repeat;
    }

    private void changeBtnShuffle() {

        if (!random)
            btnShuffle.setImageResource(R.drawable.ic_shuffle);
        else
            btnShuffle.setImageResource(R.drawable.ic_no_shuffle);

        random = !random;
    }

    private void playNext() {

        if (songList.size() > 0) {

            if (mediaPlayer.isPlaying() || mediaPlayer != null) {

                mediaPlayer.stop();
                mediaPlayer.release();
            }

            btnPlay.setImageResource(R.drawable.ic_pause);

            curPos++;
            if (curPos == songList.size()) curPos = 0;
            playSong(curPos, true);
        }
    }

    private void playPrev() {

        if (songList.size() > 0) {

            if (mediaPlayer.isPlaying() || mediaPlayer != null) {

                mediaPlayer.stop();
                mediaPlayer.release();
            }

            btnPlay.setImageResource(R.drawable.ic_pause);

//            if (random) {
//
//                int i;
//                do {
//                    i = new Random().nextInt(songList.size());
//                } while (i != curPos);
//                curPos = i;
//            }
//            else {
//
//            }
            curPos--;
            if (curPos < 0) curPos = songList.size() - 1;
            playSong(curPos, true);
        }
    }

    private void playSong(int pos, boolean newSong) {

        if (newSong) {

            new PlayerService().execute(songList.get(pos).getLink());
            discFragment.setDisc(songList.get(pos).getImage(), songList.get(pos).getName(), songList.get(pos).getArtists());

            btnNext.setClickable(false);
            btnPrev.setClickable(false);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    btnNext.setClickable(true);
                    btnPrev.setClickable(true);
                }
            }, 3000);
        }
        else {
            if (mediaPlayer.isPlaying()) {

                mediaPlayer.pause();
                btnPlay.setImageResource(R.drawable.ic_play);
            }
            else {

                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.ic_pause);
            }
        }
    }

    class PlayerService extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String song) {

            super.onPostExecute(song);

            try {
                if (mediaPlayer == null) {

                    mediaPlayer = new MediaPlayer();
                    Log.d("test", "2: " + mediaPlayer);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {

                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        }
                    });
                }
                else {
                    mediaPlayer = null;

                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {

                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        }
                    });
                }

                mediaPlayer.setDataSource(song);
                mediaPlayer.prepare();

                mediaPlayer.start();

                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                tvDuration.setText(df.format(mediaPlayer.getDuration()));
                seekbar.setMax(mediaPlayer.getDuration());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }
    }
}
