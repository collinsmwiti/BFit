//package
package com.example.collins.bfit.models;

import org.parceler.Parcel;

/**
 * Created by collins on 6/19/17.
 */

//parceler
 @Parcel
//class Meal
public class Meal {
    private String imageUrl;
    private String foodName;
    private String servingUnit;
    private String brandName;
    private String servingQty;
    private String mealCalories;

    public Meal() {}

    //constructor meal
    public Meal(String imageUrl, String foodName, String servingUnit, String brandName, String servingQty, String mealCalories) {
        this.imageUrl = imageUrl;
        this.foodName = foodName;
        this.servingUnit = servingUnit;
        this.brandName = brandName;
        this.servingQty = servingQty;
        this.mealCalories = mealCalories;
    }

    //getters methods
    public String getImageUrl() {
        return  imageUrl;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getServingUnit() {
        return servingUnit;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getServingQty() {
        return servingQty;
    }

    public String getMealCalories() {
        return mealCalories;
    }
}
