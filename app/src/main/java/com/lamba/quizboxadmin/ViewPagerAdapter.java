package com.lamba.quizboxadmin;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    public final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentListTitels = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitels.get(position);
    }

    public void AddFragment(Fragment fragment , String title){
        fragmentList.add(fragment);
        fragmentListTitels.add(title);
    }

}
