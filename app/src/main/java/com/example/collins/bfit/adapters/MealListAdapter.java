//package
package com.example.collins.bfit.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collins.bfit.R;
import com.example.collins.bfit.models.Meal;
import com.example.collins.bfit.ui.MealDetailActivity;
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

    //constructor MealListAdapter
    public MealListAdapter(Context context, ArrayList<Meal> meals) {
        mContext = context;
        mMeals = meals;
    }
    //methods required for recycler.viewholder
    @Override
    public MealListAdapter.MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_list_item, parent, false);
        MealViewHolder viewHolder = new MealViewHolder(view);
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

        //constructor MealViewHolder
        public MealViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
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
            Intent intent = new Intent(mContext, MealDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("meals", Parcels.wrap(mMeals));
            mContext.startActivity(intent);
        }
    }
}