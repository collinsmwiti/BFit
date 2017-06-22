package com.example.collins.bfit.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.collins.bfit.Constants;
import com.example.collins.bfit.R;
import com.example.collins.bfit.adapters.FirebaseMealListAdapter;
import com.example.collins.bfit.adapters.FirebaseMealViewHolder;
import com.example.collins.bfit.models.Meal;
import com.example.collins.bfit.util.OnStartDragListener;
import com.example.collins.bfit.util.SimpleItemTouchHelperCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

//class SavedMealListActivity
public class SavedMealListActivity extends AppCompatActivity implements OnStartDragListener {
    private DatabaseReference mMealReference;
    private FirebaseMealListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_meals);
        ButterKnife.bind(this);

        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mMealReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_MEALS)
                .child(uid);

        mFirebaseAdapter = new FirebaseMealListAdapter(Meal.class,
                R.layout.meal_list_item_drag, FirebaseMealViewHolder.class,
                mMealReference, this, this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
