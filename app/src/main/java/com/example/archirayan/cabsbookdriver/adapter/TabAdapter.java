package com.example.archirayan.cabsbookdriver.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.archirayan.cabsbookdriver.fragment.BadgesFragment;
import com.example.archirayan.cabsbookdriver.fragment.NotesFragment;

/**
 * Created by archirayan on 5/12/17.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public TabAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new BadgesFragment();
        } else {
            return new NotesFragment();
        }
    }


    @Override
    public int getCount() {
        return 2;
    }
}
