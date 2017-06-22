package com.example.collins.bfit.util;

/**
 * Created by collins on 6/22/17.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
