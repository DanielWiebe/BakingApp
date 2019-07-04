package com.shiftdev.masterchef.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.shiftdev.masterchef.Fragments.StepDetailFragment;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

     private ArrayList<StepDetailFragment> mFragments = new ArrayList<>();

     public MyPagerAdapter(FragmentManager fm, ArrayList<StepDetailFragment> fragments) {
          super(fm);
          mFragments = fragments;
     }

     @Override
     public Fragment getItem(int position) {
          return mFragments.get(position);
     }

     @Override
     public int getCount() {
          return mFragments.size();
     }
}
