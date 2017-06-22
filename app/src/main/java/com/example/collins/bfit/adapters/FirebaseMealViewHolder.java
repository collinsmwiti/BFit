package com.example.collins.bfit.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collins.bfit.Constants;
import com.example.collins.bfit.R;
import com.example.collins.bfit.models.Meal;
import com.example.collins.bfit.ui.MealDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import static com.example.collins.bfit.R.id.mealImageView;

/**
 * Created by collins on 6/21/17.
 */

//class FirebaseMealViewHolder
public class FirebaseMealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    Context mContext;

    public ImageView mMealImageView;


    //constructor FirebaseMealViewHolder
    public FirebaseMealViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    //bind
    public void bindMeal(Meal meal) {
        mMealImageView = (ImageView) mView.findViewById(R.id.mealImageView);
        TextView foodTextView = (TextView) mView.findViewById(R.id.mealNameTextView);
        TextView caloriesTextView = (TextView) mView.findViewById(R.id.caloriesTextView);
        TextView brandTextView = (TextView) mView.findViewById(R.id.brandTextView);

        Picasso.with(mContext).load(meal.getImageUrl()).fit().centerCrop().into(mMealImageView);
        foodTextView.setText(meal.getFoodName());
        caloriesTextView.setText("Calories: " + meal.getMealCalories());
        brandTextView.setText("Brand: " + meal.getBrandName());

    }

    @Override
    public void onClick(View view) {
        final ArrayList<Meal> meals = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MEALS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    meals.add(snapshot.getValue(Meal.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, MealDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("meals", Parcels.wrap(meals));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}