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
public class SquadsParent extends Fragment
{

    ViewPager squadsParentViewPager;
    View thisPager;

    public SquadsParent()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        thisPager = inflater.inflate(R.layout.fragment_squads_parent, container, false);

        InitControls();
        InitEvents();

        return thisPager;
    }

    public class CustAdapter extends FragmentPagerAdapter {

        public CustAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            if(position == 0)
                return new SquadInside();
            else
                return null;
        }

        @Override
        public int getCount()
        {
            return 1;
        }
    }

    public void InitControls()
    {
        squadsParentViewPager = (ViewPager)thisPager.findViewById(R.id.squadsParentViewPager);
        squadsParentViewPager.setAdapter(new CustAdapter(getChildFragmentManager()));
    }

    public void InitEvents()
    {

    }

}
