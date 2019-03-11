package phamkien.silentMusic.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import phamkien.silentMusic.Adapters.HotSongsAdapter;
import phamkien.silentMusic.Models.SongModel;
import phamkien.silentMusic.R;
import phamkien.silentMusic.Services.APIService;
import phamkien.silentMusic.Services.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotSongsFragment extends Fragment {

    private ArrayList<SongModel> hotSongs;
    private View view;
    private RecyclerView rvHotSongs;
    private TextView tvTitle;
    private HotSongsAdapter hotSongsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_hot_songs, container, false);
        rvHotSongs = view.findViewById(R.id.rvHotSongs);
        tvTitle = view.findViewById(R.id.tvTitle);

        getHotSongs();

        return view;
    }

    private void getHotSongs() {

        DataService dataService = APIService.getService();
        Call<List<SongModel>> callback = dataService.getHotSongs();
        callback.enqueue(new Callback<List<SongModel>>() {
            @Override
            public void onResponse(Call<List<SongModel>> call, Response<List<SongModel>> response) {

                hotSongs = (ArrayList<SongModel>) response.body();
                hotSongsAdapter = new HotSongsAdapter(getContext(), hotSongs);
                rvHotSongs.setLayoutManager(new LinearLayoutManager(getContext()));
                rvHotSongs.setItemAnimator(new DefaultItemAnimator());
                rvHotSongs.setAdapter(hotSongsAdapter);
                tvTitle.setText(getResources().getStringArray(R.array.mainContent)[1]);
            }

            @Override
            public void onFailure(Call<List<SongModel>> call, Throwable t) {
                Log.d("hot songs", t.getMessage());
            }
        });
    }
}
