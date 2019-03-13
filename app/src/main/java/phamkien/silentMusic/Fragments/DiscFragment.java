package phamkien.silentMusic.Fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import phamkien.silentMusic.R;

public class DiscFragment extends Fragment {

    private ObjectAnimator objectAnimator;
    private View view;
    private TextView tvSongName, tvSongArtists;
    private CircleImageView civDisc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_disc, container, false);
        civDisc = view.findViewById(R.id.civDisc);
        tvSongName = view.findViewById(R.id.tvSongName);
        tvSongArtists = view.findViewById(R.id.tvSongArtists);

        objectAnimator = ObjectAnimator.ofFloat(civDisc, "rotation", 0f, 360f);
        objectAnimator.setDuration(30000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());

        return view;
    }

    public void setDisc(String image, String name, String artists) {

        Picasso.get().load(image).into(civDisc);
        tvSongName.setText(name);
        tvSongArtists.setText(artists);

        tvSongName.setSelected(true);
        tvSongArtists.setSelected(true);
        objectAnimator.start();
    }
}
