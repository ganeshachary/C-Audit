package com.spottechnicians.caudit;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.spottechnicians.caudit.adapters.ViewPagerAdapter;

public class Daily_Audit extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily__audit);
        //toolbar=(Toolbar)findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CT_Fragment(), "Caretaker");
        adapter.addFragment(new HK_Fragment(), "Housekeeping");
        adapter.addFragment(new SRM_Fragment(), "SRM");
        adapter.addFragment(new CT_Fragment(), "CT/HK");
        adapter.addFragment(new HK_Fragment(), "ALL");
        viewPager.setAdapter(adapter);

    }
}
