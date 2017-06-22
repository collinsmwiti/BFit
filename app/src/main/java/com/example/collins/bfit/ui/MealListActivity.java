//package
package com.example.collins.bfit.ui;

//imports

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.collins.bfit.Constants;
import com.example.collins.bfit.R;
import com.example.collins.bfit.models.Meal;
import com.example.collins.bfit.util.OnMealSelectedListener;

import org.parceler.Parcels;

import java.util.ArrayList;

//class MealListActivity
public class MealListActivity extends AppCompatActivity implements OnMealSelectedListener {

    private Integer mPosition;
    ArrayList<Meal> mMeals;
    String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        if (savedInstanceState != null) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mMeals = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_MEALS));
                mSource = savedInstanceState.getString(Constants.KEY_SOURCE, mSource);

                if (mPosition != null && mMeals != null) {
                    Intent intent = new Intent(this, MealDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_MEALS, Parcels.wrap(mMeals));
                    intent.putExtra(Constants.KEY_SOURCE, mSource);
                    startActivity(intent);
                }

            }

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mPosition != null && mMeals != null) {
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_MEALS, Parcels.wrap(mMeals));
            outState.putString(Constants.KEY_SOURCE, mSource);
        }

    }

    @Override
    public void onMealSelected(Integer position, ArrayList<Meal> meals, String source) {
        mPosition = position;
        mMeals = meals;
        mSource = source;
    }
}






