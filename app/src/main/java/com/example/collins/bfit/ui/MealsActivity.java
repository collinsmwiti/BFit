//package
package com.example.collins.bfit.ui;

//imports

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.collins.bfit.R;
import com.example.collins.bfit.models.Meal;
import com.example.collins.bfit.services.NutritionixService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//class MealsActivity
public class MealsActivity extends AppCompatActivity {
    public static final String TAG = MealsActivity.class.getSimpleName();
    @Bind(R.id.mealTextView) TextView mMealTextView;
    @Bind(R.id.listView)
    ListView mListView;
    public ArrayList<Meal> mMeals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        //added butterknife
        ButterKnife.bind(this);
        //gathering data from Intent
        Intent intent = getIntent();
        String meal = intent.getStringExtra("meal");
        mMealTextView.setText("Here is the details of your meal: " + meal);
        getMeals(meal);
    }

    //receiving a response from NutritionixService class
    private void getMeals(String meal) {
        final NutritionixService nutritionixService = new NutritionixService();
        nutritionixService.findMeals(meal, new Callback() {
            //callback methods
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mMeals = nutritionixService.processResults(response);
                //to enhance threading and enable us to display data held by the API
                MealsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] mealNames = new String[mMeals.size()];
                        for (int i = 0; i< mealNames.length; i ++) {
                            mealNames[i] = mMeals.get(i).getFoodName();
                        }
                        ArrayAdapter adapter = new ArrayAdapter(MealsActivity.this, android.R.layout.simple_list_item_1, mealNames);
                        mListView.setAdapter(adapter);

                        for (Meal meal : mMeals) {
                            Log.d(TAG, "Image url: " + meal.getImageUrl());
                            Log.d(TAG, "Food name: " + meal.getFoodName());
                            Log.d(TAG, "Serving unit: " + meal.getServingUnit());
                            Log.d(TAG, "Brand name: " + meal.getBrandName());
                            Log.d(TAG, "Serving qty: " + meal.getServingQty());
                            Log.d(TAG, "Meal categories: " + meal.getMealCalories());
                        }
                    }
                });


//                try {
//                    String jsonData = response.body().string();
//                    if (response.isSuccessful()) {
//                        Log.v(TAG, jsonData);
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }
}
