//package
package com.example.collins.bfit.models;

/**
 * Created by collins on 6/19/17.
 */

//class Meal
public class Meal {
    private String mImageUrl;
    private String mFoodName;
    private String mServingUnit;
    private String mBrandName;
    private String mServingQty;
    private String mMealCalories;

    //constructor meal
    public Meal(String imageUrl, String foodName, String servingUnit, String brandName, String servingQty, String mealCalories) {
        this.mImageUrl = imageUrl;
        this.mFoodName = foodName;
        this.mServingUnit = servingUnit;
        this.mBrandName = brandName;
        this.mServingQty = servingQty;
        this.mMealCalories = mealCalories;
    }

    //getters methods
    public String getImageUrl() {
        return  mImageUrl;
    }

    public String getFoodName() {
        return mFoodName;
    }

    public String getServingUnit() {
        return mServingUnit;
    }

    public String getBrandName() {
        return mBrandName;
    }

    public String getServingQty() {
        return mServingQty;
    }

    public String getMealCalories() {
        return mMealCalories;
    }
}
