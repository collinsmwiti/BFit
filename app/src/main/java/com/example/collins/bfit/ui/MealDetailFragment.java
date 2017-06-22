//package
package com.example.collins.bfit.ui;

//imports

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collins.bfit.Constants;
import com.example.collins.bfit.R;
import com.example.collins.bfit.models.Meal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
//class MealDetailFragment
public class MealDetailFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.mealImageView) ImageView mImageLabel;
    @Bind(R.id.mealNameTextView) TextView mNameLabel;
    @Bind(R.id.brandTextView) TextView mBrandLabel;
    @Bind(R.id.caloriesTextView) TextView mCaloriesLabel;
    @Bind(R.id.servingUnitTextView) TextView mServingUnitLabel;
    @Bind(R.id.servingQtyTextView) TextView mServingQtyLabel;
    @Bind(R.id.saveMealButton) TextView mSaveMealButton;

    private Meal mMeal;
    private ArrayList<Meal> mMeals;
    private int mPosition;
    private String mSource;


    //constructor MealDetailFragment
    public static MealDetailFragment newInstance(ArrayList<Meal> meals, Integer position, String source) {
        MealDetailFragment mealDetailFragment = new MealDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_KEY_MEALS, Parcels.wrap(meals));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);
        mealDetailFragment.setArguments(args);
        return mealDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeals = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_MEALS));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mMeal = mMeals.get(mPosition);
        mSource = getArguments().getString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_detail, container, false);
        ButterKnife.bind(this, view);

        if (mSource.equals(Constants.SOURCE_SAVED)) {
            mSaveMealButton.setVisibility(View.GONE);
        }
//        else {
            Picasso.with(view.getContext()).load(mMeal.getImageUrl()).fit().centerCrop().into(mImageLabel);
            mNameLabel.setText(mMeal.getFoodName());
            mCaloriesLabel.setText("Calories: " + mMeal.getMealCalories());
            mBrandLabel.setText("Brand: " + mMeal.getBrandName());
            mServingUnitLabel.setText("Serving unit: " + mMeal.getServingUnit());
            mServingQtyLabel.setText("Serving Quantity: " + mMeal.getServingQty());
            mSaveMealButton.setOnClickListener(this);
//        }

        return view;
    }

    @Override
    public void onClick(View v) {

        if (v == mSaveMealButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            DatabaseReference mealRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_MEALS)
                    .child(uid);

            DatabaseReference pushRef = mealRef.push();
            String pushId = pushRef.getKey();
            mMeal.setPushId(pushId);
            pushRef.setValue(mMeal);
//            mealRef.push().setValue(mMeal);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

}
