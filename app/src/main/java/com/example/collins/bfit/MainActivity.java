//package
package com.example.collins.bfit;

//imports
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//class MainActivity
public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Button mFindMealsButton;
    private EditText mMealEditText;
    private TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMealEditText = (EditText) findViewById(R.id.mealEditText);
        mFindMealsButton = (Button) findViewById(R.id.findMealsButton);
        mAppNameTextView = (TextView) findViewById(R.id.appNameTextView);
        //adding a font
        Typeface ranchoFont = Typeface.createFromAsset(getAssets(), "fonts/Rancho.ttf");
        mAppNameTextView.setTypeface(ranchoFont);
        //added a listener
        mFindMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gathering data from edit text
                String meal = mMealEditText.getText().toString();
                Log.d(TAG, meal);
                Intent intent = new Intent(MainActivity.this, MealsActivity.class);
                intent.putExtra("meal", meal);
                startActivity(intent);
            }
        });
    }
}
