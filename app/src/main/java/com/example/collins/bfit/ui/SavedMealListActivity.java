package com.example.collins.bfit.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.collins.bfit.Constants;
import com.example.collins.bfit.R;
import com.example.collins.bfit.adapters.FirebaseMealViewHolder;
import com.example.collins.bfit.models.Meal;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

//class SavedMealListActivity
public class SavedMealListActivity extends AppCompatActivity {
    private DatabaseReference mRestaurantReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_meals);
        ButterKnife.bind(this);

        mRestaurantReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MEALS);
        setUpFirebaseAdapter();
    }

    //set up firebase adapter
    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Meal, FirebaseMealViewHolder>
                (Meal.class, R.layout.meal_list_item, FirebaseMealViewHolder.class,
                        mRestaurantReference) {

            @Override
            protected void populateViewHolder(FirebaseMealViewHolder viewHolder,
                                              Meal model, int position) {
                viewHolder.bindMeal(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
