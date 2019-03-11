package phamkien.silentMusic.Fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import de.hdodenhof.circleimageview.CircleImageView;
import phamkien.silentMusic.R;

public class DiscFragment extends Fragment {

    ObjectAnimator objectAnimator;
    private View view;
    private CircleImageView civDisc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_disc, container, false);
        civDisc = view.findViewById(R.id.civDisc);

        objectAnimator = ObjectAnimator.ofFloat(civDisc, "rotation", 0f, 360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());

        return view;
    }
}
