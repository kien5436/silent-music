package phamkien.silentMusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerFragment extends Fragment implements MediaController.MediaPlayerControl {

    public static final String SONG_NAME = "song name";
    public static final String ARTIST = "artist";
    private static ArrayList<Song> songList;
    private static int curPos;
    private MusicService musicService;
    private Intent playIntent;
    private boolean musicBound = false;
    private ServiceConnection musicCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            musicService = binder.getService();
            musicService.setSongList(PlayerFragment.songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicBound = false;
        }
    };
    private MusicController musicController;
    private boolean paused = false, playbackPaused = false;

    public static PlayerFragment newInstance(ArrayList<Song> songList, int pos) {

        PlayerFragment.songList = songList;
        PlayerFragment.curPos = pos;

        PlayerFragment fragment = new PlayerFragment();
        Song selectedSong = songList.get(pos);

        Bundle args = new Bundle();
        args.putString(SONG_NAME, selectedSong.getName());
        args.putString(ARTIST, selectedSong.getArtist());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStop() {
        musicController.hide();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (paused) {
            setMusicController();
            paused = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        paused = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMusicController();
        songPicked();
    }

    public void setMusicController() {
        musicController = new MusicController(getContext());
        musicController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // play next
                musicService.playNext();
                checkPlayerBehavior();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // play prev
                musicService.playPrev();
                checkPlayerBehavior();
            }
        });
        musicController.setMediaPlayer(this);
        musicController.setEnabled(true);
    }

    private void checkPlayerBehavior() {
        if (playbackPaused) {
            setMusicController();
            playbackPaused = false;
        }
        musicController.show();
    }

    public void songPicked() {

        musicService.setSong(PlayerFragment.curPos);
        musicService.playSong();
        checkPlayerBehavior();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(getContext(), MusicService.class);
            getActivity().bindService(playIntent, musicCon, Context.BIND_AUTO_CREATE);
            getActivity().startService(playIntent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        TextView tvSongName = (TextView) view.findViewById(R.id.tvSongName);
        tvSongName.setText(getArguments().getString(SONG_NAME));
        tvSongName.setSelected(true);
        TextView tvArtist = (TextView) view.findViewById(R.id.tvArtist);
        tvArtist.setText(getArguments().getString(ARTIST));
        tvArtist.setSelected(true);

        return view;
    }

    @Override
    public void start() {
        musicService.start();
    }

    @Override
    public void pause() {
        playbackPaused = true;
        musicService.pause();
    }

    @Override
    public int getDuration() {
        if (musicService != null && musicBound && musicService.isPlaying())
            return musicService.getDuration();
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (musicService != null && musicBound && musicService.isPlaying())
            return musicService.getCurrentPosition();
        return 0;
    }

    @Override
    public void seekTo(int i) {
        musicService.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        if (musicService != null && musicBound)
            return musicService.isPlaying();
        return false;
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
        return 0;
    }
}
