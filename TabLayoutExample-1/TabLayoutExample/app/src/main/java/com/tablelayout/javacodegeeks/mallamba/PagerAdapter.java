package com.tablelayout.javacodegeeks.mallamba;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    FirstFragment tab1;
    SecondFragment tab2;
    ThirdFragment tab3;

    int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs ) {
        super(fm);
        this.numberOfTabs = NumOfTabs;
        tab1 = new FirstFragment();
        tab2 = new SecondFragment();
        tab3 = new ThirdFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return tab2;
            case 1:
                return tab1;
            case 2:
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
