package com.example.collins.bfit.util;

import com.example.collins.bfit.models.Meal;

import java.util.ArrayList;

/**
 * Created by collins on 6/22/17.
 */

public interface OnMealSelectedListener {
    public void onMealSelected(Integer position, ArrayList<Meal> meals, String source);
}
