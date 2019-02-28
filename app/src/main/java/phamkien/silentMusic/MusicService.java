package phamkien.silentMusic;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private static final int NOTIFY_ID = 1;
    private final IBinder musicBinder = new MusicBinder();
    private MediaPlayer player;
    private ArrayList<Song> songList;
    private int curPos;
    private String songName;
    private String artist;

    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    public int getDuration() {
        return player.getDuration();
    }

    public boolean isPlaying() {
        return player.isPlaying();
    }

    public void pause() {
        player.pause();
    }

    public void seekTo(int pos) {
        player.seekTo(pos);
    }

    public void start() {
        player.start();
    }

    public void playPrev() {
        curPos--;
        if (curPos < 0) curPos = songList.size() - 1;
        playSong();
    }

    public void playNext() {
        curPos++;
        if (curPos == songList.size()) curPos = 0;
        playSong();
    }

    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }

    public void setSong(int curPos) {
        this.curPos = curPos;
    }

    public void playSong() {

        player.reset();

        Song song = songList.get(curPos);
        songName = song.getName();
        artist = song.getArtist();

        Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.getId());
        try {
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch (Exception e) {
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }

        player.prepareAsync();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        curPos = 0;

        player = new MediaPlayer();
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (player.getCurrentPosition() > 0) {
            mediaPlayer.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        mediaPlayer.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

        mediaPlayer.start();

        Intent notIntent = new Intent(this, PlayerFragment.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
            notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentIntent(pendInt)
            .setSmallIcon(R.drawable.ic_play_68dp)
            .setTicker(songName)
            .setOngoing(true)
            .setContentTitle("Playing")
            .setContentText(songName);

        Notification noti = builder.build();
        startForeground(NOTIFY_ID, noti);
    }

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
}
