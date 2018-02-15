package com.hfad.survey;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hfad.survey.feed.FeedFragment;
import com.hfad.survey.notifications.NotificationsFragment;

/**
 * Created by jlanecki on 02.02.18.
 */

public class FixedTabsPagerAdapter extends FragmentPagerAdapter {


    FixedTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new FeedFragment();
            case 1:
                return new NotificationsFragment();
            default:
                return null;
        }
    }
}
