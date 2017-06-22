//package
package com.example.collins.bfit.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collins.bfit.Constants;
import com.example.collins.bfit.R;
import com.example.collins.bfit.models.Meal;
import com.example.collins.bfit.ui.MealDetailActivity;
import com.example.collins.bfit.ui.MealDetailFragment;
import com.example.collins.bfit.util.OnMealSelectedListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by collins on 6/19/17.
 */

//class MealListAdapter
public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.MealViewHolder> {
    private ArrayList<Meal> mMeals = new ArrayList<>();
    private Context mContext;
    private OnMealSelectedListener mOnMealSelectedListener;

    //constructor MealListAdapter
    public MealListAdapter(Context context, ArrayList<Meal> meals, OnMealSelectedListener mealSelectedListener) {
        mContext = context;
        mMeals = meals;
        mOnMealSelectedListener = mealSelectedListener;
    }
    //methods required for recycler.viewholder
    @Override
    public MealListAdapter.MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_list_item, parent, false);
        MealViewHolder viewHolder = new MealViewHolder(view, mMeals, mOnMealSelectedListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MealListAdapter.MealViewHolder holder, int position) {
        holder.bindMeal(mMeals.get(position));
    }

    @Override
    public int getItemCount() {
        return mMeals.size();
    }
    //class MealViewHolder
    public class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.mealImageView)
        ImageView mMealImageView;
        @Bind(R.id.mealNameTextView)
        TextView mMealTextView;
        @Bind(R.id.caloriesTextView) TextView mCaloriesTextView;
        @Bind(R.id.brandTextView) TextView mBrandTextView;

        private Context mContext;
        private int mOrientation;
        private ArrayList<Meal> mMeals = new ArrayList<>();
        private OnMealSelectedListener mMealSelectedListener;


        //constructor MealViewHolder
        public MealViewHolder(View itemView, ArrayList<Meal> meals, OnMealSelectedListener mealSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            // Determines the current orientation of the device:
            mOrientation = itemView.getResources().getConfiguration().orientation;
            mMeals = meals;
            mMealSelectedListener = mealSelectedListener;

            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);
            }
            itemView.setOnClickListener(this);
        }


        // Takes position of meal in list as parameter:
        private void createDetailFragment(int position) {
            // Creates new RestaurantDetailFragment with the given position:
            MealDetailFragment detailFragment = MealDetailFragment.newInstance(mMeals, position, Constants.SOURCE_FIND);
            // Gathers necessary components to replace the FrameLayout in the layout with the RestaurantDetailFragment:
            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            //  Replaces the FrameLayout with the RestaurantDetailFragment:
            ft.replace(R.id.mealDetailContainer, detailFragment);
            // Commits these changes:
            ft.commit();
        }

        public void bindMeal(Meal meal) {
            Picasso.with(mContext).load(meal.getImageUrl()).fit().centerCrop().into(mMealImageView);
            mMealTextView.setText(meal.getFoodName());
            mCaloriesTextView.setText("Calories: " +meal.getMealCalories());
            mBrandTextView.setText( meal.getBrandName());
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            mMealSelectedListener.onMealSelected(itemPosition, mMeals, Constants.SOURCE_FIND);
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, MealDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_MEALS, Parcels.wrap(mMeals));
                intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_FIND);
                mContext.startActivity(intent);
            }
        }
    }
}