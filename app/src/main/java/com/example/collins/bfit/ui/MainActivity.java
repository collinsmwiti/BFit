//package
package com.example.collins.bfit.ui;

//imports

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.collins.bfit.Constants;
import com.example.collins.bfit.R;
import com.example.collins.bfit.adapters.CoverFlowAdapter;
import com.example.collins.bfit.models.Game;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

//class MainActivity
public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private FeatureCoverFlow coverFlow;
    private CoverFlowAdapter adapter;
    private ArrayList<Game> games;

    @Bind(R.id.findMealsButton) Button mFindMealsButton;
    @Bind(R.id.mealEditText) EditText mMealEditText;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);

        settingDummyData();
        adapter = new CoverFlowAdapter(this, games);
        coverFlow.setAdapter(adapter);
        coverFlow.setOnScrollPositionListener(onScrollListener());
        //added butterknife
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        //adding a font
        Typeface ranchoFont = Typeface.createFromAsset(getAssets(), "fonts/Rancho.ttf");
        mAppNameTextView.setTypeface(ranchoFont);
        //added a listener
        mFindMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gathering data from edit text
                if (v == mFindMealsButton) {
                    String meal = mMealEditText.getText().toString();
                    Log.d(TAG, meal);
                    if(!(meal).equals("")) {
                        addToSharedPreferences(meal);
                    }
                    Intent intent = new Intent(MainActivity.this, MealListActivity.class);
                    intent.putExtra("meal", meal);
                    startActivity(intent);
                }
            }
        });
    }

    //adding shared preferences
    private void addToSharedPreferences(String meal) {
        mEditor.putString(Constants.PREFERENCES_MEAL_KEY, meal).apply();
    }

    private FeatureCoverFlow.OnScrollPositionListener onScrollListener() {
        return new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                Log.v("MainActivty", "position: " + position);
            }

            @Override
            public void onScrolling() {
                Log.i("MainActivity", "scrolling");
            }
        };
    }

    private void settingDummyData() {
        games = new ArrayList<>();
        games.add(new Game(R.mipmap.oranges, "Fruit"));
        games.add(new Game(R.mipmap.burger, "Burger"));
        games.add(new Game(R.mipmap.pork, "Pork"));
        games.add(new Game(R.mipmap.cola, "Drink"));
        games.add(new Game(R.mipmap.chicken, "Chicken"));
        games.add(new Game(R.mipmap.chapati, "Chapati"));
        games.add(new Game(R.mipmap.spinach, "Spinach"));
        games.add(new Game(R.mipmap.chips, "Chips"));
        games.add(new Game(R.mipmap.milk, "Milk"));
    }
}
