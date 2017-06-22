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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

//class SavedMealListActivity
public class SavedMealListActivity extends AppCompatActivity {
    private DatabaseReference mMealReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_meals);
        ButterKnife.bind(this);

//        mMealReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MEALS);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mMealReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_MEALS)
                .child(uid);

        setUpFirebaseAdapter();
    }

    //set up firebase adapter
    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Meal, FirebaseMealViewHolder>
                (Meal.class, R.layout.meal_list_item, FirebaseMealViewHolder.class,
                        mMealReference) {

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
