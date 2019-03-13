package phamkien.silentMusic.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import phamkien.silentMusic.Adapters.SearchAdapter;
import phamkien.silentMusic.Models.SongModel;
import phamkien.silentMusic.R;
import phamkien.silentMusic.Services.APIService;
import phamkien.silentMusic.Services.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static phamkien.silentMusic.Activities.PlayerActivity.songList;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    private View view;
    private RecyclerView rvSearchRes;
    private TextView tvEmpty;
    private SearchView svSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search, container, false);
        svSearch = view.findViewById(R.id.svSearch);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        rvSearchRes = view.findViewById(R.id.rvSearchRes);

        svSearch.setOnQueryTextListener(this);

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        DataService dataService = APIService.getService();
        Call<List<SongModel>> callback = dataService.search(s);
        callback.enqueue(new Callback<List<SongModel>>() {
            @Override
            public void onResponse(Call<List<SongModel>> call, Response<List<SongModel>> response) {

                ArrayList<SongModel> songList = (ArrayList<SongModel>) response.body();
                if (songList.size() > 0) {

                    SearchAdapter searchAdapter = new SearchAdapter(getContext(), songList);
                    rvSearchRes.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvSearchRes.setAdapter(searchAdapter);
                    rvSearchRes.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.GONE);
                }
                else {

                    rvSearchRes.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<SongModel>> call, Throwable t) {
                Log.e("err", "SearchFragment error: " + t.getMessage());
            }
        });

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
