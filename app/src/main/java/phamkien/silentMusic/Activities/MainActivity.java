package phamkien.silentMusic.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import phamkien.silentMusic.Adapters.MainViewPagerAdapter;
import phamkien.silentMusic.Fragments.HomeFragment;
import phamkien.silentMusic.Fragments.SearchFragment;
import phamkien.silentMusic.R;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        menu = getResources().getStringArray(R.array.menu);

        // bind fragments to main view
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new HomeFragment(), menu[0]);
        mainViewPagerAdapter.addFragment(new SearchFragment(), menu[1]);

        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search);
    }
}
