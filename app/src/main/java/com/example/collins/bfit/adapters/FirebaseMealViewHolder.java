package com.example.collins.bfit.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collins.bfit.R;
import com.example.collins.bfit.models.Meal;
import com.example.collins.bfit.util.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by collins on 6/21/17.
 */

//class FirebaseMealViewHolder
public class FirebaseMealViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

    View mView;
    Context mContext;

    public ImageView mMealImageView;


    //constructor FirebaseMealViewHolder
    public FirebaseMealViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    @Override
    public void onItemSelected() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_on);
        set.setTarget(itemView);
        set.start();
    }

    @Override
    public void onItemClear() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_off);
        set.setTarget(itemView);
        set.start();
    }

    //bind
    public void bindMeal(Meal meal) {
        mMealImageView = (ImageView) mView.findViewById(R.id.mealImageView);
        TextView foodTextView = (TextView) mView.findViewById(R.id.mealNameTextView);
        TextView caloriesTextView = (TextView) mView.findViewById(R.id.caloriesTextView);
        TextView brandTextView = (TextView) mView.findViewById(R.id.brandTextView);

        if (!meal.getImageUrl().contains("http")) {
            try {
                Bitmap imageBitmap = decodeFromFirebaseBase64(meal.getImageUrl());
                mMealImageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Picasso.with(mContext).load(meal.getImageUrl()).fit().centerCrop().into(mMealImageView);
            foodTextView.setText(meal.getFoodName());
            caloriesTextView.setText("Calories: " + meal.getMealCalories());
            brandTextView.setText("Brand: " + meal.getBrandName());
        }

    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

//    @Override
//    public void onClick(View view) {
//        final ArrayList<Meal> meals = new ArrayList<>();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MEALS);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    meals.add(snapshot.getValue(Meal.class));
//                }
//
//                int itemPosition = getLayoutPosition();
//
//                Intent intent = new Intent(mContext, MealDetailActivity.class);
//                intent.putExtra("position", itemPosition + "");
//                intent.putExtra("meals", Parcels.wrap(meals));
//
//                mContext.startActivity(intent);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }

}