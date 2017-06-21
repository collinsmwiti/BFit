package com.example.collins.bfit.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.collins.bfit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealsFragment extends Fragment {
    TextView text;


    public MealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meals, container, false);
        text= (TextView) view.findViewById(R.id.meals);
        String menu = getArguments().getString("Meals");
        text.setText(menu);
        return view;
    }

}
