package com.example.minaring.hackaton;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment
{
    ViewPager profileViewPager;
    View thisFragment;

    public Profile()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        thisFragment = inflater.inflate(R.layout.fragment_profile, container, false);

        InitControls();

        return thisFragment;
    }

    public class CustAdapter extends FragmentPagerAdapter
    {
        public CustAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            if(position == 0)
                return new ProfileFragment();
            else if (position == 1)
                return new EditProfileFragment();
            else
                return null;
        }

        @Override
        public int getCount()
        {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return super.getPageTitle(position);
        }
    }

    public void InitControls()
    {
        profileViewPager = (ViewPager)thisFragment.findViewById(R.id.profileViewPager);
        profileViewPager.setAdapter(new CustAdapter(getChildFragmentManager()));
    }

    public void  InitEvents()
    {

    }

}
