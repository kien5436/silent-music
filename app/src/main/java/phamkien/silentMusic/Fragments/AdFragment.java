package phamkien.silentMusic.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import phamkien.silentMusic.Adapters.AdAdapter;
import phamkien.silentMusic.Models.AdModel;
import phamkien.silentMusic.R;
import phamkien.silentMusic.Services.APIService;
import phamkien.silentMusic.Services.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdFragment extends Fragment {

    private View view;
    private ViewPager vpBanner;
    private CircleIndicator ciBanner;
    private int curItem;
    private AdAdapter adAdapter;
    private Handler handler;
    private Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_ad, container, false);
        vpBanner = view.findViewById(R.id.vpBanner);
        ciBanner = view.findViewById(R.id.ciBanner);

        getAds();

        return view;
    }

    private void getAds() {

        DataService dataService = APIService.getService();

        Call<List<AdModel>> callback = dataService.getAds();
        callback.enqueue(new Callback<List<AdModel>>() {

            @Override
            public void onResponse(retrofit2.Call<List<AdModel>> call, Response<List<AdModel>> response) {

                ArrayList<AdModel> banners = (ArrayList<AdModel>) response.body();

                adAdapter = new AdAdapter(getActivity(), banners);
                vpBanner.setAdapter(adAdapter);
                ciBanner.setViewPager(vpBanner);

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        curItem = vpBanner.getCurrentItem();
                        curItem = (curItem == vpBanner.getAdapter().getCount() - 1) ? 0 : curItem + 1;

                        vpBanner.setCurrentItem(curItem, true);
                        handler.postDelayed(runnable, 4500);
                    }
                };
                handler.postDelayed(runnable, 4500);
            }

            @Override
            public void onFailure(retrofit2.Call<List<AdModel>> call, Throwable t) {
                Log.d("AdFragment", "error: " + t.getMessage());
            }
        });
    }
}
