//package
package com.example.collins.bfit.ui;

//imports

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collins.bfit.Constants;
import com.example.collins.bfit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

//class MainActivity
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = MainActivity.class.getSimpleName();

//    private SharedPreferences mSharedPreferences;
//    private SharedPreferences.Editor mEditor;
    private DatabaseReference mSearchedMealReference;
    private ValueEventListener mSearchedMealReferenceListener;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Bind(R.id.findMealsButton) Button mFindMealsButton;
//    @Bind(R.id.mealEditText) EditText mMealEditText;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;
    @Bind(R.id.savedMealsButton) Button mSavedMealsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    //writing to the firebase
        mSearchedMealReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_MEAL);

        mSearchedMealReferenceListener = mSearchedMealReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                    String meal = mealSnapshot.getValue().toString();
                    Log.d("Meals updated", "meal: " + meal);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    //adding listener
        mSearchedMealReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                    String meal = mealSnapshot.getValue().toString();
                    Log.d("Meals updated", "meal: " + meal); //log
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };

        mSavedMealsButton.setOnClickListener(this);

        Fragment main = new Fragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, main);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
//
//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();

        //adding a font
        Typeface ranchoFont = Typeface.createFromAsset(getAssets(), "fonts/Rancho.ttf");
        mAppNameTextView.setTypeface(ranchoFont);
        //added a listener
        mFindMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gathering data from edit text
                if (v == mFindMealsButton) {
//                    String meal = mMealEditText.getText().toString();
//                    saveMealToFirebase(meal);
//                    Log.d(TAG, meal);
//                    if(!(meal).equals("")) {
//                        addToSharedPreferences(meal);
//                    }
                    Intent intent = new Intent(MainActivity.this, MealListActivity.class);
//                    intent.putExtra("meal", meal);
                    startActivity(intent);
                }
            }
        });
    }

    //saving to database
    public void saveMealToFirebase(String meal) {
        mSearchedMealReference.setValue(meal);
    }

//    //adding shared preferences
//    private void addToSharedPreferences(String meal) {
//        mEditor.putString(Constants.PREFERENCES_MEAL_KEY, meal).apply();
//    }



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

        }

        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            String voice_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            Toast.makeText(getApplicationContext(),voice_text,Toast.LENGTH_LONG).show();

        }
    }

    //removing listeners
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedMealReference.removeEventListener(mSearchedMealReferenceListener);
    }

    @Override
    public void onClick(View v) {
         if (v == mSavedMealsButton) {
            Intent intent = new Intent(MainActivity.this, SavedMealListActivity.class);
            startActivity(intent);
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
