
package com.jelly.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {

    ViewPager mViewPager;
    PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

    }

    class PagerAdapter extends FragmentPagerAdapter {
        WeatherDetailFragment mGpsFragment;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mGpsFragment = new WeatherDetailFragment();
        }

        @Override
        public Fragment getItem(int index) {
            return mGpsFragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
