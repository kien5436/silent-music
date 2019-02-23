package phamkien.login;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Hannibal Lecter on 02/20/19.
 */

public class PlaySong extends Activity implements View.OnClickListener {

    private Button btnPrev;
    private Button btnNext;
    private Button btnStatus;
    private TextView tvSong;
    private TextView tvSinger;
    private MediaPlayer player = new MediaPlayer();
    private String status = "playing";

    @Override
    protected void onCreate(Bundle savedInstanceStatus){
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.activity_player);
        loadView();
    }

    private void loadView(){
        btnStatus = (Button) findViewById(R.id.btnStatus);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        tvSong = (TextView) findViewById(R.id.tvSong);
        tvSinger = (TextView) findViewById(R.id.tvSinger);

//        String song = getIntent().getStringExtra("SONG");
//        String singer = getIntent().getStringExtra("SINGER");
        tvSong.setText(getIntent().getStringExtra("SONG"));
        tvSinger.setText(getIntent().getStringExtra("SINGER"));

        btnPrev.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnStatus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStatus:
                if (player.isPlaying()) {
                    player.pause();
                } else {
                    player.start();
                }
                break;
        }
    }
}
