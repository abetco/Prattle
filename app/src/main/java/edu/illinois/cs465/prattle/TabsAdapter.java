package edu.illinois.cs465.prattle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private String[] tabTitles = new String[]{"Browse Hangouts", "My Hangouts"};

    public TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                BrowseHangoutsFragment browse = new BrowseHangoutsFragment();
                return browse;
            case 1:
                MyHangoutsFragment my = new MyHangoutsFragment();
                return my;
            default:
                return null;
        }
    }
}
