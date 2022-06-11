package com.example.imagesegmentation.UI.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.imagesegmentation.UI.CameraXFragment;
import com.example.imagesegmentation.UI.snapFragment;

public class viewPagerAdapter extends FragmentStateAdapter {
    public viewPagerAdapter(@NonNull FragmentManager fm, Lifecycle lifecycle)
    {
        super(fm,lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return  CameraXFragment.newInstance();
            case 1:
                return snapFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
