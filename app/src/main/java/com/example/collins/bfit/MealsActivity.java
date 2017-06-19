//package
package com.example.collins.bfit;

//imports

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//class MealsActivity
public class MealsActivity extends AppCompatActivity {
    public static final String TAG = MealsActivity.class.getSimpleName();
    @Bind(R.id.mealTextView) TextView mMealTextView;

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

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
