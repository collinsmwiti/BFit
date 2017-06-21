//package
package com.example.collins.bfit.ui;

//imports

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collins.bfit.Constants;
import com.example.collins.bfit.R;

import butterknife.Bind;
import butterknife.ButterKnife;

//class MainActivity
public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Bind(R.id.findMealsButton) Button mFindMealsButton;
    @Bind(R.id.mealEditText) EditText mMealEditText;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Fragment main = new Fragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, main);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
//
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_call:
                Intent dialer= new Intent(Intent.ACTION_DIAL);
                startActivity(dialer);
                return true;
            case R.id.action_speech:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                startActivityForResult(intent, 1234);

                return true;
            case R.id.action_contacts:
                Intent call= new Intent(Intent.ACTION_DIAL);
                startActivity(call);
                return true;
            case R.id.action_settings:
                Intent settings = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(settings);
                return true;
           default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            String voice_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            Toast.makeText(getApplicationContext(),voice_text,Toast.LENGTH_LONG).show();

        }
    }

}
