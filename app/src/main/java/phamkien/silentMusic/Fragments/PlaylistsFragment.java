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

import phamkien.silentMusic.Adapters.PlaylistsAdapter;
import phamkien.silentMusic.Models.PlaylistModel;
import phamkien.silentMusic.R;
import phamkien.silentMusic.Services.APIService;
import phamkien.silentMusic.Services.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistsFragment extends Fragment {

    private View view;
    private ArrayList<PlaylistModel> playlists;
    private RecyclerView rvPlaylists;
    private TextView tvTitle;
    private PlaylistsAdapter playlistsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_playlists, container, false);
        rvPlaylists = view.findViewById(R.id.rvPlaylists);
        tvTitle = view.findViewById(R.id.tvTitle);

        getPlaylists();

        return view;
    }

    private void getPlaylists() {

        DataService dataService = APIService.getService();
        Call<List<PlaylistModel>> callback = dataService.getPlaylists();
        callback.enqueue(new Callback<List<PlaylistModel>>() {
            @Override
            public void onResponse(Call<List<PlaylistModel>> call, Response<List<PlaylistModel>> response) {

                playlists = (ArrayList<PlaylistModel>) response.body();
                playlistsAdapter = new PlaylistsAdapter(getContext(), playlists);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                rvPlaylists.setLayoutManager(layoutManager);
                rvPlaylists.setItemAnimator(new DefaultItemAnimator());
                rvPlaylists.setAdapter(playlistsAdapter);
                tvTitle.setText(getResources().getStringArray(R.array.mainContent)[0]);
            }

            @Override
            public void onFailure(Call<List<PlaylistModel>> call, Throwable t) {
                Log.d("playlists", t.getMessage());
            }
        });
    }
}
