//package
package com.example.collins.bfit.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.collins.bfit.models.Meal;
import com.example.collins.bfit.ui.MealDetailFragment;

import java.util.ArrayList;

/**
 * Created by collins on 6/20/17.
 */

//class MealPagerAdapter
public class MealPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Meal> mMeals;
    private String mSource;

    public MealPagerAdapter(FragmentManager fm, ArrayList<Meal> meals, String source) {
        super(fm);
        mMeals = meals;
        mSource = source;
    }

    @Override
    public Fragment getItem(int position) {
        return MealDetailFragment.newInstance(mMeals, position, mSource);
    }

    @Override
    public int getCount() {
        return mMeals.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMeals.get(position).getFoodName();
    }
}