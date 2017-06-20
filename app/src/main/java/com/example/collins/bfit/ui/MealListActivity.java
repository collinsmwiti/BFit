//package
package com.example.collins.bfit.ui;

//imports

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.collins.bfit.Constants;
import com.example.collins.bfit.R;
import com.example.collins.bfit.adapters.MealListAdapter;
import com.example.collins.bfit.models.Meal;
import com.example.collins.bfit.services.NutritionixService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//class MealListActivity
public class MealListActivity extends AppCompatActivity {
    public static final String TAG = MealListActivity.class.getSimpleName();
    private SharedPreferences mSharedPreferences;
    private String mRecentMeal;
//    @Bind(R.id.mealTextView) TextView mMealTextView;
//    @Bind(R.id.listView)
//    ListView mListView;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private MealListAdapter mAdapter;

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
//        mMealTextView.setText("Here is the details of your meal: " + meal);
        getMeals(meal);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentMeal = mSharedPreferences.getString(Constants.PREFERENCES_MEAL_KEY, null);
        Log.d("Shared Pref Meal", mRecentMeal);
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
                MealListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //using custom adapters with recycler view
                        mAdapter = new MealListAdapter(getApplicationContext(), mMeals);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MealListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
//                        String[] mealNames = new String[mMeals.size()];
//                        for (int i = 0; i< mealNames.length; i ++) {
//                            mealNames[i] = mMeals.get(i).getFoodName();
//                        }
//                        ArrayAdapter adapter = new ArrayAdapter(MealListActivity.this, android.R.layout.simple_list_item_1, mealNames);
//                        mListView.setAdapter(adapter);
//
//                        for (Meal meal : mMeals) {
//                            Log.d(TAG, "Image url: " + meal.getImageUrl());
//                            Log.d(TAG, "Food name: " + meal.getFoodName());
//                            Log.d(TAG, "Serving unit: " + meal.getServingUnit());
//                            Log.d(TAG, "Brand name: " + meal.getBrandName());
//                            Log.d(TAG, "Serving qty: " + meal.getServingQty());
//                            Log.d(TAG, "Meal categories: " + meal.getMealCalories());
//                        }
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
