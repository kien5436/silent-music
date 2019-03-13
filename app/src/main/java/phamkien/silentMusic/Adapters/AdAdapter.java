package phamkien.silentMusic.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import phamkien.silentMusic.Activities.PlayerActivity;
import phamkien.silentMusic.Models.AdModel;
import phamkien.silentMusic.Models.SongModel;
import phamkien.silentMusic.R;
import phamkien.silentMusic.Services.APIService;
import phamkien.silentMusic.Services.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<AdModel> adList;

    public AdAdapter(Context context, ArrayList<AdModel> adList) {
        this.context = context;
        this.adList = adList;
    }

    /**
     * create pagers corresponding to AdModel objects and bind them to the fragment_ad
     *
     * @param container
     * @param position
     * @return
     */

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_ad, null);

        ImageView ivBackround = view.findViewById(R.id.ivBackground);
        ImageView ivSongImage = view.findViewById(R.id.ivSongImage);
        TextView tvSongName = view.findViewById(R.id.tvSongName);
        TextView tvContent = view.findViewById(R.id.tvContent);
        final AdModel adSong = adList.get(position);

        Picasso.get().load(adSong.getAdImage()).into(ivBackround);
        int imgSize = context.getResources().getInteger(R.integer.smallImg);
        Picasso.get().load(adSong.getSongImage()).resize(imgSize, imgSize).centerCrop().into(ivSongImage);
        tvSongName.setText(adSong.getSongName());
        tvContent.setText(adSong.getContent());
        container.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataService dataService = APIService.getService();
                Call<SongModel> callback = dataService.getSongById(adSong.getSongId());
                callback.enqueue(new Callback<SongModel>() {
                    @Override
                    public void onResponse(Call<SongModel> call, Response<SongModel> response) {

                        SongModel song = response.body();

                        if (song != null) {

                            context.startActivity(
                                new Intent(context, PlayerActivity.class)
                                    .putExtra(context.getResources().getString(R.string.song), song)
                            );

                            AppCompatActivity activity = (AppCompatActivity) context;
                            activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        }
                    }

                    @Override
                    public void onFailure(Call<SongModel> call, Throwable t) {
                        Log.e("err", "AdAdapter error: " + t.getMessage());
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return adList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
