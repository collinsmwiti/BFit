//package
package com.example.collins.bfit;

//imports
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

//class MealsActivity
public class MealsActivity extends AppCompatActivity {
    private TextView mMealTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        mMealTextView = (TextView) findViewById(R.id.mealTextView);
        //gathering data from Intent
        Intent intent = getIntent();
        String meal = intent.getStringExtra("meal");
        mMealTextView.setText("Here is the details of your meal: " + meal);
    }
}
