package com.example.collins.bfit.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.collins.bfit.models.Meal;
import com.example.collins.bfit.util.ItemTouchHelperAdapter;
import com.example.collins.bfit.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by collins on 6/22/17.
 */

public class FirebaseMealListAdapter extends FirebaseRecyclerAdapter<Meal, FirebaseMealViewHolder> implements ItemTouchHelperAdapter {

    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseMealListAdapter(Class<Meal> modelClass, int modelLayout,
                                   Class<FirebaseMealViewHolder> viewHolderClass,
                                   Query ref, OnStartDragListener onStartDragListener, Context context) {

        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }

    @Override
    protected void populateViewHolder(final FirebaseMealViewHolder viewHolder, Meal model, int position) {
        viewHolder.bindMeal(model);
        viewHolder.mMealImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        getRef(position).removeValue();
    }
}