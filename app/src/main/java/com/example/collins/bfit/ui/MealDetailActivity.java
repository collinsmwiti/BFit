//package
package com.example.collins.bfit.ui;

//imports
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.collins.bfit.R;
import com.example.collins.bfit.adapters.MealPagerAdapter;
import com.example.collins.bfit.models.Meal;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

//class MealDetailActivity
public class MealDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private MealPagerAdapter adapterViewPager;
    ArrayList<Meal> mMeals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);
        ButterKnife.bind(this);

        mMeals = Parcels.unwrap(getIntent().getParcelableExtra("meals"));
        int startingPosition = getIntent().getIntExtra("position", 0);
        adapterViewPager = new MealPagerAdapter(getSupportFragmentManager(), mMeals);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
